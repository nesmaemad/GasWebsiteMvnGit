/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.handlers;

import java.sql.Connection;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import main.java.utilities.DBConnection;

/**
 *
 * @author DELL
 */
public class SignInHandler {
    private static Connection connection;
    private static SignInHandler signInHandler = null;

    
    private SignInHandler(){
        connection = DBConnection.getConnection();
    }
    
    public static SignInHandler getInstance(){
        if(signInHandler == null){
            System.out.println("creating a new signuphandler object");
            signInHandler = new SignInHandler();
        }
        return signInHandler;
    }
    
    public boolean signIn(HttpServletRequest request){
        String email    = request.getParameter("email");
        String password = request.getParameter("password");
        return true;
    }
    
    
}
