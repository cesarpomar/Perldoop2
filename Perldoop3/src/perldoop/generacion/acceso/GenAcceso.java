package perldoop.generacion.acceso;

import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.SimboloAux;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColCorchete;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

/**
 * Clase generadora de acceso
 *
 * @author César Pomar
 */
public class GenAcceso {

    private TablaGenerador tabla;

    /**
     * Construye el generador
     *
     * @param tabla Tabla
     */
    public GenAcceso(TablaGenerador tabla) {
        this.tabla = tabla;
    }

    /**
     * Accede a una o varias posiciones dentro de una expresion de tipo colección
     *
     * @param s Simbolo del Acceso
     * @param expresion Codigo de la expresión
     * @param coleccion Coleccion de indices para el acceso
     * @param comentarioRef Comentario a escribir entre la expresión y su acceso
     */
    private void accesoColeccion(Acceso s, StringBuilder expresion, Coleccion coleccion, String comentarioRef) {
        StringBuilder codigo = new StringBuilder(100);
        boolean escritura = false;//Acceso para escribir
        boolean noRef = false;//No generar referencia
        Simbolo uso = Buscar.getUso((Expresion) s.getPadre());
        //Si hay otro acceso anidado o una desreferenciacion, obviamos crear la referencia
        if (uso instanceof Acceso || (Buscar.isCamino(uso, ColLlave.class, ExpColeccion.class)
                && Buscar.getUso((Expresion) Buscar.getPadre(uso, 1)) instanceof AccesoDesRef)) {
            noRef = true;
        } else {
            uso = Buscar.getUsoCol((Expresion) s.getPadre());
            if (uso instanceof Igual) {
                noRef = true;
                escritura = Buscar.isHijo(s, ((Igual) uso).getIzquierda());
            }
        }
        if (!coleccion.getTipo().isColeccion()) {
            //Encampsular en Referencia solo si es necesario
            if (s.getTipo().isRef() && !noRef) {
                codigo.append("new ").append(Tipos.declaracion(s.getTipo())).append("(");
            }
            codigo.append(expresion).append(comentarioRef);
            genAcceso(s, coleccion, escritura, codigo);
            if (s.getTipo().isRef() && !noRef) {
                codigo.append(")");
            }
        } else {
            codigo.append("Pd.").append(comentarioRef);
            genMultiAcceso(s, expresion, coleccion, escritura, codigo);
        }
        s.setCodigoGenerado(codigo);
    }

    /**
     * Genera un multiple acceso a una coleccion
     *
     * @param s Simbolo acceso
     * @param expresion Expresion
     * @param index Posicion del acceso
     * @param escritura Acceso como escritura
     * @param codigo Codigo para generar el acceso
     */
    private static void genMultiAcceso(Acceso s, StringBuilder expresion, Simbolo index, boolean escritura, StringBuilder codigo) {
        char contexto = Buscar.getContexto(s);
        codigo.append(contexto == '$' ? 's' : contexto == '@' ? 'a' : 'h').append("Access(");
        codigo.append(expresion).append(',').append(index);
        if (contexto == 'h') {
            codigo.append(", f->Casting.box(");
            //Si en el acceso hay otra coleccion
            if (s.getExpresion().getTipo().getSubtipo(1).isColeccion()) {
                codigo.append("new Ref(f))");
            } else {
                codigo.append("f)");
            }
        }
        codigo.append(escritura ? ',' : ')');
    }

    /**
     * Genera el acceso a una coleccion
     *
     * @param s Simbolo acceso
     * @param index Posicion del acceso
     * @param escritura Acceso como escritura
     * @param codigo Codigo para generar el acceso
     */
    private static void genAcceso(Acceso s, Simbolo index, boolean escritura, StringBuilder codigo) {
        //Ignorar referencia en acceso anidado
        Tipo t = s.getExpresion().getTipo();
        if (t.isRef()) {
            t = t.getSubtipo(1);
        }
        if (t.isArray()) {
            codigo.append("[").append(Casting.casting(index, new Tipo(Tipo.INTEGER))).append("]");
        } else if (t.isList()) {
            if (escritura) {
                codigo.append(".set(").append(Casting.casting(index, new Tipo(Tipo.INTEGER))).append(",");
            } else {
                codigo.append(".get(").append(Casting.casting(index, new Tipo(Tipo.INTEGER))).append(")");
            }
        } else if (escritura) {
            codigo.append(".put(").append(Casting.casting(index, new Tipo(Tipo.STRING))).append(",");
        } else {
            codigo.append(".get(").append(Casting.casting(index, new Tipo(Tipo.STRING))).append(")");
        }
    }

