/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package carwash;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Timestamp;
/**
 *
 * @author 91950
 */
public class CarWash {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
//        Class.forName("com.mysql.jdbc.Driver");  
//        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carwash","root","123456789");  
//        Statement stmt = con.createStatement();  
//        String query = "INSERT INTO user (userName, password) "+"VALUES ('Tej','password1234')";
//        stmt.executeUpdate(query);
//        System.out.println("Record is inserted in the table successfully...");
        
        int option;
        String userName, password, adminUserName, adminPassword;
        Scanner scanner = new Scanner(System.in);
        System.out.println("CAR WASH APPLICATION");
        do{
            System.out.println("MENU");
            System.out.println("1. USER LOGIN");
            System.out.println("2. USER SIGNUP");
            System.out.println("3. ADMIN LOGIN");
            System.out.println("4. ADMIN SIGNUP");
            System.out.println("5. EXIT");
            System.out.println("Enter your choice: ");
            option = scanner.nextInt();
            switch(option){
                case 1: System.out.println("USER LOGIN");
                        scanner.nextLine();
                        System.out.println("Username: ");
                        userName = scanner.nextLine();
                        System.out.println("Password: ");
                        password = scanner.nextLine();
                        User currentUser = User.getUser(userName, password);
                        userPage(currentUser);
                        currentUser = null;
                        break;

                case 2: System.out.println("USER SIGNUP");
                        scanner.nextLine();
                        System.out.println("Username: ");
                        userName = scanner.nextLine();
                        System.out.println("Password: ");
                        password = scanner.nextLine();
                        User.create(userName, password);
                        break;

                case 3: System.out.println("ADMIN LOGIN");
                        scanner.nextLine();
                        System.out.println("Username: ");
                        adminUserName = scanner.nextLine();
                        System.out.println("Password: ");
                        adminPassword = scanner.nextLine();
                        Admin currentAdmin = Admin.getAdmin(adminUserName, adminPassword);
                        adminPage();
                        currentAdmin = null;
                        break;

                case 4: System.out.println("ADMIN SIGNUP");
                        scanner.nextLine();
                        System.out.println("Username: ");
                        adminUserName = scanner.nextLine();
                        System.out.println("Password: ");
                        adminPassword = scanner.nextLine();
                        Admin.create(adminUserName, adminPassword);
                        break;
                        
                case 5: System.exit(0);
                        break;
            } 
        }while(option == 1 || option == 2 || option == 3 || option == 4);
        
    }
    
    public static void adminPage(){
        Scanner scanner = new Scanner(System.in);
        int option, bookingId;
        String locationName, serviceName, status;
        do{
            System.out.println("ADMIN PAGE");
            System.out.println("1. Add Location");
            System.out.println("2. Add Service");
            System.out.println("3. View All Bookings");
            System.out.println("4. Accept/Reject Booking");
            System.out.println("5. Log out");
            System.out.println("Enter your choice: ");
            option = scanner.nextInt();
            switch(option){
                case 1: scanner.nextLine();
                        System.out.println("Location name: ");
                        locationName = scanner.nextLine();
                        Location.create(locationName);
                        break;
                        
                case 2: scanner.nextLine();
                        System.out.println("Service name: ");
                        serviceName = scanner.nextLine();
                        Service.create(serviceName);
                        break;
                        
                case 3: Booking.printAllBookings();
                        break;
                        
                case 4: scanner.nextLine();
                        System.out.println("Booking Id: ");
                        bookingId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Status (Accepted/Rejected): ");
                        status = scanner.nextLine();
                        if(Booking.updateStatus(bookingId, status)){
                            System.out.println("Status for booking with booking id "+bookingId+" successfully changed to "+status);
                        }
                        break;
                        
                case 5: return;
            }
        }while(option == 1 || option == 2 || option == 3 || option == 4);
    }
    
    public static void userPage(User currentUser){
        Scanner scanner = new Scanner(System.in);
        int option, userId;
        String searchWord, userName, locationName, serviceName ,date;
        do{
            System.out.println("USER PAGE");
            System.out.println("1. Search Location");
            System.out.println("2. Book");
            System.out.println("3. View Booking Status");
            System.out.println("4. Log out");
            System.out.println("Enter your choice: ");
            option = scanner.nextInt();
            switch(option){
                case 1: scanner.nextLine();
                        System.out.println("Location keyword: ");
                        searchWord = scanner.nextLine();
                        ArrayList<Location> locations = Location.searchLocation(searchWord);
                        System.out.println("Results are: ");
                        for(int i = 0; i < locations.size(); i++){
                            System.out.println(locations.get(i).getLocationName());
                        }
                        break;
                        
                case 2: scanner.nextLine();
                        userName = currentUser.getUserName();
                        System.out.println("Location name: ");
                        locationName = scanner.nextLine();
                        System.out.println("Service name: ");
                        serviceName = scanner.nextLine();
                        System.out.println("Date (YYYY-MM-DD HH:MM:SS): ");
                        date = scanner.nextLine();
                        Booking.create(userName, locationName, serviceName, date);
                        break;
                        
                case 3: userId = currentUser.getUserId();
                        Booking.printUserBookings(userId);
                        break;
                       
                case 4: return;

            }
        }while(option == 1 || option == 2 || option == 3 || option == 4);
    }
    
}
