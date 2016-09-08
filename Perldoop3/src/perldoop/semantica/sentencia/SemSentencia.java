package perldoop.semantica.sentencia;

import perldoop.modelo.arbol.sentencia.*;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.Etiquetas;
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

    public void visitar(StcPaquete s) {
        if(tabla.getTablaSimbolos().isPaquete()){
            //TODO Dar error paqute ya creado
        }
        if(!tabla.getTablaSimbolos().isVacia()){
            //TODO Dar error paquete tiene que ser declarado al principio
        }
        tabla.getTablaSimbolos().crerPaquete(s.getPaquetes().getRepresentancion());
    }

    public void visitar(StcComentario s) {
        //Nada que comprobar
    }

    public void visitar(StcDeclaracion s) {
        EtiquetasPredeclaracion etiquetas = (EtiquetasPredeclaracion) s.getDeclaracion().getEtiquetas();
        for(Token t:etiquetas.getVariables()){
            tabla.getTablaSimbolos().addDeclaracion(t.getValor().substring(1, t.getValor().length()-1), etiquetas.getTipo());
        }    
    }

    public void visitar(StcError s) {
        //No se usa
    }
}
