package perldoop.modelo.arbol.funcion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.paquete.Paquete;

/**
 * Clase que representa la reduccion -> <br>
 * variable : paquete ID expresion<br>
 * | paquete ID<br>
 * | ID expresion<br>
 * | ID<br>
 *
 * @author César Pomar
 */
public final class Funcion extends Simbolo {

    private Paquete paquete;
    private Terminal identificador;
    private Expresion expresion;

    /**
     * Único contructor de la clase
     *
     * @param paquete Paquete
     * @param identificador Identificador
     * @param expresion Expresión
     */
    public Funcion(Paquete paquete, Terminal identificador, Expresion expresion) {
        if (paquete != null) {
            setPaquete(paquete);
        }
        setIdentificador(identificador);
        if (expresion != null) {
            setExpresion(expresion);
        }
    }

    /**
     * Obtiene el paquete
     *
     * @return Paquete
     */
    public Paquete getPaquete() {
        return paquete;
    }

    /**
     * Establece el paquete
     *
     * @param paquete Paquete
     */
    public void setPaquete(Paquete paquete) {
        paquete.setPadre(this);
        this.paquete = paquete;
    }

    /**
     * Obtiene el identificador
     *
     * @return Identificador
     */
    public Terminal getIdentificador() {
        return identificador;
    }

    /**
     * Establece el identificador
     *
     * @param identificador Identificador
     */
    public void setIdentificador(Terminal identificador) {
        identificador.setPadre(this);
        this.identificador = identificador;
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

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        if (paquete == null) {
            if (expresion == null) {
                return new Simbolo[]{identificador};
            } else {
                return new Simbolo[]{identificador, expresion};
            }
        } else if (expresion == null) {
            return new Simbolo[]{paquete, identificador};
        } else {
            return new Simbolo[]{paquete, identificador, expresion};
        }
    }

}
