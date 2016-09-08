package perldoop.generacion.sentencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.asignacion.Asignacion;
import perldoop.modelo.arbol.constante.*;
import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.regulares.*;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.sentencia.*;
import perldoop.modelo.arbol.variable.*;

/**
 * Clase generadora de sentencia
 *
 * @author César Pomar
 */
public class GenSentencia {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenSentencia(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    public void visitar(StcLista s) {
        StringBuilder codigo = new StringBuilder(200);
        if (!tabla.getDeclaraciones().isEmpty()) {
            for (StringBuilder d : tabla.getDeclaraciones()) {
                codigo.append(d);
            }
            tabla.getDeclaraciones().clear();
        }
        for (Expresion exp : s.getLista().getExpresiones()) {
            boolean sentencia = false;
            //Calculamos las sentencias
            if (exp instanceof ExpVariable) {
                Variable var = ((ExpVariable) exp).getVariable();
                if (var instanceof VarMy) {
                    if (tabla.getTablaSimbolos().getBloques() == 1) {
                        continue;//los atributos ya se han declarado
                    }
                    sentencia = true;
                } else {
                    continue;//Las lecturas de variables no sirven para nada
                }
            } else if (exp instanceof ExpConstante) {
                Constante cst = ((ExpConstante) exp).getConstante();
                if (cst instanceof CadenaComando) {
                    sentencia = true;
                } else {
                    continue;//Las constantes no sirven para nada
                }
            } else if (exp instanceof ExpAsignacion) {
                sentencia = true;
            } else if (exp instanceof ExpFuncion) {
                sentencia = true;
            } else if (exp instanceof ExpFuncion5) {
                sentencia = true;
            } else if (exp instanceof ExpRegulares) {
                Regulares reg = ((ExpRegulares) exp).getRegulares();
                if (reg instanceof RegularSubs || reg instanceof RegularTrans) {
                    sentencia = true;
                }
            }
            //Analizamos todo en subarbol en busca de algo que merezca ser ejecutado
            if (tabla.getOpciones().isSentenciasEstrictas() && !sentencia) {
                List<Simbolo> hijos = new ArrayList<>(100);
                hijos.add(exp);
                while (!hijos.isEmpty()) {
                    Simbolo hijo = hijos.remove(hijos.size() - 1);
                    hijos.addAll(Arrays.asList(hijo.getHijos()));
                    if (hijo instanceof Funcion || hijo instanceof Asignacion) {
                        sentencia = true;
                        break;
                    }
                }
                if (!sentencia) {
                    continue;
                }
            }
            //Si es sentencia la escribimos, si no usamos la funcion auxiliar para evaluarla
            if (sentencia) {
                codigo.append(exp.getCodigoGenerado()).append(";");
            } else {
                codigo.append("Perl.eval(").append(exp.getCodigoGenerado()).append(");");
            }
        }
        s.setCodigoGenerado(codigo);
    }

    public void visitar(StcBloque s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(StcFlujo s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void visitar(StcPaquete s) {
        tabla.getClase().setNombre(s.getId().toString());
        for (Terminal p : s.getPaquetes().getIdentificadores()) {
            tabla.getClase().getPaquetes().add(p.toString());
        }
        s.setCodigoGenerado(new StringBuilder(0));
    }

    public void visitar(StcComentario s) {
        s.setCodigoGenerado(new StringBuilder(s.getComentario().getCodigoGenerado()).insert(0, '\n'));
    }

    public void visitar(StcDeclaracion s) {
        //No genera codigo
    }

}
