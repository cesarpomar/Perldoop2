package perldoop.semantica.contexto;

import perldoop.modelo.arbol.contexto.Contexto;
import perldoop.modelo.preprocesador.TagsComportamiento;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.semantica.contexto.comportamiento.*;

/**
 * Clase para la semantica de contexto
 *
 * @author César Pomar
 */
public class SemContexto {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemContexto(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(Contexto s) {
        if (s.getLlaveI().getEtiquetas() instanceof TagsComportamiento) {
            TagsComportamiento tc = (TagsComportamiento) s.getLlaveI().getEtiquetas();
            switch (tc.getEtiqueta().getValor()) {
                case "<main>":
                    new SemCompMain(tabla).visitar(s);
                    break;
                case "<hadoop>":
                    new SemCompHadoop(tabla).visitar(s);
                    break;
            }
        }
    }

}
