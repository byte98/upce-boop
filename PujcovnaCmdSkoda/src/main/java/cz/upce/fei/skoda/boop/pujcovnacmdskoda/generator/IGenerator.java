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

import cz.upce.fei.skoda.boop.pujcovnacmdskoda.kolekce.SpojovySeznam;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.Mappable;
import java.util.Map;

/**
 * Interface defining methods common for all generators
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public interface IGenerator
{
    /**
     * Creates next item
     * @return New randomly created item
     */
    public Mappable next();
    
    /**
     * Creates object from data stored in map data structure
     * @param data Map with data of object
     * @return New object created from data stored in map data structure
     */
    public Mappable createFromMap(Map<String, String> data);
    
    /**
     * Fills list with random data
     * @param list List which will be filled with random data
     * @param count Count of item which will be inserted into list
     */    
    default void fill(SpojovySeznam<Mappable> list, int count)
    {
        for (int i = 0; i < count; i++)
        {
            list.vlozPosledni(this.next());
        }
    }
    
    /**
     * Gets class name of generated items
     * @return Class name of generated items
     */
    public String getGeneratedName();
}
