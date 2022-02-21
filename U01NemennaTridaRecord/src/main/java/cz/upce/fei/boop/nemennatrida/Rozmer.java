package cz.upce.fei.boop.nemennatrida;

import java.util.Locale;

public record Rozmer(double delka, double sirka, double vyska)
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
    public Rozmer
    {
       if(!(Rozmer.check(delka) && Rozmer.check(sirka) && Rozmer.check(vyska)))
       {
           throw new MojeException("Rozmer je mimo povoleny rozsah.");
       }
       else
       {
           delka = Math.round(delka * Rozmer.TO_CM);
           sirka = Math.round(sirka * Rozmer.TO_CM);
           vyska = Math.round(vyska * Rozmer.TO_CM);
       }
       
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Přístupové metody ke stavu">
    /**
     * @return hodnota délky rozměru v m
     */
    public double getDelka()
    {
        return this.delka() / Rozmer.TO_CM;
    }

    /**
     * @return hodnota šířky rozměru v m
     */
    public double getSirka()
    {
        return this.sirka() / Rozmer.TO_CM;
    }

    /**
     * @return hodnota výšky rozměru v m
     */
    public double getVyska()
    {
        return this.vyska() / Rozmer.TO_CM;
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
                this.delka / Rozmer.TO_CM,
                this.sirka / Rozmer.TO_CM,
                this.vyska / Rozmer.TO_CM);
    }
//</editor-fold>
    

//<editor-fold defaultstate="collapsed" desc="Ostatní veřejné metody">
    /**
     * @param dimenze hodnota dílčího rozměru ke kontrole v metrech
     * @return vraci true, kdyz je dimenze v povoleném rozsahu
     */
    public static boolean kontrolaDimenze(double dimenze)
    {
        return check(dimenze);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Privátní metody">
    private static boolean check(double dimenze)
    {
        return dimenze >= Rozmer.DIMENZE_MIN && dimenze <= Rozmer.DIMENZE_MAX;
    }
//</editor-fold>
}
