package perldoop.test.tests.asignacion;

import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import perldoop.lib.Box;
import perldoop.lib.PerlMap;
import perldoop.test.java.asignacion.IgualBox;

public class IgualBoxTest {

    @BeforeClass
    public static void IgualBoxTest() {
        System.out.println("igualBoxTest");
    }

    @Test
    public void testAsignacionBox() {
        System.out.println("asignacionBox");
        Assert.assertEquals((Integer) 1, IgualBox.l1.intValue());
        Assert.assertEquals((Double) 1.1, IgualBox.l2.doubleValue());
        Assert.assertEquals("1", IgualBox.l3.stringValue());
        Box[] l4 = (Box[]) IgualBox.l4.refValue().get();
        Assert.assertEquals((Integer) 1, l4[0].intValue());
        Assert.assertEquals((Integer) 2, l4[1].intValue());
        PerlMap<Box> l5 = (PerlMap<Box>) IgualBox.l5.refValue().get();
        Assert.assertTrue(l5.containsKey("1"));
        Box[] l6 = (Box[]) IgualBox.l6.refValue().get();
        Box[] l6a= (Box[]) l6[0].refValue().get();
        Assert.assertEquals((Integer) 1, l6a[0].intValue());
        Assert.assertEquals((Integer) 2, l6a[1].intValue());   
        PerlMap<Box> l7 = (PerlMap<Box>) IgualBox.l7.refValue().get();
        PerlMap<Box> l7a = (PerlMap<Box>) l7.get("1").refValue().get();
        Assert.assertEquals((Integer)2, l7a.get("1").intValue());
        Assert.assertEquals((Integer) 1, IgualBox.l8.intValue());
    }

    @Test
    public void testAccesosBox() {
        System.out.println("accesosBox");
        Assert.assertEquals((Integer) 1, IgualBox.l12[0][0].intValue());
        Assert.assertEquals((Integer) 2, IgualBox.l12[0][1].intValue());
        Assert.assertEquals((Integer) 7, IgualBox.l12[1][0].intValue());
        Assert.assertEquals((Integer) 8, IgualBox.l12[1][1].intValue());
        Assert.assertEquals((Integer) 9, IgualBox.l12[2][0].intValue());
        Assert.assertEquals((Integer) 10, IgualBox.l12[2][1].intValue());
        Assert.assertArrayEquals(IgualBox.l12[0], IgualBox.l13.get());
        Assert.assertEquals(IgualBox.l12[0], IgualBox.l14.refValue().get());
    }

}
