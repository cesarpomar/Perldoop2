package perldoop.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.acceso.Acceso;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.acceso.AccesoColRef;
import perldoop.modelo.arbol.acceso.AccesoDesRef;
import perldoop.modelo.arbol.aritmetica.AritPostDecremento;
import perldoop.modelo.arbol.aritmetica.AritPostIncremento;
import perldoop.modelo.arbol.aritmetica.AritPreDecremento;
import perldoop.modelo.arbol.aritmetica.AritPreIncremento;
import perldoop.modelo.arbol.asignacion.Asignacion;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueSimple;
import perldoop.modelo.arbol.bloque.SubBloque;
import perldoop.modelo.arbol.cadena.Cadena;
import perldoop.modelo.arbol.cadena.CadenaDoble;
import perldoop.modelo.arbol.cadena.CadenaSimple;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColDec;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpCadena;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.ExpNumero;
import perldoop.modelo.arbol.expresion.ExpVariable;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.funcion.FuncionBasica;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.variable.VarMy;
import perldoop.modelo.arbol.variable.VarOur;
import perldoop.modelo.arbol.variable.VarPaqueteSigil;
import perldoop.modelo.arbol.variable.VarSigil;
import perldoop.modelo.arbol.variable.Variable;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.TagsBloque;
import perldoop.modelo.semantica.Tipo;

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
     * la variable pertenece al bloque sort
     *
     * @param s Variable
     * @return Es variable sort
     */
    public static boolean isVariableSort(Variable s) {
        if (s.getContexto().getValor().charAt(0) != '$') {
            return false;
        }
        String id = s.getVar().getValor();
        if (!id.equals("a") && !id.equals("b")) {
            return false;
        }
        FuncionBloque funcion = Buscar.buscarPadre(s, FuncionBloque.class);
        return funcion != null && funcion.getIdentificador().getValor().equals("sort") && Buscar.isHijo(s, funcion.getExpresion());
    }

    /**
     * Obtiene todas las expresiones dentro de coleciones de parentesis anidadas
     *
     * @param exp Expresion
     * @return Lista de expresiones
     */
    public static List<Expresion> getExpresiones(Expresion exp) {
        if (exp instanceof ExpColeccion) {
            return getExpresiones(((ExpColeccion) exp).getColeccion());
        }
        return new ArrayList<>(Arrays.asList(exp));
    }

    /**
     * Obtiene todas las expresiones dentro de coleciones de parentesis anidadas
     *
     * @param col Coleccion parentesis
     * @return Lista de expresiones
     */
    public static List<Expresion> getExpresiones(Coleccion col) {
        List<Expresion> pila = new ArrayList<>(20);
        List<Expresion> lista = new ArrayList<>(40);
        List<Expresion> exps = col.getLista().getExpresiones();
        ListIterator<Expresion> it = exps.listIterator(exps.size());
        while (it.hasPrevious()) {
            pila.add(it.previous());
        }
        while (!pila.isEmpty()) {
            Expresion actual = pila.remove(pila.size() - 1);
            if (actual.getValor() instanceof ColParentesis) {
                exps = ((ColParentesis) actual.getValor()).getLista().getExpresiones();
                it = exps.listIterator(exps.size());
                while (it.hasPrevious()) {
                    pila.add(it.previous());
                }
            } else {
                lista.add(actual);
            }
        }
        return lista;
    }

    /**
     * Obtiene la expresion aunque estea anidada dentro de colecciones de parentesis de un solo elemento. El
     * comportamiento es el mismo que llamar a getExpresiones(ExpColeccion) cuando esta retorna una lista con un solo
     * elemento.
     *
     * @param exp Expresion
     * @return Expresion anidada
     */
    public static Expresion getExpresion(Expresion exp) {
        Expresion aux = exp;
        while (aux.getValor() instanceof ColParentesis) {
            Lista lista = ((ColParentesis) aux.getValor()).getLista();
            if (lista.getExpresiones().size() == 1) {
                aux = lista.getExpresiones().get(0);
            } else {
                break;
            }
        }
        return aux;
    }

    /**
     * Obtiene el simbolo que usa la expresion, este metodo ignora la expresiones entre parentesis como uso.
     *
     * @param exp Expresión
     * @return Simbolo uso
     */
    public static Simbolo getUso(Expresion exp) {
        Simbolo aux = exp.getPadre();
        while (aux instanceof Lista) {
            Lista lista = (Lista) aux;
            if (aux.getPadre() instanceof ColParentesis && lista.getExpresiones().size() == 1) {
                aux = aux.getPadre().getPadre().getPadre();
                continue;
            }
            break;
        }
        return aux;
    }

    /**
     * Comprueba si el simbolo aux es descenciente de s2
     *
     * @param s Simbolo hijo
     * @param s2 Posible Padre en algun grado
     * @return Descendiente aux de s2
     */
    public static boolean isHijo(Simbolo s, Simbolo s2) {
        Simbolo aux = s.getPadre();
        while (aux != null && s2 != null) {
            if (aux == s2) {
                return true;
            }
            aux = aux.getPadre();
        }
        return false;
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
            if (clase.isAssignableFrom(padre.getClass())) {
                return (T) padre;
            }
            padre = padre.getPadre();
        }
        return null;
    }

    /**
     * obtiene el padre n superior del simbolo aux
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
        if (s instanceof Expresion) {
            Expresion exp = (Expresion) s;
            if (exp instanceof ExpColeccion) {
                List<Expresion> lista = Buscar.getExpresiones((Coleccion) exp.getValor());
                if (lista.size() == 1) {
                    exp = lista.get(0);
                }
            }
            if (exp instanceof ExpNumero) {
                return true;
            } else if (exp instanceof ExpCadena) {
                Cadena c = ((ExpCadena) s).getCadena();
                if (c instanceof CadenaSimple) {
                    return true;
                } else if (c instanceof CadenaDoble) {
                    List<Simbolo> elems = ((CadenaDoble) c).getTexto().getElementos();
                    return elems.isEmpty() || (elems.size() == 1 && elems.get(0) instanceof Terminal);
                }
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
        Simbolo aux = s;
        boolean accesos = false;
        while (aux instanceof ExpAcceso) {
            accesos = true;
            Acceso acceso = ((ExpAcceso) aux).getAcceso();
            if (acceso instanceof AccesoDesRef && acceso.getExpresion().getValor() instanceof ColLlave) {
                aux = ((ColLlave) acceso.getExpresion().getValor()).getLista().getExpresiones().get(0);
            } else {
                aux = acceso.getExpresion();
            }
        }
        if (aux instanceof ExpVariable) {
            Variable var = ((ExpVariable) aux).getVariable();
            if (accesos && var.getContexto().getValor().equals("%")) {//Los acesos en % no son variables
                return false;
            }
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
            if (!(s instanceof Expresion)) {
                return false;
            }
        }
        Expresion exp = Buscar.getExpresion((Expresion) s);
        if (exp instanceof ExpColeccion) {
            List<Expresion> lista = Buscar.getExpresiones((Coleccion) exp.getValor());
            if (lista.size() == 1) {
                exp = lista.get(0);
            }
        }
        if (exp instanceof ExpAcceso) {
            return false;
        }
        if (exp instanceof ExpVariable) {
            return false;
        }
        if (exp.getValor() instanceof Igual) {
            return isNotNull(((Igual) exp.getValor()).getDerecha());
        }
        return !isUndef(exp);
    }

    /**
     * Obtiene la variable a la cual se asignara una expresion mediante una multiasignación, si el la variable es quien
     * llava a la funcion se retornara su padre. En caso de no poder calcular cual es la variable se retorna null
     *
     * @param exp Expresion
     * @return ExpVariable o ExpAcceso
     */
    public static Expresion getVarMultivar(Expresion exp) {
        //Buscar el simbolo igual
        Simbolo uso = Buscar.getUso(exp);
        if (!(uso instanceof Igual)) {
            if ((uso = uso.getPadre()) instanceof ColParentesis && (uso = uso.getPadre()) instanceof ExpColeccion) {
                uso = Buscar.getUso((Expresion) uso);
            }
        }
        if (!(uso instanceof Igual)) {
            return null;
        }
        //Obtener las variables y los valores
        Igual igual = (Igual) uso;
        List<Expresion> izquierda;
        List<Expresion> derecha;
        if (igual.getIzquierda() instanceof ExpColeccion) {
            izquierda = Buscar.getExpresiones((Coleccion) igual.getIzquierda().getValor());
        } else {
            return null;
        }
        if (igual.getDerecha() instanceof ExpColeccion) {
            derecha = Buscar.getExpresiones((Coleccion) igual.getDerecha().getValor());
        } else {
            derecha = Arrays.asList(igual.getDerecha());
        }
        //Calcular posicion
        for (int i = 0; i < izquierda.size() && i < derecha.size(); i++) {
            Expresion der = derecha.get(i);
            Expresion izq = izquierda.get(i);
            if (der == exp) {
                return izq;
            }
            if (der.getTipo().isColeccion() || izq.getTipo().isColeccion()) {
                return null;
            }
        }
        return null;
    }

    /**
     * Obtiene el uso de la expresion obviando las colecciones con parentesis
     *
     * @param exp Expresion
     * @return Simbolo
     */
    public static Simbolo getUsoCol(Expresion exp) {
        Simbolo s;
        if ((s = exp.getPadre()) instanceof Lista && (s = s.getPadre()) instanceof ColParentesis && (s = s.getPadre()) instanceof ExpColeccion) {
            exp = (Expresion) s;
        }
        return exp.getPadre();
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
     * Obtiene la coleccion que contiene la coleccion
     *
     * @param col Coleccion
     * @return Coleccion padre
     */
    public static Coleccion getColeccion(Coleccion col) {
        Simbolo aux = col.getPadre().getPadre();
        if (aux instanceof Lista && aux.getPadre() instanceof Coleccion) {
            return (Coleccion) aux.getPadre();
        }
        return null;
    }

    /**
     * Comprueba si la variable esta siendo declarada
     *
     * @param var Variable
     * @return Es variable declarada
     */
    public static boolean isDeclaracion(Variable var) {
        if (var instanceof VarMy || var instanceof VarOur) {
            return true;
        }
        Simbolo aux = var.getPadre().getPadre();
        while (aux instanceof Lista) {
            if (aux.getPadre() instanceof ColDec) {
                return true;
            }
            if (aux.getPadre() instanceof ColParentesis) {
                aux = aux.getPadre().getPadre().getPadre();
                continue;
            }
            break;
        }
        return false;
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
     * Busca el contexto de una variable
     *
     * @param s Simbolo
     * @return Contexto
     */
    public static Character getContexto(Simbolo s) {
        Simbolo aux = s;
        if (aux instanceof Expresion) {
            aux = ((Expresion) aux).getValor();
        }
        while (aux != null) {
            if (aux instanceof AccesoCol || aux instanceof AccesoColRef) {
                aux = ((Acceso) aux).getExpresion().getValor();
            } else if (aux instanceof AccesoDesRef) {
                return ((AccesoDesRef) aux).getContexto().getValor().charAt(0);
            } else if (aux instanceof Variable) {
                return ((Variable) aux).getContexto().getValor().charAt(0);
            } else {
                return null;
            }
        }
        return null;
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
     * Retorna la primera instancia de la clase que exista en el subarbol del simbolo
     *
     * @param <T> Tipo del simbolo
     * @param s Simbolo
     * @param clase Clase
     * @return Simbolo
     */
    public static <T> T buscarClase(Simbolo s, Class<T> clase) {
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
            if (clase.isInstance(s)) {
                return (T) s;
            }
            nivel = new ArrayList<>(Arrays.asList(s.getHijos()));
            simbolos.add(nivel);
        }
        return null;
    }

    /**
     * Retorna todas las instancias de la clase que existen en el subarbol del simbolo
     *
     * @param <T> Tipo del simbolo
     * @param s Simbolo
     * @param clase Clase
     * @return Lista de simbolos
     */
    public static <T> List<T> buscarClases(Simbolo s, Class<T> clase) {
        List<T> resultado = new ArrayList<>(10);
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
            if (clase.isInstance(s)) {
                resultado.add((T) s);
            }
            nivel = new ArrayList<>(Arrays.asList(s.getHijos()));
            simbolos.add(nivel);
        }
        return resultado;
    }

    /**
     * Retorna todas las instancias de las subclases mas altas que coninciden con alguna de las clases, de forma que una
     * vez que una instancia coincide, no se profundiza en sus descendientes
     *
     * @param s Simbolo
     * @param clases Clases a buscar
     * @return Lista de simbolos
     */
    public static List<Simbolo> buscarClases(Simbolo s, Class<? extends Simbolo>... clases) {
        List<Simbolo> resultado = new ArrayList<>(10);
        List<List<Simbolo>> simbolos = new ArrayList<>(100);
        List<Simbolo> nivel = new ArrayList<>(10);
        nivel.add(s);
        simbolos.add(nivel);
        WHILE:
        while (!simbolos.isEmpty()) {
            nivel = simbolos.get(simbolos.size() - 1);
            if (nivel.isEmpty()) {
                simbolos.remove(simbolos.size() - 1);
                continue;
            }
            s = nivel.remove(0);
            for (Class clase : clases) {
                if (clase.isInstance(s)) {
                    resultado.add(s);

                    continue WHILE;
                }
            }
            nivel = new ArrayList<>(Arrays.asList(s.getHijos()));
            simbolos.add(nivel);

        }
        return resultado;
    }

    /**
     * Comprueba si el codigo puede ser repetido
     *
     * @param s Simbolo
     * @return Es el codigo repetible
     */
    public static boolean isRepetible(Simbolo s) {
        List<Simbolo> lista = new ArrayList<>(100);
        lista.add(s);
        while (!lista.isEmpty()) {
            Simbolo actual = lista.remove(lista.size() - 1);
            if (actual instanceof Asignacion && !(actual instanceof Igual)) {
                return false;
            } else if (actual instanceof AritPreIncremento || actual instanceof AritPostIncremento) {
                return false;
            } else if (actual instanceof AritPreDecremento || actual instanceof AritPostDecremento) {
                return false;
            }
            lista.addAll(Arrays.asList(actual.getHijos()));
        }
        return true;
    }

    /**
     * Comprueba si la expresion es una variable o un acceso a un array
     *
     * @param exp Expresion
     * @return Es una variable o un acceso a un array
     */
    public static boolean isArrayOrVar(Expresion exp) {
        Expresion aux = Buscar.getExpresion(exp);
        if (aux instanceof ExpVariable) {
            return true;
        }
        if (aux instanceof ExpAcceso) {
            Tipo t = ((ExpAcceso) aux).getAcceso().getExpresion().getTipo();
            if (t.isRef()) {
                t = t.getSubtipo(1);
            }
            return t.isArray();

        }
        return false;
    }

    /**
     * Comprueba si el simbolo es un valor indefinido
     *
     * @param s Simbolo
     * @return Es valor indefinido
     */
    public static boolean isUndef(Simbolo s) {
        if (!(s instanceof Expresion)) {
            s = s.getPadre();
        }
        if (!(s instanceof Expresion)) {
            return false;
        }
        Expresion exp = Buscar.getExpresion((Expresion) s);
        if (exp.getValor() instanceof FuncionBasica) {
            Funcion f = (Funcion) exp.getValor();
            if (f.getIdentificador().getValor().equals("undef") && f.getPaquetes().isVacio()) {
                return true;
            }

        }
        return false;
    }

    /**
     * Busca las dependencias externas de variables de un bloque
     *
     * @param b Bloque
     * @return Lista de varaibles externas
     */
    public static List<Variable> buscarDependencias(Bloque b) {
        Map<String, Variable> vars = new HashMap<>();
        List<Set<String>> decs = new ArrayList<>();
        decs.add(new HashSet<>());
        Simbolo s = b;
        List<List<Simbolo>> simbolos = new ArrayList<>(100);
        List<Simbolo> nivel = new ArrayList<>(Arrays.asList(s.getHijos()));
        nivel.add(s);
        simbolos.add(nivel);
        NEXT:
        while (!simbolos.isEmpty()) {
            nivel = simbolos.get(simbolos.size() - 1);
            if (nivel.isEmpty()) {
                simbolos.remove(simbolos.size() - 1);
                continue;
            }
            s = nivel.remove(0);
            if (!nivel.isEmpty()) {
                nivel = new ArrayList<>(Arrays.asList(s.getHijos()));
                if (!nivel.isEmpty()) {
                    nivel.add(s);
                    simbolos.add(nivel);
                    continue;
                }
            }
            if (s instanceof AbrirBloque) {
                decs.add(new HashSet<>());
            } else if (s instanceof Cuerpo) {
                decs.remove(decs.size() - 1);
            } else if (s instanceof Bloque && !(s instanceof BloqueSimple) && !(s instanceof SubBloque)) {
                decs.remove(decs.size() - 1);
            } else if (s instanceof Variable) {
                String id = Buscar.getContexto((Variable) s) + ((Variable) s).getVar().getValor();
                if (isDeclaracion((Variable) s) || isVariableSort((Variable) s)) {
                    decs.get((decs.size() - 1)).add(id);
                } else {
                    ListIterator<Set<String>> it = decs.listIterator(decs.size());
                    while (it.hasPrevious()) {
                        if (it.previous().contains(id)) {
                            continue NEXT;
                        }
                    }
                    vars.put(id, (Variable) s);
                }
            }
        }
        return new ArrayList<>(vars.values());
    }

    /**
     * Obtiene el bloque especial
     *
     * @param s Simbolo
     * @param clase Clase del bloque especial
     * @return Bloque especial
     */
    public static Bloque getSpecial(Simbolo s, Class<? extends TagsBloque> clase) {
        while ((s = s.getPadre()) != null) {
            if (s instanceof Bloque && clase.isInstance(((Bloque) s).getLlaveI().getEtiquetas())) {
                return (Bloque) s;
            }
        }
        return null;
    }

}
