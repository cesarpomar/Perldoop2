package perldoop.semantica.bloque.especial;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueVacio;
import perldoop.modelo.arbol.fuente.Fuente;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de bloque main
 *
 * @author César Pomar
 */
public final class SemEspMain extends SemEspecial {

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemEspMain(TablaSemantica tabla) {
        super(tabla);
    }

    @Override
    public void visitar(Bloque s) {
        //StcBLoque, Cuerpo, Fuente
        Simbolo fuente = s.getPadre().getPadre().getPadre();
        if(!(fuente instanceof Fuente && s instanceof BloqueVacio)){
            tabla.getGestorErrores().error(Errores.MAIN_LOCAL, s.getLlaveI().getToken());
            throw new ExcepcionSemantica(Errores.MAIN_LOCAL);
        }
        //Todo comprobar que no hay mas de un main
    }



}
