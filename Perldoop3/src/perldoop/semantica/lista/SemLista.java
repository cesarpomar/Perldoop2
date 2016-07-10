package perldoop.semantica.lista;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de lista
 *
 * @author César Pomar
 */
public class SemLista {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemLista(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Lista s) {/*
        //Las listas no tienen tipo
        if (s.getPadre() instanceof StcLista) {
            return;
        }
        //Expresion entre parentesis
        if (s.getElementos().size() == 1 && s.getPadre() instanceof ColParentesis) {
            s.setTipo(new Tipo(s.getExpresiones().get(0).getTipo()));
            return;
        }
        //Si pertenece a una coleccion
        Simbolo padre = s;
        int anidado = 0;
        while (padre.getPadre() instanceof ColCorchete || padre.getPadre() instanceof ColCorchete) {
            padre = Buscar.getPadre(padre, 2);
            anidado++;
        }
        if (padre.getPadre() instanceof ColParentesis) {
            Simbolo exp = Buscar.getPadre(padre, 1);
            Simbolo uso = exp.getPadre();
            //Si va inicar una coleccion
            if (uso instanceof Igual) {
                Igual igual = (Igual) uso;
                //Tiene que ser el lado derecho
                if (igual.getDerecha() == exp) {
                    //Al otro lado tiene que haber un tipo de dato
                    if (igual.getIzquierda().getTipo() == null) {
                        return;
                    } else {
                        Tipo t = igual.getIzquierda().getTipo();
                        if (t != null) {
                            t = t.getSubtipo(anidado);
                            if (t.isArrayOrList()) {
                                s.setTipo(t);
                            }
                        }
                    }
                }
            }

        } else if (s.getPadre() instanceof Acceso) {
            if (s.getPadre() instanceof AccesoArray || s.getPadre() instanceof AccesoArrayRef) {
                s.setTipo(new Tipo(Tipo.ARRAY, Tipo.INTEGER));
            } else if (s.getPadre() instanceof AccesoMap || s.getPadre() instanceof AccesoMapRef) {
                s.setTipo(new Tipo(Tipo.ARRAY, Tipo.STRING));
            }
        }
        //Creamos un tipo generico
        if (s.getTipo() == null) {
            if (s.getPadre() instanceof ColLlave) {
                s.setTipo(new Tipo(Tipo.MAP,Tipo.BOX));
            }else{
                s.setTipo(new Tipo(Tipo.ARRAY,Tipo.BOX));
            }
        }
        Tipo subT = s.getTipo().getSubtipo(1);
        if (subT.isArrayOrList() || subT.isMap()) {
            subT.add(0, Tipo.REF);
        }
        Tipo t = s.getTipo();
        for (Expresion exp : s.getExpresiones()) {
            if (exp.getTipo().isArrayOrList()) {
                Tipos.casting(exp, t, tabla.getGestorErrores());
            } else if (exp.getTipo().isMap()) {
                Tipos.casting(exp, t, tabla.getGestorErrores());
            } else {
                Tipos.casting(exp, subT, tabla.getGestorErrores());
            }
        }*/

    }

}
