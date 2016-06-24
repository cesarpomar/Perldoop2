package perldoop.modelo.arbol.paquete;

import java.util.ArrayList;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;

/**
 *  Clase que representa la reduccion -><br>
 * paquete : paqueteVAR VAR AMBITO<br>
 * |	VAR AMBITO<br>
 * paquete : paqueteID ID AMBITO<br>
 * |	ID AMBITO	
 * @author César Pomar
 */
public final class Paquetes extends Simbolo {

    private List<Terminal> terminales;
    private List<Terminal> identificadores;
    private StringBuilder nombre;

    /**
     * Único contructor de la clase
     * @param id Identificador
     * @param ambito Acceso
     */
    public Paquetes(Terminal id, Terminal ambito) {
        terminales = new ArrayList<>(10);
        identificadores = new ArrayList<>(5);
        add(id, ambito);
    }

    /**
     * Obtiene los terminales
     * @return Terminales
     */
    public List<Terminal> getTerminales() {
        return terminales;
    }

    /**
     * Obtiene los identificadores
     * @return Identificadores
     */
    public List<Terminal> getIdentificadores() {
        return identificadores;
    }

    /**
     * Añade un nivel al paquete
     * @param id Identificador
     * @param ambito Ambito
     * @return Este paquete
     */
    public Paquetes add(Terminal id, Terminal ambito) {
        id.setPadre(this);
        ambito.setPadre(this);
        terminales.add(id);
        terminales.add(ambito);
        identificadores.add(id);
        return this;
    }

    /**
     * Añade un nivel a el paquete
     * @param p paquete
     * @param id Identificador
     * @param ambito Ambito
     * @return Paquete p
     */
    public static Paquetes add(Paquetes p, Terminal id, Terminal ambito) {
        return p.add(id, ambito);
    }

    @Override
    public void aceptar(Visitante v) {
        v.visitar(this);
    }

    @Override
    public Simbolo[] getHijos() {
        return terminales.toArray(new Simbolo[terminales.size()]);
    }

}
