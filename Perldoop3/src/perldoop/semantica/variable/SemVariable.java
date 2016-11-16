package perldoop.semantica.variable;

import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.acceso.Acceso;
import perldoop.modelo.arbol.bloque.BloqueForeachVar;
import perldoop.modelo.arbol.coleccion.ColDec;
import perldoop.modelo.arbol.coleccion.ColDecOur;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.FuncionBloque;
import perldoop.modelo.arbol.paquete.Paquetes;
import perldoop.modelo.arbol.variable.*;
import perldoop.modelo.preprocesador.EtiquetasTipo;
import perldoop.modelo.semantica.EntradaVariable;
import perldoop.modelo.semantica.Paquete;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;
import perldoop.util.ParserEtiquetas;

/**
 * Paquete para la semantica de variable
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
        if (Buscar.isVariableSort(s)) {
            FuncionBloque funcion = Buscar.buscarPadre(s, FuncionBloque.class);
            Tipo t = funcion.getColeccion().getTipo();
            if (t == null) {
                tabla.getAcciones().reAnalizarDespuesDe(funcion.getColeccion());
            } else {
                List<Expresion> exps = Buscar.getExpresiones(funcion.getColeccion());
                if (exps.size() == 1) {
                    t = exps.get(0).getTipo();
                }
                t = t.getSubtipo(1);
                if (t.isColeccion()) {
                    t.add(0, Tipo.REF);
                }
                s.setTipo(t);
            }
        } else if (Buscar.isDeclaracion(s)) {
            ColDec dec = Buscar.buscarPadre(s, ColDec.class);
            declaracion(s, (EtiquetasTipo) dec.getOperador().getEtiquetas(), dec instanceof ColDecOur);
        } else {
            setTipo(null, s);
        }
    }

    public void visitar(VarPaquete s) {
        setTipo(s.getPaquetes(), s);
    }

    public void visitar(VarSigil s) {
        setTipo(null, s);
        sigil(s, s.getSigil());
    }

    public void visitar(VarPaqueteSigil s) {
        setTipo(s.getPaquetes(), s);
        sigil(s, s.getSigil());
    }

    public void visitar(VarMy s) {
        declaracion(s, (EtiquetasTipo) s.getMy().getEtiquetas(), false);
    }

    public void visitar(VarOur s) {
        declaracion(s, (EtiquetasTipo) s.getOur().getEtiquetas(), true);
    }

    /**
     * Declara la variable y la añade a la tabla de simbolos
     *
     * @param s Variable
     * @param etiquetas Etiquets del tipo
     * @param publica Variable publica
     */
    private void declaracion(Variable s, EtiquetasTipo etiquetas, boolean publica) {
        noAccederDeclaracion(s);
        if (obtenerTipo(s, etiquetas)) {
            validarTipo(s);
            boolean conflicto = tabla.getTablaSimbolos().buscarVariable(s.getVar().getValor()) != null;
            EntradaVariable entrada = new EntradaVariable(s.getVar().getValor(), s.getTipo(), publica);
            entrada.setConflicto(conflicto);
            tabla.getTablaSimbolos().addVariable(entrada, Buscar.getContexto(s));
        }
    }

    /**
     * Establece el tipo de una variable creda desde la tabla de simbolos
     *
     * @param paquetes Paquetes
     * @param var Variable
     */
    public void setTipo(Paquetes paquetes, Variable var) {
        EntradaVariable e;
        char contexto = Buscar.getContexto(var);
        if (paquetes != null) {
            //Busca dentro de los paquetes la clase con la variable
            Paquete clase = tabla.getTablaSimbolos().getImports().get(paquetes.getClaseJava());
            if (clase == null) {
                tabla.getGestorErrores().error(Errores.PAQUETE_NO_EXISTE, paquetes.getIdentificadores().get(0).getToken(),
                        paquetes.getIdentificadores().get(0).getToken());
                throw new ExcepcionSemantica(Errores.PAQUETE_NO_EXISTE);
            }
            //Buscar entrada
            e = clase.buscarVariable(var.getVar().toString(), contexto);
        } else {
            //Buscar entrada
            e = tabla.getTablaSimbolos().buscarVariable(var.getVar().getValor(), contexto);
        }
        if (e == null) {
            tabla.getGestorErrores().error(Errores.VARIABLE_NO_EXISTE, var.getVar().getToken(), var.getVar().getValor(), contexto);
            throw new ExcepcionSemantica(Errores.VARIABLE_NO_EXISTE);
        }
        var.setTipo(e.getTipo());
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
     * Obtiene el tipo de la variable desde las etiquetas
     *
     * @param v Variable
     * @param tipoLinea Tipo declarado en la linea
     * @return Tipo asignado
     */
    private boolean obtenerTipo(Variable v, EtiquetasTipo tipoLinea) {
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
                            contexto, v.getVar().getValor(), String.join("", ParserEtiquetas.parseTipo(t).get(0)));
                    throw new ExcepcionSemantica(Errores.TIPO_INCORRECTO);
                }
                break;
            case '@':
                if (!t.isArrayOrList()) {
                    tabla.getGestorErrores().error(Errores.TIPO_INCORRECTO, v.getVar().getToken(),
                            contexto, v.getVar().getValor(), String.join("", ParserEtiquetas.parseTipo(t).get(0)));
                    throw new ExcepcionSemantica(Errores.TIPO_INCORRECTO);
                }
                break;
            case '%':
                if (!t.isMap()) {
                    tabla.getGestorErrores().error(Errores.TIPO_INCORRECTO, v.getVar().getToken(),
                            contexto, v.getVar().getValor(), String.join("", ParserEtiquetas.parseTipo(t).get(0)));
                    throw new ExcepcionSemantica(Errores.TIPO_INCORRECTO);
                }
                break;
        }
    }

}
