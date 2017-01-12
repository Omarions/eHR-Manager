package view;

import controller.cEmployee;
import controller.cTimesheet;
import ehrmanager.EHRManager;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;
import model.Employee;
import model.Timesheet;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.SimpleJasperReportsContext;
import net.sf.jasperreports.engine.design.JRJdtCompiler;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.Constants;
import utils.DBConnection;
import utils.DialogType;
import utils.Utils;

/**
 * Controller class for FXML file of main window.
 *
 * @author Omar
 */
public class MainWindowLayoutController implements Initializable {

    //for months
    @FXML
    private HBox hbJan, hbFeb, hbMar, hbApr, hbMay, hbJun, hbJul, hbAug,
            hbSep, hbOct, hbNov, hbDec;
    //for days of january(mmdd) like (0103) means day 3 of Jan
    @FXML
    private HBox hb0101, hb0102, hb0103, hb0104, hb0105, hb0106, hb0107, hb0108, hb0109,
            hb0110, hb0111, hb0112, hb0113, hb0114, hb0115, hb0116, hb0117, hb0118, hb0119,
            hb0120, hb0121, hb0122, hb0123, hb0124, hb0125, hb0126, hb0127, hb0128, hb0129,
            hb0130, hb0131;
    //for days of February, formate "mmdd" like (0203) means day 3 of Feb
    @FXML
    private HBox hb0201, hb0202, hb0203, hb0204, hb0205, hb0206, hb0207, hb0208, hb0209,
            hb0210, hb0211, hb0212, hb0213, hb0214, hb0215, hb0216, hb0217, hb0218, hb0219,
            hb0220, hb0221, hb0222, hb0223, hb0224, hb0225, hb0226, hb0227, hb0228;
    //for days of March, formate "mmdd" like (0303) means day 3 of March
    @FXML
    private HBox hb0301, hb0302, hb0303, hb0304, hb0305, hb0306, hb0307, hb0308, hb0309,
            hb0310, hb0311, hb0312, hb0313, hb0314, hb0315, hb0316, hb0317, hb0318, hb0319,
            hb0320, hb0321, hb0322, hb0323, hb0324, hb0325, hb0326, hb0327, hb0328, hb0329,
            hb0330, hb0331;
    //for days of April, formate "mmdd" like (0403) means day 3 of April
    @FXML
    private HBox hb0401, hb0402, hb0403, hb0404, hb0405, hb0406, hb0407, hb0408, hb0409,
            hb0410, hb0411, hb0412, hb0413, hb0414, hb0415, hb0416, hb0417, hb0418, hb0419,
            hb0420, hb0421, hb0422, hb0423, hb0424, hb0425, hb0426, hb0427, hb0428, hb0429,
            hb0430;
    //for days of May, formate "mmdd" like (0503) means day 3 of May
    @FXML
    private HBox hb0501, hb0502, hb0503, hb0504, hb0505, hb0506, hb0507, hb0508, hb0509,
            hb0510, hb0511, hb0512, hb0513, hb0514, hb0515, hb0516, hb0517, hb0518, hb0519,
            hb0520, hb0521, hb0522, hb0523, hb0524, hb0525, hb0526, hb0527, hb0528, hb0529,
            hb0530, hb0531;
    //for days of June, formate "mmdd" like (0603) means day 3 of June
    @FXML
    private HBox hb0601, hb0602, hb0603, hb0604, hb0605, hb0606, hb0607, hb0608, hb0609,
            hb0610, hb0611, hb0612, hb0613, hb0614, hb0615, hb0616, hb0617, hb0618, hb0619,
            hb0620, hb0621, hb0622, hb0623, hb0624, hb0625, hb0626, hb0627, hb0628, hb0629,
            hb0630;
    //for days of July, formate "mmdd" like (0703) means day 3 of July
    @FXML
    private HBox hb0701, hb0702, hb0703, hb0704, hb0705, hb0706, hb0707, hb0708, hb0709,
            hb0710, hb0711, hb0712, hb0713, hb0714, hb0715, hb0716, hb0717, hb0718, hb0719,
            hb0720, hb0721, hb0722, hb0723, hb0724, hb0725, hb0726, hb0727, hb0728, hb0729,
            hb0730, hb0731;
    //for days of August, formate "mmdd" like (0803) means day 3 of August
    @FXML
    private HBox hb0801, hb0802, hb0803, hb0804, hb0805, hb0806, hb0807, hb0808, hb0809,
            hb0810, hb0811, hb0812, hb0813, hb0814, hb0815, hb0816, hb0817, hb0818, hb0819,
            hb0820, hb0821, hb0822, hb0823, hb0824, hb0825, hb0826, hb0827, hb0828, hb0829,
            hb0830, hb0831;
    //for days of Septemeber, formate "mmdd" like (0903) means day 3 of Septemeber
    @FXML
    private HBox hb0901, hb0902, hb0903, hb0904, hb0905, hb0906, hb0907, hb0908, hb0909,
            hb0910, hb0911, hb0912, hb0913, hb0914, hb0915, hb0916, hb0917, hb0918, hb0919,
            hb0920, hb0921, hb0922, hb0923, hb0924, hb0925, hb0926, hb0927, hb0928, hb0929,
            hb0930;
    //for days of October, formate "mmdd" like (1003) means day 3 of October
    @FXML
    private HBox hb1001, hb1002, hb1003, hb1004, hb1005, hb1006, hb1007, hb1008, hb1009,
            hb1010, hb1011, hb1012, hb1013, hb1014, hb1015, hb1016, hb1017, hb1018, hb1019,
            hb1020, hb1021, hb1022, hb1023, hb1024, hb1025, hb1026, hb1027, hb1028, hb1029,
            hb1030, hb1031;
    //for days of November, formate "mmdd" like (1103) means day 3 of November
    @FXML
    private HBox hb1101, hb1102, hb1103, hb1104, hb1105, hb1106, hb1107, hb1108, hb1109,
            hb1110, hb1111, hb1112, hb1113, hb1114, hb1115, hb1116, hb1117, hb1118, hb1119,
            hb1120, hb1121, hb1122, hb1123, hb1124, hb1125, hb1126, hb1127, hb1128, hb1129,
            hb1130;
    //for days of December, formate "mmdd" like (1203) means day 3 of December
    @FXML
    private HBox hb1201, hb1202, hb1203, hb1204, hb1205, hb1206, hb1207, hb1208, hb1209,
            hb1210, hb1211, hb1212, hb1213, hb1214, hb1215, hb1216, hb1217, hb1218, hb1219,
            hb1220, hb1221, hb1222, hb1223, hb1224, hb1225, hb1226, hb1227, hb1228, hb1229,
            hb1230, hb1231;

