package perldoop.semantica.asignacion;

import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpVariable;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.semantica.util.Tipos;

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
        if (!(s.getIzquierda() instanceof ExpVariable)) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_CONSTANTE, s.getIgual().getToken(), s.getIgual().toString());
        }
        Tipos.casting(s.getDerecha(), s.getIzquierda().getTipo(), tabla.getGestorErrores());
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getIzquierda().getCodigoGenerado()).append(" = ").append(s.getDerecha().getCodigoGenerado());
        s.setCodigoGenerado(codigo);
    }

    /**
     * Asignación uno a varios
     *
     * @param s igual
     */
    public void uno_multi(Igual s) {
        if (!(s.getIzquierda() instanceof ExpVariable)) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_CONSTANTE, s.getIgual().getToken(), s.getIgual().toString());
        }
        Tipos.casting(s.getDerecha(), s.getIzquierda().getTipo(), tabla.getGestorErrores());
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getIzquierda().getCodigoGenerado()).append(" = ").append(s.getDerecha().getCodigoGenerado());
        s.setCodigoGenerado(codigo);
    }

    /**
     * Asignación varios a uno
     *
     * @param s igual
     */
    public void multi_uno(Igual s) {

    }

    /**
     * Asignación varios a varios
     *
     * @param s igual
     */
    public void multi_multi(Igual s) {

    }

    /**
     * Asignación inicializacion colección
     *
     * @param s igual
     */
    public void inicializacion(Igual s) {

    }
}
