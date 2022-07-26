/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carwash;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author 91950
 */
public class Service {
    private String serviceName;
    private int serviceId;
    
    public Service(String serviceName, int serviceId){
        this.serviceName = serviceName;
        this.serviceId = serviceId;
    }
    
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public static Service create(String serviceName){
        if(DBUtils.getInstance().executeUpdate("INSERT INTO service(serviceName) VALUES ('"+serviceName+"')")){
            ResultSet resultSet = DBUtils.getInstance().executeQuery("SELECT id FROM service WHERE serviceName = '"+serviceName+"'");
            int id = 0;
            try {
                if(resultSet.next()){
                    id = resultSet.getInt("id"); 
                }
            } catch (SQLException ex) {
                System.out.println("SQL exception: "+ex.getMessage());
                return null;
            }
            Service service = new Service(serviceName, id);
            return service;
        }
        return null;
    }
}
