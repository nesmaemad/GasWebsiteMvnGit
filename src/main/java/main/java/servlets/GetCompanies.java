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
import main.java.handlers.ReviewsHandler;
import main.java.handlers.SignUpHandler;

/**
 *
 * @author DELL
 */
public class GetCompanies extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out              = response.getWriter();
        ReviewsHandler reviewsHandler = ReviewsHandler.getInstance();
        System.out.println("inside GetCompaniesServlet");        
        try {
            out.println(reviewsHandler.getCompanies(request));
        } catch (Exception ex) {
            Logger.getLogger(GetProvinces.class.getName()).log(Level.SEVERE, null, ex);
        } 
        out.close();
    }



}
