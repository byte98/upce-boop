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
package cz.upce.fei.skoda.boop.pujcovnaguiskoda.data;

import cz.upce.fei.skoda.boop.pujcovnaguiskoda.perzistence.Mappable;
import java.util.Map;

/**
 * Class for just representing some kind of data (here it is train)
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Train extends Vehicle
{
    /**
     * Unique identifier used to serialization
     */
    private static final long serialVersionUID = 
            3042921776489589097L;
    
    /**
     * Number of cars of train
     */
    private int cars;
    
    /**
     * Flag, whether train has implemented European Train Control System
     */
    private boolean ETCS;
    
    
    /**
     * Creates new bus from data stored in map data structure
     * @see Mappable @link Mappable.java:30
     * @param data Map with data about bus
     */
    public Train(Map<String, String> data)
    {
        super(data);
        this.cars = Integer.parseInt(data.get("cars"));
        this.ETCS = data.get("etcs").equals("true");
    }
    
    /**
     * Creates new instance of bus
     * @param id Identifier of bus in system
     * @param manufacturer Manufacturer of bus
     * @param capacity Capacity of bus
     * @param propulsion Propulsion type of vehicle
     * @param cars Number of cars in train
     */
    public Train(String id, String manufacturer, int capacity, Propulsion propulsion, int cars)
    {
        super(id, manufacturer, capacity, propulsion);
        this.cars = cars;
        this.ETCS = false;
    }
    
    /**
     * Creates new instance of bus
     * @param id Identifier of bus in system
     * @param manufacturer Manufacturer of bus
     * @param capacity Capacity of bus
     * @param propulsion Propulsion type of vehicle
     * @param cars Number of cars in train
     * @param ETCS Flag, whether train has ETCS implemented
     */
    public Train(String id, String manufacturer, int capacity, Propulsion propulsion, int cars, boolean ETCS)
    {
        this(id, manufacturer, capacity, propulsion, cars);
        this.ETCS = true;
    }

    @Override
    public Map<String, String> toMap()
    {
        Map<String, String> reti = super.toMap();
        reti.put("cars", String.valueOf(this.cars));
        reti.put("etcs", this.ETCS ? "true" : "false");
        return reti;
    }
    
    @Override
    public String getClassName()
    {
        return Train.class.getCanonicalName();
    }
    
    @Override
    public String toString()
    {
        return String.format("Vlak [%s, pocet vozu: %d, ETCS: %s]", super.toString(),
                this.cars,
                this.ETCS ? "ano" : "ne"
                );
    }
    
    @Override
    public void setData(Map<String, String> data)
    {
        super.setData(data);
        this.cars = data.get("cars") == null ?
                this.cars : Integer.parseInt(data.get("cars"));
        this.ETCS = data.get("etcs") == null ?
                this.ETCS : data.get("etcs").equals("true");
    }
   
}
