package seznam.prostredky;

import org.junit.Test;
import static org.junit.Assert.*;
import static util.Barva.*;

/**
 *
 * @author karel@simerda.cz
 */
public class OsobniAutomobilTest {

    public OsobniAutomobilTest() {
    }

    @Test
    public void test01_01_Konstruktor() {
        OsobniAutomobil oa = new OsobniAutomobil("AAA", BILA, 5, 1000);
        String[] result = {oa.getSpz(), oa.getBarva().getNazev(),
            Integer.toString(oa.getPocetSedadel()), Double.toString(oa.getHmotnost())};
        String[] expected = {"AAA", "bílá", "5", "1000.0"};
        assertArrayEquals(expected, result);
    }

    @Test(expected = NullPointerException.class)
    public void test01_10_Konstruktor() {
        OsobniAutomobil oa = new OsobniAutomobil(null, BILA, 5, 1000);
        fail();
    }

    @Test(expected = NullPointerException.class)
    public void test01_11_Konstruktor() {
        OsobniAutomobil oa = new OsobniAutomobil("AAA", null, 5, 1000);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test01_12_Konstruktor() {
        OsobniAutomobil oa = new OsobniAutomobil("AAA", BILA, 0, 1000);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test01_13_Konstruktor() {
        OsobniAutomobil oa = new OsobniAutomobil("AAA", BILA, 1, 0);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test01_14_Konstruktor() {
        OsobniAutomobil oa = new OsobniAutomobil("", BILA, 1, 1000);
        fail();
    }

    @Test
    public void test03_01_GetTyp() {
        OsobniAutomobil oa = new OsobniAutomobil("AAA", BILA, 1, 1000);
        assertEquals(TypyDopravnichProstredku.OSOBNI_AUTOMOBIL, oa.getTyp());
    }

    @Test
    public void test03_02_GetPocetSedadel() {
        OsobniAutomobil oa = new OsobniAutomobil("AAA", BILA, 1, 1000);
        assertEquals(1, oa.getPocetSedadel());
    }

    @Test
    public void test04_01_ToString() {
        OsobniAutomobil oa = new OsobniAutomobil("AAA", BILA, 1, 1000);
        String result = oa.toString();
        String expected = "typ=osobní auto, SPZ=AAA, hmotnost=1000.00,  barva=bílá, početSedadel=1";
        assertEquals(expected, result);

    }

    
}
