package perldoop.util;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase para la gestion de etiquetas
 *
 * @author César Pomar
 */
public final class ParserEtiquetas {

    /**
     * Convierte las etiquetas a un tipo
     *
     * @param etiquetas ParserEtiquetas
     * @return Tipo
     */
    public static Tipo parseTipo(List<Token> etiquetas) {
        Tipo tipo = new Tipo();
        for (int i = 0; i < etiquetas.size(); i++) {
            switch (etiquetas.get(i).getValor()) {
                case "<array>":
                    tipo.add(Tipo.ARRAY);
                    break;
                case "<list>":
                    tipo.add(Tipo.LIST);
                    break;
                case "<hash>":
                case "<map>":
                    tipo.add(Tipo.MAP);
                    break;
                case "<ref>":
                    tipo.add(Tipo.REF);
                    break;
                case "<boolean>":
                    tipo.add(Tipo.BOOLEAN);
                    break;
                case "<integer>":
                    tipo.add(Tipo.INTEGER);
                    break;
                case "<long>":
                    tipo.add(Tipo.LONG);
                    break;
                case "<float>":
                    tipo.add(Tipo.FLOAT);
                    break;
                case "<double>":
                    tipo.add(Tipo.DOUBLE);
                    break;
                case "<string>":
                    tipo.add(Tipo.STRING);
                    break;
                case "<file>":
                    tipo.add(Tipo.FILE);
                    break;
                case "<box>":
                    tipo.add(Tipo.BOX);
                    break;
                case "<number>":
                    tipo.add(Tipo.NUMBER);
                    break;
            }
        }
        return tipo;
    }

    /**
     * Convierte un tipo a sus etiquetas
     *
     * @param tipo Tipo
     * @return Lista de etiquetas
     */
    public static List<String> parseTipo(Tipo tipo) {
        List<String> etiquetas = new ArrayList<>(tipo.getTipo().size());
        for (int st : tipo.getTipo()) {
            switch (st) {
                case Tipo.BOOLEAN:
                    etiquetas.add("<boolean>");
                    break;
                case Tipo.INTEGER:
                    etiquetas.add("<integer>");
                    break;
                case Tipo.LONG:
                    etiquetas.add("<long>");
                    break;
                case Tipo.FLOAT:
                    etiquetas.add("<float>");
                    break;
                case Tipo.DOUBLE:
                    etiquetas.add("<double>");
                    break;
                case Tipo.STRING:
                    etiquetas.add("<string>");
                    break;
                case Tipo.FILE:
                    etiquetas.add("<file>");
                    break;
                case Tipo.BOX:
                    etiquetas.add("<box>");
                    break;
                case Tipo.NUMBER:
                    etiquetas.add("<number>");
                    break;
                case Tipo.ARRAY:
                    etiquetas.add("<array>");
                    break;
                case Tipo.LIST:
                    etiquetas.add("<list>");
                    break;
                case Tipo.MAP:
                    etiquetas.add("<hash>");
                    break;
                case Tipo.REF:
                    etiquetas.add("<ref>");
                    break;
            }
        }
        return etiquetas;
    }

}
