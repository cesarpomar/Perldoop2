/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perldoop.excepciones;

/**
 *
 * @author César
 */
public class ExcepcionSemantica extends RuntimeException {

    /**
     * Creates a new instance of <code>ExcepcionSemantica</code> without detail
     * message.
     */
    public ExcepcionSemantica() {
    }

    /**
     * Constructs an instance of <code>ExcepcionSemantica</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionSemantica(String msg) {
        super(msg);
    }
}
