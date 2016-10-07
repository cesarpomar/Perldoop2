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
     * @param abrirBloque Abertura de bloque para la cabecera
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     * @param parentesisI Parentesis izquierdo
     * @param expresion Expresiom
     * @param parentesisD Parentesis derecho
     * @param idUntil Until
     * @param puntoComa Punto y coma
     */
    public BloqueDoUntil(Terminal id, AbrirBloque abrirBloque, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD, Terminal idUntil, Terminal parentesisI, Expresion expresion, Terminal parentesisD, Terminal puntoComa) {
        super(id, abrirBloque, parentesisI, expresion, parentesisD, llaveI, cuerpo, llaveD);
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
        return new Simbolo[]{id, llaveI, cuerpo, llaveD, idUntil, parentesisI, expresion, parentesisD, puntoComa};
    }
}
