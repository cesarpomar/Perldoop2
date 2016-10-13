package perldoop.modelo.arbol.regulares;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;
import perldoop.modelo.arbol.expresion.Expresion;

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
    public RegularNoMatch(Expresion expresion, Terminal operador, Terminal id, Terminal separadorIni, CadenaTexto patron, Terminal separadorFin, Terminal modificadores) {
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
        if (id != null) {
            id.setPadre(this);
            this.id = id;
        }
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        if (id == null) {
            return new Simbolo[]{expresion, operador, separadorIni, patron, separadorFin, modificadores};
        }
        return new Simbolo[]{expresion, operador, id, separadorIni, patron, separadorFin, modificadores};
    }
}
