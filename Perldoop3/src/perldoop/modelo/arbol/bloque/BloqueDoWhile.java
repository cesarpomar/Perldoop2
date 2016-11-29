package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
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
     * @param contextoBloque Contexto bloque
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     * @param parentesisI Parentesis izquierdo
     * @param contextoHead Contexto cabecera
     * @param expresion Expresiom
     * @param parentesisD Parentesis derecho
     * @param idWhile While
     * @param puntoComa Punto y coma
     */
    public BloqueDoWhile(Terminal id, AbrirBloque contextoBloque, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD, Terminal idWhile, AbrirBloque contextoHead, Terminal parentesisI, Expresion expresion, Terminal parentesisD, Terminal puntoComa) {
        super(id, contextoHead, parentesisI, expresion, parentesisD, contextoBloque, llaveI, cuerpo, llaveD);
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
        return new Simbolo[]{id, contextoBloque, llaveI, cuerpo, llaveD, idWhile, contextoHead, parentesisI, expresion, parentesisD, puntoComa};
    }
}
