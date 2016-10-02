package perldoop.generacion.asignacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.acceso.Acceso;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.expresion.ExpFuncion5;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.Etiquetas;
import perldoop.modelo.preprocesador.EtiquetasInicializacion;
import perldoop.modelo.preprocesador.EtiquetasTipo;
import perldoop.modelo.semantica.EntradaVariable;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de igual
 *
 * @author César Pomar
 */
public class GenIgual {

    private TablaGenerador tabla;

    /**
     * Contruye el gener
     *
     * @param tabla Tabla de simbolos
     */
    public GenIgual(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Igual s) {
        if (s.getIzquierda() instanceof ExpColeccion) {
            multiple(s);
        } else if (s.getDerecha() instanceof ExpColeccion) {
            ExpColeccion expcol = (ExpColeccion) s.getDerecha();
            if (expcol.getColeccion() instanceof ColParentesis && expcol.getColeccion().getLista().getElementos().isEmpty()) {
                inicializacion(s);
            } else {
                simple(s);
            }
        } else {
            simple(s);
        }
    }

    /**
     * Asignación simple, es decir la asignacion se realiza sobre una sola variable
     *
     * @param s Simbolo de asignacion
     */
    private void simple(Igual s) {
        if (s.getDerecha() instanceof ExpFuncion || s.getDerecha() instanceof ExpFuncion5) {
            Simbolo retorno = new Terminal();
            if (s.getIzquierda().getTipo().isColeccion()) {
                retorno.setCodigoGenerado(new StringBuilder(s.getDerecha().getCodigoGenerado()).append("[0]"));
                retorno.setTipo(new Tipo(Tipo.BOX));
            } else {
                retorno = s.getDerecha();
            }
            s.setCodigoGenerado(asignacion(s.getIzquierda(), s.getIgual().getComentario(), retorno));
        } else {
            s.setCodigoGenerado(asignacion(s.getIzquierda(), s.getIgual().getComentario(), s.getDerecha()));
        }
    }

    /**
     * Asignación multiple, es decir se asignan multiples variables
     *
     * @param s Simbolo de asignacion
     */
    private void multiple(Igual s) {
        List<Expresion> variables = ((ColParentesis) ((ExpColeccion) s.getIzquierda()).getColeccion()).getLista().getExpresiones();
        List<StringBuilder> asignaciones = new ArrayList<>(variables.size());
        if (s.getDerecha() instanceof ExpColeccion) {
            List<Expresion> valores = ((ColParentesis) ((ExpColeccion) s.getDerecha()).getColeccion()).getLista().getExpresiones();
            for (int i = 0; i < variables.size(); i++) {
                asignaciones.add(asignacion(variables.get(i), "", valores.get(i)));
            }
        } else {
            String valor;
            if (Buscar.isVariable(s.getDerecha())) {
                valor = s.getCodigoGenerado().toString();
            } else {
                StringBuilder variable = new StringBuilder(100);
                valor = tabla.getGestorReservas().getAux();
                variable.append(Tipos.declaracion(s.getDerecha().getTipo())).append(" ").append(valor).append(";");
                asignaciones.add(new StringBuilder(100).append(valor).append("=").append(s.getDerecha()));
                tabla.getDeclaraciones().add(variable);
            }
            Terminal coleccion = new Terminal();
            coleccion.setTipo(s.getIzquierda().getTipo().getSubtipo(1));
            if (s.getDerecha().getTipo().isArray()) {
                for (int i = 0; i < variables.size(); i++) {
                    coleccion.setCodigoGenerado(new StringBuilder(valor).append("[").append(i).append(']'));
                    asignaciones.add(asignacion(variables.get(i), "", coleccion));
                }
            } else {
                for (int i = 0; i < variables.size(); i++) {
                    coleccion.setCodigoGenerado(new StringBuilder(valor).append(".get(").append(i).append(')'));
                    asignaciones.add(asignacion(variables.get(i), "", coleccion));
                }
            }
            multiasignacion(s, asignaciones);
        }
    }

