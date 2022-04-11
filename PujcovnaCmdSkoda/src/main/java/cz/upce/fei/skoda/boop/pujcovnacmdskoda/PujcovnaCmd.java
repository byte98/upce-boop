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
package cz.upce.fei.skoda.boop.pujcovnacmdskoda;

import cz.upce.fei.skoda.boop.pujcovnacmdskoda.command.Interpreter;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.data.Model;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.kolekce.KolekceException;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.kolekce.LinkSeznam;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.BinaryReader;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.BinaryWriter;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.ListReader;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.ListWriter;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.TextReader;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.TextWriter;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.XMLReader;
import cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence.XMLWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class of whole program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class PujcovnaCmd
{
    /**
     * Path to file which will be used as data storage file
     */
    private static final String FILE = "data.xml";
    
    /**
     * Path to file which will be used as binary data storage file
     */
    private static final String BINARY_FILE = "data.bin";
    
    /**
     * Reader of text data
     */
    public static final TextReader TEXT_READER = new XMLReader(PujcovnaCmd.FILE);
    
    /**
     * Writer of text data
     */
    public static final TextWriter TEXT_WRITER = new XMLWriter(PujcovnaCmd.FILE);
    
    /**
     * Writer of binary data
     */
    public static final BinaryWriter BINARY_WRITER = new ListWriter(PujcovnaCmd.BINARY_FILE);
    
    /**
     * Reader of binary data
     */
    public static final BinaryReader BINARY_READER = new ListReader(PujcovnaCmd.BINARY_FILE);
    
    /**
     * Interpreter of commands from command line
     */
    private final Interpreter interpreter;
    
    /**
     * Flag, whether program should exit
     */
    public static boolean EXIT = false;
    
    /**
     * Flag, whether program should output additional messages
     */
    public static boolean VERBOSE = false;
    
    /**
     * Data model of application
     */
    public static final Model MODEL = new Model();
    
    /**
     * Creates new instance of application
     */
    public PujcovnaCmd()
    {
        this.interpreter = new Interpreter(System.in);
    }
    
    /**
     * Starts program
     */
    public void start()
    {
        while(this.interpreter.hasNext() && PujcovnaCmd.EXIT == false)
        {
            this.interpreter.read();
            if (PujcovnaCmd.EXIT)
            {
                break;
            }
        }
    }
    
    /**
     * Main function of program
     * @param args Arguments of program
     */
    public static void main(String[] args)
    {
        PujcovnaCmd pujcovna = new PujcovnaCmd();
        pujcovna.start();
    }
}