package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : WHILE '(' expresion ')' '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class BloqueWhile extends BloqueControlBasico {

    /**
     * Único contructor de la clase
     *
     * @param id While
     * @param contextoHead Contexto cabecera
     * @param parentesisI Parentesis izquierdo
     * @param expresion Expresión
     * @param parentesisD Parentesis derecho
     * @param contextoBloque Contexto bloque
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public BloqueWhile(Terminal id, AbrirBloque contextoHead, Terminal parentesisI, Expresion expresion, Terminal parentesisD, AbrirBloque contextoBloque, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        super(id, contextoHead, parentesisI, expresion, parentesisD, contextoBloque, llaveI, cuerpo, llaveD);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, contextoHead, parentesisI, expresion, parentesisD, contextoBloque, llaveI, cuerpo, llaveD};
    }

}
