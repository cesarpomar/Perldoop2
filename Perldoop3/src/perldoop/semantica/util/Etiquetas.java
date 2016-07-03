package perldoop.semantica.util;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase para la gestion semantica de etiquetas
 *
 * @author César Pomar
 */
public class Etiquetas {

    /**
     * Convierte las etiquetas a un tipo
     *
     * @param etiquetas Etiquetas
     * @return Tipo
     */
    public static Tipo parseEtiquetas(List<Token> etiquetas) {
        Tipo tipo = new Tipo();
        for (Token t : etiquetas) {
            switch (t.getValor()) {
                case "<BOOLEAN>":
                    tipo.add(Tipo.BOOLEAN);
                    break;
                case "<INTEGER>":
                    tipo.add(Tipo.INTEGER);
                    break;
                case "<LONG>":
                    tipo.add(Tipo.LONG);
                    break;
                case "<FLOAT>":
                    tipo.add(Tipo.FLOAT);
                    break;
                case "<DOUBLE>":
                    tipo.add(Tipo.DOUBLE);
                    break;
                case "<STRING>":
                    tipo.add(Tipo.STRING);
                    break;
                case "<FILE>":
                    tipo.add(Tipo.FILE);
                    break;
                case "<ARRAY>":
                    tipo.add(Tipo.ARRAY);
                    break;
                case "<LIST>":
                    tipo.add(Tipo.LIST);
                    break;
                case "<HASH>":
                    tipo.add(Tipo.MAP);
                    break;
                case "<REF>":
                    tipo.add(Tipo.REF);
                    break;
            }
        }
        return tipo;
    }

    /**
     * Convierte en tipo en etiquetas
     *
     * @param tipo Tipo
     * @return Etiquetas
     */
    public static List<String> parseTipo(Tipo tipo) {
        List<String> etiquetas = new ArrayList<>(tipo.getTipo().size());
        for (int st : tipo.getTipo()) {
            switch (st) {
                case Tipo.BOOLEAN:
                    etiquetas.add("<BOOLEAN>");
                    break;
                case Tipo.INTEGER:
                    etiquetas.add("<INTEGER>");
                    break;
                case Tipo.LONG:
                    etiquetas.add("<LONG>");
                    break;
                case Tipo.FLOAT:
                    etiquetas.add("<FLOAT>");
                    break;
                case Tipo.DOUBLE:
                    etiquetas.add("<DOUBLE>");
                    break;
                case Tipo.STRING:
                    etiquetas.add("<STRING>");
                    break;
                case Tipo.FILE:
                    etiquetas.add("<FILE>");
                    break;
                case Tipo.ARRAY:
                    etiquetas.add("<ARRAY>");
                    break;
                case Tipo.LIST:
                    etiquetas.add("<LIST>");
                    break;
                case Tipo.MAP:
                    etiquetas.add("<HASH>");
                    break;
                case Tipo.REF:
                    etiquetas.add("<REF>");
                    break;
            }
        }
        return etiquetas;
    }

}
