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

/**
 * Class representing command remove actual
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Remove extends Command
{
    /**
     * Message which informs that actual pointer is not set
     */
    private static final String MSG_NOACTUAL = "CHYBA: Neni nastaven aktualni prvek seznamu!";
    
    /**
     * Message which informs that actual item has been removed
     */
    private static final String MSG_SUCCESS = "Aktualni prvek byl odebran ze seznamu(%s)";

    /**
     * Creates new handler for command remove actual
     */
    public Remove()
    {
        this.aliases = new String[]{"rem", "remove", "odeber", "vyjmi", "od"};
    }
    
    @Override
    public String getHelp()
    {
        return """
               Odebere aktualni prvek ze seznamu
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        try
        {
            Mappable actual = PujcovnaCmd.MODEL.data().odeberAktualni();
            if (PujcovnaCmd.VERBOSE)
            {
                reti = String.format(Remove.MSG_SUCCESS, actual.toString());
            }
        }
        catch (KolekceException ex)
        {
            reti = Remove.MSG_NOACTUAL;
            this.error = true;
        }
        return reti;
    }
    
}
