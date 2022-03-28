package seznam.prostredky;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author karel@simerda.cz
 */
public class TypyDopravnichProstredkuTest {

    public TypyDopravnichProstredkuTest() {
    }

    @Test
    public void testValues() {
    }

    @Test
    public void testValueOf() {
    }

    @Test
    public void testNazevTruck() {
        TypyDopravnichProstredku typ = TypyDopravnichProstredku.NAKLADNI_AUTOMOBIL;
        assertEquals("truck", typ.nazev());
    }

    @Test
    public void testNazevOsobni() {
        TypyDopravnichProstredku typ = TypyDopravnichProstredku.OSOBNI_AUTOMOBIL;
        assertEquals("osobní auto", typ.nazev());
    }

    @Test
    public void testNazevTraktor() {
        TypyDopravnichProstredku typ = TypyDopravnichProstredku.TRAKTOR;
        assertEquals("traktor", typ.nazev());
    }

    @Test
    public void testToString() {
    }

}
