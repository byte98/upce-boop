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

import cz.upce.fei.skoda.boop.pujcovnacmdskoda.PujcovnaCmd;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing run command
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Run extends Command
{
    /**
     * Message informing about not existence of file
     */    
    private static final String MSG_FILENOTFOUND
            = "CHYBA: Nelze spustit skript ze zadane cesty! Soubor '%s' neexistuje.";
    
    /**
     * Message informing about script finish
     */
    private static final String MSG_SUCCESS
            = "Skript %s skoncil po %d prikazech";
    
    /**
     * Creates new handler for run command
     */
    public Run()
    {
        this.aliases = new String[]{"exec", "run", "spust", "skript"};
        this.minimumArguments = 1;
        this.maximumArguments = 1;
    }

    @Override
    public String getHelp()
    {
        return """
               Spusti skript ze souboru
                - argument: Cesta k souboru
               """;
    }

    @Override
    public String execute(String[] arguments)
    {
        this.error = false;
        String reti = "";
        File file = new File(arguments[0]);
        try
        {
            InputStream inputStream = new FileInputStream(file);
            Interpreter interpreter = new Interpreter(inputStream, Interpreter.getOutput());
            while(interpreter.hasNext())
            {
                interpreter.read();
            }
            if (PujcovnaCmd.VERBOSE)
            {
                reti = String.format(Run.MSG_SUCCESS, arguments[0], interpreter.getCommandCounter());
            }
            inputStream.close();
        }
        catch (IOException ex)
        {
            reti = String.format(Run.MSG_FILENOTFOUND, Paths.get(arguments[0]).toAbsolutePath().toString());
            this.error = true;
        }
        return reti;
               
    }
}