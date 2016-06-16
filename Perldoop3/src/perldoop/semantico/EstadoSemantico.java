package perldoop.semantico;

import java.util.HashMap;
import java.util.Map;
import perldoop.modelo.semantico.Paquete;
import perldoop.modelo.semantico.TablaSimbolos;
import perldoop.modelo.semantico.Tipo;

/**
 * Clase para almacenar el estado durante el analisis semantico
 * @author César
 */
public final class EstadoSemantico {
    
    private Map<String,Tipo> predeclaraciones;
    private TablaSimbolos ts;
    private Map<String,Paquete> paquetes;

    public EstadoSemantico() {
        predeclaraciones = new HashMap<>(20);
        ts = new TablaSimbolos();
        paquetes = new HashMap<>(10);
    }

    public EstadoSemantico(Map<String, Paquete> paquetes) {
        this();
        this.paquetes = new HashMap<>(paquetes);
    }
    
    
    
    
}
