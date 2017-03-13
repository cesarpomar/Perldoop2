package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : DO '{' cuerpo '}' UNTIL '(' expresion ')' ';'
 *
 * @author César Pomar
 */
public final class BloqueDoUntil extends BloqueControlBasico {

    private Terminal idUntil;
    private Terminal puntoComa;

    /**
     * Único contructor de la clase
     *
     * @param id Do
     * @param contextoBloque Contexto bloque
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     * @param contextoHead Contexto de la cabecera
     * @param parentesisI Parentesis izquierdo
     * @param expresion Expresion
     * @param parentesisD Parentesis derecho
     * @param idUntil Until
     * @param puntoComa Punto y coma
     */
    public BloqueDoUntil(Terminal id, AbrirBloque contextoBloque, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD, Terminal idUntil, AbrirBloque contextoHead, Terminal parentesisI, Expresion expresion, Terminal parentesisD, Terminal puntoComa) {
        super(id, contextoHead, parentesisI, expresion, parentesisD, contextoBloque, llaveI, cuerpo, llaveD);
        setIdUntil(idUntil);
        setPuntoComa(puntoComa);
    }

    /**
     * Obtiene el idUntil
     *
     * @return idUntil
     */
    public Terminal getIdUntil() {
        return idUntil;
    }

    /**
     * Establece el idUntil
     *
     * @param idUntil idUntil
     */
    public void setIdUntil(Terminal idUntil) {
        idUntil.setPadre(this);
        this.idUntil = idUntil;
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
        return new Simbolo[]{id, contextoBloque, llaveI, cuerpo, llaveD, idUntil, contextoHead, parentesisI, expresion, parentesisD, puntoComa};
    }
}
