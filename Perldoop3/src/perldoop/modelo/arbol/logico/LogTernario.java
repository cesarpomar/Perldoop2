package perldoop.modelo.arbol.logico;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -> logico : expresion '?' expresion ':'
 * expresion
 *
 * @author César Pomar
 */
public final class LogTernario extends Logico {
    
    private Expresion condicion;
    private Expresion cierta;
    private Terminal dosPuntos;
    private Expresion falsa;
    
    /**
     *  Único contructor de la clase
     * @param condicion Condición
     * @param operador Operador
     * @param cierta Expresión cierta
     * @param dosPuntos Dospuntos ':'
     * @param falsa Expresión falsa
     */
    public LogTernario(Expresion condicion, Terminal operador, Expresion cierta, Terminal dosPuntos, Expresion falsa) {
        super(operador);
        setCondicion(condicion);
        setCierta(cierta);
        setDosPuntos(dosPuntos);
        setFalsa(falsa);
    }
    
    /**
     * Obtiene la condición
     * @return Condición
     */
    public Expresion getCondicion() {
        return condicion;
    }
    
    /**
     * Establece la condición
     * @param condicion Condición
     */
    public void setCondicion(Expresion condicion) {
        condicion.setPadre(this);
        this.condicion = condicion;
    }
    
    /**
     * obtiene la expresión cierta
     * @return Expresión cierta
     */
    public Expresion getCierta() {
        return cierta;
    }
    
    /**
     * Establece la expresión cierta
     * @param cierta Expresión cierta
     */
    public void setCierta(Expresion cierta) {
        cierta.setPadre(this);
        this.cierta = cierta;
    }
    
    /**
     * Obtiene la expresión cierta
     * @return Expresión cierta
     */
    public Terminal getDosPuntos() {
        return dosPuntos;
    }
    
    /**
     * Obtiene los dos puntos ':'
     * @param dosPuntos Dos puntos ':'
     */
    public void setDosPuntos(Terminal dosPuntos) {
        dosPuntos.setPadre(this);
        this.dosPuntos = dosPuntos;
    }
    
    /**
     * Obtiene la expresión falsa
     * @return Expresión falsa
     */
    public Expresion getFalsa() {
        return falsa;
    }
    
    /**
     * Establece la expresión falsa
     * @param falsa Expresión falsa
     */
    public void setFalsa(Expresion falsa) {
        falsa.setPadre(this);
        this.falsa = falsa;
    }
    
    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }
    
    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{condicion, operador, cierta, dosPuntos, falsa};
    }
    
}
