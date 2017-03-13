package perldoop.modelo.arbol.sentencia;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; sentencia : COMENTARIO
 *
 * @author César Pomar
 */
public final class StcComentario extends Sentencia {

    private Terminal comentario;

    /**
     * Único contructor de la clase
     *
     * @param comentario Comentario
     */
    public StcComentario(Terminal comentario) {
        this.comentario = comentario;
    }

    /**
     * Obtiene el comentario
     *
     * @return Comentario
     */
    public Terminal getComentario() {
        return comentario;
    }

    /**
     * Establece el comentario
     *
     * @param comentario Comentario
     */
    public void setComentario(Terminal comentario) {
        comentario.setPadre(this);
        this.comentario = comentario;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{comentario};
    }

}
