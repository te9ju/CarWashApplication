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
public class Admin {
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private String userName;
    private String password;
    
    
    private Admin(String userName, String password, int userId){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
    
    public static Admin create(String adminUserName, String adminPassword){
        if(DBUtils.getInstance().executeUpdate("INSERT INTO admin(adminName, password) VALUES ('"+adminUserName+"', '"+adminPassword+"')")){
            ResultSet resultSet = DBUtils.getInstance().executeQuery("SELECT id FROM admin WHERE adminName = '"+adminUserName+"'");
            int id = 0;
            try {
                if(resultSet.next()){
                    id = resultSet.getInt("id");
                }
            } catch (SQLException ex) {
                System.out.println("SQL exception: "+ex.getMessage());
                return null;
            }
            Admin admin = new Admin(adminUserName, adminPassword, id);
            return admin;
        }
        return null;
    }
    
    public static Admin getAdmin(String adminUserName, String adminPassword){
        ResultSet resultSet = DBUtils.getInstance().executeQuery("SELECT * FROM admin WHERE adminName = '"+adminUserName+"'");
        int id;
        String resultPassword;
        try {
            if(resultSet.next()){
                id = resultSet.getInt("id");
                resultPassword = resultSet.getString("password");
                if(resultPassword.equals(adminPassword)){
                    System.out.println("LOGGED IN SUCCESSFULLY!");
                    Admin admin = new Admin(adminUserName, adminPassword, id);
                    return admin;
                }
                else{
                    System.out.println("Incorrect username or password!");
                }
            }
            else{
                System.out.println("Admin not found!");
            }
        }catch (SQLException ex) {
            System.out.println("SQL exception: "+ex.getMessage());
            return null;
        }
        return null;
    }
}
