package com.mycompany.javafxapplication1;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;



public class UserManagerController {
    
    @FXML
    private TextField userTextField;
    
    @FXML
    private TableView dataTableView;

    @FXML
    private Button mainMenuBtn;
    
    @FXML
    private Button refreshBtn;
    
    @FXML
    private Button deleteUserBtn;
    
    @FXML
    private PasswordField newPasswordField;
    
    @FXML
    private PasswordField oldPasswordField;
    
    @FXML
    private void RefreshBtnHandler(ActionEvent event){
        dataTableView.getColumns().clear();
        dataTableView.getItems().clear();
        initialise();
    }
    
    @FXML
    private void GoToMainMenu(){
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) mainMenuBtn.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MainMenu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Main Menu");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void DeleteUser(){
        try{
            DB db = new DB();
            db.DeleteUser("Users", userTextField.getText(), oldPasswordField.getText());
            Utilities.dialogue("User deleted", "Successful!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void UpdatePass(){
        try{
            DB db = new DB();
            if (db.validateUser(userTextField.getText(), oldPasswordField.getText())){
                db.UpdateUser("Users", userTextField.getText(), newPasswordField.getText());
                Utilities.dialogue("User Updated", "Successful!");
            }else{
                Utilities.dialogue("Incorrect Password", "Unsuccesful!");
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialise() {
        
        DB myObj = new DB();
        ObservableList<User> data;
        try {
            data = myObj.getDataFromTable();
            TableColumn user = new TableColumn("User");
            user.setCellValueFactory(
            new PropertyValueFactory<>("user"));
    
            TableColumn pass = new TableColumn("Pass");
            pass.setCellValueFactory(
            new PropertyValueFactory<>("pass"));
        
            TableColumn isAdmin = new TableColumn("Admin");
            isAdmin.setCellValueFactory(
            new PropertyValueFactory<>("isAdmin"));
        
        
            dataTableView.setItems(data);
            dataTableView.getColumns().addAll(user, pass, isAdmin);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
