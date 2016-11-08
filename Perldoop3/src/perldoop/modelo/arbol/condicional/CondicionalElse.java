package perldoop.modelo.arbol.condicional;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.contexto.Contexto;

/**
 * Clase que representa la reduccion -&gt;<br> condicional : ELSE contexto
 *
 * @author César Pomar
 */
public final class CondicionalElse extends Condicional {

    private Terminal id;
    private Contexto contexto;

    /**
     * Único contructor de la clase
     *
     * @param id Else
     * @param contexto Contexto
     */
    public CondicionalElse(Terminal id, Contexto contexto) {
        setId(id);
        setContexto(contexto);
    }

    /**
     * Obtiene el id de bloque
     *
     * @return Id
     */
    public Terminal getId() {
        return id;
    }

    /**
     * Establece el id de bloque
     *
     * @param id Id
     */
    public void setId(Terminal id) {
        id.setPadre(this);
        this.id = id;
    }

    /**
     * Obtiene el contexto del bloque
     *
     * @return Contexto del bloque
     */
    public final Contexto getContexto() {
        return contexto;
    }

    /**
     * Establece el contexto del bloque
     *
     * @param contexto Contexto del bloque
     */
    public final void setContexto(Contexto contexto) {
        contexto.setPadre(this);
        this.contexto = contexto;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, contexto};
    }
}
