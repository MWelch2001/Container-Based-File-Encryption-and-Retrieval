package com.mycompany.javafxapplication1;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author ntu-user
 */
public class CreateFile {
    File newFile;
    
    public CreateFile(String uFilePath){
        newFile = new File(uFilePath);
    }
    
    public boolean createFile() throws IOException { 
      return newFile.createNewFile();  
    }
}
