package perldoop.modelo.arbol.paquete;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> paquete : ID CONTEXTO
 *
 * @author César Pomar
 */
public final class Paquete extends Simbolo {

    private Terminal identificador;
    private Terminal contexto;

    public Paquete(Terminal identificador, Terminal contexto) {
        setIdentificador(identificador);
        setContexto(contexto);
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
     * Obtiene el contexto
     *
     * @return Contexto
     */
    public Terminal getContexto() {
        return contexto;
    }

    /**
     * Establece el contexto
     *
     * @param contexto Contexto
     */
    public void setContexto(Terminal contexto) {
        this.contexto = contexto;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{identificador, contexto};
    }

}
