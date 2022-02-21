package cz.upce.fei.boop.nemennatrida;

import java.util.Locale;
//<editor-fold defaultstate="collapsed" desc="comment">

/**
 * Třída {@code Rozmer} je určena pro vytváření objektů se třemi hodnotami,
 * které představují tři dimenze reálných trojrozměrných předmětů nebo prostorů.
 * <p>
 * Následující text obsahuje kromě popisu třídy {@code Rozmer} i trochu teorie
 * OOP. Úkolem studentů bude podle popisu v tomto dokumentačním komentáři a
 * testu třídy {@code RozmerTest} doplnit chybějící zdrojový kód třídy.
 * <p>
 * Třída {@code Rozmer} je ukázkou třídy podle které vytváří neměnné objekty
 * (Immutable object). U neměnných objektů po vytvoření již nemůže být
 * modifikován jejich stav a to až do konce jejich životů. Takový objekt může
 * být používán souběžně z více míst (tj. z různých vláken), aniž by hrozily
 * nekonzistence, tj. narušení stavu objektu a to tak, že z jednoho místa
 * změníme částečně stav a z druhého místa ho přepíšeme. Toto může nastávat ve
 * více vláknových aplikací. Použitím neměnných objektů vytváříme robustnější a
 * bezpečnější kód, aniž bychom k tomu potřebovali speciální nástroje.
 * <p>
 * Nejznámější neměnnou třídou v Javě je {@code String}, jejichž instance nelze
 * modifikovat. Například {@code String s = "Hello " + "World"} vytvoří tři
 * objekty {@code "Hello", "World" a "Hello World"}. Proto při sestavování
 * složitějších textů použijeme instance třídy {@code StringBuilder}, které jsou
 * modifikovatelné (mutable).
 * <p>
 * Co taková neměnná třída jako je {@code Rozmer} může obsahovat?
 *
 * <ol>
 * <li>Veřejné konstanty.<p>
 * V našem případě to budou dvě konstanty, které budou definovat hranice rozsahu
 * povolených hodnot dimenze třídy {@code Rozmer}. Toho mohou uživatelé třídy
 * využít k vlastnímu omezení používaných hodnot a tím předcházet zbytečnému
 * vystavování výjimek.</p>
 *
 * <li>Privátní konstanty.<p>
 * Slouží k různým účelům v implementaci třídy. Vetšinou jsou to neměnné (final)
 * proměnné s definovanou hodnotou, které většinou omezují používání číselných
 * literálů. Číselným literátům, které jsou použity v kódu se říká magická
 * čísla. Proč magická? To proto, že konkrétní číslo, např. 10, může na jednom
 * místě kódu představovat dimenzi a v jiném místě kódu počet sloupců
 * matice.</p>
 *
 * <li>Proměnné třídy.<p>
 * V této třídě nejsou potřeba.<p>
 * U instančních tříd se používají výjimečně. Například, když chceme počítat
 * kolik jsme vytvořili instancí nebo, když chceme přidělit každé instanci
 * nějakou jednoznačnou hodnotu.<p>
 * U tříd, které tvoří pouze statické metody, tj. metody třídy, se samozřejmě
 * proměnné třídy běžně používají.</p>
 *
 * <li>Instanční proměnné, neboli atributy.<p>
 * Instanční proměnné většinou deklarujeme na jednom místě před konstruktory a
 * metodami. Jejich obsah nebo jen některých z nich představuje stav objektu.
 * Instanční proměnné opatřujeme vždy modifikátorem {@code private}. Tím
 * skrýváme zásadním způsobem implementaci. Výhodu této zásady si ukážeme právě
 * na této třídě.<p>
 * V našem případě budou všechny tři instanční proměnné typu {@code long} a
 * budou představovat hodnoty v centimetrech. Zatímco přístupové metody k těmto
 * instančním proměnným budou vracet hodnoty v metrech a typově {@code double}.
 * To samé, ale obráceně, bude platit pro parametry konstruktoru, které budou v
 * metrech a v typu {@code double}. Proto musí docházet jak v konstruktoru, tak
 * v přístupových metodách k převodu hodnot.<p>
 * Protože tuto třídu navrhujeme pro vytváření neměnných objektů, jsou hodnoty
 * instančních proměnných definovány až v konstruktoru podle jeho parametrů a
 * poté je nelze změnit. Neměnnost instančních proměnných se zajistí
 * modifikátorem {@code final}.
 * </p>
 *
 * <li>Konstruktory.<p>
 * Konstruktor u neměnných objektů musí inicializovat všechny instanční
 * proměnné. V našem případě ještě konstruktor provede přepočet vstupních
 * parametrů na vnitřní rozměr tj. z metrů na centimetry.</p>
 *
 * <li>Přístupové metody<p>
 * Tyto metody přistupují k soukromým instančním proměnným a převádějí jejich
 * hodnoty z vnitřního typu na výstupní typ. V našem případě přepočítávají
 * hodnotu z centimetrů na metry a mění datový typ z {@code long} na
 * {@code double}
 * .</p>
 *
 * <li>Sestavení informace o stavu objektu<p>
 * Někdy je zapotřebí ziskat popis toho co je to za objekt a v jakém stavu se
 * nachází. Proto v Javě už ve třídě {@code Object} je deklarována metoda
 * {@code toString()} a tím pádem tuto metodu má kterákoliv další třída. Tato
 * metoda sestavuje textový řetězec, kde je uvedeno pouze název třídy z které
 * byl objekt vytvořen a referenci, což je 32 bitové číslo, které jednoznačně
 * identifikuje daný objekt. Tuto metodu mohou ostatní potomci překrýt svojí
 * verzí. Tyto nové verze metod {@code toString()} mohou přistupovat ke členům
 * své třídy (atributům a metodám) a sestavovat z nich svůj popis stavu objektu.
 * Stav předka lze sestavit a přidat k popisu stavu potomka pomocí {@code super}
 * a volání metody {@code toString()}
 * </p>
 *
 * <li>Metody {@code equals()} a {@code hashCode()}
 * <p>
 * Tyto dvě metody jsou též jako metoda {@code toString()} deklarovány ve třídě
 * {@code Object}. Pokud ve své třídě překryjeme jednu z nich, tak musíme
 * překrýt i druhou. To vyžaduje kontrakt.<p>
 * K čemu se používají?
 * <p>
 * Budeme se věnovat pouze metodě {@code equals(Object obj)}, která slouží k
 * porovnání dvou objektů.<p>
 * Pokud tuto metodu nepřekryjeme, tak dojde k porovnání referencí objektů.
 * Tzn., že se porovnají číselné hodnoty referencí. Co to znamená a kdy může
 * nastat shoda? Shoda může nastat jen tehdy a pouze tehdy, když {@code this} a
 * parameter metody {@code obj} obsahují stejná čísla, tedy reference. Když tomu
 * tak je, tak jsme zjitili, že se jedná o ten samý objekt.<p>
 * Pokud ovšem v naší třídě metodu {@code equals()} překryjeme, tak můžeme
 * porovnat dva objekty odkazované {@code this} a parametrem {@code obj} podle
 * hodnot v jejich atributech.
 *
 *
 * <li>Ostatní veřejné metody
 *
 * <li>Privátní metody
 *
 * </ol>
 *
 * @author karel@simerda.cz
 */
