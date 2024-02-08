package com.mycompany.javafxapplication1;

import java.io.File;

/**
 *
 * @author ntu-user
 */
public class DeleteFile {
    File file;
    
    public DeleteFile(String uFilePath){
        file = new File(uFilePath); 
    }

    public boolean removeFile() {
        return file.delete();
    }
    
}
