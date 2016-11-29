package perldoop.generacion.bloque.especial;

import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de bloque
 *
 * @author César Pomar
 */
public final class GenEspMain extends GenEspecial {

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenEspMain(TablaGenerador tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Bloque s) {
        StringBuilder codigo = new StringBuilder(300);
        codigo.append("public static void main(String[] __)").append(s.getLlaveI());
        codigo.append("Pd.ARGV=__;");
        codigo.append(s.getCuerpo());
        codigo.append(s.getLlaveD());
        tabla.getClase().getFunciones().add(codigo);
        s.setCodigoGenerado(new StringBuilder(0));
    }

}
