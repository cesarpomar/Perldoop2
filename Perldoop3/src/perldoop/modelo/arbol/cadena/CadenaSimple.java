package perldoop.modelo.arbol.cadena;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; cadena : '\'' TEXTO '\''
 *
 * @author César Pomar
 */
public final class CadenaSimple extends Cadena {

    private Terminal comillaI;
    private Terminal texto;
    private Terminal comillaD;

    /**
     * Contructor unico de la clase
     *
     * @param comillaI Comilla izquierda
     * @param texto Texto
     * @param comillaD Comilla derecha
     */
    public CadenaSimple(Terminal comillaI, Terminal texto, Terminal comillaD) {
        setComillaI(comillaI);
        setTexto(texto);
        setComillaD(comillaD);
    }

    /**
     * Obtiene la comilla izquierda
     *
     * @return Comilla izquierda
     */
    public Terminal getComillaI() {
        return comillaI;
    }

    /**
     * Establece la comilla izquierda
     *
     * @param comillaI Comilla izquierda
     */
    public void setComillaI(Terminal comillaI) {
        comillaI.setPadre(this);
        this.comillaI = comillaI;
    }

    /**
     * Obtiene el texto
     *
     * @return Texto
     */
    public Terminal getTexto() {
        return texto;
    }

    /**
     * Establece el texto
     *
     * @param texto Texto
     */
    public void setTexto(Terminal texto) {
        texto.setPadre(this);
        this.texto = texto;
    }

    /**
     * Obtiene la comilla derecha
     *
     * @return Comilla derecha
     */
    public Terminal getComillaD() {
        return comillaD;
    }

    /**
     * Establece la comilla derecha
     *
     * @param comillaD Comilla derecha
     */
    public void setComillaD(Terminal comillaD) {
        comillaD.setPadre(this);
        this.comillaD = comillaD;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{comillaI, texto, comillaD};
    }

}
