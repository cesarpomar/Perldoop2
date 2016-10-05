package perldoop.semantica.asignacion;

import java.util.ArrayList;
import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpVariable;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.Etiquetas;
import perldoop.modelo.preprocesador.EtiquetasInicializacion;
import perldoop.modelo.preprocesador.EtiquetasTipo;
import perldoop.modelo.semantica.EntradaVariable;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de igual
 *
 * @author César Pomar
 */
public class SemIgual {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemIgual(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Igual s) {
        if (s.getIzquierda() instanceof ExpColeccion) {
            multiple(s);
        } else if (s.getDerecha() instanceof ExpColeccion) {
            ExpColeccion expcol = (ExpColeccion) s.getDerecha();
            if (expcol.getColeccion().getLista().getElementos().isEmpty()) {
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
        checkAsignacion(s.getIzquierda(), s.getIgual(), s.getDerecha());
        s.setTipo(new Tipo(s.getIzquierda().getTipo()));
    }

    /**
     * Asignación multiple, es decir se asignan multiples variables
     *
     * @param s Simbolo de asignacion
     */
    private void multiple(Igual s) {
        List<Expresion> variables = ((ColParentesis) ((ExpColeccion) s.getIzquierda()).getColeccion()).getLista().getExpresiones();
        if (s.getDerecha() instanceof ExpColeccion) {
            List<Expresion> valores = ((ColParentesis) ((ExpColeccion) s.getDerecha()).getColeccion()).getLista().getExpresiones();
            for (int i = 0; i < variables.size() && i < valores.size(); i++) {
                checkAsignacion(variables.get(i), s.getIgual(), valores.get(i));
            }
        } else {
            Tipo t = s.getDerecha().getTipo();
            Simbolo coleccion = new SimboloAux();
            if (t.isColeccion()) {
                coleccion.setTipo(t.getSubtipo(1));
            } else {
                tabla.getGestorErrores().error(Errores.IGUAL_COLECION_REQUERIDA, Buscar.tokenInicio(s.getDerecha()));
                throw new ExcepcionSemantica(Errores.IGUAL_COLECION_REQUERIDA);
            }
            for (Expresion var : variables) {
                checkAsignacion(var, s.getIgual(), coleccion);
            }
        }
        s.setTipo(new Tipo(Tipo.INTEGER));
    }

    /**
     * Asignación para inicializacion colección
     *
     * @param s Simbolo de asignacion
     */
    private void inicializacion(Igual s) {
        checkAsignacion(s.getIzquierda(), s.getIgual(), s.getDerecha());
        Tipo t = s.getIzquierda().getTipo();
        int accesos = Buscar.accesos(s.getIzquierda());
        s.setTipo(new Tipo(t));
        List<Token> sizes = null;
        Etiquetas etiquetas = s.getIgual().getEtiquetas();
        //Obtenemos las etiquetas de tamaño
        if (etiquetas == null) {
            sizes = new ArrayList<>();
        } else if (etiquetas instanceof EtiquetasInicializacion) {
            sizes = ((EtiquetasInicializacion) etiquetas).getSizes();
        } else if (etiquetas instanceof EtiquetasTipo) {
            sizes = ((EtiquetasTipo) etiquetas).getSizes();
            //Por cada acceso a la coleccion eliminamos un tamaño       
            if (accesos < sizes.size()) {
                sizes = sizes.subList(accesos, sizes.size());
            } else {
                sizes = new ArrayList<>();
            }
        }
        //Comprobamos las variables
        int tams = 0;
        for (Token token : sizes) {
            if (token == null) {
                continue;//Aunque no se usen comprobamos todos
            }
            tams++;
            String valor = token.getValor();
            valor = valor.substring(1, valor.length() - 1);
            char c = valor.charAt(0);
            if (c == '$' || c == '@' || c == '%') {
                if (c == '%') {
                    tabla.getGestorErrores().error(Errores.MAPA_NO_TAM, token);
                    throw new ExcepcionSemantica(Errores.MAPA_NO_TAM);
                }
                EntradaVariable entrada = tabla.getTablaSimbolos().buscarVariable(valor.substring(1), c);
                if (entrada == null) {
                    tabla.getGestorErrores().error(Errores.VARIABLE_NO_EXISTE, token, valor, c);
                    throw new ExcepcionSemantica(Errores.VARIABLE_NO_EXISTE);
                }
            }
        }
        if (!t.isColeccion() && accesos == 0 && !(t.isRef() && s.getIzquierda() instanceof ExpVariable)) {
            tabla.getGestorErrores().error(Errores.INICIALIZACION_SOLO_COLECIONES, Buscar.tokenInicio(s.getDerecha()));
            throw new ExcepcionSemantica(Errores.INICIALIZACION_SOLO_COLECIONES);
        }
        if ((t.isArray() || t.isRef() && t.getSubtipo(1).isArray()) && tams == 0) {
            tabla.getGestorErrores().error(Errores.ARRAY_INICIALIZACION_TAM, Buscar.tokenInicio(s.getIzquierda()));
            throw new ExcepcionSemantica(Errores.ARRAY_INICIALIZACION_TAM);
        }
        s.setTipo(new Tipo(s.getIzquierda().getTipo()));
    }

    /**
     * Comprueba que la asignación puede realizarse
     *
     * @param izq Expresion izquierda
     * @param operador Operador
     * @param der Expresion derecha
     */
    private void checkAsignacion(Simbolo izq, Terminal operador, Simbolo der) {
        if (!Buscar.isVariable(izq)) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_CONSTANTE, Buscar.tokenInicio(izq), operador.getValor());
            throw new ExcepcionSemantica(Errores.MODIFICAR_CONSTANTE);
        }
        Tipos.casting(der, izq.getTipo(), tabla.getGestorErrores());
    }

}
