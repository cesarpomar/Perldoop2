package perldoop.semantica.funcion;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.arbol.paquete.Paquetes;
import perldoop.modelo.semantica.Paquete;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.semantica.funcion.nativa.*;

/**
 * Clase para la semantica de funcion
 *
 * @author César Pomar
 */
public class SemFuncion {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemFuncion(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionBasica s) {
        if (!s.getPaquetes().isVacio()) {
            comprobarFuncionPaquete(s, s.getPaquetes());
            return;
        }
        SemFuncionNativa fn = null;
        if (s.getPadre() instanceof ExpFuncion) {
            fn = getSemNativa(s.getIdentificador().getValor());
        }
        if (fn != null) {
            fn.visitar(s);
            return;
        } else if (tabla.getTablaSimbolos().buscarFuncion(s.getIdentificador().getToken().getValor()) == null) {
            tabla.getTablaSimbolos().addFuncionNoDeclarada(s.getIdentificador().getToken());
        }
        s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
    }

    public void visitar(FuncionHandle s) {
        SemFuncionNativa fn = null;
        if (s.getPadre() instanceof ExpFuncion) {
            fn = getSemNativa(s.getIdentificador().getValor());
        }
        if (fn != null) {
            fn.visitar(s);
        } else {
            tabla.getGestorErrores().error(Errores.FUNCION_SIN_HANDLE, s.getHandle());
            throw new ExcepcionSemantica(Errores.FUNCION_SIN_HANDLE);
        }
    }

    public void visitar(FuncionBloque s) {
        SemFuncionNativa fn = null;
        if (s.getPadre() instanceof ExpFuncion) {
            fn = getSemNativa(s.getIdentificador().getValor());
        }
        if (fn != null) {
            fn.visitar(s);
        } else {
            tabla.getGestorErrores().error(Errores.FUNCION_SIN_HANDLE, s.getLlaveD());
            throw new ExcepcionSemantica(Errores.FUNCION_SIN_HANDLE);
        }
    }

    /**
     * Comprueba una funcion de un paquete
     *
     * @param f Funcion
     * @param paquetes Paquetes
     */
    private void comprobarFuncionPaquete(Funcion f, Paquetes paquetes) {
        Paquete paquete = tabla.getTablaSimbolos().getImports().get(paquetes.getClaseJava());
        if (paquete == null) {
            tabla.getGestorErrores().error(Errores.PAQUETE_NO_IMPORTADO, paquetes.getIdentificadores().get(0).getToken(),
                    paquetes.getIdentificadores().get(0).getToken());
            throw new ExcepcionSemantica(Errores.PAQUETE_NO_IMPORTADO);
        }
        if (paquete.buscarFuncion(f.getIdentificador().getToken().getValor()) == null) {
            tabla.getGestorErrores().error(Errores.FUNCION_NO_EXISTE, f.getIdentificador().getToken(), f.getIdentificador().getValor());
            throw new ExcepcionSemantica(Errores.FUNCION_NO_EXISTE);
        }
        f.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
    }

    /**
     * Obtiene la semantica nativa de una funcion
     *
     * @param id Nombre de la funcion
     * @return Semantica nativa
     */
    private SemFuncionNativa getSemNativa(String id) {
        switch (id) {
            case "print":
                return new SemFuncionPrint(tabla);
            case "printf":
                return new SemFuncionPrintf(tabla);
            case "chop":
                return new SemFuncionChop(tabla);
            case "chomp":
                return new SemFuncionChomp(tabla);
            case "sort":
                return new SemFuncionSort(tabla);
            case "split":
                return new SemFuncionSplit(tabla);
            case "join":
                return new SemFuncionJoin(tabla);
            case "push":
                return new SemFuncionPush(tabla);
            case "pop":
                return new SemFuncionPop(tabla);
            case "shift":
                return new SemFuncionShift(tabla);
            case "unshift":
                return new SemFuncionUnshift(tabla);
            case "delete":
                return new SemFuncionDelete(tabla);
            case "keys":
                return new SemFuncionKeys(tabla);
            case "values":
                return new SemFuncionValues(tabla);
            case "die":
                return new SemFuncionDie(tabla);
            case "warn":
                return new SemFuncionWarn(tabla);
            case "exit":
                return new SemFuncionExit(tabla);
            case "open":
                return new SemFuncionOpen(tabla);
            case "close":
                return new SemFuncionClose(tabla);
            case "defined":
                return new SemFuncionDefined(tabla);
            case "undef":
                return new SemFuncionUndef(tabla);
            case "lc":
            case "lcfirst":
            case "uc":
            case "ucfirst":
                return new SemFuncionWordCase(tabla);
            case "log":
                return new SemFuncionLog(tabla);
            case "binmode":
                return new SemFuncionBinmode(tabla);
            default:
                return null;
        }
    }

}
