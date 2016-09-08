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
     * @param tabla Tabla
     */
    public GenTerminal(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Terminal s) {
        if (tabla.getOpciones().isComentarios() && s.getTokensComentario() != null) {
            StringBuilder comentario = new StringBuilder(200);
            if (s.getTokensComentario().get(0).getLinea() != s.getToken().getLinea()) {
                comentario.append("\n");
            }
            comentario.append("/*");
            Iterator<Token> comentarios = s.getTokensComentario().iterator();
            while (comentarios.hasNext()) {
                comentario.append(comentarios.next());
                if (comentarios.hasNext()) {
                    comentario.append("\n");
                }
            }
            comentario.append("*/\n");
            s.setComentario(comentario.toString());
        } else {
            s.setComentario("");
        }     
        s.setCodigoGenerado(new StringBuilder(s.getValor()));
    }

}
