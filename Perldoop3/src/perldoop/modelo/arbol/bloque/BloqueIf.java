package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.condicional.Condicional;
import perldoop.modelo.arbol.contexto.Contexto;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : IF '(' expresion ')' '{' cuerpo '}' condicional
 *
 * @author César Pomar
 */
public final class BloqueIf extends BloqueControlBasico {

    private Condicional bloqueElse;

    /**
     * Único contructor de la clase
     *
     * @param id if
     * @param abrirBloque Abertura de bloque para la cabecera
     * @param parentesisI Parentesis izquierdo
     * @param expresion Expresión
     * @param parentesisD Parentesis derecho
     * @param contexto Contexto del bloque
     * @param bloqueElse BloqueElse
     */
    public BloqueIf(Terminal id, AbrirBloque abrirBloque, Terminal parentesisI, Expresion expresion, Terminal parentesisD, Contexto contexto, Condicional bloqueElse) {
        super(id, abrirBloque, parentesisI, expresion, parentesisD, contexto);
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
        return new Simbolo[]{id, parentesisI, expresion, parentesisD, contexto, bloqueElse};
    }
}
