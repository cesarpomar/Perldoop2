package perldoop.semantica.bloque.especial;

import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueIf;
import perldoop.modelo.arbol.bloque.BloqueSimple;
import perldoop.modelo.arbol.bloque.BloqueUnless;
import perldoop.modelo.arbol.bloque.SubBloque;
import perldoop.modelo.arbol.flujo.Flujo;
import perldoop.modelo.arbol.flujo.Last;
import perldoop.modelo.arbol.flujo.Next;
import perldoop.modelo.arbol.flujo.Return;
import perldoop.modelo.arbol.fuente.Fuente;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.preprocesador.TagsBloque;
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
     * Comprueba que las sentencias de flujo no sobreapasan la etiqueta de comportamiento
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
            while (bloque != null && bloque != b) {
                if (bloque instanceof BloqueIf || bloque instanceof BloqueUnless || bloque instanceof BloqueSimple || bloque instanceof SubBloque) {
                    bloque = Buscar.buscarPadre(bloque, Bloque.class);
                } else {
                    continue FOR;
                }
            }
            error = Buscar.tokenInicio(f);
            break;
        }
        if (error != null) {
            tabla.getGestorErrores().error(Errores.FLUJO_FUERA_COMPORTAMIENTO, error);
            throw new ExcepcionSemantica(Errores.FLUJO_FUERA_COMPORTAMIENTO);
        }
    }

    /**
     * Comprueba que el bloque sea global
     *
     * @param b Bloque
     */
    protected void checkGlobal(Bloque b) {
        //StcBLoque, Cuerpo, Fuente
        Simbolo fuente = b.getPadre().getPadre().getPadre();
        if (!(fuente instanceof Fuente)) {
            tabla.getGestorErrores().error(Errores.ESPECIAL_LOCAL, ((TagsBloque) b.getLlaveI().getEtiquetas()).getEtiqueta());
            throw new ExcepcionSemantica(Errores.ESPECIAL_LOCAL);
        }
    }

    /**
     * Comprueba que el bloque no tenga cabecera
     *
     * @param b Bloque
     */
    protected void checkNoCabecera(Bloque b) {
        if (!(b instanceof BloqueSimple)) {
            tabla.getGestorErrores().error(Errores.ESPECIAL_CABECERA, ((TagsBloque) b.getLlaveI().getEtiquetas()).getEtiqueta());
            throw new ExcepcionSemantica(Errores.ESPECIAL_CABECERA);
        }
    }
}
