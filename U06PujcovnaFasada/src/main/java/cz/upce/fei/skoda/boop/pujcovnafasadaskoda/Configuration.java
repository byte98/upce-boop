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

import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.BinaryReader;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.BinaryWriter;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.FileManipulator;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.ListReader;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.ListWriter;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.TextReader;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.TextWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;

/**
 * Class holding whole configuration of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public final class Configuration
{
//<editor-fold defaultstate="collapsed" desc="Editable configuration of program">
    //<editor-fold defaultstate="collapsed" desc="1) Configuration of files">
    /**
     * Path to text file which will be used as storage for data
     */
    private static final String FILE_TEXT = "./data.xml";
    
    /**
     * Path to binary file which will be used as storage for data
     */
    private static final String FILE_BINARY = "./data.bin";
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="2) Configuration of command line">
    /**
     * Path to file with commands which will be handled by command line
     *  <li>if NULL or not existing file, standard system input will be used</li>
     */
    private static final String COMMANDS_INPUT = null;
    
    /**
     * Path to file to which output of command line will be printed into
     * <li>if NULL, standard system output will be used</li>
     */
    private static final String COMMANDS_OUTPUT = null;
    
    /**
     * Flag, whether command line should print additional information messages
     */
    private static boolean COMMANDS_VERBOSE = false;
    
    /**
     * Flag, whether command line should print anything(TRUE) or nothing(FALSE)
     */
    private static boolean COMMANDS_PRINT = true;
    //</editor-fold>
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Implicit configuration of program [THIS SHOULD NOT BE EDITED]">
    /**
     * Writer of data into binary file
     */
    private static final BinaryWriter BINARY_WRITER
            = new ListWriter(Configuration.FILE_BINARY);
    
    /**
     * Reader of data from binary file
     */
    private static final BinaryReader BINARY_READER
            = new ListReader(Configuration.FILE_BINARY);
    
    /**
     * Writer of data into text file
     */
    private static final TextWriter TEXT_WRITER
            = TextWriter.getByExtension(
                    FileManipulator.getFileExtension(Configuration.FILE_TEXT));
    
    /**
     * Reader of data from text file
     */
    private static final TextReader TEXT_READER
            = TextReader.getByExtension(
                    FileManipulator.getFileExtension(Configuration.FILE_TEXT));
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters of configuration">
    /**
     * Gets writer of data into binary file
     * @return Writer of data into binary file
     */
    public static BinaryWriter getBinaryWriter()
    {
        return Configuration.BINARY_WRITER;
    }
    
    /**
     * Gets reader of data from binary file
     * @return Reader of data from binary file
     */
    public static BinaryReader getBinaryReader()
    {
        return Configuration.BINARY_READER;
    }
    
    /**
     * Gets writer of data into text file
     * @return Writer of data into text file
     */
    public static TextWriter getTextWriter()
    {
        TextWriter reti = Objects.requireNonNull(Configuration.TEXT_WRITER);
        reti.setPath(Configuration.FILE_TEXT);
        return reti;
    }
    
    /**
     * Gets reader of data from text file
     * @return Reader of data from text file
     */
    public static TextReader getTextReader()
    {
        TextReader reti = Objects.requireNonNull(Configuration.TEXT_READER);
        reti.setPath(Configuration.FILE_TEXT);
        return reti;
    }
    
    /**
     * Gets input stream of commands which should be parsed
     * @return Input stream of commands
     */
    public static InputStream getCommandsInput()
    {
        InputStream reti = System.in;
        if (Objects.isNull(Configuration.COMMANDS_INPUT) == false)
        {
            File f = new File(Configuration.COMMANDS_INPUT);
            if (f.exists())
            {
                try
                {
                    reti = new FileInputStream(f);
                }
                catch (FileNotFoundException ex)
                {
                    reti = System.in;
                }
            }
        }
        return reti;
    }
    
    /**
     * Gets output stream for command line
     * @return Printable stream for output of command line
     */
    public static PrintStream getCommandsOutput()
    {
        PrintStream reti = System.out;
        if (Objects.isNull(Configuration.COMMANDS_OUTPUT) == false)
        {
            try
            {
            reti = new PrintStream(new FileOutputStream(Configuration.COMMANDS_OUTPUT));
            }
            catch (FileNotFoundException ex)
            {
                reti = System.out;
            }
        }
        return reti;
    }
    
    /**
     * Checks, whether command line should print additional messages or not
     * @return TRUE if additional information messages are expected, FALSE otherwise
     */
    public static boolean commandsVerbose()
    {
        return Configuration.COMMANDS_VERBOSE;
    }
    
    /**
     * Checks, whether command line should print additional messages or not
     * @param verbose New value of verbose flag
     * @return TRUE if additional information messages are expected, FALSE otherwise
     */
    public static boolean commandsVerbose(boolean verbose)
    {
        Configuration.COMMANDS_VERBOSE = verbose;
        return Configuration.commandsVerbose();
    }    
    
    /**
     * Checks, whether command line should print output or not
     * @return TRUE if command line should print output, FALSE otherwise
     */
    public static boolean commandsPrint()
    {
        return Configuration.COMMANDS_PRINT;
    }
    
    /**
     * Checks, whether command line should print output or not
     * @param print New value of print flag
     * @return TRUE if command line should print output, FALSE otherwise
     */
    public static boolean commandsPrint(boolean print)
    {
        Configuration.COMMANDS_PRINT = print;
        return Configuration.commandsPrint();
    }
//</editor-fold>
}
