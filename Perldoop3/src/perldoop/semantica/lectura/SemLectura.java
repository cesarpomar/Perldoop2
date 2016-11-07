package perldoop.semantica.lectura;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.lectura.Lectura;
import perldoop.modelo.arbol.lectura.LecturaFile;
import perldoop.modelo.arbol.lectura.LecturaIn;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de lectura
 *
 * @author César Pomar
 */
public class SemLectura {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemLectura(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Establece el tipo de la lectura
     *
     * @param s Simbolo lectura
     */
    private void setTipo(Lectura s) {
        Simbolo uso = Buscar.getUso((Expresion) s.getPadre());
        if (uso instanceof Igual && !((Igual) uso).getIzquierda().getTipo().isColeccion()) {
            s.setTipo(new Tipo(Tipo.STRING));
        } else {
            s.setTipo(new Tipo(Tipo.ARRAY, Tipo.STRING));
        }
    }

    public void visitar(LecturaIn s) {
        setTipo(s);
    }

    public void visitar(LecturaFile s) {
        setTipo(s);
        Tipos.casting(s, new Tipo(Tipo.FILE), tabla.getGestorErrores());
    }

}
