package perldoop.semantica.variable;

import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.acceso.Acceso;
import perldoop.modelo.arbol.acceso.AccesoArray;
import perldoop.modelo.arbol.acceso.AccesoMap;
import perldoop.modelo.arbol.bloque.BloqueForeachVar;
import perldoop.modelo.arbol.variable.*;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.semantica.EntradaVariable;
import perldoop.modelo.semantica.Paquete;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.util.Buscar;
import perldoop.semantica.util.Etiquetas;

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
        EntradaVariable e = tabla.getTablaSimbolos().buscarVariable(s.getVar().toString(), contexto);
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
        obtenerTipo(s, s.getMy().getToken().getEtiquetas());
        validarTipo(s);
        variableEnmascarada(s);

        EntradaVariable entrada = new EntradaVariable(s.getVar().toString(), s.getTipo(), false);
        tabla.getTablaSimbolos().addVariable(entrada, s.getContexto().toString().charAt(0));
    }

    public void visitar(VarOur s) {
        noAccederDeclaracion(s);
        obtenerTipo(s, s.getOur().getToken().getEtiquetas());
        validarTipo(s);
        variableEnmascarada(s);

        EntradaVariable entrada = new EntradaVariable(s.getVar().toString(), s.getTipo(), true);
        tabla.getTablaSimbolos().addVariable(entrada, s.getContexto().toString().charAt(0));
    }

    /**
     * Busca el contexto de una variable
     *
     * @param v Variable
     */
    private char getContexto(Variable v) {
        Simbolo uso = Buscar.getPadre(v, 1);
        if (uso instanceof AccesoArray) {
            return '@';
        } else if (uso instanceof AccesoMap) {
            return '%';
        } else {
            return v.getContexto().toString().charAt(0);
        }
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
     * @param etiquetas Etiquetas
     */
    private void obtenerTipo(Variable v, List<Token> etiquetas) {
        Simbolo uso = Buscar.getPadre(v, 1);
        Tipo t = tabla.getTablaSimbolos().getDeclaracion(v.getContexto().toString() + v.getVar().toString());
        if (uso instanceof BloqueForeachVar) {
            if (t != null) {
                tabla.getGestorErrores().error(Errores.AVISO, Errores.TIPO_FOREACH, v.getVar().getToken());
            }
            BloqueForeachVar foreach = (BloqueForeachVar) uso;
            if (foreach.getLista().getTipo() == null) {
                tabla.getAcciones().reAnalizarDesdeDe(foreach.getLista());
            } else {
                v.setTipo(new Tipo(foreach.getLista().getTipo()));
            }

        } else if (t != null) {
            v.setTipo(t);
        } else if (etiquetas != null) {
            v.setTipo(Etiquetas.parseTipo(etiquetas, tabla.getGestorErrores()));
        } else {
            tabla.getGestorErrores().error(Errores.VARIABLE_SIN_TIPO, v.getVar().getToken(), v.getVar());
            throw new ExcepcionSemantica();
        }
    }

    /**
     * Hay que validar si el tipo es compatible con el contexto
     *
     * @param v Variable
     */
    private void validarTipo(Variable v) {
        char contexto = v.getContexto().toString().charAt(0);
        Tipo t = v.getTipo();
        switch (contexto) {
            case '$':
                if (t.isArrayOrList() || t.isMap()) {
                    tabla.getGestorErrores().error(Errores.TIPO_INCORRECTO, v.getVar().getToken(),
                            contexto, v.getVar(), Etiquetas.parseTipo(t).get(0));
                }
                break;
            case '@':
                if (!t.isArrayOrList()) {
                    tabla.getGestorErrores().error(Errores.TIPO_INCORRECTO, v.getVar().getToken(),
                            contexto, v.getVar(), Etiquetas.parseTipo(t).get(0));
                }
                break;
            case '%':
                if (!t.isMap()) {
                    tabla.getGestorErrores().error(Errores.TIPO_INCORRECTO, v.getVar().getToken(),
                            contexto, v.getVar(), Etiquetas.parseTipo(t).get(0));
                }
                break;
        }
    }

    /**
     * Si la variable ya estaba declarada hay que avisar que enmascarará la variable existente
     *
     * @param v Variable
     */
    private void variableEnmascarada(Variable v) {
        /*
        String id = v.getVar().toString();
        char contexto = v.getContexto().toString().charAt(0);
        if (tabla.getTablaSimbolos().buscarVariable(id, contexto, tabla.getTablaSimbolos().getBloques()) != null) {
            tabla.getGestorErrores().error(Errores.AVISO, Errores.VARIABLE_ENMASCARADA, v.getVar().getToken(), contexto, id);
        }*/
    }

}
