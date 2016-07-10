package perldoop.semantica.asignacion;

import perldoop.modelo.arbol.asignacion.*;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de asignacion
 *
 * @author César Pomar
 */
public class SemAsignacion {

    private TablaSemantica tabla;
    private SemIgual semIgual; 

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemAsignacion(TablaSemantica tabla) {
        this.tabla = tabla;
    }
    
    public void visitar(Igual s) {
       if(semIgual==null){
           semIgual=new SemIgual(tabla);
       } 
       semIgual.visitar(s);
    }
    public void visitar(MasIgual s) {
    }

    public void visitar(MenosIgual s) {
    }

    public void visitar(MultiIgual s) {
    }

    public void visitar(DivIgual s) {
    }

    public void visitar(ModIgual s) {
    }

    public void visitar(PowIgual s) {
    }

    public void visitar(AndIgual s) {
    }

    public void visitar(OrIgual s) {
    }

    public void visitar(XorIgual s) {
    }

    public void visitar(DespDIgual s) {
    }

    public void visitar(DespIIgual s) {
    }

    public void visitar(LOrIgual s) {
    }

    public void visitar(LAndIgual s) {
    }

    public void visitar(XIgual s) {
    }

    public void visitar(ConcatIgual s) {
    }
}
