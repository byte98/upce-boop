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

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public final class Traktor extends Prostredek {
    private final double tah;
    
    public Traktor(String SPZ, double hmotnost, double tah)
    {
        super(SPZ, hmotnost);
        if (tah < 1)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            this.tah = tah;
            this.typ = TypyDopravnichProstredku.TRAKTOR;
        }        
    }
    
    public double getTah()
    {
        return this.tah;
    }
    
    @Override
    public String toString()
    {
        StringBuilder reti = new StringBuilder(super.toString());
        reti.append(", tah=");
        reti.append(String.format("%.2f", this.tah).replace(",", "."));
        return reti.toString();
    }
}
