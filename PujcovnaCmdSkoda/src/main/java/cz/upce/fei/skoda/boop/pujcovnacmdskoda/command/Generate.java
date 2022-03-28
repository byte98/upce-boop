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
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.generator.Generator;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.generator.IGenerator;

/**
 * Class representing generate command
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Generate extends Command
{
    /**
     * Message informing about successful adding item to list
     */
    private static String MSG_ADDED = "%d prvku bylo uspesne pridano do seznamu";
    
    /**
     * Message informing about invalid class name entered
     */
    private static String MSG_NOCLASS = "CHYBA: Nazev '%s' neni platny nazev tridy! Povolene tridy: %s";
    
    /**
     * Message informing about wrong input
     */
    private static String MSG_WRONGIN = "CHYBA: %s neni platny pocet prvku! Pocet prvku musi byt nezaporne cislo.";
    
    /**
     * Creates new handler for generate command
     */
    public Generate()
    {
        this.aliases = new String[]{"gen", "generate", "generuj", "g"};
        this.minimumArguments = 1;
        this.maximumArguments = 2;
    }
    
    @Override
    public String getHelp()
    {
        return """
               Vygeneruje nahodna data a vlozi je do seznamu
                - jeden argument: pocet nahodnych prvku, ktery bude vygenerovan
                - dva argumenty: prvni argument urcuje tridu prvku, ktere budou 
                  vygenerovany, druhy pak jejich pocet
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        int count = -1;
        if (arguments.length == 1)
        {
            try
            {
                count = Integer.parseInt(arguments[0]);
            }
            catch(NumberFormatException ex)
            {
                reti = String.format(Generate.MSG_WRONGIN, arguments[0]);
                this.error = true;
            }
            if (count >= 0)
            {
                IGenerator gen = new Generator();
                gen.fill(PujcovnaCmd.MODEL.data(), count);
            }
            else
            {
                reti = String.format(Generate.MSG_WRONGIN, arguments[0]);
                this.error = true;
            }
        }
        else if (arguments.length == 2)
        {
            try
            {
                count = Integer.parseInt(arguments[1]);
            }
            catch(NumberFormatException ex)
            {
                reti = String.format(Generate.MSG_WRONGIN, arguments[1]);
                this.error = true;
            }
            if (count >= 0)
            {
                IGenerator gen = Generator.getByClassName(arguments[0]);
                if (gen == null)
                {
                    reti = String.format(Generate.MSG_NOCLASS, arguments[0], this.getAvailableClassNames());
                    this.error = true;
                }
                else
                {
                    gen.fill(PujcovnaCmd.MODEL.data(), count);
                }
            }
            else
            {
                reti = String.format(Generate.MSG_WRONGIN, arguments[1]);
                this.error = true;
            }
        }
        if (PujcovnaCmd.VERBOSE && reti.equals(""))
        {
            reti = String.format(Generate.MSG_ADDED, count);
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
    
}
