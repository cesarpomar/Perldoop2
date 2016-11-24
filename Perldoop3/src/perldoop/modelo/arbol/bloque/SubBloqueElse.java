package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.cuerpo.Cuerpo;

/**
 * Clase que representa la reduccion -&gt;<br> condicional : ELSE '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class SubBloqueElse extends SubBloque {

    private Terminal id;

    /**
     * Único contructor de la clase
     *
     * @param id Else
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public SubBloqueElse(Terminal id, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        super(llaveI, cuerpo, llaveD);
        setId(id);
    }

    /**
     * Obtiene el id de bloque
     *
     * @return Id
     */
    public Terminal getId() {
        return id;
    }

    /**
     * Establece el id de bloque
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
        return new Simbolo[]{id, llaveI, cuerpo, llaveD};
    }

}
