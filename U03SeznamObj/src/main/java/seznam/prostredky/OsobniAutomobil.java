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
package seznam.prostredky;

import java.util.Objects;
import util.Barva;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public final class OsobniAutomobil extends Prostredek{
    
    private final Barva barva;
    
    private final int pocetSedadel;
    
    public OsobniAutomobil(String SPZ, Barva barva, int pocetSedadel, double hmotnost)
    {
        super(SPZ, hmotnost);
        Objects.requireNonNull(barva);
        if (pocetSedadel < 1)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            
            this.barva = barva;
            this.pocetSedadel = pocetSedadel;
            this.typ = TypyDopravnichProstredku.OSOBNI_AUTOMOBIL;
        }
        
    }
    
    public Barva getBarva()
    {
        return this.barva;
    }
    
    public int getPocetSedadel()
    {
        return this.pocetSedadel;
    }
    
    @Override
    public String toString()
    {
        StringBuilder reti = new StringBuilder(super.toString());
        reti.append(",  barva=");
        reti.append(this.barva.getNazev());
        reti.append(", početSedadel=");
        reti.append(this.pocetSedadel);
        return reti.toString();
    }
}
