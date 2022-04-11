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
package cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence;

import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.kolekce.SpojovySeznam;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Class abstracting all readers of text files
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract class TextReader extends FileManipulator
{
    /**
     * Array of all available readers
     */
    private static final TextReader[] READERS = {
        new CSVReader(""),
        new XMLReader("")
    };
    
    /**
     * Creates new reader of text file
     * @param path Path to text file
     */
    public TextReader(String path)
    {
        super(path);
    }
    
    /**
     * Loads data from file
     * @return List of loaded data from file
     */
    public abstract SpojovySeznam<Mappable> loadData();
    
    /**
     * Reads whole file into one string
     * @return Content of file in string
     */
    protected String readFile() throws IOException
    {
        return Files.readString(Paths.get(this.filePath));
    }
    
    /**
     * Gets iterator over lines in file
     * @return Iterator over lines in file
     */
    protected Iterator<String> lineIterator() throws FileNotFoundException
    {
        return new Iterator<String>()
        {
            
            /**
             * Stream of data from file
             */
            FileInputStream fileInputStream = new FileInputStream(filePath);
            
            /**
             * Scanner of input stream
             */
            Scanner scanner = new Scanner(this.fileInputStream);
            
            @Override
            public boolean hasNext()
            {
                boolean reti = scanner.hasNextLine();
                if (reti == false) this.scanner.close();
                return reti;
            }
            
            @Override
            public String next()
            {
                return this.scanner.nextLine();
            }
        };
    }
    
    /**
     * Gets text writer by its valid file extension
     * @param ext Extension determining text writer
     * @return Text writer determined by its extension
     *         or NULL if there is not such an writer
     */
    public static TextReader getByExtension(String ext)
    {
        TextReader reti = null;
        for (TextReader tr: TextReader.READERS)
        {
            if (tr.isValidExtension(ext))
            {
                reti = tr;
                break;
            }
        }
        return reti;
    }
 
    /**
     * Gets list of all supported file extensions
     * @return List of all supported extensions
     */
    public static List<String> getSupportedExtensions()
    {
        List<String> reti = new ArrayList<>();
        for(TextReader tr: TextReader.READERS)
        {
            reti.addAll(Arrays.asList(tr.extensions));
        }
        return reti;
    }
}
