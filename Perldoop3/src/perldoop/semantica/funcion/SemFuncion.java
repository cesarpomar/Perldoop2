package perldoop.semantica.funcion;

import perldoop.excepciones.ExcepcionSemantica;
import perldoop.modelo.arbol.expresion.ExpFuncion;
import perldoop.modelo.arbol.expresion.Expresion;
import perldoop.modelo.arbol.funcion.*;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.sentencia.StcLista;
import perldoop.modelo.semantica.Paquete;
import perldoop.modelo.semantica.TablaSemantica;
import perldoop.modelo.semantica.Tipo;
import perldoop.util.Buscar;

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

    public void visitar(FuncionPaqueteArgs s) {
        Paquete paquete = tabla.getTablaSimbolos().getPaquete(s.getPaquetes().getRepresentancion());
        if (paquete == null) {
            //TODO error paquete no existe
            throw new ExcepcionSemantica();
        }
        if (paquete.buscarFuncion(s.getIdentificador().getToken().getValor()) == null) {
            //TODO error funcion no existe en paquete
            throw new ExcepcionSemantica();
        }
        if (s.getColeccion().getParentesisI() == null) {
            getArgumentos(s, s.getLista());
            tabla.getAcciones().analizar(s.getColeccion());
        }
        s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
    }

    public void visitar(FuncionPaqueteNoArgs s) {
        Paquete paquete = tabla.getTablaSimbolos().getPaquete(s.getPaquetes().getRepresentancion());
        if (paquete == null) {
            //TODO error paquete no existe
            throw new ExcepcionSemantica();
        }
        if (paquete.buscarFuncion(s.getIdentificador().getToken().getValor()) == null) {
            //TODO error funcion no existe en paquete
            throw new ExcepcionSemantica();
        }
        s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
    }

    public void visitar(FuncionArgs s) {
        if (tabla.getTablaSimbolos().buscarFuncion(s.getIdentificador().getToken().getValor()) == null) {
            tabla.getTablaSimbolos().addFuncionNoDeclarada(s.getIdentificador().getToken());
        }
        if (s.getColeccion().getParentesisI() == null) {
            getArgumentos(s, s.getLista());
            tabla.getAcciones().analizar(s.getColeccion());
        }
        s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
    }

    public void visitar(FuncionNoArgs s) {
        if (tabla.getTablaSimbolos().buscarFuncion(s.getIdentificador().getToken().getValor()) == null) {
            tabla.getTablaSimbolos().addFuncionNoDeclarada(s.getIdentificador().getToken());
        }
        s.setTipo(new Tipo(Tipo.ARRAY, Tipo.BOX));
    }

    public void visitar(Argumentos s) {
        //TODO borrar
    }

    private void getArgumentos(Funcion s, Lista args) {
        StcLista sentencia = Buscar.buscarPadre(s, StcLista.class);
        if (sentencia == null) {
            return;
        }
        Lista sts = sentencia.getLista();
        int nargs = 0;
        for (Expresion st : sts.getExpresiones()) {
            if (st instanceof ExpFuncion) {
                Funcion fun = (((ExpFuncion) st).getFuncion());
                if (fun instanceof FuncionArgs || fun instanceof FuncionPaqueteArgs) {
                    break;
                }
            }
            nargs++;
        }
        args.addLista(sts, nargs);
    }
}
