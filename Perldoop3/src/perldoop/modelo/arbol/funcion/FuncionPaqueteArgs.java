package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase que representa la reduccion -> <br>
 * variable : paquete ID expresion<br>
 * | ID expresion<br>
 *
 * @author César Pomar
 */
public final class FuncionPaqueteArgs extends Funcion {

    private Paquetes paquetes;
    private ColParentesis coleccion;
    private Lista lista;

    /**
     * Único contructor de la clase
     *
     * @param paquetes Paquetes
     * @param coleccion Colección
     * @param identificador Identificador
     */
    public FuncionPaqueteArgs(Paquetes paquetes, Terminal identificador, ColParentesis coleccion) {
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
    public ColParentesis getColeccion() {
        return coleccion;
    }

    /**
     * Establece la colección
     *
     * @param coleccion Colección
     */
    public void setColeccion(ColParentesis coleccion) {
        coleccion.setPadre(this);
        lista = coleccion.getLista();
        lista.setPadre(this);
        this.coleccion = coleccion;
    }

    /**
     * Obtiene la lista de argumentos
     *
     * @return Argumentos
     */
    public Lista getLista() {
        return lista;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{paquetes, identificador, lista};
    }

}
