package cz.upce.fei.boop.nemennatrida;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * TODO Podle těchto testovacích případů implementujte třídu Rozmer.
 *
 *
 * @author karel@simerda.cz
 */
public class RozmerTest {

    /**
     * Porovnání dvou proměnných typu double nebo float nelze operátorem
     * rovnosti, protože binární reprezentace na první pohled stejných hodnot
     * nemusí stejná. Proto se hodnoty musí porovna výpočtem, zda jejich rozdíl
     * je dostatečně malý.
     */
    private static final double DELTA = Double.MIN_NORMAL;

    @Test
    public void testKontrolaDimense1() {

        boolean result = Rozmer.kontrolaDimenze(1);
        assertTrue(result);
    }

    @Test
    public void testKontrolaDimense2() {
        boolean result = Rozmer.kontrolaDimenze(100);
        assertTrue(result);
    }

    @Test
    public void testKontrolaDimense3() {
        boolean result = Rozmer.kontrolaDimenze(0);
        assertFalse(result);
    }

    @Test
    public void testKontrolaDimense4() {
        boolean result = Rozmer.kontrolaDimenze(100.1);
        assertFalse(result);
    }

    @Test
    public void testKontrolaDimense5() {
        boolean result = Rozmer.kontrolaDimenze(0.1);
        assertTrue(result);
    }

    @Test
    public void testKonstruktor00() {
        Rozmer rozmer = new Rozmer(1, 2, 3);
        assertEquals(1, rozmer.getDelka(), DELTA);
        assertEquals(2, rozmer.getSirka(), DELTA);
        assertEquals(3, rozmer.getVyska(), DELTA);
    }

    @Test
    public void testKonstruktor01() {
        Rozmer rozmer = new Rozmer(0.1, 0.1, 0.1);
        assertEquals(0.1, rozmer.getDelka(), DELTA);
        assertEquals(0.1, rozmer.getSirka(), DELTA);
        assertEquals(0.1, rozmer.getVyska(), DELTA);
    }

    @Test
    public void testKonstruktor02() {
        Rozmer rozmer = new Rozmer(100, 100, 100);
        assertEquals(100, rozmer.getDelka(), DELTA);
        assertEquals(100, rozmer.getSirka(), DELTA);
        assertEquals(100, rozmer.getVyska(), DELTA);
    }

    @Test
    public void testKonstruktor03() {
        double dim = Rozmer.DIMENZE_MIN;
        Rozmer rozmer = new Rozmer(dim, dim, dim);
        assertEquals(0.1, rozmer.getDelka(), DELTA);
        assertEquals(0.1, rozmer.getSirka(), DELTA);
        assertEquals(0.1, rozmer.getVyska(), DELTA);
    }

    @Test
    public void testKonstruktor04() {
        double dim = Rozmer.DIMENZE_MIN + 0.001;
        Rozmer rozmer = new Rozmer(dim, dim, dim);
        assertEquals(0.1, rozmer.getDelka(), DELTA);
        assertEquals(0.1, rozmer.getSirka(), DELTA);
        assertEquals(0.1, rozmer.getVyska(), DELTA);
    }

    @Test
    public void testKonstruktor05() {
        double dim = Rozmer.DIMENZE_MAX;
        Rozmer rozmer = new Rozmer(dim, dim, dim);
        assertEquals(100, rozmer.getDelka(), DELTA);
        assertEquals(100, rozmer.getSirka(), DELTA);
        assertEquals(100, rozmer.getVyska(), DELTA);
    }

    @Test
    public void testKonstruktor06() {
        double dim = Rozmer.DIMENZE_MIN + 0.0049;
        Rozmer rozmer = new Rozmer(dim, dim, dim);
        assertEquals(0.1, rozmer.getDelka(), DELTA);
        assertEquals(0.1, rozmer.getSirka(), DELTA);
        assertEquals(0.1, rozmer.getVyska(), DELTA);
    }

    @Test
    public void testKonstruktor07() {
        double dim = Rozmer.DIMENZE_MIN + 0.005;
        Rozmer rozmer = new Rozmer(dim, dim, dim);
        assertEquals(0.11, rozmer.getDelka(), DELTA);
        assertEquals(0.11, rozmer.getSirka(), DELTA);
        assertEquals(0.11, rozmer.getVyska(), DELTA);
    }

