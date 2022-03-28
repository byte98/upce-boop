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
package cz.upce.fei.skoda.boop.pujcovnacmdskoda.perzistence;

import cz.upce.fei.skoda.boop.pujcovnacmdskoda.kolekce.SpojovySeznam;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class abstracting all writers to text files
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract class TextWriter extends FileManipulator
{
    /**
     * Array with all available text writers
     */
    private static final TextWriter[] WRITERS = 
    {
        new CSVWriter(""),
        new XMLWriter("")
    };
    
    /**
     * Creates new writer to text file
     * @param path Path to file to which will be written into
     */
    public TextWriter(String path)
    {
        super(path);
    }
    
    /**
     * Writes data into file
     * @param data Data which will be written into file
     */
    public abstract void writeData(SpojovySeznam<Mappable> data);
    
    
    /**
     * Writes string into file
     * @param string String which will be written into file
     */
    protected void writeString(String string) throws IOException
    {
        Files.writeString(Paths.get(this.filePath), string);
    }
    
    /**
     * Writes lines into file
     * @param lines Lines which will be written into file
     */
    protected void writeLines(List<String> lines) throws IOException
    {
        Files.write(Paths.get(this.filePath), lines);
    }
    
    /**
     * Gets text writer by its valid file extension
     * @param ext Extension determining text writer
     * @return Text writer determined by its extension
     *         or NULL if there is not such an writer
     */
    public static TextWriter getByExtension(String ext)
    {
        TextWriter reti = null;
        for (TextWriter tw: TextWriter.WRITERS)
        {
            if (tw.isValidExtension(ext))
            {
                reti = tw;
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
        for(TextWriter tw: TextWriter.WRITERS)
        {
            reti.addAll(Arrays.asList(tw.extensions));
        }
        return reti;
    }
}
