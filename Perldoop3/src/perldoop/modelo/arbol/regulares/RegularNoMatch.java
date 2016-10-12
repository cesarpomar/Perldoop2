package perldoop.modelo.arbol.regulares;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.rexpatron.RexPatron;

/**
 * Clase que representa la reduccion -&gt;
 * <br>regulares : expresion STR_NO_REX M_REX REX_SEP rexPatron REX_SEP rexMod
 * <br>| expresion STR_NO_REX REX_SEP rexPatron REX_SEP rexMod
 *
 * @author César Pomar
 */
public final class RegularNoMatch extends Regulares {

    private Terminal id;

    /**
     * Unico contructor de la clase
     *
     * @param expresion Expresión
     * @param operador Operador
     * @param id Id
     * @param separadorIni Separador inicial
     * @param patron Patron
     * @param separadorFin Separador final
     * @param modificadores Modificadores
     */
    public RegularNoMatch(Expresion expresion, Terminal operador, Terminal id, Terminal separadorIni, RexPatron patron, Terminal separadorFin, Terminal modificadores) {
        super(expresion, operador, separadorIni, patron, separadorFin, modificadores);
        setId(id);
    }

    /**
     * Obtiene el id
     *
     * @return Id
     */
    public Terminal getId() {
        return id;
    }

    /**
     * Establece el id
     *
     * @param id Id
     */
    public void setId(Terminal id) {
        id.setPadre(this);
        this.id = id;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{expresion, operador, id, separadorIni, patron, separadorFin, modificadores};
    }
}
