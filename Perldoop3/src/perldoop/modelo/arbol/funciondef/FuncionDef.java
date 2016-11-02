package perldoop.modelo.arbol.funciondef;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.contexto.Contexto;
import perldoop.modelo.arbol.funcionsub.FuncionSub;

/**
 * Clase que representa la reduccion -&gt; funcionDef : funcionSub contexto
 *
 * @author César Pomar
 */
public final class FuncionDef extends Simbolo {

    private FuncionSub funcionSub;
    private Contexto contexto;

    /**
     * Único contructor de la clase
     *
     * @param funcionSub Declaración de la función
     * @param contexto Contexto
     */
    public FuncionDef(FuncionSub funcionSub, Contexto contexto) {
        setFuncionSub(funcionSub);
        setContexto(contexto);
    }

    /**
     * Obtiene la declaración de la función
     *
     * @return Declaración de la función
     */
    public FuncionSub getFuncionSub() {
        return funcionSub;
    }

    /**
     * Establece la declaración de la función
     *
     * @param funcionSub Declaración de la función
     */
    public void setFuncionSub(FuncionSub funcionSub) {
        funcionSub.setPadre(this);
        this.funcionSub = funcionSub;
    }

    /**
     * Obtiene el contexto del bloque
     *
     * @return Contexto del bloque
     */
    public Contexto getContexto() {
        return contexto;
    }

    /**
     * Establece el contexto del bloque
     *
     * @param contexto Contexto del bloque
     */
    public void setContexto(Contexto contexto) {
        contexto.setPadre(this);
        this.contexto = contexto;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{funcionSub, contexto};
    }

}
