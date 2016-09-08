package perldoop.modelo.arbol.funciondef;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.funcionsub.FuncionSub;

/**
 * Clase que representa la reduccion -&gt; funcionDef : funcionSub '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class FuncionDef extends Simbolo {

    private FuncionSub funcionSub;
    private Terminal llaveI;
    private Cuerpo cuerpo;
    private Terminal llaveD;

    /**
     * Único contructor de la clase
     * @param funcionSub Declaración de la función
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Lave derecha
     */
    public FuncionDef(FuncionSub funcionSub, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        setFuncionSub(funcionSub);
        setLlaveI(llaveI);
        setCuerpo(cuerpo);
        setLlaveD(llaveD);
    }

    /**
     * Obtiene la declaración de la función
     * @return Declaración de la función
     */
    public FuncionSub getFuncionSub() {
        return funcionSub;
    }

    /**
     * Establece la declaración de la función
     * @param funcionSub Declaración de la función
     */
    public void setFuncionSub(FuncionSub funcionSub) {
        funcionSub.setPadre(this);
        this.funcionSub = funcionSub;
    }

    /**
     * Obtiene la llave izquierda
     * @return Llave izquierda
     */
    public Terminal getLlaveI() {
        return llaveI;
    }

    /**
     * Establece la llave izquierda
     * @param llaveI Llave izquierda
     */
    public void setLlaveI(Terminal llaveI) {
        llaveI.setPadre(this);
        this.llaveI = llaveI;
    }

    /**
     * Obtiene el cuerpo
     * @return Cuerpo
     */
    public Cuerpo getCuerpo() {
        return cuerpo;
    }

    /**
     * Establece el cuerpo
     * @param cuerpo Cuerpo
     */
    public void setCuerpo(Cuerpo cuerpo) {
        cuerpo.setPadre(this);
        this.cuerpo = cuerpo;
    }

    /**
     * Obtiene la llave derecha
     * @return Llave derecha
     */
    public Terminal getLlaveD() {
        return llaveD;
    }

    /**
     * Establece la llave derecha
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
        return new Simbolo[]{funcionSub, llaveI, cuerpo, llaveD};
    }

}
