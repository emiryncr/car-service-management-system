/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carrepairservicemanagementsystem.models;

import com.mycompany.carrepairservicemanagementsystem.db.DBConnection;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author emiryncr
 */
public class Vehicle implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int vehicleId;
    private int customerId;
    private String make;
    private String model;
    private int year;
    private String vin;
    private String licensePlate;
    private String color;
    
    private static HashMap<Integer, Vehicle> vehicles = new HashMap<>();
    private static final String BACKUP_FILE = "vehicles_backup.ser";
    
    public Vehicle() {
        this.vehicleId = 0;
        this.customerId = 0;
        this.make = "";
        this.model = "";
        this.year = 0;
        this.vin = "";
        this.licensePlate = "";
        this.color = "";
    }
    
    public Vehicle(int vehicleId, int customerId, String make, String model, int year, String vin, String licensePlate, String color) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.vin = vin;
        this.licensePlate = licensePlate;
        this.color = color;
    }
    
    public void set_vehicle_id(int vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public void set_customer_id(int customerId) {
        this.customerId = customerId;
    }
    
    public void set_make(String make) {
        this.make = make;
    }
    
    public void set_model(String model) {
        this.model = model;
    }
    
    public void set_year(int year) {
        this.year = year;
    }
    
    public void set_vin(String vin) {
        this.vin = vin;
    }
    
    public void set_license_plate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    
    public void set_color(String color) {
        this.color = color;
    }
    
    public int get_vehicle_id() {
        return vehicleId;
    }
    
    public int get_customer_id() {
        return customerId;
    }
    
    public String get_make() {
        return make;
    }
    
    public String get_model() {
        return model;
    }
    
    public int get_year() {
        return year;
    }
    
    public String get_vin() {
        return vin;
    }
    
    public String get_license_plate() {
        return licensePlate;
    }
    
    public String get_color() {
        return color;
    }
    
    public static void add_vehicle(int vehicleId, int customerId, String make, String model, int year, String vin, String licensePlate, String color) {
        if (vehicles.containsKey(vehicleId)) {
            System.out.println("Error: Vehicle ID " + vehicleId + " already exists!");
            return;
        }
        
        Customer customerManager = new Customer();
        HashMap<Integer, Customer> allCustomers = customerManager.get_all_customers();
        
        if (!allCustomers.containsKey(customerId)) {
            System.out.println("Error: Customer ID " + customerId + " not found. Cannot add vehicle.");
            return;
        }
        
        Vehicle newVehicle = new Vehicle(vehicleId, customerId, make, model, year, vin, licensePlate, color);
        vehicles.put(vehicleId, newVehicle);
        System.out.println("Vehicle added successfully with ID: " + vehicleId);
    }
    
    public static void edit_vehicle(int vehicleId, int customerId, String make, String model, int year, String vin, String licensePlate, String color) {
        if (!vehicles.containsKey(vehicleId)) {
            System.out.println("Error: Vehicle ID " + vehicleId + " not found!");
            return;
        }
        
        Customer customerManager = new Customer();
        HashMap<Integer, Customer> allCustomers = customerManager.get_all_customers();
        if (!allCustomers.containsKey(customerId)) {
            System.out.println("Error: Customer ID " + customerId + " not found. Cannot edit vehicle.");
            return;
        }
        
        Vehicle vehicle = vehicles.get(vehicleId);
        vehicle.set_customer_id(customerId);
        vehicle.set_make(make);
        vehicle.set_model(model);
        vehicle.set_year(year);
        vehicle.set_vin(vin);
        vehicle.set_license_plate(licensePlate);
        vehicle.set_color(color);
        
        System.out.println("Vehicle ID " + vehicleId + " updated successfully!");
    }
    
    public static void delete_vehicle(int vehicleId) {
        if (!vehicles.containsKey(vehicleId)) {
            System.out.println("Error: Vehicle ID " + vehicleId + " not found!");
            return;
        }
        
        vehicles.remove(vehicleId);
        System.out.println("Vehicle ID " + vehicleId + " deleted successfully!");
    }
    
    public void list_vehicle(int vehicleId) {
        if (!vehicles.containsKey(vehicleId)) {
            System.out.println("Error: Vehicle ID " + vehicleId + " not found!");
            return;
        }
        
        Vehicle vehicle = vehicles.get(vehicleId);
        System.out.println("\nVehicle Details:");
        System.out.println("Vehicle ID: " + vehicle.get_vehicle_id());
        System.out.println("Customer ID: " + vehicle.get_customer_id());
        System.out.println("Make: " + vehicle.get_make());
        System.out.println("Model: " + vehicle.get_model());
        System.out.println("Year: " + vehicle.get_year());
        System.out.println("VIN: " + vehicle.get_vin());
        System.out.println("License Plate: " + vehicle.get_license_plate());
        System.out.println("Color: " + vehicle.get_color());
    }
    
    public void list_all_vehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found in the system!");
            return;
        }
        
        System.out.println("\nAll Vehicles:");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-5s | %-5s | %-10s | %-10s | %-6s | %-10s | %-8s\n", 
                "ID", "CustID", "Make", "Model", "Year", "License", "Color");
        System.out.println("-------------------------------------------------------------------------");
        
        for (Map.Entry<Integer, Vehicle> entry : vehicles.entrySet()) {
            Vehicle vehicle = entry.getValue();
            System.out.printf("%-5d | %-5d | %-10s | %-10s | %-6d | %-10s | %-8s\n", 
                vehicle.get_vehicle_id(),
                vehicle.get_customer_id(),
                vehicle.get_make(),
                vehicle.get_model(),
                vehicle.get_year(),
                vehicle.get_license_plate(),
                vehicle.get_color());
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Total vehicles: " + vehicles.size());
    }
    
    public void list_customer_vehicles(int customerId) {
        boolean found = false;
        
        System.out.println("\nVehicles for Customer ID " + customerId + ":");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-5s | %-10s | %-10s | %-6s | %-10s | %-8s\n", 
                "ID", "Make", "Model", "Year", "License", "Color");
        System.out.println("-------------------------------------------------------------------------");
        
        for (Map.Entry<Integer, Vehicle> entry : vehicles.entrySet()) {
            Vehicle vehicle = entry.getValue();
            if (vehicle.get_customer_id() == customerId) {
                System.out.printf("%-5d | %-10s | %-10s | %-6d | %-10s | %-8s\n", 
                    vehicle.get_vehicle_id(),
                    vehicle.get_make(),
                    vehicle.get_model(),
                    vehicle.get_year(),
                    vehicle.get_license_plate(),
                    vehicle.get_color());
                found = true;
            }
        }
        
        System.out.println("-------------------------------------------------------------------------");
        if (!found) {
            System.out.println("No vehicles found for Customer ID " + customerId);
        }
    }
    
    public void delete_all_vehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles to delete!");
            return;
        }
        
        int count = vehicles.size();
        vehicles.clear();
        System.out.println(count + " vehicles deleted successfully!");
    }
    
    public static  void backup_vehicles() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
            // Convert HashMap to ArrayList for serialization
            ArrayList<Vehicle> vehicleList = new ArrayList<>(vehicles.values());
            oos.writeObject(vehicleList);
            
            System.out.println("Backup successful! " + vehicles.size() + " vehicles saved to " + BACKUP_FILE);
        } catch (IOException e) {
            System.out.println("Error during backup: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static void restore_vehicles() {
        File backupFile = new File(BACKUP_FILE);
        if (!backupFile.exists()) {
            System.out.println("No backup file found!");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            ArrayList<Vehicle> vehicleList = (ArrayList<Vehicle>) ois.readObject();
            
            // Clear current vehicles and add from backup
            vehicles.clear();
            for (Vehicle vehicle : vehicleList) {
                vehicles.put(vehicle.get_vehicle_id(), vehicle);
            }
            
            System.out.println("Restore successful! " + vehicles.size() + " vehicles restored from backup.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during restore: " + e.getMessage());
        }
    }
    
    //Addition features
    public static HashMap<Integer, Vehicle> get_all_vehicles() {
        return new HashMap<>(vehicles);
    }
    
    public static Vehicle get_vehicle_by_id(int id){
        return vehicles.get(id);
    }
    
    //Mysql CRUD
    public static boolean addVehicleToDB(int vehicleId, int customerId, String make, String model, int year, String vin, String licensePlate, String color) {
    String sql = "INSERT INTO vehicles (vehicle_id, customer_id, make, model, year, vin, license_plate, color) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, vehicleId);
        pstmt.setInt(2, customerId);
        pstmt.setString(3, make);
        pstmt.setString(4, model);
        pstmt.setInt(5, year);
        pstmt.setString(6, vin);
        pstmt.setString(7, licensePlate);
        pstmt.setString(8, color);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Vehicle added successfully to database!");
            return true;
        }
    } catch (SQLException e) {
        System.out.println("Error adding vehicle to database: " + e.getMessage());
    }
    return false;
}

