/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ntu-user
 */
public class UserFile {
    private SimpleStringProperty fileName;
    private SimpleStringProperty author;
    private SimpleStringProperty location;
    private SimpleStringProperty date;
    
    UserFile(String fileName, String author, String location, String date) {
        this.fileName = new SimpleStringProperty(fileName);
        this.author = new SimpleStringProperty(author);
        this.location = new SimpleStringProperty(location);
        this.date = new SimpleStringProperty(date);
    }

    public String getFileName() {
        return fileName.get();
    }

    public void setFileName(String user) {
        this.fileName.set(user);
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
        
    }
    
      public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
        
    }
    
      public String getDate() {
        return location.get();
    }

    public void setDate(String date) {
        this.location.set(date);
        
    }
}
