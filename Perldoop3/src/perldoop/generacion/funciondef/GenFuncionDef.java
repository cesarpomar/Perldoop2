package perldoop.generacion.funciondef;

import java.util.List;
import perldoop.modelo.arbol.flujo.Return;
import perldoop.modelo.arbol.funciondef.FuncionDef;
import perldoop.modelo.arbol.sentencia.Sentencia;
import perldoop.modelo.arbol.sentencia.StcFlujo;
import perldoop.modelo.generacion.TablaGenerador;

/**
 * Clase generadora de funcionDef
 *
 * @author César Pomar
 */
public class GenFuncionDef {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenFuncionDef(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionDef s) {
        StringBuilder codigo = new StringBuilder(s.getCuerpo().getCodigoGenerado().length() + 50);
        codigo.append(s.getFuncionSub().getCodigoGenerado());
        codigo.append(s.getLlaveI().getCodigoGenerado());
        codigo.append(s.getCuerpo().getCodigoGenerado());
        List<Sentencia> sentencias = s.getCuerpo().getSentencias();
        if (sentencias.isEmpty()) {
            codigo.append(new StringBuilder("return new Box[0];"));
        } else {
            Sentencia sen = sentencias.get(sentencias.size() - 1);
            if (!(sen instanceof StcFlujo && ((StcFlujo) sen).getFlujo() instanceof Return)) {
                codigo.append(new StringBuilder("return new Box[0];"));
            }
        }
        codigo.append(s.getLlaveD());
        s.setCodigoGenerado(codigo);
    }

}
