package perldoop.semantico.asignacion;

import perldoop.modelo.arbol.asignacion.Igual;

/**
 *
 * @author César
 */
public class SemAsignacion {
    
    private SemIgual igual;

    public SemIgual getIgual() {
        if(igual == null){
            igual = new SemIgual();
        }
        return igual;
    }

    public void setIgual(SemIgual igual) {
        this.igual = igual;
    }
    
    public void visitar(Igual s) {
        getIgual().visitar(s);
    } 
}
