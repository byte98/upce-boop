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

/**
 * Class representing next command
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Next extends Command
{
    /**
     * Message which informs that actual pointer is not set
     */
    private static final String MSG_NOACTUAL
            = "CHYBA: Neni nastaven aktualni prvek seznamu!";
    
    /**
     * Message which informs that actual pointer has been set to next one
     */
    private static final String MSG_SUCCESS
            = "Ukazatel aktualniho prvku byl nastaven na dalsi prvek";
    
    /**
     * Message which informs that there is no next item
     */
    private static final String MSG_NONEXT
            = "CHYBA: Nelze nastavit ukazatel na dalsi prvek! Dalsi prvek neexistuje.";
    
    /**
     * Creates new handler for next command
     */
    public Next()
    {
        this.aliases = new String[]{"next", "dalsi", "da"};
    }

    @Override
    public String getHelp()
    {
        return """
               Nastavi ukazatel aktualniho prvku na dalsi prvek v seznamu
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        if (PujcovnaCmd.MODEL.data().jeDalsi())
        {
            try
            {
                PujcovnaCmd.MODEL.data().dalsi();
                if (PujcovnaCmd.VERBOSE)
                {
                    reti = Next.MSG_SUCCESS;
                }
            }
            catch (KolekceException ex)
            {
                reti = Next.MSG_NOACTUAL;
                this.error = true;
            }
        }
        else
        {
            reti = Next.MSG_NONEXT;
            this.error = true;
        }
        return reti;
    }
}
