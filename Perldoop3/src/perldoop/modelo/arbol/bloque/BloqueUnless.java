package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.condicional.Condicional;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : UNLESS '(' expresion ')' '{' cuerpo '}' condicional
 *
 * @author César Pomar
 */
public final class BloqueUnless extends BloqueControlBasico {

    private Condicional bloqueElse;

    /**
     * Único contructor de la clase
     *
     * @param id unless
     * @param abrirBloque Abertura de bloque para la cabecera
     * @param parentesisI Parentesis izquierdo
     * @param expresion Expresión
     * @param parentesisD Parentesis derecho
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     * @param bloqueElse BloqueElse
     */
    public BloqueUnless(Terminal id, AbrirBloque abrirBloque, Terminal parentesisI, Expresion expresion, Terminal parentesisD, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD, Condicional bloqueElse) {
        super(id, abrirBloque, parentesisI, expresion, parentesisD, llaveI, cuerpo, llaveD);
        setBloqueElse(bloqueElse);
    }

    /**
     * Obtiene el bloque Else
     *
     * @return Bloque Else
     */
    public Condicional getBloqueElse() {
        return bloqueElse;
    }

    /**
     * Establece el bloque Else
     *
     * @param bloqueElse Bloque Else
     */
    public void setBloqueElse(Condicional bloqueElse) {
        this.bloqueElse = bloqueElse;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, parentesisI, expresion, parentesisD, llaveI, cuerpo, llaveD, bloqueElse};
    }
}
