package perldoop.lib.box;

import perldoop.lib.Box;
import perldoop.lib.PerlFile;
import perldoop.lib.Ref;

/**
 * Contenedor de números
 *
 * @author César Pomar
 */
public final class EmptyBox implements Box {

    /**
     * Contruye un contenedor vacio
     */
    public EmptyBox() {
    }

    @Override
    public Boolean booleanValue() {
        return false;
    }

    @Override
    public Integer intValue() {
        return 0;
    }

    @Override
    public Long longValue() {
        return 0l;
    }

    @Override
    public Float floatValue() {
        return 0f;
    }

    @Override
    public Double doubleValue() {
        return 0.0;
    }

    @Override
    public Number numberValue() {
        return 0;
    }

    @Override
    public String stringValue() {
        return "";
    }

    @Override
    public Ref refValue() {
        return null;
    }

    @Override
    public PerlFile fileValue() {
        return null;
    }

}
