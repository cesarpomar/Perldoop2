package perldoop.semantica.funcion;

import java.util.List;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.FuncionNativa;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.coleccion.ColParentesis;
import perldoop.modelo.arbol.coleccion.Coleccion;
import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.paquete.Paquetes;
import perldoop.modelo.semantica.Paquete;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;

/**
 * Clase para la semantica de funcion
 *
 * @author César Pomar
 */
public class SemFuncion {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemFuncion(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(FuncionBasica s) {
        if(s.getPaquetes().getTerminales().isEmpty()){
            comprobarFuncion(s, (ColParentesis) s.getColeccion());
        }else{
            comprobarFuncionPaquete(s, s.getPaquetes());
        }
        
    }
    
    public void visitar(FuncionHandle s) {
        
    }
    
    public void visitar(FuncionBloque s) {
        
    }

    /**
     * Comprueba una funcion
     *
     * @param f Funcion
     * @param args Argumentos
     */
    private void comprobarFuncion(Funcion f, ColParentesis args) {
        FuncionNativa fn = null;
        if (f.getPadre() instanceof ExpFuncion) {
            fn = getSemNativa(f.getIdentificador().getValor());
        }
        if (args != null && args.isVirtual() && f.getTipo() == null && getArgumentos(f, args, fn != null)) {
            f.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
        } else {
            f.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
            if (fn != null) {
                fn.visitar(f, args);
            } else if (tabla.getTablaSimbolos().buscarFuncion(f.getIdentificador().getToken().getValor()) == null) {
                tabla.getTablaSimbolos().addFuncionNoDeclarada(f.getIdentificador().getToken());
            }
        }
    }

    /**
     * Comprueba una funcion de un paquete
     *
     * @param f Funcion
     * @param paquetes Paquetes
     */
    private void comprobarFuncionPaquete(Funcion f, Paquetes paquetes) {
        Paquete paquete = tabla.getTablaSimbolos().getPaquete(paquetes.getRepresentancion());
        if (paquete == null) {
            tabla.getGestorErrores().error(Errores.PAQUETE_NO_EXISTE, paquetes.getIdentificadores().get(0).getToken(),
                    paquetes.getRepresentancion());
            throw new ExcepcionSemantica(Errores.PAQUETE_NO_EXISTE);
        }
        if (paquete.buscarFuncion(f.getIdentificador().getToken().getValor()) == null) {
            tabla.getGestorErrores().error(Errores.FUNCION_NO_EXISTE, f.getIdentificador().getToken(), f.getIdentificador().getValor());
            throw new ExcepcionSemantica(Errores.FUNCION_NO_EXISTE);
        }
        f.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
    }

    /**
     * Obtiene las expresiones de una coleccion virtual
     *
     * @param f Funcion
     * @param col Argumentos
     * @param interpolar Interpolar argumentos
     * @return Argumentos añadidos
     */
    private boolean getArgumentos(Funcion f, Coleccion col, boolean interpolar) {
        Simbolo uso = f.getPadre().getPadre();
        if (!(uso instanceof Lista)) {//Solo hay un argumento
            return false;
        }
        Lista lista = (Lista) uso;
        List<Expresion> expO = lista.getExpresiones();
        List<Simbolo> elemO = lista.getElementos();
        List<Expresion> expD = col.getLista().getExpresiones();
        List<Simbolo> elemD = col.getLista().getElementos();
        int exp = expO.indexOf(f.getPadre());
        int elem = elemO.indexOf(f.getPadre());
        while (exp < expO.size()) {
            expD.add(expO.remove(exp));
        }
        while (elem < elemO.size()) {
            elemD.add(elemO.remove(elem));
        }
        if (interpolar) {
            tabla.getAcciones().reAnalizarDespuesDe(col);
        } else {
            tabla.getAcciones().reAnalizarDespuesDe(lista);
        }
        return true;
    }

    /**
     * Obtiene la semantica nativa de una funcion
     *
     * @param id Nombre de la funcion
     * @return Semantica nativa
     */
    private FuncionNativa getSemNativa(String id) {
        switch (id) {
            default:
                return null;
        }
    }

}
