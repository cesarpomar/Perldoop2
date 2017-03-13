package perldoop.lib.box;

import perldoop.lib.Box;
import perldoop.lib.Casting;
import perldoop.lib.PerlFile;
import perldoop.lib.Ref;

/**
 * Contenedor de cadenas
 *
 * @author César Pomar
 */
public final class StringBox implements Box {

    private final String value;

    /**
     * Contruye un contenedor de ncadenas
     *
     * @param value Valor para almacenar
     */
    public StringBox(String value) {
        this.value = value;
    }

    @Override
    public Boolean booleanValue() {
        return Casting.toBoolean(value);
    }

    @Override
    public Integer intValue() {
        return Casting.toInteger(value);
    }

    @Override
    public Long longValue() {
        return Casting.toLong(value);
    }

    @Override
    public Float floatValue() {
        return Casting.toFloat(value);
    }

    @Override
    public Double doubleValue() {
        return Casting.toDouble(value);
    }

    @Override
    public Number numberValue() {
        return Casting.toDouble(value);
    }

    @Override
    public String stringValue() {
        return value;
    }

    @Override
    public Ref refValue() {
        throw new ClassCastException();
    }

    @Override
    public PerlFile fileValue() {
        throw new ClassCastException();
    }

    @Override
    public String toString() {
        return stringValue();
    }

}
