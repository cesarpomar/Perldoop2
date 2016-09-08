package perldoop.semantica.coleccion;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.*;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.Funcion;
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
            } else if (uso instanceof Funcion) {
                s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));

            } else if (uso instanceof Lista) {
                uso = uso.getPadre();
                if (uso instanceof ColParentesis) {
                    ColParentesis col = (ColParentesis) uso;
                    if (col.getTipo() == null) {
                        tipar(col);
                    }
                    if (col.getTipo() != null) {
                        s.setTipo(col.getTipo().getSubtipo(1));
                    }
                } else if (uso instanceof ColCorchete) {
                    ColCorchete col = (ColCorchete) uso;
                    if (col.getTipo() == null) {
                        tipar(col);
                    }
                    if (col.getTipo() != null) {
                        s.setTipo(col.getTipo().getSubtipo(1));
                    }
                } else if (uso instanceof ColLlave) {
                    ColLlave col = (ColLlave) uso;
                    if (col.getTipo() == null) {
                        tipar(col);
                    }
                    if (col.getTipo() != null) {
                        s.setTipo(col.getTipo().getSubtipo(1));
                    }
                }
            }
        }
    }

    private void comprobarElems(Simbolo s, Lista l) {
        Tipo subT = s.getTipo().getSubtipo(1);
        if (subT.isArrayOrList() || subT.isMap()) {
            subT.add(0, Tipo.REF);
        }
        for (Expresion exp : l.getExpresiones()) {
            Tipo texp = exp.getTipo();
            if (texp.isColeccion()) {
                texp = texp.getSubtipo(1);
                if (texp.isColeccion()) {
                    texp.add(0, Tipo.REF);
                }
            }
            Tipos.casting(exp, texp, subT, tabla.getGestorErrores());
        }
    }

    public void visitar(ColParentesis s) {
        tipar(s);
        if (s.getTipo() == null) {
            s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
        }
        comprobarElems(s, s.getLista());
    }

    public void visitar(ColCorchete s) {
        if (s.getPadre() instanceof AccesoCol) {
            s.setTipo(new Tipo(Tipo.ARRAY, Tipo.INTEGER));
            comprobarElems(s, s.getLista());
        } else {
            tipar(s);
            if (s.getTipo() == null) {
                s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
            }
            comprobarElems(s, s.getLista());
            s.getTipo().add(0, Tipo.REF);
        }

    }

    public void visitar(ColLlave s) {
        if (s.getPadre() instanceof AccesoCol) {
            s.setTipo(new Tipo(Tipo.MAP, Tipo.INTEGER));
            comprobarElems(s, s.getLista());
        } else {
            tipar(s);
            if (s.getTipo() == null) {
                s.setTipo(new Tipo(Tipo.MAP, Tipo.BOX));
            }
            comprobarElems(s, s.getLista());
            s.getTipo().add(0, Tipo.REF);
        }
    }

}
