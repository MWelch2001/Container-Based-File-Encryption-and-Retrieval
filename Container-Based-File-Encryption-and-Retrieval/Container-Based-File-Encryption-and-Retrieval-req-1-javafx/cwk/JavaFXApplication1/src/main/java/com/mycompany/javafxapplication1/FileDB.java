/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author ntu-user
 */
public class FileDB {
    private String fileName = "jdbc:sqlite:comp20081.db";
    private int timeout = 30;
    private String dataBaseName = "COMP20081";
    private String dataBaseTableName = "Files";
    Connection connection = null;
    private Random random = new SecureRandom();
    private String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private int iterations = 10000;
    private int keylength = 256;
    private String saltValue;
    
    
    /**
     * @brief create a new table
     * @param tableName name of type String
     */
    
    public void createTable() throws ClassNotFoundException {
        try {
            // create a database connection
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(fileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("create table if not exists " + getTableName()  + "(id integer primary key autoincrement, filename string, author string, location string, date string)");

        } catch (SQLException ex) {
            Logger.getLogger(FileDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
    
    public void DeleteFile(String fileName) throws ClassNotFoundException, InvalidKeySpecException {
        try {
            // create a database connection
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(this.fileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("delete from " + getTableName()  + " WHERE filename = '" + fileName + "'");
        } catch (SQLException ex) {
            Logger.getLogger(FileDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
   
    

    /**
     * @brief delete table
     * @param tableName of type String
     */
    public void delTable(String tableName) throws ClassNotFoundException {
        try {
            // create a database connection
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(fileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("drop table if exists " + getTableName() );
        } catch (SQLException ex) {
            Logger.getLogger(FileDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * @brief add data to the database method
     * @param user name of type String
     * @param password of type String
     */
        public void addDataToDB(String filename, String author, String location, String date) throws InvalidKeySpecException, ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(fileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("insert into " + dataBaseTableName + " (filename, author, location, date) values('" + filename + "','" + author + "','" + location + "','" + date + "')");
        } catch (SQLException ex) {
            Logger.getLogger(FileDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(FileDB.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    // connection close failed.
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    /**
     * @brief get data from the Database method
     * @retunr results as ResultSet
     */
    public ObservableList<UserFile> getFileFromTable(String fileLoc) throws ClassNotFoundException {
        ObservableList<UserFile> result = FXCollections.observableArrayList();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(fileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select * from " + getTableName() + " where location = '" + fileLoc + "'");
            while (rs.next()) {
                // read the result set
                result.add(new UserFile(rs.getString("filename"), rs.getString("author"), rs.getString("location"), rs.getString("date")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FileDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * @brief get table name 
     * @return table name as String
     */
    public String getTableName() {
        return this.dataBaseTableName;
    }

    /**
     * @brief print a message on screen method
     * @param message of type String
     */
    public void log(String message) {
        System.out.println(message);

    }
}
