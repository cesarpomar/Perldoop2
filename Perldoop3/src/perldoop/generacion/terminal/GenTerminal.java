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
        StringBuilder codigo = new StringBuilder(200);
        Token t = s.getToken();
        codigo.append(t.getValor());
        if (tabla.getOpciones().isComentarios() && t.getComentarios() != null) {
            codigo.append("/*");
            Iterator<Token> comentarios = t.getComentarios().iterator();
            while (comentarios.hasNext()) {
                codigo.append(comentarios.next());
                if(comentarios.hasNext()){
                    codigo.append("\n");
                }
            }
            codigo.append("*/");
        }
        s.setCodigoGenerado(codigo);
    }

}
