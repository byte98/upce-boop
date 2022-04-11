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

import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.spravce.IManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing edit command
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Edit extends Command
{
    /**
     * Message which informs about finishing of item edit
     */
    private static final String MESSAGE = "Bylo upraveno %d prvku";
    
    /**
     * Creates new handler for edit command
     * @param dataManager Manager of data
     */
    public Edit(IManager dataManager)
    {
        super(dataManager);
        this.aliases = new String[]{"edit", "esel", "e", "edituj"};
        this.minimumArguments = 1;
        this.maximumArguments = Integer.MAX_VALUE;
    }

    @Override
    public String getHelp()
    {
        return """
               Upravy vybrane prvky v seznamu
                - nove hodnoty atributu jsou akceptovany ve formatu 'atribut=novaHodnota'
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        int edited = this.dataManager.editSelectedElements((t) -> {
            Map<String, String> newData = this.prepareMapFromArguments(arguments);
            t.setData(newData);
        });
        if (this.isVerbose())
        {
            reti = String.format(Edit.MESSAGE, edited);
        }
        return reti;
    }
    
    /**
     * Prepares map with data from command arguments
     * @param arguments Arguments of program
     * @return Map containing data parsed from arguments
     */
    private Map<String, String> prepareMapFromArguments(String[] arguments)
    {
        Map<String, String> reti = new HashMap<>();
        for(String argument: arguments)
        {
            if (argument.contains("="))
            {
                String[] parts = argument.split("=");
                if (parts.length >= 2)
                {
                    reti.put(parts[0], parts[1]);
                }
            }
        }
        return reti;
    }
    
}
