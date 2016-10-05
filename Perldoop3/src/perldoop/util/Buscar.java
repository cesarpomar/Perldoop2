package perldoop.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.constante.CadenaComando;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpConstante;
import perldoop.modelo.arbol.expresion.ExpVariable;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.variable.VarPaqueteSigil;
import perldoop.modelo.arbol.variable.VarSigil;
import perldoop.modelo.arbol.variable.Variable;
import perldoop.modelo.lexico.Token;

/**
 * Clase para hacer busquedas en el arbol
 *
 * @author César Pomar
 */
public final class Buscar {

    /**
     * Obtiene el primer token del simbolo
     *
     * @param s Simbolo
     * @return Token
     */
    public static Token tokenInicio(Simbolo s) {
        Simbolo hijo = s;
        do {
            Simbolo[] hijos = hijo.getHijos();
            if (hijos.length == 0) {
                return null;
            }
            hijo = hijos[0];
        } while (!(hijo instanceof Terminal));
        return ((Terminal) hijo).getToken();
    }

    /**
     * Obtiene el ultimo token del simbolo
     *
     * @param s Simbolo
     * @return TOken
     */
    public static Token tokenFin(Simbolo s) {
        Simbolo hijo = s;
        do {
            Simbolo[] hijos = hijo.getHijos();
            if (hijos.length == 0) {
                return null;
            }
            hijo = hijos[hijos.length - 1];
        } while (!(hijo instanceof Terminal));
        return ((Terminal) hijo).getToken();
    }

    /**
     * Busca un padre por su clase
     *
     * @param <T> Tipo del padre a buscar
     * @param s Simbolo que busca padre
     * @param clase Clase del padre a buscar
     * @return Si el padre existe lo retorna, en caso contrario null
     */
    public static <T extends Simbolo> T buscarPadre(Simbolo s, Class<T> clase) {
        Simbolo padre = s.getPadre();
        while (padre != null) {
            if (padre.getClass().isAssignableFrom(clase)) {
                return (T) padre;
            }
            padre = padre.getPadre();
        }
        return null;
    }

    /**
     * obtiene el padre n superior del simbolo s
     *
     * @param s Simbolo que busca padre
     * @param n Numero padre a recorrer, siendo 0 su padre inmediato
     * @return Padre n
     */
    public static Simbolo getPadre(Simbolo s, int n) {
        Simbolo padre = s.getPadre();
        for (int i = 0; padre != null && i < n; i++) {
            padre = padre.getPadre();
        }
        return padre;
    }

    /**
     * Comprueba si el simbolo es una constante
     *
     * @param s Simbolo
     * @return comprobacion
     */
    public static boolean isConstante(Simbolo s) {
        if (s instanceof ExpConstante) {
            if (!(((ExpConstante) s).getConstante() instanceof CadenaComando)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Comprueba si el simbolo es una variable
     *
     * @param s Simbolo
     * @return comprobacion
     */
    public static boolean isVariable(Simbolo s) {
        Simbolo acceso = s;
        while (acceso instanceof ExpAcceso) {
            ExpAcceso expAcceso = (ExpAcceso) acceso;
            acceso = expAcceso.getAcceso().getExpresion();
        }
        if (acceso instanceof ExpVariable) {
            Variable var = ((ExpVariable) acceso).getVariable();
            if (!(var instanceof VarSigil || var instanceof VarPaqueteSigil)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene la ruta de padres siguiendo sus clases. Si los padres son instancias de las clases, retorna la lista de
     * padres
     *
     * @param s Simbolo
     * @param clases Clases de los padres
     * @return Lista de padres.
     */
    public static List<Simbolo> getCamino(Simbolo s, Class... clases) {
        List<Simbolo> lista = new ArrayList<>(clases.length);
        Simbolo padre = s.getPadre();
        for (Class clase : clases) {
            if (clase.isInstance(padre)) {
                lista.add(padre);
                padre = padre.getPadre();
            } else {
                return new ArrayList<>(0);
            }
        }
        return lista;
    }

    /**
     * Comprueba si la ruta de padres coincide con las instancias de las clases indicadas como argumentos
     *
     * @param s Simbolo
     * @param clases Clases
     * @return Padres coinciden
     */
    public static boolean isCamino(Simbolo s, Class... clases) {
        return !getCamino(s, clases).isEmpty();
    }

    /**
     * Cuenta el numero de accesos anidadas
     *
     * @param acceso Primer acceso
     * @return Numero de accesos
     */
    public static int accesos(Expresion acceso) {
        int accesos = 0;
        Expresion exp = acceso;
        while (exp instanceof ExpAcceso) {
            ExpAcceso expAcceso = (ExpAcceso) exp;
            exp = expAcceso.getAcceso().getExpresion();
            accesos++;
        }
        return accesos;
    }

    /**
     * Busca la variable en la expresión y la retorna
     *
     * @param exp Expresión
     * @return Variable
     */
    public static Variable buscarVariable(Expresion exp) {
        while (exp instanceof ExpAcceso) {
            ExpAcceso expAcceso = (ExpAcceso) exp;
            exp = expAcceso.getAcceso().getExpresion();
        }
        if (exp instanceof ExpVariable) {
            return ((ExpVariable) exp).getVariable();
        }
        return null;
    }

    /**
     * Retorna todas las instancias de la clase que existen en el subarbol del simbolo
     *
     * @param s Simbolo
     * @param clase Clase
     * @return Simbolo
     */
    public static <T> List<T> buscarClases(Simbolo s, Class<T> clase) {
        List<Simbolo> lista = new ArrayList<>(100);
        List<T> resultado = new ArrayList<>(10);
        lista.add(s);
        while (!lista.isEmpty()) {
            Simbolo actual = lista.remove(lista.size() - 1);
            if (clase.isInstance(actual)) {
                resultado.add((T) actual);
            }
            lista.addAll(Arrays.asList(actual.getHijos()));
        }
        return resultado;
    }

    /**
     * Busca el contexto de una variable
     *
     * @param v Variable
     * @return Contexto
     */
    public static char getContexto(Variable v) {
        Simbolo uso = Buscar.getPadre(v, 1);
        if (uso instanceof AccesoCol) {
            AccesoCol col = (AccesoCol) uso;
            if (col.getColeccion() instanceof ColCorchete) {
                return '@';
            } else if (col.getColeccion() instanceof ColLlave) {
                return '%';
            }
        } else if (v instanceof VarSigil || v instanceof VarPaqueteSigil) {
            return '@';
        }
        return v.getContexto().getValor().charAt(0);
    }

    /**
     * Comrpueba si Varriable o Acceso sera asignado
     *
     * @param s Simbolo
     * @return Asignada
     */
    public static boolean isAsignada(Simbolo s) {
        List<Simbolo> lista = null;
        if (s.getPadre().getPadre() instanceof Igual) {
            lista = Arrays.asList(s.getPadre(), s.getPadre().getPadre());

        }
        if (lista == null) {
            lista = Buscar.getCamino(s.getPadre(),Lista.class, ColParentesis.class, ExpColeccion.class, Igual.class);
        }
        int last = lista.size() - 1;
        return last > 0 && ((Igual) lista.get(last)).getIzquierda().equals(lista.get(last - 1));
    }

}
