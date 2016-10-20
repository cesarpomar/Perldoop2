package perldoop.modelo.arbol.handle;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; handle : STDOUT
 *
 * @author César Pomar
 */
public final class HandleFile extends Handle {

    protected Terminal file;

    /**
     * Constructor unico de la clase
     *
     * @param std Salida
     * @param file
     */
    public HandleFile(Terminal std, Terminal file) {
        super(std);
    }

    /**
     * Obtiene le fichero
     *
     * @return Fichero
     */
    public Terminal getFile() {
        return file;
    }

    /**
     * Establece el fichero
     *
     * @param file Fichero
     */
    public void setFile(Terminal file) {
        file.setPadre(this);
        this.file = file;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{std, file};
    }

}
