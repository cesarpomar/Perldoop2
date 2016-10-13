package perldoop.modelo.arbol.cadena;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;

/**
 * Clase que representa la reduccion -&gt; cadena : QX SEP TEXTO SEP
 *
 * @author César Pomar
 */
public final class CadenaQX extends Cadena {

    private Terminal id;
    private Terminal sepI;
    private CadenaTexto texto;
    private Terminal sepD;

    /**
     * Constructor unico de la clase
     *
     * @param id Id
     * @param sepI Separador izquierdo
     * @param texto Texto
     * @param sepD Separador derecho
     */
    public CadenaQX(Terminal id, Terminal sepI, CadenaTexto texto, Terminal sepD) {
        setId(id);
        setSepI(sepI);
        setTexto(texto);
        setSepD(sepD);
    }

    /**
     * Obtiene el id
     *
     * @return Id
     */
    public Terminal getId() {
        return id;
    }

    /**
     * Establece el id
     *
     * @param id Id
     */
    public void setId(Terminal id) {
        id.setPadre(this);
        this.id = id;
    }

    /**
     * Obtiene el separador izquierdo
     *
     * @return Separador izquierdo
     */
    public Terminal getSepI() {
        return sepI;
    }

    /**
     * Establece el separador izquierdo
     *
     * @param sepI Separador izquierdo
     */
    public void setSepI(Terminal sepI) {
        sepI.setPadre(this);
        this.sepI = sepI;
    }

    /**
     * Obtiene el texto
     *
     * @return Texto
     */
    public CadenaTexto getTexto() {
        return texto;
    }

    /**
     * Establece el texto
     *
     * @param texto Texto
     */
    public void setTexto(CadenaTexto texto) {
        texto.setPadre(this);
        this.texto = texto;
    }

    /**
     * Obtiene el separador derecho
     *
     * @return Separador derecho
     */
    public Terminal getSepD() {
        return sepD;
    }

    /**
     * Establece el separador derecho
     *
     * @param sepD Separador derecho
     */
    public void setSepD(Terminal sepD) {
        sepD.setPadre(this);
        this.sepD = sepD;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, sepI, texto, sepD,};
    }
}
