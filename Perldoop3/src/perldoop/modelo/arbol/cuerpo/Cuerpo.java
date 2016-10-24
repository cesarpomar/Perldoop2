package perldoop.modelo.arbol.cuerpo;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.sentencia.Sentencia;

/**
 * Clase que representa las reducciones -&gt; <br>
 * cuerpo :<br>
 * | cuerpo sentencia<br>
 *
 * @author César Pomar
 */
public final class Cuerpo extends Simbolo {

    private AbrirBloque abrirBloque;
    private List<Sentencia> sentencias;

    /**
     * Construye un cuerpo vacio
     *
     * @param abrirBloque Abertura de bloque para el cuerpo
     */
    public Cuerpo(AbrirBloque abrirBloque) {
        setAbrirBloque(abrirBloque);
        sentencias = new ArrayList<>(100);
    }

    /**
     * Construye un cuerpo con una sentencia
     *
     * @param abrirBloque Abertura de bloque para el cuerpo
     * @param sentencia Sentecia
     */
    public Cuerpo(AbrirBloque abrirBloque, Sentencia sentencia) {
        this(abrirBloque);
        add(sentencia);
    }

    /**
     * Obtiene la abertura de bloque
     *
     * @return Abertura de bloque
     */
    public AbrirBloque getAbrirBloque() {
        return abrirBloque;
    }

    /**
     * Establece la abertura de bloque
     *
     * @param abrirBloque Abertura de bloque
     */
    public void setAbrirBloque(AbrirBloque abrirBloque) {
        abrirBloque.setPadre(this);
        this.abrirBloque = abrirBloque;
    }

    /**
     * Obtiene las sentencias
     *
     * @return Lista de sentencias
     */
    public List<Sentencia> getSentencias() {
        return sentencias;
    }

    /**
     * Añade una sentencia
     *
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
     *
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
        Simbolo[] hijos = new Simbolo[sentencias.size() + 1];
        hijos[0] = abrirBloque;
        for (int i = 1; i <= sentencias.size(); i++) {
            hijos[i] = sentencias.get(i - 1);
        }
        return hijos;
    }
}
