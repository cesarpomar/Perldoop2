package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : IF '(' expresion ')' '{' cuerpo '}' subBloque
 *
 * @author César Pomar
 */
public final class BloqueIf extends BloqueControlBasico {

    private SubBloque subBloque;

    /**
     * Único contructor de la clase
     *
     * @param id if
     * @param abrirBloque Abertura de bloque para la cabecera
     * @param parentesisI Parentesis izquierdo
     * @param expresion Expresión
     * @param parentesisD Parentesis derecho
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     * @param subBloque SubBloque
     */
    public BloqueIf(Terminal id, AbrirBloque abrirBloque, Terminal parentesisI, Expresion expresion, Terminal parentesisD, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD, SubBloque subBloque) {
        super(id, abrirBloque, parentesisI, expresion, parentesisD, llaveI, cuerpo, llaveD);
        setSubBloque(subBloque);
    }

    /**
     * Obtiene el subBloque
     *
     * @return SubBloque
     */
    public SubBloque getSubBloque() {
        return subBloque;
    }

    /**
     * Establece el subBloque
     *
     * @param subBloque SubBloque
     */
    public void setSubBloque(SubBloque subBloque) {
        subBloque.setPadre(this);
        this.subBloque = subBloque;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, parentesisI, expresion, parentesisD, llaveI, cuerpo, llaveD, subBloque};
    }
}
