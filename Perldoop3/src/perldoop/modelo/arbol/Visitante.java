package perldoop.modelo.arbol;

import perldoop.modelo.arbol.asignacion.Igual;
import perldoop.modelo.arbol.cuerpo.Cuerpo;
import perldoop.modelo.arbol.constante.*;
import perldoop.modelo.arbol.expresion.*;
import perldoop.modelo.arbol.lista.Lista;
import perldoop.modelo.arbol.sentencia.*;
import perldoop.modelo.arbol.variable.*;

/**
 *
 * @author César
 */
public interface Visitante {
    
    //Codigo
    void visitar(Cuerpo s);
    //Sentencia
    void visitar(StcLista s);     
    //Expresion
    void visitar(ExpAsignacion s);    
    void visitar(ExpConstante s);  
    void visitar(ExpVariable s);   
    //Asignacion
    void visitar(Igual s);    
    //Lista
    void visitar(Lista s);   
    //Variable
    void visitar(VarExistente s);
    void visitar(VarMy s);
    void visitar(VarOur s);    
    //Constante
    void visitar(CadenaComando s);
    void visitar(CadenaDoble s);
    void visitar(CadenaSimple s);
    void visitar(Decimal s);
    void visitar(Entero s);
    //Terminal
    void visitar(Terminal s);

    //Temp
    void visitar(Simbolo s);
}
