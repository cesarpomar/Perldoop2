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
    private final int ESTADO_REF = 5;

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
                        case Parser.PD_REF:
                            tipo = new EtiquetasTipo();
                            tipo.addTipo(tokens.get(i));
                            estado = ESTADO_REF;
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
                                terminales.add(terminal(i, inicializacion));
                                break;
                            }
                        case Parser.MY:
                        case Parser.OUR:
                            terminales.add(terminal(i, tipoSentencia));
                            break;
                        case ';':
                            tipoSentencia = null;
                        default:
                            terminales.add(terminal(i));
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
                        case Parser.PD_REF:
                            tipo.addTipo(tokens.get(i));
                            estado = ESTADO_REF;
                            break;
                        case Parser.PD_NUM:
                        case Parser.PD_VAR:
                            tipo.setSize(tokens.get(i));
                            estado = ESTADO_SIZE;
                            break;
                        default:
                            gestorErrores.error(Errores.ETIQUETAS_COLECCION_INCOMPLETAS, tokens.get(i));
                            estado = ESTADO_INICIAL;
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
                        case Parser.PD_REF:
                            tipo.addTipo(tokens.get(i));
                            estado = ESTADO_REF;
                            break;
                        case Parser.PD_NUM:
                        case Parser.PD_VAR:
                            gestorErrores.error(Errores.DEMASIADAS_ETIQUETAS_SIZE, tokens.get(i));
                            estado = ESTADO_INICIAL;
                            break;
                        default:
                            gestorErrores.error(Errores.ETIQUETAS_COLECCION_INCOMPLETAS, tokens.get(i));
                            estado = ESTADO_INICIAL;
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
                        case Parser.PD_REF:
                            tipo = predeclaracion.getTipo();
                            tipo.addTipo(tokens.get(i));
                            estado = ESTADO_REF;
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
                            terminales.add(terminal(i));
                    }
                    break;
                case ESTADO_INICIALIZACION:
                    switch (tokens.get(i).getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(i, terminales);
                            break;
                        case Parser.PD_TIPO:
                        case Parser.PD_COL:
                        case Parser.PD_REF:
                            gestorErrores.error(Errores.ETIQUETAS_COLECCION_INCOMPLETAS, tokens.get(i));
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_NUM:
                        case Parser.PD_VAR:
                            inicializacion.addSize(tokens.get(i));
                            break;
                        default:
                            aceptar(inicializacion, terminales);
                            terminales.add(terminal(i));
                    }
                    break;
                case ESTADO_REF:
                    switch (tokens.get(i).getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(i, terminales);
                            break;
                        case Parser.PD_TIPO:
                            gestorErrores.error(Errores.REF_TIPO_BASICO, tokens.get(i));
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_COL:
                            tipo.addTipo(tokens.get(i));
                            estado = ESTADO_COLECCION;
                            break;
                        case Parser.PD_REF:
                            gestorErrores.error(Errores.REF_ANIDADA, tokens.get(i));
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_NUM:
                        case Parser.PD_VAR:
                            gestorErrores.error(Errores.REF_SIZE, tokens.get(i));
                            estado = ESTADO_INICIAL;
                            break;
                        default:
                            gestorErrores.error(Errores.REF_INCOMPLETA, tokens.get(i));
                            estado = ESTADO_INICIAL;
                    }
                    break;
            }
        }
        return terminales;
    }

    /**
     * Crea un terminal de un token
     *
     * @param i Posición del token
     * @return Terminal
     */
    private Terminal terminal(int i) {
        return new Terminal(tokens.get(i));
    }

    /**
     * Crea un terminal de un token y luego le establece las etiquetas asociadas
     *
     * @param i Posición del token
     * @param etiquetas Etiquetas
     * @return Terminal
     */
    private Terminal terminal(int i, Etiquetas etiquetas) {
        Terminal t = terminal(i);
        t.setEtiquetas(etiquetas);
        return t;
    }

    /**
     * Crea un terminal de un comentario
     *
     * @param i Posición del token comentario
     * @param terminales Lista de terminales
     */
    private void comentario(int i, List<Terminal> terminales) {
        if (i == 0) {
            terminales.add(terminal(i));
        } else {
            Terminal t = terminales.get(terminales.size() - 1);
            if (t.getTokensComentario() == null) {
                t.setTokensComentario(new ArrayList<>(10));
            }
            t.getTokensComentario().add(tokens.get(i));
        }
    }

    /**
     * Acepta y almacena las etiquetas de inicialización en todos los '=' que estean en su misma linea
     *
     * @param etiqueta Etiqueta de inicialización
     * @param terminales Lista de terminales
     */
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

    /**
     * Acepta y crea un terminal con las etiquetas de predeclaración
     *
     * @param etiqueta Etiquetas de predeclaración
     * @param terminales Lista de terminales
     */
    private void aceptar(EtiquetasPredeclaracion etiqueta, List<Terminal> terminales) {
        Terminal t = new Terminal(etiqueta.getVariables().get(0));
        t.getToken().setTipo(Parser.DECLARACION_TIPO);
        t.setEtiquetas(etiqueta);
        terminales.add(t);
        //No puede haber tamaños como variable en predeclaraciones
        EtiquetasTipo tipo = etiqueta.getTipo();
        for (Token tk : tipo.getTipos()) {
            if (tk.getTipo() == Parser.PD_VAR) {
                gestorErrores.error(Errores.PREDECLARACION_DINAMICA, tk);
            }
        }
    }

    /**
     * Acepta y almacena las etiquetas de tipo en todos los 'my', 'our' y '=' que estean en su misma linea
     *
     * @param etiqueta Etiqueta de tipo
     * @param terminales Lista de terminales
     */
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
