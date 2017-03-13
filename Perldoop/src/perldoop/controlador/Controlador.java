package perldoop.controlador;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.tools.ant.DirectoryScanner;
import perldoop.depurador.Depurador;
import perldoop.error.GestorErrores;
import perldoop.generacion.Generador;
import perldoop.internacionalizacion.Errores;
import perldoop.io.CodeReader;
import perldoop.io.CodeWriter;
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
 * Controlador de Perldoop
 *
 * @author César Pomar
 */
public final class Controlador {

    private Opciones opciones;
    private List<String> rutas;
    private List<File> ficheros;
    private CodeWriter writer;

    /**
     * Crea el controlador de Perldoop
     *
     * @param opciones Opciones
     * @param rutas Lista de ficheros y Wildcards
     */
    public Controlador(Opciones opciones, List<String> rutas) {
        this.opciones = opciones;
        writer = new CodeWriter(opciones);
        this.rutas = rutas;
        ficheros = new ArrayList<>(rutas.size() * 10);
        ficheros();
    }

    /**
     * Inicia el controlador
     */
    public void iniciar() {
        LinkedList<Traductor> traductores = new LinkedList<>();
        ArbolPaquetes paquetes = new ArbolPaquetes(ficheros, opciones.getPaquetes());
        for (File fichero : ficheros) {
            GestorErrores gestorErrores = new GestorErrores(fichero.getPath(), opciones);
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
                return;//La codificacion fallaría en todos
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
            /*----------------------------Traductor 1ª Parte------------------------------*/
            TablaSimbolos tablaSimbolos = new TablaSimbolos(paquetes, opciones);
            Semantica semantica = new Semantica(tablaSimbolos, opciones, gestorErrores);
            Generador generador = new Generador(tablaSimbolos, opciones, fichero.getPath());
            Traductor traductor = new Traductor(simbolos, semantica, generador, opciones);
            traductor.traducir();
            traductores.add(traductor);
        }
        boolean interbloqueo = false;
        while (!traductores.isEmpty()) {
            Iterator<Traductor> it = traductores.iterator();
            boolean flag = true;
            while (it.hasNext()) {
                /*----------------------------Traductor 2ª Parte------------------------------*/
                Traductor traductor = it.next();
                if (!traductor.isFinalizado()) {
                    traductor.setReanudable(!interbloqueo);
                    int progreso = traductor.getProgreso();
                    traductor.traducir();
                    flag &= progreso == traductor.getProgreso();
                }
                if (traductor.isFinalizado()) {
                    it.remove();
                    if (opciones.isDepTraduccion()) {
                        List<Simbolo> simbolos = traductor.getSimbolos();
                        Depurador.arbol(simbolos.get(simbolos.size() - 1), true);
                    }
                    GestorErrores gestorErrores = traductor.getSemantica().getGestorErrores();
                    if (gestorErrores.getErrores() > 0) {
                        gestorErrores.error(Errores.FALLOS_SEMANTICO, gestorErrores.getErrores());
                        continue;
                    }
                    /*--------------------------------Escritura---------------------------------*/
                    try {
                        writer.escribir(traductor.getGenerador().getClase(), gestorErrores);
                    } catch (IOException ex) {
                        gestorErrores.error(Errores.ERROR_ESCRITURA);
                    }
                }
            }
            interbloqueo = flag;
        }
    }

    /**
     * Obtiene y verifica los ficheros
     */
    private void ficheros() {
        DirectoryScanner ds = new DirectoryScanner();
        FICHEROS:
        for (String ruta : rutas) {
            List<String> path = new ArrayList<>();
            File dir = new File(".");
            File file = new File(ruta);
            File aux = file;
            //Separamos el fichero en rutas con independencia de la plataforma
            while (aux != null) {
                String name = aux.getName();
                if (name.equals("")) {//Si es absoluta termina con el root
                    Path p = aux.toPath();
                    if (p.equals(p.getRoot())) {
                        dir = p.getRoot().toFile();
                        break;
                    }
                }
                path.add(name);
                aux = aux.getParentFile();
            }
            Collections.reverse(path);
            //La ruta relativa se ajusta al directorio superior
            int n = 0;
            for (String name : path) {
                if (!name.equals("..")) {
                    break;
                }
                dir = dir.getAbsoluteFile().getParentFile();
                if (dir == null) {
                    continue FICHEROS;
                }
                n++;
            }
            file = new File(String.join("/", path.subList(n, path.size())));
            ds.setBasedir(dir);
            ds.setIncludes(new String[]{file.toString()});
            ds.scan();

            Arrays.stream(ds.getIncludedFiles()).map(p -> new File(ds.getBasedir(),p).toPath().normalize().toFile()).forEach(f -> ficheros.add(f));
        }
        if (ficheros.isEmpty()) {
            System.err.println(new Errores().get(Errores.NO_FICHEROS));
            System.exit(0);
        }
    }

}
