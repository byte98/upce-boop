package seznam.prostredky;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author karel@simerda.cz
 */
public class TraktorTest {

    public TraktorTest() {
    }

    @Test
    public void tes01_01_Konstruktor() {
        Traktor tr = new Traktor("AAA", 1000, 2000);
        String[] result = {tr.getSpz(), Double.toString(tr.getHmotnost()), Double.toString(tr.getTah())};
        String[] expected = {"AAA", "1000.0", "2000.0"};
        assertArrayEquals(expected, result);

    }

    @Test(expected = IllegalArgumentException.class)
    public void test01_02_Konstruktor() {
        Traktor tr = new Traktor("AAA", 1000, 0);
        fail();
    }

    @Test
    public void test04_01_ToString() {
        Traktor tr = new Traktor("AAA", 1000, 2000);
        String result = tr.toString();
        String expected = "typ=traktor, SPZ=AAA, hmotnost=1000.00, tah=2000.00";
        assertEquals(expected, result);

    }

}
