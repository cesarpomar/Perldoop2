package perldoop.modelo.generacion;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestiona la reserva de palabras durante la generación de código
 *
 * @author César Pomar
 */
public final class GestorReservas {

    public final static String PREF = "pd_";
    private Map<String, Integer> reservas;

    /**
     * Construye el gestor de Reservas
     */
    public GestorReservas() {
        this.reservas = new HashMap<>(100);
    }

    /**
     * Obtiene el alias de una variable
     *
     * @param id Id
     * @return Id alias
     */
    public String getAlias(String id) {
        if (!isReservada(id)) {
            return id;
        }
        id = limpiar(id);
        int n = reservas.getOrDefault(id, 0);
        reservas.put(id, n+1);
        return PREF+id+n;
    }

    /**
     * Obtiene una variable auxiliar
     *
     * @return Variable auxiliar
     */
    public String getAux() {
        return getAlias("");
    }

    /**
     * Limpia una variable eliminado el prefijo de reserva.
     *
     * @param id Id
     * @return Id limpio
     */
    private String limpiar(String id) {
        while (id.startsWith(PREF)) {
            id = id.substring(3);
        }
        return id;
    }

    /**
     * Busca si una palabra esta reservada
     *
     * @param id Identificador
     * @return Esta reservada
     */
    private static boolean isReservada(String id) {
        switch (id) {
            case "abstract":
            case "continue":
            case "for":
            case "new":
            case "switch":
            case "assert":
            case "default":
            case "goto":
            case "package":
            case "synchronized":
            case "boolean":
            case "do":
            case "if":
            case "private":
            case "this":
            case "break":
            case "double":
            case "implements":
            case "protected":
            case "throw":
            case "byte":
            case "else":
            case "import":
            case "public":
            case "throws":
            case "case":
            case "enum":
            case "instanceof":
            case "return":
            case "transient":
            case "catch":
            case "extends":
            case "int":
            case "short":
            case "try":
            case "char":
            case "final":
            case "interface":
            case "static":
            case "void":
            case "class":
            case "finally":
            case "long":
            case "strictfp":
            case "volatile":
            case "const":
            case "float":
            case "native":
            case "super":
            case "while":
                return true;
            default:
                return id.startsWith(PREF);
        }
    }

}
