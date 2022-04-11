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

import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.Mappable;
import java.util.Map;

/**
 * Class for just representing some kind of data (here it is bus)
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Bus extends Vehicle
{
    /**
     * Flag, whether bus has low floor or not
     */
    private boolean lowFloor;
    
    /**
     * Unique identifier used to serialization
     */
    private static final long serialVersionUID = 
            5964763592193665125L;
    
    /**
     * Creates new bus from data stored in map data structure
     * @see Mappable @link Mappable.java:30
     * @param data Map with data about bus
     */
    public Bus(Map<String, String> data)
    {
        super(data);
        this.lowFloor = data.get("lowFloor").equals("true");
    }
    
    /**
     * Creates new instance of bus
     * @param id Identifier of bus in system
     * @param manufacturer Manufacturer of bus
     * @param capacity Capacity of bus
     * @param propulsion Propulsion type of vehicle
     */
    public Bus(String id, String manufacturer, int capacity, Propulsion propulsion)
    {
        super(id, manufacturer, capacity, propulsion);
        this.lowFloor = false;
    }
    
    
    /**
     * Creates new instance of bus
     * @param id Identifier of bus in system
     * @param manufacturer Manufacturer of bus
     * @param capacity Capacity of bus
     * @param propulsion Propulsion type of vehicle
     * @param lowFloor Flag, whether bus is low floor or not
     */
    public Bus(String id, String manufacturer, int capacity, Propulsion propulsion, boolean lowFloor)
    {
        this(id, manufacturer, capacity, propulsion);
        this.lowFloor = lowFloor;
    }

    @Override
    public Map<String, String> toMap()
    {
        Map<String, String> reti = super.toMap();
        reti.put("lowFloor", (this.lowFloor ? "true": "false"));
        return reti;
    }
    
    @Override
    public String getClassName()
    {
        return Bus.class.getCanonicalName();
    }
    
    @Override
    public String toString()
    {
        return String.format("Autobus [%s, <%s>]", super.toString(),
                this.lowFloor ? "nizkopodlazni" : "vysokopodlazni"
                );
    }
   
    @Override
    public void setData(Map<String, String> data)
    {
        super.setData(data);
        this.lowFloor = data.get("lowFloor") == null ?
                this.lowFloor : data.get("lowFloor").equals("true");
    }
}
