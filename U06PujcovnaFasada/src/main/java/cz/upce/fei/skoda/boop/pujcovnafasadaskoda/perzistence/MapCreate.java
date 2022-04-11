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

import java.util.Map;

/**
 * Class abstracting all classes which can be created by using map
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract class MapCreate
{
    /**
     * Declaration of constructor without any parameter
     */
    public MapCreate(){}
    
    /**
     * Loads data from map data structure
     * @param data Map data structure containing all necessary data
     */
    public abstract void loadDataFromMap(Map<String, String> data);
}
