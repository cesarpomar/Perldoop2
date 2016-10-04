package perldoop.test.tests.asignacion;

import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import perldoop.test.java.asignacion.Igual;

public class IgualTest {

    @BeforeClass
    public static void IgualTest() {
        System.out.println("igualTest");
        Igual.global();
    }

    @Test
    public void testAsignacionSimple() {
        System.out.println("asignacionSimple");
        Assert.assertEquals((Integer)1, Igual.l1);
        Assert.assertEquals("2", Igual.l2.stringValue());
        Assert.assertArrayEquals(new String[]{"1"}, Igual.l3);
        Assert.assertArrayEquals(new Integer[]{1}, Igual.l4.toArray());
        Assert.assertEquals("a", Igual.l5.get("1"));
    }

}
