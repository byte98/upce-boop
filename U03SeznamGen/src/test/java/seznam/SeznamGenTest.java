package seznam;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TestName;
import seznam.prostredky.NakladniAutomobil;
import seznam.prostredky.OsobniAutomobil;
import seznam.prostredky.Prostredek;
import static util.Barva.*;

/**
 *
 * @author karel@simerda.cz
 */
public class SeznamGenTest {

    @Rule
    public TestName testName = new TestName();

    static void print(String methodName) {
        System.out.println(String.format("SeznamProstredku %s   ",
                methodName));
    }

    static OsobniAutomobil OA01a = new OsobniAutomobil("OA01", BILA, 4, 2000.0);
    static OsobniAutomobil OA01b = new OsobniAutomobil("OA01", BILA, 4, 2000.0);

    static OsobniAutomobil OA02a = new OsobniAutomobil("OA02", MODRA, 7, 3000.0);
    static OsobniAutomobil OA02b = new OsobniAutomobil("OA02", MODRA, 7, 3000.0);

    static OsobniAutomobil OA03a = new OsobniAutomobil("OA03", BILA, 5, 2000.0);
    static OsobniAutomobil OA03b = new OsobniAutomobil("OA03", BILA, 5, 2000.0);

    static OsobniAutomobil OA04a = new OsobniAutomobil("OA01", BILA, 4, 3000.0);
    static OsobniAutomobil OA04b = new OsobniAutomobil("OA01", BILA, 4, 3000.0);

    static NakladniAutomobil NA01a = new NakladniAutomobil("NA01", 5, 20000.0);
    static NakladniAutomobil NA01b = new NakladniAutomobil("NA01", 3, 20000.0);

    static NakladniAutomobil NA02a = new NakladniAutomobil("NA02", 4, 30000.0);
    static NakladniAutomobil NA02b = new NakladniAutomobil("NA02", 2, 30000.0);

    public SeznamGenTest() {
    }

