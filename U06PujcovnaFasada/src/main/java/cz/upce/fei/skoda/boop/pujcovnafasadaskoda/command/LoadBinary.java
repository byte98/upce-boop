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
package cz.upce.fei.skoda.boop.pujcovnafasadaskoda.command;

import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.Configuration;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.BinaryReader;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.ListReader;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.spravce.IManager;

/**
 * Class representing command load data from binary file
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class LoadBinary extends Command
{
    /**
     * Message informing about successful loading data from binary file
     */
    private static final String MSG_SUCCESS
            = "Data byla uspesne nactena z binarniho souboru %s";

    /**
     * Creates new handler for command load data from binary file
     * @param dataManager Manager of data
     */
    public LoadBinary(IManager dataManager)
    {
        super(dataManager);
        this.aliases = new String[]{"lb", "loadbinary", "nactibinarni", "obnov"};
        this.maximumArguments = 1;
    }
    
    @Override
    public String getHelp()
    {
        return """
               Nacte data z binarniho souboru
                - bez argumentu:  bude pouzita vychozi cesta k binarnimu souboru
                - s 1 argumentem: argument bude pouzit jako cesta k binarnimu souboru
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        String reti = "";
        BinaryReader reader = Configuration.getBinaryReader();
        if (arguments.length == 1)
        {
            reader = new ListReader(arguments[0]);
        }
        this.dataManager.loadBinary(reader);
        if (this.isVerbose())
        {
            reti = String.format(LoadBinary.MSG_SUCCESS, reader.getPath());
        }
        return reti;
    }
    
}
