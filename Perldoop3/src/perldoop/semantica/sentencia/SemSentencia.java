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
        //Sin semantica
    }

    public void visitar(StcBloque s) {
        //Sin semantica
    }

    public void visitar(StcFlujo s) {
        //Sin semantica
    }

    public void visitar(StcComentario s) {
        //Sin semantica
    }

    public void visitar(StcTipado s) {
        EtiquetasPredeclaracion etiquetas = (EtiquetasPredeclaracion) s.getDeclaracion().getEtiquetas();
        for (Token t : etiquetas.getVariables()) {
            tabla.getTablaSimbolos().addDeclaracion(t.getValor().substring(1, t.getValor().length() - 1), etiquetas.getTipo());
        }
    }

    public void visitar(StcModulos s) {
        //Sin semantica
    }

    public void visitar(StcImport s) {
        //Sin semantica
    }

    public void visitar(StcLineaJava s) {
        //Sin semantica
    }

    public void visitar(StcError s) {
        ////Sin semantica
    }
}
