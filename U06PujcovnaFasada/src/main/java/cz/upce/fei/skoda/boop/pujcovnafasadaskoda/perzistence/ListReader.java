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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing reader of list data from binary file
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class ListReader extends BinaryReader
{

    /**
     * Creates new reader of list data from binary file
     * @param path Path to file from which will be data read
     */
    public ListReader(String path)
    {
        super(path);
    }

    @Override
    public SpojovySeznam<Serializable> loadData()
    {
        SpojovySeznam<Serializable> reti = new LinkSeznam<>();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        
        try
        {
            fis = new FileInputStream(this.filePath);
            ois = new ObjectInputStream(fis);
            this.readData(reti, ois);
            ois.close();
            fis.close();
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(ListReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(ListReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reti;
    }
    
    /**
     * Reads data from input stream to list
     * @param out List to which data will be written into
     * @param in Source of data
     */
    private void readData(SpojovySeznam<Serializable> out, ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        int count = in.readInt();
        for (int i = 0; i < count; i++)
        {
            out.vlozPosledni((Serializable) in.readObject());
        }
    }
    
}
