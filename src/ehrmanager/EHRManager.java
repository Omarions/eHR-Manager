/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehrmanager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Omar
 */
public class EHRManager extends Application {
    @FXML
    public static Stage mainStage;
    @Override
    public void start(Stage primaryStage) {
        try {
            mainStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainWindowLayout.fxml"));
            Scene scene = null;
            
            String osName = System.getProperty("os.name");
            if(osName != null && osName.startsWith("Windows")){
                scene = (new WindowsHack()).getShadowScene(root);
                primaryStage.initStyle(StageStyle.TRANSPARENT);
            }else{
                scene = new Scene(root);
                primaryStage.initStyle(StageStyle.UNDECORATED);
            }
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            
            primaryStage.setTitle("eHR Manager");
            primaryStage.setScene(scene);
            primaryStage.setMinHeight(500.0d);
            primaryStage.setMinWidth(620.0d);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(EHRManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