public static boolean editVehicleInDB(int vehicleId, int customerId, String make, String model, int year, String vin, String licensePlate, String color) {
    String sql = "UPDATE vehicles SET customer_id = ?, make = ?, model = ?, year = ?, vin = ?, license_plate = ?, color = ? WHERE vehicle_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, customerId);
        pstmt.setString(2, make);
        pstmt.setString(3, model);
        pstmt.setInt(4, year);
        pstmt.setString(5, vin);
        pstmt.setString(6, licensePlate);
        pstmt.setString(7, color);
        pstmt.setInt(8, vehicleId);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Vehicle ID " + vehicleId + " updated successfully in database!");
            return true;
        } else {
            System.out.println("Vehicle ID " + vehicleId + " not found in database!");
        }
    } catch (SQLException e) {
        System.out.println("Error updating vehicle in database: " + e.getMessage());
    }
    return false;
}

public static boolean deleteVehicleFromDB(int vehicleId) {
    String sql = "DELETE FROM vehicles WHERE vehicle_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, vehicleId);
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Vehicle ID " + vehicleId + " deleted successfully from database!");
            return true;
        } else {
            System.out.println("Vehicle ID " + vehicleId + " not found in database!");
        }
    } catch (SQLException e) {
        System.out.println("Error deleting vehicle from database: " + e.getMessage());
    }
    return false;
}

