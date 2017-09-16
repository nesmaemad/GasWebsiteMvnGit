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
import javax.servlet.http.HttpServletRequest;
import main.java.utilities.DBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */
public class ReviewsHandler {
        private static Connection connection;
    private static ReviewsHandler reviewsHandler = null;

    
    private ReviewsHandler(){
        connection = DBConnection.getConnection();
    }
    
    public static ReviewsHandler getInstance(){
        if(reviewsHandler == null){
            System.out.println("creating a new reviewsHandler object");
            reviewsHandler = new ReviewsHandler();
        }
        return reviewsHandler;
    }
    public String postReview(HttpServletRequest request){
        System.out.println("inside postReview in ReviewsHandler");
   
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
            String sql = "insert into review (";
            
            int parameterIndex = 1;
            for (String key : params.keySet()) {
                 String value = String.valueOf(params.get(key));
                 System.out.println("key: "+ key + " value: " + value);
                 sql+= key;
                 parameterIndex++;
                 if(parameterIndex <= 8){
                     sql+= ",";
                 }
             }
            sql += ") values ( ? ,? ,? ,? ,? ,? ,? ,?)";
            System.out.println(sql);
            parameterIndex = 1;
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (String key : params.keySet()) {
                 String value = String.valueOf(params.get(key));
                 System.out.println("key: "+ key + " value: " + value);
                 stmt.setString(parameterIndex, value);
                 parameterIndex++;
             }
  
            stmt.executeUpdate();
            return "success";
        }catch(Exception e){
            e.printStackTrace();
        }
        return "failed";
    }
    
    public JSONArray getReviews(HttpServletRequest request){
         JSONArray reviews  = new JSONArray();
         String province_id = request.getParameter("province_id");
         String volume_id   = request.getParameter("volume_id");
         System.out.println("province_id "+province_id);
         System.out.println("volume_id "+volume_id);
         String sql = "select company.name , count(review.company_id)  , MIN(review.price) ,"
                 + "user.user_name , review.rating , review.id from company , review , province , user , volume where "
                 + "review.company_id = company.id and review.user_id = user.id and review.province_id = province.id"
                 + " and review.volume_id = volume.id and province.id = ? and volume.id = ?"
                 + " group by company.name";
         try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, province_id);
            stmt.setString(2, volume_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                JSONObject review = new JSONObject();
                review.put("company_name" , rs.getString(1));
                review.put("reviews_count", rs.getString(2));
                review.put("price"        , rs.getString(3));
                review.put("user_name"    , rs.getString(4));
                review.put("rating"       , rs.getString(5));
                review.put("id"           , rs.getString(6));
                reviews.put(review);
            }
         }catch(Exception e){
             e.printStackTrace();
         }
         System.out.println("reviews "+reviews.toString());
         return reviews;
    }
    
    public JSONArray getCompanies(HttpServletRequest request){
        JSONArray companies = new JSONArray();
        String sql = "select id , country_id , name from company where province_id = ?";

        try {

            String province_id  = request.getParameter("province_id");
            System.out.println("province_id " + province_id);

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, province_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                JSONObject company = new JSONObject();
                company.put("id", rs.getString(1));
                company.put("country_id", rs.getString(2));
                company.put("name", rs.getString(3));
                companies.put(company);
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("companiieeeeeeeeeeeees " + companies.toString());
        return companies;
    }
}
