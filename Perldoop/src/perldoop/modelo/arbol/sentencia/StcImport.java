package perldoop.modelo.arbol.sentencia;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; sentencia : IMPORT_JAVA
 *
 * @author César Pomar
 */
public final class StcImport extends Sentencia {

    private Terminal importJava;

    /**
     * Único contructor de la clase
     *
     * @param declaracion Import
     */
    public StcImport(Terminal declaracion) {
        setImportJava(declaracion);
    }

    /**
     * Obtiene el import
     *
     * @return Import
     */
    public Terminal getImportJava() {
        return importJava;
    }

    /**
     * Establece el import
     *
     * @param importJava Import
     */
    public void setImportJava(Terminal importJava) {
        importJava.setPadre(this);
        this.importJava = importJava;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{importJava};
    }

}
