package perldoop.generacion.cadenatexto;

import java.util.Iterator;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.cadena.CadenaQR;
import perldoop.modelo.arbol.cadena.CadenaQW;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;
import perldoop.modelo.arbol.regulares.Regulares;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de constante
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

    /**
     * Analiza una cadena para adaptar los caracteres escapados a java
     *
     * @param t Terminal a analizar
     * @param regex La cadena es una expresion regular
     * @return Codigo adaptado
     */
    private StringBuilder analizar(Terminal t, boolean regex) {
        //TODO
        return t.getCodigoGenerado();
    }

    public void visitar(CadenaTexto s) {
        StringBuilder codigo = new StringBuilder(300);
        if (s.getElementos().isEmpty()) {
            if (!(s.getPadre() instanceof CadenaQW)) {
                codigo.append('"').append('"');
            }
        } else if (s.getPadre() instanceof CadenaQW) {
            String[] splits = analizar((Terminal) s.getElementos().get(0), false).toString().split("( )+");
            codigo.append('"');
            codigo.append(String.join("\",\"", splits));
            codigo.append('"');
        } else {
            boolean regex = s.getPadre() instanceof CadenaQR || s.getPadre() instanceof Regulares;
            Iterator<Simbolo> it = s.getElementos().iterator();
            Simbolo inicial = s.getElementos().get(0);
            //Asegurarse de que el operador + sea el de concatenar
            if (!(inicial instanceof Terminal) && !inicial.getTipo().isString()) {
                codigo.append("\"\"+");
            }
            while (it.hasNext()) {
                Simbolo actual = it.next();
                if (actual instanceof Terminal) {
                    codigo.append('"').append(analizar((Terminal) actual, regex)).append('"');
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

}
