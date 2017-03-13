package perldoop.modelo.arbol;

import perldoop.modelo.semantica.Tipo;

/**
 * Clase que substituye a un nodo del arbol, las operaciones que requieran cambiar un nodo del arbol ya sea su tipo o el
 * codigo generador en el.
 *
 * @author César Pomar
 */
public final class SimboloAux extends Simbolo {

    /**
     * Contruye un Simbolo auxiliar vacio
     */
    public SimboloAux() {
    }

    /**
     * Contruye un Simbolo auxiliar clonando el tipo y el codigo generador de un simbolo existente
     *
     * @param s Simbolo original
     */
    public SimboloAux(Simbolo s) {
        tipo = s.tipo;
        if (s.codigoGenerado != null) {
            codigoGenerado = new StringBuilder(s.getCodigoGenerado());
        }
    }

    /**
     * Contruye un Simbolo auxiliar
     *
     * @param tipo Tipo del simbolo
     */
    public SimboloAux(Tipo tipo) {
        setTipo(tipo);
    }

    /**
     * Contruye un Simbolo auxiliar
     *
     * @param tipo Tipo del simbolo
     * @param codigo Codigo generador
     */
    public SimboloAux(Tipo tipo, StringBuilder codigo) {
        setTipo(tipo);
        codigoGenerado = codigo;
    }

    @Override
    public void aceptar(Visitante v) {
        //Nunca pertenecerá al arbol sintactico
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{};
    }

}
