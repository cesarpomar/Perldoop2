package perldoop.modelo.arbol.expresion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.logico.Logico;

/**
 * Clase que representa la reduccion -&gt; expresion : logico
 *
 * @author César Pomar
 */
public final class ExpLogico extends Expresion {

    private Logico logico;

    /**
     * Único contructor de la clase
     *
     * @param logico Lógico
     */
    public ExpLogico(Logico logico) {
        setLogico(logico);
    }

    /**
     * Obtiene el lógico
     *
     * @return Lógico
     */
    public Logico getLogico() {
        return logico;
    }

    /**
     * Establece el lógico
     *
     * @param logico Lógico
     */
    public void setLogico(Logico logico) {
        logico.setPadre(this);
        this.logico = logico;
    }

    @Override
    public Simbolo getValor() {
        return logico;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{logico};
    }

}
