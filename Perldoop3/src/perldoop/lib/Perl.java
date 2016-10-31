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
    public static int print(Object... args) {
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
    public static int print(PerlFile f, Object... args) {
        if(f==null){
            return 0;
        }
        return f.print(args);
    }

}
