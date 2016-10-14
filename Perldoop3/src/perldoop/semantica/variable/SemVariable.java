package perldoop.semantica.variable;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.acceso.Acceso;
import perldoop.modelo.arbol.bloque.BloqueForeachVar;
import perldoop.modelo.arbol.variable.*;
import perldoop.modelo.preprocesador.EtiquetasTipo;
import perldoop.modelo.semantica.EntradaVariable;
import perldoop.modelo.semantica.Paquete;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;
import perldoop.util.ParserEtiquetas;

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
        char contexto = Buscar.getContexto(s);
        //Buscar entrada
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor(), contexto);
        if (e == null) {
            tabla.getGestorErrores().error(Errores.VARIABLE_NO_EXISTE, s.getVar().getToken(), s.getVar().getValor(), contexto);
            throw new ExcepcionSemantica(Errores.VARIABLE_NO_EXISTE);
        }
        s.setTipo(e.getTipo());
    }

    public void visitar(VarPaquete s) {
        char contexto = Buscar.getContexto(s);
        //Buscar paquete
        Paquete p = tabla.getTablaSimbolos().getPaquete(s.getPaquetes().getRepresentancion());
        if (p == null) {
            tabla.getGestorErrores().error(Errores.PAQUETE_NO_EXISTE, s.getPaquetes().getIdentificadores().get(0).getToken(),
                    s.getPaquetes().getRepresentancion());
            throw new ExcepcionSemantica(Errores.PAQUETE_NO_EXISTE);
        }
        //Buscar entrada
        EntradaVariable e = p.buscarVariable(s.getVar().toString(), contexto);
        if (e == null) {
            tabla.getGestorErrores().error(Errores.VARIABLE_NO_EXISTE, s.getVar().getToken(), s.getVar().getValor(), contexto);
            throw new ExcepcionSemantica(Errores.VARIABLE_NO_EXISTE);
        }
        s.setTipo(e.getTipo());
    }

    public void visitar(VarSigil s) {
        char contexto = Buscar.getContexto(s);
        //Buscar entrada
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor(), contexto);
        if (e == null) {
            tabla.getGestorErrores().error(Errores.VARIABLE_NO_EXISTE, s.getVar().getToken(), s.getVar().getValor(), contexto);
            throw new ExcepcionSemantica(Errores.VARIABLE_NO_EXISTE);
        }
        s.setTipo(e.getTipo());
        sigil(s, s.getSigil());
    }

    public void visitar(VarPaqueteSigil s) {
        char contexto = Buscar.getContexto(s);
        //Buscar paquete
        Paquete p = tabla.getTablaSimbolos().getPaquete(s.getPaquetes().getRepresentancion());
        if (p == null) {
            tabla.getGestorErrores().error(Errores.PAQUETE_NO_EXISTE, s.getPaquetes().getIdentificadores().get(0).getToken(),
                    s.getPaquetes().getRepresentancion());
            throw new ExcepcionSemantica(Errores.PAQUETE_NO_EXISTE);
        }
        //Buscar entrada
        EntradaVariable e = p.buscarVariable(s.getVar().toString(), contexto);
        if (e == null) {
            tabla.getGestorErrores().error(Errores.VARIABLE_NO_EXISTE, s.getVar().getToken(), s.getVar().getValor(), contexto);
            throw new ExcepcionSemantica(Errores.VARIABLE_NO_EXISTE);
        }
        s.setTipo(e.getTipo());
        sigil(s, s.getSigil());
    }

    public void visitar(VarMy s) {
        noAccederDeclaracion(s);
        if (obtenerTipo(s, (EtiquetasTipo) s.getMy().getEtiquetas())) {
            validarTipo(s);
            boolean confligto = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor()) != null;
            EntradaVariable entrada = new EntradaVariable(s.getVar().getValor(), s.getTipo(), false);
            entrada.setConflicto(confligto);
            tabla.getTablaSimbolos().addVariable(entrada, Buscar.getContexto(s));
        }
    }

    public void visitar(VarOur s) {
        noAccederDeclaracion(s);
        if (obtenerTipo(s, (EtiquetasTipo) s.getOur().getEtiquetas())) {
            validarTipo(s);
            boolean confligto = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor()) != null;
            EntradaVariable entrada = new EntradaVariable(s.getVar().getValor(), s.getTipo(), true);
            entrada.setConflicto(confligto);
            tabla.getTablaSimbolos().addVariable(entrada, Buscar.getContexto(s));
        }
    }

    /**
     * Analiza el sigil de una coleccion
     *
     * @param v Variable
     * @param sigil Sigil
     */
    private void sigil(Variable v, Terminal sigil) {
        Tipo t = v.getTipo();
        if (!t.isColeccion()) {
            tabla.getGestorErrores().error(Errores.VARIABLE_ERROR_SIGIL, sigil.getToken());
            throw new ExcepcionSemantica(Errores.VARIABLE_ERROR_SIGIL);
        }
        v.setTipo(new Tipo(Tipo.INTEGER));
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
            throw new ExcepcionSemantica(Errores.ACCESO_DECLARACION);
        }
    }

    /**
     * Obtiene el tipo de la variable
     *
     * @param v Variable
     * @param tipoLinea Tipo declarado en la linea
     * @return Tipo asignado
     */
    private boolean obtenerTipo(Variable v, EtiquetasTipo tipoLinea) {
        Simbolo uso = Buscar.getPadre(v, 1);
        EtiquetasTipo predeclaracion = tabla.getTablaSimbolos().getDeclaracion(Buscar.getContexto(v) + v.getVar().getValor());
        if (v.getPadre() instanceof BloqueForeachVar) {
            BloqueForeachVar foreach = (BloqueForeachVar) v.getPadre();
            if (foreach.getColeccion().getTipo() == null) {
                tabla.getAcciones().reAnalizarDespuesDe(foreach.getColeccion());
                return false;
            } else {
                v.setTipo(foreach.getColeccion().getTipo().getSubtipo(1));
            }
            if (predeclaracion != null || tipoLinea != null) {
                tabla.getGestorErrores().error(Errores.AVISO, Errores.TIPO_FOREACH, v.getVar().getToken());
            }
        } else if (predeclaracion != null) {
            v.setTipo(ParserEtiquetas.parseTipo(predeclaracion.getTipos()));
        } else if (tipoLinea != null) {
            v.setTipo(ParserEtiquetas.parseTipo(tipoLinea.getTipos()));
        } else {
            tabla.getGestorErrores().error(Errores.VARIABLE_SIN_TIPO, v.getVar().getToken(), v.getContexto().getValor(), v.getVar().getValor());
            throw new ExcepcionSemantica(Errores.VARIABLE_SIN_TIPO);
        }
        return true;
    }

    /**
     * Hay que comprobar que las etiquetas se usen en los contextos adecuados
     *
     * @param v Variable
     */
    private void validarTipo(Variable v) {
        char contexto = Buscar.getContexto(v);
        Tipo t = v.getTipo();
        switch (contexto) {
            case '$':
                if (t.isArrayOrList() || t.isMap()) {
                    tabla.getGestorErrores().error(Errores.TIPO_INCORRECTO, v.getVar().getToken(),
                            contexto, v.getVar().getValor(), ParserEtiquetas.parseTipo(t).get(0));
                    throw new ExcepcionSemantica(Errores.TIPO_INCORRECTO);
                }
                break;
            case '@':
                if (!t.isArrayOrList()) {
                    tabla.getGestorErrores().error(Errores.TIPO_INCORRECTO, v.getVar().getToken(),
                            contexto, v.getVar().getValor(), ParserEtiquetas.parseTipo(t).get(0));
                    throw new ExcepcionSemantica(Errores.TIPO_INCORRECTO);
                }
                break;
            case '%':
                if (!t.isMap()) {
                    tabla.getGestorErrores().error(Errores.TIPO_INCORRECTO, v.getVar().getToken(),
                            contexto, v.getVar().getValor(), ParserEtiquetas.parseTipo(t).get(0));
                    throw new ExcepcionSemantica(Errores.TIPO_INCORRECTO);
                }
                break;
        }
    }

}
