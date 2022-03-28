package seznam.prostredky;

import java.util.Locale;
import java.util.Objects;

/**
 * TODO Doplnit alespoň jednoho dalšího potomka podle vlastního výběru.
 *
 * @author karel@simerda.cz
 */
public abstract sealed class Prostredek implements Cloneable
        permits OsobniAutomobil, NakladniAutomobil, Traktor {

    protected final String SPZ;
    
    protected final double hmotnost;
    
    protected TypyDopravnichProstredku typ;
    
    protected Prostredek(String SPZ, double hmotnost)
    {
        if (SPZ.equals("") || hmotnost < 1)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            this.SPZ = SPZ;
            this.hmotnost = hmotnost;
        }
        
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
    
    public String getSpz()
    {
        return this.SPZ;
    }
    
    public double getHmotnost()
    {
        return this.hmotnost;
    }
    
    public TypyDopravnichProstredku getTyp()
    {
        return this.typ;
    }

    @Override
    public String toString()
    {
        StringBuilder reti = new StringBuilder();
        reti.append("typ=");
        reti.append(this.typ.nazev());
        reti.append(", SPZ=");
        reti.append(this.SPZ);
        reti.append(", hmotnost=");
        reti.append(String.format("%.2f", this.hmotnost).replace(",", "."));
        return reti.toString();
    }
   
    @Override
    public boolean equals(Object other)
    {
        boolean reti = false;
        if (other instanceof Prostredek)
        {
            Prostredek prostredek = (Prostredek)other;
            reti = (prostredek.SPZ.equals(this.SPZ) && prostredek.typ == this.typ);
        }
        return reti;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.SPZ);
        hash = 83 * hash + Objects.hashCode(this.typ);
        return hash;
    }

}
