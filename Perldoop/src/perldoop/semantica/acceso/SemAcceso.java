package perldoop.semantica.acceso;

import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;
import perldoop.util.ParserEtiquetas;

/**
 * Clase para la semantica de acceso
 *
 * @author César Pomar
 */
public class SemAcceso {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemAcceso(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Lanza un error al intentar acceder a un tipo de manera errones
     *
     * @param encontrado Tipo encontrado
     * @param esperado Tipo esperador
     * @param posicion Posicion del error
     */
    private void errorAcceso(Tipo encontrado, Tipo esperado, Simbolo posicion) {
        tabla.getGestorErrores().error(Errores.ACCESO_ERRONEO, Buscar.tokenInicio(posicion),
                String.join("", ParserEtiquetas.parseTipo(encontrado)),
                String.join("", ParserEtiquetas.parseTipo(esperado)));
        throw new ExcepcionSemantica(Errores.ACCESO_ERRONEO);
    }

    /**
     * Comprueba el acceso a una referencia
     *
     * @param s Simbolo Acceso
     */
    private void checkAccesoColRef(AccesoDesRef s) {
        char c = s.getContexto().getValor().charAt(0);
        Tipo t = s.getExpresion().getTipo();
        if (t.isBox()) {
            Simbolo uso = Buscar.getPadre(s, 1);
            if (uso instanceof Igual && Buscar.isHijo(s, ((Igual) uso).getDerecha())) {
                Igual igual = (Igual) uso;
                Expresion izq = igual.getIzquierda();
                if (!(izq instanceof ExpColeccion) && izq.getTipo() != null && izq.getTipo().isColeccion()) {
                    t = new Tipo(izq.getTipo()).add(0, Tipo.REF);
                }

            }
            if (t.isBox()) {
                tabla.getGestorErrores().error(Errores.UNBOXING_SIN_TIPO, s.getContexto().getToken(), String.join("", ParserEtiquetas.parseTipo(t)));
                throw new ExcepcionSemantica(Errores.ACCESO_NO_COLECCION);
            }
        }
        if (!t.isRef()) {
            tabla.getGestorErrores().error(Errores.ACCESO_NO_REF, s.getContexto().getToken(), String.join("", ParserEtiquetas.parseTipo(t)));
            throw new ExcepcionSemantica(Errores.ACCESO_NO_REF);
        }
        Tipo st = t.getSubtipo(1);
        if (!(Buscar.getUso((Expresion) s.getPadre()) instanceof Acceso)) {
            if (c == '$') {
                tabla.getGestorErrores().error(Errores.ACCESO_REF_ESCALAR, s.getContexto().getToken());
                throw new ExcepcionSemantica(Errores.ACCESO_REF_ESCALAR);
            } else if (c == '@' && !st.isArrayOrList()) {
                errorAcceso(new Tipo(st.getSubtipo(0)), new Tipo(Tipo.ARRAY), s);
            } else if (c == '%' && !st.isMap()) {
                errorAcceso(new Tipo(st.getSubtipo(0)), new Tipo(Tipo.MAP), s);
            }
        }
        s.setTipo(st);
    }

    /**
     * Accede a una o varias posiciones dentro de una expresion de tipo colección
     *
     * @param s Simbolo del Acceso
     * @param t Tipo de la expresion
     * @param coleccion Coleccion de indices para el acceso
     */
    private void accesoColeccion(Acceso s, Tipo t, Coleccion coleccion) {
        //Si hay una referencia y es fruto de un acceso a coleccion la ignoramos
        if (t.isRef() && s.getExpresion() instanceof ExpAcceso) {
            t = t.getSubtipo(1);
        }
        Tipo st = t.getSubtipo(1);
        if (!t.isColeccion()) {
            tabla.getGestorErrores().error(Errores.ACCESO_NO_COLECCION, Buscar.tokenInicio(coleccion), String.join("", ParserEtiquetas.parseTipo(t)));
            throw new ExcepcionSemantica(Errores.ACCESO_NO_COLECCION);
        }
        //Lista de expresiones en la coleccion de acceso
        List<Expresion> lista = Buscar.getExpresiones(coleccion);
        //Errores al acceder con corchetes a un map
        if (coleccion instanceof ColCorchete && !t.isArrayOrList()) {
            errorAcceso(new Tipo(Tipo.MAP), new Tipo(Tipo.ARRAY), coleccion);
        }
        //Errores al acceder con llaves a un array
        if (coleccion instanceof ColLlave && !t.isMap()) {
            if (t.isArray()) {
                errorAcceso(new Tipo(Tipo.ARRAY), new Tipo(Tipo.MAP), coleccion);
            } else {
                errorAcceso(new Tipo(Tipo.LIST), new Tipo(Tipo.MAP), coleccion);
            }
        }
        //Acceder con ningun valor
        if (lista.isEmpty()) {//Acceso sin posiciones
            tabla.getGestorErrores().error(Errores.ACCESO_VACIO_COLECCION, Buscar.tokenInicio(coleccion));
            throw new ExcepcionSemantica(Errores.ACCESO_VACIO_COLECCION);
        }
        Character contexto = Buscar.getContexto(s);
        if (contexto == null) {
            tabla.getGestorErrores().error(Errores.ACCESO_SIN_CONTEXTO, Buscar.tokenInicio(s.getExpresion()));
            throw new ExcepcionSemantica(Errores.ACCESO_SIN_CONTEXTO);
        }
        if (!coleccion.getTipo().isColeccion() || contexto == '$') {
            s.setTipo(st);
            if (st.isColeccion()) {
                st.add(0, Tipo.REF);
            }
        } else if (contexto == '@') {
            if (t.isArrayOrList()) {
                s.setTipo(new Tipo(t));
            } else {
                s.setTipo(t.getSubtipo(1).add(0, Tipo.LIST));
            }
        } else {
            s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
            Simbolo uso = Buscar.getUso((Expresion) s.getPadre());
            if (uso instanceof AccesoCol || uso instanceof AccesoColRef) {
                tabla.getGestorErrores().error(Errores.ACCESO_ANIDADO_PORCENTAJE, Buscar.tokenInicio(s.getExpresion()));
                throw new ExcepcionSemantica(Errores.ACCESO_ANIDADO_PORCENTAJE);
            }
        }
    }

    public void visitar(AccesoCol s) {
        accesoColeccion(s, s.getExpresion().getTipo(), s.getColeccion());
    }

    public void visitar(AccesoColRef s) {
        Tipo t = s.getExpresion().getTipo();
        if (!t.isRef()) {
            tabla.getGestorErrores().error(Errores.ACCESO_NO_REF, s.getFlecha().getToken(), String.join("", ParserEtiquetas.parseTipo(t)));
            throw new ExcepcionSemantica(Errores.ACCESO_NO_REF);
        }
        accesoColeccion(s, s.getExpresion().getTipo().getSubtipo(1), s.getColeccion());
    }

    public void visitar(AccesoDesRef s) {
        checkAccesoColRef(s);
    }

    public void visitar(AccesoRef s) {
        Tipo t = s.getExpresion().getTipo();
        if (!t.isColeccion()) {
            tabla.getGestorErrores().error(Errores.REFERENCIA_NO_COLECCION, s.getBarra().getToken());
            throw new ExcepcionSemantica(Errores.REFERENCIA_NO_COLECCION);
        }
        s.setTipo(new Tipo(t).add(0, Tipo.REF));
    }

}
