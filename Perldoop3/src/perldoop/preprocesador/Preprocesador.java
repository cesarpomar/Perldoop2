package perldoop.preprocesador;

import java.util.ArrayList;
import java.util.List;
import perldoop.error.GestorErrores;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.Opciones;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.TagsInicializacion;
import perldoop.modelo.preprocesador.TagsPredeclaracion;
import perldoop.modelo.preprocesador.TagsTipo;
import perldoop.sintactico.Parser;
import perldoop.modelo.preprocesador.Tags;
import perldoop.modelo.preprocesador.TagsComportamiento;
import perldoop.modelo.preprocesador.hadoop.TagsHadoop;

/**
 * Clase para preprocesar y eliminar los tokens referentes a etiquetas del codigo fuente
 *
 * @author César Pomar
 */
public final class Preprocesador {

    //Etiquetetas
    public final static int PD_COL = -10;
    public final static int PD_REF = -11;
    public final static int PD_TIPO = -12;
    public final static int PD_NUM = -13;
    public final static int PD_VAR = -14;
    public final static int PD_MAIN = -15;
    public final static int PD_HADOOP = -16;
    public final static int PD_MAPPER = -17;
    public final static int PD_REDUCCER = -18;
    public final static int PD_COMBINE = -19;
    public final static int PD_REDUCTION = -20;

    //Estados
    private final static int ESTADO_INICIAL = 0;
    private final static int ESTADO_COLECCION = 1;
    private final static int ESTADO_SIZE = 2;
    private final static int ESTADO_INICIALIZACION = 3;
    private final static int ESTADO_VARIABLES = 4;
    private final static int ESTADO_REF = 5;
    //Estados hadoop
    private final static int ESTADO_HADOOP_INICIAL = 100;
    private final static int ESTADO_HADOOP_MAPPER = 101;
    private final static int ESTADO_HADOOP_REDUCCER = 102;

