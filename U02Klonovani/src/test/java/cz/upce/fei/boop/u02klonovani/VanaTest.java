package cz.upce.fei.boop.u02klonovani;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author karel@simerda.cz
 */
public class VanaTest {

    public VanaTest() {
    }

    @Test
    public void testKonstruktor01() {
        System.out.println("Konstruktor01");
        Vana result = new Vana("Ravak", 1, 2, 3);
        Vana instance = new Vana("Ravak", new Rozmer(1, 2, 3));
        Vana expResult = instance;
        assertEquals(expResult, result);
    }

    @Test(expected = NullPointerException.class)
    public void testKonstruktor02() {
        System.out.println("Konstruktor02");
        Vana result = new Vana(null, 1, 2, 3);
        fail();
    }

    @Test(expected = NullPointerException.class)
    public void testKonstruktor03() {
        System.out.println("Konstruktor03");
        Vana result = new Vana("Ravak", null);
        fail();
    }

    @Test(expected = ClassCastException.class)
    public void testKonstruktor04() {
        System.out.println("Konstruktor04");
        Vana result = new Vana("Ravak", (Rozmer) new Object());
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor05() {
        System.out.println("Konstruktor05");
        Vana result = new Vana("Ravak", -1, 2, 3);
        fail();
    }

    /**
     * Test of clone method, of class Vana.
     */
    @Test
    public void testClone() throws Exception {
        System.out.println("clone");
        Vana instance = new Vana("Ravak", new Rozmer(1, 2, 3));
        Vana expResult = instance;
        Vana result = instance.clone();
        assertEquals(expResult, result);

    }

    /**
     * Test of getNazevVany method, of class Vana.
     */
    @Test
    public void testGetNazevVany() {
        System.out.println("getNazevVany");
        Vana instance = new Vana("Ravak", new Rozmer(1, 2, 3));
        String expResult = "Ravak";
        String result = instance.getNazevVany();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRozmer method, of class Vana.
     */
    @Test
    public void testGetRozmer() {
        System.out.println("getRozmer");
        Vana instance = new Vana("Ravak", new Rozmer(1, 2, 3));
        Rozmer expResult = new Rozmer(1, 2, 3);
        Rozmer result = instance.getRozmer();
        assertEquals(expResult, result);
    }

    @Test
    public void testEquals01() {
        System.out.println("equals01");
        Vana vana = new Vana("Ravak", new Rozmer(1, 2, 3));
        assertFalse(vana.equals(null));
    }

    @Test
    public void testEquals02() {
        System.out.println("equals02");
        Vana vana = new Vana("Ravak", new Rozmer(1, 2, 3));
        assertFalse(vana.equals(new Object()));
    }

    @Test
    public void testEquals03() {
        System.out.println("equals03");
        Vana vana1 = new Vana("Ravak1", new Rozmer(1, 2, 3));
        Vana vana2 = new Vana("Ravak2", new Rozmer(1, 2, 3));
        assertFalse(vana1.equals(vana2));
    }

    @Test
    public void testEquals04() {
        System.out.println("equals03");
        Vana vana1 = new Vana("Ravak1", new Rozmer(1, 2, 3));
        Vana vana2 = new Vana("Ravak1", new Rozmer(2, 2, 3));
        assertFalse(vana1.equals(vana2));
    }

}
