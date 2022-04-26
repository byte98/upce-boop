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
package cz.upce.fei.skoda.boop.pujcovnaguiskoda.perzistence;

import cz.upce.fei.skoda.boop.pujcovnaguiskoda.kolekce.SpojovySeznam;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing writer of list into binary file
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class ListWriter extends BinaryWriter
{

    /**
     * Creates new writer of list into binary file
     * @param path Path to file to which list will be written into
     */
    public ListWriter(String path)
    {
        super(path);
    }

    @Override
    public void writeData(SpojovySeznam<Serializable> data)
            
    {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try
        {
            fos = new FileOutputStream(this.filePath);
            oos = new ObjectOutputStream(fos);
            this.writeList(data, oos);
            oos.close();
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(ListWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ListWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try {
                oos.close();
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(ListWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Writes list to output
     * @param list List which will be written into output
     * @param output Output into which will be list written into
     */
    private void writeList(SpojovySeznam<Serializable> list, ObjectOutputStream output) throws IOException
    {
        output.writeInt(list.size()); // Write length of list
        Iterator<Serializable> it = list.iterator();
        while (it.hasNext())
        {
            output.writeObject(it.next());
        }
    }
}
