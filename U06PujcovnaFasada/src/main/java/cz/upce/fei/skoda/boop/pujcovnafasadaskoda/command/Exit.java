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

import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.PujcovnaFasada;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.spravce.IManager;

/**
 * Class representing exit command
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Exit extends Command
{
    /**
     * Creates new handler for exit command
     * @param dataManager Manager of data
     */
    public Exit(IManager dataManager)
    {
        super(dataManager);
        this.aliases = new String[]{"exit", "konec"};
        this.minimumArguments = 0;
        this.maximumArguments = Integer.MAX_VALUE;
    }

    @Override
    public String getHelp()
    {
        return "Ukonci program";
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        PujcovnaFasada.exit();
        return "";
    }
    
}