public static boolean deleteAllVehiclesFromDB() {
    String sql = "DELETE FROM vehicles";

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement()) {

        int rowsDeleted = stmt.executeUpdate(sql);
        System.out.println(rowsDeleted + " vehicles deleted from database!");
        return true;

    } catch (SQLException e) {
        System.out.println("Error deleting all vehicles from database: " + e.getMessage());
    }
    return false;
}

public static Vehicle getVehicleFromDB(int vehicleId) {
    String sql = "SELECT * FROM vehicles WHERE vehicle_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, vehicleId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return new Vehicle(
                rs.getInt("vehicle_id"),
                rs.getInt("customer_id"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getString("vin"),
                rs.getString("license_plate"),
                rs.getString("color")
            );
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving vehicle from database: " + e.getMessage());
    }
    return null;
}

public static ArrayList<Vehicle> getAllVehiclesFromDB() {
    ArrayList<Vehicle> vehicleList = new ArrayList<>();
    String sql = "SELECT * FROM vehicles ORDER BY vehicle_id";

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            Vehicle vehicle = new Vehicle(
                rs.getInt("vehicle_id"),
                rs.getInt("customer_id"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getString("vin"),
                rs.getString("license_plate"),
                rs.getString("color")
            );
            vehicleList.add(vehicle);
        }
        System.out.println("Retrieved " + vehicleList.size() + " vehicles from database.");

    } catch (SQLException e) {
        System.out.println("Error retrieving vehicles from database: " + e.getMessage());
    }
    return vehicleList;
}

