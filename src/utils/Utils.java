/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import model.Employee;

/**
 *
 * @author Omar
 */
public class Utils {
    /**
     * Display error dialog.
     *
     * @param header of the dialog
     * @param msg of the dialog
     */
    public static void showErrorDialog(String header, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                msg, ButtonType.OK);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(header);
        alert.dialogPaneProperty().getValue().getStylesheets()
                .add(Utils.class.getResource("/css/style.css").toExternalForm());
        //Styling Ok button like the rest of app buttons
        Node okButton = alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.getStyleClass().add("flat-button");
        alert.showAndWait();
    }
    
    /**
     * Display Exception error Dialog
     *
     * @param header the title
     * @param msg the content message
     * @param exception the exception that happened
     */
    public static void showExceptionDialog(String header, String msg, Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.dialogPaneProperty().getValue().getStylesheets()
                .add(Utils.class.getResource("/css/style.css").toExternalForm());

        //Styling Ok button like the rest of app buttons
        Node okButton = alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.getStyleClass().add("flat-button");
        
        
        
        //Create expandable exception
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();
        
        Label label = new Label("The exception stacktrace was: ");
        
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxHeight(Double.MAX_VALUE);
        textArea.setMaxWidth(Double.MAX_VALUE);
        
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        
        //set expandable exception int dialog pane
        alert.getDialogPane().setExpandableContent(expContent);
        
        alert.showAndWait();
        
    }
    
    public static Optional<Map<String,String>> showSettingsDialog(String currentSetting, int currentSpin){
        Dialog<Map<String,String>> dialog = new Dialog<>();
        dialog.setTitle("Server Settings");
        dialog.setHeaderText("SMTP Server Setting");
        dialog.dialogPaneProperty().getValue().getStylesheets()
                .add(Utils.class.getResource("/css/style.css").toExternalForm());
        //dialog.dialogPaneProperty().getValue().getStyleClass().add("default");
        
        
        //set the icon
        dialog.setGraphic(new ImageView(Constants.SETTING_DIALOG_ICON));
        
        //set the button types
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
        
        //create the current setting label
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,150,10,10));
        
        TextField tfNewServer = new TextField();
        tfNewServer.setPromptText("Set the new server...");
        tfNewServer.getStyleClass().add("textField");
        
        Spinner spTimer = new Spinner(0, Integer.MAX_VALUE, 0, 1);
        spTimer.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, currentSpin));
        spTimer.setEditable(true);
        spTimer.getStyleClass().add("textField");
        spTimer.setTooltip(new Tooltip("Set the duration between sending mails in minutes"));
                
        grid.add(new Label("New SMTP Server "), 0, 0);
        grid.add(tfNewServer,1,0);
        grid.add(new Label("Current Server: "), 0,1);
        grid.add(new Label(currentSetting),1,1);
        grid.add(new Label("Set Timer Spin: "),0,2);
        grid.add(spTimer, 1,2);
        grid.add(new Label(" Minutes"), 2,2);
        
        
        //Enable/Disable apply button depending on whether a new setting was entered
        Node applyButton = dialog.getDialogPane().lookupButton(ButtonType.APPLY);
        Node cancelButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        
        applyButton.setDisable(true);
        cancelButton.setDisable(false);
        
        applyButton.getStyleClass().add("flat-button");
        cancelButton.getStyleClass().add("flat-button");
        
        
        
        //Do some validation (disable it if empty textfield of new value)
        tfNewServer.textProperty().addListener((observable, oldValue, newValue)->{
            applyButton.setDisable(newValue.trim().isEmpty());
        });
        
        dialog.getDialogPane().setContent(grid);
        
        //Request focus on the new setting field by default
        Platform.runLater(()-> tfNewServer.requestFocus());
        
        //Convert the result to a string when the apply button is clicked
        dialog.setResultConverter((ButtonType dialogButton) -> {
            if(dialogButton == ButtonType.APPLY){
                Map res = new HashMap();
                res.put(Constants.HOST_KEY, tfNewServer.getText());
                res.put(Constants.SPIN_KEY, String.valueOf(spTimer.getValue()));
                return res;
            }
            return null;
        
        });
        //return the result
        return dialog.showAndWait();
        
    }
    
    public static Optional<Employee> showEmployeesDialog(ObservableList<Employee> list){
        Dialog<Employee> dialog = new Dialog<>();
        dialog.setTitle("Employee Chooser...");
        dialog.setHeaderText("Employee Timesheet chooser...");
        dialog.dialogPaneProperty().getValue().getStylesheets()
                .add(Utils.class.getResource("/css/style.css").toExternalForm());
        //dialog.dialogPaneProperty().getValue().getStyleClass().add("default");
        
        
        //set the icon
        dialog.setGraphic(new ImageView("/images/" + Constants.EMP_DUMMY_PHOTO));
        
        //set the button types
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        //create the current setting label
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,150,10,10));
        
        ComboBox<Employee> cmbEmployees = new ComboBox(list);
                
        grid.add(new Label("Choose Employee: "), 0, 0);
        grid.add(cmbEmployees,1,0);        
        
        //Enable/Disable apply button depending on whether a new setting was entered
        Node applyButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        Node cancelButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);

        applyButton.getStyleClass().add("flat-button");
        cancelButton.getStyleClass().add("flat-button");
        
        dialog.getDialogPane().setContent(grid);
        
        //Request focus on the new setting field by default
        Platform.runLater(()-> cmbEmployees.requestFocus());
        
        //Convert the result to a string when the apply button is clicked
        dialog.setResultConverter((ButtonType dialogButton) -> {
            if(dialogButton == ButtonType.OK){
                Employee res = cmbEmployees.getValue();
                return res;
            }
            return null;
        
        });
        //return the result
        return dialog.showAndWait();
        
    }
    
    public static boolean validatePhoto(Path path) {
        File temp = path.toFile();
        return temp.isFile() && temp.exists();
    }
    
    public static String getExtension(String fileName){    
        return fileName.substring(fileName.indexOf("."));
    }
    
}
