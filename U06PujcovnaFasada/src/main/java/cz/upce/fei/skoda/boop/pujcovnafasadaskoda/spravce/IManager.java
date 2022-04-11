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
package cz.upce.fei.skoda.boop.pujcovnafasadaskoda.spravce;

import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.BinaryReader;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.BinaryWriter;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.Mappable;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.TextReader;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.TextWriter;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Interface defining communication between "front-end" and data
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public interface IManager
{
    /**
     * Counts all elements stored in linked list
     * @return Number of all elements stored in linked list
     */
    public int countElements();
    
    /**
     * Counts selected elements in linked list
     * @return Number of selected elements in linked list
     */
    public int countSelectedElements();
    
    /**
     * Creates new element and adds it to linked list
     * @return Newly created element or NULL if no element could be created
     */
    public Mappable createNew();
    
    /**
     * Creates new element and adds it to linked list
     * @param dataType Name of class which instance will be created
     * @return Newly created element or NULL if no element could be created
     * @throws IllegalArgumentException Thrown when there is no generator
     *                                  of entered name of class
     */
    public Mappable createNew(String dataType) throws IllegalArgumentException;
    
    /**
     * Creates new element and adds it to linked list
     * @param dataType Name of class which instance will be created
     * @param editor Function which is able to edit elements
     * @return Newly created element
     * @throws IllegalArgumentException Thrown when there is no generator
     *                                  of entered name of class
     */
    public Mappable createNew(String dataType, Consumer<Mappable> editor) throws IllegalArgumentException;
    
    /**
     * Deletes whole linked list and all data stored in list
     * @return Number of deleted elements
     */
    public int deleteList();
    
    /**
     * Deletes selected elements from list
     * @return Number of deleted elements
     */
    public int deleteSelectedElements();
    
    /**
     * Edits data of selected elements
     * @param editor Function which can edit element in linked list
     * @return Number of edited elements
     */
    public int editSelectedElements(Consumer<Mappable> editor);
    
    /**
     * Sets pointer to actual element to first one
     * @return TRUE if first element has been set as actual, FALSE otherwise
     */
    public boolean first();
    
    /**
     * Generates random elements to linked list
     * @param number Number of elements which will be created
     * @return Number of generated elements
     */
    default public int generateData(int number)
    {
        int reti = 0;
        for (int i = 0; i < number; i++)
        {
            if (this.createNew() != null)
            {
                reti++;
            }
        }
        return reti;
    }
    
    /**
     * Generates random elements to linked list
     * @param number Number of elements which will be created
     * @param dataType Name of class which instances will be created
     * @return Number of generated elements
     * @throws IllegalArgumentException Thrown when there is no generator
     *                                  of entered name of class
     */
    default public int generateData(int number, String dataType) throws IllegalArgumentException
    {
        int reti = 0;
        for (int i = 0; i < number; i++)
        {
            Mappable m = null;
            try
            {
                m = this.createNew(dataType);
            }
            catch(IllegalArgumentException ex)
            {
                throw ex;
            }
            if (m != null)
            {
                reti++;
            }
        }
        return reti;
    }
    
    /**
     * Gets actual data
     * @return Actual data or NULL if actual data is not set
     */
    public Mappable getActual();
    
    /**
     * Gets all data stored in linked list
     * @return Array with data stored in linked list
     */
    public Mappable[] getAllData();
    
    /**
     * Gets actually selected data
     * @return Array with actually selected data
     */
    public Mappable[] getSelectedData();
    
    /**
     * Checks, whether there is element after actual one
     * @return TRUE if there is another element after actual one, FALSE otherwise
     */
    public boolean hasNext();
    
    /**
     * Sets pointer to actual element to last one
     * @return TRUE if last element has been set as actual, FALSE otherwise
     */
    public boolean last();
    
    /**
     * Loads data from binary file
     * @param reader Reader of data from binary file
     */
    public void loadBinary(BinaryReader reader);
    
    /**
     * Loads data from text file
     * @param reader Reader of text data from file
     */
    public void loadText(TextReader reader);
    
    /**
     * Sets pointer to actual element to next one
     * @return TRUE if next element has been set as actual, FALSE otherwise
     */
    public boolean next();
    
    /**
     * Removes actual element from linked list
     * @return TRUE if actual element has been removed from list,
     *         FALSE otherwise
     */
    public boolean remove();
    
    /**
     * Saves data to binary file
     * @param writer Writer of data to binary file
     */
    public void saveBinary(BinaryWriter writer);
    
    /**
     * Saves data to text file
     * @param writer Writer of data to text file
     */
    public void saveText(TextWriter writer);
    
    /**
     * Selects elements
     * @param filter Filter which checks,
     *               whether element should be selected or not
     * @return Number of selected items
     */
    public int selectElements(Predicate<Mappable> filter);
}