    /**
     * Pokud cílem testu je vyvolat výjimku, tak takový test v anotaci definuje
     * vystavení očekávané výjimky
     */
    @Test(expected = MojeException.class)
    public void testKonstruktor11() {
        new Rozmer(0, 0, 0);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor12() {
        new Rozmer(-1, 1, 1);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor13() {
        new Rozmer(1, -1, 1);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor14() {
        new Rozmer(1, 1, -1);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor15a() {
        double dim = Rozmer.DIMENZE_MIN - 0.001;
        new Rozmer(dim, dim, dim);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor15c() {
        double dim = Rozmer.DIMENZE_MIN - 0.001;
        Rozmer rozmer = new Rozmer(dim, dim, dim);
        assertEquals(0.999, rozmer.getVyska(), DELTA);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor16a() {
        double dim = Rozmer.DIMENZE_MAX + 0.001;
        new Rozmer(dim, dim, dim);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor16b() {
        double dim = Rozmer.DIMENZE_MAX + 0.000001;
        new Rozmer(dim, dim, dim);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor21() {
        new Rozmer(0.09, 1, 1);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor22() {
        new Rozmer(1, 0.09, 1);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor23() {
        new Rozmer(1, 1, 0.09);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor24() {
        new Rozmer(1, 1, 0.0);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor25() {
        new Rozmer(1, 0, 1.0);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor26() {
        new Rozmer(0, 1, 1.0);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor31() {
        new Rozmer(100.01, 1, 1);
        fail();
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor32() {
        new Rozmer(1, 100.01, 1);
        fail("");
    }

    @Test(expected = MojeException.class)
    public void testKonstruktor33() {
        new Rozmer(1, 1, 100.01);
        fail();
    }

    @Test
    public void testToString() {
        Rozmer rozmer = new Rozmer(1, 2, 3);
        String str = rozmer.toString();
        assertEquals("Rozmer{delka= 1.00,sirka= 2.00,vyska= 3.00}", str);
    }

    @Test
    public void testToString2() {
        Rozmer rozmer = new Rozmer(99.99, 0.1, 3);
        String str = rozmer.toString();
        assertEquals("Rozmer{delka=99.99,sirka= 0.10,vyska= 3.00}", str);
    }

    /**
     * Tento test prověřuje, zda metoda hashCode je v třídě Rozmer
     * implementována.
     */
    @Test
    public void testHashCode() {
        Rozmer rozmer1 = new Rozmer(1, 2, 3);
        Rozmer rozmer2 = new Rozmer(1, 2, 3);
        int hash1 = rozmer1.hashCode();
        int hash2 = rozmer2.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    public void testHashCode2() {
        Rozmer rozmer1 = new Rozmer(1, 2, 3);
        Rozmer rozmer2 = new Rozmer(1, 2, 3);
        int hash1 = rozmer1.hashCode();
        int hash2 = rozmer2.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    public void testHashCode3() {
        Rozmer rozmer1 = new Rozmer(1, 2, 3);
        Rozmer rozmer2 = new Rozmer(2, 2, 3);
        int hash1 = rozmer1.hashCode();
        int hash2 = rozmer2.hashCode();
        assertNotEquals(hash1, hash2);
    }

    /**
     * Operátor == porovnává reference, proto výsledek je FALSE
     */
    @Test
    public void testEquals01() {
        Rozmer rozmer1 = new Rozmer(1, 2, 3);
        Rozmer rozmer2 = new Rozmer(1, 2, 3);
        boolean result = rozmer1 == rozmer2;
        assertFalse(result);
    }

    /**
     * V tomto testu se porovnání provede překrytou metodou equals, proto je
     * výsledek TRUE, kdyby třída Rozmer nepřekryla metodu equals, tak by se
     * porovnaly reference,
     */
    @Test
    public void testEquals02() {
        Rozmer rozmer1 = new Rozmer(1, 2, 3);
        Rozmer rozmer2 = new Rozmer(1, 2, 3);
        boolean result = rozmer1.equals(rozmer2);
        assertTrue(result);
    }

    /**
     * Důkaz, že metody potomků překrývají (zabijí) metody předka se stejnou
     * signaturou.
     *
     * Tento test je též ukázkou substitučního principu. Tam kde je očekáván
     * předek, může být použit jakýkoliv jeho potomek.
     */
    @Test
    public void testEquals03() {
        Object rozmer1 = new Rozmer(1, 2, 3);
        Object rozmer2 = new Rozmer(1, 2, 3);
        boolean result = rozmer1.equals(rozmer2);
        assertTrue(result);
    }

    @Test
    public void testEquals04() {
        Object rozmer1 = new Rozmer(1, 2, 3);
        Object rozmer2 = new Object();
        boolean result = rozmer1.equals(rozmer2);
        assertFalse(result);
    }

    @Test
    public void testEquals05() {
        Object rozmer1 = new Rozmer(1, 2, 3);
        boolean result = rozmer1.equals(null);
        assertFalse(result);
    }

    @Test
    public void testEquals06() {
        Object rozmer1 = new Rozmer(1, 2, 3);
        boolean result = rozmer1.equals(rozmer1);
        assertTrue(result);
    }

    @Test
    public void testEquals07() {
        Object rozmer1 = new Rozmer(1, 2, 3);
        Object rozmer2 = new Rozmer(2, 2, 3);
        boolean result = rozmer1.equals(rozmer2);
        assertFalse(result);
    }
    
     @Test
    public void testEquals08() {
        Object rozmer1 = new Rozmer(1, 2, 3);
        Object rozmer2 = new Rozmer(1, 3, 3);
        boolean result = rozmer1.equals(rozmer2);
        assertFalse(result);
    }
    
     @Test
    public void testEquals09() {
        Object rozmer1 = new Rozmer(1, 2, 3);
        Object rozmer2 = new Rozmer(1, 2, 4);
        boolean result = rozmer1.equals(rozmer2);
        assertFalse(result);
    }
}
