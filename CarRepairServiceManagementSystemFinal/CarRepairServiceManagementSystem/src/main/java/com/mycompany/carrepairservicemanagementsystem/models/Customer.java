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
public class Customer implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int customerId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    
    // Collection to store customers (in-memory database)
    private static HashMap<Integer, Customer> customers = new HashMap<>();
    private static final String BACKUP_FILE = "customers_backup.ser";

    public Customer() {
        this.customerId = 0;
        this.firstName = "";
        this.lastName = "";
        this.phone = "";
        this.email = "";
        this.address = "";
    }
     
    public Customer(int customerId, String firstName, String lastName, String phone, String email, String address) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public void set_customer_id(int customerId) {
        this.customerId = customerId;
    }
    
    public void set_first_name(String firstName) {
        this.firstName = firstName;
    }
    
    public void set_last_name(String lastName) {
        this.lastName = lastName;
    }
    
    public void set_phone(String phone) {
        this.phone = phone;
    }
    
    public void set_email(String email) {
        this.email = email;
    }
    
    public void set_address(String address) {
        this.address = address;
    }
    
    public int get_customer_id() {
        return customerId;
    }
    
    public String get_first_name() {
        return firstName;
    }
    
    public String get_last_name() {
        return lastName;
    }
    
    public String get_phone() {
        return phone;
    }
    
    public String get_email() {
        return email;
    }
    
    public String get_address() {
        return address;
    }
    
    public static void add_customer(int customerId, String firstName, String lastName, String phone, String email, String address) {
        if (customers.containsKey(customerId)) {
            System.out.println("Error: Customer ID " + customerId + " already exists!");
            return;
        }
        
        Customer newCustomer = new Customer(customerId, firstName, lastName, phone, email, address);
        customers.put(customerId, newCustomer);
        System.out.println("Customer added successfully with ID: " + customerId);
    }
    
    public static void edit_customer(int customerId, String firstName, String lastName, String phone, String email, String address) {
        if (!customers.containsKey(customerId)) {
            System.out.println("Error: Customer ID " + customerId + " not found!");
            return;
        }
        
        Customer customer = customers.get(customerId);
        customer.set_first_name(firstName);
        customer.set_last_name(lastName);
        customer.set_phone(phone);
        customer.set_email(email);
        customer.set_address(address);
        
        System.out.println("Customer ID " + customerId + " updated successfully!");
    }
    
    public static void delete_customer(int customerId) {
        if (!customers.containsKey(customerId)) {
            System.out.println("Error: Customer ID " + customerId + " not found!");
            return;
        }
        
        customers.remove(customerId);
        System.out.println("Customer ID " + customerId + " deleted successfully!");
    }
    
    public void list_customer(int customerId) {
        if (!customers.containsKey(customerId)) {
            System.out.println("Error: Customer ID " + customerId + " not found!");
            return;
        }
        
        Customer customer = customers.get(customerId);
        System.out.println("\nCustomer Details:");
        System.out.println("ID: " + customer.get_customer_id());
        System.out.println("Name: " + customer.get_first_name() + " " + customer.get_last_name());
        System.out.println("Phone: " + customer.get_phone());
        System.out.println("Email: " + customer.get_email());
        System.out.println("Address: " + customer.get_address());
    }
    
    public void list_all_customers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found in the system!");
            return;
        }
        
        System.out.println("\nAll Customers:");
        System.out.println("----------------------------------------------------------");
        System.out.printf("%-5s | %-15s | %-15s | %-15s\n", "ID", "First Name", "Last Name", "Phone");
        System.out.println("----------------------------------------------------------");
        
        for (Map.Entry<Integer, Customer> entry : customers.entrySet()) {
            Customer customer = entry.getValue();
            System.out.printf("%-5d | %-15s | %-15s | %-15s\n", 
                customer.get_customer_id(), 
                customer.get_first_name(), 
                customer.get_last_name(),
                customer.get_phone());
        }
        System.out.println("----------------------------------------------------------");
        System.out.println("Total customers: " + customers.size());
    }
    
    public void delete_all_customers() {
        if (customers.isEmpty()) {
            System.out.println("No customers to delete!");
            return;
        }
        
        int count = customers.size();
        customers.clear();
        System.out.println(count + " customers deleted successfully!");
    }
    
    public static void backup_customers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
            // Convert HashMap to ArrayList for serialization, as we impelement on the top
            ArrayList<Customer> customerList = new ArrayList<>(customers.values());
            oos.writeObject(customerList);
            
            System.out.println("Backup successful! " + customers.size() + " customers saved to " + BACKUP_FILE);
        } catch (IOException e) {
            System.out.println("Error during backup: " + e.getMessage());
        }
    }
    

    @SuppressWarnings("unchecked")
    public static void restore_customers() {
        File backupFile = new File(BACKUP_FILE);
        if (!backupFile.exists()) {
            System.out.println("No backup file found!");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            ArrayList<Customer> customerList = (ArrayList<Customer>) ois.readObject();
            
            // Clear current customers and add from backup
            customers.clear();
            for (Customer customer : customerList) {
                customers.put(customer.get_customer_id(), customer);
            }
            
            System.out.println("Restore successful! " + customers.size() + " customers restored from backup.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during restore: " + e.getMessage());
        }
    }
    
    //Addition features, to use outside of the class
    public static HashMap<Integer, Customer> get_all_customers() {
        return new HashMap<>(customers);
    }
    
    public static Customer get_customer_by_id(int id) {
        return customers.get(id);
    }
    
    //MYSQL CRUDs
    public static boolean addCustomerToDB(int customerId, String firstName, String lastName, String phone, String email, String address) {
        String sql = "INSERT INTO customers (customer_id, first_name, last_name, phone, email, address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.setString(6, address);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer added successfully to database!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error adding customer to database: " + e.getMessage());
        }
        return false;
    }
    
    public static boolean editCustomerInDB(int customerId, String firstName, String lastName, String phone, String email, String address) {
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, phone = ?, email = ?, address = ?, updated_at = CURRENT_TIMESTAMP WHERE customer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.setString(5, address);
            pstmt.setInt(6, customerId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer ID " + customerId + " updated successfully in database!");
                return true;
            } else {
                System.out.println("Customer ID " + customerId + " not found in database!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating customer in database: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteCustomerFromDB(int customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer ID " + customerId + " deleted successfully from database!");
                return true;
            } else {
                System.out.println("Customer ID " + customerId + " not found in database!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting customer from database: " + e.getMessage());
        }
        return false;
    }
    
    public static boolean deleteAllCustomersFromDB() {
        String sql = "DELETE FROM customers";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsDeleted = stmt.executeUpdate(sql);
            System.out.println(rowsDeleted + " customers deleted from database!");
            return true;

        } catch (SQLException e) {
            System.out.println("Error deleting all customers from database: " + e.getMessage());
        }
        return false;
    }

    public static Customer getCustomerFromDB(int customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving customer from database: " + e.getMessage());
        }
        return null;
    }

    public static ArrayList<Customer> getAllCustomersFromDB() {
        ArrayList<Customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY customer_id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address")
                );
                customerList.add(customer);
            }
            System.out.println("Retrieved " + customerList.size() + " customers from database.");

        } catch (SQLException e) {
            System.out.println("Error retrieving customers from database: " + e.getMessage());
        }
        return customerList;
    }
    
    public static void backupCustomersFromDB() {
        ArrayList<Customer> customerList = new ArrayList<>();

        String sql = "SELECT * FROM customers ORDER BY customer_id";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    Customer customer = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                    );
                    customerList.add(customer);
                }

                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
                    oos.writeObject(customerList);
                    System.out.println("Backup successful! " + customerList.size() + " customers saved from database to " + BACKUP_FILE);
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
    public static void restoreCustomersToDBFromFile() {
        File backupFile = new File(BACKUP_FILE);
        if (!backupFile.exists()) {
            System.out.println("No backup file found!");
            return;
        }

        ArrayList<Customer> customerList = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            customerList = (ArrayList<Customer>) ois.readObject();

            if (customerList.isEmpty()) {
                System.out.println("No customers found in backup file!");
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

            String deleteSql = "DELETE FROM customers";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(deleteSql);
            }

            String insertSql = "INSERT INTO customers (customer_id, first_name, last_name, phone, email, address) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

                int restoredCount = 0;
                for (Customer customer : customerList) {
                    pstmt.setInt(1, customer.get_customer_id());
                    pstmt.setString(2, customer.get_first_name());
                    pstmt.setString(3, customer.get_last_name());
                    pstmt.setString(4, customer.get_phone());
                    pstmt.setString(5, customer.get_email());
                    pstmt.setString(6, customer.get_address());
                    pstmt.addBatch();
                    restoredCount++;
                }

                pstmt.executeBatch();
                System.out.println("Restore successful! " + restoredCount + " customers restored from backup file to database.");

            } catch (SQLException e) {
                System.out.println("Error inserting customers to database: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Database connection error during restore: " + e.getMessage());
        }
    }
    
}
