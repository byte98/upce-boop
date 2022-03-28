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
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.kolekce.KolekceException;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.Mappable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing command print actual
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class PrintActual extends Command
{
    /**
     * Message which informs that actual pointer is not set
     */
    private static final String MSG_NOACTUAL = "CHYBA: Neni nastaven aktualni prvek seznamu!";

    /**
     * Creates new handler for command print actual
     */
    public PrintActual()
    {
        this.aliases = new String[]{"printa", "actual", "printactual", "aktualni", "dej"};
    }
    
    @Override
    public String getHelp()
    {
        return """
               Vypise aktualni prvek seznamu
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        try
        {
            Mappable actual = PujcovnaCmd.MODEL.data().dejAktualni();
            reti = actual.toString();
        }
        catch (KolekceException ex)
        {
            if (PujcovnaCmd.VERBOSE)
            {
                reti = PrintActual.MSG_NOACTUAL;
            }
        }
        return reti;
    }
    
}
