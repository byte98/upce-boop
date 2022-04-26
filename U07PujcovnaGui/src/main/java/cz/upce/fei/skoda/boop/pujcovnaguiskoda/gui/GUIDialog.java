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

import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class representing dialog which manipulates with map of strings
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class GUIDialog extends Stage
{
    /**
     * Width of window
     */
    private static final int WIDTH = 400;
    
    /**
     * Height of window
     */
    private static final int HEIGHT = 600;
    
    /**
     * Data hold by dialog
     */
    private Map<String, String> data;
    
    /**
     * Viewer of map data
     */
    private TableView<Map.Entry<String, String>> table;
    
    /**
     * Flag, whether data in dialog should be editable
     */
    private boolean editable;
    
    /**
     * Text field with name of attribute
     */
    private TextField textFieldAttribute;
    
    /**
     * Text field with value of attribute
     */
    private TextField textFieldValue;
    
    /**
     * Data type which will be displayed
     */
    private String dType = GUIMain.ANY_TYPE;
    
    private boolean okClicked = false;
    
    /**
     * Creates new dialog
     * @param owner Stage owning this dialog
     * @param title Title of dialog window
     */
    public GUIDialog(Stage owner, String title)
    {
        super.setTitle(title);
        super.initModality(Modality.APPLICATION_MODAL);
        super.setResizable(false);
        this.data = new HashMap<>();
        this.editable = true;
        this.initializeComponents();
    }
    
    /**
     * Creates new dialog
     * @param owner Stage owning this dialog
     * @param title Title of dialog window
     * @param data Data which will be displayed
     * @param editable Flag, whether data should be editable
     * @param type Data type which will be displayed
     */
    public GUIDialog(Stage owner, String title, Map<String, String> data, boolean editable, String type)
    {
        super.setTitle(title);
        super.initModality(Modality.APPLICATION_MODAL);
        super.setResizable(false);
        this.data = data;
        this.editable = editable;
        this.dType = type;
        this.initializeComponents();
    }
    
    /**
     * Initializes components
     */
    private void initializeComponents()
    {
       //<editor-fold defaultstate="collapsed" desc="Content of dialog">
       BorderPane content = new BorderPane();
       super.setScene(new Scene(content, GUIDialog.WIDTH, GUIDialog.HEIGHT));
       //<editor-fold defaultstate="collapsed" desc="Header of dialog">
       VBox header = new VBox();
       header.setPadding(new Insets(10));
       header.setSpacing(10);
       FlowPane type = new FlowPane();
       type.setHgap(10);
       type.getChildren().add(GUIMain.createLabel("Typ", true));
       type.getChildren().add(GUIMain.createLabel(this.dType, false));
       header.getChildren().add(type);
       GridPane attrVal = GUIMain.createGrid();
       header.getChildren().add(attrVal);
       attrVal.add(GUIMain.createLabel("Atribut", false), 0, 0);
       attrVal.add(GUIMain.createLabel("Hodnota", false), 1, 0);
       Button setButton = new Button("Nastavit");
       setButton.setDisable(!this.editable);
       attrVal.add(setButton, 0, 2, 2, 1);
       this.textFieldAttribute = new TextField();
       this.textFieldAttribute.setDisable(!this.editable);
       attrVal.add(this.textFieldAttribute, 0, 1);
       this.textFieldValue = new TextField();
       this.textFieldValue.setDisable(!this.editable);
       attrVal.add(this.textFieldValue, 1, 1);
       content.setTop(header);
       //</editor-fold>
       this.table = this.createTable();
       content.setCenter(this.table);
       //<editor-fold defaultstate="collapsed" desc="Footer of dialog">
       FlowPane buttons = new FlowPane();
       content.setBottom(buttons);
       buttons.setAlignment(Pos.CENTER_RIGHT);
       buttons.setPadding(new Insets(5));
       buttons.setHgap(5);
       Button buttonOK = new Button("OK");
       buttonOK.setDefaultButton(true);
       buttonOK.setOnAction((t) -> {
           this.okClicked = true;
           this.close();
       });
       buttons.getChildren().add(buttonOK);
       Button buttonCancel = new Button("Zrušit");
       buttonCancel.setCancelButton(true);
       buttonCancel.setOnAction((t) -> {
           this.okClicked = false;
           this.close();
       });
       buttons.getChildren().add(buttonCancel);
       setButton.setOnAction((t) -> {
           if (this.textFieldValue.getText().trim().length() > 0 && this.textFieldAttribute.getText().trim().length() > 0)
           {
               if (this.data.containsKey(textFieldAttribute.getText().trim()))
               {
                   this.data.replace(textFieldAttribute.getText().trim(), this.textFieldValue.getText().trim());
               }
               else
               {
                   this.data.put(textFieldAttribute.getText().trim(), this.textFieldValue.getText().trim());
               }
               this.showData();
           }
       });
       //</editor-fold>
       //</editor-fold>
       this.showData();
    }
    
    /**
     * Shows data stored in map
     */
    private void showData()
    {
        this.table.getItems().clear();
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(this.data.entrySet());
        this.table.setItems(items);
    }
    
    /**
     * Creates new view for displaying map
     * @return New table view for map
     */
    private TableView<Map.Entry<String, String>> createTable()
    {
        TableColumn<Map.Entry<String, String>, String> attributeCol = new TableColumn<>("Atribut");
        attributeCol.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) -> new SimpleStringProperty(p.getValue().getKey())
        );
        
        TableColumn<Map.Entry<String, String>, String> valueCol = new TableColumn<>("Hodnota");
        valueCol.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) -> new SimpleStringProperty(p.getValue().getValue())
        );
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(this.data.entrySet());
        TableView<Map.Entry<String, String>> reti = new TableView<>(items);
        reti.getColumns().addAll(attributeCol, valueCol);
        reti.setEditable(this.editable);
        
        attributeCol.prefWidthProperty().bind(reti.widthProperty().multiply(0.5));
        valueCol.prefWidthProperty().bind(reti.widthProperty().multiply(0.5));
                
        reti.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        reti.getSelectionModel().selectedItemProperty().addListener((o) -> {
            if (reti.getSelectionModel().getSelectedItem() != null)
            {
                this.textFieldAttribute.setText(reti.getSelectionModel().getSelectedItem().getKey());
                this.textFieldValue.setText(reti.getSelectionModel().getSelectedItem().getValue());
            }
        });
        
        return reti;
    }
    
    /**
     * Checks, whether OK button has been clicked
     * @return TRUE if OK button has been clicked, FALSE otherwise
     */
    public boolean isOkClicked()
    {
        return this.okClicked;
    }
    
    /**
     * Gets data from dialog
     * @return Entered data into dialog
     */
    public Map<String, String> getData()
    {
        Map<String, String> reti = new HashMap<>();
        for(Map.Entry<String, String> item: this.table.getItems())
        {
            reti.put(item.getKey(), item.getValue());
        }
        return reti;
    }
}
