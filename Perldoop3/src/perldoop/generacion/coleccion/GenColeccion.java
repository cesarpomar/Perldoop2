package perldoop.generacion.coleccion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColGenerador;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.ColMy;
import perldoop.modelo.arbol.coleccion.ColOur;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.util.Buscar;

/**
 * Clase generadora de coleccion
 *
 * @author César Pomar
 */
public class GenColeccion {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla
     */
    public GenColeccion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(ColParentesis s) {
        //Expresion entre parentesis
        if (s.getLista().getElementos().size() == 1) {
            s.setCodigoGenerado(new StringBuilder(s.getLista().getExpresiones().get(0).getCodigoGenerado()));
            return;
        }
        /*
        //Las listas no tienen tipo
        if (s.getPadre() instanceof StcLista) {
            return;
        }
        //Colecciones
        Tipo t = s.getTipo();
        Tipo st = t.getSubtipo(1);
        StringBuilder codigo = new StringBuilder(100);
        if (t.isArray()) {
            codigo.append(Tipos.inicializacion(t)).append("{");
            Iterator<Expresion> it = s.getExpresiones().iterator();
            while (it.hasNext()) {
                codigo.append(Casting.casting(it.next(), st));
                if (it.hasNext()) {
                    codigo.append(", ");
                }
            }
            codigo.append("}");
            s.setCodigoGenerado(codigo);
        } else if (t.isList()) {

        } else if (t.isMap()) {

        }*/
    }

    public void visitar(ColCorchete s) {
    }

    public void visitar(ColLlave s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ColGenerador s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ColMy s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(ColOur s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