public static ArrayList<Vehicle> getVehiclesByCustomerFromDB(int customerId) {
    ArrayList<Vehicle> vehicleList = new ArrayList<>();
    String sql = "SELECT * FROM vehicles WHERE customer_id = ? ORDER BY vehicle_id";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, customerId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Vehicle vehicle = new Vehicle(
                rs.getInt("vehicle_id"),
                rs.getInt("customer_id"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getString("vin"),
                rs.getString("license_plate"),
                rs.getString("color")
            );
            vehicleList.add(vehicle);
        }
        System.out.println("Retrieved " + vehicleList.size() + " vehicles for customer ID " + customerId);

    } catch (SQLException e) {
        System.out.println("Error retrieving vehicles for customer from database: " + e.getMessage());
    }
    return vehicleList;
}

public static void backupVehiclesFromDB() {
    ArrayList<Vehicle> vehicleList = new ArrayList<>();
    String sql = "SELECT * FROM vehicles ORDER BY vehicle_id";

    try (Connection conn = DBConnection.getConnection()) {
        if (conn == null) {
            System.out.println("Database connection failed!");
            return;
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                    rs.getInt("vehicle_id"),
                    rs.getInt("customer_id"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("year"),
                    rs.getString("vin"),
                    rs.getString("license_plate"),
                    rs.getString("color")
                );
                vehicleList.add(vehicle);
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
                oos.writeObject(vehicleList);
                System.out.println("Backup successful! " + vehicleList.size() + " vehicles saved from database to " + BACKUP_FILE);
            } catch (IOException e) {
                System.out.println("Error writing backup file: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error reading from database: " + e.getMessage());
        }

    } catch (SQLException e) {
        System.out.println("Database connection error during backup: " + e.getMessage());
    }
}

@SuppressWarnings("unchecked")
public static void restoreVehiclesToDBFromFile() {
    File backupFile = new File(BACKUP_FILE);
    if (!backupFile.exists()) {
        System.out.println("No backup file found!");
        return;
    }

    ArrayList<Vehicle> vehicleList = new ArrayList<>();

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
        vehicleList = (ArrayList<Vehicle>) ois.readObject();

        if (vehicleList.isEmpty()) {
            System.out.println("No vehicles found in backup file!");
            return;
        }

    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error reading backup file: " + e.getMessage());
        return;
    }

    try (Connection conn = DBConnection.getConnection()) {
        if (conn == null) {
            System.out.println("Database connection failed!");
            return;
        }

        String deleteSql = "DELETE FROM vehicles";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(deleteSql);
        }

        String insertSql = "INSERT INTO vehicles (vehicle_id, customer_id, make, model, year, vin, license_plate, color) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

            int restoredCount = 0;
            for (Vehicle vehicle : vehicleList) {
                pstmt.setInt(1, vehicle.get_vehicle_id());
                pstmt.setInt(2, vehicle.get_customer_id());
                pstmt.setString(3, vehicle.get_make());
                pstmt.setString(4, vehicle.get_model());
                pstmt.setInt(5, vehicle.get_year());
                pstmt.setString(6, vehicle.get_vin());
                pstmt.setString(7, vehicle.get_license_plate());
                pstmt.setString(8, vehicle.get_color());
                pstmt.addBatch();
                restoredCount++;
            }

            pstmt.executeBatch();
            System.out.println("Restore successful! " + restoredCount + " vehicles restored from backup file to database.");

        } catch (SQLException e) {
            System.out.println("Error inserting vehicles to database: " + e.getMessage());
        }

    } catch (SQLException e) {
        System.out.println("Database connection error during restore: " + e.getMessage());
    }
}
}
