package perldoop.lib.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import perldoop.lib.PerlList;
import perldoop.lib.PerlMap;

/**
 * Clase para el despliegue de colecciones
 *
 * @author César Pomar
 */
public final class Union {

    private List<Iterator> cols;
    private int size;

    public Union() {
        cols = new ArrayList<>(10);
    }

    public Union append(Object[] array) {
        size += array.length;
        cols.add(Arrays.asList(array).iterator());
        return this;
    }

    public Union append(List list) {
        size += list.size();
        cols.add(list.iterator());
        return this;
    }

    public Union append(Map map) {
        size += map.size();
        cols.add(map.entrySet().iterator());
        return this;
    }

    public <T> T[] toArray(Class<T[]> clase) {
        Object[] array = (Object[]) Array.newInstance(clase.getComponentType(), size);
        int i = 0;
        for (Iterator it : cols) {
            while (it.hasNext()) {
                array[i++] = it.next();
            }
        }
        return (T[]) array;
    }

    public <T> PerlList<T> toList() {
        PerlList list = new PerlList(size * 2);
        for (Iterator it : cols) {
            while (it.hasNext()) {
                list.add(it.next());
            }
        }
        return list;
    }

    public <T> PerlMap<T> toMap() {
        PerlMap map = new PerlMap(size * 2);
        for (Iterator<Map.Entry> it : cols) {
            while (it.hasNext()) {
                Map.Entry next = it.next();
                map.put(next.getKey(), next.getValue());
            }
        }
        return map;
    }

}
