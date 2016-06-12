package perldoop.modelo.lexico;

import java.util.List;

/**
 *
 * @author César
 */
public class Token {

    private String valor;
    private int tipo;
    private int linea;
    private int columna;
    private List<Token> comentarios;
    private List<Token> etiquetas;
    private boolean etiqueta;

    public Token() {
        valor = "";
    }

    public Token(String valor, int tipo, int linea, int columna, boolean etiqueta) {
        this.valor = valor;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.etiqueta = etiqueta;
    }

    public final String getValor() {
        return valor;
    }

    public final void setValor(String valor) {
        this.valor = valor;
    }

    public final int getTipo() {
        return tipo;
    }

    public final void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public final int getLinea() {
        return linea;
    }

    public final void setLinea(int linea) {
        this.linea = linea;
    }

    public final int getColumna() {
        return columna;
    }

    public final void setColumna(int columna) {
        this.columna = columna;
    }

    public final List<Token> getComentarios() {
        return comentarios;
    }

    public final void setComentarios(List<Token> comentarios) {
        this.comentarios = comentarios;
    }

    public final List<Token> getEtiquetas() {
        return etiquetas;
    }

    public final void setEtiquetas(List<Token> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public final boolean isEtiqueta() {
        return etiqueta;
    }

    public final void setEtiqueta(boolean etiqueta) {
        this.etiqueta = etiqueta;
    }

}
