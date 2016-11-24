package perldoop.generacion.coleccion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.acceso.Acceso;
import perldoop.modelo.arbol.acceso.AccesoCol;
import perldoop.modelo.arbol.acceso.AccesoColRef;
import perldoop.modelo.arbol.acceso.AccesoDesRef;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColDec;
import perldoop.modelo.arbol.coleccion.ColDecMy;
import perldoop.modelo.arbol.coleccion.ColDecOur;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.Funcion;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Clase generadora de coleccion
 *
 * @author César Pomar
 */
public class GenColeccion {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenColeccion(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    /**
     * Genera la referencia cuando es necesario
     *
     * @param c Coleccion
     */
    private void genRef(Coleccion c) {
        //Si no es una referencia
        if (!c.getTipo().isRef()) {
            return;
        }
        if (c.getPadre() instanceof Expresion) {
            Simbolo uso = Buscar.getUso((Expresion) c.getPadre());
            //Si es un acceso
            if (!Buscar.getCamino(uso, ColLlave.class, ExpColeccion.class, Acceso.class).isEmpty()) {
                return;
            }
            //Si es una coleccion
            Coleccion col = Buscar.getColeccion(c);
            while (col != null) {
                if (col.getTipo() == null) {
                    break;
                }
                if ((!col.getTipo().equals(c.getTipo()) || col.getLista().getExpresiones().size() > 1) && !col.getTipo().getSubtipo(1).isBox()) {
                    return;
                }
                col = Buscar.getColeccion(col);
            }
        }
        c.getCodigoGenerado().insert(0, Tipos.declaracion(c.getTipo()).insert(0, "new ").append('(')).append(')');
    }

    /**
     * Genera la coleccion como Array o List
     *
     * @param expresiones Expresiones
     * @param seps Separadores
     * @param t Tipo
     * @return Codigo
     */
    public static StringBuilder genColeccion(List<? extends Simbolo> expresiones, List<Terminal> seps, Tipo t) {
        if (expresiones.isEmpty()) {
            if (t.isArray()) {
                return Tipos.inicializacion(t, "0");
            }
            return Tipos.inicializacion(t);
        }
        //Subtipo expresiones
        Tipo st = t.getSubtipo(1);
        if (st.isColeccion()) {
            st.add(0, Tipo.REF);
        }
        //Colecciones generadas en el proceso
        List<Simbolo> colecciones = new ArrayList<>(10);
        List<String> comentarios = new ArrayList<>(10);
        //Expresiones consecutivas
        List<Simbolo> consecutivas = new ArrayList<>(expresiones.size());
        List<String> conComentarios = new ArrayList<>(expresiones.size());
        Iterator<? extends Simbolo> it = expresiones.iterator();
        Iterator<Terminal> itSep = seps.iterator();
        //Convertimos todas las expresiones en colecciones
        while (it.hasNext()) {
            Simbolo exp = it.next();
            boolean iscoleccion = exp.getTipo().isColeccion();
            if (!iscoleccion) {
                consecutivas.add(exp);
                if (itSep.hasNext()) {
                    conComentarios.add(itSep.next().getComentario());
                }
            }
            if (!consecutivas.isEmpty() && (!it.hasNext() || exp.getTipo().isColeccion())) {
                if (t.isArrayOrList()) {
                    colecciones.add(genArrayListExps(t, consecutivas, conComentarios));
                } else {
                    colecciones.add(genMapExps(t, consecutivas, conComentarios));
                }
                consecutivas.clear();
                conComentarios.clear();
            }
            //Si es una coleccion habra que agregarla
            if (!iscoleccion) {
                continue;
            }
            if (exp.getTipo().getSubtipo(1).equals(st) && exp.getTipo().isArrayOrList() && t.isArrayOrList()) {
                colecciones.add(exp);
            } else {
                colecciones.add(new SimboloAux(t, Casting.casting(exp, t)));
            }
            if (itSep.hasNext()) {
                conComentarios.add(itSep.next().getComentario());
            }
        }
        //Unimos todas las colecciones en una
        if (colecciones.size() == 1) {
            return Casting.casting(colecciones.get(0), t);
        } else {
            StringBuilder codigo = new StringBuilder(500);
            it = colecciones.iterator();
            Iterator<String> itC = comentarios.iterator();
            codigo.append("Pd.union()");
            while (it.hasNext()) {
                codigo.append(".append(").append(it.next()).append(")");
                if (itC.hasNext()) {
                    codigo.append(itC.next());
                }
            }
            if (t.isArray()) {
                codigo.append(".toArray(").append(Tipos.declaracion(t)).append(".class)");
            } else if (t.isList()) {
                codigo.append(".toList()");
            } else {
                codigo.append(".toMap()");
            }
            return codigo;
        }
    }

    /**
     * Genera una lista o un array con expresiones simples
     *
     * @param t Tipo del array
     * @param exps Expresiones
     * @param comentarios Comentarios
     * @return Simbolo coleccion
     */
    private static Simbolo genArrayListExps(Tipo t, List<Simbolo> exps, List<String> comentarios) {
        StringBuilder codigo = new StringBuilder(100);
        String cierre;
        if (t.isArray()) {
            codigo.append(Tipos.inicializacion(t)).append("{");
            cierre = "}";
        } else {
            codigo.append(Tipos.inicializacion(t)).setLength(codigo.length() - 1);
            cierre = ")";
            //Caso especial, un elemento de tipo integer se confunde con tamaño
            if(exps.size()==1 && exps.get(0).getTipo().isInteger()){
                codigo.append(Tipos.inicializacion(t.getSubtipo(1).add(0, Tipo.ARRAY))).append("{");
                cierre = "})";
            }
        }
        //Subtipo expresiones
        Tipo st = t.getSubtipo(1);
        if (st.isColeccion()) {
            st.add(0, Tipo.REF);
        }
        Iterator<Simbolo> it = exps.iterator();
        Iterator<String> itC = comentarios.iterator();
        while (it.hasNext()) {
            Simbolo elem = it.next();
            codigo.append(Casting.casting(elem, st));
            //Separador
            if (it.hasNext()) {
                codigo.append(",");
            }
            //Comentario
            if (itC.hasNext()) {
                codigo.append(itC.next());
            }
        }
        codigo.append(cierre);
        return new SimboloAux(t, codigo);
    }

    /**
     * Genera una lista o un array con expresiones simples alternando clave y valor
     *
     * @param t Tipo del array
     * @param exps Expresiones
     * @param comentarios Comentarios
     * @return Simbolo coleccion
     */
    private static Simbolo genMapExps(Tipo t, List<Simbolo> exps, List<String> comentarios) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(Tipos.inicializacion(t)).setLength(codigo.length() - 1);
        //Subtipo expresiones
        Tipo st = t.getSubtipo(1);
        if (st.isColeccion()) {
            st.add(0, Tipo.REF);
        }
        Iterator<Simbolo> it = exps.iterator();
        Iterator<String> itC = comentarios.iterator();
        StringBuilder claves = new StringBuilder(100);
        StringBuilder valores = new StringBuilder(100);
        boolean clave = true;
        while (it.hasNext()) {
            if (clave) {
                claves.append(Casting.casting(it.next(), new Tipo(Tipo.STRING)));
            } else {
                valores.append(Casting.casting(it.next(), st));
                //Separador
                if (it.hasNext()) {
                    claves.append(",");
                    valores.append(",");
                }
                //Comentarios
                claves.append(itC.next());
                if (itC.hasNext()) {
                    valores.append(itC.next());
                }
            }
            clave = !clave;
        }
        codigo.append("new String[]{").append(claves).append("}, ");
        codigo.append(Tipos.inicializacion(t.getSubtipo(1).add(0, Tipo.ARRAY))).append("{").append(valores).append("}");
        codigo.append(")");
        return new SimboloAux(t, codigo);
    }

