package perldoop.lib.box;

import perldoop.lib.Box;
import perldoop.lib.PerlFile;
import perldoop.lib.Ref;

/**
 * Contenedor de ficheros
 *
 * @author César Pomar
 */
public class FileBox implements Box {

    private final PerlFile value;

    /**
     * Contruye un contenedor de nreferencias
     *
     * @param value Valor para almacenar
     */
    public FileBox(PerlFile value) {
        this.value = value;
    }

    @Override
    public Boolean booleanValue() {
        return (value != null);
    }

    @Override
    public Integer intValue() {
        return (value != null) ? 1 : 0;
    }

    @Override
    public Long longValue() {
        return (value != null) ? 1l : 0l;
    }

    @Override
    public Float floatValue() {
        return (value != null) ? 1f : 0f;
    }

    @Override
    public Double doubleValue() {
        return (value != null) ? 1d : 0d;
    }

    @Override
    public Number numberValue() {
        return (value != null) ? 1 : 0;
    }

    @Override
    public String stringValue() {
        return (value != null) ? "1" : "0";
    }

    @Override
    public Ref refValue() {
        throw new ClassCastException();
    }

    @Override
    public PerlFile fileValue() {
        return value;
    }

    @Override
    public String toString() {
        return stringValue();
    }

}
