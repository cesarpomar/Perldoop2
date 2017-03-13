package perldoop.modelo.arbol.sentencia;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; sentencia : LINEA_JAVA
 *
 * @author César Pomar
 */
public final class StcLineaJava extends Sentencia {

    private Terminal lineaJava;

    /**
     * Único contructor de la clase
     *
     * @param declaracion Linea java
     */
    public StcLineaJava(Terminal declaracion) {
        setLineaJava(declaracion);
    }

    /**
     * Obtiene la linea java
     *
     * @return Linea java
     */
    public Terminal getLineaJava() {
        return lineaJava;
    }

    /**
     * Establece la linea java
     *
     * @param lineaJava Linea java
     */
    public void setLineaJava(Terminal lineaJava) {
        lineaJava.setPadre(this);
        this.lineaJava = lineaJava;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{lineaJava};
    }

}
