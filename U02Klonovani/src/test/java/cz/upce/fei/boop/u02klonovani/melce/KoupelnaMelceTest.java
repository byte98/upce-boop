package cz.upce.fei.boop.u02klonovani.melce;

import cz.upce.fei.boop.u02klonovani.MojeException;
import cz.upce.fei.boop.u02klonovani.Rozmer;
import cz.upce.fei.boop.u02klonovani.Vana;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Testy instancí trídy KoupelnaMelce kontrolují, zda třída má požadované
 * vlastnosti. Tou hlavní kontrolou je, zda dochazí pouze k mělkému klonování.
 *
 * Dále se testy využívají k procvičování přetěžování konstruktorů.
 *
 * Na příkladu koupelny z vanou je na první pohled jasné, že mělké klonování
 * neodpovídá realitě, protože vanu nelze sdílet mezi dvěmi koupelnami. Jedině
 * že by byla přenosná, potom by to šlo, ale potom by se nemohla jedna vana ve
 * stejném čase vyskytovat na dvou místech současně.
 *
 *
 * @author karel@simerda.cz
 */
public class KoupelnaMelceTest {

    /**
     * Tento test ve volání konstruktoru určuje jaké je pořadí parametrů a jaké
     * jsou jejich typy. Dále testuje, zda koupelna byla vytvořena mělce.
     *
     * První parametr konstruktoru je název koupelny, druhý je odkaz na její
     * rozměr a třetí je odkaz na vanu.
     *
     * Kontrolou se provnají dvě hodnoty reference na instanci vany a protože se
     * jedná o mělké klonování, tak tyto hodnoty musí být stejné.
     *
     * @throws java.lang.CloneNotSupportedException Tato výjimka se vystaví,
     * když třída nepovoluje klonování
     */
    @Test
    public void test01Constructor() throws CloneNotSupportedException {
        Vana vana = new Vana("Ravak", new Rozmer(2.0, 1.2, 0.5));
        Rozmer rozmerKoupelny = new Rozmer(4, 5, 3);
        KoupelnaMelce koupelna = new KoupelnaMelce("K1", rozmerKoupelny, vana);
        KoupelnaMelce klonKoupelny = koupelna.clone();
        Vana result = klonKoupelny.getVana();
        assertEquals(vana, result);
    }

    /**
     * Tento test má o proti předchozímu testu jinou signaturu konstruktoru,
     * tzn. že třída KoupelnaMelce bude mít přetížené konstruktory.
     *
     * Podle kontroly očekávaného rozměru a rozměru z instnace koupelny lze
     * odvodit, co který parametr konstruktoru znamená.
     */
    @Test
    public void test02Constructor() {
        Vana vana = new Vana("Ravak", new Rozmer(2.5, 1.2, 0.5));
        KoupelnaMelce koupelna = new KoupelnaMelce("K1", 3, 2, 3, vana);
        Rozmer expResult = new Rozmer(3, 2, 3);
        Rozmer result = koupelna.getRozmer();
        assertEquals(expResult, result);
    }

    /**
     * Tento test ověří, zda není ve volání konstruktoru použita hodnota null
     * místo odkazu na instanci vany.
     *
     * Testo obsahuje kontrolu, zda jsem nezapoměli vystavit výjimku. Když se
     * výjimka vystaví, tak se to ošetří parametrem excepted se jmenem očekávané
     * výjimky a test může pobíhat dál. Jestliže však výjimku nevystavíme,
     * konstruktor normálně proběhne a hlášení chyby by nenastalo. Hlášení chyby
     * způsobí až metoda fail().
     *
     */
    @Test(expected = NullPointerException.class)
    public void test03Constructor() {
        KoupelnaMelce koupelna = new KoupelnaMelce("K1", 3, 2, 3, null);
        fail("Nevystavila se výjimka NullPointerException z důvodu null ve parametru na vanu koupelny");

    }

    /**
     * Tento test ověří, zda není ve volání konstrultoru použita hodnota null
     * místo odkazu na instanci rozmeru koupelny.
     *
     * Kontrola nevystavení výjimky proběhne stejně jako v předchozím případu.
     */
    @Test(expected = NullPointerException.class)
    public void test04Constructor() {
        Vana vana = new Vana("Ravak", new Rozmer(2.5, 1.2, 0.5));
        KoupelnaMelce koupelna = new KoupelnaMelce("K1", null, vana);
        fail("Nevystavila se výjimka NullPointerException z důvodu null v rozměru koupelny");

    }

    /**
     * Tento test prověří, zda není ve volání konstruktoru použita hodnota null
     * místo odkazu na jméno koupelny.
     *
     * Kontrola nevystavení výjimky proběhne stejně jako v předchozích
     * případech.
     */
    @Test(expected = NullPointerException.class)
    public void test05Constructor() {
        Vana vana = new Vana("Ravak", new Rozmer(2.5, 1.2, 0.5));
        KoupelnaMelce koupelna = new KoupelnaMelce(null, new Rozmer(1, 1, 1), vana);
        fail("Nevystavila se výjimka NullPointerException z důvodu null ve jménu koupelny");
    }

    /**
     * Tento test prověří, zda není ve volání konstruktoru není použit prázdný
     * řetězec jako jméno koupelny.
     *
     * Kontrola nevystavení výjimky proběhne stejně jako v předchozích
     * případech.
     */
    @Test(expected = MojeException.class)
    public void test06Constructor() {
        Vana vana = new Vana("Ravak", new Rozmer(2.5, 1.2, 0.5));
        KoupelnaMelce koupelna = new KoupelnaMelce("", new Rozmer(3, 4, 3), vana);
        fail("Nevystavila se výjimka MojeException");
    }

    @Test
    public void test07GetNazev() {
        Vana vana = new Vana("Ravak", new Rozmer(2.5, 1.2, 0.5));
        KoupelnaMelce koupelna = new KoupelnaMelce("Koupelna", new Rozmer(3, 4, 3), vana);
        assertEquals("Koupelna", koupelna.getNazev());
    }

}
