package perldoop.interfaz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import perldoop.error.GestorErrores;
import perldoop.generacion.Generador;
import perldoop.internacionalizacion.Errores;
import perldoop.io.CodeReader;
import perldoop.io.CodeWriter;
import perldoop.lexico.Lexer;
import perldoop.modelo.Opciones;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.semantica.Paquete;
import perldoop.modelo.semantica.TablaSimbolos;
import perldoop.preprocesador.Preprocesador;
import perldoop.semantica.Semantica;
import perldoop.traductor.Traductor;
import perldoop.sintactico.Parser;

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
        GestorErrores gestorErrores;
        Opciones opciones;
        CodeWriter writer;
        Consola consola = new Consola(args);
        Map<String, Paquete> paquetes = new HashMap<>();
        consola.parse();
        opciones = consola.getOpciones();
        writer = new CodeWriter(opciones.getDirectorioSalida());
        for (String ruta : consola.getFicheros()) {
            gestorErrores = new GestorErrores(ruta);
            File fichero = new File(ruta);
            //Lexico
            List<Token> tokens;
            try (CodeReader codeReader = new CodeReader(fichero)) {
                gestorErrores.setCodigo(codeReader.getCodigo());
                Lexer lexer = new Lexer(codeReader, opciones, gestorErrores);
                tokens = lexer.getTokens();
                if (lexer.getErrores() > 0) {
                    gestorErrores.error(Errores.FALLOS_LEXICOS, lexer.getErrores());
                    continue;
                }
            } catch (FileNotFoundException ex) {
                gestorErrores.error(Errores.FICHERO_NO_EXISTE);
                continue;

            } catch (IOException ex) {
                gestorErrores.error(Errores.ERROR_LECTURA);
                continue;
            }
            //Preprocesador
            Preprocesador preprocesador = new Preprocesador(tokens, opciones, gestorErrores);
            tokens = preprocesador.procesar();
            if (preprocesador.getErrores() > 0) {
                gestorErrores.error(Errores.FALLOS_PREPROCESADOR, preprocesador.getErrores());
                continue;
            }
            //Sintactico
            List<Simbolo> simbolos;
            Parser parser = new Parser(tokens, opciones, gestorErrores);
            simbolos = parser.parsear();
            if (parser.getErrores() > 0) {
                gestorErrores.error(Errores.FALLOS_SINTACTICOS, parser.getErrores());
                continue;
            }
            //Traductor
            TablaSimbolos tablaSimbolos = new TablaSimbolos(paquetes);
            Semantica semantica = new Semantica(tablaSimbolos, opciones, gestorErrores);
            Generador generador = new Generador(tablaSimbolos, opciones, gestorErrores);
            Traductor traductor = new Traductor(simbolos, semantica, generador, opciones);        
            if (traductor.traducir() > 0) {
                gestorErrores.error(Errores.FALLOS_SEMANTICO, traductor.getErrores());
                continue;
            }
            //Escritura
            try {
                writer.escribir(generador.getClase(), opciones.isFormatearCodigo());
            } catch (IOException ex) {
                gestorErrores.error(Errores.ERROR_ESCRITURA);
            }
        }
    }
}
