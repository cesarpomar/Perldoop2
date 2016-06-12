package perldoop.modelo.simbolos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author César
 */
public class TablaSimbolos {

    private List<Map<String, EntradaTabla>> variables;

    public TablaSimbolos() {
        variables = new ArrayList<>();
        variables.add(new HashMap<>());
    }

    public void abrirBloque() {
        variables.add(new HashMap<>());
    }

    public void cerrarBloque() {
        if (variables.size() == 1) {
            //throw new
        } else {
            variables.remove(variables.size() - 1);
        }
    }

    public void nuevaVariable(EntradaTabla entrada) {
        if (entrada != null) {
            entrada.setNivel(variables.size() - 1);
            variables.get(entrada.getNivel()).put(entrada.getIdentificador(), entrada);
        }
    }

    public EntradaTabla buscarVariable(String identificador) {
        for (int i = variables.size() - 1; i >= 0; i--) {
            EntradaTabla entrada = variables.get(i).get(identificador);
            if(entrada != null){
                return entrada;
            }
        }
        return null;
    }
    
    public int getNiveles(){
        return variables.size() - 1;
    }

}
