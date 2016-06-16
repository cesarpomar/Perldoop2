package perldoop.semantico.variable;

import perldoop.semantico.excepciones.ExcepcionSemantica;
import perldoop.modelo.arbol.variable.VarExistente;
import perldoop.modelo.arbol.variable.VarMy;
import perldoop.modelo.arbol.variable.VarOur;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.semantico.Tipo;
import perldoop.modelo.semantico.EntradaTabla;
import perldoop.modelo.semantico.TablaSimbolos;
import perldoop.util.Etiquetas;

/**
 *
 * @author César
 */
public class SemVariable {
    
    private TablaSimbolos ts;//TEMP luego lo van a necesitar los bloques

    public SemVariable() {
        ts = new TablaSimbolos();
    }
    
    public void visitar(VarExistente s) {
        EntradaTabla entrada = ts.buscarVariable(/*s.getIdentificador().getToken().getValor()*/null,'1');
        if(entrada == null){
            throw new ExcepcionSemantica();
        }
        s.setTipo(entrada.getTipo());
    }

    public void visitar(VarMy s) {
        Token token = s.getMy().getToken();
        String id = null;//s.getIdentificador().getToken().getValor();
        EntradaTabla entrada = ts.buscarVariable(id,s.getContexto().getToken().getValor().charAt(0));
        if(entrada != null){
            throw new ExcepcionSemantica();
        }
        if(token.getEtiquetas() == null){
            throw new ExcepcionSemantica();
        }
        Tipo tipo = Etiquetas.parseTipo(token.getEtiquetas());
        if(!tipo.isValido()){
            throw new ExcepcionSemantica();
        }
        //ts.nuevaVariable(new EntradaTabla(id, tipo));
        s.setTipo(tipo);
    }

    public void visitar(VarOur s) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO
    }
    
}
