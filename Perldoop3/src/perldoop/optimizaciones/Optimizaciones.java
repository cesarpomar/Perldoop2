package perldoop.optimizaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import perldoop.modelo.Opciones;

/**
 * Optimizaciones que se pueden realizar sobre el codigo final
 *
 * @author César Pomar
 */
public class Optimizaciones {

    private Opciones opciones;

    /**
     * Constructor unico
     *
     * @param opciones Optimizaciones
     */
    public Optimizaciones(Opciones opciones) {
        this.opciones = opciones;
    }

    /**
     * Optimiza el codigo fuente con las opciones selecionadas
     *
     * @param codigo Codigo fuente
     */
    public void optimizar(StringBuilder codigo) {
        if (opciones.isOptDiamante()) {
            notacionDiamante(codigo);
        }
        if (opciones.isOptIntancias()) {
            instancias(codigo);
        }
    }

    /**
     * Obliga el uso de la notacion Diamante en todas las asignaciones
     *
     * @param codigo Codigo fuente
     */
    private void notacionDiamante(StringBuilder codigo) {
        String s = Pattern.compile("(=\\s*[\\w\\d\\s]+<)([^(]*)(>)", 0).matcher(codigo).replaceAll("$1$3");
        codigo.replace(0, codigo.length(), s);
    }

    /**
     * Elimina las referencias que siguen el patron new Ref<...>(...).get()
     *
     * @param codigo Codigo fuente
     */
    private void instancias(StringBuilder codigo) {
        Pattern pattern = Pattern.compile("new Ref<[^(]*>\\(");
        String end = ".get()";
        List<Integer[]> cortes = new ArrayList<>(100);
        Matcher m = pattern.matcher(codigo);
        while (m.find()) {
            int stack = 1;
            int i = m.end();
            //Balanceamos parentesis
            for (; stack != 0 && i < codigo.length(); i++) {
                if (codigo.charAt(i) == '(') {
                    stack++;
                } else if (codigo.charAt(i) == ')') {
                    stack--;
                }
            }
            //Miramos si termina con la expresion
            if (i + end.length() < codigo.length() && codigo.substring(i, i + end.length()).equals(end)) {
                cortes.add(new Integer[]{m.start(), m.end(), i - 1});
            }
        }
        //Eliminados esas partes del codigo
        for (int i = cortes.size() - 1; i >= 0; i--) {
            Integer[] corte = cortes.get(i);
            codigo.delete(corte[2], corte[2] + end.length() + 1).delete(corte[0], corte[1]);
        }

    }

}
