package perldoop.semantica.sentencia;

import perldoop.modelo.arbol.sentencia.*;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.EtiquetasPredeclaracion;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de sentencia
 *
 * @author César Pomar
 */
public class SemSentencia {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemSentencia(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(StcLista s) {
        //Nada que comprobar
    }

    public void visitar(StcBloque s) {
        //Nada que comprobar
    }

    public void visitar(StcFlujo s) {
    }

    public void visitar(StcComentario s) {
        //Nada que comprobar
    }

    public void visitar(StcTipado s) {
        EtiquetasPredeclaracion etiquetas = (EtiquetasPredeclaracion) s.getDeclaracion().getEtiquetas();
        for (Token t : etiquetas.getVariables()) {
            tabla.getTablaSimbolos().addDeclaracion(t.getValor().substring(1, t.getValor().length() - 1), etiquetas.getTipo());
        }
    }

    public void visitar(StcModulos s) {
    }

    public void visitar(StcImport s) {
    }

    public void visitar(StcLineaJava s) {
    }

    public void visitar(StcError s) {
        //No se usa
    }
}
