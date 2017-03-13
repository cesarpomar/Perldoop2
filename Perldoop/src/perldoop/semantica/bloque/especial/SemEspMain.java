package perldoop.semantica.bloque.especial;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.bloque.Bloque;
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
        checkGlobal(s);
        checkNoCabecera(s);
        if (tabla.getClaseAttr().isMain()) {
            tabla.getGestorErrores().error(Errores.MAIN_EXISTENTE, s.getLlaveI().getToken());
            throw new ExcepcionSemantica(Errores.MAIN_EXISTENTE);
        }
        tabla.getClaseAttr().setMain(true);
    }

}
