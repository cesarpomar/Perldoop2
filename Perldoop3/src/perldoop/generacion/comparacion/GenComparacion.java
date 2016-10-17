package perldoop.generacion.comparacion;

import perldoop.generacion.util.Casting;
import perldoop.modelo.arbol.comparacion.*;
import perldoop.modelo.arbol.comparacion.Comparacion;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Clase generadora de comparacion
 *
 * @author César Pomar
 */
public class GenComparacion {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenComparacion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    /**
     * Comapara dos numeros
     *
     * @param s Comparacion
     */
    private void compNum(Comparacion s) {
        StringBuilder codigo = new StringBuilder(100);
        if (tabla.getOpciones().isOptNulos() || (Buscar.isNotNull(s.getIzquierda()) && Buscar.isNotNull(s.getDerecha()))) {
            codigo.append(Casting.toNumber(s.getIzquierda()));
            codigo.append(s.getOperador());
            codigo.append(Casting.toNumber(s.getDerecha()));
        } else {
            Tipo t = new Tipo(Tipo.NUMBER);
            codigo.append("Pd.compare(");
            codigo.append(Casting.castingNotNull(s.getIzquierda(), t));
            codigo.append(',');
            codigo.append(Casting.castingNotNull(s.getDerecha(), t));
            codigo.append(")");
            codigo.append(s.getOperador());
            codigo.append("0");
        }
        s.setCodigoGenerado(codigo);
    }

    /**
     * Compara dos cadenas
     *
     * @param s Comparacion
     * @param operacion Operacion
     */
    private void compSrt(Comparacion s, String operacion) {
        StringBuilder codigo = new StringBuilder(100);
        Tipo t = new Tipo(Tipo.STRING);
        codigo.append("Pd.compare(");
        codigo.append(Casting.castingNotNull(s.getIzquierda(), t));
        codigo.append(',');
        codigo.append(Casting.castingNotNull(s.getDerecha(), t));
        codigo.append(")");
        codigo.append(operacion).append(s.getOperador().getComentario());
        codigo.append("0");
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CompNumEq s) {
        compNum(s);
    }

    public void visitar(CompNumNe s) {
        compNum(s);
    }

    public void visitar(CompNumLt s) {
        compNum(s);
    }

    public void visitar(CompNumLe s) {
        compNum(s);
    }

    public void visitar(CompNumGt s) {
        compNum(s);
    }

    public void visitar(CompNumGe s) {
        compNum(s);
    }

    public void visitar(CompNumCmp s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Pd.compare(");
        if (tabla.getOpciones().isOptNulos() || (Buscar.isNotNull(s.getIzquierda()) && Buscar.isNotNull(s.getDerecha()))) {
            codigo.append(Casting.toNumber(s.getIzquierda()));
            codigo.append(',');
            codigo.append(Casting.toNumber(s.getDerecha()));
        } else {
            Tipo t = new Tipo(Tipo.NUMBER);
            codigo.append(Casting.castingNotNull(s.getIzquierda(), t));
            codigo.append(',');
            codigo.append(Casting.castingNotNull(s.getDerecha(), t));
        }
        codigo.append(")").append(s.getOperador().getComentario());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CompStrEq s) {
        compSrt(s, "==");
    }

    public void visitar(CompStrNe s) {
        compSrt(s, "!=");
    }

    public void visitar(CompStrLt s) {
        compSrt(s, "<");
    }

    public void visitar(CompStrLe s) {
        compSrt(s, "<=");
    }

    public void visitar(CompStrGt s) {
        compSrt(s, ">");
    }

    public void visitar(CompStrGe s) {
        compSrt(s, ">=");
    }

    public void visitar(CompStrCmp s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("Pd.compare(");
        if (tabla.getOpciones().isOptNulos() || (Buscar.isNotNull(s.getIzquierda()) && Buscar.isNotNull(s.getDerecha()))) {
            codigo.append(Casting.toString(s.getIzquierda()));
            codigo.append(',');
            codigo.append(Casting.toString(s.getDerecha()));
        } else {
            Tipo t = new Tipo(Tipo.STRING);
            codigo.append(Casting.castingNotNull(s.getIzquierda(), t));
            codigo.append(',');
            codigo.append(Casting.castingNotNull(s.getDerecha(), t));
        }
        codigo.append(")").append(s.getOperador().getComentario());
        s.setCodigoGenerado(codigo);
    }

    public void visitar(CompSmart s) {
    }

}
