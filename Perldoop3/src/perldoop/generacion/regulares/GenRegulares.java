package perldoop.generacion.regulares;

import perldoop.generacion.asignacion.GenIgual;
import perldoop.generacion.util.Casting;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.regulares.RegularMatch;
import perldoop.modelo.arbol.regulares.RegularNoMatch;
import perldoop.modelo.arbol.regulares.RegularSubs;
import perldoop.modelo.arbol.regulares.RegularTrans;
import perldoop.modelo.arbol.regulares.Regulares;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de regulares
 *
 * @author César Pomar
 */
public class GenRegulares {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenRegulares(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    /**
     * Genera los modificadores del match y la substitución
     *
     * @param s Simbolo regular
     * @return Codigo relacionado con los modificadores
     */
    private StringBuilder genMods(Regulares s) {     
        if(s.getModificadores()==null){
            return new StringBuilder("\"\",false");
        }
        StringBuilder codigo = new StringBuilder(50);
        codigo.append(s.getModificadores().getValor().replaceAll("[og]", "")).append(",");
        codigo.append(s.getModificadores().getValor().contains("g")); 
        codigo.append(s.getModificadores().getComentario());
        return codigo;
    }

    public void visitar(RegularMatch s) {
        StringBuilder codigo = new StringBuilder(100);
        if(s.getTipo().isArray()){
            codigo.append("Regex.matcher(");
        }else{
            codigo.append("Regex.match(");
        }
        codigo.append(Casting.toString(s.getExpresion())).append(",");
        codigo.append(s.getPatron()).append(",");
        codigo.append(genMods(s));
        codigo.append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(RegularNoMatch s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("!Regex.match(");
        codigo.append(Casting.toString(s.getExpresion())).append(",");
        codigo.append(s.getPatron()).append(",");
        codigo.append(genMods(s));
        codigo.append(")");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(RegularSubs s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Regex.substitution(");
        codigo.append(Casting.toString(s.getExpresion())).append(",");
        codigo.append(s.getPatron()).append(",");
        codigo.append(s.getRemplazo()).append(",");
        codigo.append(genMods(s));
        codigo.append(")");
        s.setCodigoGenerado(GenIgual.asignacion(s.getExpresion(), "", new SimboloAux(s.getTipo(), codigo)));
    }

    public void visitar(RegularTrans s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Regex.translation(");
        codigo.append(Casting.toString(s.getExpresion())).append(",");
        codigo.append(s.getPatron()).append(",");
        codigo.append(s.getRemplazo()).append(",");
        if(s.getModificadores()==null){
            codigo.append("\"\"");
        }else{
            codigo.append(s.getModificadores());
        }
        codigo.append(")");
        s.setCodigoGenerado(GenIgual.asignacion(s.getExpresion(), "", new SimboloAux(s.getTipo(), codigo)));
    }

}
