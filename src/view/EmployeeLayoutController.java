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
    private ImageView ivEmpPhoto;
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

    private boolean isPhotoChanged = false;
    private File employeePhoto = Constants.RESOURCE_PATH
            .resolve(Constants.EMP_DUMMY_PHOTO).toFile();
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
                ivEmpPhoto.setImage(new Image(in));
                employeePhoto = selectedFile;
                isPhotoChanged = true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EmployeeLayoutController.class.getName())
                        .log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EmployeeLayoutController.class.getName())
                        .log(Level.SEVERE, null, ex);
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

    /**
     * Event handler of lost focus of each input box of numbers
     *
     * @param event
     */
    @FXML
    public void lostFocusHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
            //validate numbers inputs
            if (!isValidNumbers()) {
                Utils.showErrorDialog("Wrong Data Format...",
                        "Enter money in numbers only like 00.00");
            }
        }
    }

    /**
     * Copy the employee photo file to the resources folder of photos.
     * 
     * @param source is the path of the source photo file
     * @return true, if the copy operation is done, otherwise, return false
     */
    private boolean copyPhoto(Path source) {
        Path dst;
        try {
            //check if the photo of employee is changed or not
            if (isPhotoChanged) {
                //if changed, rename the file
                dst = Constants.RESOURCE_PATH.resolve(Constants.lastID
                        + getExtension(source.getFileName().toString()));
            } else {
                //if not changed, keep the same name of photo.
                dst = Constants.RESOURCE_PATH.resolve(source.getFileName());
            }
            //copy the file to the resouces folder
            Files.copy(source, dst, StandardCopyOption.REPLACE_EXISTING); 
            return true;
        } catch (IOException ex) {
            Logger.getLogger(EmployeeLayoutController.class.getName())
                    .log(Level.SEVERE, null, ex);
            Utils.showExceptionDialog("Exception in File Handling...",
                    "There is exception takes place while handling the file.\n"
                    + " See below for more details: ", ex);
            return false;
        }
    }

    /**
     * Build the client object from the input controls. It returns optional
     * object of type client with the client object created from input data or
     * empty optional object in case of missing inputs.
     *
     * @return Optional object of type client.
     */
    private Optional<Employee> buildEmployee() {

        if (isValid()) {

            //copiedPhotoPath = copyPhoto();
            int id = (editableEmployee != null) ? editableEmployee.getID() : 0;
            String name = tfName.getText();
            String dept = tfDepartment.getText();
            String title = tfTitle.getText();
            String nationalID = tfNationalID.getText();
            String InsNo = tfInsuranceNo.getText();
            String empPhotoName = (id == 0)
                    ? String.valueOf(mCEmployee.getLastID() + 1)
                    + getExtension(employeePhoto.getName())
                    : (isPhotoChanged)
                            ? employeePhoto.getName()
                            : editableEmployee.getPhoto();

            LocalDate startDate = (dpHiringDate.getValue() == null)
                    ? LocalDate.now() : dpHiringDate.getValue();
            double basicSalary = (Double.valueOf(tfBasicSalary.getText()) <= 0.00)
                    ? 0.00 : Double.valueOf(tfBasicSalary.getText());
            double grossSalary = (Double.valueOf(tfGrossSalary.getText()) <= 0.00)
                    ? 0.00 : Double.valueOf(tfGrossSalary.getText());
            double insDeduction = (Double.valueOf(tfInsDeduction.getText()) <= 0.00)
                    ? 0.00 : Double.valueOf(tfInsDeduction.getText());
            double healthInsDeduction = (Double.valueOf(tfHealthInsDeduction.getText()) <= 0.00)
                    ? 0.00 : Double.valueOf(tfHealthInsDeduction.getText());
            double otherDeduction = (Double.valueOf(tfOtherDeduction.getText()) <= 0.00)
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
                copyPhoto(employeePhoto.toPath());

                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "The employee saved successfully!", ButtonType.OK);
                alert.setHeaderText("Save Employee...");
                alert.showAndWait();

            });
            return true;
        } else {
            optEmployee.ifPresent((emp) -> {
                mCEmployee.update(emp);
                Path src = Constants.RESOURCE_PATH.resolve(emp.getPhoto());
                copyPhoto(src);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "The employee saved successfully!", ButtonType.OK);
                alert.setHeaderText("Save Employee...");
                alert.showAndWait();
            });
            return true;
        }
    }

    private String getExtension(String fileName) {
        String fName = fileName;
        String ext = fName.substring(fName.indexOf("."), fName.length());

        return ext;
    }

    /**
     * Validate the inputs for required fields
     *
     * @return
     */
    private boolean isValid() {
        if (tfName.getText().isEmpty() || tfDepartment.getText().isEmpty()
                || tfTitle.getText().isEmpty() || tfInsuranceNo.getText().isEmpty()
                || !isValidNumbers()) {

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
    private boolean isValidNumbers() {

        if (tfBasicSalary.getText().isEmpty()
                || !tfBasicSalary.getText().matches("[0-9]+.[0-9]{2}")) {
            return false;
        } else if (tfGrossSalary.getText().isEmpty()
                || !tfGrossSalary.getText().matches("[0-9]+.[0-9]{2}")) {
            return false;
        } else if (tfInsDeduction.getText().isEmpty()
                || !tfInsDeduction.getText().matches("[0-9]+.[0-9]{2}")) {
            return false;
        } else if (tfHealthInsDeduction.getText().isEmpty()
                || !tfHealthInsDeduction.getText().matches("[0-9]+.[0-9]{2}")) {
            return false;
        } else if (tfOtherDeduction.getText().isEmpty()
                || !tfOtherDeduction.getText().matches("[0-9]+.[0-9]{2}")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Clear all input controls to add new client data
     */
    private void clearInputs() {
        tfName.setText("");
        tfDepartment.setText("");
        tfTitle.setText("");
        dpHiringDate.setValue(LocalDate.now());
        tfBasicSalary.setText("0.00");
        tfGrossSalary.setText("0.00");
        tfInsDeduction.setText("0.00");
        tfInsuranceNo.setText("");
        tfHealthInsDeduction.setText("0.00");
        tfOtherDeduction.setText("0.00");
        ivEmpPhoto.setImage(Constants.EMP_ICON);
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
        tfNationalID.setText(employee.getNationalID());
        dpHiringDate.setValue(employee.getHiringDate());
        tfBasicSalary.setText(String.valueOf(employee.getBasicSalary()));
        tfGrossSalary.setText(String.valueOf(employee.getGrossSalary()));
        tfInsuranceNo.setText(employee.getInsuranceNum());
        tfInsDeduction.setText(String.valueOf(employee.getInsDeduction()));
        tfHealthInsDeduction.setText(String.valueOf(employee.getHealthInsDeduction()));
        tfOtherDeduction.setText(String.valueOf(employee.getOtherDeduction()));

        Path path = Paths.get(Constants.RESOURCE_PATH.toString(), employee.getPhoto());
        if (utils.Utils.validatePhoto(path)) {
            try (FileInputStream fis = new FileInputStream(path.toString());) { 
                ivEmpPhoto.setImage(new Image(fis));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EmployeeLayoutController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EmployeeLayoutController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
