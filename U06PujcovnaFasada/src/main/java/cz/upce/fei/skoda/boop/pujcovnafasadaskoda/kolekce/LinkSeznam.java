//<editor-fold defaultstate="collapsed" desc="Licence">
/*
 * Copyright (C) 2022 Jiri Skoda <jiri.skoda@student.upce.cz>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
//</editor-fold>
package cz.upce.fei.skoda.boop.pujcovnafasadaskoda.kolekce;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementace rozhrani SpojovySeznam
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class LinkSeznam<E> implements SpojovySeznam<E>
{
    //<editor-fold defaultstate="collapsed" desc="Implementace prvku seznamu">
    /**
     * Trida, ktera reprezentuje jeden prvek spojoveho seznamu
     * @param <E> Datovy typ dat, ktera budou ulozena v seznamu
     */
    private class Prvek<E>
    {
        /**
         * Data, ktera budou ulozena v seznamu
         */
        private E data;
        
        /**
         * Reference na dalsi prvek v seznamu
         */
        private Prvek<E> dalsi;
        
        /**
         * Vytvoreni noveho prvku v seznamu
         */
        public Prvek()
        {
            this.dalsi = null;
            this.data = null;
        }
        
        /**
         * Vytvoreni noveho prvku v seznamu
         * @param data Data, ktera budou ulozena v seznamu
         */
        public Prvek(E data)
        {
            this();
            this.data = data;
        }
        
        // Gettery a settery
        public void setDalsi(Prvek dalsi)
        {
            this.dalsi = dalsi;
        }
        
        public Prvek<E> getDalsi()
        {
            return this.dalsi;
        }
        
        
        public E getData()
        {
            return this.data;
        }
    }
    //</editor-fold>
    
    /**
     * Reference na pocatek seznamu
     */
    private Prvek<E> prvni;
    
    /**
     * Reference na aktualni prvek seznamu
     */
    private Prvek<E> aktualni;
    
    /**
     * Reference na posledni prvek seznamu
     */
    private Prvek<E> posledni;
    
    /**
     * Velikost(= pocet prvku) seznamu
     */
    private int velikost;
    
    public LinkSeznam()
    {
        this.prvni = null;
        this.aktualni = null;
        this.velikost = 0;
    }
    
    /**
     * Metoda, ktera zkontroluje, zdali je zadany prvek v seznamu
     * @param prvek Prvek, ktery bude zkontrolovan
     * @return TRUE, pokud je prvek v seznamu, jinak FALSE
     */
    private boolean jeVSeznamu(Prvek prvek)
    {
        boolean reti = false;
        if (this.jePrazdny() == false)
        {
            Prvek k = this.prvni;
            while (k.equals(prvek) == false && k.getDalsi() != null)
            {
                k = k.getDalsi();
            }
            reti = k.equals(prvek);
        }
        return reti;
    }
    

    /**
     * Metoda, ktera ze seznamu vybere predka daneho prvku
     * @param prvek Prvek, jehoz predek bude vracen
     * @return Predka prvku nebo NULL, kdyz zadny takovy prvek neexistuje
     */
    private Prvek<E> getPredchozi(Prvek prvek)
    {
        Prvek<E> reti = null;
        if (
                this.jePrazdny() == false && 
                prvek != null &&
                this.prvni.equals(prvek) == false &&
                this.size() > 1 &&
                this.jeVSeznamu(prvek))
        {
            reti = this.prvni;
            Prvek<E> kontrola = this.prvni.getDalsi();
            while(kontrola.equals(prvek) == false &&
                    kontrola.getDalsi() != null)
            {
                reti = reti.getDalsi();
                kontrola = kontrola.getDalsi();
            }
        }
        return reti;
    }
    
    /**
     * Metoda, ktera odstrani prvek ze seznamu
     * @param p Prvek seznamu, ktery bude odstranen
     * @return TRUE, pokud byl prvek uspesne odstranen ze seznamu, jinak FALSE
     */
    private boolean odstranPrvek(Prvek p)
    {
        boolean reti = false;
        if (this.jePrazdny() == false && this.jeVSeznamu(p))
        {
            reti = true;
            if (this.size() == 1)
            {
                this.prvni = null;
                this.posledni = null;
            }
            else if (this.prvni.equals(p))
            {
                this.prvni = this.prvni.getDalsi();
            }
            else if (this.posledni.equals(p))
            {
                Prvek novyPosledni = this.getPredchozi(this.posledni);
                if (novyPosledni != null)
                {
                    this.posledni = novyPosledni;
                    this.posledni.setDalsi(null);
                }                
            }
            else
            {
                Prvek<E> predchudce = this.getPredchozi(p);
                predchudce.setDalsi(p.getDalsi());
            }     
            if (this.aktualni != null && this.aktualni.equals(p))
            {
                this.aktualni = null;
            }
            p = null;
            this.velikost--;
        }
        return reti;
    }
    
    //<editor-fold defaultstate="collapsed"  desc="Implementace rozhrani SpojovySeznam">
    @Override
    public void nastavPrvni() throws KolekceException
    {
        if (this.jePrazdny())
        {
            throw new KolekceException();
        }
        else
        {
            this.aktualni = this.prvni;
        }
    }
    
    @Override
    public void nastavPosledni() throws KolekceException
    {
        if (this.jePrazdny())
        {
            throw new KolekceException();
        }
        else
        {
            this.aktualni = this.posledni;
        }
    }
    
    @Override
    public void dalsi() throws KolekceException
    {
        if (this.jePrazdny() ||
                this.aktualni == null ||
                this.aktualni.getDalsi() == null)
        {
            throw new KolekceException();
        }
        else
        {
            this.aktualni = this.aktualni.getDalsi();
        }
    }
    
    @Override
    public boolean jeDalsi()
    {
        return (this.jePrazdny() == false &&
                this.aktualni != null &&
                this.aktualni.getDalsi() != null);
    }
    
    @Override
    public void vlozPrvni(E data)
    {
        if (data == null)
        {
            throw new NullPointerException("Argument je NULL!");
        }
        else
        {
            Prvek<E> p = new Prvek<>(data);
            p.setDalsi(this.prvni);
            this.prvni = p;
            this.velikost++;
            if (this.size() == 1)
            {
                this.posledni = p;
            }
        }
    }
    
    @Override
    public void vlozPosledni(E data)
    {
        if (data == null)
        {
            throw new NullPointerException("Argument je NULL!");
        }
        else
        {
            Prvek<E> p = new Prvek<>(data);
            if (this.jePrazdny())
            {
                this.prvni = p;
            }
            else
            {
                this.posledni.setDalsi(p);
            }
            this.posledni = p;
            this.velikost++;
            
        }
    }
    
    @Override
    public void vlozZaAktualni(E data) throws KolekceException
    {
        if (data == null)
        {
            throw new NullPointerException("Argument je NULL!");
        }
        else if (this.aktualni == null)
        {
            throw new KolekceException();
        }
        else
        {
            Prvek<E> p = new Prvek<>(data);
            p.setDalsi(this.aktualni.getDalsi());
            if (this.aktualni.equals(this.posledni))
            {
                this.posledni = p;
            }
            this.aktualni.setDalsi(p);
            this.velikost++;
        }
    }
    
    @Override
    public void vlozPredAktualnim(E data) throws KolekceException
    {
        if (data == null)
        {
            throw new NullPointerException("Argument je NULL!");
        }
        else if (this.aktualni == null)
        {
            throw new KolekceException();
        }
        else
        {
            Prvek<E> p = new Prvek<>(data);
            p.setDalsi(this.aktualni);
            if (this.aktualni.equals(this.prvni))
            {
                this.prvni = p;
            }
            else
            {
                this.getPredchozi(this.aktualni).setDalsi(p);
            }
        }
    }
    
    @Override
    public boolean jePrazdny()
    {
        return this.velikost == 0 || this.prvni == null;
    }
    
    @Override
    public E dejPrvni() throws KolekceException
    {
        E reti = null;
        if (this.jePrazdny())
        {
            throw new KolekceException();
        }
        else
        {
            reti = this.prvni.getData();
        }
        return reti;
    }
    
    @Override
    public E dejPosledni() throws KolekceException
    {
        E reti = null;
        if (this.jePrazdny())
        {
            throw new KolekceException();
        }
        else
        {
            reti = this.posledni.getData();
        }
        return reti;
    }
    
    @Override
    public E dejAktualni() throws KolekceException
    {
        E reti = null;
        if (this.jePrazdny() || this.aktualni == null)
        {
            throw new KolekceException();
        }
        else
        {
            reti = this.aktualni.getData();
        }
        return reti;
    }
    
    @Override
    public E dejZaAktualnim() throws KolekceException
    {
        E reti = null;
        if (this.jePrazdny() || this.aktualni == null || this.aktualni.getDalsi() == null)
        {
            throw new KolekceException();
        }
        else
        {
            reti = this.aktualni.getDalsi().getData();
        }
        return reti;
    }
    
    @Override
    public E dejPredAktualnim() throws KolekceException
    {
        E reti = null;
        if (this.jePrazdny() || this.aktualni == null)
        {
            throw new KolekceException();
        }
        else if (this.getPredchozi(this.aktualni) != null)
        {
            reti = this.getPredchozi(this.aktualni).getData();
        }
        return reti;
    }
    
    @Override
    public E odeberPrvni() throws KolekceException
    {
        E reti = null;
        if (this.jePrazdny())
        {
            throw new KolekceException();
        }
        else
        {
            reti = this.prvni.getData();
            this.odstranPrvek(this.prvni);
        }
        return reti;
    }
    
    @Override
    public E odeberPosledni() throws KolekceException
    {
        E reti = null;
        if (this.jePrazdny())
        {
            throw new KolekceException();
        }
        else
        {
            reti = this.posledni.getData();
            this.odstranPrvek(this.posledni);
        }
        return reti;
    }
    
    @Override
    public E odeberAktualni() throws KolekceException
    {
        E reti = null;
        if (this.jePrazdny() || this.aktualni == null)
        {
            throw new KolekceException();
        }
        else
        {
            reti = this.aktualni.getData();
            this.odstranPrvek(this.aktualni);
            this.aktualni = null;
        }
        return reti;
    }
    
    @Override
    public E odeberZaAktualnim() throws KolekceException
    {
        E reti = null;
        if (this.jePrazdny() || this.aktualni == null || this.aktualni.getDalsi() == null)
        {
            throw new KolekceException();
        }
        else
        {
            reti = this.aktualni.getDalsi().getData();
            this.odstranPrvek(this.aktualni.getDalsi());
        }
        return reti;
    }
    
    @Override
    public E odeberPredAktualnim() throws KolekceException
    {
        E reti = null;
        if (this.jePrazdny() || this.aktualni == null)
        {
            throw new KolekceException();
        }
        else if (this.getPredchozi(this.aktualni) != null)
        {
            reti = this.getPredchozi(this.aktualni).getData();
            this.odstranPrvek(this.getPredchozi(this.aktualni));
        }
        return reti;
    }
    
    @Override
    public int size()
    {
        return this.velikost;
    }

    @Override
    public void zrus()
    {
        while (this.jePrazdny() == false)
        {
            this.odstranPrvek(this.prvni);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Implamentace rozhrani Iterable">
    @Override
    public Iterator<E> iterator()
    {
        Iterator<E> reti = new Iterator<E>(){
            private Prvek<E> actual = prvni;
            @Override
            public boolean hasNext()
            {
                return actual != null;
            }
            @Override
            public E next()
            {
                E reti = null;
                if (actual == null)
                {
                   throw new NoSuchElementException();
                }
                else
                {
                   reti = actual.getData();
                   this.actual = this.actual.getDalsi();   
                }
                return reti;
            }
        };
        return reti;
    }
    //</editor-fold>
}