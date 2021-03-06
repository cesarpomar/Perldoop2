package perldoop.modelo.semantica;

/**
 * Clase para almacenar los tres tipos contexto que puede tener un identificador.
 *
 * @author César Pomar
 */
public final class ContextoVariable {

    private EntradaVariable escalar;
    private EntradaVariable array;
    private EntradaVariable hash;

    /**
     * Constructor del contexto
     */
    public ContextoVariable() {
    }

    /**
     * Obtiene el contexto escalar
     *
     * @return Escalar
     */
    public EntradaVariable getEscalar() {
        return escalar;
    }

    /**
     * Establece el contexto escalar
     *
     * @param escalar Escalar
     */
    public void setEscalar(EntradaVariable escalar) {
        this.escalar = escalar;
    }

    /**
     * Obtiene el contexto array
     *
     * @return Array
     */
    public EntradaVariable getArray() {
        return array;
    }

    /**
     * Establece el contexto array
     *
     * @param array Array
     */
    public void setArray(EntradaVariable array) {
        this.array = array;
    }

    /**
     * Obtiene el contexto Hash
     *
     * @return hash
     */
    public EntradaVariable getHash() {
        return hash;
    }

    /**
     * Establece el contexto hash
     *
     * @param hash Hash
     */
    public void setHash(EntradaVariable hash) {
        this.hash = hash;
    }

    /**
     * Establece la variable en su contexto
     *
     * @param var Variable
     * @return Variable sobreescrita
     */
    public EntradaVariable setVar(EntradaVariable var) {
        Tipo t = var.getTipo();
        EntradaVariable old;
        if (t.isArrayOrList()) {
            old = array;
            array = var;
        } else if (t.isMap()) {
            old = hash;
            hash = var;
        } else {
            old = escalar;
            escalar = var;
        }
        return old;
    }

    /**
     * Obtiene la variable de un contexto
     *
     * @param contexto Contexto
     * @return Entrada variable
     */
    public EntradaVariable getVar(char contexto) {
        switch (contexto) {
            case '$':
                return escalar;
            case '@':
                return array;
            case '%':
                return hash;
        }
        return null;
    }

    /**
     * Obtiene el número de contextos de la variable
     *
     * @return Número de contextos
     */
    public int size() {
        int size = 0;
        if (escalar != null) {
            size++;
        }
        if (array != null) {
            size++;
        }
        if (hash != null) {
            size++;
        }
        return size;
    }

}
