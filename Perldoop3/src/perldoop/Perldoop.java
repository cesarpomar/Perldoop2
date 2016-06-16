package perldoop;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import perldoop.error.GestorErrores;
import perldoop.generador.Generador;
import perldoop.io.CodigoReader;
import perldoop.lexico.Lexer;
import perldoop.modelo.Opciones;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.semantico.Paquete;
import perldoop.preprocesador.Preprocesador;
import perldoop.semantico.AnalizadorSemantico;
import perldoop.sintactico.Parser;

/**
 *
 * @author César
 */
public class Perldoop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fichero = "D:\\test.pl";
        Map<String,Paquete> paquetes = new HashMap<>();
        Opciones opciones = new Opciones();
        //Gestor de Errores
        GestorErrores gestorErrores;
        //Lexico
        List<Token> tokens;
        try (CodigoReader cr = new CodigoReader(fichero)) {
            gestorErrores = new GestorErrores(fichero, cr.getCodigo());
            Lexer lex = new Lexer(cr, opciones, gestorErrores);
            tokens = lex.getTokens();
            lex.yyclose();
            if (lex.getErrores() > 0) {
                System.err.println("Analisi lexico fallido, errores: " + lex.getErrores());
                return;
            }
        } catch (IOException ex) {
            Logger.getLogger(Perldoop.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        //Preprocesador
        Preprocesador prep = new Preprocesador(tokens, opciones, gestorErrores);
        prep.procesar();
        if (prep.getErrores() > 0) {
            System.err.println("Procesado de etiquetas fallido, errores: " + prep.getErrores());
            return;
        }
        //Sintactico
        Parser parser = new Parser(tokens, opciones, gestorErrores);
        List<Simbolo> simbolos = parser.parsear();
        if (parser.getErrores() > 0) {
            System.err.println("Analisis sintactico fallido, errores: " + parser.getErrores());
            return;
        }
        System.exit(0);
        //Semantico
        AnalizadorSemantico as = new AnalizadorSemantico(simbolos, opciones, gestorErrores, paquetes);
        as.analizar();
        if (as.getErrores() > 0) {
            System.err.println("Analisis semantico fallido, errores: " + parser.getErrores());
            return;
        }
        //Generador
        Generador gen = new Generador(simbolos);
        //gen.generar();
    }

}
