package perldoop.modelo.arbol.regulares;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;

/**
 * Clase abtracta que representa todas las reduciones de regulares
 *
 * @author César Pomar
 */
public abstract class Regulares extends Simbolo {

    protected Expresion expresion;
    protected Terminal operador;
    protected Terminal separadorIni;
    protected CadenaTexto patron;
    protected Terminal separadorFin;
    protected Terminal modificadores;

    /**
     * Unico contructor de la clase
     *
     * @param expresion Expresión
     * @param operador Operador
     * @param separadorIni Separador inicial
     * @param patron Patron
     * @param separadorFin Separador final
     * @param modificadores Modificadores
     */
    public Regulares(Expresion expresion, Terminal operador, Terminal separadorIni, CadenaTexto patron, Terminal separadorFin, Terminal modificadores) {
        setExpresion(expresion);
        setOperador(operador);
        setSeparadorIni(separadorIni);
        setPatron(patron);
        setSeparadorFin(separadorFin);
        setModificadores(modificadores);
    }

    /**
     * Obtiene la expresion
     *
     * @return Expresion
     */
    public final Expresion getExpresion() {
        return expresion;
    }

    /**
     * Establece la expresion
     *
     * @param expresion Expresion
     */
    public final void setExpresion(Expresion expresion) {
        expresion.setPadre(this);
        this.expresion = expresion;
    }

    /**
     * Obtiene el operador
     *
     * @return Operador
     */
    public final Terminal getOperador() {
        return operador;
    }

    /**
     * Establece el operador
     *
     * @param operador Operador
     */
    public final void setOperador(Terminal operador) {
        this.operador = operador;
    }

    /**
     * Obtiene el separador inicial
     *
     * @return Separador inicial
     */
    public final Terminal getSeparadorIni() {
        return separadorIni;
    }

    /**
     * Establece el separador inicial
     *
     * @param separadorIni
     */
    public final void setSeparadorIni(Terminal separadorIni) {
        separadorIni.setPadre(this);
        this.separadorIni = separadorIni;
    }

    /**
     * Obtiene el patron
     *
     * @return patron
     */
    public final CadenaTexto getPatron() {
        return patron;
    }

    /**
     * Establece el patron
     *
     * @param patron Patron
     */
    public final void setPatron(CadenaTexto patron) {
        patron.setPadre(this);
        this.patron = patron;
    }

    /**
     * Obtiene el separador final
     *
     * @return Separador final
     */
    public final Terminal getSeparadorFin() {
        return separadorFin;
    }

    /**
     * Establece el separador final
     *
     * @param separadorFin Separador final
     */
    public final void setSeparadorFin(Terminal separadorFin) {
        separadorFin.setPadre(this);
        this.separadorFin = separadorFin;
    }

    /**
     * Obtiene los modificadores
     *
     * @return Modificadores
     */
    public final Terminal getModificadores() {
        return modificadores;
    }

    /**
     * Establece los modificadores
     *
     * @param modificadores Modificiadores
     */
    public final void setModificadores(Terminal modificadores) {
        if (modificadores != null) {
            modificadores.setPadre(this);
            this.modificadores = modificadores;
        }
    }

}