    /**
     * Concatena las declaraciones
     *
     * @param c Coleccion de variables
     */
    private void genDec(ColDec c) {
        if (!(Buscar.getUso((Expresion) c.getPadre()) instanceof StcLista)) {
            visitar(c);
            if(c.getCodigoGenerado()!=null){
                c.getCodigoGenerado().insert(0, c.getOperador().getComentario());
            }
            return;
        }
        StringBuilder codigo = new StringBuilder(100);
        Iterator<Expresion> itExp = c.getLista().getExpresiones().iterator();
        Iterator<Terminal> itSep = c.getLista().getSeparadores().iterator();
        codigo.append(c.getOperador().getComentario());
        codigo.append(c.getParentesisI().getComentario());
        while (itExp.hasNext()) {
            codigo.append(itExp.next());
            if (itExp.hasNext()) {
                codigo.append(";");
            }
            if (itSep.hasNext()) {
                codigo.append(itSep.next().getComentario());
            }
        }
        codigo.append(c.getParentesisD().getComentario());
        c.setCodigoGenerado(codigo);
    }

    public void visitar(ColParentesis s) {
        if (s.getTipo() == null) {
            return;
        }
        StringBuilder codigo;
        //Si es un simple valor, podemos los parentesis y listo
        if (s.getLista().getExpresiones().size() == 1 && !(s.getPadre() instanceof Funcion) && !s.getTipo().isColeccion()) {
            codigo = new StringBuilder(100);
            codigo.append(Casting.casting(s.getLista().getExpresiones().get(0), s.getTipo()));
            if (!s.getLista().getSeparadores().isEmpty()) {
                codigo.append(s.getLista().getSeparadores().get(0).getComentario());
            }
            //Comprobar si hay parentesis
            if (!s.isVirtual()) {
                codigo.insert(0, s.getParentesisI());
                codigo.append(s.getParentesisD());
            }
        } else {
            codigo = genColeccion(s.getLista().getExpresiones(), s.getLista().getSeparadores(), s.getTipo());
            //Comprobar si hay parentesis
            if (!s.isVirtual()) {
                codigo.insert(0, s.getParentesisI().getComentario());
                codigo.append(s.getParentesisD().getComentario());
            }
        }
        s.setCodigoGenerado(codigo);
    }

