package perldoop.generacion.sentencia;

import java.util.Iterator;
import java.util.List;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.aritmetica.AritPostDecremento;
import perldoop.modelo.arbol.aritmetica.AritPostIncremento;
import perldoop.modelo.arbol.aritmetica.AritPreDecremento;
import perldoop.modelo.arbol.aritmetica.AritPreIncremento;
import perldoop.modelo.arbol.asignacion.Asignacion;
import perldoop.modelo.arbol.coleccion.ColDec;
import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.lectura.Lectura;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.modificador.ModNada;
import perldoop.modelo.arbol.regulares.*;
import perldoop.modelo.arbol.sentencia.*;
import perldoop.modelo.arbol.variable.*;
import perldoop.modelo.generacion.Declaracion;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.util.Buscar;

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

    /**
     * Genera las declaraciones generadas por hijos del simbolo s
     *
     * @param s Simbolo respomsable de la declaración
     * @param tabla Tabla de generacion de codigo
     * @return Declaraciones
     */
    public static StringBuilder genDeclaraciones(Simbolo s, TablaGenerador tabla) {
        StringBuilder codigo = new StringBuilder(100);
        Iterator<Declaracion> it = tabla.getDeclaraciones().iterator();
        while (it.hasNext()) {
            Declaracion dec = it.next();
            if (dec.getSimbolo() == null || Buscar.isHijo(dec.getSimbolo(), s)) {
                if (dec.getTipo() != null) {
                    codigo.append(Tipos.declaracion(dec.getTipo())).append(" ");
                }
                codigo.append(dec.getNombre());
                if (dec.getValor() != null) {
                    codigo.append("=").append(dec.getValor());
                }
                codigo.append(";");
                it.remove();
            }
        }
        return codigo;
    }

    public void visitar(StcLista s) {
        StringBuilder codigo = new StringBuilder(300);
        codigo.append(genDeclaraciones(s, tabla));
        boolean mod = !(s.getModificador() instanceof ModNada);
        if (mod) {
            codigo.append(s.getModificador()).append("{");
        }
        if (!tabla.getOpciones().isOptSentencias()) {
            Iterator<Terminal> it = s.getLista().getSeparadores().iterator();
            for (Expresion exp : s.getLista().getExpresiones()) {
                codigo.append(exp).append(";");
                if (it.hasNext()) {
                    codigo.append(it.next().getComentario());
                }
            }
        } else {
            sentenciasOpt(s.getLista(), codigo);
        }
        codigo.append(s.getPuntoComa().getComentario());
        if (mod) {
            codigo.append("}");
        }
        s.setCodigoGenerado(codigo);
    }

    public void visitar(StcBloque s) {
        s.setCodigoGenerado(genDeclaraciones(s, tabla).append(s.getBloque()));
    }

    public void visitar(StcFlujo s) {
        s.setCodigoGenerado(genDeclaraciones(s, tabla).append(s.getFlujo()));
    }

    public void visitar(StcComentario s) {
        s.setCodigoGenerado(new StringBuilder(s.getComentario().getCodigoGenerado()).insert(0, '\n').append('\n'));
    }

    public void visitar(StcTipado s) {
        s.setCodigoGenerado(new StringBuilder(0));
    }

    public void visitar(StcModulos s) {
        s.setCodigoGenerado(genDeclaraciones(s, tabla).append(s.getModulos().getCodigoGenerado()));
    }

    public void visitar(StcImport s) {
        s.setCodigoGenerado(new StringBuilder(s.getImportJava().toString().substring("#<java-import>".length())));
    }

    public void visitar(StcLineaJava s) {
        s.setCodigoGenerado(new StringBuilder(s.getLineaJava().toString().substring("#<java-line>".length())));
    }

    /**
     * Optimiza la generación de sentencias
     *
     * @param lista Lista
     * @param codigo Cadena para almacenar el codigo
     */
    public void sentenciasOpt(Lista lista, StringBuilder codigo) {
        Iterator<Terminal> it = lista.getSeparadores().iterator();
        for (Expresion exp : lista.getExpresiones()) {
            exp = Buscar.getExpresion(exp);
            if (exp instanceof ExpNumero) {
                //Ignoramos numero
            } else if (exp instanceof ExpCadena) {
                //Ignoramos cadena
            } else if (exp instanceof ExpVariable || exp instanceof ExpAsignacion
                    || (exp instanceof ExpColeccion && ((ExpColeccion) exp).getColeccion() instanceof ColDec)) {
                codigo.append(exp).append(";");
            } else if (exp instanceof ExpBinario || exp instanceof ExpAritmetica || exp instanceof ExpLogico
                    || exp instanceof ExpComparacion || exp instanceof ExpColeccion || exp instanceof ExpAcceso) {
                List<Simbolo> sentencias = Buscar.buscarClases(exp, VarMy.class, VarOur.class, Funcion.class, Asignacion.class, AritPreIncremento.class, AritPreDecremento.class, AritPostIncremento.class,
                        AritPostDecremento.class, Lectura.class);
                for (Simbolo sentencia : sentencias) {
                    codigo.append("Pd.eval(").append(sentencia).append(");");
                }
            } else if (exp instanceof ExpFuncion || exp instanceof ExpFuncion5) {
                codigo.append(exp).append(";");
            } else if (exp instanceof ExpLectura) {
                codigo.append(exp).append(";");;
            } else if (exp instanceof ExpStd) {
                //Ignoramos std
            } else if (exp instanceof ExpRegulares) {
                Regulares r = ((ExpRegulares) exp).getRegulares();
                if (r instanceof RegularSubs || r instanceof RegularTrans) {
                    codigo.append(exp).append(";");
                }
            } else if (exp instanceof ExpRango) {
                //Ignoramos el rango
            }
            //Comentario separador
            if (it.hasNext()) {
                codigo.append(it.next().getComentario());
            }
        }
    }

}
