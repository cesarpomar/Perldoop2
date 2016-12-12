package perldoop.generacion.modificador;

import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.modificador.ModFor;
import perldoop.modelo.arbol.modificador.ModIf;
import perldoop.modelo.arbol.modificador.ModNada;
import perldoop.modelo.arbol.modificador.ModUnless;
import perldoop.modelo.arbol.modificador.ModUntil;
import perldoop.modelo.arbol.modificador.ModWhile;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase generadora de modificador
 *
 * @author César Pomar
 */
public class GenModificador {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenModificador(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(ModNada s) {
        s.setCodigoGenerado(new StringBuilder());
    }

    public void visitar(ModUnless s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("if").append(s.getId().getComentario());
        codigo.append('(').append(Casting.casting(s.getExpresion(), new Tipo(Tipo.BOOLEAN), tabla.getOpciones().isOptNulos())).append(')');
        s.setCodigoGenerado(codigo);
    }

    public void visitar(ModIf s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("if").append(s.getId().getComentario());
        codigo.append('(').append(Casting.casting(s.getExpresion(), new Tipo(Tipo.BOOLEAN), tabla.getOpciones().isOptNulos())).append(')');
        s.setCodigoGenerado(codigo);
    }

    public void visitar(ModWhile s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("while").append(s.getId().getComentario());
        codigo.append('(').append(Casting.casting(s.getExpresion(), new Tipo(Tipo.BOOLEAN), tabla.getOpciones().isOptNulos())).append(')');
        s.setCodigoGenerado(codigo);
    }

    public void visitar(ModUntil s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("while").append(s.getId().getComentario());
        codigo.append('(').append(Casting.casting(s.getExpresion(), new Tipo(Tipo.BOOLEAN), tabla.getOpciones().isOptNulos())).append(')');
        s.setCodigoGenerado(codigo);
    }

    public void visitar(ModFor s) {
        StringBuilder codigo = new StringBuilder(100);
        String aux = tabla.getGestorReservas().getAux();
        Tipo t = new Tipo(Tipo.INTEGER);
        codigo.append(s.getId());
        codigo.append("(");
        codigo.append(Tipos.declaracion(t)).append(" ").append(aux).append(";");
        codigo.append(aux).append("<").append(Casting.casting(s.getExpresion(), t)).append(";");
        codigo.append(aux).append("++");
        codigo.append(")");
        s.setCodigoGenerado(codigo);
    }

}
