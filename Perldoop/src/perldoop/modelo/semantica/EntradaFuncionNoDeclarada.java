package perldoop.modelo.semantica;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.lexico.Token;

/**
 * Invocaciones a una funcion no declarada
 *
 * @author César Pomar
 */
public final class EntradaFuncionNoDeclarada {

    private String identificador;
    private String alias;
    private List<Token> llamadas;

    /**
     * Contruye una entrada de la tabla pra una función
     *
     * @param identificador Identificador
     */
    public EntradaFuncionNoDeclarada(String identificador) {
        this.identificador = identificador;
        llamadas = new ArrayList<>(10);
    }

    /**
     * Obtiene el identificador
     *
     * @return Identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Establece el identificador
     *
     * @param identificador Identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene el alias de la variable
     *
     * @return Alias de la variable
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Establece el alias de la variable
     *
     * @param alias Alias de la variable
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Obtiene las llamdas a la funcion
     *
     * @return Lista de llamadas
     */
    public List<Token> getLlamadas() {
        return llamadas;
    }

    /**
     * Establece las llamadas
     *
     * @param llamadas Lista de llamadas
     */
    public void setLlamadas(List<Token> llamadas) {
        this.llamadas = llamadas;
    }

}
