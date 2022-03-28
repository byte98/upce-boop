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
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing select command
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Select extends Command
{
    /**
     * Message which informs about selection done
     */
    private static final String MESSAGE = "Bylo vybrano %d prvku";
    
    /**
     * Creates new handler for select command
     */
    public Select()
    {
        this.aliases = new String[]{"sel", "select", "najdi", "na", "n", "vyber"};
        this.maximumArguments = Integer.MAX_VALUE;
    }

    @Override
    public String getHelp()
    {
        return """
               Vybere prvky podle hodnot atributu
                - prvky budou vybrany podle hodnot ve formatu 'atribut=hodnota'
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        PujcovnaCmd.MODEL.select(this.prepareMapFromArguments(arguments));
        if (PujcovnaCmd.VERBOSE)
        {
            reti = String.format(Select.MESSAGE, PujcovnaCmd.MODEL.getSelected().size());
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
