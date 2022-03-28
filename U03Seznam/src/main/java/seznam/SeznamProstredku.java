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
package seznam;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import seznam.prostredky.Prostredek;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class SeznamProstredku implements Seznam
{
    /**
     * Pole s daty
     */
    private Prostredek[] prostredky;
    
    /**
     * Pocet prvku v poli
     */
    private int pocet;
    
    
    /**
     * Koeficient, kterym se bude nasobit kapacita pri rozsirovani seznamu
     */
    private final double KOEFICIENT_ROZSIRENI = 2;
    
    /**
     * Koeficient kapacity pri jejimz splneni bude seznam zkracen
     */
    private final double KONTROLA_ZKRACENI = 0.25;
    
    /**
     * Koeficient, kterym se bude nasobit kapacita pri zkracovani seznamu
     */
    private final double KOEFICIENT_ZKRACENI = 0.5;
    
    public SeznamProstredku(int capacity)
    {
        if (capacity < 1)
        {
            throw new IllegalArgumentException("Neplatna kapacita seznamu.");
        }
        else
        {
            this.pocet = 0;
            this.prostredky = new Prostredek[capacity];
        }
        
    }
    
    /**
     * Zkontroluje, zda je potreba seznam zkratit a pripadne ho i zkrati
     */
    private void zkrat()
    {
        if ((int)Math.floor((double)this.prostredky.length * this.KONTROLA_ZKRACENI) > this.pocet)
        {
            int novaKapacita = (int)Math.floor((double)this.prostredky.length * this.KOEFICIENT_ZKRACENI);
            this.prostredky = Arrays.copyOf(this.prostredky, novaKapacita);
        }
    }
    
    /**
     * Zkontroluje, jestli je potreba pole rozsirit a pripadne ho i rozsiri
     */
    private void rozsir()
    {
        if (this.pocet + 1 > this.prostredky.length)
        {
            int novaKapacita = (int)Math.floor((double)this.prostredky.length * this.KOEFICIENT_ROZSIRENI);
            this.prostredky = Arrays.copyOf(this.prostredky, novaKapacita);
        }
    }
    
    @Override
    public Prostredek dej(final int pozice) throws IndexOutOfBoundsException, IllegalAccessError
    {
        Prostredek reti = null;
        if (this.pocet == 0)
        {
            throw new IllegalAccessError("Nepovolená operace, protože seznam je prázdný.");
        }
        else if (pozice > this.pocet)
        {
            throw new IndexOutOfBoundsException("Parametr index je mimo povoleny rozsah.");
        }
        else
        {
            reti = this.prostredky[pozice - 1];
        }
        return reti;
    }

    @Override
    public Prostredek[] dejDopravniProstredky()
    {
        Prostredek[] reti = new Prostredek[this.pocet];
        for (int i = 0; i < this.pocet; i++)
        {
            reti[i] = this.prostredky[i];
        }
        return reti;
    }

    @Override
    public Prostredek[] dejKopieDopravniProstredku() throws CloneNotSupportedException
    {
        Prostredek[] reti = new Prostredek[this.pocet];
        for (int i = 0; i < this.pocet; i++)
        {
            reti[i] = (Prostredek)this.prostredky[i].clone();
        }
        return reti;
    }

    @Override
    public Prostredek odeber(int pozice) throws IndexOutOfBoundsException, IllegalAccessError
    {
        Prostredek reti = null;
        if (this.pocet == 0)
        {
            throw new IllegalAccessError("Nepovolená operace, protože seznam je prázdný.");
        }
        else if (pozice > this.pocet)
        {
            throw new IndexOutOfBoundsException("Parametr pozice je mimo povoleny rozsah.");
        }
        else
        {
            reti = this.dej(pozice);
            for (int i = pozice - 1; i < this.prostredky.length - 1; i++)
            {
                this.prostredky[i] = this.prostredky[i + 1];
            }
            this.pocet--;
            this.zkrat();
        }
        return reti;
    }

    @Override
    public void vloz(Prostredek prostredek) throws IllegalArgumentException
    {
        if (prostredek == null)
        {
            throw new IllegalArgumentException("Neplatna hodnota argumentu prostredek.");
        }
        else
        {
            this.rozsir();
            this.prostredky[this.pocet] = prostredek;
            this.pocet++;
        }
    }

    @Override
    public int pocet()
    {
        return this.pocet;
    }

    @Override
    public int kapacita()
    {
        return this.prostredky.length;
    }

    @Override
    public void zrus()
    {
        for(int i = 0; i < this.prostredky.length; i++)
        {
            this.prostredky[i] = null;
        }
        this.prostredky = new Prostredek[1];
        this.pocet = 0;
    }

    @Override
    public Iterator<Prostredek> iterator() {
        Iterator<Prostredek> reti = new Iterator<Prostredek>(){
            private int currentIndex = 0;
            @Override
            public boolean hasNext()
            {
                return (currentIndex < prostredky.length && prostredky[currentIndex] != null);
            }
            @Override
            public Prostredek next()
            {
                Prostredek reti = null;
                if (this.hasNext())
                {
                    reti = prostredky[currentIndex];
                    currentIndex++;
                }
                else
                {
                    throw new NoSuchElementException();
                }
                return reti;
            }
        };
        return reti;
    }
    
    
}
