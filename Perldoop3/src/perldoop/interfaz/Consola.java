package perldoop.interfaz;

import java.util.Map;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.action.HelpArgumentAction;
import net.sourceforge.argparse4j.impl.action.StoreTrueArgumentAction;
import net.sourceforge.argparse4j.impl.action.VersionArgumentAction;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentGroup;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.internal.HelpScreenException;
import perldoop.internacionalizacion.Interfaz;
import perldoop.modelo.Opciones;

/**
 * Interfaz de consola y parsing de argumentnos
 *
 * @author César Pomar
 */
public final class Consola {

    private Interfaz interfaz;
    private ArgumentParser parser;
    private String[] args;
    private Namespace comandos;
    private Opciones opciones;

    /**
     * Construye la interfaz de consola
     *
     * @param args Argumentos
     */
    public Consola(String[] args) {
        interfaz = new Interfaz();
        interfaz();
        this.args = args;
    }

    /**
     * Analiza los argumentos
     */
    public void parse() {
        try {
            comandos = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
    }

    /**
     * Obtiene las opciones
     *
     * @return Opciones
     */
    public Opciones getOpciones() {
        return null;
    }

    /**
     * Inicializa la interfaz
     */
    private void interfaz() {
        parser = ArgumentParsers.newArgumentParser(interfaz.get(Interfaz.APP_NOMBRE), false);
        parser.description(interfaz.get(Interfaz.APP_DESCRIPCION));
        parser.version(interfaz.get(Interfaz.APP_VERSION));
        //Posicionales
        ArgumentGroup posicinales = parser.addArgumentGroup(interfaz.get(Interfaz.ARGS_POSICIONAL));
        posicinales.addArgument("files").metavar("infile").help(interfaz.get(Interfaz.INFILE));
        //Opcionales
        ArgumentGroup opcionales = parser.addArgumentGroup(interfaz.get(Interfaz.ARGS_OPCIONAL));
        opcionales.addArgument("-h", "--help").action(new HelpArgumentActionExt()).help(interfaz.get(Interfaz.AYUDA));
        opcionales.addArgument("-v", "--version").action(new VersionArgumentAction()).help(interfaz.get(Interfaz.VERSION));
        //Depuracion
        ArgumentGroup depuracion = parser.addArgumentGroup(interfaz.get(Interfaz.ARGS_DEPURACION));
    }

    /**
     * Clase para redefinir la escritura de la ayuda para solucionar los problemas con caracteres especiales.
     */
    public static class HelpArgumentActionExt extends HelpArgumentAction {

        @Override
        public void run(ArgumentParser parser, Argument arg, Map<String, Object> attrs, String flag, Object value)
                throws ArgumentParserException {
            System.out.print(parser.formatHelp());
            System.exit(0);
        }

    }

}
