package perldoop.semantico;

import java.util.List;
import perldoop.error.GestorErrores;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.sentencia.Sentencia;

/**
 *
 * @author César
 */
public class AnalizadorSemantico {

    private List<Simbolo> simbolos;
    private GestorErrores gestorErrores;
    private int errores;

    public AnalizadorSemantico(List<Simbolo> simbolos, GestorErrores gestorErrores) {
        this.simbolos = simbolos;
        this.gestorErrores = gestorErrores;
    }

    public void setGestorErrores(GestorErrores gestorErrores) {
        this.gestorErrores = gestorErrores;
    }

    public int getErrores() {
        return errores;
    }

    public void analizar() {
        Semantica semantica = new Semantica();
        boolean error = false;
        for (Simbolo s : simbolos) {
            if (!error) {
                try {
                    s.aceptar(semantica);
                } catch (ExcepcionSemantica ex) {
                    error = true;
                    System.err.println("ERROR SEMANTICO");
                }
            } else if (s instanceof Sentencia) {
                error = false;
            }

        }
    }

}
