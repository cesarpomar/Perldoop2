package perldoop.preprocesador;

import java.util.ArrayList;
import java.util.List;
import perldoop.error.GestorErrores;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.Opciones;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.Etiquetas;
import perldoop.modelo.preprocesador.EtiquetasInicializacion;
import perldoop.modelo.preprocesador.EtiquetasPredeclaracion;
import perldoop.modelo.preprocesador.EtiquetasTipo;
import perldoop.sintactico.Parser;

/**
 * Clase para preprocesar y eliminar los tokens referentes a etiquetas del codigo fuente
 *
 * @author César Pomar
 */
public final class Preprocesador {

    private final int ESTADO_INICIAL = 0;
    private final int ESTADO_COLECCION = 1;
    private final int ESTADO_SIZE = 2;
    private final int ESTADO_INICIALIZACION = 3;
    private final int ESTADO_VARIABLES = 4;

    private List<Token> tokens;
    private Opciones opciones;
    private GestorErrores gestorErrores;
    private int errores;

    /**
     * Crea un preprocesador
     *
     * @param tokens Lista de tokens a analizar
     * @param opciones Opciones
     * @param gestorErrores Gestor de errores
     */
    public Preprocesador(List<Token> tokens, Opciones opciones, GestorErrores gestorErrores) {
        this.tokens = tokens;
        this.opciones = opciones;
        this.gestorErrores = gestorErrores;
    }

    /**
     * Establece el gestor de errores
     *
     * @param gestorErrores Gestor de errores
     */
    public void setGestorErrores(GestorErrores gestorErrores) {
        this.gestorErrores = gestorErrores;
    }

    /**
     * Obtiene el gestor de errores
     *
     * @return Gestor de errores
     */
    public GestorErrores getGestorErrores() {
        return gestorErrores;
    }

    /**
     * Obtiene las opciones
     *
     * @return Opciones
     */
    public Opciones getOpciones() {
        return opciones;
    }

    /**
     * Establece las opciones
     *
     * @param opciones Opciones
     */
    public void setOpciones(Opciones opciones) {
        this.opciones = opciones;
    }

    /**
     * Obtiene el número de errores, si no hay errores el analisis se ha realizado correctamente.
     *
     * @return Número de errores
     */
    public int getErrores() {
        return errores;
    }

