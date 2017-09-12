/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.utilities;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static main.java.config.configurations.DB_PASS;
import static main.java.config.configurations.DB_USER;

/**
 *
 * @author DELL
 */
public class DBConnection {
    private static Connection connection = null;
    public static Connection getConnection(){
        if(connection == null){
            System.out.println("creating a new connection object");
            initConnection();           
        }
       return connection;       
        
    }
    
    private static Connection initConnection(){
         try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		System.out.println("Where is your MySQL JDBC Driver?");
		e.printStackTrace();
	}

	System.out.println("MySQL JDBC Driver Registered!");


	try {
		connection = (Connection) DriverManager
		.getConnection("jdbc:mysql://localhost:3306/gas",DB_USER, DB_PASS);

	} catch (SQLException e) {
		System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		
	}

	if (connection != null) {
		System.out.println("You made it, take control your database now!");
	} else {
		System.out.println("Failed to make connection!");
	}
        return connection;
    }

    
}
