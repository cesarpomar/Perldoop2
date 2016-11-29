package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.cuerpo.Cuerpo;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : FOR coleccion '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class BloqueForeach extends Bloque {

    private Terminal id;
    private AbrirBloque contextoHead;
    private Coleccion coleccion;

    /**
     * Único contructor de la clase
     *
     * @param id Id
     * @param contextoHead Contexto cabecera
     * @param coleccion Coleccion
     * @param contextoBloque Contexto bloque
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public BloqueForeach(Terminal id, AbrirBloque contextoHead, Coleccion coleccion, AbrirBloque contextoBloque, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        super(contextoBloque, llaveI, cuerpo, llaveD);
        setId(id);
        setContextoHead(contextoHead);
        setColeccion(coleccion);
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

    /**
     * Obtiene el contexto de la cabecera
     *
     * @return Contexto de la cabecera
     */
    public AbrirBloque getContextoHead() {
        return contextoHead;
    }

    /**
     * Establece el contexto de la cabecera
     *
     * @param contextoHead Contexto de la cabecera
     */
    public void setContextoHead(AbrirBloque contextoHead) {
        contextoHead.setPadre(this);
        this.contextoHead = contextoHead;
    }

    /**
     * Obtiene la colección
     *
     * @return Coleccion
     */
    public Coleccion getColeccion() {
        return coleccion;
    }

    /**
     * Establece la colección
     *
     * @param coleccion Coleccion
     */
    public void setColeccion(Coleccion coleccion) {
        coleccion.setPadre(this);
        this.coleccion = coleccion;
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return new Simbolo[]{id, contextoHead, coleccion, contextoBloque, llaveI, cuerpo, llaveD};
    }
}
