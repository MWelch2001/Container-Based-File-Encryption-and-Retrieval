package com.mycompany.javafxapplication1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Button goFileBtn;

    @FXML
    private Button goTerminalBtn;
    
    @FXML
    private Button manageUsersBtn;
    
    @FXML
    private Button logoutBtn;
    
    @FXML
    void GoToFiles(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) goFileBtn.getScene().getWindow();
        try {
            User activeUser = (User) primaryStage.getUserData();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FileEditor.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 800);
            secondaryStage.setScene(scene);
            secondaryStage.setUserData(activeUser);
            secondaryStage.setTitle("Edit Files");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void GoToTerminal(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) goFileBtn.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Terminal.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 800);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Terminal");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void GoToManageUsers(ActionEvent event){
         Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) goFileBtn.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UserManager.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 640, 480);
                secondaryStage.setScene(scene);
                UserManagerController controller = loader.getController();
                secondaryStage.setTitle("Show users");
                controller.initialise();
            secondaryStage.setScene(scene);
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void Logout(){
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) logoutBtn.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Login"); 
            secondaryStage.setScene(scene);
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
