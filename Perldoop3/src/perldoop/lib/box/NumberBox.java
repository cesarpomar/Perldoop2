package perldoop.lib.box;

import perldoop.lib.Box;
import perldoop.lib.PerlFile;
import perldoop.lib.Ref;

/**
 * Contenedor de números
 *
 * @author César Pomar
 */
public final class NumberBox implements Box {

    private final Number value;

    /**
     * Contruye un contenedor de numeros
     *
     * @param value Valor para almacenar
     */
    public NumberBox(Number value) {
        this.value = value;
    }

    @Override
    public Boolean booleanValue() {
        return value == null ? null : value.floatValue() != 0;
    }

    @Override
    public Integer intValue() {
        return value == null ? null : value.intValue();
    }

    @Override
    public Long longValue() {
        return value == null ? null : value.longValue();
    }

    @Override
    public Float floatValue() {
        return value == null ? null : value.floatValue();
    }

    @Override
    public Double doubleValue() {
        return value == null ? null : value.doubleValue();
    }

    @Override
    public Number numberValue() {
        return value;
    }

    @Override
    public String stringValue() {
        return value == null ? null : String.valueOf(value);
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
