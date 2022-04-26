package cz.upce.fei.skoda.boop.pujcovnacmdskoda.kolekce;

import cz.upce.fei.skoda.boop.pujcovnaguiskoda.kolekce.LinkSeznam;
import cz.upce.fei.skoda.boop.pujcovnaguiskoda.kolekce.KolekceException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author karel@simerda.cz
 */
public class LinkSeznamTest {

//<editor-fold defaultstate="collapsed" desc="Testovací třída a objekty">    
    private static class TestClass {

        int a;

        public TestClass(int a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return "T" + a;
        }

    }

    private final TestClass T1 = new TestClass(1);
    private final TestClass T2 = new TestClass(2);
    private final TestClass T3 = new TestClass(3);
    private final TestClass T4 = new TestClass(4);
    private final TestClass T5 = new TestClass(5);
    private final TestClass T6 = new TestClass(6);
    private final TestClass T7 = new TestClass(7);
    private final TestClass T8 = new TestClass(8);
    private final TestClass T9 = new TestClass(9);
//</editor-fold>

    public LinkSeznamTest() {
    }

    //<editor-fold defaultstate="collapsed" desc="01 Vkládání prvního prvku">
    @Test
    public void test_01_01_VlozPrvni() {
        try {
            LinkSeznam<TestClass> instance = new LinkSeznam<>();
            instance.vlozPrvni(T1);
            TestClass[] result = {instance.dejPrvni(), instance.dejPosledni()};
            TestClass[] expected = {T1, T1};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }
    
    @Test(expected = NullPointerException.class)
    public void test_01_02_VlozPrvni_NullPointerException() {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPrvni(null);
        fail();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="02 Vkládání posledního prvku">
    @Test
    public void test_02_01_VlozNaKonec() {
        try {
            LinkSeznam<TestClass> instance = new LinkSeznam<>();
            instance.vlozPosledni(T1);
            TestClass[] result = {instance.dejPrvni(), instance.dejPosledni()};
            TestClass[] expected = {T1, T1};
            assertArrayEquals(expected, result);
        } catch (KolekceException ex) {
            fail();
        }
    }
    
    @Test(expected = NullPointerException.class)
    public void test_02_02_VlozPosledni_NullPointerException() {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPosledni(null);
        fail();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="03 Kontrola nastavení prvního prvku">
    @Test
    public void test_03_01_NastavPrvni() {
        try {
            LinkSeznam<TestClass> instance = new LinkSeznam<>();
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            TestClass result = instance.dejAktualni();
            TestClass expected = T1;
            assertEquals(expected, result);
        } catch (KolekceException ex) {
            fail();
        }
    }
    
    @Test(expected = KolekceException.class)
    public void test_03_02_NastavPrvni_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> list = new LinkSeznam<>();
        list.nastavPrvni();
        fail();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="04 Kontrola nastavení posledního prvku">
    @Test
    public void test_04_01_NastavPosledni() {
        try {
            LinkSeznam<TestClass> instance = new LinkSeznam<>();
            instance.vlozPrvni(T1);
            instance.nastavPosledni();
            TestClass result = instance.dejAktualni();
            TestClass expected = T1;
            assertEquals(expected, result);
        } catch (KolekceException ex) {
            fail();
        }
    }
    
    @Test(expected = KolekceException.class)
    public void test_04_02_NastavPosledni_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> list = new LinkSeznam<>();
        list.nastavPosledni();
        fail();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="05 Testy metod dej">
    @Test
    public void test_05_01_DejPrvni() throws KolekceException {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPrvni(T1);
        assertEquals(T1, instance.dejPrvni());
    }
    
    @Test(expected = KolekceException.class)
    public void test_05_02_DejPrvni_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.dejPrvni();
        fail();
    }
    
    @Test(expected = KolekceException.class)
    public void test_05_03_DejPosledni_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.dejPosledni();
        fail();
    }
    
    @Test(expected = KolekceException.class)
    public void test_05_04_DejAktualni_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.dejAktualni();
        fail();
    }
    