    @Test
    public void test01_01_Konstruktlor() {
        Seznam seznam = new SeznamGen(1);
        assertEquals(0, seznam.pocet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test01_02_Konstruktlor() {
        Seznam seznam = new SeznamGen(0);
        fail();
    }

    @Test
    public void test02_01Vloz() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        assertEquals(1, seznam.pocet());
    }

    @Test
    public void test02_02_Vloz() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a);
        assertEquals(2, seznam.pocet());
    }

    @Test
    public void test02_03_Vloz() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, NA01a, OA03a, OA04a, NA02a);
        assertEquals(6, seznam.pocet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test02_04_Vloz() {
        print(testName.getMethodName());
        Prostredek prostredek = null;
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(prostredek);
        fail();
    }

    @Test(expected = IllegalAccessError.class)
    public void test03_01_DejPrazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.dej(1);
        fail();
    }

    @Test(expected = IllegalAccessError.class)
    public void test03_02_DejPrazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.dej(0);
        fail();
    }

    @Test
    public void test03_03_DejNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        Object result = seznam.dej(1);
        assertEquals(OA01a, result);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test03_04_DejNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(NA01a);
        seznam.dej(0);
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test03_05_DejNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(NA01a);
        seznam.dej(2);
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test03_06_DejNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(NA01a);
        seznam.dej(-1);
        fail();
    }

    /**
     * XXX Jak Java porovnává instance?
     * <p>
     * Následujících několik variant testovacích případů prezentuje, jak dochází
     * k porovnávání. Je totiž rozdíl v chování operátoru rovnosti == a metody
     * equals. Operátor rovnosti porovnává reference a metoda equals porovnává
     * podle obsahu. Pokud ovšem metodu equals nepřekryjeme, tak se chování obou
     * dvou variant shodují, protože metoda ve třídě Object může porovanávat
     * pouze reference. Zde je metoda ze třídy Object
     * <pre>
     *       public boolean equals(Object obj) {
     *           return (this == obj);
     *       }
     * </pre>
     * <p>
     * Ve variante "a" je použito přetypování na vyzvednuté instance ze seznamu
     * na očekávaný datový typ. Ve variantě "b" to tak není, přesto překladač
     * nehlásí chybu.
     * <p>
     * Proč tomu tak je?
     * <p>
     * Je tomu proto, že se v obou testovacích případech porovnávají odkazy
     * (referenční hodnoty) na stejnou instanci a ty jsou ve dvou proměnných v
     * našem případě stejné. Těmi proměnnými jsou statická NA02a a lokální
     * result. Obě tyto dvě proměnné obsahují stejné číslo.
     * <p>
     * Když překryjeme metodu equals ve třídě, to potom porovnáváme obsah dvou
     * instancí (objektů)a ne jejich reference. Metodu equals máme ve třídě
     * Prostredek překrytou a proto test ve variantě "c" projde i když
     * porovnáváme dvě odlišné instance. Dále jsou rozdíly v porovnávání ještě
     * ve variantách "d" a "e".
     * <p>
     *
     * Doporučení: U textových řetězců, když chceme dva porovnat, zda jsou
     * obsahově shodné, tak použijeme metodu equals než operátor rovnosti (tj.
     * ==). Příklad
     * <pre>
     * public class Test {
     *   public static void main(String[] args) {
     *     String s1 = new String("HELLO");
     *     String s2 = new String("HELLO");
     *     System.out.println(s1 == s2);
     *     System.out.println(s1.equals(s2));
     *   }
     * }
     * Výstup:
     * false
     * true
     * </pre>
     *
     */
    @Test
    public void test03_07a_DejNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, NA01a, OA03a, OA04a, NA02a);
        NakladniAutomobil result = (NakladniAutomobil) seznam.dej(6);
        assertEquals(NA02a == result, true);
    }

    @Test
    public void test03_07b_DejNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, NA01a, OA03a, OA04a, NA02a);
        Object result = seznam.dej(6);
        assertEquals(NA02a == result, true);
    }

    /**
     * Tato varianta porovnává dvě odlišné instance se stejným obsahem. Máme
     * překrytou metodu equals.
     */
    @Test
    public void test03_07c_DejNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        Object result = seznam.dej(1);
        assertEquals(OA01b, result);
    }

    /**
     * Ukázka chování metody equals, zde se porovnává obsah dvou instancí
     */
    @Test
    public void test03_07d_DejNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        Object result = seznam.dej(1);
        assertTrue(OA01b.equals(result));
    }

    /**
     * Ukázka chování operátoru porovnání ==, zde se porovnávají reference
     */
    @Test
    public void test03_07e_DejNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        Object result = seznam.dej(1);
        assertFalse(OA01b == result);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test03_08_DejNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, NA01a);
        seznam.dej(3);
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test03_09_DejNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, NA01a, OA03a, OA04a, NA02a);
        seznam.dej(7);
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test03_10_DejNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, NA01a, OA03a, OA04a, NA02a);
        seznam.dej(7);
        fail();
    }

    @Test
    public void test04_01_DejDopravniProstredky() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, NA01a, OA03a, OA04a, NA02a);
        Prostredek[] expResult = {OA01a, OA02a, NA01a, OA03a, OA04a, NA02a};
        Object[] result = seznam.dejDopravniProstredky();
        assertArrayEquals(expResult, result);
    }

    /**
     * Kontrola, zda dodává kopie pole.
     */
    
    @Test
    public void test04_02_DejDopravniProstredky() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, NA01a, OA03a, OA04a, NA02a);
        Prostredek[] expResult = {OA01a, OA02a, NA01a, OA03a, OA04a, NA02a};
        Object[] kopie = seznam.dejDopravniProstredky();
        kopie[0] = NA01a; //  provede se změna v kopii
        // Znovu převzetí kopie seznamu prostředků, nesmí dojít ke změně
        Object[] result = seznam.dejDopravniProstredky();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void test04_03_DejDopravniProstredky() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, NA01a);
        Object[] result = seznam.dejDopravniProstredky();
        assertEquals(2, result.length);
    }

    @Test
    public void test04_04_DejDopravniProstredky() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, NA01a, OA02a);
        Object[] result = seznam.dejDopravniProstredky();
        assertEquals(3, result.length);
    }

    @Test
    public void test04_05_DejDopravniProstredky() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, NA01a, OA03a, OA04a, NA02a);
        Object[] result = seznam.dejDopravniProstredky();
        assertEquals(6, result.length);
    }

    @Test
    public void test05_01_DejKopieDopravniProstredku() {
        try {
            print(testName.getMethodName());
            SeznamGen seznam = new SeznamGen(1);
            seznam.vloz(OA01a, OA02a, NA01a, OA03a, OA04a, NA02a);
            Prostredek[] expResult = {OA01a, OA02a, NA01a, OA03a, OA04a, NA02a};
            Object[] result = seznam.dejKopieDopravniProstredku();
            assertArrayEquals(expResult, result);
        } catch (CloneNotSupportedException ex) {
            fail();
        }
    }

    /**
     * Tento test zjišťtuje, zda došlo ke klonování vloženého prostředku do
     * seznamu
     */
    @Test
    public void test05_02_DejKopieDopravniProstredku() {
        try {
            print(testName.getMethodName());
            SeznamGen seznam = new SeznamGen(1);
            seznam.vloz(OA01a);
            Object[] result = seznam.dejKopieDopravniProstredku();
            assertFalse(result[0] == OA01a);
            assertEquals(result[0], OA01a);
        } catch (CloneNotSupportedException ex) {
            fail();
        }
    }

    @Test(expected = IllegalAccessError.class)
    public void test06_01_OdeberPrazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.odeber(1);
        fail();
    }

    @Test(expected = IllegalAccessError.class)
    public void test06_02_OdeberPrazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.odeber(0);
        fail();
    }

    @Test(expected = IllegalAccessError.class)
    public void test06_03_OdeberPrazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.odeber(-1);
        fail();
    }

    @Test(expected = IllegalAccessError.class)
    public void test06_04_OdeberPrazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.odeber(2);
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test06_05_OdeberNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        seznam.odeber(2);
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test06_06_OdeberNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        seznam.odeber(0);
        fail();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test06_07_OdeberNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        seznam.odeber(-1);
        fail();
    }

    @Test
    public void test06_08_OdeberNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        Object result = seznam.odeber(1);
        assertEquals(OA01a, result);
    }

    @Test
    public void test06_09_OdeberNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a);
        Object result = seznam.odeber(1);
        assertEquals(OA01a, result);
    }

    @Test
    public void test06_10_OdeberNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a);
        Object result = seznam.odeber(2);
        assertEquals(OA02a, result);
    }

    @Test
    public void test06_11_OdeberNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        seznam.odeber(1);
        assertEquals(0, seznam.pocet());
    }

    @Test
    public void test06_12_OdeberNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        seznam.odeber(1);
        Prostredek[] expResult = {};
        Object[] result = seznam.dejDopravniProstredky();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void test06_13_OdeberNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a);
        seznam.odeber(1);
        assertEquals(1, seznam.pocet());
    }

    @Test
    public void test06_14_OdeberNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a);
        seznam.odeber(1);
        Prostredek[] expResult = {OA02a};
        Object[] result = seznam.dejDopravniProstredky();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void test06_15_OdeberNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a);
        seznam.odeber(2);
        Prostredek[] expResult = {OA01a};
        Object[] result = seznam.dejDopravniProstredky();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void test06_16_OdeberNeprazdnySeznam() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, NA01a, OA03a, OA04a, NA02a);
        seznam.odeber(2);
        seznam.odeber(2);
        seznam.odeber(2);
        Prostredek[] expResult = {OA01a, OA04a, NA02a};
        Object[] result = seznam.dejDopravniProstredky();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void test07_01_IteratorHasNexEmpty() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        Iterator<Object> iterator = seznam.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void test07_02_IteratorNextEmpty() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        Iterator<Object> iterator = seznam.iterator();
        iterator.next();
