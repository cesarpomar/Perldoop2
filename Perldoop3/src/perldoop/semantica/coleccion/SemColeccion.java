package perldoop.semantica.coleccion;

import java.util.Iterator;
import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.acceso.AccesoColRef;
import perldoop.modelo.arbol.acceso.AccesoDesRef;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.*;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpVariable;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.variable.VarMy;
import perldoop.modelo.arbol.variable.VarOur;
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
        //Si es una multiasignacion, intentamos usar el tipo de la variable
        if ((s instanceof ColLlave || s instanceof ColCorchete) && s.getPadre() instanceof Expresion) {
            Expresion var = Buscar.getVarMultivar((Expresion) s.getPadre());
            if (var != null) {
                Tipo t = var.getTipo();
                if (t.isRef()) {
                    t = t.getSubtipo(1);
                }
                s.setTipo(t);
                return;
            }
        }
        Simbolo uso = Buscar.getPadre(s, 1);
        //Comprobar si es una coleccion anidada
        if (uso instanceof Lista && uso.getPadre() instanceof Coleccion) {
            Coleccion padre = (Coleccion) uso.getPadre();
            tipar(padre);
            Tipo t = padre.getTipo().getSubtipo(0);
            if (t.isBox()) {
                t.add(0, Tipo.ARRAY);
            }
            if (s instanceof ColParentesis) {
                s.setTipo(t);
            } else {
                t = t.getSubtipo(1);
                if (t.isBox()) {
                    if (s instanceof ColLlave) {
                        t.add(0, Tipo.MAP);
                    } else {
                        t.add(0, Tipo.ARRAY);
                    }
                }
                s.setTipo(t);
            }
            return;
        }
        //Si esta a la derecha de igual, cogemos el tipo de la asignacion si es una coleccion
        if (uso instanceof Igual && Buscar.isHijo(s, ((Igual) uso).getDerecha())) {
            Tipo t = ((Igual) uso).getIzquierda().getTipo();
            if (t != null) {
                if (t.isRef()) {
                    t = t.getSubtipo(1);
                }
                if (!t.isColeccion()) {
                    t = t.getSubtipo(0);
                    if (s instanceof ColLlave) {
                        t.add(0, Tipo.MAP);
                    } else {
                        t.add(0, Tipo.ARRAY);
                    }
                }
                s.setTipo(t);
                return;
            }
        }
        if (s instanceof ColParentesis) {
            //Si es una funcion la coleccion representa el argumento
            if (s.getPadre() instanceof Funcion) {
                s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
                return;
            }
            //Si esta a la izquierda de igual, es una mutiasignacion, no se necesita tipo
            Igual igual = Buscar.buscarPadre(s, Igual.class);
            if (igual != null && Buscar.isHijo(s, igual.getIzquierda())) {
                return;
            }
            //Si es una expresion sola entre parentesis, propagamos el tipo
            if (s.getLista().getExpresiones().size() == 1) {
                s.setTipo(s.getLista().getExpresiones().get(0).getTipo());
                if (s.getTipo() != null) {
                    return;
                }
            }
        }
        if (s instanceof ColCorchete) {
            //Si es un acceso
            if (s.getPadre() instanceof AccesoCol || s.getPadre() instanceof AccesoColRef) {
                //Con solo una expresion en su interior
                if (s.getLista().getExpresiones().size() == 1) {
                    Expresion exp = s.getLista().getExpresiones().get(0);
                    if (!(exp.getValor() instanceof ColParentesis) || Buscar.getExpresiones((Coleccion) exp.getValor()).size() == 1) {
                        s.setTipo(new Tipo(Tipo.INTEGER));
                        return;
                    }
                }
                s.setTipo(new Tipo(Tipo.ARRAY, Tipo.NUMBER));
                return;
            }
        }
        if (s instanceof ColLlave) {
            //Si es un acceso
            if (s.getPadre() instanceof AccesoCol || s.getPadre() instanceof AccesoColRef) {
                //Con solo una expresion en su interior
                if (s.getLista().getExpresiones().size() == 1) {
                    Expresion exp = s.getLista().getExpresiones().get(0);
                    if (!(exp.getValor() instanceof ColParentesis) || Buscar.getExpresiones((Coleccion) exp.getValor()).size() == 1) {
                        s.setTipo(new Tipo(Tipo.STRING));
                        return;
                    }
                }
                s.setTipo(new Tipo(Tipo.ARRAY, Tipo.STRING));
                return;
            }
            uso = Buscar.getUso((Expresion) s.getPadre());
            if (uso instanceof AccesoDesRef && s.getLista().getExpresiones().size() == 1) {
                Tipo t = s.getLista().getExpresiones().get(0).getTipo();
                if (t != null) {
                    if (t.isRef()) {
                        t = t.getSubtipo(1);
                    }
                    s.setTipo(t);
                    return;
                }
            }

        }
        //En el caso final de que la coleccion no vaya a ser usada por nadie 'util', asignamos tipo generico
        s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
    }

    /**
     * Comprueba que todas las claves tienen un valor
     *
     * @param expresiones Lista de expresiones
     * @param tabla Tabla semantica
     */
    private static void checkClaveValor(List<Expresion> expresiones, TablaSemantica tabla) {
        int e = 0;
        Iterator<Expresion> it = expresiones.iterator();
        Expresion last = null;
        while (it.hasNext()) {
            Expresion exp = it.next();
            if (!exp.getTipo().isColeccion()) {
                e++;
            }
            if ((!it.hasNext() || exp.getTipo().isColeccion()) && e % 2 != 0) {
                tabla.getGestorErrores().error(Errores.MAPA_NO_VALUE, Buscar.tokenInicio(last));
                throw new ExcepcionSemantica(Errores.MAPA_NO_VALUE);
            }
            last = exp;
        }
    }

    /**
     * Comprueba que todos los elementos de la coleccion son compatibles con el tipo de la misma
     *
     * @param t Tipo de la coleccion
     * @param expresiones Lista de expresiones
     * @param tabla Tabla semantica
     */
    public static void comprobarElems(Tipo t, List<Expresion> expresiones, TablaSemantica tabla) {
        Tipo subT = t.getSubtipo(1);
        if (subT.isColeccion()) {
            subT.add(0, Tipo.REF);
        }
        for (int i = 0; i < expresiones.size(); i++) {
            Tipo texp = expresiones.get(i).getTipo();
            if (!t.isMap() || i % 2 == 1) {
                if (texp.isColeccion()) {
                    texp = texp.getSubtipo(1);
                    if (texp.isColeccion()) {
                        texp.add(0, Tipo.REF);
                    }
                }
                Tipos.casting(expresiones.get(i), texp, subT, tabla.getGestorErrores());
            } else {
                Tipos.casting(expresiones.get(i), texp, new Tipo(Tipo.STRING), tabla.getGestorErrores());
            }
        }
        if (t.isMap()) {
            checkClaveValor(expresiones, tabla);
        }
    }

    /**
     * Comprueba que en la lista solo haya variables y ninguna este acompañada de my o our
     *
     * @param s Simbolo s
     */
    private void checkVariablesDec(ColDec s) {
        for (Expresion exp : s.getLista().getExpresiones()) {
            if (exp instanceof ExpVariable) {
                if (((ExpVariable) exp).getVariable() instanceof VarMy || ((ExpVariable) exp).getVariable() instanceof VarOur) {
                    tabla.getGestorErrores().error(Errores.DOBLE_DECLARACION, Buscar.tokenInicio(exp));
                    throw new ExcepcionSemantica(Errores.DOBLE_DECLARACION);
                }
            } else {
                tabla.getGestorErrores().error(Errores.DECLARACION_NO_VAR, Buscar.tokenInicio(exp));
                throw new ExcepcionSemantica(Errores.DECLARACION_NO_VAR);
            }
        }
    }

    public void visitar(ColParentesis s) {
        tipar(s);
        //Si es null es el lado izquierdo de la multi asignacion y si no es una coleccion es una simple expresion
        if (s.getTipo() != null && s.getTipo().isColeccion()) {
            comprobarElems(s.getTipo(), s.getLista().getExpresiones(), tabla);
        }
    }

    public void visitar(ColDecMy s) {
        checkVariablesDec(s);
        visitar((ColParentesis) s);
    }

    public void visitar(ColDecOur s) {
        checkVariablesDec(s);
        visitar((ColParentesis) s);
    }

    public void visitar(ColCorchete s) {
        tipar(s);
        if (s.getLista().getExpresiones().size() > 1) {
            comprobarElems(s.getTipo(), s.getLista().getExpresiones(), tabla);
        }
        if (s.getPadre() instanceof AccesoCol || s.getPadre() instanceof AccesoColRef) {
            return;
        }
        s.getTipo().add(0, Tipo.REF);
    }

    public void visitar(ColLlave s) {
        tipar(s);
        if (s.getLista().getExpresiones().size() > 1) {
            comprobarElems(s.getTipo(), s.getLista().getExpresiones(), tabla);
        }
        if (s.getPadre() instanceof AccesoCol || s.getPadre() instanceof AccesoColRef) {
            return;
        }
        s.getTipo().add(0, Tipo.REF);
    }

}
