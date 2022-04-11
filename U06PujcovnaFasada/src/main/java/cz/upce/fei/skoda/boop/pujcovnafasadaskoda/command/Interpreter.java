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
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Interpreter of commands from command line
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Interpreter
{
    /**
     * Definition of character which marks name of variable
     */
    private static final String CHAR_VAR = "$";
    
    /**
     * Definition of character which marks comment
     */
    private static final String CHAR_COMMENT = "#";
    
    /**
     * Message which will be returned if no command has been found
     */
    public static final String MESSAGE_NOCMD =
    "CHYBA: '%s' neni platny prikaz! "
            + "Pro vypis platnych prikazu zadejte prikaz 'help'.";
    
    /**
     * Message which will be returned if no command has been entered
     */
    private static final String MESSAGE_NOINPUT = 
    "CHYBA: Nebyl zadan zadny prikaz!";
    
    /**
     * Message which will be returned if number of entered arguments is invalid
     */
    private static final String MESSAGE_INVALIDARGS = 
    "CHYBA: Neocekavany pocet argumentu! "
            + "Ocekavano minimalne %d, maximalne %d, ale zadano bylo %d.";
    
    /**
     * Message which will be returned if number of entered arguments and if
     * command expects exact number of arguments
     */
    private static final String MESSAGE_INVALIDARGSEX =
    "CHYBA: Neocekavany pocet argumentu! "
            + "Ocekavano bylo %d, ale zadano bylo %d.";
    
    /**
     * Message which informs about invalid name of variable
     */
    private static final String MESSAGE_UNKNOWNVARIABLE = 
            "CHYBA: Neznama promenna! Promenna se jmenem '%s' nebyla nalezena.";
    
    /**
     * Array with all available commands
     */
    private static Command[] commands;
    
    /**
     * Source of input of commands
     */
    private final InputStream input;
    
    /**
     * Stream for output of commands
     */
    private static PrintStream output;
    
    /**
     * Reference to scanner of source of commands
     */
    private Scanner inputScanner;
    
    /**
     * Map of integer type variables
     */
    private static Map<String, Integer> VARS_INT;
    
    /**
     * Map of string type variables
     */
    private static Map<String, String> VARS_STRING;
    
    /**
     * Counter of executed commands
     */
    private int commandCounter;
    
    /**
     * Manager of data
     */
    private IManager dataManager;
    
    /**
     * Flag, whether last command ended with error
     */
    private boolean error;
    
    /**
     * Creates new interpreter of commands
     * @param dataManager Manager of data
     * @param is Source of commands
     */
    public Interpreter(IManager dataManager, InputStream is)
    {
        this.dataManager = dataManager;
        this.input = is;
        Interpreter.output = System.out;
        this.inputScanner = new Scanner(this.input);
        Interpreter.VARS_INT = new HashMap<>();
        Interpreter.VARS_STRING = new HashMap<>();
        this.commandCounter = 1;
        this.prepareCommands();
    }
    
    /**
     * Creates new interpreter of commands
     * @param dataManager Manager of data
     * @param is Source of commands
     * @param ps Reference to where output of commands will be sent
     */
    public Interpreter(IManager dataManager, InputStream is, PrintStream ps)
    {
        this(dataManager, is);
        Interpreter.output = ps;
    }   
    
    /**
     * Prepares all commands for interpreter
     */
    private void prepareCommands()
    {
        Interpreter.commands = new Command[]{
            new Count(this.dataManager),new CountSelected(this.dataManager),
            new Delete(this.dataManager), new DeleteSelected(this.dataManager),
            new Echo(this.dataManager), new Edit(this.dataManager),
            new Exit(this.dataManager), new First(this.dataManager),
            new Generate(this.dataManager), new Help(this.dataManager),
            new Int(this.dataManager), new Last(this.dataManager),
            new LoadBinary(this.dataManager), new LoadText(this.dataManager),
            new New(this.dataManager), new Next(this.dataManager),
            new NoOutput(this.dataManager), new NonVerbose(this.dataManager),
            new Output(this.dataManager), new Print(this.dataManager),
            new PrintActual(this.dataManager),
            new PrintSelected(this.dataManager), new Remove(this.dataManager),
            new Run(this.dataManager), new SaveBinary(this.dataManager),
            new SaveText(this.dataManager), new Select(this.dataManager),
            new Str(this.dataManager), new Verbose(this.dataManager)
        };
                
    }
    
    /**
     * Sets new integer type variable
     * @param name Name of variable
     * @param value Value of variable
     */
    public static void setInt(String name, int value)
    {
        if(Interpreter.VARS_STRING.containsKey(name.trim().toLowerCase()))
        {
            Interpreter.VARS_STRING.remove(name.trim().toLowerCase());
        }
        Interpreter.VARS_INT.put(name.trim().toLowerCase(), value);
    }
    
    /**
     * Gets value of variable
     * @param name Name of variable
     * @return Value of variable or NULL
     */
    public static Integer getInt(String name)
    {
        return Interpreter.VARS_INT.get(name.trim().toLowerCase());
    }
    
    
    /**
     * Sets string variable
     * @param name Name of variable
     * @param value Value of variable
     */
    public static void setString(String name, String value)
    {
        if(Interpreter.VARS_INT.containsKey(name.trim().toLowerCase()))
        {
            Interpreter.VARS_INT.remove(name.trim().toLowerCase());
        }
        Interpreter.VARS_STRING.put(name.trim().toLowerCase(), value);
    }
    
    /**
     * Gets value of string type variable
     * @param name Name of variable
     * @return Value of variable or NULL
     */
    public static String getString(String name)
    {
        return Interpreter.VARS_STRING.get(name.trim().toLowerCase());
    } 
    
    /**
     * Reads command from input and handles it
     * @return Output of command
     */
    public String read()
    {
        String reti = "";
        if(this.hasNext())
        {
            String line = this.inputScanner.nextLine();
            if (line != null && line.equals("") == false && line.startsWith(Interpreter.CHAR_COMMENT) == false)
            {
                reti = this.handleCommand(line);
            }
        }
        return reti;
    }
    
    /**
     * Checks, whether there is something in input
     * @return TRUE if there is line in input, FALSE otherwise
     */
    public boolean hasNext()
    {
        return this.inputScanner.hasNextLine();
    }
    
    /**
     * Checks, whether last command ended with error
     * @return TRUE if last command ended with error, FALSE otherwise
     */
    public boolean isError()
    {
        return this.error;
    }
    
    /**
     * Handles command from input
     * @param command Input from input stream which will be handled
     * @return String containing output of command or error message if something
     *         has failed
     */
    private String handleCommand(String command)
    {
        this.error = false;
        String reti = null;
        String[] parts = this.parseArguments(command);
        if(parts != null && parts.length > 0)
        {
            Command cmd = this.getCommand(parts[0]);
            if (cmd == null)
            {
                reti = String.format("[%d] ", this.commandCounter) + String.format(Interpreter.MESSAGE_NOCMD, command);
            }
            else
            {
                reti = this.checkArguments(cmd, parts.length - 1);
                if (reti == null)
                {
                    reti = cmd.execute(Arrays.copyOfRange(parts, 1, parts.length));
                    if (cmd.isError())
                    {
                        reti = String.format("[%d] %s", this.commandCounter, reti);
                        this.error = true;
                    }
                }
            }            
        }
        else if (parts != null)
        {
            reti = Interpreter.MESSAGE_NOINPUT;
        }
        this.commandCounter++;
        return reti;
    }
    
    /**
     * Gets command by input from input stream
     * @param command Input from input stream by which
     *                command will be determined
     * @return Command determined by input from command line or NULL
     */
    private Command getCommand(String command)
    {
        Command reti = null;
        for(Command cmd: Interpreter.commands)
        {
            if(cmd.isCommand(command))
            {
                reti = cmd;
                break;
            }
        }
        return reti;
    }
    
    /**
     * Checks, whether number of entered arguments is valid
     * @param cmd Command which number of commands will be checked
     * @param argc Number of arguments which will be checked
     * @return Error message if number is invalid or NULL if otherwise
     */
    private String checkArguments(Command cmd, int argc)
    {
        String reti = null;
        if (cmd.isArgumentsNumberValid(argc) == false)
        {
            if (cmd.getMaximumArguments() == cmd.getMinimumArguments())
            {
                reti = String.format(
                        Interpreter.MESSAGE_INVALIDARGSEX, 
                        cmd.getMaximumArguments(),
                        argc
                        );
            }
            else
            {
                reti = String.format(
                        Interpreter.MESSAGE_INVALIDARGS,
                        cmd.getMinimumArguments(),
                        cmd.getMaximumArguments(),
                        argc
                );
            }
        }
        return reti;
    }
    
    /**
     * Gets all commands available to interpreter
     * @return Array of all available commands
     */
    public static Command[] getAllCommands()
    {
        return Arrays.copyOf(Interpreter.commands, Interpreter.commands.length);
    }
    
    /**
     * Parses arguments from input line
     * @param input Line which will be parsed into arguments
     * @return Parsed arguments from input line
     */
    private String[] parseArguments(String input)
    {
        List<String> args = new ArrayList<>();
        StringBuilder argument = new StringBuilder();
        boolean wasQuotation = false;
        boolean stop = false;
        for(char c: input.toCharArray())
        {
            if (stop)
            {
                break;
            }
            switch (c) {
                case '\"' -> {
                    if (wasQuotation == false)
                    {
                        wasQuotation = true;
                    }
                    else if (wasQuotation == true)
                    {
                        wasQuotation = false;
                        if (argument.toString().equals("") == false)
                            args.add(argument.toString());
                        argument.delete(0, argument.length());
                    }
                }
                case ' ' -> {
                    if (wasQuotation)
                    {
                        argument.append(c);
                    }
                    else
                    {
                        if (argument.toString().equals("") == false)
                            args.add(argument.toString());
                        argument.delete(0, argument.length());
                    }
                }
                default -> {
                        if (c == Interpreter.CHAR_COMMENT.charAt(0) && wasQuotation == false)
                                stop = true;
                        else
                            argument.append(c);
                }
            }
        }
        if (argument.toString().equals("") == false)
            args.add(argument.toString());        
        String[] reti = new String[args.size()];
        String errVar = "";
        for(int i = 0; i < args.size(); i++)
        {
            if (args.get(i).startsWith(Interpreter.CHAR_VAR)) // Is variable
            {
                if (Interpreter.VARS_INT.get(args.get(i).substring(1)) != null) // INTEGER
                {
                    reti[i] = String.valueOf(Interpreter.VARS_INT.get(args.get(i).substring(1)));
                }
                else if (Interpreter.VARS_STRING.get(args.get(i).substring(1)) != null) // STRING
                {
                    reti[i] = Interpreter.VARS_STRING.get(args.get(i).substring(1));
                }
                else
                {
                    errVar = args.get(i).substring(1);
                    reti = null;
                    break;
                }
            }
            else
            {
                reti[i] = args.get(i);
            }            
        }
        if (reti == null)
            Interpreter.output.println(String.format(Interpreter.MESSAGE_UNKNOWNVARIABLE, errVar));
        return reti;
    }
    
    /**
     * Gets reference to actual output of interpreter
     * @return Reference to actual output of interpreter
     */
    public static PrintStream getOutput()
    {
        return Interpreter.output;
    }
    
    /**
     * Gets actual value of counter of processed commands
     * @return Value of counter of commands
     */
    public int getCommandCounter()
    {
        return this.commandCounter;
    }
}
