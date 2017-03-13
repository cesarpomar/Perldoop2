package perldoop.generacion.regulares;

import perldoop.generacion.acceso.GenAcceso;
import perldoop.generacion.util.Casting;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.regulares.RegularMatch;
import perldoop.modelo.arbol.regulares.RegularNoMatch;
import perldoop.modelo.arbol.regulares.RegularSubs;
import perldoop.modelo.arbol.regulares.RegularTrans;
import perldoop.modelo.arbol.regulares.Regulares;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.util.Buscar;

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
        if (s.getModificadores() == null) {
            return new StringBuilder("\"\",false");
        }
        StringBuilder codigo = new StringBuilder(50);
        codigo.append('"').append(s.getModificadores().getValor().replaceAll("[og]", "")).append('"').append(",");
        codigo.append(s.getModificadores().getValor().contains("g"));
        codigo.append(s.getModificadores().getComentario());
        return codigo;
    }

    public void visitar(RegularMatch s) {
        StringBuilder codigo = new StringBuilder(100);
        if (s.getTipo().isArray()) {
            codigo.append("Regex.matcher(");
        } else {
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
        //Generar codigo lectura escritura
        Simbolo lectura = new SimboloAux(s.getExpresion());
        Simbolo escritura = new SimboloAux(s.getExpresion());
        if (Buscar.getExpresion(s.getExpresion()) instanceof ExpAcceso) {
            GenAcceso.getReplica((ExpAcceso) s.getExpresion(), lectura, escritura, tabla);
        }
        //Expresion regular
        StringBuilder regex = new StringBuilder(100);
        regex.append("Regex.substitution(");
        regex.append(Casting.toString(lectura)).append(",");
        regex.append(s.getPatron()).append(",");
        regex.append(s.getRemplazo()).append(",");
        regex.append(genMods(s));
        regex.append(")");
        //Actualizacion de variable
        regex = Casting.casting(new SimboloAux(s.getTipo(), regex), escritura.getTipo());
        if (Buscar.isArrayOrVar(s.getExpresion())) {
            codigo.append(escritura).append("=").append(regex);
        } else {
            codigo.append(escritura).append(regex).append(")");
        }
        s.setCodigoGenerado(codigo);
    }

    public void visitar(RegularTrans s) {
        StringBuilder codigo = new StringBuilder(100);
        //Generar codigo lectura escritura
        Simbolo lectura = new SimboloAux(s.getExpresion());
        Simbolo escritura = new SimboloAux(s.getExpresion());
        if (Buscar.getExpresion(s.getExpresion()) instanceof ExpAcceso) {
            GenAcceso.getReplica((ExpAcceso) s.getExpresion(), lectura, escritura, tabla);
        }
        //Expresion regular
        StringBuilder regex = new StringBuilder(100);
        regex.append("Regex.translation(");
        regex.append(Casting.toString(s.getExpresion())).append(",");
        regex.append(s.getPatron()).append(",");
        regex.append(s.getRemplazo()).append(",");
        if (s.getModificadores() == null) {
            regex.append("\"\"");
        } else {
            regex.append(s.getModificadores());
        }
        regex.append(")");
        //Actualizacion de variable
        regex = Casting.casting(new SimboloAux(s.getTipo(), regex), escritura.getTipo());
        if (Buscar.isArrayOrVar(s.getExpresion())) {
            codigo.append(escritura).append("=").append(regex);
        } else {
            codigo.append(escritura).append(regex).append(")");
        }
        s.setCodigoGenerado(codigo);
    }

}
