package perldoop.semantica.util;

import java.util.ArrayList;
import java.util.List;
import perldoop.error.GestorErrores;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase para la gestion semantica de etiquetas
 *
 * @author César Pomar
 */
public final class SemanticaEtiquetas {

    /**
     * Convierte las etiquetas a un tipo
     *
     * @param etiquetas SemanticaEtiquetas
     * @param ge Gestor de errores
     * @return Tipo
     */
    public static Tipo parseTipo(List<Token> etiquetas, GestorErrores ge) {
        Tipo tipo = new Tipo();
        Token t;
        int i;
        FOR:
        for (i = 0; i < etiquetas.size() - 1; i++) {
            t = etiquetas.get(i);
            switch (t.getValor()) {
                case "<array>":
                    tipo.add(Tipo.ARRAY);
                    break;
                case "<list>":
                    tipo.add(Tipo.LIST);
                    break;
                case "<hash>":
                    tipo.add(Tipo.MAP);
                    break;
                case "<ref>":
                    tipo.add(Tipo.REF);
                    break;
                default:
                    i--;
                    break FOR;
            }
        }

        t = etiquetas.get(i++);
        switch (t.getValor()) {
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
                tipo.add(Tipo.BOX);
                break;
            default:
                ge.error(Errores.TIPO_ESCALAR_OMITIDO, t);
                throw new ExcepcionSemantica();
        }
        for (; i < etiquetas.size(); i++) {
            t = etiquetas.get(i);
            ge.error(Errores.AVISO, Errores.ETIQUETA_NO_USADA, t);
        }

        return tipo;
    }

    /**
     * Convierte en tipo en etiquetas
     *
     * @param tipo Tipo
     * @return SemanticaEtiquetas
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
