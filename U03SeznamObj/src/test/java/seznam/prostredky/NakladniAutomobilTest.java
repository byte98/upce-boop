package seznam.prostredky;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author karel@simerda.cz
 */
public class NakladniAutomobilTest {

    public NakladniAutomobilTest() {
    }

    @Test
    public void tes01_01_Konstruktor() {
        NakladniAutomobil na = new NakladniAutomobil("AAA", 5, 1000);
        String[] result = {na.getSpz(), Integer.toString(na.getPocetNaprav()), Double.toString(na.getHmotnost())};
        String[] expected = {"AAA", "5", "1000.0"};
        assertArrayEquals(expected, result);

    }

    @Test(expected = IllegalArgumentException.class)
    public void test01_02_Konstruktor() {
        NakladniAutomobil na = new NakladniAutomobil("AAA", 0, 1000);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test01_03_Konstruktor() {
        NakladniAutomobil na = new NakladniAutomobil("AAA", -1, 1000);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test01_04_Konstruktor() {
        NakladniAutomobil na = new NakladniAutomobil("", -1, 1000);
        fail();
    }

    @Test
    public void test04_01_ToString() {
        NakladniAutomobil na = new NakladniAutomobil("AAA", 5, 1000);
        String result = na.toString();
        String expected = "typ=truck, SPZ=AAA, hmotnost=1000.00, početNáprav=5";
        assertEquals(expected, result);

    }

}
