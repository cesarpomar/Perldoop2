package perldoop.generacion.funcion;

import perldoop.modelo.arbol.FuncionNativa;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.funcion.FuncionArgs;
import perldoop.modelo.arbol.funcion.FuncionNoArgs;
import perldoop.modelo.arbol.funcion.FuncionPaqueteArgs;
import perldoop.modelo.arbol.funcion.FuncionPaqueteNoArgs;

/**
 * Clase generadora de funcion
 *
 * @author César Pomar
 */
public class GenFuncion {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenFuncion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionPaqueteArgs s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getPaquetes()).append(s.getIdentificador());
        codigo.append("(").append(s.getColeccion()).append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(FuncionPaqueteNoArgs s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getPaquetes()).append(s.getIdentificador());
        codigo.append("(").append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(FuncionArgs s) {
        if (s.getPadre() instanceof ExpFuncion) {
            FuncionNativa fn = getGenNativa(s.getIdentificador().getValor());
            if (fn != null) {
                fn.visitar(s, (ColParentesis) s.getColeccion().getColeccion());
                return;
            }
        }
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getIdentificador());
        codigo.append("(").append(s.getColeccion()).append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(FuncionNoArgs s) {
        if (s.getPadre() instanceof ExpFuncion) {
            FuncionNativa fn = getGenNativa(s.getIdentificador().getValor());
            if (fn != null) {
                fn.visitar(s, null);
                return;
            }
        }
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getIdentificador());
        codigo.append("(").append(")");
        s.setCodigoGenerado(codigo);
    }

    /**
     * Obtiene la semantica nativa de una funcion
     *
     * @param id Nombre de la funcion
     * @return Semantica nativa
     */
    private FuncionNativa getGenNativa(String id) {
        switch (id) {
            default:
                return null;
        }
    }

}
