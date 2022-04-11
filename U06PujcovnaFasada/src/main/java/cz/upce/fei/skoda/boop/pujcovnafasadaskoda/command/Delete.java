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
 * Class representing delete command
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Delete extends Command
{
    /**
     * Message informing about finishing deleting list
     */
    private static final String MSG_DONE = "Seznam byl uspesne smazan (smazano bylo %d polozek)";
    
    /**
     * Creates new handler for delete command
     * @param dataManager Manager of data
     */
    public Delete(IManager dataManager)
    {
        super(dataManager);
        this.aliases = new String[]{"del", "delete", "remove", "smaz", "zrus"};
        this.minimumArguments = 0;
        this.maximumArguments = 0;
    }

    @Override
    public String getHelp()
    {
        return """
               Smaze vsechny prvky ze seznamu
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        int deleted = this.dataManager.deleteList();
        if (this.isVerbose())
        {
            reti = String.format(Delete.MSG_DONE, deleted);
        }
        return reti;
    }
    
}
