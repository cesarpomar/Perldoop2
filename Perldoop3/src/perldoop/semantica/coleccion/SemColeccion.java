package perldoop.semantica.coleccion;

import java.util.Iterator;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.acceso.AccesoColRef;
import perldoop.modelo.arbol.acceso.AccesoRefArray;
import perldoop.modelo.arbol.acceso.AccesoRefEscalar;
import perldoop.modelo.arbol.acceso.AccesoRefMap;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.*;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Tipos;
import perldoop.util.Buscar;

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

    /**
     * Calcula el tipo de la coleccion
     *
     * @param s Simbolo de la coleccion
     */
    private void tipar(Coleccion s) {
        if (s.getTipo() != null) {
            return;
        }
        Simbolo exp = s.getPadre();
        Simbolo uso = exp.getPadre();
        Simbolo multi;
        if (uso instanceof Igual) {//Uso para inicializar
            Igual igual = (Igual) uso;
            //Al otro lado tiene que haber un tipo de dato
            if (igual.getIzquierda().getTipo() != null) {
                s.setTipo(new Tipo(igual.getIzquierda().getTipo()));
            }
        } else if ((multi = Buscar.getVarMultivar(s)) != null) {//Uso para inicializar en multiasignacion
            s.setTipo(multi.getTipo());
        } else if (uso instanceof Funcion) {//Uso argumentos funcion
            s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
        } else if (uso instanceof Lista && uso.getPadre() instanceof Coleccion) {//Coleccion anidada
            Coleccion col = (Coleccion) uso.getPadre();
            tipar(col);
            if (col.getTipo() != null) {
                Tipo t = new Tipo(col.getTipo());
                if (s instanceof ColParentesis || t.isBox()) {
                    s.setTipo(t);
                } else {
                    s.setTipo(col.getTipo().getSubtipo(1));
                }
            }
        }
        //Tipamos siembre sin referencias
        if (s.getTipo() != null && s.getTipo().isRef()) {
            s.setTipo(s.getTipo().getSubtipo(1));
        }
    }

    /**
     * Comprueba que todas las claves tienen un valor
     *
     * @param l Lista
     */
    public void checkClaveValor(Lista l) {
        int e = 0;
        Iterator<Expresion> it = l.getExpresiones().iterator();
        Expresion last = null;
        while (it.hasNext()) {
            Expresion exp = it.next();
            if (!exp.getTipo().isColeccion()) {
                e++;
            }
            if (exp.getTipo().isColeccion() || !it.hasNext()) {
                if (e % 2 != 0) {
                    tabla.getGestorErrores().error(Errores.MAPA_NO_VALUE, Buscar.tokenInicio(last));
                    throw new ExcepcionSemantica(Errores.MAPA_NO_VALUE);
                }
            }
            last = exp;
        }
    }

    /**
     * Comprueba si la colección contiene una subcolección que debe ser desplegada y la retorna
     *
     * @param l Lista
     * @return Coleccion
     */
    private Expresion contieneCol(Lista l) {
        for (Expresion exp : l.getExpresiones()) {
            if (exp.getTipo().isColeccion()) {
                return exp;
            }
        }
        return null;
    }

    /**
     * Comprueba que todos los elementos de la coleccion son compatibles con el tipo de la misma
     *
     * @param t Tipo de la coleccion
     * @param l Lista de elementos
     */
    private void comprobarElems(Tipo t, Lista l) {
        Tipo subT = t.getSubtipo(1);
        if (subT.isArrayOrList() || subT.isMap()) {
            subT.add(0, Tipo.REF);
        }
        for (int i = 0; i < l.getExpresiones().size(); i++) {
            Tipo texp = l.getExpresiones().get(i).getTipo();
            if (!t.isMap() || i % 2 == 1) {
                if (texp.isColeccion()) {
                    texp = texp.getSubtipo(1);
                    if (texp.isColeccion()) {
                        texp.add(0, Tipo.REF);
                    }
                }
                Tipos.casting(l.getExpresiones().get(i), texp, subT, tabla.getGestorErrores());
            } else {
                Tipos.casting(l.getExpresiones().get(i), texp, new Tipo(Tipo.STRING), tabla.getGestorErrores());
            }
        }
        if (t.isMap()) {
            checkClaveValor(l);
        }
    }

    public void visitar(ColParentesis s) {
        Simbolo uso = s.getPadre().getPadre();
        if (uso instanceof Igual && ((Igual) uso).getIzquierda() == s.getPadre()) {
            if (contieneCol(s.getLista()) != null) {
                tabla.getGestorErrores().error(Errores.VARIABLE_COLECCION_MULTIASIGNACION, Buscar.tokenInicio(contieneCol(s.getLista())));
            }
            s.setTipo(null);
            tabla.getAcciones().saltarGenerador();
        } else if (s.getLista().getElementos().size() == 1) {
            s.setTipo(new Tipo(s.getLista().getExpresiones().get(0).getTipo()));
        } else {
            tipar(s);
            if (s.getTipo() == null && !(uso instanceof Igual && contieneCol(s.getLista()) == null)) {
                s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
            } else if (s.getTipo() != null && s.getTipo().isBox()){
                s.getTipo().add(0, Tipo.ARRAY);
            } else if (s.getTipo() != null) {
                comprobarElems(s.getTipo(), s.getLista());
            } else {
                tabla.getAcciones().saltarGenerador();
            }
        }
    }

    public void visitar(ColCorchete s) {
        if (s.getPadre() instanceof AccesoCol || s.getPadre() instanceof AccesoColRef) {
            if (s.getLista().getExpresiones().size() == 1) {
                s.setTipo(new Tipo(Tipo.INTEGER));
                Tipos.casting(s.getLista().getExpresiones().get(0), s.getTipo(), tabla.getGestorErrores());
            } else {
                s.setTipo(new Tipo(Tipo.ARRAY, Tipo.NUMBER));
                comprobarElems(s.getTipo(), s.getLista());
            }
        } else {
            tipar(s);
            if (s.getTipo() == null) {
                s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
            } else if (s.getTipo().isBox()) {
                s.getTipo().add(0, Tipo.ARRAY);
            }
            comprobarElems(s.getTipo(), s.getLista());
            s.getTipo().add(0, Tipo.REF);
        }
    }

    public void visitar(ColLlave s) {
        Simbolo padre = s.getPadre();
        Simbolo uso = padre.getPadre();
        if (padre instanceof AccesoCol || padre instanceof AccesoColRef) {
            if (s.getLista().getExpresiones().size() == 1) {
                s.setTipo(new Tipo(Tipo.STRING));
                Tipos.casting(s.getLista().getExpresiones().get(0), s.getTipo(), tabla.getGestorErrores());
            } else {
                s.setTipo(new Tipo(Tipo.ARRAY, Tipo.STRING));
                comprobarElems(s.getTipo(), s.getLista());
            }
        } else if (uso instanceof AccesoRefEscalar || uso instanceof AccesoRefArray || uso instanceof AccesoRefMap) {
            if (s.getLista().getElementos().size() != 1) {
                tabla.getGestorErrores().error(Errores.ACCESO_REF_MULTIPLE, s.getLlaveI());
                throw new ExcepcionSemantica(Errores.ACCESO_REF_MULTIPLE);
            }
            s.setTipo(new Tipo(s.getLista().getExpresiones().get(0).getTipo()));
        } else {
            tipar(s);
            if (s.getTipo() == null) {
                s.setTipo(new Tipo(Tipo.MAP, Tipo.BOX));
            } else if (s.getTipo().isBox()) {
                s.getTipo().add(0, Tipo.MAP);
            }
            comprobarElems(s.getTipo(), s.getLista());
            s.getTipo().add(0, Tipo.REF);
        }
    }

}
