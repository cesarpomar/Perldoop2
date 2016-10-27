package perldoop.generacion.lectura;

import perldoop.modelo.arbol.lectura.LecturaFile;
import perldoop.modelo.arbol.lectura.LecturaIn;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de lectura
 *
 * @author César Pomar
 */
public class GenLectura {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenLectura(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(LecturaIn s) {
        StringBuilder codigo = new StringBuilder(100);
        if (s.getTipo().isString()) {
            codigo.append("Pd.read(");
        } else {
            codigo.append("Pd.readLines(");
        }
        codigo.append(s.getMenor().getComentario());
        codigo.append("PerlFile.STDIN");
        codigo.append(")").append(s.getMayor().getComentario());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(LecturaFile s) {
        StringBuilder codigo = new StringBuilder(100);
        if (s.getTipo().isString()) {
            codigo.append("Pd.read(");
        } else {
            codigo.append("Pd.readLines(");
        }
        codigo.append(s.getMenor().getComentario());
        codigo.append(s.getExpresion());
        codigo.append(")").append(s.getMayor().getComentario());
        s.setCodigoGenerado(codigo);
    }

}
