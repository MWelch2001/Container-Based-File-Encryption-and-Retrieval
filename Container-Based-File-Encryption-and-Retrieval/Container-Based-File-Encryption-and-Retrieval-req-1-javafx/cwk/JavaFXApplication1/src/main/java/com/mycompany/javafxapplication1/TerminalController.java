package com.mycompany.javafxapplication1;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TerminalController {

    @FXML
    private TextField commandInputField;   
    
    @FXML
    private TextField paramInputField; 
    
    @FXML
    private Button runCommandBtn;
    
    @FXML
    private Button mainMenuBtn;
    
    @FXML 
    private TextArea terminalOutput;
    
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
            secondaryStage.setTitle("MainMenu"); 
            secondaryStage.setScene(scene);
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void RunCommand(ActionEvent event) throws IOException{
        terminalOutput.clear();
        Stage primaryStage = (Stage) runCommandBtn.getScene().getWindow();
        primaryStage.setTitle("Run Command");
        String cmd =  commandInputField.getText();
        String param = paramInputField.getText();
        if (cmd.contains("nano")){
            runNano(param);
        } else {
            terminalOutput.appendText(startProcess(cmd, param));   
        }
        Utilities.dialogue("Command Executed!", "Successful!");
    }
    
  
            
    private String startProcess(String cmd, String param){
        String output = "";
        try {
            String[] params = new String[2];
            boolean multiParams = false;
            var pBuilder = new ProcessBuilder();
            if (param.contains(" ")){
                params = Utilities.splitParam(param);
                multiParams = true;
            }
            if (param.isEmpty()){
                pBuilder.command(cmd);
            }else if (multiParams){
                pBuilder.command(cmd, params[0], params[1]);
            }else{
                pBuilder.command(cmd, param);
            }
            var process = pBuilder.start();
            output = (Utilities.returnMsg(process));
            process.destroy(); 
        } catch (IOException ex) {
            Logger.getLogger(TerminalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    } 
    
    private void runNano(String param){
        try {
            String output = "";
            var pBuilder = new ProcessBuilder();
            String cmd = "terminator -e 'nano ";
            pBuilder.command("/bin/bash", "-c",cmd + param +"'");
            var process = pBuilder.start();
            var ret = process.waitFor(); 
            process.destroy();
        } catch (IOException ex) {
            Logger.getLogger(TerminalController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TerminalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}