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

/**
 * Class representing set integer command
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Int extends Command
{
    /**
     * Message informing about wrong input
     */
    private static final String MSG_NOINT
            = "CHYBA: Ocekavano cele cislo! '%s' nelze prevest na cele cislo.";
    
    /**
     * Message informing about successful setting integer variable
     */
    private static final String MSG_SUCCESS
            = "Promenna '%s' byla nastavena na hodnotu '%d'";
    
    
    /**
     * Creates new handler for set integer command
     * @param dataManager Manager of data
     */
    public Int(IManager dataManager)
    {
        super(dataManager);
        this.minimumArguments = this.maximumArguments = 2;
        this.aliases = new String[]{"int", "integer"};
    }

    @Override
    public String getHelp()
    {
        return """
               Nastavi promennou celociselneho datoveho typu
                - prvni argument: nazev promenne
                - druhy argument: hodnota promenne
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        int value = 0;
        try
        {
            value = Integer.parseInt(arguments[1]);
            Interpreter.setInt(arguments[0], value);
            if (this.isVerbose())
            {
                reti = String.format(Int.MSG_SUCCESS, arguments[0], value);
            }
        }
        catch(NumberFormatException ex)
        {
            reti = String.format(Int.MSG_NOINT, arguments[1]);
            this.error = true;
        }
        return reti;
    }
    
}
