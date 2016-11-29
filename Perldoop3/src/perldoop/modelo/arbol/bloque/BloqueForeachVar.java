package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.variable.Variable;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : FOR variable coleccion '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class BloqueForeachVar extends Bloque {

    private Terminal id;
    private AbrirBloque contextoHead;
    private Variable variable;
    private Coleccion coleccion;

    /**
     * Único contructor de la clase
     *
     * @param id Id
     * @param contextoHead Contexto cabecera
     * @param variable Variable
     * @param coleccion Coleccion
     * @param contextoBloque Contexto bloque
     * @param llaveI Llave izquierda
     * @param cuerpo Cuerpo
     * @param llaveD Llave derecha
     */
    public BloqueForeachVar(Terminal id, AbrirBloque contextoHead, Variable variable, Coleccion coleccion, AbrirBloque contextoBloque, Terminal llaveI, Cuerpo cuerpo, Terminal llaveD) {
        super(contextoBloque,llaveI, cuerpo, llaveD);
        setId(id);
        setContextoHead(contextoHead);
        setVariable(variable);
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
     * Obtiene la variable
     *
     * @return Variable
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * Establece la variable
     *
     * @param variable Variable
     */
    public void setVariable(Variable variable) {
        variable.setPadre(this);
        this.variable = variable;
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
        return new Simbolo[]{id,contextoHead, variable, coleccion, contextoBloque,llaveI, cuerpo, llaveD};
    }
}