    private List<Token> tokens;
    private Opciones opciones;
    private GestorErrores gestorErrores;
    private int index;

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
        TagsInicializacion inicializacion = null;
        TagsPredeclaracion predeclaracion = null;
        TagsTipo tipo = null;
        TagsComportamiento tcomp = null;
        int estado = 0;
        for (index = 0; index < tokens.size(); index++) {
            Token token = tokens.get(index);
            switch (estado) {
                case ESTADO_INICIAL:
                    switch (token.getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(terminales);
                            break;
                        case PD_TIPO:
                            tipo = new TagsTipo();
                            tipo.addTipo(token);
                            tipo = aceptar(tipo, terminales);
                            break;
                        case PD_COL:
                            tipo = new TagsTipo();
                            tipo.addTipo(token);
                            estado = ESTADO_COLECCION;
                            break;
                        case PD_REF:
                            tipo = new TagsTipo();
                            tipo.addTipo(token);
                            estado = ESTADO_REF;
                            break;
                        case PD_NUM:
                            inicializacion = new TagsInicializacion();
                            inicializacion.addSize(token);
                            estado = ESTADO_INICIALIZACION;
                            break;
                        case PD_VAR:
                            inicializacion = new TagsInicializacion();
                            predeclaracion = new TagsPredeclaracion();
                            inicializacion.addSize(token);
                            predeclaracion.addVariable(token);
                            estado = ESTADO_VARIABLES;
                            break;
                        case PD_MAIN:
                            tcomp = aceptar(tcomp = new TagsComportamiento(token), terminales);
                            break;
                        case PD_HADOOP:
                            tcomp = aceptar(tcomp = new TagsHadoop(token), terminales);
                            procesarHadoop((TagsHadoop) tcomp, terminales);
                            index--;
                            estado = ESTADO_INICIAL;
                            break;
                        case '=':
                            terminales.add(terminal(token, inicializacion));
                            break;
                        case Parser.MY:
                        case Parser.OUR:
                            terminales.add(terminal(token, tipo));
                            break;
                        case ';':
                            tipo = null;
                        case '{':
                            if (tcomp != null && tcomp.getEtiqueta().getLinea() + 1 != token.getLinea()) {
                                tcomp = null;
                            }
                            terminales.add(terminal(token, tcomp));
                            break;
                        default:
                            terminales.add(terminal(token));
                    }
                    break;
                case ESTADO_COLECCION:
                    switch (token.getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(terminales);
                            break;
                        case PD_TIPO:
                            tipo.addTipo(token);
                            if (predeclaracion == null) {
                                tipo = aceptar(tipo, terminales);
                            } else {
                                aceptar(predeclaracion, terminales);
                            }
                            estado = ESTADO_INICIAL;
                            break;
                        case PD_COL:
                            tipo.addTipo(token);
                            break;
                        case PD_REF:
                            gestorErrores.error(Errores.REF_ANIDADA, token);
                            estado = ESTADO_INICIAL;
                            break;
                        case PD_NUM:
                        case PD_VAR:
                            tipo.setSize(token);
                            estado = ESTADO_SIZE;
                            break;
                        default:
                            gestorErrores.error(Errores.ETIQUETAS_COLECCION_INCOMPLETAS, token);
                            estado = ESTADO_INICIAL;
                            index--;
                    }
                    break;
                case ESTADO_SIZE:
                    switch (token.getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(terminales);
                            break;
                        case PD_TIPO:
                            tipo.addTipo(token);
                            if (predeclaracion == null) {
                                tipo = aceptar(tipo, terminales);
                            } else {
                                aceptar(predeclaracion, terminales);
                            }
                            estado = ESTADO_INICIAL;
                            break;
                        case PD_COL:
                            tipo.addTipo(token);
                            estado = ESTADO_COLECCION;
                            break;
                        case PD_REF:
                            gestorErrores.error(Errores.REF_ANIDADA, token);
                            estado = ESTADO_INICIAL;
                            break;
                        case PD_NUM:
                        case PD_VAR:
                            gestorErrores.error(Errores.DEMASIADAS_ETIQUETAS_SIZE, token);
                            estado = ESTADO_INICIAL;
                            break;
                        default:
                            gestorErrores.error(Errores.ETIQUETAS_COLECCION_INCOMPLETAS, token);
                            estado = ESTADO_INICIAL;
                            index--;
                    }
                    break;
                case ESTADO_VARIABLES:
                    switch (token.getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(terminales);
                            break;
                        case PD_TIPO:
                            tipo = predeclaracion.getTipo();
                            tipo.addTipo(token);
                            tipo = aceptar(tipo, terminales);
                            estado = ESTADO_INICIAL;
                            break;
                        case PD_COL:
                            tipo = predeclaracion.getTipo();
                            tipo.addTipo(token);
                            estado = ESTADO_COLECCION;
                            break;
                        case PD_REF:
                            tipo = predeclaracion.getTipo();
                            tipo.addTipo(token);
                            estado = ESTADO_REF;
                            break;
                        case PD_NUM:
                            inicializacion.addSize(token);
                            estado = ESTADO_INICIALIZACION;
                            break;
                        case PD_VAR:
                            predeclaracion.addVariable(token);
                            break;
                        default:
                            inicializacion=aceptar(inicializacion, terminales);
                            estado = ESTADO_INICIAL;
                            index--;
                    }
                    break;
                case ESTADO_INICIALIZACION:
                    switch (token.getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(terminales);
                            break;
                        case PD_TIPO:
                        case PD_COL:
                        case PD_REF:
                            gestorErrores.error(Errores.ETIQUETAS_COLECCION_INCOMPLETAS, token);
                            estado = ESTADO_INICIAL;
                            break;
                        case PD_NUM:
                        case PD_VAR:
                            inicializacion.addSize(token);
                            break;
                        default:
                            inicializacion=aceptar(inicializacion, terminales);
                            estado = ESTADO_INICIAL;
                            index--;
                    }
                    break;
                case ESTADO_REF:
                    switch (token.getTipo()) {
                        case Parser.COMENTARIO:
                            comentario(terminales);
                            break;
                        case PD_TIPO:
                            gestorErrores.error(Errores.REF_TIPO_BASICO, token);
                            estado = ESTADO_INICIAL;
                            break;
                        case PD_COL:
                            tipo.addTipo(token);
                            estado = ESTADO_COLECCION;
                            break;
                        case PD_REF:
                            gestorErrores.error(Errores.REF_ANIDADA, token);
                            estado = ESTADO_INICIAL;
                            break;
                        case PD_NUM:
                        case PD_VAR:
                            gestorErrores.error(Errores.REF_SIZE, token);
                            estado = ESTADO_INICIAL;
                            break;
                        default:
                            gestorErrores.error(Errores.REF_INCOMPLETA, token);
                            estado = ESTADO_INICIAL;
                            index--;
                    }
                    break;
            }
        }
        return terminales;
    }

    /**
     * Procesa las etiquetas de hadoop
     *
     * @param hadoop Etiqueta hadoop
     * @param terminales Terminales
     */
    private void procesarHadoop(TagsHadoop hadoop, List<Terminal> terminales) {
        int estado = ESTADO_HADOOP_INICIAL;
        index++;
        for (; index < tokens.size(); index++) {
            Token token = tokens.get(index);
            switch (estado) {
                case ESTADO_HADOOP_INICIAL:
                    switch (token.getTipo()) {
                        case PD_MAPPER:
                        case PD_REDUCCER:
                        default:
                            return;
                    }
            }
        }
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
     * @param t Token
     * @param etiquetas Etiquetas
     * @return Terminal
     */
    private Terminal terminal(Token t, Tags etiquetas) {
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
    private void comentario(List<Terminal> terminales) {
        if (terminales.isEmpty()) {
            terminales.add(terminal(tokens.get(index)));
        } else {
            Terminal t = terminales.get(terminales.size() - 1);
            if (t.getTokensComentario() == null) {
                t.setTokensComentario(new ArrayList<>(10));
            }
            t.getTokensComentario().add(tokens.get(index));
        }
    }

    /**
     * Acepta y almacena las etiquetas de inicialización en todos los '=' que estean en su misma linea
     *
     * @param etiqueta Etiqueta de inicialización
     * @param terminales Lista de terminales
     * @return Etiqueta de inicialización
     */
    private TagsInicializacion aceptar(TagsInicializacion etiqueta, List<Terminal> terminales) {
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
            return null;
        }
        return etiqueta;
    }

    /**
     * Acepta y crea un terminal con las etiquetas de predeclaración
     *
     * @param etiqueta Etiquetas de predeclaración
     * @param terminales Lista de terminales
     */
    private void aceptar(TagsPredeclaracion etiqueta, List<Terminal> terminales) {
        Terminal t = new Terminal(etiqueta.getVariables().get(0));
        t.getToken().setTipo(Parser.DECLARACION_TIPO);
        t.setEtiquetas(etiqueta);
        terminales.add(t);
        //No puede haber tamaños como variable en predeclaraciones
        TagsTipo tipo = etiqueta.getTipo();
        for (Token tk : tipo.getTipos()) {
            if (tk.getTipo() == PD_VAR) {
                gestorErrores.error(Errores.PREDECLARACION_DINAMICA, tk);
            }
        }
    }

    /**
     * Acepta y almacena las etiquetas de tipo en todos los 'my', 'our' y '=' que estean en su misma linea
     *
     * @param etiqueta Etiqueta de tipo
     * @param terminales Lista de terminales
     * @return Etiquetas tipo
     */
    private TagsTipo aceptar(TagsTipo etiqueta, List<Terminal> terminales) {
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
            return null;
        }
        return etiqueta;
    }

    /**
     * Acepta y almacena las etiquetas de comportamiento en el bloque que estea en su misma linea
     *
     * @param etiqueta Etiqueta de comportamiento
     * @param terminales Lista de terminales
     * @return Etiqueta de comportamiento
     */
    private TagsComportamiento aceptar(TagsComportamiento etiqueta, List<Terminal> terminales) {
        int linea = etiqueta.getEtiqueta().getLinea();
        if (!terminales.isEmpty()) {
            Terminal t = terminales.get(terminales.size() - 1);
            if (linea == t.getToken().getLinea() && t.getToken().getTipo() == '{') {
                t.setEtiquetas(etiqueta);
                return null;
            }
        }
        return etiqueta;
    }
}
