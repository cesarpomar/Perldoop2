package perldoop.generacion.asignacion;

import perldoop.modelo.arbol.asignacion.AndIgual;
import perldoop.modelo.arbol.asignacion.ConcatIgual;
import perldoop.modelo.arbol.asignacion.DLOrIgual;
import perldoop.modelo.arbol.asignacion.DespDIgual;
import perldoop.modelo.arbol.asignacion.DespIIgual;
import perldoop.modelo.arbol.asignacion.DivIgual;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.asignacion.LAndIgual;
import perldoop.modelo.arbol.asignacion.LOrIgual;
import perldoop.modelo.arbol.asignacion.MasIgual;
import perldoop.modelo.arbol.asignacion.MenosIgual;
import perldoop.modelo.arbol.asignacion.ModIgual;
import perldoop.modelo.arbol.asignacion.MultiIgual;
import perldoop.modelo.arbol.asignacion.OrIgual;
import perldoop.modelo.arbol.asignacion.PowIgual;
import perldoop.modelo.arbol.asignacion.XIgual;
import perldoop.modelo.arbol.asignacion.XorIgual;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de asignacion
 *
 * @author César Pomar
 */
public class GenAsignacion {

    private TablaGenerador tabla;
    private GenIgual genIgual;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenAsignacion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Igual s) {
        if (genIgual == null) {
            genIgual = new GenIgual(tabla);
        }
        genIgual.visitar(s);
    }

    public void visitar(MasIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(MenosIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(MultiIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(DivIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(ModIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(PowIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(AndIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(OrIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(XorIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(DespDIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(DespIIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(LOrIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(DLOrIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(LAndIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(XIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

    public void visitar(ConcatIgual s) {
        throw new UnsupportedOperationException(s.getOperador().getValor()+" Not supported yet."); 
    }

}
