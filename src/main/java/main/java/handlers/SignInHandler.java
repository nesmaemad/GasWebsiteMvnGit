/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.handlers;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    
    
    public String signIn(HttpServletRequest request){
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
            String email    = params.getString("email");
            String password = params.getString("password");
            System.out.println("email " + email);
            System.out.println("password " + password);
            String sql = "select id , first_name , last_name , user_name , province_id , country_id"
                    + " from user where email = ? and password = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                HttpSession session = request.getSession();
                session.setAttribute("id"          , rs.getString(1));
                session.setAttribute("first_name"  , rs.getString(2));
                session.setAttribute("last_name"   , rs.getString(3));
                session.setAttribute("user_name"   , rs.getString(4));
                session.setAttribute("province_id" , rs.getString(5));
                session.setAttribute("country_id"  , rs.getString(6));
                session.setAttribute("email"       , email);
                
                return rs.getString(2);
            }else{
                return "failed";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "failed";
    }
    
    
}
