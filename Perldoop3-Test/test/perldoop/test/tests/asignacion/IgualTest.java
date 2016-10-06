package perldoop.test.tests.asignacion;

import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import perldoop.test.java.asignacion.Igual;

public class IgualTest {

    @BeforeClass
    public static void IgualTest() {
        System.out.println("igualTest");
    }

    @Test
    public void testAsignacionSimple() {
        System.out.println("asignacionSimple");
        Assert.assertEquals((Integer) 1, Igual.l1);
        Assert.assertEquals("2", Igual.l2.stringValue());
        Assert.assertNotNull(Igual.l3);
        Assert.assertArrayEquals(new String[]{"1"}, Igual.l3);
        Assert.assertNotNull(Igual.l4);
        Assert.assertArrayEquals(new Integer[]{1}, Igual.l4.toArray());
        Assert.assertNotNull(Igual.l5);
        Assert.assertEquals(1, Igual.l5.size());
        Assert.assertEquals("a", Igual.l5.get("1"));
    }

    @Test
    public void testInicializacion() {
        System.out.println("inicializacion");
        int tam = 8;
        Assert.assertNotNull(Igual.l6);
        Assert.assertEquals(tam, Igual.l6.length);
        Assert.assertNotNull(Igual.l7);
        Assert.assertNotNull(Igual.l8);
    }

    @Test
    public void testInicializacionReferencias() {
        System.out.println("inicializacionReferencias");
        int tam = 10;
        Assert.assertNotNull(Igual.l9);
        Assert.assertEquals(tam, Igual.l9.get().length);
        Assert.assertNotNull(Igual.l10);
        Assert.assertNotNull(Igual.l11);
        Assert.assertNotNull(Igual.l6[0]);
        Assert.assertEquals(tam, Igual.l6[0].length);
        Assert.assertNotNull(Igual.l7aux.get(0));
        Assert.assertNotNull(Igual.l8.get("0"));
    }

    @Test
    public void testMultiasignacionColeccion() {
        System.out.println("multiasignacionColeccion");
        Assert.assertEquals((Integer) 1, Igual.l13);
        Assert.assertEquals((Integer) 2, Igual.l12.get(0));
        Assert.assertEquals((Integer) 3, Igual.l14);
        Assert.assertNotNull(Igual.l15);
        Assert.assertArrayEquals(new Integer[]{1, 2}, Igual.l15.get());
        Assert.assertArrayEquals(new Integer[]{1, 2}, Igual.l16.get());
        Assert.assertEquals((Integer) 2, Igual.l17);
        Assert.assertEquals((Integer) 1, Igual.l18);
    }

    @Test
    public void testMultiasignacionVariable() {
        System.out.println("multiasignacionVariable");
        Assert.assertEquals((Integer) 1, Igual.l20);
        Assert.assertEquals((Integer) 2, Igual.l19.get(0));
        Assert.assertEquals((Integer) 3, Igual.l21);
    }

}
