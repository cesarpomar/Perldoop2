package perldoop.modelo.arbol.rexpatron;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa el analisis del contenido de una expresion regular separando los patrones de las variables
 *
 * @author César Pomar
 */
public class RexPatron extends Simbolo {

    private List<Simbolo> lista;

    /**
     * Constructor unico de la clase
     */
    public RexPatron() {
        lista = new ArrayList<>(10);
    }

    /**
     * Añade un patron
     *
     * @param s patron
     * @return Esta instancia
     */
    public RexPatron add(Simbolo s) {
        s.setPadre(this);
        lista.add(s);
        return this;
    }

    /**
     * Añade un patron a rex
     *
     * @param rex RexPatron
     * @param s patron
     * @return Esta instancia
     */
    public static RexPatron add(RexPatron rex, Simbolo s) {
        rex.add(s);
        return rex;
    }

    /**
     * Obtiene la lista de patrones
     *
     * @return Lista de patrones
     */
    public List<Simbolo> getLista() {
        return lista;
    }

    /**
     * Establece la lista de patrones
     *
     * @param lista lista de patrones
     */
    public void setLista(List<Simbolo> lista) {
        this.lista = lista;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return lista.toArray(new Simbolo[lista.size()]);
    }

}
