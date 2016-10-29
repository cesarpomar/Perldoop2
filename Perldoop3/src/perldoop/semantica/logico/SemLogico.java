package perldoop.semantica.logico;

import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.logico.*;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;

/**
 * Clase para la semantica de logico
 *
 * @author César Pomar
 */
public class SemLogico {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemLogico(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Calcula el tipo de la operacion logica
     *
     * @param izq Expresion izquierda
     * @param s Simbolo
     * @param der Expresion derecha
     */
    private void setTipo(Expresion izq, Logico s, Expresion der) {
        if (izq.getTipo().isBoolean() || der.getTipo().isBoolean()) {
            s.setTipo(new Tipo(Tipo.BOOLEAN));
        } else if (izq.getTipo().equals(der.getTipo())) {
            s.setTipo(izq.getTipo());
        } else if (izq.getTipo().isNumberType() && der.getTipo().isNumberType()) {
            s.setTipo(new Tipo(Tipo.NUMBER));
        } else if (!izq.getTipo().isColeccion() || !der.getTipo().isColeccion()) {
            s.setTipo(new Tipo(Tipo.BOX));
        } else {
            s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
        }
    }

    public void visitar(LogOr s) {
        setTipo(s.getIzquierda(), s, s.getDerecha());
        Tipos.casting(s.getIzquierda(), s.getTipo(), tabla.getGestorErrores());
        Tipos.casting(s.getDerecha(), s.getTipo(), tabla.getGestorErrores());
    }

    public void visitar(DLogOr s) {
        setTipo(s.getIzquierda(), s, s.getDerecha());
        Tipos.casting(s.getIzquierda(), s.getTipo(), tabla.getGestorErrores());
        Tipos.casting(s.getDerecha(), s.getTipo(), tabla.getGestorErrores());
    }

    public void visitar(LogAnd s) {
        setTipo(s.getIzquierda(), s, s.getDerecha());
        Tipos.casting(s.getIzquierda(), s.getTipo(), tabla.getGestorErrores());
        Tipos.casting(s.getDerecha(), s.getTipo(), tabla.getGestorErrores());
    }

    public void visitar(LogNot s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(LogOrBajo s) {
        setTipo(s.getIzquierda(), s, s.getDerecha());
        Tipos.casting(s.getIzquierda(), s.getTipo(), tabla.getGestorErrores());
        Tipos.casting(s.getDerecha(), s.getTipo(), tabla.getGestorErrores());
    }

    public void visitar(LogAndBajo s) {
        setTipo(s.getIzquierda(), s, s.getDerecha());
        Tipos.casting(s.getIzquierda(), s.getTipo(), tabla.getGestorErrores());
        Tipos.casting(s.getDerecha(), s.getTipo(), tabla.getGestorErrores());
    }

    public void visitar(LogNotBajo s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(LogXorBajo s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
    }

    public void visitar(LogTernario s) {
        setTipo(s.getCierta(), s, s.getFalsa());
        Tipos.casting(s.getCierta(), s.getTipo(), tabla.getGestorErrores());
        Tipos.casting(s.getFalsa(), s.getTipo(), tabla.getGestorErrores());
    }
}
