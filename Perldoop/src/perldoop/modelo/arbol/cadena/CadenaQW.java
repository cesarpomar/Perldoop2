package perldoop.modelo.arbol.cadena;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cadenatexto.CadenaTexto;

/**
 * Clase que representa la reduccion -&gt; cadena : QW SEP cadenaTexto SEP
 *
 * @author César Pomar
 */
public final class CadenaQW extends Cadena {
    private Terminal id;

    /**
     * Constructor unico de la clase
     *
     * @param id Id
     * @param sepI Separador izquierdo
     * @param texto Texto
     * @param sepD Separador derecho
     */
    public CadenaQW(Terminal id, Terminal sepI, CadenaTexto texto, Terminal sepD) {
        super(sepI, texto, sepD);
        setId(id);
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

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, sepI, texto, sepD,};
    }
}
