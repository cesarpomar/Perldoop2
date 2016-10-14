package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase que representa la reduccion -&gt; funcion : paqueteID ID expresion
 *
 * @author César Pomar
 */
public final class FuncionPaqueteArgs extends Funcion {

    private Paquetes paquetes;
    private ExpColeccion coleccion;

    /**
     * Único contructor de la clase
     *
     * @param paquetes Paquetes
     * @param coleccion Colección
     * @param identificador Identificador
     */
    public FuncionPaqueteArgs(Paquetes paquetes, Terminal identificador, ExpColeccion coleccion) {
        super(identificador);
        setPaquetes(paquetes);
        setColeccion(coleccion);
    }

    /**
     * Obtiene los paquetes
     *
     * @return Paquetes
     */
    public Paquetes getPaquetes() {
        return paquetes;
    }

    /**
     * Establece los paquetes
     *
     * @param paquetes Paquetes
     */
    public void setPaquetes(Paquetes paquetes) {
        paquetes.setPadre(this);
        this.paquetes = paquetes;
    }

    /**
     * Obtiene la colección
     *
     * @return Colección
     */
    public ExpColeccion getColeccion() {
        return coleccion;
    }

    /**
     * Establece la colección
     *
     * @param coleccion Colección
     */
    public void setColeccion(ExpColeccion coleccion) {
        coleccion.setPadre(this);
        this.coleccion = coleccion;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{paquetes, identificador, coleccion};
    }

}
