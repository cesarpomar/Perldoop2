package perldoop.test.tests.colecciones;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import perldoop.test.java.coleciones.Colecciones;

public class ColeccionesTest {

    @BeforeClass
    public static void coleccionesTest() {
        System.out.println("coleccionesTest");
    }

    @Test
    public void testInicializacionBasica() {
        System.out.println("inicializacionBasica");
        Assert.assertArrayEquals(new String[]{"1", "2", "3", "4"}, Colecciones.a);
        Assert.assertArrayEquals(new Integer[]{1, 2, 3, 4}, Colecciones.l.toArray());
        Assert.assertEquals("a", Colecciones.h.get("1"));
        Assert.assertEquals("n", Colecciones.h.get("2"));
    }

    @Test
    public void testInicializacionBasicaAnidada() {
        System.out.println("inicializacionBasicaAnidada");
        Assert.assertArrayEquals(new String[]{"1", "2"}, Colecciones.aa[0]);
        Assert.assertArrayEquals(new String[]{"3", "4"}, Colecciones.aa[1]);
        Assert.assertArrayEquals(new Integer[]{1, 2}, Colecciones.ll.get(0).toArray());
        Assert.assertArrayEquals(new Integer[]{3, 4}, Colecciones.ll.get(1).toArray());
        Assert.assertEquals("a", Colecciones.hh.get("1").get("1"));
        Assert.assertEquals("n", Colecciones.hh.get("2").get("2"));
    }

    @Test
    public void testInicializacionDespliegue() {
        System.out.println("inicializacionBasicaDespliegue");
        Assert.assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8}, Colecciones.a2);
        Assert.assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8}, Colecciones.l2.toArray());
        Assert.assertEquals((Integer) 2, Colecciones.h2.get("1"));
        Assert.assertEquals((Integer) 4, Colecciones.h2.get("3"));
        Assert.assertEquals((Integer) 6, Colecciones.h2.get("5"));
        Assert.assertEquals((Integer) 8, Colecciones.h2.get("7"));
    }

}
