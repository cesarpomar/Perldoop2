package perldoop.semantica.acceso;

import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.SemanticaEtiquetas;
import perldoop.util.Buscar;

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
        if (t.isColeccion()) {
            List<Expresion> lista = coleccion.getLista().getExpresiones();
            if (coleccion instanceof ColCorchete && !t.isArrayOrList()) {
                tabla.getGestorErrores().error(Errores.ACCESO_ERRONEO, Buscar.tokenInicio(coleccion),
                        SemanticaEtiquetas.parseTipo(new Tipo(Tipo.MAP)),
                        SemanticaEtiquetas.parseTipo(new Tipo(Tipo.ARRAY)));
                throw new ExcepcionSemantica(Errores.ACCESO_ERRONEO);
            } else if (coleccion instanceof ColLlave && !t.isMap()) {
                if (t.isArray()) {
                    tabla.getGestorErrores().error(Errores.ACCESO_ERRONEO, Buscar.tokenInicio(coleccion),
                            SemanticaEtiquetas.parseTipo(new Tipo(Tipo.ARRAY)),
                            SemanticaEtiquetas.parseTipo(new Tipo(Tipo.MAP)));
                } else {
                    tabla.getGestorErrores().error(Errores.ACCESO_ERRONEO, Buscar.tokenInicio(coleccion),
                            SemanticaEtiquetas.parseTipo(new Tipo(Tipo.LIST)),
                            SemanticaEtiquetas.parseTipo(new Tipo(Tipo.MAP)));
                }
                throw new ExcepcionSemantica(Errores.ACCESO_ERRONEO);
            } else if (lista.isEmpty()) {
                tabla.getGestorErrores().error(Errores.ACCESO_VACIO, Buscar.tokenInicio(coleccion));
                throw new ExcepcionSemantica(Errores.ACCESO_VACIO);
            } else if (lista.size() == 1 && !lista.get(0).getTipo().isColeccion()) {
                s.setTipo(st);
                if (st.isColeccion()) {
                    st.add(0, Tipo.REF);
                }
            } else if (t.isArrayOrList()) {
                s.setTipo(new Tipo(t));
            } else {
                s.setTipo(t.getSubtipo(1).add(0, Tipo.LIST));
            }
        } else {
            tabla.getGestorErrores().error(Errores.ACCESO_NO_COLECCION, Buscar.tokenInicio(coleccion), SemanticaEtiquetas.parseTipo(t));
            throw new ExcepcionSemantica(Errores.ACCESO_NO_COLECCION);
        }
    }

    public void visitar(AccesoCol s) {
        accesoColeccion(s, s.getExpresion().getTipo(), s.getColeccion());
    }

    public void visitar(AccesoColRef s) {
        Tipo t = s.getExpresion().getTipo();
        if (!t.isRef()) {
            tabla.getGestorErrores().error(Errores.ACCESO_NO_COLECCION, s.getFlecha().getToken(), SemanticaEtiquetas.parseTipo(t));
            throw new ExcepcionSemantica(Errores.ACCESO_NO_COLECCION);
        }
        accesoColeccion(s, s.getExpresion().getTipo().getSubtipo(1), s.getColeccion());
    }

    public void visitar(AccesoRefEscalar s) {
        tabla.getGestorErrores().error(Errores.ACCESO_REF_ESCALAR, s.getDolar().getToken());
        throw new ExcepcionSemantica(Errores.ACCESO_REF_ESCALAR);
    }

    public void visitar(AccesoRefArray s) {
        Tipo t = s.getExpresion().getTipo();
        Tipo st = t.getSubtipo(1);
        if (t.isBox()) {
            tabla.getGestorErrores().error(Errores.UNBOXING_SIN_TIPO, s.getArroba().getToken(), SemanticaEtiquetas.parseTipo(t));
            throw new ExcepcionSemantica(Errores.ACCESO_NO_COLECCION);
        }
        if (!t.isRef()) {
            tabla.getGestorErrores().error(Errores.ACCESO_NO_COLECCION, s.getArroba().getToken(), SemanticaEtiquetas.parseTipo(t));
            throw new ExcepcionSemantica(Errores.ACCESO_NO_COLECCION);
        }
        if (!st.isArrayOrList()) {
            tabla.getGestorErrores().error(Errores.ACCESO_ERRONEO, s.getArroba().getToken(),
                    SemanticaEtiquetas.parseTipo(new Tipo(Tipo.REF, Tipo.MAP)),
                    SemanticaEtiquetas.parseTipo(new Tipo(Tipo.REF, Tipo.ARRAY)));
            throw new ExcepcionSemantica(Errores.ACCESO_ERRONEO);
        }
        s.setTipo(st);
    }

    public void visitar(AccesoRefMap s) {
        Tipo t = s.getExpresion().getTipo();
        Tipo st = t.getSubtipo(1);
        if (t.isBox()) {
            tabla.getGestorErrores().error(Errores.UNBOXING_SIN_TIPO, s.getPorcentaje().getToken(), SemanticaEtiquetas.parseTipo(t));
            throw new ExcepcionSemantica(Errores.ACCESO_NO_COLECCION);
        }
        if (!t.isRef()) {
            tabla.getGestorErrores().error(Errores.ACCESO_NO_COLECCION, s.getPorcentaje().getToken(), SemanticaEtiquetas.parseTipo(t));
            throw new ExcepcionSemantica(Errores.ACCESO_NO_COLECCION);
        }
        if (!st.isMap()) {
            if (st.isArray()) {
                tabla.getGestorErrores().error(Errores.ACCESO_ERRONEO, s.getPorcentaje().getToken(),
                        SemanticaEtiquetas.parseTipo(new Tipo(Tipo.REF, Tipo.ARRAY)),
                        SemanticaEtiquetas.parseTipo(new Tipo(Tipo.REF, Tipo.MAP)));
            } else {
                tabla.getGestorErrores().error(Errores.ACCESO_ERRONEO, s.getPorcentaje().getToken(),
                        SemanticaEtiquetas.parseTipo(new Tipo(Tipo.REF, Tipo.LIST)),
                        SemanticaEtiquetas.parseTipo(new Tipo(Tipo.REF, Tipo.MAP)));
            }
            throw new ExcepcionSemantica(Errores.ACCESO_ERRONEO);
        }
        s.setTipo(st);
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
