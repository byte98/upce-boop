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
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which can write data to XML file
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class XMLWriter extends TextWriter
{
    /**
     * Default header of XML files
     */
    public static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    
    /**
     * Name of root element
     */
    public static final String ROOT = "xml";
    
    /**
     * String used for padding in XML file
     */
    private static final String PADDING = "    ";    

    /**
     * Creates new writer of data to XML file
     * @param path Path to file to which will be data written into
     */
    public XMLWriter(String path)
    {
        super(path);
        this.extensions = new String[]{"xml"};
    }

    @Override
    public void writeData(SpojovySeznam<Mappable> data)
    {
        try
        {
            StringBuilder output = new StringBuilder();
            output.append(XMLWriter.HEADER);
            output.append(System.lineSeparator());
            output.append("<");
            output.append(XMLWriter.ROOT);
            output.append(">");
            output.append(System.lineSeparator());
            Iterator<Mappable> it = data.iterator();
            while(it.hasNext())
            {
                Mappable d = it.next();
                output.append(this.prepareObject(d));
            }
            output.append("</");
            output.append(XMLWriter.ROOT);
            output.append(">");
            output.append(System.lineSeparator());
            this.writeString(output.toString());
        }
        catch (IOException ex)
        {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Prepares attribute to be written into XML file
     * @param name Name of attribute
     * @param value Value of attribute
     * @return XML string containing attribute with its value
     */
    private String prepareAttribute(String name, String value)
    {
        StringBuilder reti = new StringBuilder();
        reti.append(XMLWriter.PADDING);
        reti.append(XMLWriter.PADDING);
        reti.append("<");
        reti.append(name);
        reti.append(">");
        reti.append(value);
        reti.append("</");
        reti.append(name);
        reti.append(">");
        reti.append(System.lineSeparator());
        return reti.toString();
    }
    
    /**
     * Prepares object to be written into XML file
     * @param obj Object which will be written into XML file
     * @return XML string containing object with all its attributes
     */
    private String prepareObject(Mappable obj)
    {
        StringBuilder reti = new StringBuilder();
        reti.append(XMLWriter.PADDING);
        reti.append("<");
        reti.append(obj.getClassName());
        reti.append(">");
        reti.append(System.lineSeparator());
        Map<String, String> map = obj.toMap();
        for(String key: map.keySet())
        {
            reti.append(this.prepareAttribute(key, map.get(key)));
        }
        reti.append(XMLWriter.PADDING);
        reti.append("</");
        reti.append(obj.getClassName());
        reti.append(">");
        reti.append(System.lineSeparator());
        return reti.toString();
    }
}
