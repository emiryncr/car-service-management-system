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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;

/**
 *
 * @author emiryncr
 */
public class ServiceOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int orderId;
    private int vehicleId;
    private Date dateReceived;
    private Date dateCompleted;
    private String status;
    private String description;
    private float totalCost;
    
    private static HashMap<Integer, ServiceOrder> serviceOrders = new HashMap<>();
    private static final String BACKUP_FILE = "service_orders_backup.ser";
    
    
    public ServiceOrder() {
        this.orderId = 0;
        this.vehicleId = 0;
        this.dateReceived = null;
        this.dateCompleted = null;
        this.status = "";
        this.description = "";
        this.totalCost = 0.0f;
    }
    
    public ServiceOrder(int orderId, int vehicleId, Date dateReceived, Date dateCompleted, 
                       String status, String description, float totalCost) {
        this.orderId = orderId;
        this.vehicleId = vehicleId;
        this.dateReceived = dateReceived;
        this.dateCompleted = dateCompleted;
        this.status = status;
        this.description = description;
        this.totalCost = totalCost;
    }
    
    public void set_order_id(int orderId) {
        this.orderId = orderId;
    }
    
    public void set_vehicle_id(int vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public void set_date_received(Date dateReceived) {
        this.dateReceived = dateReceived;
    }
    
    public void set_date_completed(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }
    
    public void set_status(String status) {
        this.status = status;
    }
    
    public void set_description(String description) {
        this.description = description;
    }
    
    public void set_total_cost(float totalCost) {
        this.totalCost = totalCost;
    }
    
    public int get_order_id() {
        return orderId;
    }
    
    public int get_vehicle_id() {
        return vehicleId;
    }
    
    public Date get_date_received() {
        return dateReceived;
    }
    
    public Date get_date_completed() {
        return dateCompleted;
    }
    
    public String get_status() {
        return status;
    }
    
    public String get_description() {
        return description;
    }
    
    public float get_total_cost() {
        return totalCost;
    }
    
    public static void add_service_order(int orderId, int vehicleId, Date dateReceived, 
                                 Date dateCompleted, String status, String description, float totalCost) {
        if (serviceOrders.containsKey(orderId)) {
            System.out.println("Error: Service Order ID " + orderId + " already exists!");
            return;
        }
        
        Vehicle vehicleManager = new Vehicle();
        HashMap<Integer, Vehicle> allVehicles = vehicleManager.get_all_vehicles();
        
        if (!allVehicles.containsKey(vehicleId)) {
            System.out.println("Error: Vehicle ID " + vehicleId + " not found. Cannot add order.");
            return;
        }
        
        ServiceOrder newOrder = new ServiceOrder(orderId, vehicleId, dateReceived, 
                                               dateCompleted, status, description, totalCost);
        serviceOrders.put(orderId, newOrder);
        System.out.println("Service Order added successfully with ID: " + orderId);
    }
    
    public static void edit_service_order(int orderId, int vehicleId, Date dateReceived, 
                                  Date dateCompleted, String status, String description, float totalCost) {
        if (!serviceOrders.containsKey(orderId)) {
            System.out.println("Error: Service Order ID " + orderId + " not found!");
            return;
        }
        
        Vehicle vehicleManager = new Vehicle();
        HashMap<Integer, Vehicle> allVehicles = vehicleManager.get_all_vehicles();
        
        if (!allVehicles.containsKey(vehicleId)) {
            System.out.println("Error: Vehicle ID " + vehicleId + " not found. Cannot edit order.");
            return;
        }
        
        ServiceOrder order = serviceOrders.get(orderId);
        order.set_vehicle_id(vehicleId);
        order.set_date_received(dateReceived);
        order.set_date_completed(dateCompleted);
        order.set_status(status);
        order.set_description(description);
        order.set_total_cost(totalCost);
        
        System.out.println("Service Order ID " + orderId + " updated successfully!");
    }
    
    public static void delete_service_order(int orderId) {
        if (!serviceOrders.containsKey(orderId)) {
            System.out.println("Error: Service Order ID " + orderId + " not found!");
            return;
        }
        
        serviceOrders.remove(orderId);
        System.out.println("Service Order ID " + orderId + " deleted successfully!");
    }
    
    
    public void list_service_order(int orderId) {
        if (!serviceOrders.containsKey(orderId)) {
            System.out.println("Error: Service Order ID " + orderId + " not found!");
            return;
        }
        
        ServiceOrder order = serviceOrders.get(orderId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        System.out.println("\nService Order Details:");
        System.out.println("Order ID: " + order.get_order_id());
        System.out.println("Vehicle ID: " + order.get_vehicle_id());
        System.out.println("Date Received: " + (order.get_date_received() != null ? dateFormat.format(order.get_date_received()) : "N/A"));
        System.out.println("Date Completed: " + (order.get_date_completed() != null ? dateFormat.format(order.get_date_completed()) : "N/A"));
        System.out.println("Status: " + order.get_status());
        System.out.println("Description: " + order.get_description());
        System.out.println("Total Cost: $" + String.format("%.2f", order.get_total_cost()));
    }
    

    public void list_all_service_orders() {
        if (serviceOrders.isEmpty()) {
            System.out.println("No service orders found in the system!");
            return;
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        System.out.println("\nAll Service Orders:");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-5s | %-12s | %-12s | %-12s | %-20s | %-10s\n", 
                "ID", "VehID", "Received", "Completed", "Status", "Description", "Cost");
        System.out.println("-----------------------------------------------------------------------------------------");
        
        for (Map.Entry<Integer, ServiceOrder> entry : serviceOrders.entrySet()) {
            ServiceOrder order = entry.getValue();
            String receivedDate = order.get_date_received() != null ? dateFormat.format(order.get_date_received()) : "N/A";
            String completedDate = order.get_date_completed() != null ? dateFormat.format(order.get_date_completed()) : "N/A";
            
            System.out.printf("%-5d | %-5d | %-12s | %-12s | %-12s | %-20s | $%-9.2f\n", 
                order.get_order_id(),
                order.get_vehicle_id(),
                receivedDate,
                completedDate,
                order.get_status(),
                (order.get_description().length() > 20 ? order.get_description().substring(0, 17) + "..." : order.get_description()),
                order.get_total_cost());
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Total service orders: " + serviceOrders.size());
    }
    

    public void list_vehicle_service_orders(int vehicleId) {
        boolean found = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        System.out.println("\nService Orders for Vehicle ID " + vehicleId + ":");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-12s | %-12s | %-12s | %-20s | %-10s\n", 
                "ID", "Received", "Completed", "Status", "Description", "Cost");
        System.out.println("-----------------------------------------------------------------------------------------");
        
        for (Map.Entry<Integer, ServiceOrder> entry : serviceOrders.entrySet()) {
            ServiceOrder order = entry.getValue();
            if (order.get_vehicle_id() == vehicleId) {
                String receivedDate = order.get_date_received() != null ? dateFormat.format(order.get_date_received()) : "N/A";
                String completedDate = order.get_date_completed() != null ? dateFormat.format(order.get_date_completed()) : "N/A";
                
                System.out.printf("%-5d | %-12s | %-12s | %-12s | %-20s | $%-9.2f\n", 
                    order.get_order_id(),
                    receivedDate,
                    completedDate,
                    order.get_status(),
                    (order.get_description().length() > 20 ? order.get_description().substring(0, 17) + "..." : order.get_description()),
                    order.get_total_cost());
                found = true;
            }
        }
        
        System.out.println("-----------------------------------------------------------------------------------------");
        if (!found) {
            System.out.println("No service orders found for Vehicle ID " + vehicleId);
        }
    }
   
    public void delete_all_service_orders() {
        if (serviceOrders.isEmpty()) {
            System.out.println("No service orders to delete!");
            return;
        }
        
        int count = serviceOrders.size();
        serviceOrders.clear();
        System.out.println(count + " service orders deleted successfully!");
    }
    
    public static void backup_service_orders() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
            // Convert HashMap to ArrayList for serialization
            ArrayList<ServiceOrder> orderList = new ArrayList<>(serviceOrders.values());
            oos.writeObject(orderList);
            
            System.out.println("Backup successful! " + serviceOrders.size() + " service orders saved to " + BACKUP_FILE);
        } catch (IOException e) {
            System.out.println("Error during backup: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static void restore_service_orders() {
        File backupFile = new File(BACKUP_FILE);
        if (!backupFile.exists()) {
            System.out.println("No backup file found!");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            ArrayList<ServiceOrder> orderList = (ArrayList<ServiceOrder>) ois.readObject();
            
            serviceOrders.clear();
            for (ServiceOrder order : orderList) {
                serviceOrders.put(order.get_order_id(), order);
            }
            
            System.out.println("Restore successful! " + serviceOrders.size() + " service orders restored from backup.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during restore: " + e.getMessage());
        }
    }
        
    public static HashMap<Integer, ServiceOrder> get_all_services(){
        return new HashMap<>(serviceOrders);
    }
    
    public static ServiceOrder get_service_by_id(int id){
        return serviceOrders.get(id);
    }
    
    //Mysql CRUDs
    // MySQL CRUD operations
    public static boolean addServiceOrderToDB(int orderId, int vehicleId, Date dateReceived, 
                                            Date dateCompleted, String status, String description, float totalCost) {
        String sql = "INSERT INTO service_orders (order_id, vehicle_id, date_received, date_completed, status, description, total_cost) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            pstmt.setInt(2, vehicleId);
            pstmt.setTimestamp(3, dateReceived != null ? new java.sql.Timestamp(dateReceived.getTime()) : null);
            pstmt.setTimestamp(4, dateCompleted != null ? new java.sql.Timestamp(dateCompleted.getTime()) : null);
            pstmt.setString(5, status);
            pstmt.setString(6, description);
            pstmt.setFloat(7, totalCost);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Service order added successfully to database!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error adding service order to database: " + e.getMessage());
        }
        return false;
    }

    public static boolean editServiceOrderInDB(int orderId, int vehicleId, Date dateReceived, 
                                             Date dateCompleted, String status, String description, float totalCost) {
        String sql = "UPDATE service_orders SET vehicle_id = ?, date_received = ?, date_completed = ?, status = ?, description = ?, total_cost = ? WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehicleId);
            pstmt.setTimestamp(2, dateReceived != null ? new java.sql.Timestamp(dateReceived.getTime()) : null);
            pstmt.setTimestamp(3, dateCompleted != null ? new java.sql.Timestamp(dateCompleted.getTime()) : null);
            pstmt.setString(4, status);
            pstmt.setString(5, description);
            pstmt.setFloat(6, totalCost);
            pstmt.setInt(7, orderId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Service order ID " + orderId + " updated successfully in database!");
                return true;
            } else {
                System.out.println("Service order ID " + orderId + " not found in database!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating service order in database: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteServiceOrderFromDB(int orderId) {
        String sql = "DELETE FROM service_orders WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Service order ID " + orderId + " deleted successfully from database!");
                return true;
            } else {
                System.out.println("Service order ID " + orderId + " not found in database!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting service order from database: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteAllServiceOrdersFromDB() {
        String sql = "DELETE FROM service_orders";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsDeleted = stmt.executeUpdate(sql);
            System.out.println(rowsDeleted + " service orders deleted from database!");
            return true;

        } catch (SQLException e) {
            System.out.println("Error deleting all service orders from database: " + e.getMessage());
        }
        return false;
    }

    public static ServiceOrder getServiceOrderFromDB(int orderId) {
        String sql = "SELECT * FROM service_orders WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new ServiceOrder(
                    rs.getInt("order_id"),
                    rs.getInt("vehicle_id"),
                    rs.getTimestamp("date_received") != null ? new Date(rs.getTimestamp("date_received").getTime()) : null,
                    rs.getTimestamp("date_completed") != null ? new Date(rs.getTimestamp("date_completed").getTime()) : null,
                    rs.getString("status"),
                    rs.getString("description"),
                    rs.getFloat("total_cost")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving service order from database: " + e.getMessage());
        }
        return null;
    }

    public static ArrayList<ServiceOrder> getAllServiceOrdersFromDB() {
        ArrayList<ServiceOrder> orderList = new ArrayList<>();
        String sql = "SELECT * FROM service_orders ORDER BY order_id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ServiceOrder order = new ServiceOrder(
                    rs.getInt("order_id"),
                    rs.getInt("vehicle_id"),
                    rs.getTimestamp("date_received") != null ? new Date(rs.getTimestamp("date_received").getTime()) : null,
                    rs.getTimestamp("date_completed") != null ? new Date(rs.getTimestamp("date_completed").getTime()) : null,
                    rs.getString("status"),
                    rs.getString("description"),
                    rs.getFloat("total_cost")
                );
                orderList.add(order);
            }
            System.out.println("Retrieved " + orderList.size() + " service orders from database.");

        } catch (SQLException e) {
            System.out.println("Error retrieving service orders from database: " + e.getMessage());
        }
        return orderList;
    }

    public static ArrayList<ServiceOrder> getServiceOrdersByVehicleFromDB(int vehicleId) {
        ArrayList<ServiceOrder> orderList = new ArrayList<>();
        String sql = "SELECT * FROM service_orders WHERE vehicle_id = ? ORDER BY order_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehicleId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ServiceOrder order = new ServiceOrder(
                    rs.getInt("order_id"),
                    rs.getInt("vehicle_id"),
                    rs.getTimestamp("date_received") != null ? new Date(rs.getTimestamp("date_received").getTime()) : null,
                    rs.getTimestamp("date_completed") != null ? new Date(rs.getTimestamp("date_completed").getTime()) : null,
                    rs.getString("status"),
                    rs.getString("description"),
                    rs.getFloat("total_cost")
                );
                orderList.add(order);
            }
            System.out.println("Retrieved " + orderList.size() + " service orders for vehicle ID " + vehicleId);

        } catch (SQLException e) {
            System.out.println("Error retrieving service orders by vehicle from database: " + e.getMessage());
        }
        return orderList;
    }

    public static void backupServiceOrdersFromDB() {
        ArrayList<ServiceOrder> orderList = new ArrayList<>();
        String sql = "SELECT * FROM service_orders ORDER BY order_id";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    ServiceOrder order = new ServiceOrder(
                        rs.getInt("order_id"),
                        rs.getInt("vehicle_id"),
                        rs.getTimestamp("date_received") != null ? new Date(rs.getTimestamp("date_received").getTime()) : null,
                        rs.getTimestamp("date_completed") != null ? new Date(rs.getTimestamp("date_completed").getTime()) : null,
                        rs.getString("status"),
                        rs.getString("description"),
                        rs.getFloat("total_cost")
                    );
                    orderList.add(order);
                }

                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
                    oos.writeObject(orderList);
                    System.out.println("Backup successful! " + orderList.size() + " service orders saved from database to " + BACKUP_FILE);
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
    public static void restoreServiceOrdersToDBFromFile() {
        File backupFile = new File(BACKUP_FILE);
        if (!backupFile.exists()) {
            System.out.println("No backup file found!");
            return;
        }

        ArrayList<ServiceOrder> orderList = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            orderList = (ArrayList<ServiceOrder>) ois.readObject();

            if (orderList.isEmpty()) {
                System.out.println("No service orders found in backup file!");
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

            String deleteSql = "DELETE FROM service_orders";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(deleteSql);
            }

            String insertSql = "INSERT INTO service_orders (order_id, vehicle_id, date_received, date_completed, status, description, total_cost) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

                int restoredCount = 0;
                for (ServiceOrder order : orderList) {
                    pstmt.setInt(1, order.get_order_id());
                    pstmt.setInt(2, order.get_vehicle_id());
                    pstmt.setTimestamp(3, order.get_date_received() != null ? new java.sql.Timestamp(order.get_date_received().getTime()) : null);
                    pstmt.setTimestamp(4, order.get_date_completed() != null ? new java.sql.Timestamp(order.get_date_completed().getTime()) : null);
                    pstmt.setString(5, order.get_status());
                    pstmt.setString(6, order.get_description());
                    pstmt.setFloat(7, order.get_total_cost());
                    pstmt.addBatch();
                    restoredCount++;
                }

                pstmt.executeBatch();
                System.out.println("Restore successful! " + restoredCount + " service orders restored from backup file to database.");

            } catch (SQLException e) {
                System.out.println("Error inserting service orders to database: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Database connection error during restore: " + e.getMessage());
        }
    }
}