    @Test(expected = KolekceException.class)
    public void test_05_05_DejZaAktualnim_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.dejZaAktualnim();
        fail();
    }
    
    @Test(expected = KolekceException.class)
    public void test_05_06_DejPredAktualnim_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.dejPredAktualnim();
        fail();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="06 Testy metody odeberPrvni">
    @Test
    public void test_06_01_OdeberPrvniJedenZJednoho() throws KolekceException {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPrvni(T1);
        assertEquals(T1, instance.odeberPrvni());
    }
    
    @Test
    public void test_06_02_OdeberPrvni() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.vlozPosledni(T2);
        instance.odeberAktualni();
        assertEquals(instance.odeberPrvni(), T2);
    }
    
    @Test(expected=KolekceException.class)
    public void test_06_03_OdeberPrvni_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.odeberPrvni();
        fail();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="07 Testy metody odeberPosledni">
    @Test
    public void test_07_01_OdeberPosledniJedenZJednoho() throws KolekceException {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPrvni(T1);
        assertEquals(T1, instance.odeberPosledni());
    }

    @Test(expected=KolekceException.class)
    public void test_07_02_OdeberPosledni_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.odeberPosledni();
        fail();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="08 Procházení seznamu metodou dalsi">
    @Test(expected = KolekceException.class)
    public void test_08_01_Dalsi_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> list = new LinkSeznam<>();
        list.dalsi();
        fail();
    }
    
    @Test(expected = KolekceException.class)
    public void test_08_02_Dalsi_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> list = new LinkSeznam<>();
        list.vlozPrvni(T1);
        list.nastavPosledni();
        list.dalsi();
        fail();
    }
    
    @Test
    public void test_08_03_Dalsi() throws KolekceException
    {
        LinkSeznam<TestClass> list = new LinkSeznam<>();
        list.vlozPrvni(T1);
        list.vlozPrvni(T2);
        list.nastavPrvni();
        list.dalsi();
        assertEquals(T1, list.dejAktualni());
    }
    
    @Test
    public void test_08_04_JeDalsi() throws KolekceException {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPrvni(T1);
        instance.vlozPrvni(T2);
        instance.nastavPrvni();
        assertTrue(instance.jeDalsi());
    }

