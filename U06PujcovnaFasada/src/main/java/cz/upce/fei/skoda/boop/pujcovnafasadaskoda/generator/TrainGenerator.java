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
package cz.upce.fei.skoda.boop.pujcovnafasadaskoda.generator;

import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.data.Propulsion;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.data.Train;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class used for creating pseudo random trains
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class TrainGenerator implements IGenerator
{
    /**
     * Reference for random generator
     */
    private Random random;
    
    /**
     * Array of available manufacturers
     */
    private static final String[] MANUFACTURERS = {"Skoda Transportation", "Alstom", "Bombardier", "Stadler", "Pesa"};
    
    /**
     * Array of available types of propulsions
     */
    private static final String[] PROPULSIONS = {"DIESEL", "ELECTRIC"};
    
    /**
     * Minimal capacity of train
     */
    private static final int CAPACITY_MIN = 57;
    
    /**
     * Maximal capacity of train
     */
    private static final int CAPACITY_MAX = 279;
    
    /**
     * Minimal cars in train
     */
    private static final int CARS_MIN = 1;
    
    /**
     * Maximal cars in train
     */
    private static final int CARS_MAX = 11;
    
    /**
     * Format of train identifier
     */
    private static final String ID_FORMAT = "TRAIN_%04d";
    
    /**
     * Creates new generator of random trains
     */
    public TrainGenerator()
    {
        this.random = ThreadLocalRandom.current();
    }
    
    @Override
    public Train next()
    {
        String id = String.format(TrainGenerator.ID_FORMAT, this.random.nextInt(1000, 10000));
        int capacity = this.random.nextInt(TrainGenerator.CAPACITY_MIN, TrainGenerator.CAPACITY_MAX);
        String manufacturer = TrainGenerator.MANUFACTURERS[this.random.nextInt(0, TrainGenerator.MANUFACTURERS.length)];
        Propulsion propulsion = Propulsion.getPropulsion(TrainGenerator.PROPULSIONS[this.random.nextInt(0, TrainGenerator.PROPULSIONS.length)]);
        return new Train(id, manufacturer, capacity, propulsion, this.random.nextInt(TrainGenerator.CARS_MIN, TrainGenerator.CARS_MAX), this.random.nextBoolean());
    }
    
    @Override
    public Train createFromMap(Map<String, String> data)
    {
        String id = (
                data.get("id") == null?
                String.format(TrainGenerator.ID_FORMAT, this.random.nextInt(1000, 10000)):
                data.get("id")
                );
        int capacity = (
                data.get("capacity") == null?
                this.random.nextInt(TrainGenerator.CAPACITY_MIN, TrainGenerator.CAPACITY_MAX):
                Integer.parseInt(data.get("capacity"))
                );
        String manufacturer = (
                data.get("manufacturer") == null?
                TrainGenerator.MANUFACTURERS[this.random.nextInt(0, TrainGenerator.MANUFACTURERS.length)]:
                data.get("manufacturer")
                );
        Propulsion prop = (
                data.get("propulsion") == null?
                Propulsion.getPropulsion(TrainGenerator.PROPULSIONS[this.random.nextInt(0, TrainGenerator.PROPULSIONS.length)]):
                Propulsion.getPropulsion(data.get("propulsion"))
                );
        boolean etcs = (
                data.get("etcs") == null?
                this.random.nextBoolean():
                data.get("etcs").equals("true")
                );
        int cars = (
                data.get("cars") == null?
                this.random.nextInt(TrainGenerator.CARS_MIN, TrainGenerator.CARS_MAX):
                Integer.parseInt(data.get("cats"))
                );
        return new Train(id, manufacturer, capacity, prop, cars, etcs);
    }

    @Override
    public String getGeneratedName()
    {
       return Train.class.getCanonicalName();
    }
}
