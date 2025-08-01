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
public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int partId;
    private String partName;
    private String partNumber;
    private String category;
    private int quantityInStock;
    
    private static HashMap<Integer, Inventory> inventoryItems = new HashMap<>();
    private static final String BACKUP_FILE = "inventory_backup.ser";
    
    public Inventory() {
        this.partId = 0;
        this.partName = "";
        this.partNumber = "";
        this.category = "";
        this.quantityInStock = 0;
    }
    
    public Inventory(int partId, String partName, String partNumber, String category, int quantityInStock) {
        this.partId = partId;
        this.partName = partName;
        this.partNumber = partNumber;
        this.category = category;
        this.quantityInStock = quantityInStock;
    }
    
    public void set_part_id(int partId) {
        this.partId = partId;
    }
    
    public void set_part_name(String partName) {
        this.partName = partName;
    }
    
    public void set_part_number(String partNumber) {
        this.partNumber = partNumber;
    }
    
    public void set_category(String category) {
        this.category = category;
    }
    
    public void set_quantity_in_stock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
    
    public int get_part_id() {
        return partId;
    }
    
    public String get_part_name() {
        return partName;
    }
    
    public String get_part_number() {
        return partNumber;
    }
    
    public String get_category() {
        return category;
    }
    
    public int get_quantity_in_stock() {
        return quantityInStock;
    }
    
    public static void add_inventory_item(int partId, String partName, String partNumber, String category, int quantityInStock) {
        if (inventoryItems.containsKey(partId)) {
            System.out.println("Error: Part ID " + partId + " already exists!");
            return;
        }
        
        Inventory newItem = new Inventory(partId, partName, partNumber, category, quantityInStock);
        inventoryItems.put(partId, newItem);
        System.out.println("Inventory item added successfully with ID: " + partId);
    }
    
    public static void edit_inventory_item(int partId, String partName, String partNumber, String category, int quantityInStock) {
        if (!inventoryItems.containsKey(partId)) {
            System.out.println("Error: Part ID " + partId + " not found!");
            return;
        }
        
        Inventory item = inventoryItems.get(partId);
        item.set_part_name(partName);
        item.set_part_number(partNumber);
        item.set_category(category);
        item.set_quantity_in_stock(quantityInStock);
        
        System.out.println("Inventory item ID " + partId + " updated successfully!");
    }
    
    public static void delete_inventory_item(int partId) {
        if (!inventoryItems.containsKey(partId)) {
            System.out.println("Error: Part ID " + partId + " not found!");
            return;
        }
        
        inventoryItems.remove(partId);
        System.out.println("Inventory item ID " + partId + " deleted successfully!");
    }
    
    public void list_inventory_item(int partId) {
        if (!inventoryItems.containsKey(partId)) {
            System.out.println("Error: Part ID " + partId + " not found!");
            return;
        }
        
        Inventory item = inventoryItems.get(partId);
        System.out.println("\nInventory Item Details:");
        System.out.println("Part ID: " + item.get_part_id());
        System.out.println("Part Name: " + item.get_part_name());
        System.out.println("Part Number: " + item.get_part_number());
        System.out.println("Category: " + item.get_category());
        System.out.println("Quantity in Stock: " + item.get_quantity_in_stock());
    }
    
    public void list_all_inventory_items() {
        if (inventoryItems.isEmpty()) {
            System.out.println("No inventory items found in the system!");
            return;
        }
        
        System.out.println("\nAll Inventory Items:");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-5s | %-25s | %-15s | %-15s | %-10s\n", 
                "ID", "Part Name", "Part Number", "Category", "Quantity");
        System.out.println("-------------------------------------------------------------------------");
        
        for (Map.Entry<Integer, Inventory> entry : inventoryItems.entrySet()) {
            Inventory item = entry.getValue();
            System.out.printf("%-5d | %-25s | %-15s | %-15s | %-10d\n", 
                item.get_part_id(),
                item.get_part_name(),
                item.get_part_number(),
                item.get_category(),
                item.get_quantity_in_stock());
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Total inventory items: " + inventoryItems.size());
    }
    
    public void list_inventory_by_category(String category) {
        boolean found = false;
        
        System.out.println("\nInventory Items in Category: " + category);
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-5s | %-25s | %-15s | %-10s\n", 
                "ID", "Part Name", "Part Number", "Quantity");
        System.out.println("-------------------------------------------------------------------------");
        
        for (Map.Entry<Integer, Inventory> entry : inventoryItems.entrySet()) {
            Inventory item = entry.getValue();
            if (item.get_category().equalsIgnoreCase(category)) {
                System.out.printf("%-5d | %-25s | %-15s | %-10d\n", 
                    item.get_part_id(),
                    item.get_part_name(),
                    item.get_part_number(),
                    item.get_quantity_in_stock());
                found = true;
            }
        }
        
        System.out.println("-------------------------------------------------------------------------");
        if (!found) {
            System.out.println("No inventory items found in category: " + category);
        }
    }
    
    public static void update_stock_level(int partId, int newQuantity) {
        if (!inventoryItems.containsKey(partId)) {
            System.out.println("Error: Part ID " + partId + " not found!");
            return;
        }
        
        Inventory item = inventoryItems.get(partId);
        int oldQuantity = item.get_quantity_in_stock();
        item.set_quantity_in_stock(newQuantity);
        
        System.out.println("Stock level for Part ID " + partId + " (" + item.get_part_name() + ") updated:");
        System.out.println("Previous quantity: " + oldQuantity);
        System.out.println("New quantity: " + newQuantity);
    }
    
    public static void check_low_stock_items(int threshold) {
        ArrayList<Inventory> lowStockItems = new ArrayList<>();
        
        for (Map.Entry<Integer, Inventory> entry : inventoryItems.entrySet()) {
            Inventory item = entry.getValue();
            if (item.get_quantity_in_stock() < threshold) {
                lowStockItems.add(item);
            }
        }
        
        if (lowStockItems.isEmpty()) {
            System.out.println("No items found with stock level below " + threshold);
            return;
        }
        
        System.out.println("\nLow Stock Items (Below " + threshold + " units):");
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-5s | %-25s | %-15s | %-15s | %-10s\n", 
                "ID", "Part Name", "Part Number", "Category", "Quantity");
        System.out.println("-------------------------------------------------------------------------");
        
        for (Inventory item : lowStockItems) {
            System.out.printf("%-5d | %-25s | %-15s | %-15s | %-10d\n", 
                item.get_part_id(),
                item.get_part_name(),
                item.get_part_number(),
                item.get_category(),
                item.get_quantity_in_stock());
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Total low stock items: " + lowStockItems.size());
    }
    
    public static int get_stock_level(int partId) {
    if (inventoryItems.containsKey(partId)) {
        return inventoryItems.get(partId).get_quantity_in_stock();
    } else {
        throw new IllegalArgumentException("Part ID not found: " + partId);
    }
}

    
    public void delete_all_inventory_items() {
        if (inventoryItems.isEmpty()) {
            System.out.println("No inventory items to delete!");
            return;
        }
        
        int count = inventoryItems.size();
        inventoryItems.clear();
        System.out.println(count + " inventory items deleted successfully!");
    }
    
    public static void backup_inventory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
            // Convert HashMap to ArrayList for serialization
            ArrayList<Inventory> itemList = new ArrayList<>(inventoryItems.values());
            oos.writeObject(itemList);
            
            System.out.println("Backup successful! " + inventoryItems.size() + " inventory items saved to " + BACKUP_FILE);
        } catch (IOException e) {
            System.out.println("Error during backup: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static void restore_inventory() {
        File backupFile = new File(BACKUP_FILE);
        if (!backupFile.exists()) {
            System.out.println("No backup file found!");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            ArrayList<Inventory> itemList = (ArrayList<Inventory>) ois.readObject();
            
            inventoryItems.clear();
            for (Inventory item : itemList) {
                inventoryItems.put(item.get_part_id(), item);
            }
            
            System.out.println("Restore successful! " + inventoryItems.size() + " inventory items restored from backup.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during restore: " + e.getMessage());
        }
    }
    
    public static HashMap<Integer, Inventory> get_all_inventories(){
        return new HashMap<>(inventoryItems);
    }
    
    public static Inventory get_inventory_by_id(int id){
        return inventoryItems.get(id);
    }
    
    //Mysql CRUDs
    // MySQL CRUD operations
    public static boolean addInventoryToDB(int partId, String partName, String partNumber, String category, int quantityInStock) {
        String sql = "INSERT INTO inventories (part_id, part_name, part_number, category, quantity_in_stock) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, partId);
            pstmt.setString(2, partName);
            pstmt.setString(3, partNumber);
            pstmt.setString(4, category);
            pstmt.setInt(5, quantityInStock);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Inventory item added successfully to database!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error adding inventory item to database: " + e.getMessage());
        }
        return false;
    }

    public static boolean editInventoryInDB(int partId, String partName, String partNumber, String category, int quantityInStock) {
        String sql = "UPDATE inventories SET part_name = ?, part_number = ?, category = ?, quantity_in_stock = ? WHERE part_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, partName);
            pstmt.setString(2, partNumber);
            pstmt.setString(3, category);
            pstmt.setInt(4, quantityInStock);
            pstmt.setInt(5, partId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Inventory item ID " + partId + " updated successfully in database!");
                return true;
            } else {
                System.out.println("Inventory item ID " + partId + " not found in database!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating inventory item in database: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteInventoryFromDB(int partId) {
        String sql = "DELETE FROM inventories WHERE part_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, partId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Inventory item ID " + partId + " deleted successfully from database!");
                return true;
            } else {
                System.out.println("Inventory item ID " + partId + " not found in database!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting inventory item from database: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteAllInventoryFromDB() {
        String sql = "DELETE FROM inventories";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsDeleted = stmt.executeUpdate(sql);
            System.out.println(rowsDeleted + " inventory items deleted from database!");
            return true;

        } catch (SQLException e) {
            System.out.println("Error deleting all inventory items from database: " + e.getMessage());
        }
        return false;
    }

    public static Inventory getInventoryFromDB(int partId) {
        String sql = "SELECT * FROM inventories WHERE part_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, partId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Inventory(
                    rs.getInt("part_id"),
                    rs.getString("part_name"),
                    rs.getString("part_number"),
                    rs.getString("category"),
                    rs.getInt("quantity_in_stock")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving inventory item from database: " + e.getMessage());
        }
        return null;
    }

    public static ArrayList<Inventory> getAllInventoryFromDB() {
        ArrayList<Inventory> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM inventories ORDER BY part_id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Inventory item = new Inventory(
                    rs.getInt("part_id"),
                    rs.getString("part_name"),
                    rs.getString("part_number"),
                    rs.getString("category"),
                    rs.getInt("quantity_in_stock")
                );
                inventoryList.add(item);
            }
            System.out.println("Retrieved " + inventoryList.size() + " inventory items from database.");

        } catch (SQLException e) {
            System.out.println("Error retrieving inventory items from database: " + e.getMessage());
        }
        return inventoryList;
    }

    public static ArrayList<Inventory> getInventoryByCategoryFromDB(String category) {
        ArrayList<Inventory> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM inventories WHERE category = ? ORDER BY part_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Inventory item = new Inventory(
                    rs.getInt("part_id"),
                    rs.getString("part_name"),
                    rs.getString("part_number"),
                    rs.getString("category"),
                    rs.getInt("quantity_in_stock")
                );
                inventoryList.add(item);
            }
            System.out.println("Retrieved " + inventoryList.size() + " inventory items for category " + category);

        } catch (SQLException e) {
            System.out.println("Error retrieving inventory items by category from database: " + e.getMessage());
        }
        return inventoryList;
    }

    public static void backupInventoryFromDB() {
        ArrayList<Inventory> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM inventories ORDER BY part_id";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    Inventory item = new Inventory(
                        rs.getInt("part_id"),
                        rs.getString("part_name"),
                        rs.getString("part_number"),
                        rs.getString("category"),
                        rs.getInt("quantity_in_stock")
                    );
                    inventoryList.add(item);
                }

                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
                    oos.writeObject(inventoryList);
                    System.out.println("Backup successful! " + inventoryList.size() + " inventory items saved from database to " + BACKUP_FILE);
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
    public static void restoreInventoryToDBFromFile() {
        File backupFile = new File(BACKUP_FILE);
        if (!backupFile.exists()) {
            System.out.println("No backup file found!");
            return;
        }

        ArrayList<Inventory> inventoryList = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            inventoryList = (ArrayList<Inventory>) ois.readObject();

            if (inventoryList.isEmpty()) {
                System.out.println("No inventory items found in backup file!");
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

            String deleteSql = "DELETE FROM inventories";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(deleteSql);
            }

            String insertSql = "INSERT INTO inventories (part_id, part_name, part_number, category, quantity_in_stock) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

                int restoredCount = 0;
                for (Inventory item : inventoryList) {
                    pstmt.setInt(1, item.get_part_id());
                    pstmt.setString(2, item.get_part_name());
                    pstmt.setString(3, item.get_part_number());
                    pstmt.setString(4, item.get_category());
                    pstmt.setInt(5, item.get_quantity_in_stock());
                    pstmt.addBatch();
                    restoredCount++;
                }

                pstmt.executeBatch();
                System.out.println("Restore successful! " + restoredCount + " inventory items restored from backup file to database.");

            } catch (SQLException e) {
                System.out.println("Error inserting inventory items to database: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Database connection error during restore: " + e.getMessage());
        }
    }
}