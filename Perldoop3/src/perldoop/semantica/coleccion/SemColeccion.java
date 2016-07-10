package perldoop.semantica.coleccion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.*;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;

/**
 * Clase para la semantica de Coleccion
 *
 * @author César Pomar
 */
public class SemColeccion {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemColeccion(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    private void tipar(Coleccion s) {
        if (s.getTipo() == null) {
            Simbolo exp = s.getPadre();
            Simbolo uso = exp.getPadre();
            //Cogemos el tipo de la asignación
            if (uso instanceof Igual) {
                Igual igual = (Igual) uso;
                if (igual.getDerecha() == exp) {
                    //Al otro lado tiene que haber un tipo de dato
                    if (igual.getIzquierda().getTipo() == null) {
                        return;
                    } else {
                        s.setTipo(new Tipo(igual.getIzquierda().getTipo()));
                    }
                }
            } else if (uso instanceof Lista) {
                uso = uso.getPadre();
                if (uso instanceof ColParentesis) {
                    ColParentesis col = (ColParentesis) uso;
                    if (col.getTipo() == null) {
                        tipar(col);
                    }
                    if (col.getTipo() == null) {
                        s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
                    } else {
                        s.setTipo(col.getTipo().getSubtipo(1));
                    }
                } else if (uso instanceof ColCorchete) {
                    ColCorchete col = (ColCorchete) uso;
                    if (col.getTipo() == null) {
                        tipar(col);
                    }
                    if (col.getTipo() == null) {
                        s.setTipo(new Tipo(Tipo.REF, Tipo.ARRAY, Tipo.BOX));
                    } else {
                        s.setTipo(col.getTipo().getSubtipo(1));
                    }
                } else if (uso instanceof ColLlave) {
                    ColLlave col = (ColLlave) uso;
                    if (col.getTipo() == null) {
                        tipar(col);
                    }
                    if (col.getTipo() == null) {
                        s.setTipo(new Tipo(Tipo.REF, Tipo.MAP, Tipo.BOX));
                    } else {
                        s.setTipo(col.getTipo().getSubtipo(1));
                    }
                }
            }
        }
        //Definimos uno genérico
        if (s.getTipo() == null) {
            s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
        }
    }

    private void comprobarElems(Simbolo s, Lista l) {
        Tipo subT = s.getTipo().getSubtipo(1);
        if (subT.isArrayOrList() || subT.isMap()) {
            subT.add(0, Tipo.REF);
        }
        for (Expresion exp : l.getExpresiones()) {
            if (exp.getTipo().isArrayOrList()) {
                Tipos.casting(exp, s.getTipo(), tabla.getGestorErrores());
            } else if (exp.getTipo().isMap()) {
                Tipos.casting(exp, s.getTipo(), tabla.getGestorErrores());
            } else {
                Tipos.casting(exp, subT, tabla.getGestorErrores());
            }
        }
    }

    public void visitar(ColParentesis s) {
        if (s.getLista().getElementos().size() > 1) {
            tipar(s);
            comprobarElems(s, s.getLista());
        }else{
            s.setTipo(s.getLista().getExpresiones().get(0).getTipo());
        }
    }

    public void visitar(ColCorchete s) {
        tipar(s);
        comprobarElems(s, s.getLista());
    }

    public void visitar(ColLlave s) {
        tipar(s);
        comprobarElems(s, s.getLista());
    }

    public void visitar(ColGenerador s) {
    }

    public void visitar(ColMy s) {
    }

    public void visitar(ColOur s) {
    }
}
