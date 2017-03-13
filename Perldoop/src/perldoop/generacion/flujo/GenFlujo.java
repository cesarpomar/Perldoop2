package perldoop.generacion.flujo;

import perldoop.generacion.util.Casting;
import perldoop.modelo.arbol.flujo.Last;
import perldoop.modelo.arbol.flujo.Next;
import perldoop.modelo.arbol.flujo.Return;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase generadora de flujo
 *
 * @author César Pomar
 */
public class GenFlujo {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenFlujo(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(Next s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("continue").append(s.getId().getComentario());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(Last s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("break").append(s.getId().getComentario());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(Return s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(s.getId()).append(" ");
        if (s.getExpresion() != null) {
            if(s.getExpresion().getTipo().isColeccion()){
                codigo.append(Casting.casting(s.getExpresion(), new Tipo(Tipo.ARRAY,Tipo.BOX)));
            }else{
                codigo.append("new Box[]{");
                codigo.append(Casting.toBox(s.getExpresion()));
                codigo.append("}");
            }
        }
        s.setCodigoGenerado(codigo);
    }

}
