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
package cz.upce.fei.skoda.boop.pujcovnacmdskoda.generator;

import cz.upce.fei.skoda.boop.pujcovnacmdskoda.data.Bus;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.data.Propulsion;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class used for creating pseudo random buses
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class BusGenerator implements IGenerator
{
    /**
     * Reference for random generator
     */
    private Random random;
    
    /**
     * Array of available manufacturers
     */
    private static final String[] MANUFACTURERS = {"Iveco Bus", "Mercedes-Benz", "Setra", "VDL", "MAN", "Neoplan", "Sor", "Solaris"};
    
    /**
     * Array of available types of propulsion
     */
    private static final String[] PROPULSIONS = {"DIESEL", "ELECTRIC", "HYBRID", "CNG"};
    
    /**
     * Minimal capacity of bus
     */
    private static final int CAPACITY_MIN = 23;
    
    /**
     * Maximal capacity of bus
     */
    private static final int CAPACITY_MAX = 67;
    
    /**
     * Format of bus identifier
     */
    private static final String ID_FORMAT = "BUS_%04d";
    
    /**
     * Creates generator of random buses
     */
    public BusGenerator()
    {
        this.random = ThreadLocalRandom.current();
    }
    
    @Override
    public Bus next()
    {
        String id = String.format(BusGenerator.ID_FORMAT, this.random.nextInt(1000, 10000));
        int capacity = this.random.nextInt(BusGenerator.CAPACITY_MIN, BusGenerator.CAPACITY_MAX);
        String manufacturer = BusGenerator.MANUFACTURERS[this.random.nextInt(0, BusGenerator.MANUFACTURERS.length)];
        Propulsion propulsion = Propulsion.getPropulsion(BusGenerator.PROPULSIONS[this.random.nextInt(0, BusGenerator.PROPULSIONS.length)]);
        return new Bus(id, manufacturer, capacity, propulsion, this.random.nextBoolean());
    }
    
    @Override
    public Bus createFromMap(Map<String, String> data)
    {
        String id = (
                data.get("id") == null?
                String.format(BusGenerator.ID_FORMAT, this.random.nextInt(1000, 10000)):
                data.get("id")
                );
        int capacity = (
                data.get("capacity") == null?
                this.random.nextInt(BusGenerator.CAPACITY_MIN, BusGenerator.CAPACITY_MAX):
                Integer.parseInt(data.get("capacity"))
                );
        String manufacturer = (
                data.get("manufacturer") == null?
                BusGenerator.MANUFACTURERS[this.random.nextInt(0, BusGenerator.MANUFACTURERS.length)]:
                data.get("manufacturer")
                );
        Propulsion prop = (
                data.get("propulsion") == null?
                Propulsion.getPropulsion(BusGenerator.PROPULSIONS[this.random.nextInt(0, BusGenerator.PROPULSIONS.length)]):
                Propulsion.getPropulsion(data.get("propulsion"))
                );
        boolean lF = (
                data.get("lowFloor") == null?
                this.random.nextBoolean():
                data.get("lowFloor").equals("true")
                );
        return new Bus(id, manufacturer, capacity, prop, lF);
    }

    @Override
    public String getGeneratedName()
    {
        return Bus.class.getCanonicalName();
    }
    
}
