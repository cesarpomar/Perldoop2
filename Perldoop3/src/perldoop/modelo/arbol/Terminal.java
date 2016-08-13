package perldoop.modelo.arbol;

import java.util.List;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.Etiquetas;

/**
 * Clase que representa un nodo hoja del arbol de simbolos, esta clase contiene un token del analizador lexico.
 *
 * @author César Pomar
 */
public final class Terminal extends Simbolo {

    private String comentario;
    private Etiquetas etiquetas;
    private Token token;
    private List<Token> tokensComentario;

    /**
     * Contruye un Terminal con un token
     *
     * @param token Token
     */
    public Terminal(Token token) {
        setToken(token);
    }

    /**
     * Contruye un terminal vacio
     */
    public Terminal() {
    }

    /**
     * Obtiene el token
     *
     * @return Token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Establece el token
     *
     * @param token Token
     */
    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * Obtiene el valor
     *
     * @return Valor
     */
    public final String getValor() {
        return token.getValor();
    }

    /**
     * Obtiene el comentario
     *
     * @return Comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Establece el comentario
     *
     * @param comentario Comentario
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Obtiene las etiquetas asignadas al terminal
     *
     * @return Etiquetas
     */
    public Etiquetas getEtiquetas() {
        return etiquetas;
    }

    /**
     * Establece las etiquetas asignadas al terminal
     *
     * @param etiquetas Etiquetas
     */
    public void setEtiquetas(Etiquetas etiquetas) {
        this.etiquetas = etiquetas;
    }

    /**
     * Establece los tokens de los comentarios
     *
     * @return Tokens de los comentarios
     */
    public List<Token> getTokensComentario() {
        return tokensComentario;
    }

    /**
     * Obtiene los tokens de los comentarios
     *
     * @param tokensComentario Tokens de los comentarios
     */
    public void setTokensComentario(List<Token> tokensComentario) {
        this.tokensComentario = tokensComentario;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{};
    }

}
