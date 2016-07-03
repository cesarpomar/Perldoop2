package perldoop.modelo.arbol.funcion;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase auxiliar para almacenar los argumentos de una función
 * @author César Pomar
 */
public final class Argumentos extends Simbolo{

    private List<Expresion> argumentos;

    /**
     * Único contructor de la clase
     * @param expresion Primer argumento
     */
    public Argumentos(Expresion expresion) {
        argumentos = new ArrayList<>(8);
        addArgumentos(expresion);
    }
        
        /**
     * Obtiene los argumentos
     *
     * @return Argumentos
     */
    public List<Expresion> getArgumentos() {
        return argumentos;
    }

    /**
     * Establece los argumentos
     *
     * @param argumentos Argumentos
     */
    public void setArgumentos(List<Expresion> argumentos) {
        this.argumentos = argumentos;
    }

    /**
     * Establece los argumentos
     *
     * @param expresion Expresión
     */
    public void addArgumentos(Expresion expresion) {
        argumentos.add(expresion);
        expresion.setPadre(this);
    }
    
    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return argumentos.toArray(new Simbolo[argumentos.size()]);
    }
    
}
