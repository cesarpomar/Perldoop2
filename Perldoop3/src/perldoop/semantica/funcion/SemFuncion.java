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
            tabla.getGestorErrores().error(Errores.PAQUETE_NO_EXISTE, paquetes.getIdentificadores().get(0).getToken(),
                    paquetes.getIdentificadores().get(0).getToken());
            throw new ExcepcionSemantica(Errores.PAQUETE_NO_EXISTE);
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
            default:
                return null;
        }
    }

}
