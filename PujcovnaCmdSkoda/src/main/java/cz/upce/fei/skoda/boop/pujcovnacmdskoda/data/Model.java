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

import cz.upce.fei.skoda.boop.pujcovnacmdskoda.PujcovnaCmd;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.kolekce.KolekceException;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.kolekce.LinkSeznam;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.kolekce.SpojovySeznam;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.BinaryReader;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.BinaryWriter;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.Mappable;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.TextReader;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.TextWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class which is used as data model of application
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Model
{
    /**
     * Data stored in application
     */
    private SpojovySeznam<Mappable> data;
    
    /**
     * List of selected data
     */
    private List<Mappable> selected;
    
    /**
     * Creates new data model of application
     */
    public Model()
    {
        this.data = new LinkSeznam<>();
        this.selected = new ArrayList<>();
    }
    
    /**
     * Gets access to models data
     * @return Model data
     */
    public SpojovySeznam<Mappable> data()
    {
        return this.data;
    }
    
    /**
     * Saves data to text file
     */
    public void saveText()
    {
        PujcovnaCmd.TEXT_WRITER.writeData(this.data);
    }
    
    /**
     * Saves data to text file
     * @param tw Utility which can write data to text file
     */
    public void saveText(TextWriter tw)
    {
        tw.writeData(this.data);
    }
    
    /**
     * Loads data from text file
     */
    public void loadText()
    {
        this.data = PujcovnaCmd.TEXT_READER.loadData();
    }
    
    /**
     * Loads data from text file
     * @param tr Utility which can load data from text file
     */
    public void loadText(TextReader tr)
    {
        this.data = tr.loadData();
    }
    
    /**
     * Selects data by values of attributes
     * @param selectors Values of attributes by which data will be selected
     */
    public void select(Map<String, String> selectors)
    {
        this.selected = new ArrayList<>();
        Iterator<Mappable> it = this.data.iterator();
        while(it.hasNext())
        {
            Mappable data = it.next();
            if (Model.matches(data, selectors))
            {
                this.selected.add(data);
            }
            
        }
    }
    
    /**
     * Deletes all data
     */
    public void delete()
    {
        this.selected = new ArrayList<>();
        this.data.zrus();
    }
    
    /**
     * Deletes selected data from list
     * @return Number of deleted items
     */
    public int deleteSelected()
    {
        int reti = 0;
        if (this.data.jePrazdny() == false)
        {
            Mappable currentActual = null;
            try
            {
                currentActual = this.data.dejAktualni();
            } catch (KolekceException ex) {}
            try
            {
                this.data.nastavPrvni();
                boolean del = false;
                if (this.data.jeDalsi() == false && this.selected.contains(this.data.dejAktualni()))
                {
                    this.data.odeberAktualni();
                    reti++;
                }
                else
                {
                    while(this.data.jeDalsi())
                    {
                        if (del == true)
                        {
                            this.data.odeberPredAktualnim();
                            reti++;
                            del = false;
                        }
                        if (this.selected.contains(this.data.dejAktualni()))
                        {
                            del = true;
                        }
                        this.data.dalsi();
                    }
                }
                if (del == true)
                {
                    this.data.odeberPredAktualnim();
                    reti++;
                }
                if (currentActual != null)
                {
                    this.setAcutal(currentActual);
                }
            } catch (KolekceException ex) {}
        }
        this.selected = new ArrayList<>();
        return reti;
    }
    
    /**
     * Sets reference to actual by value
     * @param item Item which will be set as actual (if in list)
     */
    private void setAcutal(Mappable item)
    {
        if (this.data.jePrazdny() == false)
        {
            try
            {
                this.data.nastavPrvni();
                if (this.data.dejAktualni().equals(item) == false)
                {
                    while(this.data.jeDalsi())
                    {
                        this.data.dalsi();
                        if (this.data.dejAktualni().equals(item))
                        {
                            break;
                        }
                    }
                }

            }
            catch (KolekceException ex)
            {}
        }        
    }
    
    /**
     * Gets list of selected items
     * @return List of selected items
     */
    public List<Mappable> getSelected()
    {
        return this.selected;
    }
    
    /**
     * Edits selected data
     * @param newData New values of attributes of data
     * @return Number of changed items
     */
    public int editSelected(Map<String, String> newData)
    {
        int reti = 0;
        newData = Model.mapToLowerCase(newData);
        for(Mappable item: this.selected)
        {
            item.setData(newData);
            reti++;
        }
        return reti;
    }
    
    /**
     * Converts all map keys to lower case
     * @param map Map which keys will be converted into lowercase
     * @return Map with lower case keys only
     */
    private static Map<String, String> mapToLowerCase(Map<String, String> map)
    {
        Map<String, String> reti = new HashMap<>();
        for(String key: map.keySet())
        {
            reti.put(key.toLowerCase(), map.get(key));
        }
        return reti;
    }
    
    /**
     * Checks, whether selector matches data
     * @param data Data which will be checked
     * @param selectors Selectors which values will be compared to data
     * @return TRUE if selectors matches data, FALSE otherwise
     */
    private static boolean matches(Mappable data, Map<String, String> selectors)
    {
        boolean reti = true;
        Map<String, String> dataMap = data.toMap();
        for(String selector: selectors.keySet())
        {
            if (dataMap.get(selector) == null ||
                    dataMap.get(selector).toLowerCase().equals(selectors.get(selector).toLowerCase()) == false)
            {
                reti = false;
                break;
            }
        }
        return reti;
    }
    
    /**
     * Transforms models data to list of serializable data types
     * @return List with models data as list of serializable data
     */
    private SpojovySeznam<Serializable> toSerializable()
    {
        SpojovySeznam<Serializable> reti = new LinkSeznam<>();
        Iterator<Mappable> it = this.data.iterator();
        while (it.hasNext())
        {
            reti.vlozPosledni(it.next());
        }
        return reti;
    }
    
    /**
     * Saves model data to binary file
     * @param bw Writer of data into binary file
     */
    public void saveBinary(BinaryWriter bw)
    {
        bw.writeData(this.toSerializable());
    }
    
    /**
     * Saves data to binary file
     */
    public void saveBinary()
    {
        this.saveBinary(PujcovnaCmd.BINARY_WRITER);
    }
    
    /**
     * Loads model data from binary file
     * @param br Reader of data from binary file
     */
    public void loadBinary(BinaryReader br)
    {
        this.data.zrus();
        Iterator<Serializable> it = br.loadData().iterator();
        while(it.hasNext())
        {
            this.data.vlozPosledni((Mappable) it.next());
        }
    }
    
    /**
     * Loads model data from binary file
     */
    public void loadBinary()
    {
        this.loadBinary(PujcovnaCmd.BINARY_READER);
    }
}
