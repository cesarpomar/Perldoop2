package perldoop.interfaz;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import perldoop.Test;
import perldoop.depurador.Depurador;
import perldoop.error.GestorErrores;
import perldoop.generacion.Generador;
import perldoop.internacionalizacion.Errores;
import perldoop.io.CodeReader;
import perldoop.io.CodeWriter;
import perldoop.jar.LibJar;
import perldoop.lexico.Lexer;
import perldoop.modelo.Opciones;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.semantica.ArbolPaquetes;
import perldoop.modelo.semantica.TablaSimbolos;
import perldoop.preprocesador.Preprocesador;
import perldoop.semantica.Semantica;
import perldoop.sintactico.Parser;
import perldoop.traductor.Traductor;

/**
 * Clase para gestionar el funcionamiento de la herramienta
 *
 * @author César Pomar
 */
public final class Perldoop {

    /**
     * Punto de inicio de la aplicacion
     *
     * @param args Argumentos de ejecución
     */
    public static void main(String[] args) {
        Consola consola = new Consola(args);
        consola.parse();
        Opciones opciones = consola.getOpciones();
        CodeWriter writer = new CodeWriter(opciones);
        GestorErrores gestorErrores;
        checkFicheros(consola);
        ArbolPaquetes paquetes = new ArbolPaquetes(consola.getFicheros(), opciones.getPaquetes());
        for (String ruta : consola.getFicheros()) {
            gestorErrores = new GestorErrores(ruta.trim(), opciones);
            File fichero = new File(ruta);
            /*--------------------------------Lexico---------------------------------*/
            List<Token> tokens;
            try (CodeReader codeReader = new CodeReader(fichero, opciones.getCodificacion())) {
                gestorErrores.setCodigo(codeReader.getCodigo());
                Lexer lexer = new Lexer(codeReader, opciones, gestorErrores);
                tokens = lexer.getTokens();
                if (opciones.isDepTokens()) {
                    Depurador.tokens(tokens);
                }
                if (gestorErrores.getErrores() > 0) {
                    gestorErrores.error(Errores.FALLOS_LEXICOS, gestorErrores.getErrores());
                    continue;
                }
                if (opciones.getDepEtapas() == 1) {
                    continue;
                }
            } catch (UnsupportedEncodingException ex) {
                gestorErrores.error(Errores.ERROR_CODIFICACION, opciones.getCodificacion());
                return;//La codificacion fallara en todos
            } catch (IOException ex) {
                gestorErrores.error(Errores.ERROR_LECTURA);
                continue;
            }
            /*--------------------------------Preprocesador---------------------------------*/
            List<Terminal> terminales;
            Preprocesador preprocesador = new Preprocesador(tokens, opciones, gestorErrores);
            terminales = preprocesador.procesar();
            if (opciones.isDepTerminales()) {
                Depurador.terminales(terminales);
            }
            if (gestorErrores.getErrores() > 0) {
                gestorErrores.error(Errores.FALLOS_PREPROCESADOR, gestorErrores.getErrores());
                continue;
            }
            if (opciones.getDepEtapas() == 2) {
                continue;
            }
            /*--------------------------------Sintactico---------------------------------*/
            List<Simbolo> simbolos;
            Parser parser = new Parser(terminales, opciones, gestorErrores);
            simbolos = parser.parsear();
            if (opciones.isDepTree()) {
                Depurador.arbol(simbolos.get(simbolos.size() - 1), false);
            }
            if (gestorErrores.getErrores() > 0) {
                gestorErrores.error(Errores.FALLOS_SINTACTICOS, gestorErrores.getErrores());
                continue;
            }
            if (opciones.getDepEtapas() == 3) {
                continue;
            }
            /*--------------------------------Traductor---------------------------------*/
            TablaSimbolos tablaSimbolos = new TablaSimbolos(paquetes);
            Semantica semantica = new Semantica(tablaSimbolos, opciones, gestorErrores);
            Generador generador = new Generador(tablaSimbolos, opciones, gestorErrores);
            Traductor traductor = new Traductor(simbolos, semantica, generador, opciones);
            traductor.traducir();
            if (opciones.isDepTraduccion()) {
                Depurador.arbol(simbolos.get(simbolos.size() - 1), true);
            }
            if (gestorErrores.getErrores() > 0) {
                gestorErrores.error(Errores.FALLOS_SEMANTICO, gestorErrores.getErrores());
                continue;
            }
            /*--------------------------------Escritura---------------------------------*/
            try {
                writer.escribir(generador.getClase(),gestorErrores);
            } catch (IOException ex) {
                gestorErrores.error(Errores.ERROR_ESCRITURA);
            }
        }
        if (opciones.isLibreria()) {
            try {
                LibJar.buildLib(opciones.getDirectorioSalida());
            } catch (Exception ex) {
                new GestorErrores(opciones.getDirectorioSalida().getPath(), opciones).error(Errores.ERROR_LIBRERIA);
            }
        }
    }

    /**
     * Comprueba que todos los ficheros existen
     *
     * @param consola Consola
     */
    private static void checkFicheros(Consola consola) {
        boolean error = false;
        for (String ruta : consola.getFicheros()) {
            File f = new File(ruta);
            if (!f.exists()) {
                error = true;
                GestorErrores ge = new GestorErrores(ruta, consola.getOpciones());
                ge.error(Errores.FICHERO_NO_EXISTE);
            }
        }
        if (error) {
            System.exit(0);
        }
    }
}
