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

/**
 * Class representing delete selected command
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class DeleteSelected extends Command
{
    /**
     * Message informing about finishing deleting list
     */
    private static final String MSG_DONE = "Vybrane prvky byly uspesne smazany (smazano bylo %d polozek)";
    
    /**
     * Creates new handler for delete selected command
     */
    public DeleteSelected()
    {
        this.aliases = new String[]{"dsel", "delsel", "deleteselected", "smazvybrane", "sv"};
    }

    @Override
    public String getHelp()
    {
        return """
               Smaze vybrane prvky ze seznamu
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        int deleted = PujcovnaCmd.MODEL.deleteSelected();
        if (PujcovnaCmd.VERBOSE)
        {
            reti = String.format(DeleteSelected.MSG_DONE, deleted);
        }
        return reti;
    }
    
}
