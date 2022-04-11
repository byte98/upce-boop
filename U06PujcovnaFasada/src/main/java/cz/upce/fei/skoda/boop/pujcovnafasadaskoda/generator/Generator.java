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

import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.Mappable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * Class used to generate all other elements
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Generator implements IGenerator
{
    /**
     * Array with all generators that can be used to generate elements
     */
    private static final IGenerator[] GENERATORS = {
        new BusGenerator(),
        new TrainGenerator()
    };
    
    /**
     * Reference for random generator
     */
    private Random random;
    
    /**
     * Creates generator of random elements
     */
    public Generator()
    {
        this.random = ThreadLocalRandom.current();
    }
    
    /**
     * Gets random generator from all available generators
     * @return Random generator
     */
    private IGenerator getRandomGenerator()
    {
        return Generator.GENERATORS[this.random.nextInt(0, Generator.GENERATORS.length)];
    }
    
    @Override
    public Mappable next()
    {
        return this.getRandomGenerator().next();
    }

    @Override
    public Mappable createFromMap(Map<String, String> data)
    {
        return this.getRandomGenerator().createFromMap(data);
    }
    
    /**
     * Gets generator by class name of generated items
     * @param cName Name of class which generator will be returned
     * @return Generator determined by class name of generated items
     *         or NULL if there is not such an generator
     */
    public static IGenerator getByClassName(String cName)
    {
        IGenerator reti = null;
        for(IGenerator gen: Generator.GENERATORS)
        {
            if (Generator.areClassNamesSame(cName, gen.getGeneratedName()))
            {
                reti = gen;
                break;
            }
        }
        return reti;
    }
    
    /**
     * Gets names of allowed class names
     * @return List with all allowed class names
     */
    public static List<String> getAllowedClassNames()
    {
        List<String> reti = new ArrayList<>();
        for(IGenerator gen: Generator.GENERATORS)
        {
            reti.add(Generator.cleanClassName(gen.getGeneratedName()));
        }
        return reti;
    }
    
    /**
     * Cleans name of class from package names
     * @param cName Name of class which will be cleaned
     * @return Name of class without packages
     */
    private static String cleanClassName(String cName)
    {
        String reti = cName;
        if (cName.contains("."))
        {
            String parts[] = cName.split(Pattern.quote("."));
            if (parts.length > 1)
            {
                reti = parts[parts.length - 1];
            }
        }
        return reti;
    }
    
    /**
     * Checks, if two class names are same
     * @param cName1 Name of first class which will be compared
     * @param cName2 Name of second class which will be compared
     * @return TRUE if class names are some kind of same, FALSE otherwise
     */
    private static boolean areClassNamesSame(String cName1, String cName2)
    {
        return (Generator.cleanClassName(cName1).trim().toLowerCase().equals(
                Generator.cleanClassName(cName2).trim().toLowerCase()));
    }

    @Override
    public String getGeneratedName()
    {
        return Mappable.class.getCanonicalName();
    }
    
    
}