//</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="09 Testy metod vlozZaAktualni a vlozPredAktualni">
    /**
     * Ukazatel na aktuální prvek se metodou vlož neposouvá.
     *
     * @throws KolekceException
     */
    @Test(expected = NullPointerException.class)
    public void test_09_01_VlozZaAktualni_NullPointerException() throws KolekceException
    {
        LinkSeznam<TestClass> list = new LinkSeznam<>();
        list.vlozZaAktualni(null);
        fail();
    }
    
    @Test(expected = KolekceException.class)
    public void test_09_02_VlozZaAktualni_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> list = new LinkSeznam<>();
        list.vlozZaAktualni(T1);
        fail();
    }
    
    @Test
    public void test_09_03_VlozZaAktualni() throws KolekceException
    {
        LinkSeznam<TestClass> list = new LinkSeznam<>();
        list.vlozPrvni(T1);
        list.nastavPrvni();
        list.vlozZaAktualni(T2);
        assertEquals(T2, list.dejPosledni());
    }
    
    @Test (expected = NullPointerException.class)
    public void test_09_04_VlozPredAktualni_NullPointerException() throws KolekceException
    {
        LinkSeznam<TestClass> list = new LinkSeznam<>();
        list.vlozPredAktualnim(null);
        fail();
    }
    
    @Test (expected = KolekceException.class)
    public void test_09_05_VlozPredAktualni_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> list = new LinkSeznam<>();
        list.vlozPredAktualnim(T1);
        fail();
    }

    @Test
    public void test_09_06_VlozPredAktualni() throws KolekceException
    {
        LinkSeznam<TestClass> list = new LinkSeznam<>();
        list.vlozPrvni(T1);
        list.nastavPrvni();
        list.vlozPredAktualnim(T2);
        assertEquals(T2, list.dejPrvni());
    }
    
    @Test
    public void test_09_07_VlozPredAktualni() throws KolekceException
    {
        LinkSeznam<TestClass> list = new LinkSeznam<>();
        list.vlozPrvni(T1);
        list.nastavPrvni();
        list.vlozPrvni(T2);
        list.vlozPredAktualnim(T3);
        assertEquals(T3, list.dejPredAktualnim());
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="10 Testy metod dejAktualni"> 
    @Test
    public void test_10_01_DejAktualniPosledni() throws KolekceException {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPosledni(T2);       
        instance.nastavPosledni();
        assertEquals(T2, instance.dejAktualni());
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="11 Testy metod dejZaAkualnim">
    @Test
    public void test_11_01_DejZaAktualnim() throws KolekceException {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPosledni(T2);
        instance.vlozPosledni(T1);
        instance.nastavPrvni();
        assertEquals(T1, instance.dejZaAktualnim());
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="12 Testy metod odeberAktualni">
    @Test
    public void test_12_01_OdeberAktualniPrvni() throws KolekceException {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPosledni(T2);
        instance.nastavPrvni();
        TestClass result = instance.odeberAktualni();
        assertEquals(T2, result);
    }
    
    @Test(expected=KolekceException.class)
    public void test_12_02_OdeberAktualni_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.odeberAktualni();
        fail();
    }
    
    @Test (expected = KolekceException.class)
    public void test_12_03_OdeberAktualni_KolekceException() throws KolekceException {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPosledni(T1);
        instance.nastavPrvni();
        instance.vlozPosledni(T2);
        instance.odeberPrvni();
        instance.odeberAktualni();
        fail();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="13 Testy metod odeberZaAktualnim a odeberPredAktualnim">
    @Test
    public void test_13_01_OdeberZaAktualnim() throws KolekceException {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPosledni(T2);
        instance.vlozPosledni(T1);
        instance.vlozPosledni(T3);
        instance.nastavPrvni();
        TestClass result = instance.odeberZaAktualnim();
        assertEquals(T1, result);
    }
    
    @Test(expected=KolekceException.class)
    public void test_13_02_OdeberZaAktualnim_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.odeberZaAktualnim();
        fail();
    }
    
    @Test(expected=KolekceException.class)
    public void test_13_03_OdeberPredAktualnim_KolekceException() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.odeberPredAktualnim();
        fail();
    }
    
    @Test
    public void test_13_04_OdeberPredAktualnim() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.vlozPrvni(T2);
        instance.vlozPrvni(T3);
        instance.vlozPrvni(T4);
        assertEquals(instance.odeberPredAktualnim(), T2);
    }
    
    @Test
    public void test_13_05_OdeberPredAktualnim() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.vlozPrvni(T2);
        instance.odeberPredAktualnim();
        assertEquals(instance.dejPrvni(), T1);
    }
    
    @Test
    public void test_13_05_OdeberZaAktualnim() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPrvni(T1);
        instance.nastavPrvni();
        instance.vlozPosledni(T2);
        instance.odeberZaAktualnim();
        assertEquals(instance.dejPosledni(), T1);
    }
    
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="14 Testy iterátoru">
    @Test
    public void test_14_01_Iterator() {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPosledni(T2);
        Iterator<TestClass> iterator = instance.iterator();
        assertEquals(T2, iterator.next());
    }
    
    @Test
    public void test_14_02_Iterator()
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        TestClass[] data = {T1, T2, T3};
        for(TestClass d: data)
        {
            instance.vlozPosledni(d);
        }
        Iterator<TestClass> it = instance.iterator();
        int idx = 0;
        while(it.hasNext())
        {
            assertEquals(it.next(), data[idx]);
            idx++;
        }
        assertEquals(data.length, idx);
    }
    
    @Test
    public void test_14_03_Iterator()
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPosledni(T1);
        Iterator<TestClass> it = instance.iterator();
        assertTrue(it.hasNext());
    }
    
    @Test
    public void test_14_04_Iterator() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPosledni(T1);
        instance.vlozPosledni(T2);
        Iterator<TestClass> it = instance.iterator();
        instance.odeberPrvni();
        assertTrue(it.hasNext());        
    }
    
    @Test
    public void test_14_05_Iterator() throws KolekceException
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPosledni(T1);
        instance.vlozPosledni(T2);
        Iterator<TestClass> it = instance.iterator();
        instance.odeberPrvni();
        instance.odeberPosledni();
        //assertFalse(it.hasNext());        
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="15 Testy na velikost seznamu">
    @Test
    public void test_15_01_JePrazdny() {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        assertTrue(instance.jePrazdny());
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="16 Výkonnostní testy">
    @Test(timeout = 200L)
    public void test_Size10() {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        for (int i = 0; i < 1000000; i++) {
            instance.vlozPosledni(T1);
        }
        int p = instance.size();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="17 Testy metody zrus">
    @Test
    public void test_17_01_Zrus()
    {
        LinkSeznam<TestClass> instance = new LinkSeznam<>();
        instance.vlozPrvni(T1);
        instance.zrus();
        assertTrue(instance.jePrazdny());
    }
    //</editor-fold>    
    //<editor-fold defaultstate="collapsed" desc="Privátní pomocné metody">
    private TestClass[] dejPole(LinkSeznam<TestClass> instance) {
        int pocet = instance.size();
        TestClass[] pole = new TestClass[pocet];
        Iterator<TestClass> iterator = instance.iterator();
        for (int i = 0; i < pocet; i++) {
            pole[i] = iterator.next();
        }
        return pole;
    }
    //</editor-fold>
}
