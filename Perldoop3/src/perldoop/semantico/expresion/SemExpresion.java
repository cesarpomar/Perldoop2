/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perldoop.semantico.expresion;

import perldoop.modelo.arbol.expresion.ExpAsignacion;
import perldoop.modelo.arbol.expresion.ExpConstante;
import perldoop.modelo.arbol.expresion.ExpVariable;

/**
 *
 * @author César
 */
public class SemExpresion {

    public void visitar(ExpAsignacion s) {
        s.setTipo(s.getAsignacion().getTipo());
    }

    public void visitar(ExpConstante s) {
        s.setTipo(s.getConstante().getTipo());
    }

    public void visitar(ExpVariable s) {
        s.setTipo(s.getVariable().getTipo());
    }
    
}
