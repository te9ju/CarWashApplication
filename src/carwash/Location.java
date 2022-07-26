/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carwash;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 91950
 */
public class Location {
    private String locationName;
    private int locationId;
    
    public Location(String locationName, int locationId){
        this.locationName = locationName;
        this.locationId = locationId;
    }
    
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    public static Location create(String locationName){
        if(DBUtils.getInstance().executeUpdate("INSERT INTO locations(locationName) VALUES ('"+locationName+"')")){
            ResultSet resultSet = DBUtils.getInstance().executeQuery("SELECT id FROM locations WHERE locationName = '"+locationName+"'");
            int id = 0;
            try {
                if(resultSet.next()){
                    id = resultSet.getInt("id");
                }
            } catch (SQLException ex) {
                System.out.println("SQL exception: "+ex.getMessage());
                return null;
            }
            Location location = new Location(locationName, id);
            return location;
        }
        return null;
    }
    
    public static ArrayList<Location> searchLocation(String searchWord){
        ArrayList<Location> relevantLocations = new ArrayList<>();
        int locationId;
        String locationName;
        ResultSet resultSet = DBUtils.getInstance().executeQuery("SELECT * FROM locations WHERE locationName LIKE '%"+searchWord+"%';");
        try {
            while(resultSet.next()){
                locationId = resultSet.getInt("id");
                locationName = resultSet.getString("locationName");
                relevantLocations.add(new Location(locationName, locationId));
            }
            return relevantLocations;
        } catch (SQLException ex) {
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
