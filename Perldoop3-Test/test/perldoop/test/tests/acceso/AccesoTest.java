package perldoop.test.tests.acceso;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import perldoop.test.java.acceso.Acceso;

public class AccesoTest {

    @BeforeClass
    public static void accesoTest() {
        System.out.println("accesoTest");
        Acceso.global();
    }

    @Test
    public void testLectura() {
        System.out.println("lectura");
        Assert.assertEquals("1", Acceso.l1);
        Assert.assertEquals((Integer) 1, Acceso.l2);
        Assert.assertEquals("a", Acceso.l3);
        Assert.assertArrayEquals(new String[]{"1", "2"}, Acceso.l4.get());
        Assert.assertArrayEquals(new Integer[]{1, 2}, Acceso.l5.get().toArray());
        Assert.assertEquals("a", Acceso.l6.get().get("1"));
    }

    @Test
    public void testLecturaAninada() {
        System.out.println("lecturaAnidada");
        Assert.assertArrayEquals(new String[]{"1", "2"}, Acceso.l10);
        Assert.assertArrayEquals(new Integer[]{1, 2}, Acceso.l11.toArray());
        List<String> listHash = Acceso.l12;
        Assert.assertTrue(listHash.contains("a"));
        Assert.assertTrue(listHash.contains("n"));
    }

    @Test
    public void testLecturaMultiple() {
        System.out.println("lecturaMultiple");
        Assert.assertEquals("1", Acceso.l7);
        Assert.assertEquals((Integer) 1, Acceso.l8);
        Assert.assertEquals("a", Acceso.l9);
    }

    @Test
    public void testEscritura() {
        System.out.println("escritura");
        Assert.assertEquals("0", Acceso.w1[0]);
        Assert.assertEquals((Integer) 0, Acceso.w2.get(0));
        Assert.assertEquals("z", Acceso.w3.get("1"));
        Assert.assertArrayEquals(Acceso.w4[0], Acceso.w4[1]);
        Assert.assertEquals(Acceso.w5.get(0), Acceso.w5.get(1));
        Assert.assertEquals(Acceso.w6.get("1"), Acceso.w6.get("2"));
    }

    @Test
    public void testEscrituraAninada() {
        System.out.println("escrituraAnidada");
        Assert.assertEquals("0", Acceso.w7[0][0]);
        Assert.assertEquals((Integer) 0, Acceso.w8.get(0).get(0));
        Assert.assertEquals("z", Acceso.w9.get("1").get("1"));
    }

    @Test
    public void testEscrituraMultiple() {
        System.out.println("escrituraMultiple");
        Assert.assertArrayEquals(new String[]{"1", "7", "8", "4"}, Acceso.w10);
        Assert.assertArrayEquals(new Integer[]{1, 7, 8, 4}, Acceso.w11.toArray());
        Assert.assertEquals("n", Acceso.w12.get("1"));
        Assert.assertEquals("o", Acceso.w12.get("2"));

    }

}
