package perldoop.modelo.arbol.bloque;

import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.modelo.arbol.abrirbloque.AbrirBloque;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.contexto.Contexto;
import perldoop.modelo.arbol.variable.Variable;

/**
 * Clase que representa la reduccion -&gt;<br> bloque : FOR variable coleccion '{' cuerpo '}'
 *
 * @author César Pomar
 */
public final class BloqueForeachVar extends Bloque {

    private Terminal id;
    protected AbrirBloque abrirBloque;
    private Variable variable;
    private Coleccion coleccion;

    /**
     * Único contructor de la clase
     *
     * @param id Id
     * @param abrirBloque Abertura de bloque para la cabecera
     * @param variable Variable
     * @param coleccion Coleccion
     * @param contexto Contexto del bloque
     */
    public BloqueForeachVar(Terminal id, AbrirBloque abrirBloque, Variable variable, Coleccion coleccion, Contexto contexto) {
        super(contexto);
        setId(id);
        setAbrirBloque(abrirBloque);
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
     * Obtiene la abertura de bloque
     *
     * @return Abertura de bloque
     */
    public AbrirBloque getAbrirBloque() {
        return abrirBloque;
    }

    /**
     * Establece la abertura de bloque
     *
     * @param abrirBloque Abertura de bloque
     */
    public void setAbrirBloque(AbrirBloque abrirBloque) {
        abrirBloque.setPadre(this);
        this.abrirBloque = abrirBloque;
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
        return new Simbolo[]{id, variable, coleccion, contexto};
    }
}
