package perldoop.generacion.asignacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.expresion.ExpFuncion5;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.generacion.TablaGenerador;
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
            if (s.getDerecha() instanceof ExpColeccion) {
                multi_multi(s);
            } else {
                multi_uno(s);
            }
        } else if (s.getDerecha() instanceof ExpColeccion) {
            uno_multi(s);
        } else {
            uno_uno(s);
        }
    }

    /**
     * Asignación uno a uno
     *
     * @param s igual
     */
    public void uno_uno(Igual s) {
        if (s.getDerecha() instanceof ExpFuncion || s.getDerecha() instanceof ExpFuncion5) {
            Terminal exp = new Terminal();
            exp.setCodigoGenerado(new StringBuilder(s.getDerecha().getCodigoGenerado()).append("[0]"));
            exp.setTipo(new Tipo(Tipo.BOX));
            s.setCodigoGenerado(asignacion(s.getIzquierda(), s.getIgual().getCodigoGenerado(), exp));
        } else {
            s.setCodigoGenerado(asignacion(s.getIzquierda(), s.getIgual().getCodigoGenerado(), s.getDerecha()));
        }
    }

    /**
     * Asignación uno a varios
     *
     * @param s igual
     */
    public void uno_multi(Igual s) {
        if (s.getDerecha() instanceof ExpColeccion) {
            ExpColeccion expcol = (ExpColeccion) s.getDerecha();
            if (expcol.getColeccion() instanceof ColParentesis && expcol.getColeccion().getLista().getElementos().isEmpty()) {
                inicializacion(s);
                return;
            }
        }
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getIzquierda().getCodigoGenerado())
                .append(s.getIgual().getCodigoGenerado())
                .append(s.getDerecha().getCodigoGenerado());
        s.setCodigoGenerado(codigo);
    }

    /**
     * Asignación varios a uno
     *
     * @param s igual
     */
    public void multi_uno(Igual s) {
        ColParentesis ci = (ColParentesis) ((ExpColeccion) s.getIzquierda()).getColeccion();
        Terminal ter = new Terminal();
        String der;
        ter.setTipo(s.getDerecha().getTipo().getSubtipo(1));
        List<StringBuilder> asings = new ArrayList<>(10);
        if (!s.getDerecha().getTipo().isVariable()) {
            StringBuilder declaracion = new StringBuilder(100);
            der = tabla.getGestorReservas().getAux();
            declaracion.append(Tipos.declaracion(s.getDerecha().getTipo())).append(" ").append(der).append(";");
            asings.add(new StringBuilder(100).append(der).append("=").append(s.getDerecha()));
            tabla.getDeclaraciones().add(declaracion);
        } else {
            der = s.getCodigoGenerado().toString();
        }
        int i = 0;
        for (Expresion exp : ci.getLista().getExpresiones()) {
            if (s.getDerecha().getTipo().isArray()) {
                ter.setCodigoGenerado(new StringBuilder(der).append("[").append(i++).append("]"));
            } else {
                ter.setCodigoGenerado(new StringBuilder(der).append("(").append(i++).append(")"));
            }
            asings.add(asignacion(exp, new StringBuilder("="), ter));
        }
        s.setCodigoGenerado(multiasignacion(s, asings));
    }

    /**
     * Asignación varios a varios
     *
     * @param s igual
     */
    public void multi_multi(Igual s) {
        ColParentesis ci = (ColParentesis) ((ExpColeccion) s.getIzquierda()).getColeccion();
        ColParentesis cd = (ColParentesis) ((ExpColeccion) s.getDerecha()).getColeccion();
        List<StringBuilder> asings = new ArrayList<>(10);
        if (ci.getLista().getExpresiones().size() == cd.getLista().getExpresiones().size()) {
            Iterator<Expresion> iti = ci.getLista().getExpresiones().iterator();
            Iterator<Expresion> itd = cd.getLista().getExpresiones().iterator();
            while (iti.hasNext()) {
                StringBuilder asig = new StringBuilder(100);
                Expresion var = iti.next();
                asig.append(var);
                asig.append("=");
                asig.append(Casting.casting(itd.next(), var.getTipo()));
                asings.add(asig);
            }
        } else {
            //Comprobar  que pasa si no son iguales
        }
        s.setCodigoGenerado(multiasignacion(s, asings));
    }

    /**
     * Asignación inicializacion colección
     *
     * @param s igual
     */
    public void inicializacion(Igual s) {
        String[] tams = new String[0];
        //TODO
        s.setCodigoGenerado(Tipos.inicializacion(s.getTipo(), tams));
    }

    private StringBuilder asignacion(Simbolo izq, StringBuilder igual, Simbolo der) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(izq.getCodigoGenerado())
                .append(igual)
                .append(Casting.casting(der, izq.getTipo()));
        return codigo;
    }

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
