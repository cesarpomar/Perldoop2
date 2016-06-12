package perldoop.modelo.arbol;

import perldoop.modelo.semantico.Tipo;


/**
 * Clase que represeta un nodo abstracto del arbol de simbolos
 * @author César Pomar
 */
public abstract class Simbolo {

    protected Tipo tipo;
    protected Simbolo padre;
    protected StringBuilder codigoGenerado;
    
    /**
     * Metodo para definir acciones sobre la clase siguiendo el patron visitor
     * @param v Visitante
     */
    public abstract void aceptar(Visitante v);
    
    /**
     * Obtiene todos los nodos hijos del Simbolo
     * @return Nodos hijos
     */
    public abstract Simbolo[] getHijos();

    /**
     * Obtiene el tipo
     * @return Tipo
     */
    public final Tipo getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo
     * @param tipo Tipo
     */
    public final void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el nodo padre
     * @return Nodo padre
     */
    public final Simbolo getPadre() {
        return padre;
    }

    /**
     * Establece el nodo padre
     * @param padre Nodo padre
     */
    public final void setPadre(Simbolo padre) {
        this.padre = padre;
    }

    /**
     * Obtiene el código java generado
     * @return Código java
     */
    public final StringBuilder getCodigoGenerado() {
        return codigoGenerado;
    }

    /**
     * Establece el código java generado
     * @param codigoGenerado Código java
     */
    public final void setCodigoGenerado(StringBuilder codigoGenerado) {
        this.codigoGenerado = codigoGenerado;
    }
    
}
