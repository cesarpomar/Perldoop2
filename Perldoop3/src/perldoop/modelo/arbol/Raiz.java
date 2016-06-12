package perldoop.modelo.arbol;

import perldoop.modelo.arbol.fuente.Fuente;

/**
 * Clase que representa la raiz del arbol de simbolos, aunque hereda el atributo
 * padre de Simbolo, nunca tiene padre.
 *
 * @author César Pomar
 */
public final class Raiz extends Simbolo {

    private Fuente fuente;

    /**
     * Único contructor de la clase
     *
     * @param fuente Fuente
     */
    public Raiz(Fuente fuente) {
        setFuente(fuente);
    }

    /**
     * Obtiene la fuente
     *
     * @return Fuente
     */
    public Fuente getFuente() {
        return fuente;
    }

    /**
     * Establece la fuente
     *
     * @param fuente Fuente
     */
    public void setFuente(Fuente fuente) {
        fuente.setPadre(this);
        this.fuente = fuente;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{fuente};
    }

}
