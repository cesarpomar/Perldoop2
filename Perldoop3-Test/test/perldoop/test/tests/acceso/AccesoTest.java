package perldoop.test.tests.acceso;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import perldoop.test.java.acceso.Acceso;

public class AccesoTest {

    @BeforeClass
    public static void accesoTest() {
        System.out.println("accesoTest");
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
    public void testLecturaAnidada() {
        System.out.println("lecturaAnidada");
        Assert.assertEquals("1", Acceso.l7);
        Assert.assertEquals((Integer) 1, Acceso.l8);
        Assert.assertEquals("a", Acceso.l9);
    }

    @Test
    public void testLecturaMultiple() {
        System.out.println("lecturaMultiple");
        Assert.assertArrayEquals(new String[]{"1", "2"}, Acceso.l10);
        Assert.assertArrayEquals(new Integer[]{1, 2}, Acceso.l11.toArray());
        Assert.assertTrue(Acceso.l12.contains("a"));
        Assert.assertTrue(Acceso.l12.contains("n"));
    }

    @Test
    public void testLecturaAccesoReferencia() {
        System.out.println("lecturaAccesoReferencia");
        Assert.assertEquals("1", Acceso.l13);
        Assert.assertEquals((Integer) 1, Acceso.l14);
        Assert.assertEquals("a", Acceso.l15);
        Assert.assertEquals("1", Acceso.l16);
        Assert.assertEquals((Integer) 1, Acceso.l17);
        Assert.assertEquals("a", Acceso.l18);
    }

    @Test
    public void testLecturaAccesoReferenciaMultiple() {
        System.out.println("lecturaAccesoReferenciaMultiple");
        Assert.assertArrayEquals(new String[]{"1", "2"}, Acceso.l19);
        Assert.assertArrayEquals(new Integer[]{1, 2}, Acceso.l20.toArray());
        Assert.assertTrue(Acceso.l21.contains("a"));
        Assert.assertArrayEquals(new String[]{"1", "2"}, Acceso.l22);
        Assert.assertArrayEquals(new Integer[]{1, 2}, Acceso.l23.toArray());
        Assert.assertTrue(Acceso.l24.contains("a"));
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

    @Test
    public void testEscrituraAccesoReferencia() {
        System.out.println("escrituraAccesoReferencia");
        Assert.assertEquals("0", Acceso.w13[0][0]);
        Assert.assertEquals((Integer) 0, Acceso.w14.get(0).get(0));
        Assert.assertEquals("z", Acceso.w15.get("1").get("1"));
        Assert.assertEquals("0", Acceso.w16.get()[0]);
        Assert.assertEquals((Integer) 0, Acceso.w17.get().get(0));
        Assert.assertEquals("z", Acceso.w18.get().get("1"));
    }

    @Test
    public void testEscrituraAccesoReferenciaMultiple() {
        System.out.println("escrituraaAccesoReferenciaMultiple");
        Assert.assertArrayEquals(new String[]{"7", "8"}, Acceso.w19[0]);
        Assert.assertArrayEquals(new Integer[]{7, 8}, Acceso.w20.get(0).toArray());
        Assert.assertNotNull(Acceso.w21.get("1"));
        Assert.assertEquals("n", Acceso.w21.get("1").get("1"));
        Assert.assertEquals("o", Acceso.w21.get("1").get("2"));
        Assert.assertArrayEquals(new String[]{"7", "8"}, Acceso.w22.get());
        Assert.assertArrayEquals(new Integer[]{7, 8}, Acceso.w23.get().toArray());
        Assert.assertEquals("n", Acceso.w24.get().get("1"));
        Assert.assertEquals("o", Acceso.w24.get().get("3"));
    }

    @Test
    public void testAccesoReferencia() {
        System.out.println("accesoReferencia");
        Assert.assertArrayEquals(Acceso.aa[0], Acceso.l28);
        Assert.assertArrayEquals(Acceso.ll.get(0).toArray(), Acceso.l29.toArray());
        Assert.assertEquals(Acceso.hh.get("1").size(), Acceso.l30.size());
        Assert.assertArrayEquals(Acceso.aa[0], Acceso.l31);
        Assert.assertArrayEquals(Acceso.ll.get(0).toArray(), Acceso.l32.toArray());
        Assert.assertEquals(Acceso.hh.get("1"), Acceso.l33);
        Assert.assertArrayEquals(Acceso.a, Acceso.l34);
        Assert.assertArrayEquals(new String[]{"1", "2"}, Acceso.l35);
    }

    @Test
    public void testCreacionReferencia() {
        System.out.println("creacionReferencia");
        Assert.assertArrayEquals(Acceso.a, Acceso.l36.get());
        Assert.assertEquals(Acceso.l, Acceso.l37.get());
        Assert.assertEquals(Acceso.h, Acceso.l38.get());
    }

    @Test
    public void testSigil() {
        System.out.println("sigil");
        Assert.assertEquals((Integer)(Acceso.a.length-1), Acceso.l39);
        Assert.assertEquals((Integer)(Acceso.l.size()-1), Acceso.l40);
    }

}
