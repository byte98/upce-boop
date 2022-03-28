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

/**
 * Class abstracting all common attributes for each available command
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract class Command
{
    /**
     * Array with all available aliases for command
     */
    protected String[] aliases = {};
    
    /**
     * Minimum number of arguments needed for command
     */
    protected int minimumArguments = 0;
    
    /**
     * Maximum number of arguments needed for command
     */
    protected int maximumArguments = 0;
    
    /**
     * Flag, whether commands result is error
     */
    protected boolean error = false;
    
    /**
     * Checks, whether input is command or not
     * @param command String containing command which will be checked
     * @return TRUE if input is command, FALSE otherwise
     */
    public boolean isCommand(String command)
    {
        boolean reti = false;
        for(String alias: aliases)
        {
            if(alias.trim().toLowerCase().equals(command.trim().toLowerCase()))
            {
                reti = true;
                break;
            }
        }
        return reti;
    }
    
    /**
     * Gets minimum number of arguments required by command
     * @return Minimum number of arguments required by command
     */
    public int getMinimumArguments()
    {
        return this.minimumArguments;
    }
    
    /**
     * Gets maximum number of arguments required by command
     * @return Maximum number of arguments required by command
     */
    public int getMaximumArguments()
    {
        return this.maximumArguments;
    }
    
    /**
     * Checks, whether number of arguments is valid for command
     * @param numberArguments Number of arguments which will be checked
     * @return TRUE if number of arguments is valid for command, FALSE otherwise
     */
    public boolean isArgumentsNumberValid(int numberArguments)
    {
        return (numberArguments >= this.getMinimumArguments() && 
                numberArguments <= this.getMaximumArguments());
    }
    
    /**
     * Checks, whether commands result is error
     * @return TRUE if commands result is error, FALSE otherwise
     */
    public boolean isError()
    {
        return this.error;
    }
    
    /**
     * Gets help to the command
     * @return Help to the command
     */
    public abstract String getHelp();
    
    /**
     * Runs command
     * @param arguments Arguments of command
     * @return Output of command
     */
    public abstract String execute(String[] arguments);
}
