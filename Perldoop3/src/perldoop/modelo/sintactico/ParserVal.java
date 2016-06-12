package perldoop.modelo.sintactico;

import perldoop.modelo.arbol.Simbolo;


public class ParserVal {
    
    private Simbolo s;

    public ParserVal(){}
    
    public ParserVal(Simbolo s) {
        this.s = s;
    }

    public Simbolo get() {
        return s;
    }

    public void set(Simbolo s) {
        this.s = s;
    }
    
}
