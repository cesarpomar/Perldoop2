package perldoop.interfaz;

import java.io.File;
import java.util.List;
import java.util.Map;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.action.HelpArgumentAction;
import net.sourceforge.argparse4j.impl.action.StoreArgumentAction;
import net.sourceforge.argparse4j.impl.action.StoreFalseArgumentAction;
import net.sourceforge.argparse4j.impl.action.StoreTrueArgumentAction;
import net.sourceforge.argparse4j.impl.action.VersionArgumentAction;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentGroup;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.ArgumentType;
import net.sourceforge.argparse4j.inf.Namespace;
import perldoop.internacionalizacion.Errores;
import perldoop.internacionalizacion.Interfaz;
import perldoop.modelo.Opciones;
import perldoop.modelo.generacion.GestorReservas;

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
        if (opciones == null) {
            opciones();
        }
        return opciones;
    }

    /**
     * Obtiene los ficheros para analizar
     *
     * @return Lista de nombres de ficheros
     */
    public List<String> getFicheros() {
        return comandos.getList("files");
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
        posicinales.addArgument("files").metavar("infile").nargs("+").help(interfaz.get(Interfaz.INFILE));
        //Opcionales
        ArgumentGroup opcionales = parser.addArgumentGroup(interfaz.get(Interfaz.ARGS_OPCIONAL));
        opcionales.addArgument("-h", "--help").action(new HelpArgumentActionExt()).help(interfaz.get(Interfaz.AYUDA));
        opcionales.addArgument("-v", "--version").action(new VersionArgumentAction()).help(interfaz.get(Interfaz.VERSION));
        opcionales.addArgument("-out").metavar("dir").setDefault(new File(".")).action(new StoreArgumentAction()).help(interfaz.get(Interfaz.OUT));
        opcionales.addArgument("-lib").action(new StoreTrueArgumentAction()).help(interfaz.get(Interfaz.LIBRERIA));
        opcionales.addArgument("-nf", "--not-formatting").action(new StoreFalseArgumentAction()).help(interfaz.get(Interfaz.NO_FORMATEAR));
        opcionales.addArgument("-cc", "--copy-comments").action(new StoreTrueArgumentAction()).help(interfaz.get(Interfaz.COPIAR_COMENTARIOS));
        opcionales.addArgument("-hw", "--hide-warnings").action(new StoreTrueArgumentAction()).help(interfaz.get(Interfaz.OCULTAR_AVISOS));
        opcionales.addArgument("-se", "--show-errors").metavar("n").action(new StoreArgumentAction()).type(Integer.class).help(interfaz.get(Interfaz.MOSTRAR_ERRORES));
        opcionales.addArgument("-en", "--encoding").metavar("e").action(new StoreArgumentAction()).help(interfaz.get(Interfaz.CODIFICACION));
        opcionales.addArgument("-pk", "--package").metavar("p").action(new StoreArgumentAction()).type(new ArgumentPaquete()).help(interfaz.get(Interfaz.PAQUETES));
        opcionales.addArgument("-jim", "--java-importer").action(new StoreTrueArgumentAction()).help(interfaz.get(Interfaz.J_IMPORTER));
        //Optimizaciones
        ArgumentGroup optimizacion = parser.addArgumentGroup(interfaz.get(Interfaz.ARGS_OPTIMIZACION));
        optimizacion.addArgument("-on", "--optimize-nulls").action(new StoreTrueArgumentAction()).help(interfaz.get(Interfaz.OPTIMIZAR_NULOS));
        optimizacion.addArgument("-oi", "--optimize-instance").action(new StoreTrueArgumentAction()).help(interfaz.get(Interfaz.OPTIMIZAR_INSTANCIAS));
        optimizacion.addArgument("-ol", "--optimize-diamond").action(new StoreTrueArgumentAction()).help(interfaz.get(Interfaz.OPTIMIZAR_DIAMANTE));
        optimizacion.addArgument("-os", "--optimize-statements").action(new StoreTrueArgumentAction()).help(interfaz.get(Interfaz.OPTIMIZAR_SENTENCIAS));
        optimizacion.addArgument("-om", "--optimize-modulus").action(new StoreTrueArgumentAction()).help(interfaz.get(Interfaz.OPTIMIZAR_SENTENCIAS));
        //Depuracion
        ArgumentGroup depuracion = parser.addArgumentGroup(interfaz.get(Interfaz.ARGS_DEPURACION));
        depuracion.addArgument("-dtk", "--debug-tokens").action(new StoreTrueArgumentAction()).help(interfaz.get(Interfaz.DEPURACION_TOKENS));
        depuracion.addArgument("-dtl", "--debug-terminal").action(new StoreTrueArgumentAction()).help(interfaz.get(Interfaz.DEPURACION_TERMINALES));
        depuracion.addArgument("-dtr", "--debug-tree").action(new StoreTrueArgumentAction()).help(interfaz.get(Interfaz.DEPURACION_ARBOL));
        depuracion.addArgument("-dtn", "--debug-translation").action(new StoreTrueArgumentAction()).help(interfaz.get(Interfaz.DEPURACION_TRADUCCION));
        depuracion.addArgument("-ds", "--debug-stages").metavar("stage").choices(1, 2, 3).type(Integer.class).action(new StoreArgumentAction()).help(interfaz.get(Interfaz.DEPURACION_ESTADOS));
    }

    /**
     * Clase para redefinir la escritura de la ayuda para solucionar los problemas con caracteres especiales.
     */
    private static class HelpArgumentActionExt extends HelpArgumentAction {

        @Override
        public void run(ArgumentParser parser, Argument arg, Map<String, Object> attrs, String flag, Object value)
                throws ArgumentParserException {
            System.out.print(parser.formatHelp());
            System.exit(0);
        }

    }

    /**
     * Clase para comprobar que el argumento de paquetes es sintacticamente correcto
     */
    private static class ArgumentPaquete implements ArgumentType<String[]> {

        @Override
        public String[] convert(ArgumentParser parser, Argument arg, String value) throws ArgumentParserException {
            String[] paquetes = value.split("\\.");
            for (String paquete : paquetes) {
                if (!paquete.matches("[_a-zA-Z][_a-zA-Z0-9]*") || GestorReservas.isReservadaJava(paquete)) {
                    throw new ArgumentParserException(new Errores().get(Errores.PAQUETE_INVALIDO), parser, arg);
                }
            }
            return paquetes;
        }
    }

    /**
     * Carga las opciones
     */
    private void opciones() {
        opciones = new Opciones();
        opciones.setDirectorioSalida(new File(comandos.getString("out")));
        opciones.setLibreria(comandos.getBoolean("lib"));
        opciones.setFormatearCodigo(comandos.getBoolean("not_formatting"));
        opciones.setCopiarComentarios(comandos.getBoolean("copy_comments"));
        opciones.setOcultarAvisos(comandos.getBoolean("hide_warnings"));
        opciones.setMostrarErrores(comandos.getInt("show_errors"));
        opciones.setCodificacion(comandos.getString("encoding"));
        opciones.setPaquetes(comandos.get("package"));
        opciones.setjImporter(comandos.getBoolean("java_importer"));
        opciones.setOptNulos(comandos.getBoolean("optimize_nulls"));
        opciones.setOptIntancias(comandos.getBoolean("optimize_instance"));
        opciones.setOptDiamante(comandos.getBoolean("optimize_diamond"));
        opciones.setOptSentencias(comandos.getBoolean("optimize_statements"));
        opciones.setOptModulo(comandos.getBoolean("optimize_modulus"));
        opciones.setDepTokens(comandos.getBoolean("debug_tokens"));
        opciones.setDepTerminales(comandos.getBoolean("debug_terminal"));
        opciones.setDepTree(comandos.getBoolean("debug_tree"));
        opciones.setDepTraduccion(comandos.getBoolean("debug_translation"));
        Integer stages = comandos.getInt("debug_stages");
        opciones.setDepEtapas(stages != null ? stages : 4);
    }

}
