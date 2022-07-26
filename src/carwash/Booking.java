/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carwash;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 91950
 */
public class Booking {
    private int id;
    private int userId;
    private int locationId;
    private int serviceId;
    private String timestamp;
    private String status;
    
    public Booking(int id, int userId, int locationId, int serviceId, String timestamp){
        this.id = id;
        this.userId = userId;
        this.locationId = locationId;
        this.serviceId = serviceId;
        this.timestamp = timestamp;
        this.status = "Pending";
    }
    
    public static boolean updateStatus(int bookingId, String status){
        if(DBUtils.getInstance().executeUpdate("UPDATE booking SET status = '"+status+"'"+" WHERE id = '"+bookingId+"'")){
            return true;
        }
        return false;
    }
    
    public static Booking create(String userName, String locationName, String serviceName, String date){
        int userId = 0, locationId = 0, serviceId = 0;
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date dateObject;
//        Timestamp ts = null;
//        try {
//            dateObject = formatter.parse(date);
//            ts = new Timestamp(dateObject.getTime());
//        } catch (ParseException ex) {
//            System.out.println("Parse exception: "+ex.getMessage());
//        }
        ResultSet resultSet = DBUtils.getInstance().executeQuery("SELECT userId FROM user WHERE userName = '"+userName+"'");
        try {
            if(resultSet.next()){
                userId = resultSet.getInt("userId");
            }
        }catch (SQLException ex) {
            System.out.println("SQL exception in User: "+ex.getMessage());
            return null;
        }
        ResultSet resultSet2 = DBUtils.getInstance().executeQuery("SELECT id FROM locations WHERE locationName = '"+locationName+"'");
        try {
            if(resultSet2.next()){
                locationId = resultSet2.getInt("id");
            }
        }catch (SQLException ex) {
            System.out.println("SQL exception in Location: "+ex.getMessage());
            return null;
        }
        ResultSet resultSet3 = DBUtils.getInstance().executeQuery("SELECT id FROM service WHERE serviceName = '"+serviceName+"'");
        try {
            if(resultSet3.next()){
                serviceId = resultSet3.getInt("id");
            }
        }catch (SQLException ex) {
            System.out.println("SQL exception: "+ex.getMessage());
            return null;
        }
        ResultSet resultSet4 = DBUtils.getInstance().executeQuery("SELECT * FROM booking WHERE date = '"+date+"' AND locationId = '"+locationId+"'");
        int count = 0;
        try {
            while(resultSet4.next()){
                count += 1;
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception: "+ex.getMessage());
        }
        if(count >= 5){
            System.out.println("Sorry location not available for given date!");
        }
        else{
            if(DBUtils.getInstance().executeUpdate("INSERT INTO booking(userId, locationId, serviceId, date) VALUES ('"+userId+"', '"+locationId+"', '"+serviceId+"', '"+date+"');")){
                ResultSet resultSet5 = DBUtils.getInstance().executeQuery("SELECT id FROM booking WHERE userId = '"+userId+"' AND locationId = '"+locationId+"' AND serviceId = '"+serviceId+"' AND date = '"+date+"';");
                int bookingId = 0;
                try {
                    if(resultSet5.next()){
                        bookingId = resultSet5.getInt("id");
                    }
                } catch (SQLException ex) {
                    System.out.println("SQL exception: "+ex.getMessage());
                    return null;
                }
                Booking booking = new Booking(bookingId, userId, locationId, serviceId, date);
                return booking;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public static void printUserBookings(int userId){
        try {
            ResultSet resultSet = DBUtils.getInstance().executeQuery("SELECT * FROM booking WHERE userId = '"+userId+"'");
            ResultSetMetaData rsmd = (ResultSetMetaData) resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                        String columnValue = resultSet.getString(i);
                System.out.print(rsmd.getColumnName(i) + ": "+columnValue + " ");
                }
                System.out.println("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void printAllBookings(){
        try {
            ResultSet resultSet = DBUtils.getInstance().executeQuery("SELECT * FROM booking");
            ResultSetMetaData rsmd = (ResultSetMetaData) resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                        String columnValue = resultSet.getString(i);
                System.out.print(rsmd.getColumnName(i) + ": "+columnValue + " ");
                }
                System.out.println("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
