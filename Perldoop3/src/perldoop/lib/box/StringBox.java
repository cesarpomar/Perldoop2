package perldoop.lib.box;

import perldoop.lib.Box;
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
    public Integer intValue() {
        return (int) Double.parseDouble(value);
    }

    @Override
    public Long longValue() {
        return (long) Double.parseDouble(value);
    }

    @Override
    public Float floatValue() {
        return Float.parseFloat(value);
    }

    @Override
    public Double doubleValue() {
        return Double.parseDouble(value);
    }

    @Override
    public String stringValue() {
        return value;
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
