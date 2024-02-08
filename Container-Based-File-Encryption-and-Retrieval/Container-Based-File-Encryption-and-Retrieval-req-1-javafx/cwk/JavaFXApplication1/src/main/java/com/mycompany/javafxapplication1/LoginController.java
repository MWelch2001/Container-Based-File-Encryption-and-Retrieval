package com.mycompany.javafxapplication1;

import java.io.IOException;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class LoginController {

    @FXML
    private Button registerBtn;
    
    @FXML
    private Button primaryButton;

    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passPasswordField;

    @FXML
    private void registerBtnHandler(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) registerBtn.getScene().getWindow();
        DB myObj = new DB();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("register.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Register a new User");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    private void ValidateLogin() {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) primaryButton.getScene().getWindow();
        try {
            DB myObj = new DB();
            //String[] credentials = {userTextField.getText(), passPasswordField.getText()};
            if(myObj.validateUser(userTextField.getText(), passPasswordField.getText())){
                ObservableList<User> user = myObj.getUserFromTable(userTextField.getText(), passPasswordField.getText());
                User activeUser = user.get(user.size() - 1);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("MainMenu.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 640, 480);
                secondaryStage.setScene(scene);
                secondaryStage.setUserData(activeUser);
                secondaryStage.setTitle("Main Menu");
                secondaryStage.show();
                primaryStage.close();
            }
            else{
                Utilities.dialogue("Invalid User Name / Password","Please try again!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
