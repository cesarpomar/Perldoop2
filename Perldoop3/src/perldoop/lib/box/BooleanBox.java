package perldoop.lib.box;

import perldoop.lib.Box;
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
        return value == null ? null : value ? 1 : 0;
    }

    @Override
    public Long longValue() {
        return value == null ? null : value ? 1l : 0l;
    }

    @Override
    public Float floatValue() {
        return value == null ? null : value ? 1f : 0f;
    }

    @Override
    public Double doubleValue() {
        return value == null ? null : value ? 1d : 0d;
    }

    @Override
    public Number numberValue() {
        return value == null ? null : value ? 1 : 0;
    }

    @Override
    public String stringValue() {
        return value == null ? null : value ? "1" : "0";
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
