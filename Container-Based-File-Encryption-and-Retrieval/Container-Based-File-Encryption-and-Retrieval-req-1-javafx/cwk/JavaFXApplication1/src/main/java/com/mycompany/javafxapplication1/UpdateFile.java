package com.mycompany.javafxapplication1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author ntu-user
 */
public class UpdateFile {
    private File userFile;
    private Scanner fileReader;
    private FileWriter fileWriter;

    public UpdateFile(String uFilePath) throws FileNotFoundException, IOException {
        try {
            userFile = new File(uFilePath);
            fileReader = new Scanner(userFile);
        } catch (FileNotFoundException e) {
         
            
        }
    }
    
    public String getFileContents() throws FileNotFoundException {
        String fileContents = "";
        fileContents = readFile(fileContents);   
        return fileContents;
    }

    private String readFile(String fileContents) {
       try {
        fileContents += fileReader.nextLine();
        while (fileReader.hasNextLine()) {
            fileContents += "\n" + fileReader.nextLine();
        }
         fileReader.close();
         } catch (Exception ex) {
            System.out.println(ex.getMessage());
            
         }
        return fileContents;
    }

    public void writeToFile(String writeText) throws IOException {
        fileWriter = new FileWriter(userFile);
        fileWriter.write(writeText);
        fileWriter.close();
    }

}