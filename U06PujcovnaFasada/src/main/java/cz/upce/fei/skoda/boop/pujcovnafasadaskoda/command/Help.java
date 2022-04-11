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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handler of help command
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Help extends Command
{
    /**
     * Number of columns into which commands will be showed
     */
    private static final int COLUMNS = 4;
    
    /**
     * Creates new handler of help command
     * @param dataManager Manager of data
     */
    public Help(IManager dataManager)
    {
        super(dataManager);
        this.aliases = new String[]{"help", "pomoc", "man", "h"};
        this.minimumArguments = 0;
        this.maximumArguments = 1;
    }

    @Override
    public String getHelp()
    {        
        return ("""
                Ukaze napovedu
                 - bez argumentu: ukaze prehled vsech prikazu
                 - s argumentem: ukaze napovedu k zadanemu prikazu
                """
                );
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        if (arguments.length == 0)
        {
            reti = this.prepareAllHelps();
        }
        else
        {
            reti = this.prepareHelpForCommand(arguments[0]);
        }
        return reti;
    }
    
    /**
     * Prepares help with all commands
     * @return String containing summary of all commands
     */
    private String prepareAllHelps()
    {
        StringBuilder reti = new StringBuilder();
        int i = 0;
        for(String alias: this.prepareSorted())
        {
            reti.append(alias);
            if (i < Help.COLUMNS - 1)
            {
                reti.append('\t');
            }
            if (i == Help.COLUMNS - 1)
            {
                reti.append(System.lineSeparator());
            }
            i = (i + 1) % Help.COLUMNS;
        }
        return reti.toString();
    }
    
    /**
     * Prepares sorted help to all commands
     * @return List with sorted aliases of commands
     */
    private List<String> prepareSorted()
    {
        List<String> reti = new ArrayList<>();
        for(Command cmd: Interpreter.getAllCommands())
        {
            reti.add(cmd.aliases[0].toUpperCase());
        }
        Collections.sort(reti);
        return reti;
    }
    
    /**
     * Prepares help for specific command
     * @param command Command which help will be prepared
     * @return Help for specific command or error message
     */
    private String prepareHelpForCommand(String command)
    {
        String reti = String.format(Interpreter.MESSAGE_NOCMD, command);
        for(Command cmd: Interpreter.getAllCommands())
        {
            if(cmd.isCommand(command))
            {
                StringBuilder out = new StringBuilder();
                out.append(cmd.aliases[0].toUpperCase());
                out.append(System.lineSeparator());
                out.append(cmd.getHelp().trim());
                out.append(System.lineSeparator());
                out.append("Dostupne alternativy: ");
                for(String alias: cmd.aliases)
                {
                    out.append(alias.toUpperCase());
                    out.append(" ");
                }
                reti = out.toString();
                break;
            }
        }
        return reti;
    }
    
}
