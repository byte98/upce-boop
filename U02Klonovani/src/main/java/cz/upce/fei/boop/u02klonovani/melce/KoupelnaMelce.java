package cz.upce.fei.boop.u02klonovani.melce;

import cz.upce.fei.boop.u02klonovani.MojeException;
import cz.upce.fei.boop.u02klonovani.Rozmer;
import cz.upce.fei.boop.u02klonovani.Vana;
import java.util.Objects;

/**
 * TODO Upravte třídu KoupelnaMelce tak vyhověla testu a byla mělce klonovatelná
 * TODO Při upravách třídy dodržujte strukturu třídy podle editor-fold
 */
public class KoupelnaMelce implements Cloneable
{

//<editor-fold defaultstate="collapsed" desc="instanční proměnný/atributy">

    /**
     * Vana ktera se v koupelne nachazi
     */
    private Vana vana;
    
    /**
     * Nazev koupelny
     */
    private String nazev;
    
    /**
     * Rozmery koupelny
     */
    private Rozmer rozmer;

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="konstruktory">
    
    /**
     * Vytvori novou koupelnu
     * @param nazev Nazev nove vytvorene koupelny
     * @param rozmer Rozmery nove vytvorene koupelny
     * @param vana Vana, ktera bude v koupelne umistena
     */
    public KoupelnaMelce(String nazev, Rozmer rozmer, Vana vana)
    {
        if (nazev == null || rozmer == null || vana == null)
        {
            throw new NullPointerException();
        }
        else if (nazev.equals(""))
        {
            throw new MojeException("Neplatny nazev koupelny!");
        }
        else
        {
            this.nazev = nazev;
            this.rozmer = rozmer;
            this.vana = vana;
        }        
    }
    
    /**
     * Vytvori novou koupelnu
     * @param nazev Nazev koupelny
     * @param delka Delka koupelny v metrech
     * @param sirka Sirka koupelny v metrech
     * @param vyska Vyska koupelny v metrech
     * @param vana Vana ktera bude umistena v koupelne
     */
    public KoupelnaMelce(String nazev, double delka, double sirka, double vyska, Vana vana)
    {
        this(nazev, new Rozmer(delka, sirka, vyska), vana);
    }
    
//</editor-fold>    

//<editor-fold defaultstate="collapsed" desc="klonování">
    @Override
    public KoupelnaMelce clone() throws CloneNotSupportedException
    {
        return (KoupelnaMelce)super.clone();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="metoda toString">
    @Override
    public String toString()
    {
        return "KoupelnaMelce{" + "vana=" + this.vana + ", nazev=" + this.nazev + ", rozmer=" + this.rozmer + '}';
    }

//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Metody get/set ">
    public Vana getVana()
    {
        return this.vana;
    }

    public void setVana(Vana vana)
    {
        this.vana = vana;
    }

    public String getNazev()
    {
        return this.nazev;
    }

    public void setNazev(String nazev)
    {
        this.nazev = nazev;
    }

    public Rozmer getRozmer()
    {
        return this.rozmer;
    }

    public void setRozmer(Rozmer rozmer)
    {
        this.rozmer = rozmer;
    }
//</editor-fold>

    
}
