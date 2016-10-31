package perldoop.semantica.asignacion;

import perldoop.modelo.arbol.asignacion.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de asignacion
 *
 * @author César Pomar
 */
public class SemAsignacion {

    private TablaSemantica tabla;
    private SemIgual semIgual;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemAsignacion(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Obtiene la semantica de Igual
     *
     * @return Semantica Igual
     */
    private SemIgual getSemIgual() {
        if (semIgual == null) {
            semIgual = new SemIgual(tabla);
        }
        return semIgual;
    }

    /**
     * Comprueba que la asignacion puede realizarse
     *
     * @param a Asignacion
     */
    private void checkVar(Asignacion a) {
        getSemIgual().checkAsignacion(a.getIzquierda(), a.getOperador(), a.getDerecha());
    }

    public void visitar(Igual s) {
        getSemIgual().visitar(s);
    }

    public void visitar(MasIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
    }

    public void visitar(MenosIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(MultiIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(DivIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(ModIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(PowIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(AndIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(OrIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(XorIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(DespDIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(DespIIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(LOrIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(DLOrIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(LAndIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(XIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

    public void visitar(ConcatIgual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkVar(s);
    }

}
