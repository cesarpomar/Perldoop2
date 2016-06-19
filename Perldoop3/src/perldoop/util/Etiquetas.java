package perldoop.util;

import java.util.List;
import perldoop.modelo.lexico.Token;
import perldoop.modelo.semantica.Tipo;

/**
 *
 * @author César
 */
public class Etiquetas {
    
    public static Tipo parseTipo(List<Token> etiquetas){
        Tipo tipo= new Tipo();
        for(Token t:etiquetas){
            switch(t.getValor()){
            case "<BOOLEAN>":
                tipo.add(Tipo.BOOLEAN);
                break;
            case "<INTEGER>":
                tipo.add(Tipo.INTEGER);
                break;
            case "<LONG>":
                tipo.add(Tipo.LONG);
                break;
            case "<FLOAT>":
                tipo.add(Tipo.FLOAT);
                break;
            case "<DOUBLE>":
                tipo.add(Tipo.DOUBLE);
                break;
            case "<STRING>":
                tipo.add(Tipo.STRING);
                break;
            case "<FILE>":
                tipo.add(Tipo.FILE);
                break;
            case "<ARRAY>":
                tipo.add(Tipo.ARRAY);
                break;
            case "<LIST>":
                tipo.add(Tipo.LIST);
                break;
            case "<MAP>":
                tipo.add(Tipo.MAP);
                break;
            }
        }
        return tipo;
    }
}
