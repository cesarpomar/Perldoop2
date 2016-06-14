package perldoop.modelo.arbol.elsif;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.bloqueelsif.BloqueElsIf;

/**
 * Clase que representa la reduccion -> elsif : bloqueElsif elsif
 *
 * @author César Pomar
 */
public final class ElsIfElsIf extends ElsIf {

    private BloqueElsIf bloqueElsIf;
    private ElsIf elsIf;

    /**
     * Único contructor de la clase
     *
     * @param bloqueElsIf Bloque elsIf
     * @param elsIf ElsIf
     */
    public ElsIfElsIf(BloqueElsIf bloqueElsIf, ElsIf elsIf) {
        setBloqueElsIf(bloqueElsIf);
        setElsIf(elsIf);
    }

    /**
     * Obtiene le bloque elsIf
     *
     * @return Bloque elsIf
     */
    public BloqueElsIf getBloqueElsIf() {
        return bloqueElsIf;
    }

    /**
     * Establece el bloque elsIf
     *
     * @param bloqueElsIf Bloque elsIf
     */
    public void setBloqueElsIf(BloqueElsIf bloqueElsIf) {
        bloqueElsIf.setPadre(this);
        this.bloqueElsIf = bloqueElsIf;
    }

    /**
     * obtiene el elsif
     *
     * @return Elsif
     */
    public ElsIf getElsIf() {
        return elsIf;
    }

    /**
     * Establece el elsif
     *
     * @param elsIf Elsif
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
        return new Simbolo[]{bloqueElsIf, elsIf};
    }

}
