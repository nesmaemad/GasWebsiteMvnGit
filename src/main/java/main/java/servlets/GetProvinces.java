/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.java.handlers.SignUpHandler;

/**
 *
 * @author DELL
 */
public class GetProvinces extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out             = response.getWriter();
        String country_id           = request.getParameter("country_id");
        SignUpHandler signUpHandler = SignUpHandler.getInstance();
        System.out.println("inside GetProvinces Servlet");
        System.out.println(country_id);
        
        try {
            out.println(signUpHandler.getProvinces(country_id));
        } catch (SQLException ex) {
            Logger.getLogger(GetProvinces.class.getName()).log(Level.SEVERE, null, ex);
        } 
        out.close();
        
    }



}
