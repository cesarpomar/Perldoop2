package perldoop.generacion.funcion;

import perldoop.modelo.arbol.FuncionNativa;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.generacion.TablaGenerador;

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

    public void visitar(FuncionBasica s) {
        if (s.getPadre() instanceof ExpFuncion) {
            FuncionNativa fn = getGenNativa(s.getIdentificador().getValor());
            if (fn != null) {
                fn.visitar(s, (ColParentesis) s.getColeccion());
                return;
            }
        }
        StringBuilder codigo = new StringBuilder(100);
        //codigo.append(s.getPaquetes().getRepresentancion());TODO
        codigo.append(s.getIdentificador());
        codigo.append("(").append(s.getColeccion()).append(")");
        s.setCodigoGenerado(codigo);

    }

    public void visitar(FuncionHandle s) {

    }

    public void visitar(FuncionBloque s) {

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
