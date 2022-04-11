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

import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.generator.Generator;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.Mappable;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.spravce.IManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing new command
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class New extends Command
{
    /**
     * Message informing about successful adding item to list
     */
    private static String MSG_ADDED = "Novy prvek byl uspesne vlozen do seznamu(%s)";
    
    /**
     * Message informing about invalid class name entered
     */
    private static String MSG_NOCLASS = "CHYBA: Nazev '%s' neni platny nazev tridy! Povolene tridy: %s";
    
    /**
     * Creates new handler for new command
     * @param dataManager Manager of data
     */
    public New(IManager dataManager)
    {
        super(dataManager);
        this.aliases = new String[]{"new", "novy", "no"};
        this.minimumArguments = 0;
        this.maximumArguments = Integer.MAX_VALUE;
    }

    @Override
    public String getHelp()
    {
        return """
               Vytvori nove vozidlo a vlozi ho do seznamu
                - bez argumentu: vytvori nahodne vozidlo
                - s 1 argumentem: bude vytvoreno vozidlo zadane tridy
                - s vice argumenty: bude vytvoreno vozidlo tridy prvniho
                  argumentu s atritbuty zadanymi jako dalsi argumenty, a to
                  ve formatu 'atribut=hodnota'
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        Mappable data = null;
        if (arguments.length == 0)
        {
            data = this.dataManager.createNew();
        }
        else if (arguments.length == 1)
        {
            try
            {
                data = this.dataManager.createNew(arguments[0]);
            }
            catch(IllegalArgumentException ex)
            {
                reti = String.format(New.MSG_NOCLASS, arguments[0], this.getAvailableClassNames());
                this.error = true;
            }
        }
        else if (arguments.length > 1)
        {
            try
            {
                data = this.dataManager.createNew(arguments[0], (t) -> {
                    t.setData(this.prepareMapFromArguments(arguments));
                });
            }
            catch(IllegalArgumentException ex)
            {
                reti = String.format(New.MSG_NOCLASS, arguments[0], this.getAvailableClassNames());
                this.error = true;
            }
                        
        }     
        if (this.isVerbose() && "".equals(reti) && data != null)
        {
            reti = String.format(New.MSG_ADDED, data.toString());
        }
        return reti;
    }
    
    /**
     * Gets string with all available class names for generator
     * @return String with all class names for generator
     */
    private String getAvailableClassNames()
    {
        return Generator.getAllowedClassNames()
                .stream()
                .reduce("", (partialString, element) -> partialString + element.toUpperCase() + " ");
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