    /**
     * Replica un acceso para lectura y escritura
     *
     * @param exp Expresion acceso
     * @param lectura Simbolo para la lectura
     * @param escritura Simbolo para la escritura
     * @param tabla Tabla generador
     */
    public static void getReplica(ExpAcceso exp, Simbolo lectura, Simbolo escritura, TablaGenerador tabla) {
        Acceso acceso = exp.getAcceso();
        Simbolo colL;
        Simbolo colE;
        String Refcomen;
        if (acceso instanceof AccesoCol) {
            Refcomen = "";
            colE = colL = ((AccesoCol) acceso).getColeccion();
        } else {
            Refcomen = ((AccesoColRef) acceso).getFlecha().getComentario();
            colE = colL = ((AccesoColRef) acceso).getColeccion();
        }
        if (!Buscar.isRepetible(colL)) {
            //Declarar variable aux
            String aux = tabla.getGestorReservas().getAux();
            StringBuilder declaracion = Tipos.declaracion(colL.getTipo());
            declaracion.append(" ").append(aux).append(";");
            tabla.getDeclaraciones().add(declaracion);
            //Subtituir codigo original
            colL = new SimboloAux(colL.getTipo(), new StringBuilder(aux));
            colE = new SimboloAux(colE.getTipo(), new StringBuilder(100).append(aux).append("=").append(colE));
        }
        if (!Buscar.isRepetible(acceso.getExpresion())) {
            Tipo te = acceso.getExpresion().getTipo().getSubtipo(1);//Quitar el ref de acceso anidado
            //Declarar variable aux
            String aux = tabla.getGestorReservas().getAux();
            StringBuilder declaracion = Tipos.declaracion(te);
            declaracion.append(" ").append(aux).append(";");
            tabla.getDeclaraciones().add(declaracion);
            //Subtituir codigo original
            lectura.setCodigoGenerado(new StringBuilder(aux));
            escritura.setCodigoGenerado(new StringBuilder(100).append("(").append(aux).append("=").append(acceso.getExpresion()).append(Refcomen).append(")"));
        } else {
            lectura.setCodigoGenerado(new StringBuilder(100).append(acceso.getExpresion()));
            escritura.setCodigoGenerado(new StringBuilder(100).append(acceso.getExpresion()).append(Refcomen));
        }
        genAcceso(acceso, colL, false, lectura.getCodigoGenerado());
        genAcceso(acceso, colE, true, escritura.getCodigoGenerado());
    }

    /**
     * Genera el get para acceder a una referencia siempre que este sea necesario
     *
     * @param exp Expresion
     * @return Acceso a referencia
     */
    private String genGet(Expresion exp) {
        exp = Buscar.getExpresion(exp);
        //Si esta entre llaves cogemos el contenido
        if (exp.getValor() instanceof ColLlave) {
            exp = ((ColLlave) exp.getValor()).getLista().getExpresiones().get(0);
        }
        if (!(exp.getValor() instanceof AccesoCol) && !(exp.getValor() instanceof AccesoColRef) 
                && !(exp.getValor() instanceof ColCorchete)&& !(exp.getValor() instanceof ColLlave)) {
            return ".get()";
        }
        return "";
    }

    /**
     * Accede a una referencia
     *
     * @param s Simbolo del Acceso a referencia
     * @param comenrarioSimbolo Comentario del simbolo que indica la desreferenciación
     */
    private void AccesoReferencia(Acceso s, String comenrarioSimbolo) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append(comenrarioSimbolo);
        codigo.append(s.getExpresion().getCodigoGenerado());
        codigo.append(genGet(s.getExpresion()));
        s.setCodigoGenerado(codigo);
    }

    public void visitar(AccesoCol s) {
        accesoColeccion(s, s.getExpresion().getCodigoGenerado(), s.getColeccion(), "");
    }

    public void visitar(AccesoColRef s) {
        Expresion exp = s.getExpresion();
        accesoColeccion(s, new StringBuilder(100).append(exp).append(genGet(exp)), s.getColeccion(), s.getFlecha().getComentario());
    }

    public void visitar(AccesoDesRef s) {
        AccesoReferencia(s, s.getContexto().getComentario());
    }

    public void visitar(AccesoRef s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("new ").append(Tipos.declaracion(s.getTipo())).append(s.getBarra().getComentario());
        codigo.append("(").append(s.getExpresion().getCodigoGenerado()).append(")");
        s.setCodigoGenerado(codigo);
    }

}
