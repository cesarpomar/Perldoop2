package perldoop.generacion.acceso;

import java.util.List;
import perldoop.generacion.util.Casting;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase generadora de acceso
 *
 * @author César Pomar
 */
public class GenAcceso {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenAcceso(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(AccesoCol s) {
        List<Expresion> exps = s.getColeccion().getLista().getExpresiones();
        StringBuilder codigo = new StringBuilder(100);
        if (exps.size() > 1) {
            codigo.append("Perl.access(").append(s.getExpresion().getCodigoGenerado()).append(", ");
            codigo.append(s.getColeccion().getCodigoGenerado()).append(")");
        } else {
            Expresion exp = exps.get(0);
            codigo.append(s.getExpresion().getCodigoGenerado());
            if (s.getColeccion() instanceof ColCorchete) {
                if (s.getExpresion().getTipo().isArray()) {
                    codigo.append("[").append(Casting.casting(exp, new Tipo(Tipo.INTEGER))).append("]");
                }else{
                    codigo.append(".get(").append(Casting.casting(exp, new Tipo(Tipo.INTEGER))).append(")");
                }
            } else {
                codigo.append(".get(").append(Casting.casting(exp, new Tipo(Tipo.STRING))).append(")");
            }
        }
        s.setCodigoGenerado(codigo);
    }

    public void visitar(AccesoColRef s) {
    }

    public void visitar(AccesoRefEscalar s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AccesoRefArray s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AccesoSigil s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AccesoRefMap s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(AccesoRef s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
