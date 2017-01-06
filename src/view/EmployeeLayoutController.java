/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.cEmployee;
import ehrmanager.EHRManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Employee;
import utils.Constants;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author Omar
 */
public class EmployeeLayoutController implements Initializable {

    public boolean saveClicked = false;

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfDepartment;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfNationalID;
    @FXML
    private TextField tfInsuranceNo;
    @FXML
    private DatePicker dpHiringDate;
    @FXML
    private TextField tfBasicSalary;
    @FXML
    private TextField tfGrossSalary;
    @FXML
    private TextField tfInsDeduction;
    @FXML
    private TextField tfHealthInsDeduction;
    @FXML
    private TextField tfOtherDeduction;
    @FXML
    private ImageView imgEmpPhoto;
    @FXML
    private Label lblNetSalary;
    

    private Path copiedPhotoPath;
    private File selectedPhoto;
    private String empPhotoName;
    private Stage stage;
    private cEmployee mCEmployee;
    private Employee editableEmployee;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //create the object of cClient controller to communicate with DB
        mCEmployee = new cEmployee();
    }

    /**
     * Set the stage of the current window, so we can close it or hide and show
     * again for various operation in the window.
     *
     * @param stage the stage of current window
     */
    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Show the client to be updated in controls
     *
     * @param employee to be updated
     */
    public void showEditableEmployee(Employee employee) {
        editableEmployee = employee;
        populateWindow(employee);
    }

    /**
     * Event Handler of buttons for save (save and quit) and (save and add new)
     * according to the button clicked, the operation will be performed close
     * the window or clear for new input data.
     *
     * @param event
     */
    @FXML
    public void saveHandler(ActionEvent event) {

        Button btnSave = (Button) event.getSource();
        if (btnSave.getText().equalsIgnoreCase("save & quit")) {
            if (saveEmployee()) {
                stage.close();
                saveClicked = true;
            }
        } else if (btnSave.getText().equalsIgnoreCase("save & add new")) {
            if (saveEmployee()) {
                clearInputs();
                stage.hide();
                saveClicked = true;
                stage.show();
            }
        }

    }

    @FXML
    public void changePhotoHandler(MouseEvent event) {
        FileChooser browse = new FileChooser();
        browse.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Image Files", "jpg", "png", "pmp"));
        browse.setTitle("Employee Photo Browser...");

        File selectedFile = browse.showOpenDialog(EHRManager.mainStage);
        if (selectedFile != null && selectedFile.isFile() && selectedFile.exists()) {
            try (FileInputStream in = new FileInputStream(selectedFile)) {
                imgEmpPhoto.setImage(new Image(in));
                selectedPhoto = selectedFile;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EmployeeLayoutController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EmployeeLayoutController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Event Handler of Cancel button
     *
     * @param event
     */
    @FXML
    public void cancelHandler(ActionEvent event) {
        saveClicked = false;
        stage.close();
    }

    @FXML
    public void lostFocusHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
            if (validateNumbers()) {
                double gross = Double.valueOf(tfGrossSalary.getText());
                double insDeduction = Double.valueOf(tfInsDeduction.getText());
                double otherDed = Double.valueOf(tfOtherDeduction.getText());

                double netSalary = gross - (insDeduction + otherDed);

                lblNetSalary.setText("(Net Salary = " + netSalary + " EGP)");
            } else {
                Utils.showErrorDialog("Wrong Data Format...", "Enter money in numbers only like 00.00");
            }

        }
    }

    /**
     * Copy the employee photo file to the resources folder of photos.
     */
    private Path copyPhoto() {
        try {
            Path source = selectedPhoto.toPath();
            Path dst = Constants.RESOURCE_PATH.resolve(source.getFileName());

            return Files.copy(source, dst, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeLayoutController.class.getName())
                    .log(Level.SEVERE, null, ex);
            Utils.showExceptionDialog("Exception in File Handling...",
                    "There is exception takes place while handling the file.\n"
                    + " See below for more details: ", ex);
        }
        return null;
    }

    /**
     * Build the client object from the input controls. It returns optional
     * object of type client with the client object created from input data or
     * empty optional object in case of missing inputs.
     *
     * @return Optional object of type client.
     */
    private Optional<Employee> buildEmployee() {

        if (validate()) {
            
            copiedPhotoPath = copyPhoto();
            
            int id = (editableEmployee != null) ? editableEmployee.getID() : 0;
            String name = tfName.getText();
            String dept = tfDepartment.getText();
            String title = tfTitle.getText();
            String nationalID = tfNationalID.getText();
            String InsNo = tfInsuranceNo.getText();
            empPhotoName = (selectedPhoto == null)
                    ? Constants.EMP_DUMMY_PHOTO
                    : (id == 0)
                            ? String.valueOf(Constants.lastID + 1) + getExtension(copiedPhotoPath)
                            : String.valueOf(id) + getExtension(copiedPhotoPath);

            LocalDate startDate = (dpHiringDate.getValue() == null)
                    ? LocalDate.now() : dpHiringDate.getValue();
            double basicSalary = (Double.valueOf(tfGrossSalary.getText()) < 0)
                    ? 0.00 : Double.valueOf(tfGrossSalary.getText());
            double grossSalary = (Double.valueOf(tfGrossSalary.getText()) < 0)
                    ? 0.00 : Double.valueOf(tfGrossSalary.getText());
            double insDeduction = (Double.valueOf(tfInsDeduction.getText()) < 0)
                    ? 0.00 : Double.valueOf(tfInsDeduction.getText());
            double healthInsDeduction = (Double.valueOf(tfInsDeduction.getText()) < 0)
                    ? 0.00 : Double.valueOf(tfInsDeduction.getText());
            double otherDeduction = (Double.valueOf(tfOtherDeduction.getText()) < 0)
                    ? 0.00 : Double.valueOf(tfOtherDeduction.getText());

            Employee emp = new Employee();
            emp.setID(id);
            emp.setName(name);
            emp.setDepartment(dept);
            emp.setTitle(title);
            emp.setNationalID(nationalID);
            emp.setInsuranceNo(InsNo);
            emp.setPhoto(empPhotoName);
            emp.setBasicSalary(basicSalary);
            emp.setGrossSalary(grossSalary);
            emp.setInsDeduction(insDeduction);
            emp.setHealthInsDeduction(healthInsDeduction);
            emp.setOtherDeduction(otherDeduction);
            emp.setHiringDate(startDate);

            return Optional.of(emp);
        } else {
            return Optional.empty();
        }

    }

    /**
     * Save the client to the DB and alert user with success or missing required
     * fields
     */
    private boolean saveEmployee() {

        //create the optional object of client from the method  
        Optional<Employee> optEmployee = buildEmployee();
        //check if the client is present, save to db and exit,
        //otherwise (the optional is empty) show alert message for the required fields.
        if (!optEmployee.isPresent()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "All fields are required!", ButtonType.OK);
            alert.setHeaderText("Required Fields Error...");
            alert.showAndWait();
            return false;
        }
        if (editableEmployee == null) {
            optEmployee.ifPresent((emp) -> {
                Constants.lastID = mCEmployee.insert(emp);
                Path dst = Constants.RESOURCE_PATH;
                try { 
                    Files.copy(copiedPhotoPath,
                            dst.resolve(Constants.lastID + getExtension(copiedPhotoPath)),
                            StandardCopyOption.REPLACE_EXISTING);
                    boolean isDeleted = Files.deleteIfExists(copiedPhotoPath);
                    System.out.println("Does file is Deleted? " + isDeleted);
                } catch (IOException ex) {
                    Logger.getLogger(EmployeeLayoutController.class.getName()).log(Level.SEVERE, null, ex);
                }
   
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "The employee saved successfully!", ButtonType.OK);
                alert.setHeaderText("Save Employee...");
                alert.showAndWait();

            });
            return true;
        } else {
            optEmployee.ifPresent((emp) -> {
                try {
                    mCEmployee.update(emp);
                    Path dst = Constants.RESOURCE_PATH;
                    Files.copy(copiedPhotoPath, 
                            dst.resolve(emp.getID() + getExtension(copiedPhotoPath)),
                            StandardCopyOption.REPLACE_EXISTING);
                    
                    boolean isDeleted = Files.deleteIfExists(copiedPhotoPath);
                    System.out.println("Does file is Deleted? " + isDeleted);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "The employee saved successfully!", ButtonType.OK);
                    alert.setHeaderText("Save Employee...");
                    alert.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(EmployeeLayoutController.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            });
            return true;
        }
    }
    
    private String getExtension(Path fileName){
        String fName = fileName.getFileName().toString();
        String ext = fName.substring(fName.indexOf("."), fName.length());
        
        return ext;
    }

    /**
     * Validate the inputs for required fields
     *
     * @return
     */
    private boolean validate() {
        if (tfName.getText().isEmpty() || tfDepartment.getText().isEmpty()
                || tfTitle.getText().isEmpty() || dpHiringDate.getValue() == null
                || tfInsuranceNo.getText().isEmpty() || !validateNumbers()) {

            return false;
        }

        return true;
    }

    /**
     * check that the input in text field is any number of digits before dot and
     * 2 digits only after it.
     *
     * @return true if it matches numbers with format 0.00, otherwise, returns
     * false
     */
    private boolean validateNumbers() {

        if (tfGrossSalary.getText().isEmpty()
                || !tfGrossSalary.getText().matches("[0-9]+.[0-9]{2}")) {
            return false;
        }
        if (tfInsDeduction.getText().isEmpty()
                || !tfInsDeduction.getText().matches("[0-9]+.[0-9]{2}")) {
            return false;
        }
        if (tfOtherDeduction.getText().isEmpty()
                || !tfOtherDeduction.getText().matches("[0-9]+.[0-9]{2}")) {
            return false;
        }

        return true;
    }

    /**
     * Clear all input controls to add new client data
     */
    private void clearInputs() {
        tfName.setText("");
        tfDepartment.setText("");
        tfTitle.setText("");
        dpHiringDate.setValue(LocalDate.now());
        tfGrossSalary.setText("");
        tfInsDeduction.setText("");
        tfInsuranceNo.setText("");
        tfOtherDeduction.setText("");
        imgEmpPhoto.setImage(Constants.EMP_ICON);
    }

    /**
     * Populate the window controls with employee data
     *
     * @param employee to be shown
     */
    private void populateWindow(Employee employee) {
        tfName.setText(employee.getName());
        tfDepartment.setText(employee.getDepartment());
        tfTitle.setText(employee.getTitle());
        dpHiringDate.setValue(employee.getStartDate());
        tfGrossSalary.setText(String.valueOf(employee.getGrossSalary()));
        tfInsuranceNo.setText(employee.getInsuranceNum());
        tfInsDeduction.setText(String.valueOf(employee.getInsDeduction()));
        tfOtherDeduction.setText(String.valueOf(employee.getOtherDeduction()));
        double netSalary = employee.getGrossSalary()
                - (employee.getInsDeduction() + employee.getOtherDeduction());
        lblNetSalary.setText("; Net Salary = " + String.valueOf(netSalary) + " EGP");
        Path path = Paths.get(Constants.RESOURCE_PATH.toString(), employee.getPhoto());
        if (utils.Utils.validatePhoto(path)) {
            try {
                imgEmpPhoto.setImage(new Image(new FileInputStream(path.toString())));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EmployeeLayoutController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
