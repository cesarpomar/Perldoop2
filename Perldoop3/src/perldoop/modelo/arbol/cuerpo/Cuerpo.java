package perldoop.modelo.arbol.cuerpo;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.sentencia.Sentencia;


/**
 * Clase que representa las reducciones -> <br>
 * cuerpo :<br>
          | cuerpo sentencia<br>							
 *
 * @author César Pomar
 */
public final class Cuerpo extends Simbolo {
    private List<Sentencia> sentencias;

    /**
     * Único contructor de la clase
     */
    public Cuerpo() {
        sentencias = new ArrayList<>(100);
    }

    /**
     * Obtiene las sentencias
     * @return Lista de sentencias
     */
    public List<Sentencia> getSentencias() {
        return sentencias;
    }

    /**
     * Añade una sentencia
     * @param sentencia Sentecia
     * @return Esta instancia
     */
    public Cuerpo add(Sentencia sentencia) {
        sentencias.add(sentencia);
        sentencia.setPadre(this);
        return this;
    }

    /**
     * Realiza la misma acción que invocar c.add(sentencia)
     * @param c Cuerpo
     * @param sentencia Sencia
     * @return c
     */
    public static Cuerpo add(Cuerpo c, Sentencia sentencia) {
        return c.add(sentencia);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return sentencias.toArray(new Simbolo[sentencias.size()]);
    }
}
