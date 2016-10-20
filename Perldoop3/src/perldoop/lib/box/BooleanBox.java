package perldoop.lib.box;

import perldoop.lib.Box;
import perldoop.lib.Casting;
import perldoop.lib.PerlFile;
import perldoop.lib.Ref;

/**
 * Contenedor de números
 *
 * @author César Pomar
 */
public final class BooleanBox implements Box {

    private final Boolean value;

    /**
     * Contruye un contenedor de numeros
     *
     * @param value Valor para almacenar
     */
    public BooleanBox(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean booleanValue() {
        return value;
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
        return Casting.toInteger(value);
    }

    @Override
    public String stringValue() {
        return Casting.toString(value);
    }

    @Override
    public Ref refValue() {
        throw new ClassCastException();
    }

    @Override
    public PerlFile fileValue() {
        throw new ClassCastException();
    }

}