    @FXML
    private VBox vbox;
    @FXML
    private TreeView tvEmployees;
    @FXML
    private TextField tfTagFilter;
    @FXML
    private MenuItem miExit;
    @FXML
    private MenuItem miRefresh;
    @FXML
    private MenuItem miSettings;
    @FXML
    private MenuItem miAbout;

    private final TreeItem<Employee> rootNode;
    private final List<TreeItem<Employee>> treeItems;

    private final ObservableList<Employee> employeesList;
    private Properties settingProps;
    private cEmployee cEmp;

    //variables for dragging the window
    private double startMoveX = -1, startMoveY = -1;
    private Boolean dragging = false;
    private Rectangle moveTrackingRect;
    private Popup moveTrackingPopup;

    public MainWindowLayoutController() {
        this.employeesList = FXCollections.observableArrayList();
        treeItems = new ArrayList<>();
        rootNode = new TreeItem<>(new Employee(0, "HR Resources", "", "", "", "",
                "", LocalDate.now(), 00.00, 00.00, 00.00, 00.00, 00.00),
                Constants.CUSTOMER_ICON);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cEmp = new cEmployee();

        setHBMonths();
        setHBDays();

        //init the menus icons
        miExit.setGraphic(new ImageView(Constants.EXIT_MENU_ICON));
        miRefresh.setGraphic(new ImageView(Constants.REFRESH_MENU_ITEM_ICON));
        miSettings.setGraphic(new ImageView(Constants.SETTING_MENU_ICON));
        miAbout.setGraphic(new ImageView(Constants.ABOUT_MENU_ICON));

        fillEmployeesList();

        tvEmployees.setCellFactory(new Callback<TreeView<Employee>, TreeCell<Employee>>() {
            @Override
            public TreeCell<Employee> call(TreeView<Employee> param) {
                return new MyTreeCellImpl();
            }
        });

        populateTreeView(employeesList);
        
        ContextMenu monthMenu = new ContextMenu();
        EventHandler<MouseEvent> monthRightClickEvent = (MouseEvent event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {

                Menu mnTimeSheet = new Menu("Time Sheet");
                MenuItem miImportTS = new MenuItem("Import Timesheets");
                MenuItem miTSEmp = new MenuItem("Show Employee TS");
                MenuItem miTSEmps = new MenuItem("Show Employees TS");

                miImportTS.setOnAction((miImportEvent) -> {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Import Timesheets...");
                    fileChooser.setSelectedExtensionFilter(
                            new ExtensionFilter(
                                    "Excel 2007-2013", "xlsx", "xlsm"));

                    List<File> selectedTSs = fileChooser
                            .showOpenMultipleDialog(EHRManager.mainStage);

                    if (selectedTSs != null) {
                        LocalDate tsDate = (LocalDate) ((HBox) event.getSource()).getUserData();
                        loadTSs(selectedTSs, tsDate);
                    }

                });

                miTSEmp.setOnAction((miTSEmpEvent) -> {
                    //TODO Show dialog to choose employee. then
                    showEmployeeTS((LocalDate) ((HBox) event.getSource()).getUserData(), 2);
                });

                miTSEmps.setOnAction((miTSEmpEvent) -> {
                    //TODO
                    showEmployeesTS((LocalDate) ((HBox) event.getSource()).getUserData(), 2);
                });

                mnTimeSheet.getItems().clear();
                mnTimeSheet.getItems().addAll(miImportTS, miTSEmp, miTSEmps);

                MenuItem miPrintReciptSalaryEmp
                        = new MenuItem("Print Employee Salary Recipt");
                //MenuItem miPrintReciptSalarySummary = new MenuItem("Print Salary Recipt Summary");
                MenuItem miPrintMonthSummary
                        = new MenuItem("Print Month Salary Summary");

                miPrintReciptSalaryEmp.setOnAction((miMonthPrintsEvent) -> {
                    showPrintEmployeeSalaryRecipt((LocalDate) ((HBox) event.getSource()).getUserData());
                });
                /*
                miPrintReciptSalarySummary.setOnAction((miMonthPrintsEvent) -> {
                    showPrintSalaryReciptSummary((LocalDate) ((HBox) event.getSource()).getUserData());
                });
                 */
                miPrintMonthSummary.setOnAction((miMonthPrintsEvent) -> {
                    showPrintMonthSalarySummary((LocalDate) ((HBox) event.getSource()).getUserData());
                });

                monthMenu.getItems().clear();
                monthMenu.getItems().addAll(mnTimeSheet, miPrintReciptSalaryEmp,
                        miPrintMonthSummary);

                monthMenu.show(((HBox) event.getSource()), event.getScreenX(), event.getScreenY());
            } else {
                monthMenu.hide();
            }
        };

        ContextMenu dayMenu = new ContextMenu();
        EventHandler<MouseEvent> dayRightClickEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {

                    MenuItem miTSEmp = new MenuItem("Show day Details for employee");
                    MenuItem miTSEmps = new MenuItem("Show day details for all");

                    miTSEmp.setOnAction((miTSEmpEvent) -> {
                        //show for day
                        showEmployeeTS((LocalDate) ((HBox) event.getSource()).getUserData(), 1);
                    });

                    miTSEmps.setOnAction((miTSEmpEvent) -> {
                        //show for day
                        showEmployeesTS((LocalDate) ((HBox) event.getSource()).getUserData(), 1);
                    });

                    dayMenu.getItems().clear();
                    dayMenu.getItems().addAll(miTSEmp, miTSEmps);

                    dayMenu.show(((HBox) event.getSource()), event.getScreenX(), event.getScreenY());
                } else {
                    dayMenu.hide();
                }
            }
        };

        hbJan.setOnMouseClicked(monthRightClickEvent);
        hbFeb.setOnMouseClicked(monthRightClickEvent);
        hbMar.setOnMouseClicked(monthRightClickEvent);
        hbApr.setOnMouseClicked(monthRightClickEvent);
        hbMay.setOnMouseClicked(monthRightClickEvent);
        hbJun.setOnMouseClicked(monthRightClickEvent);
        hbJul.setOnMouseClicked(monthRightClickEvent);
        hbAug.setOnMouseClicked(monthRightClickEvent);
        hbSep.setOnMouseClicked(monthRightClickEvent);
        hbOct.setOnMouseClicked(monthRightClickEvent);
        hbNov.setOnMouseClicked(monthRightClickEvent);
        hbDec.setOnMouseClicked(monthRightClickEvent);

        getDaysBoxes().stream().forEach((box) -> {
            box.setOnMouseClicked(dayRightClickEvent);
        });
    }

    /**
     * Event handler for close label.
     *
     * @param event of the mouse click
     */
    @FXML
    public void closeHandler(MouseEvent event) {
        Platform.exit();
    }

    /**
     * Event handler for close button and close menu item.
     *
     * @param event of the mouse click
     */
    @FXML
    public void closeMenuHandler(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Event handler of minimize button.
     *
     * @param event of the mouse click
     */
    @FXML
    public void minimizeHandler(MouseEvent event) {
        Stage stage = (Stage) ((Label) event.getSource()).getScene().getWindow();

        stage.setIconified(true);
    }

    /**
     * Event handler for start dragging event.
     *
     * @param evt the mouse event of dragging detected
     */
    @FXML
    public void startMoveWindow(MouseEvent evt) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        dragging = true;

        moveTrackingRect = new Rectangle();
        moveTrackingRect.setWidth(vbox.getWidth());
        moveTrackingRect.setHeight(vbox.getHeight());
        moveTrackingRect.getStyleClass().add("tracking-rect");

        moveTrackingPopup = new Popup();
        moveTrackingPopup.getContent().add(moveTrackingRect);
        moveTrackingPopup.show(vbox.getScene().getWindow());
        moveTrackingPopup.setOnHidden((e) -> resetMoveOperations());
    }

    /**
     * Event handler of dragging window.
     *
     * @param evt the mouse drag event
     */
    @FXML
    public void moveWindow(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = vbox.getScene().getWindow();

            double stageX = w.getX();
            double stageY = w.getY();

            moveTrackingPopup.setX(stageX + (endMoveX - startMoveX));
            moveTrackingPopup.setY(stageY + (endMoveY - startMoveY));
        }
    }

    /**
     * Event handler for end of dragging
     *
     * @param evt the mouse click release event
     */
    @FXML
    public void endMoveWindow(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = vbox.getScene().getWindow();

            double stageX = w.getX();
            double stageY = w.getY();

            w.setX(stageX + (endMoveX - startMoveX));
            w.setY(stageY + (endMoveY - startMoveY));

            if (moveTrackingPopup != null) {
                moveTrackingPopup.hide();
                moveTrackingPopup = null;
            }
        }
        resetMoveOperations();
    }

    /**
     * Filter the collection of clients and its contacts with the tag of client.
     * So, only clients with specified tag written in tag text field is
     * displayed in the tree view with the helper method populateTVTO().
     *
     * @param event
     */
    @FXML
    public void filter(ActionEvent event) {
        ObservableList<Employee> tagedEmployees = FXCollections.observableArrayList();

        String filterQuery = (tfTagFilter.getText().isEmpty())
                ? "none"
                : tfTagFilter.getText().toLowerCase();

        employeesList.stream().filter((employee) -> {
            return (filterQuery.equalsIgnoreCase("none"))
                    ? true
                    : (employee.getID() == Integer.valueOf(tfTagFilter.getText()));

        }).forEach((employee) -> tagedEmployees.add(employee));

        populateTreeView(tagedEmployees);
    }

    /**
     * Event handler for menu items
     *
     * @param event
     */
    @FXML
    public void menusEventHandler(ActionEvent event) {
        String selectedMenuItem = ((MenuItem) event.getSource()).getText();
        switch (selectedMenuItem) {
            case Constants.REFRESH_MENU_ITEM:
                fillEmployeesList();
                populateTreeView(employeesList);
                break;
            case Constants.SETTINGS_MENU_ITEM:
                break;
        }
    }

    /**
     * Populate the TreeView with the data from DB
     */
    private void populateTreeView(ObservableList<Employee> employees) {
        rootNode.getChildren().clear();
        rootNode.setExpanded(true);

        employees.stream().forEach((employee) -> {
            boolean found = false;
            TreeItem<Employee> empLeaf = new TreeItem<>(employee,
                    new ImageView(Constants.EMP_ICON));
            Path photoPath = Paths.get(Constants.RESOURCE_PATH.toString(), employee.getPhoto());
            if (Utils.validatePhoto(photoPath)) {
                try(FileInputStream fis =new FileInputStream(photoPath.toString());) {
                    empLeaf.setGraphic(new ImageView(
                            new Image(fis, 32, 32, true, true))
                    );
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainWindowLayoutController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainWindowLayoutController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (TreeItem<Employee> deptNode : rootNode.getChildren()) {
                if (deptNode.getValue().getDepartment()
                        .contentEquals(employee.getDepartment())) {

                    deptNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }

            if (!found) {
                TreeItem<Employee> deptNode = new TreeItem<>(
                        new Employee(0, employee.getDepartment(), "",
                                employee.getDepartment(), "", "", "", LocalDate.now(),
                                00.00, 00.00, 00.00, 00.00, 00.00),
                        new ImageView(Constants.DEPT_ICON));

                rootNode.getChildren().add(deptNode);
                deptNode.getChildren().add(empLeaf);
            }
        });

        tvEmployees.setRoot(rootNode);

        ContextMenu tvMenu = new ContextMenu();
        miRefresh.setOnAction((event) -> {
            menusEventHandler(event);
        });
        tvMenu.getItems().add(miRefresh);

        tvEmployees.setContextMenu(tvMenu);
    }

    /**
     * Fill the list of clients and show error dialog if happens while
     * retrieving data from DB
     */
    private void fillEmployeesList() {
        try {
            employeesList.setAll(cEmp.getAll());
        } catch (SQLException ex) {
            Utils.showExceptionDialog("Database Error...", "Exception occured while getting employees list! See the details below:", ex);
        }
    }

    /**
     * Create the properties object for session, it loads the properties from
     * the settings file first if exists, otherwise it displays the error dialog
     *
     * @return properties object with values from settings file.
     */
    private Properties getProperties() {
        Properties props = new Properties();
        File propFile = new File(Constants.PROP_FILE_URL);
        if (propFile.exists()) {
            try {
                props.loadFromXML(new BufferedInputStream(new FileInputStream(propFile)));
                return props;
            } catch (IOException ex) {
                Logger.getLogger(MainWindowLayoutController.class.getName()).log(Level.SEVERE, null, ex);
                Utils.showExceptionDialog("IO Exception", "Error in loading file!", ex);
                return null;
            }
        } else {
            Utils.showErrorDialog("File Error...", "The properties file is not exist!");
            return null;
        }
    }

    /**
     * set the values of host to the properties object and write it to the
     * settings file
     *
     * @param host the smtp server
     */
    private void setProperties(String host, int timerSpin) {
        Properties props = new Properties();
        File propFile = new File(Constants.PROP_FILE_URL);
        if (propFile.exists()) {
            try {
                props.setProperty(Constants.HOST_KEY, host);

                props.storeToXML(new BufferedOutputStream(new FileOutputStream(propFile)), host);
            } catch (IOException ex) {
                Logger.getLogger(MainWindowLayoutController.class.getName()).log(Level.SEVERE, null, ex);
                Utils.showExceptionDialog("IO Exception", "Error in loading file!", ex);
            }
        } else {
            Utils.showErrorDialog("File Error...", "The properties file is not exist!");
        }
    }

    /**
     * Reset the variables of moving the window.
     */
    private void resetMoveOperations() {
        startMoveX = 0;
        startMoveY = 0;
        dragging = false;
        moveTrackingRect = null;
    }

    /**
     * Show the dialog for adding new employee or editing existing one.
     *
     * @param url the resource of fxml file that should be loaded
     * @param flag the type of dialog (dialog for client or for contact)
     * @return true when either button of save (save and quit or save and add
     * new ) is clicked, otherwise returns false.
     */
    private boolean showDialog(String url, DialogType flag, Employee updateEmployee) {

        FXMLLoader loader = new FXMLLoader();
        Stage dialogeStage = new Stage();
        dialogeStage.setWidth(549);

        try {
            loader.setLocation(EHRManager.class.getResource(url));
            AnchorPane page = (AnchorPane) loader.load();

            Scene scene = new Scene(page);
            scene.setFill(null);

            dialogeStage.initModality(Modality.WINDOW_MODAL);
            dialogeStage.initOwner(EHRManager.mainStage);
            dialogeStage.initStyle(StageStyle.TRANSPARENT);

            dialogeStage.setY(tvEmployees.getLayoutY() + 206);
            dialogeStage.setX(tvEmployees.getLayoutX() + tvEmployees.getWidth() + 230);

            dialogeStage.setScene(scene);

            // create the controller of employee layout controller
            EmployeeLayoutController employeeController = loader.getController();
            employeeController.setDialogStage(dialogeStage);
            switch (flag) {
                case ADD_DIALOG:
                    // Show the dialog and wait until the user closes it
                    dialogeStage.showAndWait();
                    break;
                case EDIT_DIALOG:
                    //set the editable employee to populate the window with its data
                    employeeController.showEditableEmployee(updateEmployee);
                    // Show the dialog and wait until the user closes it
                    dialogeStage.showAndWait();
                    break;
            }

            if (employeeController.saveClicked) {
                fillEmployeesList();
                populateTreeView(employeesList);
            }

            return employeeController.saveClicked;

        } catch (IOException ex) {
            Logger.getLogger(MainWindowLayoutController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    /**
     * To display the time sheet of certain employee whether for a day or a
     * month So, open the assigned time sheet for that employee.
     *
     * @param emp that want to display his time sheet
     * @param flag indicates whether the report will be for day or month for day
     * : flag is 1 for month : flag is 2
     */
    private void showEmployeeTS(LocalDate tsDate, int flag) {
        Optional<Employee> optEmp = utils.Utils.showEmployeesDialog(employeesList);
        new Thread(() -> {

            final StringBuilder reportPath = new StringBuilder();

            optEmp.ifPresent((employee) -> {
                Optional<Timesheet> optTS = Optional.empty();
                //get the appropriate report path and retrieve data from DB
                //according to day or month flag.
                switch (flag) {
                    case 1:     //for day details
                        if (!reportPath.toString().isEmpty()) {
                            reportPath.delete(0, reportPath.toString().length() - 1);
                        }
                        reportPath.append(Constants.REPORTS_PATH.resolve("DayTSEmp.jrxml").toString());
                        optTS = new cTimesheet().getEmpTSByDay(tsDate, employee.getID());
                        break;
                    case 2:     //for month details
                        if (!reportPath.toString().isEmpty()) {
                            reportPath.delete(0, reportPath.toString().length() - 1);
                        }
                        reportPath.append(Constants.REPORTS_PATH.resolve("MonthTSEmp.jrxml").toString());
                        optTS = new cTimesheet().getEmpTSByMonth(tsDate, employee.getID());
                        break;
                }

                optTS.ifPresent((ts) -> {
                    try {
                        Map<String, Object> params = new HashMap<>();
                        params.put("emp_id", employee.getID());
                        params.put("ts_date", tsDate.toString());
                        StringBuilder query = new StringBuilder();

                        JasperDesign jrDesign = JRXmlLoader.load(reportPath.toString());

                        JRJdtCompiler compiler = new JRJdtCompiler(new SimpleJasperReportsContext());
                        JasperReport jr = compiler.compileReport(jrDesign);
                        //To support Arabic when exporting to PDF
                        jr.setProperty("net.sf.jasperreports.default.pdf.encoding", "Cp1256");

                        JasperPrint jrPrint = JasperFillManager.fillReport(jr, params, DBConnection.getConnection());

                        JasperViewer.viewReport(jrPrint, false);
                    } catch (JRException | SQLException ex) {
                        Logger.getLogger(MainWindowLayoutController.class.getName()).log(Level.SEVERE, null, ex);
                        Utils.showExceptionDialog("JasperReport Exception...", "For more details see below:", ex);
                    }
                });

            });
        }).start();

    }

    /**
     * To display the time sheet of all employees whether for a day or a month
     * So, open the summary of time sheet for that date.
     *
     * @param emp that want to display his time sheet
     * @param flag indicates whether the report will be for day or month for day
     * : flag is 1 for month : flag is 2
     */
    private void showEmployeesTS(LocalDate tsDate, int flag) {
        new Thread(() -> {
            String reportPath = "";

            //get the appropriate report path and retrieve data from DB
            //according to day or month flag.
            switch (flag) {
                case 1:     //for day details
                    reportPath = Constants.REPORTS_PATH.resolve("DayTSAllEmps.jrxml").toString();
                    break;
                case 2:     //for month details
                    reportPath = Constants.REPORTS_PATH.resolve("MonthTSAllEmps.jrxml").toString();
                    break;
            }
            try {

                Map<String, Object> params = new HashMap<>();
                params.put("ts_date", tsDate.toString());

                JasperDesign jrDesign = JRXmlLoader.load(reportPath.toString());
                JRJdtCompiler compiler = new JRJdtCompiler(new SimpleJasperReportsContext());
                JasperReport jrReport = compiler.compileReport(jrDesign);
                //To support arabic when exporting to PDF
                jrReport.setProperty("net.sf.jasperreports.default.pdf.encoding", "Cp1256");
                JasperPrint jrPrint = JasperFillManager.fillReport(
                        jrReport, params, DBConnection.getConnection());
                //Show the print view for the report
                JasperViewer.viewReport(jrPrint, false);
            } catch (JRException | SQLException ex) {
                Logger.getLogger(MainWindowLayoutController.class.getName()).log(Level.SEVERE, null, ex);
                Utils.showExceptionDialog("JasperReport Exception...", "For more details see below:", ex);
            }

        }).start();
    }

    /**
     * Display a report of the month of such employee.
     *
     * @param month that display the report of the month of employee
     */
    private void showPrintEmployeeSalaryRecipt(LocalDate tsDate) {
        Optional<Employee> optEmp = utils.Utils.showEmployeesDialog(employeesList);
        System.out.println("Print Employee Salary Recipt of " + tsDate.getMonth());

        String reportPath = Constants.REPORTS_PATH.resolve("EmployeeSalaryRecipt.jrxml").toString();
        System.out.println(reportPath);
        //String reportPath = "reports/test_report.jrxml";
        optEmp.ifPresent((employee) -> {

            Optional<Timesheet> optTS = new cTimesheet().getEmpTSByMonth(tsDate, employee.getID());
            optTS.ifPresent((ts) -> {
                try {
                    Map<String, Object> params = new HashMap<>();
                    params.put("logo", "images/NGC_Logo.jpg");
                    params.put("id", employee.getID());
                    params.put("dt", tsDate.toString());
                    StringBuilder query = new StringBuilder();
                    //query.append("SELECT ts.emp_id, ts.ts_date, sum(ts.allowance) as allowances, sum(ts.bonus) as bonuses, sum(ts.penalty) as penalties, emp.* FROM timesheet AS ts INNER JOIN employee AS emp ON(ts.emp_id = emp.id) WHERE ts.emp_id = $P{id} AND EXTRACT(YEAR_MONTH FROM ts.ts_date)=EXTRACT(YEAR_MONTH FROM '$P!{dt}')");

                    JasperDesign jrDesign = JRXmlLoader.load(reportPath);

                    //JRDesignQuery jrQuery = new JRDesignQuery();
                    //jrQuery.setText(query.toString());
                    //jrDesign.setQuery(jrQuery);
                    JRJdtCompiler compiler = new JRJdtCompiler(new SimpleJasperReportsContext());
                    JasperReport jr = compiler.compileReport(jrDesign);
                    jr.setProperty("net.sf.jasperreports.default.pdf.encoding", "Cp1256");

                    JasperPrint jrPrint = JasperFillManager.fillReport(jr, params, DBConnection.getConnection());

                    JasperViewer.viewReport(jrPrint, false);
                } catch (JRException | SQLException ex) {
                    Logger.getLogger(MainWindowLayoutController.class.getName()).log(Level.SEVERE, null, ex);
                    Utils.showExceptionDialog("JasperReport Exception...", "For more details see below:", ex);
                }
            });

        });
    }

    /**
     * To Display the report of recipt salary
     *
     * @param tsDate month of summary
     */
    private void showPrintSalaryReciptSummary(LocalDate tsDate) {
        //TODO
        System.out.println("Print Salary Recipt Summary of " + tsDate);
    }

    /**
     * Display the report of salaries of the month for all employees
     *
     * @param tsDate the month of the report as complete date.
     */
    private void showPrintMonthSalarySummary(LocalDate tsDate) {
        new Thread(() -> {
            String reportPath = Constants.REPORTS_PATH
                    .resolve("MonthSummary.jrxml").toString();

            try {
                Map<String, Object> params = new HashMap<>();
                params.put("logo", "images/NGC_Logo.jpg");
                params.put("dt", tsDate.toString());

                JasperDesign jrDesign = JRXmlLoader.load(reportPath);

                JRJdtCompiler compiler = new JRJdtCompiler(new SimpleJasperReportsContext());
                JasperReport jr = compiler.compileReport(jrDesign);
                //To support arabic when exporting to PDF
                jr.setProperty("net.sf.jasperreports.default.pdf.encoding", "Cp1256");

                JasperPrint jrPrint = JasperFillManager.fillReport(jr, params, DBConnection.getConnection());

                JasperViewer.viewReport(jrPrint, false);
            } catch (JRException | SQLException ex) {
                Logger.getLogger(MainWindowLayoutController.class.getName()).log(Level.SEVERE, null, ex);
                Utils.showExceptionDialog("JasperReport Exception...", "For more details see below:", ex);
            }
        }).start();

    }

    /**
     * Load Excel files of a month to be read and saved to DB
     *
     * @param selectedTSs Excel files to be read and saved to DB
     * @param tsDate the month of time sheet to be loaded
     */
    private void loadTSs(List<File> selectedTSs, LocalDate tsDate) {
        selectedTSs.stream().forEach((ts) -> {
            saveDate(ts.toPath(), tsDate);
        });
    }

    /**
     * Read data from Excel file and write them to text file.
     *
     * @param tsFile the excel time sheet file
     * @param month the month of time sheet
     */
    private void saveDate(Path tsFile, LocalDate tsDate) {
        File empTS = tsFile.toFile();
        try (FileInputStream fis = new FileInputStream(empTS)) {
            Workbook wb = new XSSFWorkbook(fis);
            Sheet mainSheet = wb.getSheetAt(0);

            //BufferedWriter fw = new BufferedWriter(new FileWriter("E:\\ts.txt", true));
            Timesheet ts = new Timesheet();
            ts.setTSDate(LocalDate.MAX);

            mainSheet.forEach((Row row) -> {
                row.forEach((Cell cell) -> {
                    int rIndex = cell.getRowIndex();
                    int cIndex = cell.getColumnIndex();
                    //get ID
                    if (rIndex == 4 && cIndex == 2) {
                        Optional<Employee> optEmp = new cEmployee().getByID((int) cell.getNumericCellValue());
                        optEmp.ifPresent((emp) -> ts.setEmployee(emp));
                    }

                    //get Allowance
                    if (rIndex == 37 && cIndex == 5) {
                        ts.setAllowance(cell.getNumericCellValue());
                    }
                    //get Bonus
                    if (rIndex == 37 && cIndex == 7) {
                        ts.setBonus(cell.getNumericCellValue());
                    }
                    //get Penality
                    if (rIndex == 37 && cIndex == 9) {
                        ts.setPenalty(cell.getNumericCellValue());
                    }
                });
            });

            String newName = tsDate.getMonth() + "_" + String.valueOf(ts.getEmployee().getID())
                    + Utils.getExtension(tsFile.getFileName().toString());
            Path dst = Paths.get(Constants.TIMESHEETS_PATH.toString(), newName);

            if (!Files.exists(dst)) {
                Files.copy(tsFile, dst);
            }

            new cTimesheet().insert(ts);

        } catch (IOException ex) {
            Logger.getLogger(MainWindowLayoutController.class.getName()).log(Level.SEVERE, null, ex);
            Utils.showExceptionDialog("File Handling Exception...", "See below for details: ", ex);
        }

    }

    /**
     * Customized Tree Cell for tree view
     */
    private final class MyTreeCellImpl extends TreeCell<Employee> {

        ContextMenu manageeEmpsMenu = new ContextMenu();
        MenuItem miAddEmployee;
        MenuItem miEditEmployee;
        MenuItem miDeleteEmployee;

        public MyTreeCellImpl() {
            miAddEmployee = new MenuItem(Constants.ADD_EMP_MENU_ITEM, new ImageView(Constants.ADD_MENU_ITEM_ICON));
            miEditEmployee = new MenuItem(Constants.EDIT_EMP_MENU_ITEM, new ImageView(Constants.EDIT_MENU_ITEM_ICON));
            miDeleteEmployee = new MenuItem(Constants.DELETE_EMP_MENU_ITEM, new ImageView(Constants.DELETE_MENU_ITEM_ICON));

            EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    MenuItem pressedMenuItem = (MenuItem) event.getSource();
                    switch (pressedMenuItem.getText()) {
                        case Constants.ADD_EMP_MENU_ITEM:
                            System.out.println("Pressed Add Employee Menu Item");
                            showDialog("/view/EmployeeLayout.fxml", DialogType.ADD_DIALOG, null);
                            break;
                        case Constants.EDIT_EMP_MENU_ITEM:
                            System.out.println("Pressed Edit Employee Menu Item");
                            showDialog("/view/EmployeeLayout.fxml", DialogType.EDIT_DIALOG, getTreeItem().getValue());
                            break;
                    }
                }
            };
            //event handler of delete employee menu item
            EventHandler<ActionEvent> deleteEmpHandler = new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Removing Employee...");
                    alert.setContentText("Do you want to remove employee "
                            + getTreeItem().getValue().getName() + "?");
                    alert.getButtonTypes().clear();
                    alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

                    Optional<ButtonType> userChoice = alert.showAndWait();
                    userChoice.ifPresent((ButtonType buttonType) -> {
                        if (buttonType == ButtonType.YES) {
                            Employee emp = getTreeItem().getValue();
                            //getTreeItem().setGraphic(new ImageView(Constants.EMP_ICON));
                            int id = emp.getID();
                            String fileName = emp.getPhoto();
                            int res = cEmp.delete(id);

                            if (res == 1) {
                                //delete the photo file of the employee 
                                try {
                                    Files.deleteIfExists(Paths
                                            .get(Constants.RESOURCE_PATH.toString(),
                                                    fileName));

                                } catch (IOException ex) {
                                    Logger.getLogger(MainWindowLayoutController.class.getName())
                                            .log(Level.SEVERE, null, ex);

                                    Utils.showExceptionDialog("File Handling Exception...",
                                            "Exception occurred while deleting file\n"
                                            + "See below for more details: ", ex);
                                }
                                //show the success alert dialog
                                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                                successAlert.setHeaderText("Removing Employee...");
                                successAlert.setContentText("Employee "
                                        + getTreeItem().getValue().getName()
                                        + " removed successfully!");
                                successAlert.showAndWait();
                                fillEmployeesList();
                                //repopulate the tree view after deleting the employee
                                populateTreeView(employeesList);
                            } else {
                                Utils.showErrorDialog("Deleting Employee",
                                        "No employee found in DB");
                            }
                        } else {
                            alert.hide();
                        }

                    });

                }
            };

            //add event handlers of each menu item.
            miAddEmployee.setOnAction(eventHandler);
            miEditEmployee.setOnAction(eventHandler);
            miDeleteEmployee.setOnAction(deleteEmpHandler);

        }

        @Override
        public void startEdit() {
            super.startEdit(); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        protected void updateItem(Employee item, boolean empty) {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

            if (empty) {
                setText(null);
                setGraphic(null);
            } else if (!isEditing()) {
                setText(getItem().getName());
                setGraphic(getTreeItem().getGraphic());
                if (!getTreeItem().isLeaf() && getTreeItem().getParent() != null) {
                    manageeEmpsMenu.getItems().clear();
                    manageeEmpsMenu.getItems().add(miRefresh);
                    manageeEmpsMenu.getItems().add(miAddEmployee);
                    setContextMenu(manageeEmpsMenu);
                } else if (getTreeItem().isLeaf()) {
                    manageeEmpsMenu.getItems().clear();
                    manageeEmpsMenu.getItems().add(miEditEmployee);
                    manageeEmpsMenu.getItems().add(miDeleteEmployee);
                    setContextMenu(manageeEmpsMenu);
                }
            }
        }

        private Employee getString() {
            return getItem() == null ? null : getItem();
        }

    }

    /**
     * Get list of HBox of days of the months
     *
     * @return list of HBox of days of the months
     */
    private List<HBox> getDaysBoxes() {
        HBox[] boxes = {hb0101, hb0102, hb0103, hb0104, hb0105, hb0106, hb0107, hb0108,
            hb0109, hb0110, hb0111, hb0112, hb0113, hb0114, hb0115, hb0116, hb0117, hb0118,
            hb0119, hb0120, hb0121, hb0122, hb0123, hb0124, hb0125, hb0126, hb0127, hb0128,
            hb0129, hb0130, hb0131,
            hb0201, hb0202, hb0203, hb0204, hb0205, hb0206, hb0207, hb0208, hb0209, hb0210,
            hb0211, hb0212, hb0213, hb0214, hb0215, hb0216, hb0217, hb0218, hb0219, hb0220,
            hb0221, hb0222, hb0223, hb0224, hb0225, hb0226, hb0227, hb0228,
            hb0301, hb0302, hb0303, hb0304, hb0305, hb0306, hb0307, hb0308, hb0309, hb0310,
            hb0311, hb0312, hb0313, hb0314, hb0315, hb0316, hb0317, hb0318, hb0319, hb0320,
            hb0321, hb0322, hb0323, hb0324, hb0325, hb0326, hb0327, hb0328, hb0329, hb0330,
            hb0331,
            hb0401, hb0402, hb0403, hb0404, hb0405, hb0406, hb0407, hb0408, hb0409, hb0410,
            hb0411, hb0412, hb0413, hb0414, hb0415, hb0416, hb0417, hb0418, hb0419, hb0420,
            hb0421, hb0422, hb0423, hb0424, hb0425, hb0426, hb0427, hb0428, hb0429, hb0430,
            hb0501, hb0502, hb0503, hb0504, hb0505, hb0506, hb0507, hb0508, hb0509, hb0510,
            hb0511, hb0512, hb0513, hb0514, hb0515, hb0516, hb0517, hb0518, hb0519, hb0520,
            hb0521, hb0522, hb0523, hb0524, hb0525, hb0526, hb0527, hb0528, hb0529, hb0530,
            hb0531,
            hb0601, hb0602, hb0603, hb0604, hb0605, hb0606, hb0607, hb0608, hb0609, hb0610,
            hb0611, hb0612, hb0613, hb0614, hb0615, hb0616, hb0617, hb0618, hb0619, hb0620,
            hb0621, hb0622, hb0623, hb0624, hb0625, hb0626, hb0627, hb0628, hb0629, hb0630,
            hb0701, hb0702, hb0703, hb0704, hb0705, hb0706, hb0707, hb0708, hb0709, hb0710,
            hb0711, hb0712, hb0713, hb0714, hb0715, hb0716, hb0717, hb0718, hb0719, hb0720,
            hb0721, hb0722, hb0723, hb0724, hb0725, hb0726, hb0727, hb0728, hb0729, hb0730,
            hb0731,
            hb0801, hb0802, hb0803, hb0804, hb0805, hb0806, hb0807, hb0808, hb0809, hb0810,
            hb0811, hb0812, hb0813, hb0814, hb0815, hb0816, hb0817, hb0818, hb0819, hb0820,
            hb0821, hb0822, hb0823, hb0824, hb0825, hb0826, hb0827, hb0828, hb0829, hb0830,
            hb0831,
            hb0901, hb0902, hb0903, hb0904, hb0905, hb0906, hb0907, hb0908, hb0909, hb0910,
            hb0911, hb0912, hb0913, hb0914, hb0915, hb0916, hb0917, hb0918, hb0919, hb0920,
            hb0921, hb0922, hb0923, hb0924, hb0925, hb0926, hb0927, hb0928, hb0929, hb0930,
            hb1001, hb1002, hb1003, hb1004, hb1005, hb1006, hb1007, hb1008, hb1009, hb1010,
            hb1011, hb1012, hb1013, hb1014, hb1015, hb1016, hb1017, hb1018, hb1019, hb1020,
            hb1021, hb1022, hb1023, hb1024, hb1025, hb1026, hb1027, hb1028, hb1029, hb1030,
            hb1031,
            hb1101, hb1102, hb1103, hb1104, hb1105, hb1106, hb1107, hb1108, hb1109, hb1110,
            hb1111, hb1112, hb1113, hb1114, hb1115, hb1116, hb1117, hb1118, hb1119, hb1120,
            hb1121, hb1122, hb1123, hb1124, hb1125, hb1126, hb1127, hb1128, hb1129, hb1130,
            hb1201, hb1202, hb1203, hb1204, hb1205, hb1206, hb1207, hb1208, hb1209, hb1210,
            hb1211, hb1212, hb1213, hb1214, hb1215, hb1216, hb1217, hb1218, hb1219, hb1220,
            hb1221, hb1222, hb1223, hb1224, hb1225, hb1226, hb1227, hb1228, hb1229, hb1230,
            hb1231};

        return Arrays.asList(boxes);

    }

    /**
     * Set the user data of HBoxs of months with the date of 1st day of each
     * month as Local date object
     */
    private void setHBMonths() {
        int currentYear = 2016;//LocalDate.now().getYear();
        hbJan.setUserData(LocalDate.of(currentYear, 1, 1));
        hbFeb.setUserData(LocalDate.of(currentYear, 2, 1));
        hbMar.setUserData(LocalDate.of(currentYear, 3, 1));
        hbApr.setUserData(LocalDate.of(currentYear, 4, 1));
        hbMay.setUserData(LocalDate.of(currentYear, 5, 1));
        hbJun.setUserData(LocalDate.of(currentYear, 6, 1));
        hbJul.setUserData(LocalDate.of(currentYear, 7, 1));
        hbAug.setUserData(LocalDate.of(currentYear, 8, 1));
        hbSep.setUserData(LocalDate.of(currentYear, 9, 1));
        hbOct.setUserData(LocalDate.of(currentYear, 10, 1));
        hbNov.setUserData(LocalDate.of(currentYear, 11, 1));
        hbDec.setUserData(LocalDate.of(currentYear, 12, 1));
    }

    /**
     * Set UserData of each HBox of each day in the year with the according date
     */
    private void setHBDays() {
        int currentYear = 2016;//LocalDate.now().getYear();
        //Set UserData for January days
        List<HBox> boxes = getDaysBoxes();
        boxes.stream().forEach((box) -> {
            //extract month and day from HBox ID
            int month = Integer.valueOf(box.getId().substring(2, 4));
            int day = Integer.valueOf(box.getId().substring(4));

            box.setUserData(LocalDate.of(currentYear, month, day));
        });

    }
}
