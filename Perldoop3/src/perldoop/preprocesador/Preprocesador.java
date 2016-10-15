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
            Token token = tokens.get(i);
            switch (estado) {
                case ESTADO_INICIAL:
                    switch (token.getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(i, terminales);
                            break;
                        case Parser.PD_TIPO:
                            tipo = new EtiquetasTipo();
                            tipo.addTipo(token);
                            aceptar(tipo, terminales);
                            break;
                        case Parser.PD_COL:
                            tipo = new EtiquetasTipo();
                            tipo.addTipo(token);
                            estado = ESTADO_COLECCION;
                            break;
                        case Parser.PD_REF:
                            tipo = new EtiquetasTipo();
                            tipo.addTipo(token);
                            estado = ESTADO_REF;
                            break;
                        case Parser.PD_NUM:
                            inicializacion = new EtiquetasInicializacion();
                            inicializacion.addSize(token);
                            estado = ESTADO_INICIALIZACION;
                            break;
                        case Parser.PD_VAR:
                            inicializacion = new EtiquetasInicializacion();
                            predeclaracion = new EtiquetasPredeclaracion();
                            inicializacion.addSize(token);
                            predeclaracion.addVariable(token);
                            estado = ESTADO_VARIABLES;
                            break;
                        case '=':
                            if (inicializacion != null) {
                                terminales.add(terminal(token, inicializacion));
                                break;
                            }
                        case Parser.MY:
                        case Parser.OUR:
                            terminales.add(terminal(token, tipoSentencia));
                            break;
                        case ';':
                            tipoSentencia = null;
                        default:
                            terminales.add(terminal(token));
                    }
                    break;
                case ESTADO_COLECCION:
                    switch (token.getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(i, terminales);
                            break;
                        case Parser.PD_TIPO:
                            tipo.addTipo(token);
                            if (predeclaracion == null) {
                                aceptar(tipo, terminales);
                            } else {
                                aceptar(predeclaracion, terminales);
                            }
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_COL:
                            tipo.addTipo(token);
                            break;
                        case Parser.PD_REF:
                            gestorErrores.error(Errores.REF_ANIDADA, token);
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_NUM:
                        case Parser.PD_VAR:
                            tipo.setSize(token);
                            estado = ESTADO_SIZE;
                            break;
                        default:
                            gestorErrores.error(Errores.ETIQUETAS_COLECCION_INCOMPLETAS, token);
                            estado = ESTADO_INICIAL;
                            i--;
                    }
                    break;
                case ESTADO_SIZE:
                    switch (token.getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(i, terminales);
                            break;
                        case Parser.PD_TIPO:
                            tipo.addTipo(token);
                            if (predeclaracion == null) {
                                aceptar(tipo, terminales);
                            } else {
                                aceptar(predeclaracion, terminales);
                            }
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_COL:
                            tipo.addTipo(token);
                            estado = ESTADO_COLECCION;
                            break;
                        case Parser.PD_REF:
                            gestorErrores.error(Errores.REF_ANIDADA, token);
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_NUM:
                        case Parser.PD_VAR:
                            gestorErrores.error(Errores.DEMASIADAS_ETIQUETAS_SIZE, token);
                            estado = ESTADO_INICIAL;
                            break;
                        default:
                            gestorErrores.error(Errores.ETIQUETAS_COLECCION_INCOMPLETAS, token);
                            estado = ESTADO_INICIAL;
                            i--;
                    }
                    break;
                case ESTADO_VARIABLES:
                    switch (token.getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(i, terminales);
                            break;
                        case Parser.PD_TIPO:
                            tipo = predeclaracion.getTipo();
                            tipo.addTipo(token);
                            aceptar(tipo, terminales);
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_COL:
                            tipo = predeclaracion.getTipo();
                            tipo.addTipo(token);
                            estado = ESTADO_COLECCION;
                            break;
                        case Parser.PD_REF:
                            tipo = predeclaracion.getTipo();
                            tipo.addTipo(token);
                            estado = ESTADO_REF;
                            break;
                        case Parser.PD_NUM:
                            inicializacion.addSize(token);
                            estado = ESTADO_INICIALIZACION;
                            break;
                        case Parser.PD_VAR:
                            predeclaracion.addVariable(token);
                            break;
                        default:
                            aceptar(inicializacion, terminales);
                            estado = ESTADO_INICIAL;
                            i--;
                    }
                    break;
                case ESTADO_INICIALIZACION:
                    switch (token.getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(i, terminales);
                            break;
                        case Parser.PD_TIPO:
                        case Parser.PD_COL:
                        case Parser.PD_REF:
                            gestorErrores.error(Errores.ETIQUETAS_COLECCION_INCOMPLETAS, token);
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_NUM:
                        case Parser.PD_VAR:
                            inicializacion.addSize(token);
                            break;
                        default:
                            aceptar(inicializacion, terminales);
                            estado = ESTADO_INICIAL;
                            i--;
                    }
                    break;
                case ESTADO_REF:
                    switch (token.getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(i, terminales);
                            break;
                        case Parser.PD_TIPO:
                            gestorErrores.error(Errores.REF_TIPO_BASICO, token);
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_COL:
                            tipo.addTipo(token);
                            estado = ESTADO_COLECCION;
                            break;
                        case Parser.PD_REF:
                            gestorErrores.error(Errores.REF_ANIDADA, token);
                            estado = ESTADO_INICIAL;
                            break;
                        case Parser.PD_NUM:
                        case Parser.PD_VAR:
                            gestorErrores.error(Errores.REF_SIZE, token);
                            estado = ESTADO_INICIAL;
                            break;
                        default:
                            gestorErrores.error(Errores.REF_INCOMPLETA, token);
                            estado = ESTADO_INICIAL;
                            i--;
                    }
                    break;
            }
        }
        return terminales;
    }

    /**
     * Crea un terminal de un token
     *
     * @param t Token
     * @return Terminal
     */
    private Terminal terminal(Token t) {
        return new Terminal(t);
    }

    /**
     * Crea un terminal de un token y luego le establece las etiquetas asociadas
     *
     * @param i Posición del token
     * @param etiquetas Etiquetas
     * @return Terminal
     */
    private Terminal terminal(Token t, Etiquetas etiquetas) {
        Terminal tl = terminal(t);
        tl.setEtiquetas(etiquetas);
        return tl;
    }

    /**
     * Crea un terminal de un comentario
     *
     * @param i Posición del token comentario
     * @param terminales Lista de terminales
     */
    private void comentario(int i, List<Terminal> terminales) {
        if (terminales.isEmpty()) {
            terminales.add(terminal(tokens.get(i)));
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
