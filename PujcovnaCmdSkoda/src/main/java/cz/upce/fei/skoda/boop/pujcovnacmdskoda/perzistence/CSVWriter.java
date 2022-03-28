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
package cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence;

import cz.upce.fei.skoda.boop.pujcovnacmdskoda.kolekce.SpojovySeznam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing writer of data to CSV files
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class CSVWriter extends TextWriter
{
    
    /**
     * Separator of values in file
     */
    public static final char SEPARATOR = ',';
    
    /**
     * Creates new writer of data to CSV file
     * @param path Path to file to which will be data written into
     */
    public CSVWriter(String path)
    {
        super(path);
        this.extensions = new String[]{"csv"};        
    }

    @Override
    public void writeData(SpojovySeznam<Mappable> data)
    {
        try
        {
            StringBuilder output = new StringBuilder();
            output.append(this.prepareHeader(data));
            output.append(this.prepareBody(data));
            this.writeString(output.toString());
        }
        catch (IOException ex)
        {
            Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Prepares header of CSV file
     * @param data Data from which header of file will be prepared
     * @return String containing header of file
     */
    private String prepareHeader(SpojovySeznam<Mappable> data)
    {
        StringBuilder reti = new StringBuilder();
        List<String> keys = this.getAllKeys(data);
        for(int i = 0; i < keys.size(); i++)
        {
            reti.append(keys.get(i));
            if (i < keys.size() - 1)
            {
                reti.append(CSVWriter.SEPARATOR);
            }
        }
        reti.append(System.lineSeparator());
        return reti.toString();
    }
    
    /**
     * Prepares body of CSV file
     * @param data Data from which will be file body prepared
     * @return String containing body of file
     */
    private String prepareBody(SpojovySeznam<Mappable> data)
    {
        StringBuilder reti = new StringBuilder();
        List<String> keys = this.getAllKeys(data);
        Iterator<Mappable> it = data.iterator();
        while (it.hasNext())
        {
            Mappable d = it.next();
            Map<String, String> m = d.toMap();
            m.put("_className", d.getClassName());
            for(int i = 0; i < keys.size(); i++)
            {
                reti.append((
                            m.get(keys.get(i)) == null ? "" : m.get(keys.get(i))
                        ));
                if (i < keys.size() - 1)
                {
                    reti.append(CSVWriter.SEPARATOR);
                }
            }
            reti.append(System.lineSeparator());
        }
        return reti.toString();
    }
    
    /**
     * Gets all keys which occurs in list of data
     * @param data List of maps from which keys will be get
     * @return List of keys which occurs in list of data
     */
    private List<String> getAllKeys(SpojovySeznam<Mappable> data)
    {
        List<String> reti = new ArrayList<>();
        Iterator<Mappable> it = data.iterator();
        while (it.hasNext())
        {
            Mappable m = it.next();
            Map<String,String> map = m.toMap();
            for(String key: map.keySet())
            {
                if (reti.contains(key) == false)
                {
                    reti.add(key);
                }
            }
        }
        if(reti.contains("_className") == false)
        {
            reti.add("_className");
        }
        return reti;
    }    
}
