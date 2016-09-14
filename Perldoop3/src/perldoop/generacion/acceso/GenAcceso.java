package perldoop.generacion.acceso;

import java.util.List;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

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
     * @param tabla Tabla
     */
    public GenAcceso(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(AccesoCol s) {
        List<Expresion> exps = s.getColeccion().getLista().getExpresiones();
        StringBuilder codigo = new StringBuilder(100);
        Simbolo uso = Buscar.getPadre(s, 1);
        boolean escritura = false;
        boolean asignacionCol = false;//Asignacion entre colecciones
        boolean accesoAnidado = false;//Se accedera al acceso
        if (uso instanceof Igual) {
            escritura = ((Igual) uso).getIzquierda() == s.getPadre();
            asignacionCol = ((Igual) uso).getIzquierda() instanceof ExpAcceso;
        }else if(uso instanceof AccesoCol){
            accesoAnidado = ((AccesoCol)uso).getExpresion() == s.getPadre();
        }
        if (exps.size() > 1 || exps.get(0).getTipo().isColeccion()) {
            codigo.append("Pd.access(").append(s.getExpresion().getCodigoGenerado()).append(", ");
            codigo.append(s.getColeccion().getCodigoGenerado());
            if (escritura) {
                codigo.append(",");
            } else {
                codigo.append(")");
            }
        } else {
            Expresion exp = exps.get(0);
            if (!escritura && !accesoAnidado && !asignacionCol && s.getTipo().isRef()) {
                codigo.append("new ").append(Tipos.declaracion(s.getTipo())).append("(");
            }
            codigo.append(s.getExpresion().getCodigoGenerado());
            Tipo texp = s.getExpresion().getTipo();
            if(texp.isRef()){
                texp = texp.getSubtipo(1);
            }
            if (s.getColeccion() instanceof ColCorchete) {
                if (texp.isArray()) {
                    codigo.append("[").append(Casting.casting(exp, new Tipo(Tipo.INTEGER))).append("]");
                } else if (escritura) {
                    codigo.append(".set(").append(Casting.casting(exp, new Tipo(Tipo.INTEGER))).append(",");
                } else {
                    codigo.append(".get(").append(Casting.casting(exp, new Tipo(Tipo.INTEGER))).append(")");
                }
            } else if (escritura) {
                codigo.append(".put(").append(Casting.casting(exp, new Tipo(Tipo.STRING))).append(",");
            } else {
                codigo.append(".get(").append(Casting.casting(exp, new Tipo(Tipo.STRING))).append(")");
            }
            if (!escritura && !accesoAnidado && !asignacionCol && s.getTipo().isRef()) {
                codigo.append(")");
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
