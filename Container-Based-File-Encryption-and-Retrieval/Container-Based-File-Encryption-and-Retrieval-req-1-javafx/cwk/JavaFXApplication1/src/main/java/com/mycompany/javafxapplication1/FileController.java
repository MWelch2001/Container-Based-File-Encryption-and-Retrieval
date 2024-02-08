package com.mycompany.javafxapplication1;


import java.io.File;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;

public class FileController {
    private UpdateFile uFile;
    
    @FXML
    private Button openFileBtn;
    
    @FXML
    private Button deleteFileBtn;
    
    @FXML
    private Button createFileBtn;
    
    @FXML
    private Button saveFileBtn;
    
    @FXML
    private Button mainMenuBtn;
        

    @FXML
    private TextArea outputWindow;
    
    @FXML
    private TextField newFileInput;
    
     @FXML
    void GoToMainMenu(){
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) mainMenuBtn.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MainMenu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Main Menu"); 
            secondaryStage.setScene(scene);
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OpenFile(ActionEvent event) throws IOException {
        outputWindow.clear();
        FileDB myObj = new FileDB();
        Stage primaryStage = (Stage) openFileBtn.getScene().getWindow();
        User activeUser = (User) primaryStage.getUserData();
        primaryStage.setTitle("Select a File");
        String file = GetPath(primaryStage, false);
        UserFile activeFile = getFile(myObj, file);
        if (checkAuthor(activeUser.getUser(), activeFile.getAuthor())) {
            System.out.print(activeUser.getUser());
            System.out.print(activeFile.getAuthor());
            uFile = new UpdateFile(file);
            outputWindow.appendText( uFile.getFileContents() + '\n');
        }else {
            Utilities.dialogue("You do not own this file", "Access denied!");
        }
    }
    
    @FXML
    void SaveFile(ActionEvent event) throws IOException {
        uFile.writeToFile(outputWindow.getText());
        outputWindow.clear();
        Utilities.dialogue(" File update", "Successful!");
    }
    
    
    @FXML
    void DeleteFile(ActionEvent event) throws IOException {
        boolean isOk = false;
        boolean invalidUser = true;
        FileDB myObj = new FileDB();
        Stage primaryStage = (Stage) deleteFileBtn.getScene().getWindow();
        User activeUser = (User) primaryStage.getUserData();
        primaryStage.setTitle("Select a File");
        String file = GetPath(primaryStage, false);
        DeleteFile dFile= new DeleteFile(file);
        UserFile activeFile = getFile(myObj, file);
        if (checkAuthor(activeUser.getUser(), activeFile.getAuthor())){
          isOk = dFile.removeFile();  
        } else {
            Utilities.dialogue("You do not own this file", "Access denied!");
            isOk = false;
            invalidUser = false;
        }
        if (isOk){
            try {
                myObj.DeleteFile(GetName());         
            } catch (InvalidKeySpecException | ClassNotFoundException ex) {
                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
               Utilities.dialogue(" File deleted", "Successful!");   
            }         
        }else if (!invalidUser){
          Utilities.dialogue(" Invalid file path", "Please try again");
        }     
    }
    
    @FXML
    void CreateFile(ActionEvent event) throws IOException {
        boolean isOk;
        FileDB myObj = new FileDB();
        Stage primaryStage = (Stage) createFileBtn.getScene().getWindow();
        User activeUser = (User) primaryStage.getUserData();
        primaryStage.setTitle("Create a File");
        String file = GetPath(primaryStage, true);
        file += "/" + GetName();
        System.out.print(file);
        CreateFile cFile = new CreateFile(file);
        isOk = cFile.createFile();
        if (isOk){
            try {
                myObj.addDataToDB(GetName(), activeUser.getUser(), file, getDate());         
            } catch (InvalidKeySpecException | ClassNotFoundException ex) {
                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
               Utilities.dialogue(" File created", "Successful!"); 
            }  
        }else{
          Utilities.dialogue(" Invalid file path", "Please try again");
        }
        newFileInput.clear();
    } 
    
    private String GetPath(Stage stage, boolean isDtr){
        String filePath = "";
        try {
            if(isDtr){
               DirectoryChooser dtrChooser = new DirectoryChooser();
               dtrChooser.setTitle("Open Directory");
                File selectedFile = dtrChooser.showDialog(stage);
                if(selectedFile!=null){ 
                    filePath = (String)selectedFile.getCanonicalPath();
                }   
            }else{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File selectedFile = fileChooser.showOpenDialog(stage);
                if(selectedFile!=null){ 
                    filePath = (String)selectedFile.getCanonicalPath();
                }  
            }   
        } catch (IOException ex) {
                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filePath;
    }
    
    private UserFile getFile(FileDB myObj, String file){
        UserFile activeFile;
        try {
            ObservableList<UserFile> files = myObj.getFileFromTable(file);
            activeFile = files.get(files.size() - 1);
            return activeFile;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private String GetName(){
        return newFileInput.getText();
    }
    
    private boolean checkAuthor(String user, String author){
             return user.equals(author);       
    }
    
    private String getDate(){
        long currentTimeInMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeInMillis);
        String DateTime = currentDate.toString();
        return DateTime;
    }
}
    
    