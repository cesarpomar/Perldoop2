package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.paquete.Paquetes;

/**
 * Clase que representa la reduccion -&gt; funcion : Paquete ID '{' expresion '}' expresion
 *
 * @author César Pomar
 */
public final class FuncionBloque extends Funcion {

    protected Terminal llaveI;
    protected Expresion expresion;
    protected Terminal llaveD;

    /**
     * Único contructor de la clase
     *
     * @param paquetes Paquetes
     * @param identificador Identificador
     * @param llaveI Llave izquierda
     * @param expresion Expresion
     * @param llaveD Llave derecha
     * @param coleccion Colección
     */
    public FuncionBloque(Paquetes paquetes, Terminal identificador, Terminal llaveI, Expresion expresion, Terminal llaveD, ColParentesis coleccion) {
        super(paquetes, identificador, coleccion);
        setLlaveI(llaveI);
        setExpresion(expresion);
        setLlaveD(llaveD);
    }

    /**
     * Obtiene la llave izquierda
     *
     * @return Llave izquierda
     */
    public Terminal getLlaveI() {
        return llaveI;
    }

    /**
     * Establece la llave izquierda
     *
     * @param llaveI Llave izquierda
     */
    public void setLlaveI(Terminal llaveI) {
        llaveI.setPadre(this);
        this.llaveI = llaveI;
    }

    /**
     * Obtiene la expresión
     *
     * @return Expresión
     */
    public Expresion getExpresion() {
        return expresion;
    }

    /**
     * Establece la expresión
     *
     * @param expresion Expresión
     */
    public void setExpresion(Expresion expresion) {
        expresion.setPadre(this);
        this.expresion = expresion;
    }

    /**
     * Obtiene la llave derecha
     *
     * @return Llave derecha
     */
    public Terminal getLlaveD() {
        return llaveD;
    }

    /**
     * Establece la llave derecha
     *
     * @param llaveD Llave derecha
     */
    public void setLlaveD(Terminal llaveD) {
        llaveD.setPadre(this);
        this.llaveD = llaveD;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{paquetes, identificador, llaveI, expresion, llaveD, coleccion};
    }

}
