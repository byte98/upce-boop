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

import java.io.Serializable;
import java.util.Map;


/**
 * Interface defining methods for all classes that can save and load
 * its state using map data structure.
 * <p>
 * <b>
 * Interface requires existence of constructor, which can create instance
 * of object by passing map with data only.
 * </b>
 * If there is not such an constructor, <code>NoSuchMethodException</code> will
 * be thrown.
 * </p>
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public interface Mappable extends Serializable
{
    /**
     * Gets map containing attributes of object
     * @return Map with attributes of object
     */
    public Map<String, String> toMap();
        
    /**
     * Gets name of class
     * @return Name of class
     */
    public String getClassName();
    
    /**
     * Sets new values of attributes to object
     * @param data New values of attributes of object
     */
    public void setData(Map<String, String> data);
        
}
