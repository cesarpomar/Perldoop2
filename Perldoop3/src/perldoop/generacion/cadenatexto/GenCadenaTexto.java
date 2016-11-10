package perldoop.generacion.cadenatexto;

import java.util.Iterator;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.cadena.CadenaQ;
import perldoop.modelo.arbol.cadena.CadenaQR;
import perldoop.modelo.arbol.cadena.CadenaQW;
import perldoop.modelo.arbol.cadena.CadenaSimple;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;
import perldoop.modelo.arbol.regulares.Regulares;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de cadenas
 *
 * @author César Pomar
 */
public class GenCadenaTexto {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenCadenaTexto(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(CadenaTexto s) {
        StringBuilder codigo = new StringBuilder(300);
        if (s.getElementos().isEmpty()) {
            if (!(s.getPadre() instanceof CadenaQW)) {
                codigo.append('"').append('"');
            }
        } else if (s.getPadre() instanceof CadenaQW) {
            String[] splits = analizar((Terminal) s.getElementos().get(0), false, false).toString().split("( )+");
            codigo.append('"');
            codigo.append(String.join("\",\"", splits));
            codigo.append('"');
        } else {
            boolean regex = s.getPadre() instanceof CadenaQR || s.getPadre() instanceof Regulares;
            boolean literal = s.getPadre() instanceof CadenaQ || s.getPadre() instanceof CadenaSimple;
            Iterator<Simbolo> it = s.getElementos().iterator();
            Simbolo inicial = s.getElementos().get(0);
            //Asegurarse de que el operador + sea el de concatenar
            if (!(inicial instanceof Terminal) && !inicial.getTipo().isString()) {
                codigo.append("\"\"+");
            }
            while (it.hasNext()) {
                Simbolo actual = it.next();
                if (actual instanceof Terminal) {
                    codigo.append('"').append(analizar((Terminal) actual, regex, literal)).append('"');
                } else {
                    codigo.append(actual);
                }
                if (it.hasNext()) {
                    codigo.append("+");
                }
            }
        }
        s.setCodigoGenerado(codigo);
    }

    /**
     * Analiza una cadena para adaptar los caracteres escapados a java
     *
     * @param t Terminal a analizar
     * @param regex La cadena es una expresion regular
     * @param literal La cadena es un literal
     * @return Codigo analizado
     */
    private StringBuilder analizar(Terminal t, boolean regex, boolean literal) {
        if (regex) {
            return analizarRegex(t);
        } else if (literal) {
            return analizarLiteral(t);
        } else {
            return analizarEstandar(t);
        }
    }

    /**
     * Analiza una cadena donde ningun caracter debe ser especial
     *
     * @param t Terminal texto
     * @return Cadena analizada
     */
    private StringBuilder analizarLiteral(Terminal t) {
        StringBuilder analizada = new StringBuilder(100);
        for (char c : t.getValor().toCharArray()) {
            if (c == '\\') {
                analizada.append('\\');
            }
            analizada.append(c);
        }
        return analizada;
    }

    /**
     * Analiza una cadena donde los caracteres de expresion regular son especiales
     *
     * @param t Terminal texto
     * @return Cadena analizada
     */
    private StringBuilder analizarRegex(Terminal t) {
        StringBuilder analizada = new StringBuilder(100);
        boolean escape = false;
        for (char c : t.getValor().toCharArray()) {
            if (c == '\\') {
                escape = true;
            } else if (escape) {
                if (isCharRereg(c)) {
                    analizada.append("\\\\");
                } else if (isCharEspecial(c)) {
                    analizada.append("\\");
                }
                analizada.append(c);
                escape = false;
            } else {
                analizada.append(c);
            }
        }
        return analizada;
    }

    /**
     * Analiza una cadena donde los carateres para ser especiales deben ser escapados don '\'
     *
     * @param t Terminal texto
     * @return Cadena analizada
     */
    private StringBuilder analizarEstandar(Terminal t) {
        StringBuilder analizada = new StringBuilder(100);
        boolean escape = false;
        for (char c : t.getValor().toCharArray()) {
            if (c == '\\') {
                escape = true;
            } else if (escape) {
                if (isCharEspecial(c)) {
                    analizada.append("\\");
                }
                analizada.append(c);
                escape = false;
            } else {
                analizada.append(c);
            }
        }
        return analizada;
    }

    /**
     * Comprueba si el caracter tiene un significado especial en una expresion regular
     *
     * @param c Caracter
     * @return Es especial
     */
    private boolean isCharRereg(char c) {
        switch (c) {
            case '^':
            case '$':
            case '.':
            case '*':
            case '+':
            case '?':
            case '|':
            case '(':
            case ')':
            case '[':
            case ']':
            case '{':
            case '}':
                return true;
            default:
                return false;
        }
    }

    /**
     * Comprueba si el caracter tiene un significado especial cuando esta acompañado de '\'
     *
     * @param c Caracter
     * @return Caracter es escapable
     */
    private boolean isCharEspecial(char c) {
        switch (c) {
            case 't':
            case 'b':
            case 'n':
            case 'r':
            case 'f':
            case '\'':
            case '"':
            case '\\':
                return true;
            default:
                return false;
        }
    }

}
