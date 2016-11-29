package perldoop.semantica.bloque;

import perldoop.modelo.arbol.bloque.*;
import perldoop.modelo.preprocesador.Tags;
import perldoop.modelo.preprocesador.TagsComportamiento;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.semantica.bloque.especial.SemEspFuncion;
import perldoop.semantica.bloque.especial.SemEspHadoop;
import perldoop.semantica.bloque.especial.SemEspMain;

/**
 * Clase para la semantica de bloque
 *
 * @author César Pomar
 */
public class SemBloque {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemBloque(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(BloqueWhile s) {
        checkSpecial(s);
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueUntil s) {
        checkSpecial(s);
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueDoWhile s) {
        checkSpecial(s);
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueDoUntil s) {
        checkSpecial(s);
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueFor s) {
        checkSpecial(s);
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueForeachVar s) {
        checkSpecial(s);
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueForeach s) {
        checkSpecial(s);
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueIf s) {
        checkSpecial(s);
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueUnless s) {
        checkSpecial(s);
        tabla.getTablaSimbolos().cerrarBloque();
    }

    public void visitar(BloqueVacio s) {
        checkSpecial(s);
        //No tiene cabecera
    }

    public void visitar(SubBloqueElse s) {
        checkSpecial(s);
        //No tiene cabecera propia
    }

    public void visitar(SubBloqueElsif s) {
        checkSpecial(s);
        //No tiene cabecera propia
    }

    public void visitar(SubBloqueVacio s) {
        //No tiene cabecera propia
    }

    /**
     * Comprueba la semantica de bloques especiales
     *
     * @param s Bloque
     */
    private void checkSpecial(Bloque s) {
        Tags tags = s.getLlaveI().getEtiquetas();
        if (tags != null) {
            switch (((TagsComportamiento) tags).getEtiqueta().getValor()) {
                case "<main>":
                    new SemEspMain(tabla).visitar(s);
                    break;
                case "<hadoop>":
                    new SemEspHadoop(tabla).visitar(s);
                    break;
                case "<function>":
                    new SemEspFuncion(tabla).visitar(s);
                    break;
            }
        }
    }
}
