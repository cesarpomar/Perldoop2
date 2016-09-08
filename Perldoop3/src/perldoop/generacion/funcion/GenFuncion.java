package perldoop.generacion.funcion;

import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.funcion.Argumentos;
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
        codigo.append(s.getPaquetes().getCodigoGenerado()).append(s.getIdentificador().getCodigoGenerado());
        codigo.append("(").append(s.getColeccion().getCodigoGenerado()).append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(FuncionPaqueteNoArgs s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getPaquetes().getCodigoGenerado()).append(s.getIdentificador().getCodigoGenerado());
        codigo.append("(").append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(FuncionArgs s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getIdentificador().getCodigoGenerado());
        codigo.append("(").append(s.getColeccion().getCodigoGenerado()).append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(FuncionNoArgs s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getIdentificador().getCodigoGenerado());
        codigo.append("(").append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(Argumentos s) {
        //TODO borrar
    }

}
