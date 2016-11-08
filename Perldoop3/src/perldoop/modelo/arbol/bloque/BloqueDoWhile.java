package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.contexto.Contexto;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : DO '{' cuerpo '}' WHILE '(' expresion ')' ';'
 *
 * @author César Pomar
 */
public final class BloqueDoWhile extends BloqueControlBasico {

    private Terminal idWhile;
    private Terminal puntoComa;

    /**
     * Único contructor de la clase
     *
     * @param id Do
     * @param abrirBloque Abertura de bloque para la cabecera
     * @param contexto Contexto del bloque
     * @param parentesisI Parentesis izquierdo
     * @param expresion Expresiom
     * @param parentesisD Parentesis derecho
     * @param idWhile While
     * @param puntoComa Punto y coma
     */
    public BloqueDoWhile(Terminal id, AbrirBloque abrirBloque, Contexto contexto, Terminal idWhile, Terminal parentesisI, Expresion expresion, Terminal parentesisD, Terminal puntoComa) {
        super(id, abrirBloque, parentesisI, expresion, parentesisD, contexto);
        setIdWhile(idWhile);
        setPuntoComa(puntoComa);
    }

    /**
     * Obtiene el idWhile
     *
     * @return idWhile
     */
    public Terminal getIdWhile() {
        return idWhile;
    }

    /**
     * Establece el idWhile
     *
     * @param idWhile idWile
     */
    public void setIdWhile(Terminal idWhile) {
        idWhile.setPadre(this);
        this.idWhile = idWhile;
    }

    /**
     * Obtiene el punto y coma
     *
     * @return Punto y coma
     */
    public Terminal getPuntoComa() {
        return puntoComa;
    }

    /**
     * Establece el punto y coma
     *
     * @param puntoComa Punto y coma
     */
    public void setPuntoComa(Terminal puntoComa) {
        puntoComa.setPadre(this);
        this.puntoComa = puntoComa;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, contexto, idWhile, parentesisI, expresion, parentesisD, puntoComa};
    }
}
