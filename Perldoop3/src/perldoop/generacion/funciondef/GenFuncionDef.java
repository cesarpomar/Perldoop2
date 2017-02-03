package perldoop.generacion.funciondef;

import java.util.List;
import java.util.ListIterator;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueDoUntil;
import perldoop.modelo.arbol.bloque.BloqueDoWhile;
import perldoop.modelo.arbol.bloque.BloqueIf;
import perldoop.modelo.arbol.bloque.BloqueSimple;
import perldoop.modelo.arbol.bloque.BloqueUnless;
import perldoop.modelo.arbol.bloque.SubBloqueElse;
import perldoop.modelo.arbol.bloque.SubBloqueElsif;
import perldoop.modelo.arbol.flujo.Return;
import perldoop.modelo.arbol.funciondef.FuncionDef;
import perldoop.modelo.arbol.modificador.ModNada;
import perldoop.modelo.arbol.sentencia.Sentencia;
import perldoop.modelo.arbol.sentencia.StcFlujo;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.util.Buscar;

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
        codigo.append(genReturn(s));
        codigo.append(s.getLlaveD());
        s.setCodigoGenerado(codigo);
    }

    /**
     * Comprueba que la funcion siempre genere un return
     *
     * @param f Funcion
     * @return Codigo return
     */
    private String genReturn(FuncionDef f) {
        List<Sentencia> sentencias = f.getCuerpo().getSentencias();
        if (!sentencias.isEmpty()) {
            //Asumiendo que el codigo inalcanzable ya ha sido comprobado, buscamos retorno en la ultima sentencia
            List<Return> retornos = Buscar.buscarClases(sentencias.get(sentencias.size() - 1), Return.class);
            ListIterator<Return> it = retornos.listIterator(retornos.size());
            Simbolo condicion = null;
            WHILE:
            while (it.hasPrevious()) {
                Return r = it.previous();
                Simbolo padre = r.getPadre();
                //Si tiene un modificador ya no es seguro su ejecución
                if (!(((StcFlujo) padre).getModificador() instanceof ModNada)) {
                    continue;
                }
                while (padre != f) {
                    //Si el padre es el bloque condicional, podemos ascender
                    if (padre.getPadre() == condicion) {
                        padre = condicion.getPadre();
                        continue;
                    }
                    //Si es un bloque condicional lo almacenamos en espera del superbloque
                    if (padre instanceof BloqueIf || padre instanceof BloqueUnless || padre instanceof SubBloqueElsif || padre instanceof SubBloqueElse) {
                        condicion = padre.getPadre();
                    }
                    //Si el bloque depende de una condicion, se asume que no se ejecutara
                    if (padre instanceof Bloque && !(padre instanceof BloqueSimple) && !(padre instanceof BloqueDoUntil) && !(padre instanceof BloqueDoWhile)) {
                        continue WHILE;
                    }
                    padre = padre.getPadre();
                }
                return "";
            }
        }
        return "return new Box[0];";
    }

}
