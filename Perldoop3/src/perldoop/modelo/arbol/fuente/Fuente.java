package perldoop.modelo.arbol.fuente;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -&gt; fuente : cuerpo masFuente <br>
 * masFuente :<br>
 * | funcionDef fuente
 *
 * @author César Pomar
 */
public final class Fuente extends Simbolo {

    private List<Simbolo> codigoFuente;

    /**
     * Único contructor de la clase
     */
    public Fuente() {
        codigoFuente = new ArrayList<>(100);
    }

    /**
     * Añade codigo fuente
     *
     * @param codigo Codigo fuetne
     * @return Esta instancia
     */
    public Fuente add(Simbolo codigo) {
        codigoFuente.add(codigo);
        codigo.setPadre(this);
        return this;
    }

    /**
     * Realiza la misma acción que invocar f.add(codigo)
     *
     * @param f Fuente
     * @param codigo Codigo fuente
     * @return Fuente f
     */
    public static Fuente add(Fuente f, Simbolo codigo) {
        return f.add(codigo);
    }

    /**
     * Obtiene el codigo fuente
     *
     * @return Codigo fuente
     */
    public List<Simbolo> getCodigoFuente() {
        return codigoFuente;
    }

    /**
     * Establece el codigo fuente
     *
     * @param codigoFuente Codigo fuente
     */
    public void setCodigoFuente(List<Simbolo> codigoFuente) {
        this.codigoFuente = codigoFuente;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return codigoFuente.toArray(new Simbolo[codigoFuente.size()]);
    }

}
