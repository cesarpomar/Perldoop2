package perldoop.semantica.util;

import perldoop.error.GestorErrores;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;
import perldoop.util.ParserEtiquetas;

/**
 * Clase para la gestion semantica de tipos
 *
 * @author César Pomar
 */
public final class Tipos {

    /**
     * Comprueba el casting de un simbolo a otro para verificar la conversión
     *
     * @param s Simbolo con tipo de origen
     * @param t Tipo de destino
     * @param ge Sistema de errores
     */
    public static void casting(Simbolo s, Tipo t, GestorErrores ge) {
        casting(s, s.getTipo(), t, ge);
    }

    /**
     * Comprueba el casting de un simbolo a otro para verificar la conversión, en este caso el tipo de simbolo se
     * especifica a parte por si hiciera falta especificar otro
     *
     * @param s Simbolo
     * @param to Tipo origen
     * @param td Tipo destino
     * @param ge Sistema de errores
     */
    public static void casting(Simbolo s, Tipo to, Tipo td, GestorErrores ge) {
        if (Buscar.isUndef(s)) {
        } else if (td.isColeccion()) {
            toColeccion(s, to, td, ge);
        } else {
            toScalar(s, to, td, ge);
        }
    }

    /**
     * Comprueba el casting de un simbolo a Escalar
     *
     * @param s Simbolo
     * @param to Tipo origen
     * @param td Tipo destino
     * @param ge Sistema de errores
     */
    public static void toScalar(Simbolo s, Tipo to, Tipo td, GestorErrores ge) {
        if (td.isFile()) {
            if (!to.isFile() && !to.isBox()) {
                error(s, to, td, ge);
            }
        } else if (td.isRef()) {
            if (to.isRef()) {
                if (!to.getTipo().equals(td.getTipo())) {
                    error(s, to, td, ge);
                }
            } else if (!to.isBox()) {
                error(s, to, td, ge);
            }
        }
    }

    /**
     * Comprueba el casting de un simbolo a Coleccion
     *
     * @param s Simbolo
     * @param to Tipo origen
     * @param td Tipo destino
     * @param ge Sistema de errores
     */
    public static void toColeccion(Simbolo s, Tipo to, Tipo td, GestorErrores ge) {
        if (!td.isColeccion()) {
            error(s, to, td, ge);
        }
        if (to.getTipo().size() != td.getTipo().size()) {
            if (!to.getSimple().isBox() || !td.getSimple().isBox()) {
                error(s, to, td, ge);
            }
        }
    }

    /**
     * Comprueba si es un tipo de coleccion especial
     *
     * @param s Simbolo s
     * @return Coleccion especial
     */
    private static boolean isSpecialCol(Simbolo s) {
        Expresion exp;
        if (s instanceof Expresion) {
            exp = Buscar.getExpresion((Expresion) s);
        } else if (s.getPadre() instanceof Expresion) {
            exp = Buscar.getExpresion((Expresion) s.getPadre());
        } else {
            return false;
        }
        return exp.getTipo() != null && exp.getTipo().isArrayOrList()
                && (exp.getValor() instanceof Funcion || exp.getValor() instanceof ColParentesis);
    }

    /**
     * Obtiene la representacion numerica de cualquier tipo
     *
     * @param s Simbolo
     * @return Tipo numerico
     */
    public static Tipo asNumber(Simbolo s) {
        Tipo t = s.getTipo();
        if (isSpecialCol(s)) {
            t = t.getSubtipo(1);
        }
        switch (t.getTipo().get(0)) {
            case Tipo.INTEGER:
            case Tipo.LONG:
            case Tipo.FLOAT:
            case Tipo.DOUBLE:
                return new Tipo(t);
            case Tipo.BOOLEAN:
            case Tipo.FILE:
            case Tipo.REF:
            case Tipo.ARRAY:
            case Tipo.LIST:
            case Tipo.MAP:
                return new Tipo(Tipo.INTEGER);
            case Tipo.NUMBER:
            case Tipo.BOX:
            case Tipo.STRING:
            default:
                return new Tipo(Tipo.DOUBLE);
        }
    }

    /**
     * Lanza un error de casting cuando no se puede combertir de tipo origen a tipo destino
     *
     * @param s Simbolo
     * @param to Tipo origen
     * @param td Tipo destino
     * @param ge Sistema de errores
     */
    public static void error(Simbolo s, Tipo to, Tipo td, GestorErrores ge) {
        ge.error(Errores.ERROR_CASTING, Buscar.tokenInicio(s), String.join("", ParserEtiquetas.parseTipo(to)), String.join("", ParserEtiquetas.parseTipo(td)));
        throw new ExcepcionSemantica(Errores.ERROR_CASTING);
    }

}
