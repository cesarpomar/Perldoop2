package perldoop.semantica.regulares;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.regulares.*;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de regulares
 *
 * @author César Pomar
 */
public class SemRegulares {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemRegulares(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Comrpueba que no se une un modificador desconocido o no soportador
     *
     * @param regex Nombre de la expresion regular(Errores)
     * @param mods Modificadores usados
     * @param sopotados Modificadores soportados
     */
    private void checkModificadores(String regex, Terminal mods, String sopotados) {
        if (mods == null) {
            return;
        }
        for (char m : mods.getValor().toCharArray()) {
            if (sopotados.indexOf(m) == -1) {
                tabla.getGestorErrores().error(Errores.REGEX_MODIFICADOR_DESCONOCIDO, mods.getToken(), regex, m);
                throw new ExcepcionSemantica(Errores.REGEX_MODIFICADOR_DESCONOCIDO);
            }
        }
    }

    /**
     * Comrprueba que la expresion es una variable
     *
     * @param regex Nombre de la expresion regular
     * @param exp Expresion de la variable
     */
    private void checkVariable(String regex, Expresion exp) {
        if (!Buscar.isVariable(exp)) {
            tabla.getGestorErrores().error(Errores.REGEX_NO_VARIABLE, Buscar.tokenInicio(exp), regex);
            throw new ExcepcionSemantica(Errores.REGEX_NO_VARIABLE);
        }
    }

    public void visitar(RegularNoMatch s) {
        s.setTipo(new Tipo(Tipo.BOOLEAN));
        checkModificadores("m", s.getModificadores(), "imosxg");
    }

    public void visitar(RegularMatch s) {
        s.setTipo(new Tipo(Tipo.ARRAY, Tipo.STRING));
        checkModificadores("m", s.getModificadores(), "imosxg");
    }

    public void visitar(RegularSubs s) {
        s.setTipo(new Tipo(Tipo.STRING));
        checkVariable("s", s.getExpresion());
        checkModificadores("s", s.getModificadores(), "imosxg");
    }

    public void visitar(RegularTrans s) {
        s.setTipo(new Tipo(Tipo.STRING));
        checkVariable(s.getId().getValor(), s.getExpresion());
        checkModificadores(s.getId().getValor(), s.getModificadores(), "cds");
    }
}
