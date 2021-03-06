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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class SeznamObj implements Seznam
{
    /**
     * Pole s daty
     */
    private Object[] objekty;
    
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
    
    public SeznamObj(int kapacita)
    {
        if (kapacita < 1)
        {
            throw new IllegalArgumentException("Neplatna kapacita seznamu.");
        }
        else
        {
            this.pocet = 0;
            this.objekty = new Object[kapacita];
        }
        
    }
    
    /**
     * Zkontroluje, zda je potreba seznam zkratit a pripadne ho i zkrati
     */
    private void zkrat()
    {
        if ((int)Math.floor((double)this.objekty.length * this.KONTROLA_ZKRACENI) > this.pocet)
        {
            int novaKapacita = (int)Math.floor((double)this.objekty.length * this.KOEFICIENT_ZKRACENI);
            this.objekty = Arrays.copyOf(this.objekty, novaKapacita);
        }
    }
    
    /**
     * Zkontroluje, jestli je potreba pole rozsirit a pripadne ho i rozsiri
     */
    private void rozsir()
    {
        if (this.pocet + 1 > this.objekty.length)
        {
            int novaKapacita = (int)Math.floor((double)this.objekty.length * this.KOEFICIENT_ROZSIRENI);
            this.objekty = Arrays.copyOf(this.objekty, novaKapacita);
        }
    }
    
    @Override
    public Object dej(final int pozice) throws IndexOutOfBoundsException, IllegalAccessError
    {
        Object reti = null;
        if (this.pocet == 0)
        {
            throw new IllegalAccessError("Nepovolen?? operace, proto??e seznam je pr??zdn??.");
        }
        else if (pozice > this.pocet)
        {
            throw new IndexOutOfBoundsException("Parametr index je mimo povoleny rozsah.");
        }
        else
        {
            reti = this.objekty[pozice - 1];
        }
        return reti;
    }

    @Override
    public Object[] dejDopravniProstredky()
    {
        Object[] reti = new Object[this.pocet];
        for (int i = 0; i < this.pocet; i++)
        {
            reti[i] = this.objekty[i];
        }
        return reti;
    }

    @Override
    public Object[] dejKopieDopravniProstredku() throws CloneNotSupportedException
    {
        Object[] reti = new Object[this.pocet];
        for (int i = 0; i < this.pocet; i++)
        {
            if (this.objekty[i] instanceof Cloneable)
            {
                reti[i] = this.objekty[i];
                try
                {
                    Method method = this.objekty[i].getClass().getMethod("clone");
                    reti[i] = method.invoke(this.objekty[i]);
                }
                catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
                {
                    Logger.getLogger(SeznamObj.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                reti[i] = this.objekty[i];
            }
        }
        return reti;
    }

    @Override
    public Object odeber(int pozice) throws IndexOutOfBoundsException, IllegalAccessError
    {
        Object reti = null;
        if (this.pocet == 0)
        {
            throw new IllegalAccessError("Nepovolen?? operace, proto??e seznam je pr??zdn??.");
        }
        else if (pozice > this.pocet)
        {
            throw new IndexOutOfBoundsException("Parametr pozice je mimo povoleny rozsah.");
        }
        else
        {
            reti = this.dej(pozice);
            for (int i = pozice - 1; i < this.objekty.length - 1; i++)
            {
                this.objekty[i] = this.objekty[i + 1];
            }
            this.pocet--;
            this.zkrat();
        }
        return reti;
    }

    @Override
    public void vloz(Object objekt) throws IllegalArgumentException
    {
        if (objekt == null)
        {
            throw new IllegalArgumentException("Neplatna hodnota argumentu prostredek.");
        }
        else
        {
            this.rozsir();
            this.objekty[this.pocet] = objekt;
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
        return this.objekty.length;
    }

    @Override
    public void zrus()
    {
        for(int i = 0; i < this.objekty.length; i++)
        {
            this.objekty[i] = null;
        }
        this.objekty = new Object[1];
        this.pocet = 0;
    }

    @Override
    public Iterator<Object> iterator() {
        Iterator<Object> reti = new Iterator<Object>(){
            private int currentIndex = 0;
            @Override
            public boolean hasNext()
            {
                return (currentIndex < objekty.length && objekty[currentIndex] != null);
            }
            @Override
            public Object next()
            {
                Object reti = null;
                if (this.hasNext())
                {
                    reti = objekty[currentIndex];
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