//</editor-fold>
public class Rozmer
{

//<editor-fold defaultstate="collapsed" desc="Veřejné konstanty">
    /**
     * Maximální hodnota dílčího rozměru v metrech
     */
    public static final double DIMENZE_MAX = 100.0;
    /**
     * Minimální hodnota dílčího rozměru v metrech
     */
    public static final double DIMENZE_MIN = .1;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Privátní konstanty ">
    //Konstanta na přepočítání metrů na centimetry
    private static final double TO_CM = 100;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Instanční proměnné/Atributy">
    // Stav objektu-u neměných objektu použít final
    /**
     * Délka rozměru jako celé číslo v centimetrech
     */
      private final long delka;
    /**
     * Šířka rozměru jako celé číslo v centimetrech
     */
      private final long sirka;
    /**
     * Výška rozměru jako celé číslo v centimetrech
     */
      private final long vyska;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Konstruktory">
    /**
     * Konstruktor třídy zajistí definici (inicializaci) hodnot privátních
     * instančních proměnných
     *
     * @param delka rozměr délka v m
     * @param sirka rozměr šířka v m
     * @param vyska rozměr výška v m
     *
     * @throws MojeException se vystaví, když alespoň hodnota jednoho vstupního
     * parametruje mimo povolený rozsah
     */
    public Rozmer(final double delka, final double sirka, final double vyska) {
       if(Rozmer.check(delka) && Rozmer.check(sirka) && Rozmer.check(vyska))
       {
           this.delka = Math.round(delka * (double)Rozmer.TO_CM);
           this.sirka = Math.round(sirka * (double)Rozmer.TO_CM);
           this.vyska = Math.round(vyska * (double)Rozmer.TO_CM);
       }
       else
       {
           throw new MojeException("Rozmer je mimo povoleny rozsah.");
       }
       
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Přístupové metody ke stavu">
    /**
     * @return hodnota délky rozměru v m
     */
    public double getDelka()
    {
        return (double)((double)this.delka/(double)Rozmer.TO_CM);
    }

    /**
     * @return hodnota šířky rozměru v m
     */
    public double getSirka()
    {
        return (double)((double)this.sirka/(double)Rozmer.TO_CM);
    }

    /**
     * @return hodnota výšky rozměru v m
     */
    public double getVyska()
    {
        return (double)((double)this.vyska/(double)Rozmer.TO_CM);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Sestavení informace o stavu - toString">
    /**
     * Nápověda:
     * format(Locale.ENGLISH, "Rozmer{delka=%5.2f,sirka=%5.2f,vyska=%5.2f}", 
     * 
     * @return 
     */
    @Override
    public String toString()
    {
        return String.format(Locale.ENGLISH,
                "Rozmer{delka=%5.2f,sirka=%5.2f,vyska=%5.2f}",
                (float)((float)this.delka/(float)Rozmer.TO_CM),
                (float)((float)this.sirka/(float)Rozmer.TO_CM),
                (float)((float)this.vyska/(float)Rozmer.TO_CM));
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Metody equals a hashCode">

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 89 * hash + (int) (this.delka ^ (this.delka >>> 32));
        hash = 89 * hash + (int) (this.sirka ^ (this.sirka >>> 32));
        hash = 89 * hash + (int) (this.vyska ^ (this.vyska >>> 32));
        return hash;
    }
   

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rozmer other = (Rozmer) obj;
        if (this.delka != other.delka) {
            return false;
        }
        if (this.sirka != other.sirka) {
            return false;
        }
        if (this.vyska != other.vyska) {
            return false;
        }
        return true;
    }
   
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Ostatní veřejné metody">
    /**
     * @param dimenze hodnota dílčího rozměru ke kontrole v metrech
     * @return vraci true, kdyz je dimenze v povoleném rozsahu
     */
    public static boolean kontrolaDimenze(double dimenze)
    {
        return Rozmer.check(dimenze);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Privátní metody">
    private static boolean check(double dimenze)
    {
        return dimenze >= Rozmer.DIMENZE_MIN && dimenze <= Rozmer.DIMENZE_MAX;
    }
//</editor-fold>
}
