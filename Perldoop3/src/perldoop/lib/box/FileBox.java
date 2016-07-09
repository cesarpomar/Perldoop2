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
    public Integer intValue() {
        return 1;
    }

    @Override
    public Long longValue() {
        return 1l;
    }

    @Override
    public Float floatValue() {
        return 1f;
    }

    @Override
    public Double doubleValue() {
        return 1d;
    }

    @Override
    public String stringValue() {
        return "0x1";
    }

    @Override
    public <T> Ref<T> RefValue() {
        throw new ClassCastException();
    }

    @Override
    public PerlFile FileValue() {
        return value;
    }

}
