package perldoop.semantica.flujo;

import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.bloque.Bloque;
import perldoop.modelo.arbol.bloque.BloqueDoUntil;
import perldoop.modelo.arbol.bloque.BloqueDoWhile;
import perldoop.modelo.arbol.bloque.BloqueIf;
import perldoop.modelo.arbol.bloque.BloqueUnless;
import perldoop.modelo.arbol.bloque.BloqueVacio;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.flujo.*;
import perldoop.modelo.arbol.fuente.Fuente;
import perldoop.modelo.arbol.funciondef.FuncionDef;
import perldoop.modelo.arbol.sentencia.Sentencia;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.util.Buscar;

/**
 * Clase para la semantica de flujo
 *
 * @author César Pomar
 */
public class SemFlujo {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemFlujo(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    /**
     * Comprueba si el simbolo esta dentro de un bucle
     *
     * @param s Simbolo
     * @param t Terminal para error
     * @return Bloque encontrado
     */
    private Bloque isBucle(Flujo s, Terminal t) {
        Bloque bloque = Buscar.buscarPadre(s, Bloque.class);
        while (bloque != null) {
            //Ignoramos los bloques condicionales
            if ((bloque instanceof BloqueIf || bloque instanceof BloqueUnless || bloque instanceof BloqueVacio)) {
                bloque = Buscar.buscarPadre(bloque, Bloque.class);
            } else {
                //Si encontramos un bucle retornamos
                return bloque;
            }

        }
        tabla.getGestorErrores().error(Errores.NEXT_LAST_SIN_BUCLE, t.getToken());
        throw new ExcepcionSemantica(Errores.NEXT_LAST_SIN_BUCLE);
    }

    /**
     * Comprueba que no se genera codigo muerto
     *
     * @param s Simbolo
     */
    private void codigoMuerto(Flujo s) {
        Simbolo fin;
        //Buscamos el simbolo que delimitara la busqueda
        if (s instanceof Return) {
            fin = Buscar.buscarPadre(s, FuncionDef.class);
        } else {
            fin = isBucle(s, null);
        }
        Simbolo padre = s.getPadre().getPadre();
        Simbolo stc = s;
        while (padre != fin) {
            stc = stc.getPadre();
            padre = padre.getPadre();
            if (padre instanceof Bloque) {
                List<Sentencia> sts = ((Bloque) padre).getCuerpo().getSentencias();
                if (stc != sts.get(sts.size() - 1)) {
                    errorCodigoMuerto(((Bloque) padre).getCuerpo(), stc);
                }
                if (!(padre instanceof BloqueVacio) && !(padre instanceof BloqueDoUntil) && !(padre instanceof BloqueDoWhile)) {
                    return;
                }
            } else if (padre instanceof FuncionDef) {
                List<Sentencia> sts = ((FuncionDef) padre).getCuerpo().getSentencias();
                if (stc != sts.get(sts.size() - 1)) {
                    errorCodigoMuerto(((Bloque) padre).getCuerpo(), stc);
                }
            }
        }
    }

    /**
     * Muestra todo el codigo muerto encontrado
     *
     * @param c Cuerpo con codigo muerto
     * @param stc Sentencia generadora del codigo muerto
     */
    private void errorCodigoMuerto(Cuerpo c, Simbolo stc) {
        Simbolo s = c;
        Simbolo actual = stc;
        while (!(s instanceof Fuente) && !(s instanceof FuncionDef)) {
            if (s instanceof Cuerpo) {
                List<Sentencia> sts = ((Cuerpo) s).getSentencias();
                for (int i = sts.indexOf(actual) + 1; i < sts.size(); i++) {
                    tabla.getGestorErrores().error(Errores.SENTENCIA_INALCANZABLE, Buscar.tokenInicio(sts.get(i)));
                }
            }else if(s instanceof Bloque && !(s instanceof BloqueVacio) && !(s instanceof BloqueDoUntil) && !(s instanceof BloqueDoWhile)){
                break;
            }
            actual = s;
            s = s.getPadre();
        }
        throw new ExcepcionSemantica(Errores.SENTENCIA_INALCANZABLE);
    }

    public void visitar(Next s) {
        isBucle(s, s.getNext());
        codigoMuerto(s);
    }

    public void visitar(Last s) {
        isBucle(s, s.getLast());
        codigoMuerto(s);
    }

    public void visitar(Return s) {
        FuncionDef funcion = Buscar.buscarPadre(s, FuncionDef.class);
        if (funcion == null) {
            tabla.getGestorErrores().error(Errores.RETURN_SIN_FUNCION, s.getId().getToken());
            throw new ExcepcionSemantica(Errores.RETURN_SIN_FUNCION);
        }
        codigoMuerto(s);
    }
}
