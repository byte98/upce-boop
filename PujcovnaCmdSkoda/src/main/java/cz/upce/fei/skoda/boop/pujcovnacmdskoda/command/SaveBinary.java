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
package cz.upce.fei.skoda.boop.pujcovnacmdskoda.command;

import cz.upce.fei.skoda.boop.pujcovnacmdskoda.PujcovnaCmd;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.ListWriter;

/**
 * Class representing command save data to binary file
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class SaveBinary extends Command
{
    /**
     * Message informing about successful saving data into binary file
     */
    private static final String MSG_SUCCESS
            = "Data byla uspesne ulozena do binarniho souboru %s";
    
    /**
     * Creates new handler for command save data to binary file
     */
    public SaveBinary()
    {
        this.aliases = new String[]{"sb", "savebinary", "ulozbinarni", "zalohuj"};
        this.maximumArguments = 1;
    }

    @Override
    public String getHelp()
    {
        return """
               Ulozi data do binarniho souboru
                - bez argumentu:  bude pouzita vychozi cesta k vystupnimu souboru
                - s 1 argumentem: argument slouzi jako cesta k vystupnimu souboru
               """;
    }

    @Override
    public String execute(String[] arguments) 
    {
        String reti = "";
        String path = PujcovnaCmd.BINARY_WRITER.getPath();
        if (arguments.length == 0)
        {
            PujcovnaCmd.MODEL.saveBinary();
        }
        else if (arguments.length == 1)
        {
            path = arguments[0];
            PujcovnaCmd.MODEL.saveBinary(new ListWriter(path));
        }
        if (PujcovnaCmd.VERBOSE)
        {
            reti = String.format(SaveBinary.MSG_SUCCESS, path);
        }
        return reti;
    }
    
}
