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
        return value ? 1 : 0;
    }

    @Override
    public Long longValue() {
        return value ? 1l : 0l;
    }

    @Override
    public Float floatValue() {
        return value ? 1f : 0f;
    }

    @Override
    public Double doubleValue() {
        return value ? 1d : 0d;
    }

    @Override
    public String stringValue() {
        return value ? "1" : "0";
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
