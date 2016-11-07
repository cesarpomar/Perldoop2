package perldoop.generacion.cadena;

import perldoop.modelo.arbol.cadena.*;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de cadenas
 *
 * @author César Pomar
 */
public class GenCadena {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenCadena(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(CadenaSimple s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getTexto());
        codigo.append(s.getSepD().getComentario());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CadenaDoble s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getTexto());
        codigo.append(s.getSepD().getComentario());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CadenaComando s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Pd.cmd(");
        codigo.append(s.getTexto());
        codigo.append(")").append(s.getSepD().getComentario());;
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CadenaQ s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getId().getComentario());
        codigo.append(s.getTexto());
        codigo.append(s.getSepD().getComentario());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CadenaQW s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getId().getComentario());
        codigo.append("new String[]{");
        codigo.append(s.getTexto());
        codigo.append("}").append(s.getSepD().getComentario());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CadenaQQ s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getId().getComentario());
        codigo.append(s.getTexto());
        codigo.append(s.getSepD().getComentario());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CadenaQX s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getId().getComentario());
        codigo.append("Pd.cmd(");
        codigo.append(s.getTexto());
        codigo.append(")").append(s.getSepD().getComentario());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CadenaQR s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getId().getComentario());
        codigo.append(s.getTexto());
        codigo.append(s.getSepD().getComentario());
        s.setCodigoGenerado(codigo);
    }

}
