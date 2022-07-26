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
public class User {
    private int userId;
    private String userName;

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
    private String password;
    
    
    private User(String userName, String password, int userId){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
    
    public static User create(String userName, String password){
        if(DBUtils.getInstance().executeUpdate("INSERT INTO user(userName, password) VALUES ('"+userName+"', '"+password+"')")){
            ResultSet resultSet = DBUtils.getInstance().executeQuery("SELECT userId FROM user WHERE userName = '"+userName+"'");
            int id = 0;
            try {
                if(resultSet.next()){
                    id = resultSet.getInt("userId");
                }
            } catch (SQLException ex) {
                System.out.println("SQL exception: "+ex.getMessage());
                return null;
            }
            User user = new User(userName, password, id);
            return user;
        }
        return null;
    }
    
    public static User getUser(String userName, String password){
        ResultSet resultSet = DBUtils.getInstance().executeQuery("SELECT * FROM user WHERE userName = '"+userName+"'");
        int id;
        String resultPassword;
        try {
            if(resultSet.next()){
                id = resultSet.getInt("userId");
                resultPassword = resultSet.getString("password");
                if(resultPassword.equals(password)){
                    System.out.println("LOGGED IN SUCCESSFULLY!");
                    User user = new User(userName, password, id);
                    return user;
                }
                else{
                    System.out.println("Incorrect username or password!");
                }
            }
            else{
                System.out.println("User not found!");
            }
        }catch (SQLException ex) {
            System.out.println("SQL exception: "+ex.getMessage());
            return null;
        }
        return null;
    }
}
