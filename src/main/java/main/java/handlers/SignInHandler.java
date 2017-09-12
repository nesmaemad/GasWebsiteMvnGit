/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.handlers;

import java.io.BufferedReader;
import java.sql.Connection;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import main.java.utilities.DBConnection;
import org.json.JSONObject;

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
        System.out.println("inside signIn in signInHandler");
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader;
        try {
            reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String data = buffer.toString();
            JSONObject params = new JSONObject(data);
            System.out.println(params.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
    
    
}
