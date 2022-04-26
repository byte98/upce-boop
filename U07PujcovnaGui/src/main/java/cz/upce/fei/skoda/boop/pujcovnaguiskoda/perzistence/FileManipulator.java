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
package cz.upce.fei.skoda.boop.pujcovnaguiskoda.perzistence;

import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 * Class abstracting all utilities which works with files
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract class FileManipulator
{
    /**
     * Path to file
     */
    protected String filePath;
    
    /**
     * Array of valid extension for file manipulator
     */
    protected String extensions[];
    
    /**
     * Creates new manipulator with files
     */
    public FileManipulator()
    {
        this.extensions = new String[0];
    }
    
    /**
     * Creates new manipulator with files
     * @param path Path to file
     */
    public FileManipulator(String path)
    {
        this();
        this.filePath = path;
    }
    
    /**
     * Sets new path for file manipulator
     * @param newPath New path to file for file manipulator
     */
    public void setPath(String newPath)
    {
        this.filePath = newPath;
    }
    
     /**
     * Checks, whether file extension is valid for file manipulator
     * @param ext Extension which will be checked
     * @return TRUE if extension is valid for file manipulator, FALSE otherwise
     */
    public boolean isValidExtension(String ext)
    {
        boolean reti = false;
        if (ext != null && ext.equals("") == false)
        {
            for(String extension: this.extensions)
            {
                if (extension.trim().toLowerCase().equals(ext.trim().toLowerCase()))
                {
                    reti = true;
                    break;
                }
            }
        }
        return reti;
    }
    
    /**
     * Gets path to file
     * @return Path to file
     */
    public String getPath()
    {
        return Paths.get(this.filePath).toAbsolutePath().toString();
    }
    
    /**
     * Gets file extension from file path
     * @param path Path to file
     * @return File extension or empty string if extension
     *         has not been found in file path
     */
    public static String getFileExtension(String path)
    {
        String reti = "";
        if (path.contains("."))
        {
            String[] parts = path.split(Pattern.quote("."));
            if (parts.length > 0)
            {
                reti = parts[parts.length - 1];
            }
        }
        return reti;
    }
}
