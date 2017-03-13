package perldoop.semantica.asignacion;

import java.util.ArrayList;
import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.Tags;
import perldoop.modelo.preprocesador.TagsInicializacion;
import perldoop.modelo.semantica.EntradaVariable;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.coleccion.SemColeccion;
import perldoop.semantica.util.Tipos;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de igual
 *
 * @author César Pomar
 */
public class SemIgual {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemIgual(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Igual s) {
        Expresion izq = s.getIzquierda();
        Expresion der = s.getDerecha();
        if (izq.getValor() instanceof ColParentesis) {
            multiple(s);
        } else if (der instanceof ExpColeccion && ((ExpColeccion) der).getColeccion().getLista().getExpresiones().isEmpty()) {
            inicializacion(s);
        } else {
            simple(s);
        }
    }

    /**
     * Asignación simple, es decir la asignacion se realiza sobre una sola variable
     *
     * @param s Simbolo de asignacion
     */
    private void simple(Igual s) {
        s.setTipo(s.getIzquierda().getTipo());
        checkAsignacion(s.getIzquierda(), s.getOperador(), s.getDerecha());
    }

    /**
     * Asignación multiple, es decir se asignan multiples variables
     *
     * @param s Simbolo de asignacion
     */
    private void multiple(Igual s) {
        s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
        List<Expresion> variables = Buscar.getExpresiones((ExpColeccion) s.getIzquierda());
        //Todas tienen que ser variables
        for (Expresion var : variables) {
            if (!Buscar.isVariable(var)) {
                tabla.getGestorErrores().error(Errores.MODIFICAR_CONSTANTE, Buscar.tokenInicio(var), s.getOperador().getValor());
                throw new ExcepcionSemantica(Errores.MODIFICAR_CONSTANTE);
            }
        }
        if (s.getDerecha() instanceof ExpColeccion) {//Asignacion directa sin formar una colección
            List<Expresion> valores = Buscar.getExpresiones((ExpColeccion) s.getDerecha());
            if (!valores.stream().anyMatch(i -> i.getTipo().isColeccion())) {//Son todos tipos basicos
                for (int i = 0; i < variables.size() && i < valores.size(); i++) {
                    Expresion var = variables.get(i);
                    if (var.getTipo().isColeccion()) {
                        SemColeccion.comprobarElems(var.getTipo(), valores.subList(i, valores.size()), tabla);
                        break;
                    } else {
                        checkAsignacion(var, s.getOperador(), valores.get(i));
                    }
                }
            }

        } else {
            //Usando un solo origen
            Tipo t = s.getDerecha().getTipo();
            if (t.isColeccion()) {
                Tipo st = t.getSubtipo(1);
                for (Expresion var : variables) {
                    if (!var.getTipo().isColeccion()) {
                        Tipos.casting(s.getDerecha(), st, var.getTipo(), tabla.getGestorErrores());
                    } else {
                        Tipos.casting(s.getDerecha(), t, var.getTipo(), tabla.getGestorErrores());
                        break;
                    }
                }
            } else {
                checkAsignacion(s.getDerecha(), s.getOperador(), variables.get(0));
            }
        }
    }

    /**
     * Asignación para inicializacion colección
     *
     * @param s Simbolo de asignacion
     */
    private void inicializacion(Igual s) {
        checkAsignacion(s.getIzquierda(), s.getOperador(), s.getDerecha());
        Tipo t = s.getIzquierda().getTipo();
        int accesos = Buscar.accesos(s.getIzquierda());
        s.setTipo(new Tipo(t));
        List<Token> sizes = null;
        boolean smart;
        Tags etiquetas = s.getOperador().getEtiquetas();
        //Obtenemos las etiquetas de tamaño
        if (etiquetas == null) {
            sizes = new ArrayList<>();
            smart = false;
        } else {
            sizes = ((TagsInicializacion) etiquetas).getSizes();
            smart = ((TagsInicializacion) etiquetas).getSmart() != null;
            //Por cada acceso a la coleccion eliminamos un tamaño    
            if (accesos < sizes.size()) {
                sizes = sizes.subList(accesos, sizes.size());
            } else {
                sizes = new ArrayList<>();
            }
        }
        //Comprobamos las variables
        int tams = 0;
        for (Token token : sizes) {
            if (token == null) {
                if (tams == 0) {
                    tams = -sizes.size();//Si el primer tamaño es nulo, es como si no huviera ninguno
                }
                continue;//Aunque no se usen comprobamos todos        
            }
            tams++;
            String valor = token.getValor();
            valor = valor.substring(1, valor.length() - 1);
            char c = valor.charAt(0);
            if (c == '$' || c == '@' || c == '%') {
                EntradaVariable entrada = tabla.getTablaSimbolos().buscarVariable(valor.substring(1), c);
                if (entrada == null) {
                    tabla.getGestorErrores().error(Errores.VARIABLE_NO_EXISTE, token, valor, c);
                    throw new ExcepcionSemantica(Errores.VARIABLE_NO_EXISTE);
                }
            }
        }
        if (t.isRef()) {
            t = t.getSubtipo(1);
        }
        if (!t.isColeccion() || !Buscar.isVariable(s.getIzquierda())) {
            tabla.getGestorErrores().error(Errores.INICIALIZACION_SOLO_COLECIONES, s.getOperador().getToken());
            throw new ExcepcionSemantica(Errores.INICIALIZACION_SOLO_COLECIONES);
        }
        if (t.isArray() && tams < 1) {
            tabla.getGestorErrores().error(Errores.ARRAY_INICIALIZACION_TAM, s.getOperador().getToken());
            throw new ExcepcionSemantica(Errores.ARRAY_INICIALIZACION_TAM);
        }
        List<Byte> sl = t.getTipo();
        if (smart && (t.isArray() || !sl.subList(0, sl.size() - 1).stream().allMatch(b -> b.equals(s.getTipo().getTipo(0))))) {
            tabla.getGestorErrores().error(Errores.SMART_INCOMPATIBLE, s.getOperador().getToken());
            throw new ExcepcionSemantica(Errores.SMART_INCOMPATIBLE);
        }
        s.setTipo(s.getIzquierda().getTipo());
    }

    /**
     * Comprueba que la asignación puede realizarse
     *
     * @param izq Expresion izquierda
     * @param operador Operador
     * @param der Expresion derecha
     */
    void checkAsignacion(Simbolo izq, Terminal operador, Simbolo der) {
        if (!Buscar.isVariable(izq)) {
            tabla.getGestorErrores().error(Errores.MODIFICAR_CONSTANTE, Buscar.tokenInicio(izq), operador.getValor());
            throw new ExcepcionSemantica(Errores.MODIFICAR_CONSTANTE);
        }
        Tipo t = izq.getTipo();
        if (t.isColeccion() && !der.getTipo().isColeccion()) {
            t = t.getSubtipo(1);
        }
        Tipos.casting(der, t, tabla.getGestorErrores());
    }

}
