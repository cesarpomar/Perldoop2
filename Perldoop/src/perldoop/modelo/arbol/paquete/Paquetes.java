package perldoop.modelo.arbol.paquete;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import perldoop.modelo.arbol.Simbolo;
import perldoop.modelo.arbol.Terminal;
import perldoop.modelo.arbol.Visitante;
import perldoop.util.Utiles;

/**
 * Clase que representa la reduccion -&gt;<br>
 * paquete : paqueteVAR VAR AMBITO<br>
 * |	VAR AMBITO<br>
 * paquete : paqueteID ID AMBITO<br>
 * |	ID AMBITO
 *
 * @author César Pomar
 */
public final class Paquetes extends Simbolo {

    private List<Terminal> terminales;
    private List<Terminal> identificadores;

    /**
     * Constructor sin argumentos
     */
    public Paquetes() {
        terminales = new ArrayList<>(10);
        identificadores = new ArrayList<>(5);
    }

    /**
     * Constructor con argumentos
     *
     * @param id Identificador
     * @param ambito Acceso
     */
    public Paquetes(Terminal id, Terminal ambito) {
        this();
        add(id, ambito);
    }

    /**
     * Obtiene los terminales
     *
     * @return Terminales
     */
    public List<Terminal> getTerminales() {
        return terminales;
    }

    /**
     * Obtiene los identificadores
     *
     * @return Identificadores
     */
    public List<Terminal> getIdentificadores() {
        return identificadores;
    }

    /**
     * Obtiene los paquetes como un espacio de nombres representado por una clase java
     *
     * @return Ruta
     */
    public String getClaseJava() {
        Iterator<Terminal> it = identificadores.iterator();
        StringBuilder clase = new StringBuilder(100);
        while (it.hasNext()) {
            clase.append(Utiles.normalizar(it.next().getValor()));
            if (it.hasNext()) {
                clase.append("_");
            }
        }
        return clase.toString();
    }

    /**
     * Obtiene los paquetes como un array de nombres
     *
     * @return Array de nombres
     */
    public String[] getArrayString() {
        return identificadores.stream().map(i -> Utiles.normalizar(i.getValor())).toArray(String[]::new);
    }

    /**
     * Obtiene el numero de identificadores
     *
     * @return Identificadores
     */
    public int size() {
        return identificadores.size();
    }

    /**
     * Comprueba si existe algun paquete
     *
     * @return Existe algun paquete
     */
    public boolean isVacio() {
        return identificadores.isEmpty();
    }

    /**
     * Añade un nivel al paquete
     *
     * @param id Identificador
     * @return Este paquete
     */
    public Paquetes addId(Terminal id) {
        id.setPadre(this);
        terminales.add(id);
        identificadores.add(id);
        return this;
    }

    /**
     * Añade un nivel al paquete
     *
     * @param id Identificador
     * @param ambito Ambito
     * @return Este paquete
     */
    public Paquetes add(Terminal id, Terminal ambito) {
        addId(id);
        ambito.setPadre(this);
        terminales.add(ambito);
        return this;
    }

    /**
     * Añade un nivel a el paquete
     *
     * @param p paquete
     * @param id Identificador
     * @param ambito Ambito
     * @return Paquete p
     */
    public static Paquetes add(Paquetes p, Terminal id, Terminal ambito) {
        return p.add(id, ambito);
    }

    /**
     * Añade un nivel a el paquete
     *
     * @param p paquete
     * @param id Identificador
     * @return Paquete p
     */
    public static Paquetes addId(Paquetes p, Terminal id) {
        return p.addId(id);
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
