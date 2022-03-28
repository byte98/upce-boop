package cz.upce.fei.boop.nemennatrida;

import java.util.Locale;

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
     * Délka rozměru jako reálné číslo v centimetrech
     */
      private final double delka;
    /**
     * Šířka rozměru jako reálné číslo v centimetrech
     */
      private final double sirka;
    /**
     * Výška rozměru jako reálné číslo v centimetrech
     */
      private final double vyska;
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
    public Rozmer(final double delka, final double sirka, final double vyska)
    {
       if(Rozmer.check(delka) && Rozmer.check(sirka) && Rozmer.check(vyska))
       {
           this.delka = Math.round(delka * Rozmer.TO_CM);
           this.sirka = Math.round(sirka * Rozmer.TO_CM);
           this.vyska = Math.round(vyska * Rozmer.TO_CM);
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
        return this.delka/Rozmer.TO_CM;
    }

    /**
     * @return hodnota šířky rozměru v m
     */
    public double getSirka() {
        return this.sirka/Rozmer.TO_CM;
    }

    /**
     * @return hodnota výšky rozměru v m
     */
    public double getVyska() {
        return this.vyska/Rozmer.TO_CM;
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
    public String toString() {
        return String.format(Locale.ENGLISH,
                "Rozmer{delka=%5.2f,sirka=%5.2f,vyska=%5.2f}",
                this.getDelka(),
                this.getSirka(),
                this.getVyska());
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Metody equals a hashCode">

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.delka) ^ (Double.doubleToLongBits(this.delka) >>> 32));
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.sirka) ^ (Double.doubleToLongBits(this.sirka) >>> 32));
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.vyska) ^ (Double.doubleToLongBits(this.vyska) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
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
        if (Double.doubleToLongBits(this.delka) != Double.doubleToLongBits(other.delka)) {
            return false;
        }
        if (Double.doubleToLongBits(this.sirka) != Double.doubleToLongBits(other.sirka)) {
            return false;
        }
        return Double.doubleToLongBits(this.vyska) == Double.doubleToLongBits(other.vyska);
    }

    

    
   
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Ostatní veřejné metody">
    /**
     * @param dimenze hodnota dílčího rozměru ke kontrole v metrech
     * @return vraci true, kdyz je dimenze v povoleném rozsahu
     */
    public static boolean kontrolaDimenze(double dimenze) {
        return check(dimenze);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Privátní metody">
    private static boolean check(double dimenze) {
        return dimenze >= Rozmer.DIMENZE_MIN && dimenze <= Rozmer.DIMENZE_MAX;
    }
//</editor-fold>
}
