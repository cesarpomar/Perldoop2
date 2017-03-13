package perldoop.semantica.logico;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueControlBasico;
import perldoop.modelo.arbol.bloque.BloqueFor;
import perldoop.modelo.arbol.bloque.SubBloqueElsif;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.logico.*;
import perldoop.modelo.arbol.regulares.RegularMatch;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;
import perldoop.util.Buscar;

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
     * Comprueba si la operacion logica debe ser una condicion booleana
     *
     * @param s Logico
     * @return Es una condicion
     */
    public boolean checkCondicion(Logico s) {
        //Contiene una expresion regular match
        for(Simbolo hijo:s.getHijos()){
            if(hijo instanceof Expresion){
                Expresion exp = Buscar.getExpresion((Expresion)hijo);
                if(exp.getValor() instanceof RegularMatch){
                    return true;
                }
            }
        }        
        //Uso como condicion en bloque condicional
        Bloque bloque = Buscar.buscarPadre(s, Bloque.class);
        Simbolo head = null;
        if (bloque instanceof BloqueControlBasico) {
            head = ((BloqueControlBasico) bloque).getExpresion();
        }else if (bloque instanceof SubBloqueElsif) {
            head = ((SubBloqueElsif) bloque).getExpresion();
        }else if (bloque instanceof BloqueFor) {
            head = ((BloqueFor) bloque).getLista2();
        }
        return head != null && Buscar.isHijo(s, head);
    }

    /**
     * Calcula el tipo de la operacion logica
     *
     * @param izq Expresion izquierda
     * @param s Simbolo
     * @param der Expresion derecha
     */
    private void setTipo(Expresion izq, Logico s, Expresion der) {
        if (izq.getTipo().isBoolean() || der.getTipo().isBoolean() || checkCondicion(s)) {
            s.setTipo(new Tipo(Tipo.BOOLEAN));
        } else if (izq.getTipo().equals(der.getTipo())) {
            s.setTipo(izq.getTipo());
        } else if (izq.getTipo().isNumberType() && der.getTipo().isNumberType()) {
            s.setTipo(new Tipo(Tipo.DOUBLE));
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
        if (s.getCierta().getTipo().equals(s.getFalsa().getTipo())) {
            s.setTipo(s.getCierta().getTipo());
        } else {
            s.setTipo(new Tipo(Tipo.BOX));
            Tipos.casting(s.getCierta(), s.getTipo(), tabla.getGestorErrores());
            Tipos.casting(s.getFalsa(), s.getTipo(), tabla.getGestorErrores());
        }
    }
}
