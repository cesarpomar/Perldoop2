package perldoop.generacion.terminal;

import java.util.Iterator;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.lexico.Token;

/**
 * Clase generadora de terminales
 *
 * @author César Pomar
 */
public class GenTerminal {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenTerminal(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Terminal s) {
        StringBuilder comentario = new StringBuilder(200);
        Token t = s.getToken();
        if (tabla.getOpciones().isComentarios() && s.getTokensComentario() != null) {
            comentario.append("/*");
            Iterator<Token> comentarios = s.getTokensComentario().iterator();
            while (comentarios.hasNext()) {
                comentario.append(comentarios.next());
                if (comentarios.hasNext()) {
                    comentario.append("\n");
                }
            }
            comentario.append("*/");
        }
        s.setComentario(comentario.toString());
        s.setCodigoGenerado(new StringBuilder(s.getValor()).append(comentario));
    }

}
