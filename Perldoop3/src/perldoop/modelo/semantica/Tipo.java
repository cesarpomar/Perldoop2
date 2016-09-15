package perldoop.modelo.semantica;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa los tipos de datos
 *
 * @author César Pomar
 */
public final class Tipo {

    public static final byte BOOLEAN = 1;
    public static final byte INTEGER = 2;
    public static final byte LONG = 3;
    public static final byte FLOAT = 4;
    public static final byte DOUBLE = 5;
    public static final byte STRING = 6;
    public static final byte FILE = 7;
    public static final byte BOX = 8;
    public static final byte NUMBER = 9;

    public static final byte ARRAY = 100;
    public static final byte LIST = 101;
    public static final byte MAP = 102;
    public static final byte REF = 103;

    private List<Byte> tipo;
    private boolean variable;
    private boolean constante;

    /**
     * Construye el tipo
     *
     * @param valores Subtipo
     */
    public Tipo(byte... valores) {
        if (valores.length == 0) {
            tipo = new ArrayList<>(2);
        } else {
            tipo = new ArrayList<>(valores.length);
            for (byte valor : valores) {
                add(valor);
            }
        }
    }

    /**
     * Construye el tipo
     *
     * @param list Lista de subtipos
     */
    public Tipo(List<Byte> list) {
        tipo = new ArrayList<>(list);
    }

    /**
     * Contruye un tipo basado en otro
     *
     * @param t Tipo
     */
    public Tipo(Tipo t) {
        tipo = new ArrayList<>(t.tipo);
    }

    /**
     * Añade un subtipo
     *
     * @param valor Subtipo
     * @return Este tipo
     */
    public Tipo add(byte valor) {
        tipo.add(valor);
        return this;
    }

    /**
     * Añade un subtipo
     *
     * @param pos Posición
     * @param valor Subtipo
     * @return Este tipo
     */
    public Tipo add(int pos, byte valor) {
        tipo.add(pos, valor);
        return this;
    }

    /**
     * Comprueba si el tipo pertenece a una variable
     *
     * @return Tipo pertenece a una variable
     */
    public boolean isVariable() {
        return variable;
    }

    /**
     * Establece si el tipo pertenece a una variable
     *
     * @param variable Tipo pertenece a una variable
     */
    public void setVariable(boolean variable) {
        this.variable = variable;
    }

    /**
     * Crea un subtipo del actual partiendo del subtipo init
     *
     * @param init Inicio del subtipo
     * @return Subtipo
     */
    public Tipo getSubtipo(int init) {
        return new Tipo(tipo.subList(init, tipo.size()));
    }

    /**
     * Obtiene si el tipo pertenece a una consantante
     *
     * @return Tipo pertenece a una consantante
     */
    public boolean isConstante() {
        return constante;
    }

    /**
     * Establece si el tipo pertenece a una consantante
     *
     * @param constante Tipo pertenece a una consantante
     */
    public void setConstante(boolean constante) {
        this.constante = constante;
    }

    /**
     * Obtiene los subtipos
     *
     * @return Lista de subtipos
     */
    public List<Byte> getTipo() {
        return tipo;
    }

    /**
     * Obtiene si el tipo es Array
     *
     * @return Tipo es Array
     */
    public boolean isArray() {
        return !tipo.isEmpty() && tipo.get(0) == ARRAY;
    }

    /**
     * Obtiene si el tipo es Array
     *
     * @return Tipo es Array
     */
    public boolean isList() {
        return !tipo.isEmpty() && tipo.get(0) == LIST;
    }

    /**
     * Obtiene si el tipo es List
     *
     * @return Tipo es List
     */
    public boolean isArrayOrList() {
        return !tipo.isEmpty() && (tipo.get(0) == ARRAY || tipo.get(0) == LIST);
    }

    /**
     * Obtiene si el tipo es Map
     *
     * @return Tipo es Map
     */
    public boolean isMap() {
        return !tipo.isEmpty() && tipo.get(0) == MAP;
    }

    /**
     * Obtiene si el tipo es una colección Array, List o Map
     *
     * @return Tipo es colección
     */
    public boolean isColeccion() {
        return !tipo.isEmpty() && (tipo.get(0) == ARRAY || tipo.get(0) == LIST || tipo.get(0) == MAP);
    }

    /**
     * Obtiene si el tipo es Ref
     *
     * @return Tipo es Ref
     */
    public boolean isRef() {
        return !tipo.isEmpty() && tipo.get(0) == REF;
    }

    /**
     * Obtiene si el tipo es Boolean
     *
     * @return Tipo es Boolean
     */
    public boolean isBoolean() {
        return !tipo.isEmpty() && tipo.get(0) == BOOLEAN;
    }

    /**
     * Obtiene si el tipo es Integer
     *
     * @return Tipo es Integer
     */
    public boolean isInteger() {
        return !tipo.isEmpty() && tipo.get(0) == INTEGER;
    }

    /**
     * Obtiene si el tipo es Long
     *
     * @return Tipo es Long
     */
    public boolean isLong() {
        return !tipo.isEmpty() && tipo.get(0) == LONG;
    }

    /**
     * Obtiene si el tipo es Float
     *
     * @return Tipo es Float
     */
    public boolean isFloat() {
        return !tipo.isEmpty() && tipo.get(0) == FLOAT;
    }

    /**
     * Obtiene si el tipo es Double
     *
     * @return Tipo es Double
     */
    public boolean isDouble() {
        return !tipo.isEmpty() && tipo.get(0) == DOUBLE;
    }

    /**
     * Obtiene si el tipo es String
     *
     * @return Tipo es String
     */
    public boolean isString() {
        return !tipo.isEmpty() && tipo.get(0) == STRING;
    }

    /**
     * Obtiene si el tipo es File
     *
     * @return Tipo es File
     */
    public boolean isFile() {
        return !tipo.isEmpty() && tipo.get(0) == FILE;
    }

    /**
     * Obtiene si el tipo es Simple, se considera simple los siguientes tipos: Boolean, Integer, Long, Float, Double,
     * String.
     *
     * @return Tipo es Simple
     */
    public boolean isSimple() {
        return !tipo.isEmpty() && tipo.get(0) >= BOOLEAN && tipo.get(0) <= STRING;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tipo) {
            return ((Tipo) obj).getTipo().equals(this.getTipo());
        }
        return false;
    }

}
