package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.expresion.Expresion;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : UNTIL '(' expresion ')' '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class BloqueUntil extends BloqueControlBasico {

    /**
     * Único contructor de la clase
     *
     * @param id While
     * @param abrirBloque Abertura de bloque para la cabecera
     * @param parentesisI Parentesis izquierdo
     * @param expresion Expresión
     * @param parentesisD Parentesis derecho
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public BloqueUntil(Terminal id, AbrirBloque abrirBloque, Terminal parentesisI, Expresion expresion, Terminal parentesisD, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        super(id, abrirBloque, parentesisI, expresion, parentesisD,  llaveI, cuerpo, llaveD);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, parentesisI, expresion, parentesisD,  llaveI, cuerpo, llaveD};
    }
}
