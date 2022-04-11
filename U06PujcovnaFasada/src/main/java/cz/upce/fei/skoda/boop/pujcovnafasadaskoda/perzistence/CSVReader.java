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
package cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence;

import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.kolekce.LinkSeznam;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.kolekce.SpojovySeznam;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which can read objects from CSV file
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class CSVReader extends TextReader
{

    /**
     * Creates new object which can read data from CSV file
     * @param path Path to file from which data will be read
     */
    public CSVReader(String path)
    {
        super(path);
        this.extensions = new String[]{"csv"};
    }

    @Override
    public SpojovySeznam<Mappable> loadData()
    {
        SpojovySeznam<Mappable> reti = new LinkSeznam<>();
        try
        {
            Iterator<String> it = this.lineIterator();
            boolean firstLine = true;
            List<String> keys = null;
            while(it.hasNext())
            {
                if(firstLine)
                {
                    keys = this.dataToList(it.next());
                    firstLine = false;
                }
                else if(keys != null)
                {
                    reti.vlozPosledni(this.createObject(keys, this.dataToList(it.next())));
                }
            }
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reti;
    }
    
    /**
     * Gets list of strings from string containing data
     * @param data String containing data
     * @return List of strings from string containing data
     */
    private List<String> dataToList(String data)
    {
        List<String> reti = new ArrayList<>();
        String[] parts = data.split(Character.toString(CSVWriter.SEPARATOR));
        for(String part: parts)
        {
            reti.add(part.trim());
        }
        return reti;
    }
    
    /**
     * Creates object from keys and values
     * @param keys Keys of map from which object will be created
     * @param values Values of map from which object will be created
     * @return Object created from entered list of keys and values
     */
    private Mappable createObject(List<String> keys, List<String> values)
    {
        Mappable reti = null;
        Map<String, String> data = new HashMap<>();
        for(int i = 0; i < keys.size(); i++)
        {
            if (i < values.size())
            {
                data.put(keys.get(i), values.get(i));
            }
            else
            {
                data.put(keys.get(i), null);
            }
        }
        if (data.get("_className") != null)
        {
            try
            {
                Class<?> cls = Class.forName(data.get("_className"));
                Constructor<?> ctor = cls.getConstructor(Map.class);
                reti = (Mappable)ctor.newInstance(new Object[]{data});
            }
            catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
            {
                Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return reti;
    }
    
}