    public void visitar(ColDecMy s) {
        genDec(s);
    }

    public void visitar(ColDecOur s) {
        genDec(s);
    }

    public void visitar(ColCorchete s) {
        //Si es un acceso con un valor
        if ((s.getPadre() instanceof AccesoCol || s.getPadre() instanceof AccesoColRef) && s.getLista().getExpresiones().size() == 1) {
            Expresion exp = s.getLista().getExpresiones().get(0);
            s.setCodigoGenerado(Casting.casting(exp, s.getTipo()));
        } else {
            Tipo t = s.getTipo();
            if (t.isRef()) {
                t = t.getSubtipo(1);
            }
            s.setCodigoGenerado(genColeccion(s.getLista().getExpresiones(), s.getLista().getSeparadores(), t));
            genRef(s);
        }
        s.getCodigoGenerado().insert(0, s.getCorcheteI().getComentario());
        s.getCodigoGenerado().append(s.getCorcheteD().getComentario());
    }

    public void visitar(ColLlave s) {
        Simbolo uso = s.getPadre().getPadre();
        if (((s.getPadre() instanceof AccesoCol || s.getPadre() instanceof AccesoColRef) && s.getLista().getExpresiones().size() == 1)
                || (uso instanceof AccesoDesRef)) {
            Expresion exp = s.getLista().getExpresiones().get(0);
            s.setCodigoGenerado(Casting.casting(exp, s.getTipo()));
        } else {
            Tipo t = s.getTipo();
            if (t.isRef()) {
                t = t.getSubtipo(1);//Quitar ref
            }
            s.setCodigoGenerado(genColeccion(s.getLista().getExpresiones(), s.getLista().getSeparadores(), t));
            genRef(s);
        }
        s.getCodigoGenerado().insert(0, s.getLlaveI().getComentario());
        s.getCodigoGenerado().append(s.getLlaveD().getComentario());
    }

}
