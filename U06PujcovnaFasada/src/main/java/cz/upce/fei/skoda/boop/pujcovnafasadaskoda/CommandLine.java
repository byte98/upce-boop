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
package cz.upce.fei.skoda.boop.pujcovnafasadaskoda;

import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.command.Interpreter;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.spravce.IManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Class representing command line interface
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class CommandLine
{
    /**
     * Stream for output of program
     */
    private PrintStream output;
    
    /**
     * Stream of input of program
     */
    private InputStream input;
    
    /**
     * Format for printing error messages
     */
    private static final String ERR = "\033[0;31m %s \033[0m";
    
    /**
     * Manager of data
     */
    private IManager dataManager;
    
    /**
     * Interpreter of commands
     */
    private Interpreter interpreter;
    
    /**
     * Stop flag of command line
     */
    private boolean stop = false;
    
    /**
     * Prepares command line to be started
     * @param manager Manager of data
     */
    public void prepare(IManager manager)
    {
        this.dataManager = manager;
        this.input = Configuration.getCommandsInput();
        this.output = Configuration.getCommandsOutput();
        this.interpreter = new Interpreter(this.dataManager, this.input, this.output);
    }
    
    /**
     * Starts command line
     */
    public void start()
    {
        while(this.stop == false && this.interpreter.hasNext())
        {
            if (this.stop) break;
            String out = this.interpreter.read();
            if (this.interpreter.isError())
            {
                this.printError(out);
            }
            else if (Configuration.commandsPrint() && out.equals("") == false)
            {
                this.output.println(out.trim());
            }
        }
    }
    
    /**
     * Stops command line
     */
    public void stop()
    {
        this.stop = true;
        try
        {
            this.input.close();
            this.output.close();
        }
        catch (IOException ex){}        
    }
    
    /**
     * Prints error to output
     * @param message Message which will be printed
     */
    private void printError(String message)
    {
        output.println(String.format(CommandLine.ERR, message.trim()));        
    }
}
