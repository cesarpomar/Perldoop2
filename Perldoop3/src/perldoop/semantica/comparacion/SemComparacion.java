package perldoop.semantica.comparacion;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.comparacion.*;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase para la semantica de comparacion
 *
 * @author César Pomar
 */
public class SemComparacion {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemComparacion(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(CompNumEq s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(CompNumNe s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(CompNumLt s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(CompNumLe s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(CompNumGt s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(CompNumGe s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(CompNumCmp s) {
        s.setTipo(new Tipo(Tipo.INTEGER));
    }

    public void visitar(CompStrEq s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(CompStrNe s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(CompStrLt s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(CompStrLe s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(CompStrGt s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(CompStrGe s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(CompStrCmp s) {
        s.setTipo(new Tipo(Tipo.INTEGER));
    }

    public void visitar(CompSmart s) {
        tabla.getGestorErrores().error(Errores.OPERADOR_SMART, s.getOperador().getTipo());
        throw new ExcepcionSemantica(Errores.OPERADOR_SMART);
    }
}
