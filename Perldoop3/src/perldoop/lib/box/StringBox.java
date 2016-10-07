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
    public Boolean booleanValue() {
        return !(value == null || value.isEmpty() || value.equals("0"));
    }

    @Override
    public Integer intValue() {
        return value == null ? null : (int) Double.parseDouble(value);
    }

    @Override
    public Long longValue() {
        return value == null ? null : (long) Double.parseDouble(value);
    }

    @Override
    public Float floatValue() {
        return value == null ? null : Float.parseFloat(value);
    }

    @Override
    public Double doubleValue() {
        return value == null ? null : Double.parseDouble(value);
    }

    @Override
    public Number numberValue() {
        return value == null ? null : Double.parseDouble(value);
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

}
