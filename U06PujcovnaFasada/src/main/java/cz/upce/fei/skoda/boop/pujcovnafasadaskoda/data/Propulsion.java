//<editor-fold defaultstate="collapsed" desc="License">
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
package cz.upce.fei.skoda.boop.pujcovnafasadaskoda.data;

/**
 * Enumeration of all available propulsion types
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public enum Propulsion
{    
    DIESEL("DIESEL"),
    ELECTRIC("ELECTRIC"),
    HYBRID("HYBRID"),
    CNG("CNG");
    
    private final String name;
    
    /**
     * Unique identifier used to serialization
     */
    private static final long serialVersionUID = 
            1464029727053807551L;
    
    private Propulsion(String name)
    {
        this.name = name;
    }
    
    public static Propulsion getPropulsion(String name)
    {
        Propulsion reti = null;
        switch(name.trim().toLowerCase())
        {
            case "diesel" -> reti = DIESEL;
            case "electric" -> reti = ELECTRIC;
            case "hybrid" -> reti = HYBRID;
            case "cng" -> reti = CNG;
        }
        return reti;
    }
    
    @Override
    public String toString()
    {
        String reti = "";
        switch(this.name().toLowerCase())
        {
            case "diesel" -> reti = "nafta";
            case "electric" -> reti = "elektrina";
            case "hybrid" -> reti = "hybridni";
            case "cng" -> reti = "CNG";
        }
            
        return reti;
    }
}
