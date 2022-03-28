package seznam.prostredky;

import org.junit.Test;
import static org.junit.Assert.*;
import static util.Barva.BILA;
import static util.Barva.ZELENA;

/**
 *
 * @author karel@simerda.cz
 */
public class ProstredekTest implements Cloneable {

    Prostredek oa = new OsobniAutomobil("A", ZELENA, 2, 1000);

    public ProstredekTest() {
    }

    @Test
    public void testGetTyp() {
    }

    @Test
    public void testGetHmotnost() {
    }

    @Test
    public void testGetSpz() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void test05_01_Equals() {
        Prostredek oa1 = new OsobniAutomobil("AAA", BILA, 1, 1000);
        Prostredek oa2 = new OsobniAutomobil("AAA", BILA, 1, 1000);
        assertTrue(oa1.equals(oa2));
    }

    @Test
    public void test05_02_Equals() {
        Prostredek oa1 = new OsobniAutomobil("AAA", BILA, 1, 1000);
        Prostredek oa2 = new OsobniAutomobil("AAB", BILA, 1, 1000);
        assertFalse(oa1.equals(oa2));
    }

    @Test
    public void test05_03_Equals() {
        Prostredek oa1 = new OsobniAutomobil("AAA", BILA, 1, 1000);
        Prostredek oa2 = new OsobniAutomobil("AAA", ZELENA, 1, 1000);
        assertTrue(oa1.equals(oa2));
    }

    @Test
    public void test05_04_Equals() {
        Prostredek oa1 = new OsobniAutomobil("AAA", BILA, 1, 1000);
        Prostredek oa2 = new OsobniAutomobil("AAA", BILA, 2, 1000);
        assertTrue(oa1.equals(oa2));
    }

    @Test
    public void test05_05_Equals() {
        Prostredek oa1 = new OsobniAutomobil("AAA", BILA, 1, 1000);
        Prostredek oa2 = new OsobniAutomobil("AAA", BILA, 1, 1001);
        assertTrue(oa1.equals(oa2));
    }

    @Test
    public void test05_06_Equals() {
        Prostredek p1 = new OsobniAutomobil("AAA", BILA, 1, 1000);
        Prostredek p2 = new NakladniAutomobil("AAA", 5, 1000);
        assertFalse(p1.equals(p2));
    }

    @Test
    public void test05_07_Equals() {
        OsobniAutomobil oa1 = new OsobniAutomobil("AAA", BILA, 1, 1000);
        assertFalse(oa1.equals(null));
    }

    @Test
    public void test05_08_Equals() {
        Prostredek oa1 = new OsobniAutomobil("AAA", BILA, 1, 1000);
        assertFalse(oa1.equals(new Object()));
    }

    @Test
    public void test06_01_HashCode() {
        Prostredek oa1 = new OsobniAutomobil("AAA", BILA, 1, 1000);
        Prostredek oa2 = new OsobniAutomobil("AAA", BILA, 1, 1001);
        assertTrue(oa1.hashCode() == oa2.hashCode());
    }

    @Test
    public void test06_02_HashCode() {
        Prostredek oa1 = new OsobniAutomobil("AAA", BILA, 1, 1000);
        Prostredek oa2 = new OsobniAutomobil("AAB", BILA, 1, 1000);
        assertFalse(oa1.hashCode() == oa2.hashCode());
    }

    @Test
    public void test06_03_HashCode() {
        Prostredek p1 = new OsobniAutomobil("AAA", BILA, 1, 1000);
        Prostredek p2 = new NakladniAutomobil("AAA", 5, 1000);
        assertFalse(p1.hashCode() == p2.hashCode());
    }

}
