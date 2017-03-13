package perldoop.modelo.arbol.regulares;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt;
 * <br>regulares : expresion STR_REX S_REX REX_SEP rexPatron REX_SEP rexPatron REX_SEP rexMod
 *
 * @author César Pomar
 */
public final class RegularSubs extends Regulares {

    private Terminal id;
    private Terminal separador;
    private CadenaTexto remplazo;

    /**
     * Unico contructor de la clase
     *
     * @param expresion Expresión
     * @param operador Operador
     * @param id Id
     * @param separadorIni Separador inicial
     * @param patron Patron
     * @param separador Separador
     * @param remplazo Patron remplazo
     * @param separadorFin Separador final
     * @param modificadores Modificadores
     */
    public RegularSubs(Expresion expresion, Terminal operador, Terminal id, Terminal separadorIni, CadenaTexto patron, Terminal separador, CadenaTexto remplazo, Terminal separadorFin, Terminal modificadores) {
        super(expresion, operador, separadorIni, patron, separadorFin, modificadores);
        setId(id);
        setSeparador(separador);
        setRemplazo(remplazo);
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

    /**
     * Obtiene el separador
     *
     * @return Separador
     */
    public Terminal getSeparador() {
        return separador;
    }

    /**
     * Establece el separador
     *
     * @param separador Separador
     */
    public void setSeparador(Terminal separador) {
        separador.setPadre(this);
        this.separador = separador;
    }

    /**
     * Obtiene el patron remplazo
     *
     * @return Patron remplazo
     */
    public CadenaTexto getRemplazo() {
        return remplazo;
    }

    /**
     * Establece el patron remplazo
     *
     * @param remplazo Patron remplazo
     */
    public void setRemplazo(CadenaTexto remplazo) {
        remplazo.setPadre(this);
        this.remplazo = remplazo;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        if(modificadores==null){
            return new Simbolo[]{expresion, operador, id, separadorIni, patron, separador, remplazo, separadorFin};
        }
        return new Simbolo[]{expresion, operador, id, separadorIni, patron, separador, remplazo, separadorFin, modificadores};
    }
}
