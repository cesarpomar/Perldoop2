package perldoop.semantica.modulos;

import java.util.List;
import static javax.management.Query.value;
import perldoop.excepciones.ExcepcionSemantica;
import perldoop.internacionalizacion.Errores;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.modulos.ModuloPackage;
import perldoop.modelo.arbol.modulos.ModuloUse;
import perldoop.modelo.semantica.Paquete;
import perldoop.modelo.semantica.TablaSemantica;

/**
 * Clase para la semantica de modulos
 *
 * @author César Pomar
 */
public class SemModulo {

    private TablaSemantica tabla;

    /**
     * Contruye la semantica
     *
     * @param tabla Tabla de simbolos
     */
    public SemModulo(TablaSemantica tabla) {
        this.tabla = tabla;
    }

    public void visitar(ModuloPackage s) {
        if(!tabla.getTablaSimbolos().isVacia()){
            tabla.getGestorErrores().error(Errores.MODULO_NO_VACIO, s.getIdPackage().getToken());
            throw new ExcepcionSemantica(Errores.MODULO_NO_VACIO);
        }
        if (tabla.getTablaSimbolos().getPaquete()!=null) {
            tabla.getGestorErrores().error(Errores.MODULO_YA_CREADO, s.getIdPackage().getToken());
            throw new ExcepcionSemantica(Errores.MODULO_YA_CREADO);
        }
        tabla.getTablaSimbolos().crearPaquete(tabla.getGestorErrores().getFichero());
    }

    public void visitar(ModuloUse s) {
        List<Terminal> ids = s.getPaquetes().getIdentificadores();
        String clase = ids.get(ids.size()-1).getValor();
        String fichero = tabla.getGestorErrores().getFichero();
        String[] paquetes = s.getPaquetes().getArrayString();
        Paquete paquete = tabla.getTablaSimbolos().getPaquete(fichero, paquetes);
        if(paquete==null){
            tabla.getGestorErrores().error(Errores.MODULO_NO_EXISTE,s.getPuntoComa());
            throw new ExcepcionSemantica(Errores.MODULO_NO_EXISTE);
        }
        tabla.getTablaSimbolos().getImports().put(clase, paquete);
    }

}
