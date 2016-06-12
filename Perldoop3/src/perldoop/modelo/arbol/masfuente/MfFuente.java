package perldoop.modelo.arbol.masfuente;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.funciondef.FuncionDef;
import perldoop.modelo.arbol.fuente.Fuente;

/**
 * Clase que representa la reduccion -> masFuente : funcionDef fuente
 *
 * @author César Pomar
 */
public final class MfFuente extends MasFuente {

    private FuncionDef funcionDef;
    private Fuente fuente;

    /**
     * Único contructor de la clase
     *
     * @param funcionDef Definicion de función
     * @param fuente Fuente
     */
    public MfFuente(FuncionDef funcionDef, Fuente fuente) {
        setFuncionDef(funcionDef);
        setFuente(fuente);
    }

    /**
     * Obtiene la definición de una función
     *
     * @return Definición de una función
     */
    public FuncionDef getFuncionDef() {
        return funcionDef;
    }

    /**
     * Establece la definición de una función
     *
     * @param funcionDef Definición de una función
     */
    public void setFuncionDef(FuncionDef funcionDef) {
        funcionDef.setPadre(this);
        this.funcionDef = funcionDef;
    }

    /**
     * Obtiene la fuente
     *
     * @return Fuente
     */
    public Fuente getFuente() {
        return fuente;
    }

    /**
     * Establece la fuente
     *
     * @param fuente Fuente
     */
    public void setFuente(Fuente fuente) {
        fuente.setPadre(this);
        this.fuente = fuente;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{funcionDef, fuente};
    }

}
