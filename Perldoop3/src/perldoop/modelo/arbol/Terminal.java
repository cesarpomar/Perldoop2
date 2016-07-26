package perldoop.modelo.arbol;

import perldoop.modelo.lexico.Token;

/**
 * Clase que representa un nodo hoja del arbol de simbolos, esta clase contiene
 * un token del analizador lexico.
 *
 * @author César Pomar
 */
public final class Terminal extends Simbolo {

    private Token token;

    /**
     * Único contructor de la clase
     * @param token Token
     */
    public Terminal(Token token) {
        setToken(token);
    }

    /**
     * Obtiene el token
     * @return Token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Establece el token
     * @param token Token
     */
    public void setToken(Token token) {
        this.token = token;
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
