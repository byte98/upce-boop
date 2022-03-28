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
package cz.upce.fei.skoda.boop.pujcovnacmdskoda.data;

import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.Mappable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class abstracting all attributes and method common for all vehicles
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract class Vehicle implements Mappable
{   
    /**
     * Unique identifier used to serialization
     */
    private static final long serialVersionUID = 
            7988428449919238453L;
    
    /**
     * Identifier of vehicle in system
     */
    protected String id;
    
    /**
     * Capacity of vehicle
     */
    protected int capacity;
    
    /**
     * Propulsion type of vehicle
     */
    protected Propulsion propulsion;
    
    /**
     * Manufacturer of vehicle
     */
    protected String manufacturer;
    
    /**
     * Creates new vehicle from data stored in map data structure
     * @see Mappable @link Mappable.java:30
     * @param data Map with data about vehicle
     */
    public Vehicle(Map<String, String> data)
    {
        this.id = data.get("id");
        this.capacity = Integer.parseInt(data.get("capacity"));
        this.propulsion = Propulsion.getPropulsion(data.get("propulsion").toUpperCase());
        this.manufacturer = data.get("manufacturer");
    }
    
    /**
     * Creates new vehicle
     * @param id Identifier of vehicle in system
     * @param manufacturer Manufacturer of vehicle
     * @param capacity Capacity of vehicle
     * @param propulsion Propulsion type of vehicle
     */
    public Vehicle(String id, String manufacturer, int capacity, Propulsion propulsion)
    {
        this.id = id;
        this.manufacturer = manufacturer;
        this.capacity = capacity;
        this.propulsion = propulsion;
    }
    
    @Override
    public Map<String, String> toMap()
    {
        Map<String, String> reti = new HashMap<>();
        reti.put("id", this.id);
        reti.put("capacity", Integer.toString(this.capacity));
        reti.put("propulsion", propulsion.name());
        reti.put("manufacturer", this.manufacturer);
        return reti;
    }
    
    @Override
    public String toString()
    {
        return String.format("id: %s, kapacita: %d, pohon: %s, vyrobce: %s",
                            this.id,
                            this.capacity,
                            this.propulsion,
                            this.manufacturer
                            );
    }
    
    @Override
    public void setData(Map<String, String> data)
    {
        this.id = data.get("id") == null ?
                this.id : data.get("id");
        this.capacity = data.get("capacity") == null ?
                this.capacity : Integer.parseInt(data.get("capacity"));
        this.propulsion = data.get("propulsion") == null ?
                this.propulsion : Propulsion.getPropulsion(data.get("propulsion").toUpperCase());
        this.manufacturer = data.get("manufacturer") == null ?
                this.manufacturer : data.get("manufacturer");
    }
}
