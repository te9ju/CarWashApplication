/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carwash;
import java.sql.*;
/**
 *
 * @author 91950
 */
public class DBUtils {
    
    private static DBUtils instance = null;
    private Connection connection;
    
    private DBUtils() 
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");  
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/carwash","root","123456789");
        }
        catch(ClassNotFoundException e){
           System.out.println("Class not found exception: "+e.getMessage());
        }
        catch(SQLException e){
           System.out.println("SQL exception: "+e.getMessage()); 
        }
        
    }
    
    public static DBUtils getInstance()
    {
        if (instance == null)
            instance = new DBUtils();
 
        return instance;
    }
    
    public boolean executeUpdate(String query){
        try {
            Statement stmt = connection.createStatement();  
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("SQL exception: "+ex.getMessage());
            return false;
        }
        return true;
    }
    
    public ResultSet executeQuery(String query){
        try {
            PreparedStatement p = connection.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            return rs;
        } catch (SQLException ex) {
            System.out.println("SQL exception: "+ex.getMessage());
            return null;
        }
    }
    
}
