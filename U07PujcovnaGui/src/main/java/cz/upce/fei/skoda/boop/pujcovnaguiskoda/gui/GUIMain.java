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

import cz.upce.fei.skoda.boop.pujcovnaguiskoda.generator.Generator;
import cz.upce.fei.skoda.boop.pujcovnaguiskoda.perzistence.Mappable;
import cz.upce.fei.skoda.boop.pujcovnaguiskoda.perzistence.TextReader;
import cz.upce.fei.skoda.boop.pujcovnaguiskoda.perzistence.TextWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Class which handles main window of GUI
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class GUIMain extends Application
{
    /**
     * Title of main window
     */
    private static final String TITLE = "U07PujcovnaGuiSkoda";
    
    /**
     * String identifying any data type
     */
    public static final String ANY_TYPE = "<jakýkoliv>";
    
    /**
     * Initial width of window
     */
    private static final int WIDTH = 800;
    
    /**
     * Initial height of window
     */
    private static final int HEIGHT = 600;
    
    /**
     * Number of maximal displayed characters of actual element
     */
    private static final int MAX_ACTUAL_LENGTH = 24;
    
    /**
     * Stage hosting all controls on main window
     */
    private Stage primaryStage;
    
    /**
     * Content of window
     */
    private BorderPane content;
    
    /**
     * Viewer of data stored in list
     */
    private ListView<Mappable> listView;
    
    /**
     * Controller of GUI
     */
    private final GUI controller;
    
    /**
     * List of all available data types
     */
    private List<String> dataTypes;
    
    /**
     * Combo boxes in which will be data types displayed
     */
    private List<ComboBox<String>> dataBoxes;
    
    /**
     * List with data which will be displayed
     */
    private List<Mappable> displayedData;
    
    /**
     * Default font for GUI
     */
    public static final Font FONT_DEFAULT = Font.font("Segoe UI Symbol");
    
    /**
     * Bold variant of default font
     */
    public static final Font FONT_BOLD = Font.font(GUIMain.FONT_DEFAULT.getFamily(), FontWeight.BOLD, GUIMain.FONT_DEFAULT.getSize());
    
    /**
     * Label holding information about number of elements in list
     */
    private Label numberLabel;
    
    /**
     * Label holding information about number of selected elements in list
     */
    private Label selectedLabel;
    
    /**
     * Label holding information about actual element
     */
    private Label actualLabel;
    
    /**
     * Flag, whether filter is enabled or not
     */
    private boolean filterEnabled = false;
    
    /**
     * Data from dialog
     */
    private Map<String, String> dialogData = null;
    
    /**
     * Creates new main window of GUI
     */
    public GUIMain()
    {
        this(new GUI());
    }
    
    /**
     * Creates new main window of GUI
     * @param controller Controller of GUI
     */
    public GUIMain(GUI controller)
    {
        this.controller = controller; 
        this.displayedData = new ArrayList<>();
        this.dataTypes = new ArrayList<>();
        this.dataTypes.add(GUIMain.ANY_TYPE);
        this.dataTypes.addAll(Generator.getAllowedClassNames());
        this.dataBoxes = new ArrayList<>();
    }

    /**
     * Runs GUIMain
     * @param args Arguments of program
     */
    public void run(String[] args)
    {
        launch(args);
    }
    
    /**
     * Initialises components of window
     */
    private void initializeComponents()
    {
        //<editor-fold defaultstate="collapsed" desc="List viewer">
        this.listView = new ListView<>();
        this.listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.content.setCenter(this.listView);
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Top menu">
        TabPane topMenu = new TabPane();
        this.content.setTop(topMenu);
        //<editor-fold defaultstate="collapsed" desc="List menu">
        HBox listMenu = this.createHBox();
        topMenu.getTabs().add(this.createTab("SEZNAM", listMenu));
        //<editor-fold defaultstate="collapsed" desc="Browse submenu">
        GridPane browseMenu = GUIMain.createGrid();
        listMenu.getChildren().add(browseMenu);
        browseMenu.add(GUIMain.createLabel(" PROCHÁZENÍ", true), 0, 0, 2, 1);
        browseMenu.add(GUIMain.createButton(" První", (ActionEvent t) -> {
            controller.firstClicked(this);
        }), 0, 2);
        browseMenu.add(GUIMain.createButton(" Předchozí", (ActionEvent t) -> {
            controller.previousClicked(this);
        }), 0, 1);
        browseMenu.add(GUIMain.createButton(" Následující", (ActionEvent t) -> {
            controller.nextClicked(this);
        }), 1, 1);
        browseMenu.add(GUIMain.createButton(" Poslední", (ActionEvent t) -> {
            controller.lastClicked(this);
        }), 1, 2);
        //</editor-fold>
        listMenu.getChildren().add(new Separator(Orientation.VERTICAL));
        //<editor-fold defaultstate="collapsed" desc="Actions submenu">
        GridPane actionsMenu = GUIMain.createGrid();
        listMenu.getChildren().add(actionsMenu);
        actionsMenu.add(GUIMain.createLabel(" AKCE", true), 0, 0);
        actionsMenu.add(GUIMain.createButton(" Zobrazit", (t) -> {
            controller.viewClicked(this);
        }), 0, 1);
        actionsMenu.add(GUIMain.createButton(" Odebrat", (t) -> {
            controller.removeClicked(this);
        }), 0, 2);
        actionsMenu.add(GUIMain.createButton(" Smazat", (t) -> {
            controller.deleteClicked(this);
        }), 0, 3);
        //</editor-fold>
        listMenu.getChildren().add(new Separator(Orientation.VERTICAL));
        //<editor-fold defaultstate="collapsed" desc="List data submenu">
        GridPane listDataMenu = GUIMain.createGrid();
        listMenu.getChildren().add(listDataMenu);
        listDataMenu.add(GUIMain.createLabel(" ÚDAJE O SEZNAMU", true), 0, 0, 2, 1);
        listDataMenu.add(this.createLabel("Počet prvků v seznamu"), 0, 1);
        listDataMenu.add(this.createLabel("Počet vybraných prvků seznamu"), 0, 2);
        listDataMenu.add(this.createLabel("Aktuální prvek"), 0, 3);
        this.numberLabel = this.createLabel("0");
        listDataMenu.add(this.numberLabel, 1, 1);
        this.selectedLabel = this.createLabel("0");
        listDataMenu.add(this.selectedLabel, 1, 2);
        this.actualLabel = this.createLabel("<ŽÁDNÝ>");
        listDataMenu.add(this.actualLabel, 1, 3);
        //</editor-fold>
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Data menu">
        HBox dataMenu = this.createHBox();
        topMenu.getTabs().add(this.createTab("DATA", dataMenu));    
        //<editor-fold defaultstate="collapsed" desc="Generate sub menu">
        GridPane generateMenu = GUIMain.createGrid();
        generateMenu.add(GUIMain.createLabel(" GENEROVÁNÍ DAT", true), 0, 0, 2, 1);
        generateMenu.add(this.createLabel(" Počet"), 0, 1);
        Spinner<Integer> numberValue = new Spinner<>();
        numberValue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
        generateMenu.add(numberValue, 1, 1);
        dataMenu.getChildren().add(generateMenu);
        generateMenu.add(this.createLabel(" Typ"), 0, 2);
        ComboBox<String> typeValue = this.createTypeComboBox();
        generateMenu.add(typeValue, 1, 2);
        generateMenu.add(GUIMain.createButton(" Generovat", (t) -> {
            if (typeValue.getValue().equals(GUIMain.ANY_TYPE))
            {
                controller.generateClicked(this, numberValue.getValue());
            }
            else
            {
                controller.generateClicked(this, numberValue.getValue(), typeValue.getValue());
            }
        }), 0, 3, 2, 1);
        //</editor-fold>
        dataMenu.getChildren().add(new Separator(Orientation.VERTICAL));     
        //<editor-fold defaultstate="collapsed" desc="Create submenu">
        GridPane createMenu = GUIMain.createGrid();
        dataMenu.getChildren().add(createMenu);
        createMenu.add(GUIMain.createLabel(" VYTVÁŘENÍ DAT", true), 0, 0, 2, 1);   
        createMenu.add(GUIMain.createButton(" Vytvořit náhodný prvek", (t) -> {
            controller.createClicked(this);
        }), 0, 1, 2, 1);
        createMenu.add(this.createLabel(" Typ"), 0, 2);
        ComboBox<String> createType = this.createTypeComboBox();
        createMenu.add(createType, 1, 2);
        createMenu.add(GUIMain.createButton(" Vytvořit", (t) -> {
            if (createType.getValue().equals(GUIMain.ANY_TYPE))
            {
                controller.createClicked(this);
            }
            else
            {
                controller.createClicked(this, createType.getValue());
            }
        }), 0, 3);
        createMenu.add(GUIMain.createButton(" Pokročilé", (t) -> {
            if (createType.getValue().equals(GUIMain.ANY_TYPE) == false)
            {
                controller.createAdvancedClicked(this, createType.getValue());
            }
        }), 1, 3);
        //</editor-fold>
        dataMenu.getChildren().add(new Separator(Orientation.VERTICAL));
        //<editor-fold defaultstate="collapsed" desc="Filter submenu">
        GridPane filterMenu = GUIMain.createGrid();
        dataMenu.getChildren().add(filterMenu);
        filterMenu.add(GUIMain.createLabel(" FILTROVÁNÍ DAT", true), 0, 0, 2, 1);
        filterMenu.add(this.createLabel("Filtrovat data"), 0, 1);
        CheckBox filterEnabler = new CheckBox();
        filterEnabler.setOnAction((t) -> {
            this.filterEnabled = filterEnabler.isSelected();
            controller.filterEnabledClicked(this, this.filterEnabled);
        });
        filterMenu.add(filterEnabler, 1, 1);
        filterMenu.add(GUIMain.createButton(" Nastavení filtru", (t) -> {
            controller.filterSettingsClicked(this);
        }), 0, 2, 2, 1);
        filterMenu.add(GUIMain.createButton(" Upravit vybrané", (t) -> {
            controller.editClicked(this);
        }), 0, 3);
        filterMenu.add(GUIMain.createButton(" Odebrat vybrané", (t) -> {
            controller.deleteSelectedClicked(this);
        }), 1, 3);
        //</editor-fold>
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="File menu">
        HBox fileMenu = this.createHBox();
        topMenu.getTabs().add(this.createTab("SOUBOR", fileMenu));
        //<editor-fold defaultstate="collapsed" desc="Text files submenu">
        GridPane textFileMenu = GUIMain.createGrid();
        fileMenu.getChildren().add(textFileMenu);
        textFileMenu.add(this.createLabel(" TEXTOVÉ SOUBORY"), 0, 0, 2, 1);
        textFileMenu.add(GUIMain.createButton(" Načíst", (t) -> {
            controller.textFileLoadClicked(this);
        }), 0, 1);
        textFileMenu.add(GUIMain.createButton(" Načíst ze souboru", (t) -> {
            FileChooser fileChooser = new FileChooser();
            for(String ext: TextReader.getSupportedExtensions())
            {
                fileChooser.getExtensionFilters().add(new ExtensionFilter("Soubor " + ext.toUpperCase(), "*." + ext));
            }
            File f = fileChooser.showOpenDialog(this.primaryStage);
            if (Objects.nonNull(f))
            {
                controller.textFileLoadClicked(this, f.getAbsolutePath());
            }            
        }), 0, 2);
        textFileMenu.add(GUIMain.createButton(" Uložit", (t) -> {
            controller.textFileSaveClicked(this);
        }), 1, 1);
        textFileMenu.add(GUIMain.createButton(" Uložit do souboru", (t) -> {
            FileChooser fileChooser = new FileChooser();
            for(String ext: TextWriter.getSupportedExtensions())
            {
                fileChooser.getExtensionFilters().add(new ExtensionFilter("Soubor " + ext.toUpperCase(), "*." + ext));
            }
            File f = fileChooser.showSaveDialog(this.primaryStage);
            if (Objects.nonNull(f))
            {
                controller.textFileSaveClicked(this, f.getAbsolutePath());
            }  
        }), 1, 2);
        //</editor-fold>
        fileMenu.getChildren().add(new Separator(Orientation.VERTICAL));
        //<editor-fold defaultstate="collapsed" desc="Binary files submenu">
        GridPane binaryMenu = GUIMain.createGrid();
        fileMenu.getChildren().add(binaryMenu);
        binaryMenu.add(this.createLabel(" BINÁRNÍ SOUBORY"), 0, 0, 2, 1);
        binaryMenu.add(GUIMain.createButton(" Načíst", (t) -> {
            controller.binaryFileLoadClicked(this);
        }), 0, 1);
        binaryMenu.add(GUIMain.createButton(" Načíst ze souboru", (t) -> {
            FileChooser fileChooser = new FileChooser();
            File f = fileChooser.showOpenDialog(this.primaryStage);
            if (Objects.nonNull(f))
            {
                controller.binaryFileLoadClicked(this, f.getAbsolutePath());
            }            
        }), 0, 2);
        binaryMenu.add(GUIMain.createButton(" Uložit", (t) -> {
            controller.binaryFileSaveClicked(this);
        }), 1, 1);
        binaryMenu.add(GUIMain.createButton(" Uložit do souboru", (t) -> {
            FileChooser fileChooser = new FileChooser();
            File f = fileChooser.showSaveDialog(this.primaryStage);
            if (Objects.nonNull(f))
            {
                controller.binaryFileSaveClicked(this, f.getAbsolutePath());
            }  
        }), 1, 2);
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        this.showDataTypes();
    }    
    //<editor-fold defaultstate="collapsed" desc="Creating components">
    /**
     * Creates new button
     * @param text Text which will be displayed in button
     * @param eh Handler of buttons 'on action' event
     * @return Newly created button
     */
    public static Button createButton(String text, EventHandler<ActionEvent> eh)
    {
        Button reti = new Button();
        reti.setText(text);
        reti.setFont(GUIMain.FONT_DEFAULT);
        reti.setOnAction(eh);
        return reti;
    }
    
    /**
     * Creates new grid pane with defined gaps
     * @return Newly created grid pane with defined gaps
     */
    public static GridPane createGrid()
    {
        GridPane reti = new GridPane();
        reti.setHgap(10);
        reti.setVgap(5);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS) ; // allow column to grow
        cc.setFillWidth(true); // ask nodes to fill space for column
        // other settings as needed...
        reti.getColumnConstraints().add(cc);
        
        return reti;
    }
    
    /**
     * Creates new label
     * @param text Text which will be displayed in label
     * @return New label with defined text
     */
    private Label createLabel(String text)
    {
        return GUIMain.createLabel(text, false);
    }
    
    /**
     * Creates new label
     * @param text Text which will be displayed in label
     * @param bold Flag, whether bold font variant should be used
     * @return New label with defined text
     */
    public static Label createLabel(String text, boolean bold)
    {
        Label reti = new Label(text);
        if (bold)
            reti.setFont(GUIMain.FONT_BOLD);
        else
            reti.setFont(GUIMain.FONT_DEFAULT);
        return reti;
    }
    
    /**
     * Creates new tab for tab pane
     * @param name Name of tab
     * @param content Content of tab
     * @return Newly created tab for tab pane
     */
    private Tab createTab(String name, Pane content)
    {
        Tab reti = new Tab();
        reti.setText(name);
        reti.setContent(content);
        reti.setClosable(false);
        return reti;
    }
    
    /**
     * Creates new horizontal box
     * @return Newly created horizontal box with default settings
     */
    private HBox createHBox()
    {
        HBox reti = new HBox();
        reti.setSpacing(20);
        reti.setPadding(new Insets(5));
        return reti;
    }
    
    /**
     * Creates combo box with all available data types
     * @return Combo box with all available data types
     */
    private ComboBox<String> createTypeComboBox()
    {
        ComboBox<String> reti = new ComboBox<>();
        this.dataBoxes.add(reti);
        reti.getSelectionModel().select(0);
        return reti;
    }
    
    //</editor-fold>
    
    @Override
    public void start(Stage stage) throws Exception
    {
        this.primaryStage = stage;
        this.content = new BorderPane();
        Scene scene = new Scene(this.content, GUIMain.WIDTH, GUIMain.HEIGHT);
        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle(GUIMain.TITLE);
        this.initializeComponents();
        this.primaryStage.show();
        this.showDataTypes();
        this.showData();
    }
    
    
    /**
     * Sets visible data types
     * @param types Data types which will be visible to user
     */
    public void setDataTypes(List<String> types)
    {
        this.dataTypes.clear();
        this.dataTypes.add(GUIMain.ANY_TYPE);
        this.dataTypes.addAll(types);
        this.showDataTypes();
    }
    
    /**
     * Displays data types in all components
     */
    private void showDataTypes()
    {
        for(ComboBox<String> cb: this.dataBoxes)
        {
            cb.setItems(FXCollections.observableList(this.dataTypes));
            cb.getSelectionModel().select(GUIMain.ANY_TYPE);
        }
    }
    
    /**
     * Sets visible data to user
     * @param data Data which will be displayed to user
     */
    public void setData(Mappable[] data)
    {
        this.displayedData.clear();
        for(Mappable m: data)
        {
            this.displayedData.add(m);
        }
        this.showData();
    }
    
    /**
     * Displays data to user
     */
    private void showData()
    {
        if (this.listView != null)
        {
            this.listView.setItems(FXCollections.observableList(this.displayedData));
        }
    }
    
    /**
     * Sets data which will be displayed as selected to user
     * @param item Item which will be displayed as selected to user
     */
    public void setSelected(Mappable item)
    {
        if(Objects.nonNull(item))
        {
            this.listView.getSelectionModel().clearSelection();
            this.listView.getSelectionModel().select(item);
            this.listView.scrollTo(item);
            this.actualLabel.setText(String.format("%s...", item.toString().substring(0, Math.min(item.toString().length(), GUIMain.MAX_ACTUAL_LENGTH))));
        }
        else
        {
            this.actualLabel.setText("<ŽÁDNÝ>");
        }
    }
    /**
     * Shows dialog
     * @param title Title of dialog window
     * @param editable Flag, whether data in dialog should be editable or not
     */
    public void showDialog(String title, boolean editable)
    {
       this.showDialog(title, editable, GUIMain.ANY_TYPE, new HashMap<>());
    }
    
    /**
     * Shows dialog
     * @param title Title of dialog window
     * @param editable Flag, whether data in dialog should be editable or not
     * @param type Type of data which will be displayed in dialog
     */
    public void showDialog(String title, boolean editable, String type)
    {
        this.showDialog(title, editable, type, new HashMap<>());
    }
    
    /**
     * Shows dialog
     * @param title Title of dialog window
     * @param editable Flag, whether data in dialog should be editable or not
     * @param type Type of data which will be displayed in dialog
     * @param data Data which will be displayed in dialog
     */
    public void showDialog(String title, boolean editable, String type, Map<String, String> data)
    {
        GUIDialog dialog = new GUIDialog(this.primaryStage, title, data, editable, type);
        dialog.showAndWait();
        if (dialog.isOkClicked())
        {
            this.dialogData = dialog.getData();
        }
        else
        {
            this.dialogData = null;
        }
    }
    
    /**
     * Gets data from dialog
     * @return Map with attribute value pairs from dialog
     */
    public Map<String, String> getDialogData()
    {
        return this.dialogData;
    }
    
    /**
     * Sets counter of all elements
     * @param number Number which will be displayed as number of all elements
     */
    public void setCounter(int number)
    {
        this.numberLabel.setText(String.valueOf(number));
    }
    
    /**
     * Sets counter of selected elements
     * @param number Number which will be displayed
     *               as number of selected elements
     */
    public void setSelectedCounter(int number)
    {
        this.selectedLabel.setText(String.valueOf(number));
    }
    
    /**
     * Checks, whether element filtering is enabled or not
     * @return TRUE if element filtering is enabled, FALSE otherwise
     */
    public boolean isFilterEnabled()
    {
        return this.filterEnabled;
    }
}
