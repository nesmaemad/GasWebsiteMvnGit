/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import main.java.utilities.DBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */
public class SignUpHandler {
    private static Connection connection;
    private static SignUpHandler signUpHandler = null;
    private static HashMap<String , String > sign_up_colunms_names  = null;
    
    private static void initSignUpMap(){
          sign_up_colunms_names.put("number"     , "phone");
          sign_up_colunms_names.put("country"    , "country_id");
          sign_up_colunms_names.put("password"   , "password");
          sign_up_colunms_names.put("address"    , "address");
          sign_up_colunms_names.put("code"       , "postal_zip");
          sign_up_colunms_names.put("province"   , "province_id");
          sign_up_colunms_names.put("user_name"  , "user_name");
          sign_up_colunms_names.put("last_name"  , "last_name");
          sign_up_colunms_names.put("first_name" , "first_name");
          sign_up_colunms_names.put("email"      , "email");
          
    }
    
    private SignUpHandler(){
        connection = DBConnection.getConnection();
        sign_up_colunms_names = new HashMap<>();
        initSignUpMap();
    }
    
    public static SignUpHandler getInstance(){
        if(signUpHandler == null){
            System.out.println("creating a new signuphandler object");
            signUpHandler = new SignUpHandler();
        }
        return signUpHandler;
    }
    
    public void signUp(HttpServletRequest request) throws SQLException{
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
            String sql = "insert into user (";
            
            int parameterIndex = 1;
            for (String key : params.keySet()) {
                 String value = String.valueOf(params.get(key));
                 System.out.println("key: "+ key + " value: " + value);
                 sql+= sign_up_colunms_names.get(key);
                 parameterIndex++;
                 if(parameterIndex <= 10){
                     sql+= ",";
                 }
             }
            sql += ") values ( ? ,? ,? ,? ,? ,? ,? ,? ,? ,?)";
            System.out.println(sql);
            parameterIndex = 1;
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (String key : params.keySet()) {
                 String value = String.valueOf(params.get(key));
                 System.out.println("key: "+ key + " value: " + value);
                 stmt.setString(parameterIndex, value);
                 parameterIndex++;
             }
            //stmt.setInt(1, Integer.parseInt(country_id));  
            stmt.executeUpdate();
            System.out.println(data);
        } catch (IOException ex) {
            Logger.getLogger(SignUpHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public JSONArray getProvinces(String country_id) throws SQLException{
        JSONArray provinces = new JSONArray();
        String sql = "select id , name from province where country_id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1 , country_id);  
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()){
           int id              = rs.getInt("id");
           String name         = rs.getString("name");
           JSONObject province = new JSONObject();
           
           province.put("id", id);
           province.put("name", name);
           provinces.put(province);
        }
        rs.close();
        stmt.close();

        return provinces;
    }
    
}
