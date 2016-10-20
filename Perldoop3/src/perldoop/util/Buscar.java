package perldoop.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.cadena.Cadena;
import perldoop.modelo.arbol.cadena.CadenaDoble;
import perldoop.modelo.arbol.cadena.CadenaSimple;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpAsignacion;
import perldoop.modelo.arbol.expresion.ExpCadena;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.expresion.ExpNumero;
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
        List<List<Simbolo>> simbolos = new ArrayList<>(100);
        List<Simbolo> nivel = new ArrayList<>(10);
        nivel.add(s);
        simbolos.add(nivel);
        while (!simbolos.isEmpty()) {
            nivel = simbolos.get(simbolos.size() - 1);
            if (nivel.isEmpty()) {
                simbolos.remove(simbolos.size() - 1);
                continue;
            }
            s = nivel.remove(0);
            if (s instanceof Terminal) {
                return ((Terminal) s).getToken();
            }
            nivel = new ArrayList<>(Arrays.asList(s.getHijos()));
            simbolos.add(nivel);
        }
        return null;
    }

    /**
     * Obtiene el ultimo token del simbolo
     *
     * @param s Simbolo
     * @return TOken
     */
    public static Token tokenFin(Simbolo s) {
        List<List<Simbolo>> simbolos = new ArrayList<>(100);
        List<Simbolo> nivel = new ArrayList<>(10);
        nivel.add(s);
        simbolos.add(nivel);
        while (!simbolos.isEmpty()) {
            nivel = simbolos.get(simbolos.size() - 1);
            if (nivel.isEmpty()) {
                simbolos.remove(simbolos.size() - 1);
                continue;
            }
            s = nivel.remove(nivel.size() - 1);
            if (s instanceof Terminal) {
                return ((Terminal) s).getToken();
            }
            nivel = new ArrayList<>(Arrays.asList(s.getHijos()));
            simbolos.add(nivel);
        }
        return null;
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
        if (s instanceof ExpNumero) {
            return true;
        } else if (s instanceof ExpCadena) {
            Cadena c = ((ExpCadena) s).getCadena();
            if (c instanceof CadenaSimple) {
                return true;
            } else if (c instanceof CadenaDoble) {
                List<Simbolo> elems = ((CadenaDoble) c).getTexto().getElementos();
                return elems.isEmpty() || (elems.size() == 1 && elems.get(0) instanceof Terminal);
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
     * Comprueba si el simbolo nunca sera nulo
     *
     * @param s Simbolo
     * @return comprobacion
     */
    public static boolean isNotNull(Simbolo s) {
        if (!(s instanceof Expresion)) {
            s = s.getPadre();
        }
        if (s instanceof ExpAcceso) {
            return false;
        }
        if (s instanceof ExpVariable) {
            return false;
        }
        if (s instanceof ExpAsignacion && ((ExpAsignacion) s).getAsignacion() instanceof Igual) {
            return isNotNull(((Igual) ((ExpAsignacion) s).getAsignacion()).getDerecha());
        }
        if (s instanceof ExpFuncion) {
            if (((ExpFuncion) s).getFuncion().getIdentificador().getValor().equals("undef")) {
                return false;
            }
        }
        return true;
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
    public static Variable buscarVariable(Simbolo exp) {
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
     * Obtiene la variable a la cual se asignara una expresion mediante una multiasignación, si el la variable es quien
     * llava a la funcion se retornara su padre
     *
     * @param s Simbolo
     * @return ExpVariable o ExpAcceso
     */
    public static Expresion getVarMultivar(Simbolo s) {
        List<Simbolo> lista = Buscar.getCamino(s, Expresion.class, Lista.class, ColParentesis.class, ExpColeccion.class, Igual.class);
        if (!lista.isEmpty()) {
            Igual igual = (Igual) lista.get(4);
            int index = ((Lista) lista.get(1)).getExpresiones().indexOf(lista.get(0));
            if (igual.getIzquierda() instanceof ExpColeccion) {
                Coleccion col = ((ExpColeccion) igual.getIzquierda()).getColeccion();
                List<Expresion> vars = col.getLista().getExpresiones();
                if (col instanceof ColParentesis && vars.size() > index) {
                    return vars.get(index);
                }
            }
        }
        return null;
    }

}
