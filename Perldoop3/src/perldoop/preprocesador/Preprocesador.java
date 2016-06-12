package perldoop.preprocesador;

import java.util.ArrayList;
import java.util.List;
import perldoop.error.GestorErrores;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.lexico.Token;
import perldoop.sintactico.Parser;

/**
 * Clase para preprocesar y eliminar los tokens referentes a etiquetas del
 * codigo fuente
 *
 * @author César Pomar
 */
public class Preprocesador {

    private List<Token> tokens;
    private int index;
    private GestorErrores gestorErrores;
    private int errores;

    /**
     * Crea un preprocesador
     *
     * @param tokens Lista de tokens a analizar
     * @param gestorErrores Gestor de errores
     */
    public Preprocesador(List<Token> tokens, GestorErrores gestorErrores) {
        this.tokens = tokens;
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
     * Obtiene el número de errores, si no hay errores el analisis
     * se ha realizado correctamente.
     * @return Número de errores
     */
    public int getErrores() {
        return errores;
    }

    /**
     * Inicia el procesado de tokens, el analizador procesa las etiquetas
     * en la lista de tokens y luego las elimina.
     *
     * @return Lista de token con la que se contruyo la clase.
     */
    public List<Token> procesar() {
        for (index = 0; index < tokens.size(); index++) {
            switch (tokens.get(index).getTipo()) {
                //Comentarios en el texto
                case Parser.COMENTARIO:
                    comentario();
                    break;
                //Todas las etiquetas del tipo
                case Parser.PD_TIPO:
                    tipo();
                    break;
                //Etiqueta que referencia a una variable
                case Parser.PD_VAR:
                    variable();
                    break;
                //Etiqueta que especifica un tamaño
                case Parser.PD_NUM:
                    tamano();
                    break;
                //Etiqueta de argumentos de funcioón
                case Parser.PD_ARGS:
                    argumentos();
                    break;
                //Etiqueta de retornos de función
                case Parser.PD_RETURNS:
                    retornos();
                    break;
            }
        }
        return tokens;
    }

    /**
     * Procesa los tokens comentario
     */
    private void comentario() {
        if (index > 0) {
            Token comentario = tokens.remove(index);
            Token token = tokens.get(--index);
            if (token.getComentarios() == null) {
                token.setComentarios(new ArrayList<>(2));
            }
            token.getComentarios().add(comentario);
        }else{
            tokens.get(index).setEtiqueta(false);
        }
    }

    /**
     * Procesa los tokens de tipo
     */
    private void tipo() {
        Token etiqueta = tokens.get(index);
        boolean usada = false;
        //Al principio de la linea
        if (index == 0 || tokens.get(index - 1).getLinea() != etiqueta.getLinea()) {
            tokens.remove(index--);
            //Tipamos los my y our hasta final de sentencia
            for (int i = index + 1; i < tokens.size(); i++) {
                Token ti = tokens.get(i);
                if (ti.getTipo() != ';') {
                    break;
                }
                switch (ti.getTipo()) {
                    case Parser.MY:
                    case Parser.OUR:
                        usada = true;
                        if (ti.getEtiquetas() == null) {
                            ti.setEtiquetas(new ArrayList<>(2));
                        }
                        ti.getEtiquetas().add(etiqueta);
                        break;
                }
            }
        } else {
            //Vamos hacia atras tipando todos los my, our y <var> de la linea
            tokens.remove(index--);
            for (int i = index; i >= 0; i--) {
                Token ti = tokens.get(i);
                if (ti.getLinea() != etiqueta.getLinea()) {
                    break;
                }
                switch (ti.getTipo()) {
                    case Parser.MY:
                    case Parser.OUR:
                    case Parser.DECLARACION_TIPO:
                        usada = true;
                        if (ti.getEtiquetas() == null) {
                            ti.setEtiquetas(new ArrayList<>(2));
                        }
                        ti.getEtiquetas().add(etiqueta);
                        break;
                }
            }
        }
        if (!usada) {
            gestorErrores.error(Errores.AVISO, Errores.ETIQUETA_NO_USADA, etiqueta, etiqueta.getValor());
        }
    }

    /**
     * Procesa los tokens de variable
     */
    private void variable() {
        Token etiqueta = tokens.get(index);
        //Al principio de la linea
        if (index == 0 || tokens.get(index - 1).getLinea() != etiqueta.getLinea() || tokens.get(index - 1).getTipo() == Parser.DECLARACION_TIPO) {
            etiqueta.setEtiqueta(false);
            etiqueta.setTipo(Parser.DECLARACION_TIPO);
        } else {
            //Tiene el significado de tamaño
            tamano();
        }
    }

    /**
     * Procesa los tokens de tamaño
     */
    private void tamano() {
        Token etiqueta = tokens.get(index);
        boolean usada = false;
        //Al principio de la linea
        if (index == 0 || tokens.get(index - 1).getLinea() != etiqueta.getLinea()) {
            tokens.remove(index--);
            //Guardamos el tamaño en todos los = hasta final de sentencia
            for (int i = index + 1; i < tokens.size(); i++) {
                Token ti = tokens.get(i);
                if (ti.getTipo() != ';') {
                    break;
                }
                if (ti.getTipo() == '=') {
                    if (ti.getEtiquetas() == null) {
                        ti.setEtiquetas(new ArrayList<>(2));
                    }
                    ti.getEtiquetas().add(etiqueta);
                }
            }
        } else {
            //Vamos hacia atras guardando el tamaño en los = de la linea
            tokens.remove(index--);
            for (int i = index; i >= 0; i--) {
                Token ti = tokens.get(i);
                if (ti.getLinea() != etiqueta.getLinea()) {
                    break;
                }
                switch (ti.getTipo()) {
                    case '=':
                    case Parser.DECLARACION_TIPO:
                        usada = true;
                        if (ti.getEtiquetas() == null) {
                            ti.setEtiquetas(new ArrayList<>(2));
                        }
                        ti.getEtiquetas().add(etiqueta);
                        break;
                }
            }
        }
        if (!usada) {
            gestorErrores.error(Errores.AVISO, Errores.ETIQUETA_NO_USADA, etiqueta, etiqueta.getValor());
        }
    }

    /**
     * Procesa los tokens de argumentos
     */
    private void argumentos() {
        Token etiqueta = tokens.remove(index--);
        etiqueta.setEtiquetas(new ArrayList<>(5));
        boolean usada = false;
        int i = index + 1;
        //Absorbemos los tipos
        for (; i < tokens.size(); i++) {
            if (tokens.get(i).getTipo() == Parser.PD_TIPO) {
                etiqueta.getEtiquetas().add(tokens.remove(i--));
            } else {
                break;
            }
        }
        for (; i < tokens.size(); i++) {
            if (!tokens.get(i).isEtiqueta()) {
                if (tokens.get(i).getTipo() == Parser.SUB) {
                    Token sub = tokens.get(i);
                    usada = true;
                    if (sub.getEtiquetas() == null) {
                        sub.setEtiquetas(new ArrayList<>(2));
                    }
                    sub.getEtiquetas().add(etiqueta);
                } else {
                    break;
                }
            }
        }
        if (!usada) {
            gestorErrores.error(Errores.AVISO, Errores.ETIQUETA_NO_USADA, etiqueta, etiqueta.getValor());
        }
    }

    /**
     * Procesa los tokens de retorno
     */
    private void retornos() {
        argumentos();
    }

}
