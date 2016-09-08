package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.condicional.Condicional;
import perldoop.modelo.arbol.elsif.ElsIf;

/**
 * Clase que representa la reduccion -&gt; bloque : condicional elsif
 *
 * @author César Pomar
 */
public final class BloqueCondicional extends Simbolo {

    private Condicional condicional;
    private ElsIf elsIf;

    /**
     * Único contructor de la clase
     *
     * @param condicional Condicional
     * @param elsIf Bloque elsIf
     */
    public BloqueCondicional(Condicional condicional, ElsIf elsIf) {
        setCondicional(condicional);
        setElsIf(elsIf);
    }

    /**
     * Obtiene el condicional
     *
     * @return Condicional
     */
    public Condicional getCondicional() {
        return condicional;
    }

    /**
     * Establece el condicional
     *
     * @param condicional Condicional
     */
    public void setCondicional(Condicional condicional) {
        condicional.setPadre(this);
        this.condicional = condicional;
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
        return new Simbolo[]{condicional, elsIf};
    }

}