    /**
     * Asignación para inicializacion colección
     *
     * @param s Simbolo de asignacion
     */
    private void inicializacion(Igual s) {
        List<Token> sizes = null;
        Etiquetas etiquetas = s.getIgual().getEtiquetas();
        //Obtenemos las etiquetas de tamaño
        if (etiquetas == null) {
            sizes = new ArrayList<>();
        } else if (etiquetas instanceof EtiquetasInicializacion) {
            sizes = ((EtiquetasInicializacion) etiquetas).getSizes();
        } else if (etiquetas instanceof EtiquetasTipo) {
            sizes = ((EtiquetasTipo) etiquetas).getSizes();
        }
        //Por cada acceso a la coleccion eliminamos un tamaño
        int accesos = Buscar.accesos(s.getIzquierda());
        if (sizes.size() > accesos) {
            sizes = sizes.subList(accesos, sizes.size());
        } else {
            sizes = new ArrayList<>();
        }
        //Cogemos los tamaños y tranformanos los tamaños variables
        String[] tams = new String[sizes.size()];
        for (int i = 0; i < sizes.size(); i++) {
            String valor = sizes.get(i).getValor();
            char c = valor.charAt(0);
            if (c == '$' || c == '@') {
                EntradaVariable entrada = tabla.getTablaSimbolos().buscarVariable(valor.substring(1), c);
                Terminal var = new Terminal();
                var.setTipo(entrada.getTipo());
                var.setCodigoGenerado(new StringBuilder(entrada.getAlias()));
                valor = Casting.toInteger(var).toString();
            }
            tams[i] = valor;
        }
        s.setCodigoGenerado(Tipos.inicializacion(s.getTipo(), tams));
    }

    /**
     * Realiza la asignacion
     *
     * @param izq Simbolo izquierda
     * @param igual Comentario simbolo igual
     * @param der Simbolo derecho
     * @return Codigo asignacion
     */
    private StringBuilder asignacion(Simbolo izq, String igual, Simbolo der) {
        StringBuilder codigo = new StringBuilder(100);
        Simbolo der2 = der;
        //Copiar colecciones y Referencias
        if (der.getTipo().isColeccion()) {
            der2 = new Terminal();
            der2.setTipo(der.getTipo());
            StringBuilder copia = new StringBuilder(50 + der.getCodigoGenerado().length());
            copia.append("Pd.copy(").append(der.getCodigoGenerado()).append(")");
            der2.setCodigoGenerado(copia);
        }
        //Usar una funcion en lugar del operador =
        if (izq instanceof ExpAcceso) {
            Acceso acceso = ((ExpAcceso) izq).getAcceso();
            Tipo t = acceso.getExpresion().getTipo();
            Tipo st = t.getSubtipo(1);
            if (acceso.getTipo().isColeccion() || t.isList() || t.isMap() || (t.isRef() && (st.isList() || st.isMap()))) {
                codigo.append(izq.getCodigoGenerado())
                        .append(igual)
                        .append(Casting.casting(der2, izq.getTipo())).append(")");
                return codigo;
            }
        }
        codigo.append(izq.getCodigoGenerado())
                .append("=").append(igual)
                .append(Casting.casting(der2, izq.getTipo()));
        return codigo;
    }

    /**
     * Une multiples asignaciones teniendo en cuenta el contexto de su uso
     *
     * @param s Simbolo asginacion
     * @param asignaciones Lista de asignaciones
     * @return Codigo asignaciones
     */
    private StringBuilder multiasignacion(Igual s, List<StringBuilder> asignaciones) {
        StringBuilder codigo = new StringBuilder(100);
        if (Buscar.getPadre(s, 2) instanceof StcLista) {
            Iterator<StringBuilder> it = asignaciones.iterator();
            while (it.hasNext()) {
                codigo.append(it.next());
                if (it.hasNext()) {
                    codigo.append(";");
                }
            }
        } else {
            Iterator<StringBuilder> itAsigns = asignaciones.iterator();
            codigo.append("Pd.equals(");
            while (itAsigns.hasNext()) {
                codigo.append(itAsigns.next());
                if (itAsigns.hasNext()) {
                    codigo.append(",");
                }
            }
            codigo.append(")");
        }
        return codigo;
    }
}
