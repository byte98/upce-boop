package seznam;

import seznam.prostredky.Prostredek;

/**
 *
 * @author karel@simerda.cz
 */
public interface Seznam extends Iterable<Prostredek> {

    /**
     * Metoda vrátí v návratové hodnotě odkaz na dopravní prostředek, který je
     * adresován hodnotou v parametru.
     *
     * @param pozice v parametru je hodnota pozice prvku v seznamu, první prvek
     * pole je adresován číslem 1.
     *
     * @return vrací odkaz na dopravní prostředek podle hodnoty v parametru
     * pozice
     *
     * @throws IndexOutOfBoundsException tato výjimka se vystaví, když hodnota
     * parametru pozice je mimo povolený rozsah, který je dán naplněností
     * seznamu dopravních prostředků. Výjimka dodá tento text¨: "Parametr index
     * je mimo povoleny rozsah."
     *
     * @throws IllegalAccessError tato výjimka se vystaví, když je seznam
     * prázdný. Výjimka dodá tento text: "Nepovolená operace, protože seznam je
     * prázdný."
     *
     * Poznámka k implementaci: Při kontrole dvou parametrů může být vystavování
     * výjimek závislé na pořadí kontrol. Zde se může docházet, že výjímky budou
     * závislé na tom, zda je seznam prázdný nebo ne. Proto kombinujte
     * vystavování výjimek tak, abyste vyhověli testům. Přednost má kontrola,
     * zda seznam prázdný nebo ne a potom teprve kontrola, zda je pozice v
     * povoleném rozsahu.
     */
    Prostredek dej(final int pozice) throws IndexOutOfBoundsException, IllegalAccessError;

    /**
     * Metoda vrací nové pole s aktuálním seznamem dopravních prostředků
     *
     * @return metod vrací odkaz na pole s odkazy na dopravní prostředky seznamu
     */
    Prostredek[] dejDopravniProstredky();

    /**
     * Metoda vrací nové pole s klony dopravních prostředků v seznamu
     *
     * @return metod vrací odkaz na pole s odkazy na klony dopravních prostředků
     */
    Prostredek[] dejKopieDopravniProstredku() throws CloneNotSupportedException;

    /**
     * Metoda odebere odkaz na dopravní prostředek ze seznamu, který je
     * adresován hodnotou v parametru pozice
     *
     * Metoda mění aktuální počet dopravních prostředků.
     *
     * @param pozice v parametru je hodnota pozice prvku v seznamu, první prvek
     * pole je adresován číslem 1.
     *
     * Metoda zkracuje vnitřní pole na polovinu, když počet prvků v poli klesne
     * pod jednu čtvrtinu délky pole.     *
     *
     * @return vrací odkaz na prostředek podle hodnoty v parametru pozice
     *
     * @throws IndexOutOfBoundsException tato výjimka se vystaví, když hodnota
     * parametru pozice je mimo povolený rozsah, který je dán naplněností
     * seznamu dopravních prostředků. Výjimka dodá tento text¨: "Parametr pozice
     * je mimo povoleny rozsah."
     *
     * @throws IllegalAccessError tato výjimka se vystaví, když je seznam
     * prázdný. Výjimka dodá tento text: "Nepovolená operace, protože seznam je
     * prázdný."
     *
     * Poznámka k implementaci: O řazení výjimek, platí to samé jako u metody
     * dej.
     */
    Prostredek odeber(final int pozice)
            throws IndexOutOfBoundsException, IllegalAccessError;

    /**
     * Metoda vloží dopravní prostředek na konec seznamu
     *
     * Jestliže metoda zjistí před vložením odkazu do seznamu(pole), že se
     * vyčerpala kapacita vnitřního pole, tak toto pole zvětší na dvojnásobek a
     * překopíruje do něj všechny odkazy původního pole.
     *
     * Metoda nekontroluje, zda již byla stejná instance o seznamu vložena!
     *
     * Metoda mění aktuální počet dopravních prostředků v seznamu.
     *
     * Metoda nekontroluje, zda již byla seznamu vložena stejná instance!
     *
     * @param prostredek parametr s odkazem na vkládaný dopravní prostředek
     *
     *
     * @throws IllegalArgumentException tato výjimka se vystaví když, je v
     * parametru není odkaz na objekt, tj. parametr obsahuje null
     */
    void vloz(Prostredek prostredek) throws IllegalArgumentException;

    /**
     * Metoda vrací aktuální počet dopravních prostředů v seznamu
     *
     * @return hodnota s počtem dopravních prostředků v seznamu
     */
    int pocet();

    /**
     * Metoda vrací aktuální velikost vnitřního pole
     *
     * @return velikost vnitřního pole
     */
    int kapacita();

    /**
     * Metoda zruší všechny položky v seznamu.
     */
    void zrus();

    /**
     * Metoda vloží proměnlivý počet parametrů s dopravními prostředky do
     * seznamu.
     *
     * <p>
     * Rozšíření JAVA 8 v interfejsech
     * <p>
     * Od Javy 8 lze do rozhraní vkládat i speciální verze metod s tělem. V
     * tomto rozhraní je to uvedeno jako příklad. Omezení a teorie budou
     * vysvětleny v následujícím předměru IOOP.
     *
     * Tuto metodu budeme využívat testech, protože usnadňuje zápis testovacích
     * případů.
     * <p>
     * Proměnlivý počet parametru u metod
     * <P>
     *
     * Dále tato metoda používá zajímavou syntatickou vlastnost v deklaraci
     * parametrů metody a to tři tečky za typem parametru. Tuto konstrukci
     * můžeme použít pouze jako poslední v seznamu parametrů metody. Co to
     * umožňuje? Umožňuje to mít proměnlivý počet argumentů ve volání metody.
     * Omezení jsou dvě. Jedno už bylo zmíněno, že to musí být na konci seznamu
     * parametrů. Za druhé, že všechny argumenty ve volání metody musí být
     * stejného typu. Při vstupu do metody se na tento seznam pohlíží jako na
     * pole.
     *
     * @param prostredky seznam dopravních prostředků
     */
    default void vloz(Prostredek... prostredky) {
        for (Prostredek prostredek : prostredky) {
            vloz(prostredek);
        }
    }

}
