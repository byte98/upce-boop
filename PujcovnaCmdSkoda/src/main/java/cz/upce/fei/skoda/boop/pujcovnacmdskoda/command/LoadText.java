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
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.FileManipulator;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.TextReader;

/**
 * Handler for load data from text command
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class LoadText extends Command
{
    /**
     * Message informing about successful saving data into text file
     */
    private static final String MSG_SUCCESS = "Data byla uspesne nactena ze souboru %s";
    
    /**
     * Message informing about unsupported file extension entered
     */
    private static final String MSG_INVALIDEXT =
            "CHYBA: '%s' neni podporovany format souboru! Podporovane formaty: %s";
    
    /**
     * Creates new handler for loading data from text
     */
    public LoadText()
    {
        this.aliases = new String[]{"lt", "loadtext", "nt", "nactitext"};
        this.maximumArguments = 1;
    }

    @Override
    public String getHelp()
    {
        return """
               Nacte data z textoveho souboru
                - bez argumentu: bude pouzit vychozi format a vychozi umisteni
                - s argumentem: argument urcuje umisteni souboru a z pripony
                  bude urcen i format dat
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        String path = "";
        if (arguments.length == 0)
        {
            PujcovnaCmd.MODEL.loadText();
            path = PujcovnaCmd.TEXT_READER.getPath();
        }
        else if (arguments.length == 1)
        {
            TextReader tr = TextReader.getByExtension(FileManipulator.getFileExtension(arguments[0]));
            if (tr == null)
            {
                reti = String.format(LoadText.MSG_INVALIDEXT,
                        FileManipulator.getFileExtension(arguments[0]),
                        TextReader.getSupportedExtensions()
                                .stream()
                                .reduce("", (partialString, element) -> partialString + element.toUpperCase() + " ")
                        );
                this.error = true;
            }
            else
            {
                tr.setPath(arguments[0]);
                PujcovnaCmd.MODEL.loadText(tr);
                path = tr.getPath();
            }
            
        }
        if(PujcovnaCmd.VERBOSE && reti.equals(""))
        {
           reti = String.format(LoadText.MSG_SUCCESS, path);
        }        
        return reti;
    }
    
}
