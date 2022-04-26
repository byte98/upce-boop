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
package cz.upce.fei.skoda.boop.pujcovnaguiskoda.gui;

import cz.upce.fei.skoda.boop.pujcovnaguiskoda.Configuration;
import cz.upce.fei.skoda.boop.pujcovnaguiskoda.generator.Generator;
import cz.upce.fei.skoda.boop.pujcovnaguiskoda.perzistence.FileManipulator;
import cz.upce.fei.skoda.boop.pujcovnaguiskoda.perzistence.ListReader;
import cz.upce.fei.skoda.boop.pujcovnaguiskoda.perzistence.ListWriter;
import cz.upce.fei.skoda.boop.pujcovnaguiskoda.perzistence.TextReader;
import cz.upce.fei.skoda.boop.pujcovnaguiskoda.perzistence.TextWriter;
import cz.upce.fei.skoda.boop.pujcovnaguiskoda.spravce.IManager;
import cz.upce.fei.skoda.boop.pujcovnaguiskoda.spravce.LinkedListManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Class which controls behaviour of GUIMain
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class GUI
{
    /**
     * Main window of application
     */
    private final GUIMain mainWindow;
    
    /**
     * Manager of data
     */
    private final IManager dataManager;
    
    /**
     * Creates new controller of GUI
     */
    public GUI()
    {
        this.dataManager = new LinkedListManager();
        this.mainWindow = new GUIMain(this);
    }
    
    /**
     * Prepares GUI for usage
     */
    public void prepare()
    {
        // pass
    }
    
    /**
     * Starts GUI
     */
    public void start()
    {
        this.mainWindow.setDataTypes(Generator.getAllowedClassNames());
        this.mainWindow.run(null);        
    }
    
    /**
     * Handles click on first button
     * @param source Source of event
     */
    public void firstClicked(GUIMain source)
    {
        this.dataManager.first();
        source.setSelected(this.dataManager.getActual());
    }
    
    /**
     * Handles click on previous button
     * @param source Source of event
     */
    public void previousClicked(GUIMain source)
    {
        this.dataManager.previous();
        source.setSelected(this.dataManager.getActual());
    }
    
    /**
     * Handles click on next button
     * @param source Source of event
     */
    public void nextClicked(GUIMain source)
    {
        this.dataManager.next();
        source.setSelected(this.dataManager.getActual());

    }
    
    /**
     * Handles click on last button
     * @param source Source of event
     */
    public void lastClicked(GUIMain source)
    {
        this.dataManager.last();
        source.setSelected(this.dataManager.getActual());
    }
    
    /**
     * Handles click on view button
     * @param source Source of event
     */
    public void viewClicked(GUIMain source)
    {
        if (Objects.nonNull(this.dataManager.getActual()))
        {
            source.showDialog("Detail prvku seznamu", false, this.dataManager.getActual().getClassName(), this.dataManager.getActual().toMap());
        }
    }
    
    /**
     * Handles click on edit button
     * @param source Source of event
     */
    public void editClicked(GUIMain source)
    {
        if (source.isFilterEnabled())
        {
            source.showDialog("Editace vybraných prvků seznamu", true);
            if (Objects.nonNull(source.getDialogData()))
            {
                this.dataManager.editSelectedElements((t) -> {
                    t.setData(source.getDialogData());
                });
                this.displayAll(source);
            }
        }
    }
    
    /**
     * Handles click on remove button
     * @param source Source of event
     */
    public void removeClicked(GUIMain source)
    {
        if (this.dataManager.remove())
        {
            this.displayAll(source);
            source.setSelected(null);
        }
    }
    
    /**
     * Handles click on delete button
     * @param source Source of event
     */
    public void deleteClicked(GUIMain source)
    {
        this.dataManager.deleteList();
        this.displayAll(source);
        source.setSelected(null);
    }
        
    /**
     * Handles click on generate button
     * @param source Source of event
     * @param number Number of elements which will be generated
     */
    public void generateClicked(GUIMain source, int number)
    {
        this.dataManager.generateData(number);
        this.displayAll(source);
    }
   
    /**
     * Handles click on generate button
     * @param source Source of event
     * @param number Number of elements which will be generated
     * @param type Type of generated elements
     */
    public void generateClicked(GUIMain source, int number, String type)
    {
        this.dataManager.generateData(number, type);
        this.displayAll(source);
    }
    
    /**
     * Handles click on create new element
     * @param source Source of event
     */
    public void createClicked(GUIMain source)
    {
        if (Objects.nonNull(this.dataManager.createNew()))
        {
            this.displayAll(source);
        }
    }
    
    /**
     * Handles click on create new element
     * @param source Source of event
     * @param type Type of element which will be created
     */
    public void createClicked(GUIMain source, String type)
    {
        if (Objects.nonNull(this.dataManager.createNew(type)))
        {
            this.displayAll(source);
        }
    }
            
    /**
     * Handles click on create advanced button
     * @param source Source of event
     * @param type Type of element which should be created
     */
    public void createAdvancedClicked(GUIMain source, String type)
    {
        source.showDialog("Vytvoření nového prvku seznamu", true, type);
        if (Objects.nonNull(source.getDialogData()))
        {
            this.dataManager.createNew(type, (t) -> {
                t.setData(source.getDialogData());
            });
            this.displayAll(source);
        }
    }
    
    /**
     * Handles click on filter enabled check box
     * @param source Source of event
     * @param enabled Flag, whether data should be filtered or not
     */
    public void filterEnabledClicked(GUIMain source, boolean enabled)
    {
        this.displayAll(source);
    }
    
    /**
     * Handles click on filter settings button
     * @param source Source of event
     */
    public void filterSettingsClicked(GUIMain source)
    {
        source.showDialog("Nastavení filtru prvků seznamu", true);
        if (Objects.nonNull(source.getDialogData()))
        {
            this.dataManager.selectElements((t) -> {
                boolean matches = true;
                Map<String, String> data = this.mapToLowerCase(t.toMap());
                Map<String, String> filter = this.mapToLowerCase(source.getDialogData());
                for(String key: filter.keySet())
                {
                    if (data.containsKey(key) == false ||
                            data.get(key).toLowerCase().equals(filter.get(key).toLowerCase()) == false)
                    {
                        matches = false;
                        break;
                    }
                }
                return matches;
            });
            this.displayAll(source);
        }
    }
    
    /**
     * Handles click on delete selected items button
     * @param source Source of event
     */
    public void deleteSelectedClicked(GUIMain source)
    {
        if (source.isFilterEnabled())
        {
            this.dataManager.deleteSelectedElements();
            this.displayAll(source);
        }
    }
    
    /**
     * Changes keys in map into lower case variant
     * @param data Map which keys will be changed
     * @return New map created from original with lower case keys
     */
    private Map<String, String> mapToLowerCase(Map<String, String> data)
    {
        Map<String, String> reti = new HashMap<>();
        for(String key: data.keySet())
        {
            reti.put(key.toLowerCase(), data.get(key));
        }
        return reti;
    }
    
    /**
     * Displays all data to GUI window
     * @param window Window to which all data will be displayed into
     */
    private void displayAll(GUIMain window)
    {
        window.setCounter(this.dataManager.countElements());
        window.setSelectedCounter(this.dataManager.countSelectedElements());
        if (window.isFilterEnabled())
        {
            window.setData(this.dataManager.getSelectedData());
        }
        else
        {
            window.setData(this.dataManager.getAllData());
        }
    }
    
    /**
     * Handles click on load data from text file
     * @param source Source of event
     */
    public void textFileLoadClicked(GUIMain source)
    {
        this.dataManager.loadText(Configuration.getTextReader());
        this.displayAll(source);
    }
    
    /**
     * Handles click on load data from text file
     * @param source Source of event
     * @param path Path to file from which data will be loaded
     */
    public void textFileLoadClicked(GUIMain source, String path)
    {
       TextReader tr = TextReader.getByExtension(FileManipulator.getFileExtension(path));
       if (Objects.nonNull(tr))
       {
        tr.setPath(path);
        this.dataManager.loadText(tr);
       }
       this.displayAll(source);
       
    }
    
    /**
     * Handles click on save data to text file
     * @param source Source of event
     */
    public void textFileSaveClicked(GUIMain source)
    {
        this.dataManager.saveText(Configuration.getTextWriter());
    }
    
     /**
     * Handles click on save data to text file
     * @param source Source of event
     * @param path Path to file to which data will be saved into
     */
    public void textFileSaveClicked(GUIMain source, String path)
    {
        TextWriter tw = TextWriter.getByExtension(FileManipulator.getFileExtension(path));
        if (Objects.nonNull(tw))
        {
            tw.setPath(path);
            this.dataManager.saveText(tw);
        }
    }
    
    
    /**
     * Handles click on load data from binary file
     * @param source Source of event
     */
    public void binaryFileLoadClicked(GUIMain source)
    {
        this.dataManager.loadBinary(Configuration.getBinaryReader());
        this.displayAll(source);
    }
    
    /**
     * Handles click on load data from binary file
     * @param source Source of event
     * @param path Path to file from which data will be loaded
     */
    public void binaryFileLoadClicked(GUIMain source, String path)
    {
        ListReader reader = new ListReader(path);
        this.dataManager.loadBinary(reader);
        this.displayAll(source);
    }
    
    /**
     * Handles click on save data to binary file
     * @param source Source of event
     */
    public void binaryFileSaveClicked(GUIMain source)
    {
        this.dataManager.saveBinary(Configuration.getBinaryWriter());
    }
    
     /**
     * Handles click on save data to binary file
     * @param source Source of event
     * @param path Path to file to which data will be saved into
     */
    public void binaryFileSaveClicked(GUIMain source, String path)
    {
        this.dataManager.saveBinary(new ListWriter(path));
    }
}
