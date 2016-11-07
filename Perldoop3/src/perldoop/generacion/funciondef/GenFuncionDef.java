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
        StringBuilder codigo = new StringBuilder(s.getContexto().getCuerpo().getCodigoGenerado().length() + 50);
        codigo.append(s.getFuncionSub().getCodigoGenerado());
        codigo.append(s.getContexto().getLlaveI().getCodigoGenerado());
        codigo.append(s.getContexto().getCuerpo().getCodigoGenerado());
        List<Sentencia> sentencias = s.getContexto().getCuerpo().getSentencias();
        if (sentencias.isEmpty()) {
            codigo.append(new StringBuilder("return new Box[0];"));
        } else {
            //TODO solucion temporal
            Sentencia sen = sentencias.get(sentencias.size() - 1);
            if (!(sen instanceof StcFlujo && ((StcFlujo) sen).getFlujo() instanceof Return)) {
                codigo.append(new StringBuilder("return new Box[0];"));
            }
        }
        codigo.append(s.getContexto().getLlaveD());
        s.setCodigoGenerado(codigo);
    }

}
