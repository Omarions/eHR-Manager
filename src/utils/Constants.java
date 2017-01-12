/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Omar
 */
public class Constants {
    public static int lastID = 9;
    public static int CURRENT_YEAR = LocalDate.now().getYear();
    public static final Path RESOURCE_PATH = FileSystems.getDefault().getPath("employees_photos").toAbsolutePath();
    public static final Path TIMESHEETS_PATH = FileSystems.getDefault().getPath("timesheets").toAbsolutePath();
    public static final Path REPORTS_PATH = FileSystems.getDefault().getPath("reports").toAbsolutePath();
   
    public static final String HOST_DEFAULT_VALUE = "localhost";
    public static final String HOST_KEY = "mail.smtp.host";
    public static final String AUTH_KEY = "mail.smtp.auth";
    public static final String EMP_DUMMY_PHOTO = "dummy_emp_photo.png";
    public static final String EMP_DUMMY_ICON = "dummy_emp_icon.png"; 
    
    public static final String REFRESH_MENU_ITEM = "Refresh";
    public static final String ADD_EMP_MENU_ITEM = "Add Employee";
    public static final String EDIT_EMP_MENU_ITEM = "Edit Employee";
    public static final String DELETE_EMP_MENU_ITEM = "Delete Employee";
    public static final String SETTINGS_MENU_ITEM = "Settings";
    public static final String PROP_FILE_URL = "settings.xml";
    
    public static final Node CUSTOMER_ICON = new ImageView(
            new Image(Constants.class.getResourceAsStream("/images/customers_32.png")));
    public static final Image DEPT_ICON
            = new Image(Constants.class.getResourceAsStream("/images/dept_icon.png"));
    public static final Image EDIT_MENU_ITEM_ICON
            = new Image(Constants.class.getResourceAsStream("/images/edit_icon.png"));
    public static final Image ADD_MENU_ITEM_ICON
            = new Image(Constants.class.getResourceAsStream("/images/add_icon.png"));
    public static final Image DELETE_MENU_ITEM_ICON
            = new Image(Constants.class.getResourceAsStream("/images/delete_icon.png"));
    public static final Image REFRESH_MENU_ITEM_ICON
            = new Image(Constants.class.getResourceAsStream("/images/arrows_refresh.png"));
    public static final Image SETTING_DIALOG_ICON 
            = new Image(Constants.class.getResourceAsStream("/images/server-dialog-icon.png"));
    public static final Image SETTING_MENU_ICON 
            = new Image(Constants.class.getResourceAsStream("/images/setting-menu-icon.png"));
    public static final Image ABOUT_MENU_ICON 
            = new Image(Constants.class.getResourceAsStream("/images/about-icon.png"));
    public static final Image EXIT_MENU_ICON 
            = new Image(Constants.class.getResourceAsStream("/images/exit-icon 24.png"));
    public static final Image EMP_ICON 
            = new Image(Constants.class.getResourceAsStream("/images/dummy_emp_icon.png")); 
    public static final Image DUMM_EMP_PHOTO 
            = new Image(Constants.class.getResourceAsStream("/images/dummy_emp_photo.png"));
    
}
