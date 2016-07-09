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
    public Integer intValue() {
        return value.intValue();
    }

    @Override
    public Long longValue() {
        return value.longValue();
    }

    @Override
    public Float floatValue() {
        return value.floatValue();
    }

    @Override
    public Double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public String stringValue() {
        return String.valueOf(value);
    }

    @Override
    public <T> Ref<T> RefValue() {
        throw new ClassCastException();
    }

    @Override
    public PerlFile FileValue() {
        throw new ClassCastException();
    }

}
