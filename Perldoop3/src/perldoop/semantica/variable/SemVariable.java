package perldoop.semantica.variable;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.Acceso;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.bloque.BloqueForeachVar;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.variable.*;
import perldoop.modelo.preprocesador.EtiquetasTipo;
import perldoop.modelo.semantica.EntradaVariable;
import perldoop.modelo.semantica.Paquete;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;
import perldoop.semantica.util.SemanticaEtiquetas;

/**
 * Clase para la semantica de variable
 *
 * @author César Pomar
 */
public class SemVariable {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemVariable(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(VarExistente s) {
        char contexto = getContexto(s);
        //Buscar entrada
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor(), contexto);
        if (e == null) {
            tabla.getGestorErrores().error(Errores.VARIABLE_NO_EXISTE, s.getVar().getToken(), s.getVar(), contexto);
            throw new ExcepcionSemantica();
        }
        s.setTipo(e.getTipo());
    }

    public void visitar(VarPaquete s) {
        char contexto = getContexto(s);
        //Buscar paquete
        Paquete p = tabla.getTablaSimbolos().getPaquete(s.getPaquetes().getRepresentancion());
        if (p == null) {
            tabla.getGestorErrores().error(Errores.PAQUETE_NO_EXISTE, s.getPaquetes().getIdentificadores().get(0).getToken(),
                    s.getPaquetes().getRepresentancion());
            throw new ExcepcionSemantica();
        }
        //Buscar entrada
        EntradaVariable e = p.buscarVariable(s.getVar().toString(), contexto);
        if (e == null) {
            tabla.getGestorErrores().error(Errores.VARIABLE_NO_EXISTE, s.getVar().getToken(), contexto);
            throw new ExcepcionSemantica();
        }
        s.setTipo(e.getTipo());
    }

    public void visitar(VarMy s) {
        noAccederDeclaracion(s);
        obtenerTipo(s, (EtiquetasTipo) s.getMy().getEtiquetas());
        validarTipo(s);
        boolean confligto = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor()) != null;
        EntradaVariable entrada = new EntradaVariable(s.getVar().getValor(), s.getTipo(), false);
        entrada.setConflicto(confligto);
        tabla.getTablaSimbolos().addVariable(entrada, getContexto(s));
    }

    public void visitar(VarOur s) {
        noAccederDeclaracion(s);
        obtenerTipo(s, (EtiquetasTipo) s.getOur().getEtiquetas());
        validarTipo(s);
        boolean confligto = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor()) != null;
        EntradaVariable entrada = new EntradaVariable(s.getVar().getValor(), s.getTipo(), true);
        entrada.setConflicto(confligto);
        tabla.getTablaSimbolos().addVariable(entrada, getContexto(s));
    }

    /**
     * Busca el contexto de una variable
     *
     * @param v Variable
     */
    private char getContexto(Variable v) {
        Simbolo uso = Buscar.getPadre(v, 1);
        if (uso instanceof AccesoCol) {
            AccesoCol col = (AccesoCol) uso;
            if (col.getColeccion() instanceof ColCorchete) {
                return '@';
            } else if (col.getColeccion() instanceof ColLlave) {
                return '%';
            }
        }
        return v.getContexto().getValor().charAt(0);
    }

    /**
     * No se puede acceder a una variable en su declaracion
     *
     * @param v Variable
     */
    private void noAccederDeclaracion(Variable v) {
        Simbolo uso = Buscar.getPadre(v, 1);
        if (uso instanceof Acceso) {
            tabla.getGestorErrores().error(Errores.ACCESO_DECLARACION, v.getVar().getToken());
            throw new ExcepcionSemantica();
        }
    }

    /**
     * Obtiene el tipo de la variable
     *
     * @param v Variable
     * @param tipoLinea Tipo declarado en la linea
     */
    private void obtenerTipo(Variable v, EtiquetasTipo tipoLinea) {
        Simbolo uso = Buscar.getPadre(v, 1);
        EtiquetasTipo predeclaracion = tabla.getTablaSimbolos().getDeclaracion(getContexto(v) + v.getVar().getValor());
        if (uso instanceof BloqueForeachVar) {
            if (predeclaracion != null) {
                tabla.getGestorErrores().error(Errores.AVISO, Errores.TIPO_FOREACH, v.getVar().getToken());
            }
            BloqueForeachVar foreach = (BloqueForeachVar) uso;
            if (foreach.getLista().getTipo() == null) {
                tabla.getAcciones().reAnalizarDespuesDe(foreach.getLista());
            } else {
                v.setTipo(new Tipo(foreach.getLista().getTipo()));
            }

        } else if (predeclaracion != null) {
            v.setTipo(SemanticaEtiquetas.parseTipo(predeclaracion.getTipos()));
        } else if (tipoLinea != null) {
            v.setTipo(SemanticaEtiquetas.parseTipo(tipoLinea.getTipos()));
        } else {
            tabla.getGestorErrores().error(Errores.VARIABLE_SIN_TIPO, v.getVar().getToken(), v.getVar());
            throw new ExcepcionSemantica();
        }
    }

    /**
     * Hay que comprobar que las etiquetas se usen en los contextos adecuados
     *
     * @param v Variable
     */
    private void validarTipo(Variable v) {
        char contexto = getContexto(v);
        Tipo t = v.getTipo();
        switch (contexto) {
            case '$':
                if (t.isArrayOrList() || t.isMap()) {
                    tabla.getGestorErrores().error(Errores.TIPO_INCORRECTO, v.getVar().getToken(),
                            contexto, v.getVar(), SemanticaEtiquetas.parseTipo(t).get(0));
                }
                break;
            case '@':
                if (!t.isArrayOrList()) {
                    tabla.getGestorErrores().error(Errores.TIPO_INCORRECTO, v.getVar().getToken(),
                            contexto, v.getVar(), SemanticaEtiquetas.parseTipo(t).get(0));
                }
                break;
            case '%':
                if (!t.isMap()) {
                    tabla.getGestorErrores().error(Errores.TIPO_INCORRECTO, v.getVar().getToken(),
                            contexto, v.getVar(), SemanticaEtiquetas.parseTipo(t).get(0));
                }
                break;
        }
    }

}