    /**
     * Inicia el procesado de tokens, el analizador procesa las etiquetas en la lista de tokens y luego los transforma
     * en terminales
     *
     * @return Lista de terminales
     */
    public List<Terminal> procesar() {
        List<Terminal> terminales = new ArrayList<>(tokens.size());
        EtiquetasInicializacion inicializacion = null;
        EtiquetasPredeclaracion predeclaracion = null;
        EtiquetasTipo tipo = null;
        EtiquetasTipo tipoSentencia = null;
        int estado = 0;
        for (int i = 0; i < tokens.size(); i++) {
            switch (estado) {
                case ESTADO_INICIAL:
                    switch (tokens.get(i).getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(i, terminales);
                            break;
                        case Parser.PD_TIPO:
                            tipo = new EtiquetasTipo();
                            tipo.addTipo(tokens.get(i));
                            aceptar(tipo, terminales);
                            break;
                        case Parser.PD_COL:
                            tipo = new EtiquetasTipo();
                            tipo.addTipo(tokens.get(i));
                            estado = ESTADO_COLECCION;
                            break;
                        case Parser.PD_NUM:
                            inicializacion = new EtiquetasInicializacion();
                            inicializacion.addSize(tokens.get(i));
                            estado = ESTADO_INICIALIZACION;
                            break;
                        case Parser.PD_VAR:
                            inicializacion = new EtiquetasInicializacion();
                            predeclaracion = new EtiquetasPredeclaracion();
                            inicializacion.addSize(tokens.get(i));
                            predeclaracion.addVariable(tokens.get(i));
                            estado = ESTADO_VARIABLES;
                            break;
                        case '=':
                            if (inicializacion != null) {
                                terminales.add(setEtiqueta(i, inicializacion));
                                break;
                            }
                        case Parser.MY:
                        case Parser.OUR:
                            terminales.add(setEtiqueta(i, tipoSentencia));
                            break;
                        case ';':
                            tipoSentencia = null;
                        default:
                            terminales.add(perl(i));
                    }
                    break;
                case ESTADO_COLECCION:
                    switch (tokens.get(i).getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(i, terminales);
                            break;
                        case Parser.PD_TIPO:
                            tipo.addTipo(tokens.get(i));
                            if (predeclaracion == null) {
                                aceptar(tipo, terminales);
                            } else {
                                aceptar(predeclaracion, terminales);
                            }
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_COL:
                            tipo.addTipo(tokens.get(i));
                            break;
                        case Parser.PD_NUM:
                        case Parser.PD_VAR:
                            tipo.setSize(tokens.get(i));
                            estado = ESTADO_SIZE;
                            break;
                        default:
                        //Error sintaxis etiqueta
                    }
                    break;
                case ESTADO_SIZE:
                    switch (tokens.get(i).getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(i, terminales);
                            break;
                        case Parser.PD_TIPO:
                            tipo.addTipo(tokens.get(i));
                            if (predeclaracion == null) {
                                aceptar(tipo, terminales);
                            } else {
                                aceptar(predeclaracion, terminales);
                            }
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_COL:
                            tipo.addTipo(tokens.get(i));
                            estado = ESTADO_COLECCION;
                            break;
                        case Parser.PD_NUM:
                        case Parser.PD_VAR:
                            //Error sintaxis etiqueta
                            break;
                        default:
                        //Error sintaxis etiqueta
                    }
                    break;
                case ESTADO_VARIABLES:
                    switch (tokens.get(i).getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(i, terminales);
                            break;
                        case Parser.PD_TIPO:
                            tipo = predeclaracion.getTipo();
                            tipo.addTipo(tokens.get(i));
                            aceptar(tipo, terminales);
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_COL:
                            tipo = predeclaracion.getTipo();
                            tipo.addTipo(tokens.get(i));
                            estado = ESTADO_COLECCION;
                            break;
                        case Parser.PD_NUM:
                            inicializacion.addSize(tokens.get(i));
                            estado = ESTADO_INICIALIZACION;
                            break;
                        case Parser.PD_VAR:
                            predeclaracion.addVariable(tokens.get(i));
                            break;
                        default:
                            aceptar(inicializacion, terminales);
                            terminales.add(perl(i));
                    }
                    break;
                case ESTADO_INICIALIZACION:
                    switch (tokens.get(i).getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(i, terminales);
                            break;
                        case Parser.PD_TIPO:
                        case Parser.PD_COL:
                            //Error sintaxis etiqueta
                            break;
                        case Parser.PD_NUM:
                        case Parser.PD_VAR:
                            inicializacion.addSize(tokens.get(i));
                            break;
                        default:
                            aceptar(inicializacion, terminales);
                            terminales.add(perl(i));
                    }
                    break;
            }
        }
        return terminales;
    }

    private Terminal perl(int i) {
        return new Terminal(tokens.get(i));
    }

    private Terminal setEtiqueta(int i, Etiquetas etiquetas) {
        Terminal t = perl(i);
        t.setEtiquetas(etiquetas);
        return t;
    }

    private void comentario(int i, List<Terminal> terminales) {
        if (i == 0) {
            terminales.add(perl(i));
        } else {
            Terminal t = terminales.get(terminales.size() - 1);
            if (t.getTokensComentario() == null) {
                t.setTokensComentario(new ArrayList<>(10));
            }
            t.getTokensComentario().add(tokens.get(i));
        }
    }

    private void aceptar(EtiquetasInicializacion etiqueta, List<Terminal> terminales) {
        int linea = etiqueta.getSizes().get(0).getLinea();
        if (!terminales.isEmpty() && linea == terminales.get(terminales.size() - 1).getToken().getLinea()) {
            for (int i = terminales.size() - 1; i > -1; i--) {
                Terminal t = terminales.get(i);
                if (t.getToken().getTipo() == '=') {
                    t.setEtiquetas(etiqueta);
                }
                if (t.getToken().getLinea() != linea) {
                    break;
                }
            }
        }
    }

    private void aceptar(EtiquetasPredeclaracion etiqueta, List<Terminal> terminales) {
        Token t = etiqueta.getVariables().get(0);
        t.setTipo(Parser.DECLARACION_TIPO);
        terminales.add(new Terminal(t));
    }

    private void aceptar(EtiquetasTipo etiqueta, List<Terminal> terminales) {
        int linea = etiqueta.getTipos().get(0).getLinea();
        if (!terminales.isEmpty() && linea == terminales.get(terminales.size() - 1).getToken().getLinea()) {
            for (int i = terminales.size() - 1; i > -1; i--) {
                Terminal t = terminales.get(i);
                switch (t.getToken().getTipo()) {
                    case Parser.MY:
                    case Parser.OUR:
                    case '=':
                        t.setEtiquetas(etiqueta);
                }
                if (t.getToken().getLinea() != linea) {
                    break;
                }
            }
        }
    }

}
