package perldoop.modelo.arbol.fuente;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.masfuente.MasFuente;

/**
 * Clase que representa la reduccion -> fuente : cuerpo masFuente
 *
 * @author César Pomar
 */
public final class Fuente extends Simbolo {

    private Cuerpo cuerpo;
    private MasFuente masFuente;

    /**
     * Único contructor de la clase
     *
     * @param cuerpo Cuerpo
     * @param masFuente MasFuente
     */
    public Fuente(Cuerpo cuerpo, MasFuente masFuente) {
        setCuerpo(cuerpo);
        setMasFuente(masFuente);
    }

    /**
     * Obtiene el Cuerpo
     *
     * @return Cuerpo
     */
    public Cuerpo getCuerpo() {
        return cuerpo;
    }

    /**
     * Establece el cuerpo
     *
     * @param cuerpo Cuerpo
     */
    public void setCuerpo(Cuerpo cuerpo) {
        cuerpo.setPadre(this);
        this.cuerpo = cuerpo;
    }

    /**
     * Obtiene el masFuente
     *
     * @return Mas Fuente
     */
    public MasFuente getMasFuente() {
        return masFuente;
    }

    /**
     * Estabelce el masFuente
     *
     * @param masFuente Masfuente
     */
    public void setMasFuente(MasFuente masFuente) {
        masFuente.setPadre(this);
        this.masFuente = masFuente;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{cuerpo, masFuente};
    }

}
