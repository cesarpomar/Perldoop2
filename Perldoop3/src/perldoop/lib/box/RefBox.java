package perldoop.lib.box;

import perldoop.lib.Box;
import perldoop.lib.PerlFile;
import perldoop.lib.Ref;

/**
 * Contenedor de referencias
 *
 * @author César Pomar
 */
public class RefBox implements Box {

    private final Ref value;

    /**
     * Contruye un contenedor de nreferencias
     *
     * @param value Valor para almacenar
     */
    public RefBox(Ref value) {
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
        return value;
    }

    @Override
    public PerlFile FileValue() {
        throw new ClassCastException();
    }

}
