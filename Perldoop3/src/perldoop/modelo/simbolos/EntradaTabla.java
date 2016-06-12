package perldoop.modelo.simbolos;

import perldoop.modelo.semantico.Tipo;

/**
 *
 * @author César
 */
public class EntradaTabla {
    
    private String identificador;
    private Tipo tipo;
    private int nivel;

    public EntradaTabla(String identificador, Tipo tipo) {
        this.identificador = identificador;
        this.tipo = tipo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
    public boolean isGlobal(){
        return nivel == 0;
    }
    
}
