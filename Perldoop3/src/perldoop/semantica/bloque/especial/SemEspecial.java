package perldoop.semantica.bloque.especial;

import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueIf;
import perldoop.modelo.arbol.bloque.BloqueUnless;
import perldoop.modelo.arbol.bloque.BloqueVacio;
import perldoop.modelo.arbol.bloque.SubBloque;
import perldoop.modelo.arbol.flujo.Flujo;
import perldoop.modelo.arbol.flujo.Last;
import perldoop.modelo.arbol.flujo.Next;
import perldoop.modelo.arbol.flujo.Return;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de bloques especiales
 *
 * @author César Pomar
 */
public abstract class SemEspecial {

    protected TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemEspecial(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Visita un bloque
     *
     * @param s Bloque
     */
    public abstract void visitar(Bloque s);

    /**
     * Comprueba que las sentencias de flujo no sobreapsa la etiqueta de comportamiento
     *
     * @param b Bloque
     */
    protected void checkFlujo(Bloque b) {
        List<Flujo> lista = (List) Buscar.buscarClases(b, Next.class, Last.class, Return.class);
        Token error = null;
        FOR:
        for (Flujo f : lista) {
            if (f instanceof Return) {
                error = ((Return) f).getPuntoComa().getToken();
                break;
            }
            Bloque bloque = Buscar.buscarPadre(b, Bloque.class);
            do {
                if (bloque instanceof BloqueIf || bloque instanceof BloqueUnless || bloque instanceof BloqueVacio || bloque instanceof SubBloque) {
                    bloque = Buscar.buscarPadre(bloque, Bloque.class);
                } else {
                    continue FOR;
                }
            } while (bloque != null && bloque != b);
            error = ((Return) f).getPuntoComa().getToken();
            break;
        }
        if (error != null) {
            tabla.getGestorErrores().error(Errores.FLUJO_FUERA_COMPORTAMIENTO, error);
            throw new ExcepcionSemantica(Errores.FLUJO_FUERA_COMPORTAMIENTO);
        }

    }
}
