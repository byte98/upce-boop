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
 * Class representing command last
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Last extends Command
{
    /**
     * Message which informs that list is empty
     */
    private static final String MSG_LISTEMPTY =
            "CHYBA: Nelze nastavit ukazatel na prvni prvek seznamu! Seznam je prazdny.";
    
    /**
     * Message which informs about successful setting of pointer to last item
     */
    private static final String MSG_SUCCESS =
            "Ukazatel byl nastaven na posledni prvek seznamu";
    
    /**
     * Creates new handler of command last
     */
    public Last()
    {
        this.aliases = new String[]{"last", "l", "posledni", "po"};
    }

    @Override
    public String getHelp()
    {
        return """
               Nastavi ukazatel aktualniho prvku seznamu na posledni prvek seznamu
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        try
        {
            PujcovnaCmd.MODEL.data().nastavPosledni();
        }
        catch (KolekceException ex)
        {
            reti = Last.MSG_LISTEMPTY;
            this.error = true;
        }
        if (reti.equals("") && PujcovnaCmd.VERBOSE)
        {
            reti = Last.MSG_SUCCESS;
        }
        return reti;
    }
    
}
