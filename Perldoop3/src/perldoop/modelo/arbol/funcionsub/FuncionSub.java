package perldoop.modelo.arbol.funcionsub;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 * Clase que representa la reduccion -> funcionSub : SUB ID
 *
 * @author César Pomar
 */
public final class FuncionSub extends Simbolo {

    private Terminal sub;
    private Terminal id;

    /**
     * Único contructor de la clase
     * @param sub Sub
     * @param id ID
     */
    public FuncionSub(Terminal sub, Terminal id) {
        setSub(sub);
        setId(id);
    }

    /**
     * Obtiene el sub
     * @return Sub
     */
    public Terminal getSub() {
        return sub;
    }

    /**
     * Establece el Sub
     * @param sub Sub
     */
    public void setSub(Terminal sub) {
        sub.setPadre(this);
        this.sub = sub;
    }

    /**
     * Obtiene el id
     * @return Id
     */
    public Terminal getId() {
        return id;
    }

    /**
     * Estabelce el id
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
        return new Simbolo[]{sub, id};
    }

}
