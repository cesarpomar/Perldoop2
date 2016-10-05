package perldoop.generacion.acceso;

import java.util.List;
import perldoop.generacion.util.Casting;
import perldoop.generacion.util.Tipos;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.generacion.TablaGenerador;
import perldoop.modelo.arbol.acceso.*;
import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.coleccion.ColLlave;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.ExpAcceso;
import perldoop.modelo.arbol.expresion.ExpColeccion;
import perldoop.modelo.arbol.expresion.Expresion;
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
        Simbolo uso = Buscar.getPadre(s, 1);
        if (uso instanceof Igual) {
            escritura = ((Igual) uso).getIzquierda() == s.getPadre();
            noRef = ((Igual) uso).getIzquierda() instanceof ExpAcceso;
        } else if (Buscar.isAsignada(s)) {
            noRef = escritura = true;
        } else if (uso instanceof Acceso) {
            noRef = true;
        } else {
            Simbolo uso2 = Buscar.getPadre(s, 4);
            if (uso2 instanceof AccesoRefEscalar || uso2 instanceof AccesoRefArray || uso2 instanceof AccesoRefMap) {
                noRef = true;
            }
        }
        List<Expresion> lista = coleccion.getLista().getExpresiones();
        //Acceso a mas de una posición
        if (lista.size() > 1 || lista.get(0).getTipo().isColeccion()) {
            codigo.append("Pd.access(").append(expresion).append(comentarioRef).append(", ");
            codigo.append(coleccion.getCodigoGenerado());
            if (escritura) {
                codigo.append(",");
            } else {
                codigo.append(")");
            }
        } else {
            Expresion exp = lista.get(0);
            //Encapsular en una referencia
            if (!escritura && !noRef && s.getTipo().isRef()) {
                codigo.append("new ").append(Tipos.declaracion(s.getTipo())).append("(");
            }
            codigo.append(expresion).append(comentarioRef);
            //Ignorar referencia en acceso anidado
            Tipo t = s.getExpresion().getTipo();
            if (t.isRef()) {
                t = t.getSubtipo(1);
            }
            if (t.isArray()) {
                codigo.append("[").append(Casting.casting(exp, new Tipo(Tipo.INTEGER))).append("]");
            } else if (t.isList()) {
                if (escritura) {
                    codigo.append(".set(").append(Casting.casting(exp, new Tipo(Tipo.INTEGER))).append(",");
                } else {
                    codigo.append(".get(").append(Casting.casting(exp, new Tipo(Tipo.INTEGER))).append(")");
                }
            } else if (escritura) {
                codigo.append(".put(").append(Casting.casting(exp, new Tipo(Tipo.STRING))).append(",");
            } else {
                codigo.append(".get(").append(Casting.casting(exp, new Tipo(Tipo.STRING))).append(")");
            }
            //hay que cerrar la referencia
            if (!escritura && !noRef && s.getTipo().isRef()) {
                codigo.append(")");
            }
        }
        s.setCodigoGenerado(codigo);
    }

    /**
     * Accede a una referencia
     *
     * @param s Simbolo del Acceso a referencia
     * @param comenrarioSimbolo Comentario del simbolo que indica la desreferenciación
     */
    public void AccesoReferencia(Acceso s, String comenrarioSimbolo) {
        StringBuilder codigo = new StringBuilder(100);
        s.setCodigoGenerado(codigo);
        codigo.append(comenrarioSimbolo);
        codigo.append(s.getExpresion().getCodigoGenerado());
        //Cogemos la expresion accdida
        Expresion exp = s.getExpresion();
        //En caso de estar entre llaves
        if (s.getExpresion() instanceof ExpColeccion) {
            ExpColeccion ec = (ExpColeccion) s.getExpresion();
            if (ec.getColeccion() instanceof ColLlave) {
                exp = ((ColLlave) ec.getColeccion()).getLista().getExpresiones().get(0);
            }
        }
        //Accesos y colecciones no son realmente referencias
        if (exp instanceof ExpAcceso) {
            ExpAcceso acceso = (ExpAcceso) exp;
            if (acceso.getAcceso() instanceof AccesoCol || acceso.getAcceso() instanceof AccesoColRef) {
                return;
            }
        } else if (exp instanceof ExpColeccion) {
            return;
        }
        codigo.append(".get()");
    }

    public void visitar(AccesoCol s) {
        accesoColeccion(s, s.getExpresion().getCodigoGenerado(), s.getColeccion(), "");
    }

    public void visitar(AccesoColRef s) {
        StringBuilder expresion = s.getExpresion().getCodigoGenerado();
        if (!(s.getExpresion() instanceof ExpAcceso)) {
            expresion = new StringBuilder(expresion).append(".get()");
        }
        accesoColeccion(s, expresion, s.getColeccion(), s.getFlecha().getComentario());
    }

    public void visitar(AccesoRefEscalar s) {
        AccesoReferencia(s, s.getDolar().getComentario());
    }

    public void visitar(AccesoRefArray s) {
        AccesoReferencia(s, s.getArroba().getComentario());
    }

    public void visitar(AccesoRefMap s) {
        AccesoReferencia(s, s.getPorcentaje().getComentario());
    }

    public void visitar(AccesoRef s) {
        StringBuilder codigo = new StringBuilder(100);
        codigo.append("new ").append(Tipos.declaracion(s.getTipo())).append(s.getBarra().getComentario());
        codigo.append("(").append(s.getExpresion().getCodigoGenerado()).append(")");
        s.setCodigoGenerado(codigo);
    }

}
