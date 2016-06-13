package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.bloqueif.BloqueIf;
import perldoop.modelo.arbol.elsif.ElsIf;

/**
 * Clase que representa la reduccion -> bloque : bloqueIf elsif
 *
 * @author César Pomar
 */
public final class BloqueCondicional extends Simbolo {

    private BloqueIf bloqueIf;
    private ElsIf elsIf;

    /**
     * Único contructor de la clase
     *
     * @param bloqueIf Bloque if
     * @param elsIf Bloque elsIf
     */
    public BloqueCondicional(BloqueIf bloqueIf, ElsIf elsIf) {
        setBloqueIf(bloqueIf);
        setElsIf(elsIf);
    }

    /**
     * Obtiene el bloque if
     *
     * @return Bloque if
     */
    public BloqueIf getBloqueIf() {
        return bloqueIf;
    }

    /**
     * Establece el bloque if
     *
     * @param bloqueIf Bloque if
     */
    public void setBloqueIf(BloqueIf bloqueIf) {
        bloqueIf.setPadre(this);
        this.bloqueIf = bloqueIf;
    }

    /**
     * Obtiene el bloque elsIf
     *
     * @return Bloque elsIf
     */
    public ElsIf getElsIf() {
        return elsIf;
    }

    /**
     * Establece el bloque elsIf
     *
     * @param elsIf Bloque elsIf
     */
    public void setElsIf(ElsIf elsIf) {
        elsIf.setPadre(this);
        this.elsIf = elsIf;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{bloqueIf, elsIf};
    }

}
