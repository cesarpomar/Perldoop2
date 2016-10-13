package perldoop.modelo.arbol.cadenatexto;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa el texto y las variables dentro de una expresion interpolada
 *
 * @author César Pomar
 */
public final class CadenaTexto extends Simbolo {

    private List<Simbolo> elementos;

    /**
     * Constructor unico de la clase
     */
    public CadenaTexto() {
        elementos = new ArrayList<>(100);
    }

    /**
     * Obtiene los elementos
     *
     * @return lista de elementos
     */
    public List<Simbolo> getElementos() {
        return elementos;
    }

    /**
     * Establece los elementos
     *
     * @param elementos Elementos
     */
    public void setElementos(List<Simbolo> elementos) {
        this.elementos = elementos;
    }

    /**
     * Añade un elemento a la cadena
     *
     * @param elem Elemento
     * @return Esta instancia
     */
    public CadenaTexto add(Simbolo elem) {
        elem.setPadre(this);
        elementos.add(elem);
        return this;
    }

    /**
     * Añade un elemento a la cadena pasada como argumento
     *
     * @param cadena Cadena
     * @param elem Elemento
     * @return Cadena
     */
    public static CadenaTexto add(CadenaTexto cadena, Simbolo elem) {
        return cadena.add(elem);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return elementos.toArray(new Simbolo[elementos.size()]);
    }

}
