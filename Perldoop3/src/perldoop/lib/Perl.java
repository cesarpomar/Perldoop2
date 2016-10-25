package perldoop.lib;

/**
 * TEMPORAL hasta definir clases de funciones auxiliares
 *
 * @author César Pomar
 */
public final class Perl {

    /**
     * Imprime por pantalla
     *
     * @param args Argumentos
     * @return Retorna 1 si tiene exito
     */
    public int print(Object... args) {
        for (Object arg : args) {
            System.out.print(arg);
        }
        return 1;
    }

    /**
     * Imprime en un fichero
     *
     * @param f Fichero
     * @param args Argumentos
     * @return Retorna 1 si tiene exito
     */
    public int print(PerlFile f, Object... args) {
        return f.print(args);
    }

    public static void eval(Object exp) {
        //No tiene que hacer nada
    }

}