//        fail();
    }

    @Test
    public void test07_03_IteratorHasNextOneItemReturnTrue() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        Iterator<Object> iterator = seznam.iterator();
        assertTrue(iterator.hasNext());
    }

    @Test
    public void test07_04_IteratorHasNextReturnOneItemTrueSeveralTimes() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        Iterator<Object> iterator = seznam.iterator();
        seznam.vloz(OA01a);
        for (int i = 0; i < 10; i++) {
            assertTrue(iterator.hasNext());
        }
    }

    @Test
    public void test07_05_IteratorHasNexNextOneItem() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        Iterator<Object> iterator = seznam.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(OA01a, iterator.next());
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void test07_06_IteratorHasNexNextOneItem() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, OA03a, NA01a, NA02a);

        Iterator<Object> iterator = seznam.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(OA01a, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(OA02a, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(OA03a, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(NA01a, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(NA02a, iterator.next());

        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void test08_01_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {0, 1};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_02_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {1, 1};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_03_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {2, 2};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_04_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, OA03a);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {3, 4};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_05_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, OA03a, NA01a);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {4, 4};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_06_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, OA03a, NA01a, NA02a);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {5, 8};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_07_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, OA03a, NA01a, NA02a);
        seznam.odeber(1);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {4, 8};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_08_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, OA03a, NA01a, NA02a);
        seznam.odeber(1);
        seznam.odeber(1);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {3, 8};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_09_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, OA03a, NA01a, NA02a);
        seznam.odeber(5);
        seznam.odeber(4);
        seznam.odeber(2);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {2, 8};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_10_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, OA03a, NA01a, NA02a);
        seznam.odeber(5);
        seznam.odeber(4);
        seznam.odeber(3);
        seznam.odeber(1);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {1, 4};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_11_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, OA03a, NA01a, NA02a);
        seznam.odeber(5);
        seznam.odeber(4);
        seznam.odeber(3);
        seznam.odeber(2);
        seznam.odeber(1);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {0, 2};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_12_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(1);
        seznam.vloz(OA01a, OA02a, OA03a, NA01a, NA02a);
        seznam.odeber(5);
        seznam.odeber(4);
        seznam.odeber(3);
        seznam.odeber(2);
        seznam.odeber(1);
        seznam.vloz(OA01a);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {1, 2};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_13_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(3);
        seznam.vloz(OA01a);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {1, 3};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_14_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(3);
        seznam.vloz(OA01a, OA02a, OA03a);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {3, 3};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_15_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(3);
        seznam.vloz(OA01a, OA02a, OA03a, NA01a);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {4, 6};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_16_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(3);
        seznam.vloz(OA01a, OA02a, OA03a, NA01a, NA02b);
        seznam.odeber(5);
        seznam.odeber(4);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {3, 6};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_17_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(3);
        seznam.vloz(OA01a, OA02a, OA03a, NA01a, NA02b);
        seznam.odeber(5);
        seznam.odeber(4);
        seznam.odeber(3);
        seznam.odeber(2);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {1, 6};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test08_18_PocetKapacita() {
        print(testName.getMethodName());
        Seznam seznam = new SeznamGen(3);
        seznam.vloz(OA01a, OA02a, OA03a, NA01a, NA02b);
        seznam.odeber(5);
        seznam.odeber(4);
        seznam.odeber(3);
        seznam.odeber(2);
        seznam.odeber(1);
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {0, 3};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test09_01_Zrus() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.zrus();
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {0, 1};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test09_02_Zrus() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        seznam.zrus();
        int[] result = {seznam.pocet(), seznam.kapacita()};
        int[] expected = {0, 1};
        assertArrayEquals(expected, result);
    }

    @Test
    public void test09_03_Zrus() {
        print(testName.getMethodName());
        SeznamGen seznam = new SeznamGen(1);
        seznam.vloz(OA01a);
        seznam.zrus();
        Object[] result = seznam.dejDopravniProstredky();
        assertEquals(0, result.length);
    }

}
