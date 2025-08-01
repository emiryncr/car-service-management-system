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
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int orderItemId;
    private int orderId;
    private int partId;
    private String serviceDescription;
    private int quantity;
    private float unitPrice;
    private float lineTotal;
    
    private static HashMap<Integer, OrderItem> orderItems = new HashMap<>();
    private static final String BACKUP_FILE = "order_items_backup.ser";
    
    private static Inventory inventoryRef;
    
    public OrderItem() {
        this.orderItemId = 0;
        this.orderId = 0;
        this.partId = 0;
        this.serviceDescription = "";
        this.quantity = 0;
        this.unitPrice = 0.0f;
        this.lineTotal = 0.0f;
    }
    
    public OrderItem(int orderItemId, int orderId, int partId, String serviceDescription, 
                    int quantity, float unitPrice, float lineTotal) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.partId = partId;
        this.serviceDescription = serviceDescription;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }
    
    public void setInventoryReference(Inventory inv) {
        inventoryRef = inv;
    }
    
    public void set_order_item_id(int orderItemId) {
        this.orderItemId = orderItemId;
    }
    
    public void set_order_id(int orderId) {
        this.orderId = orderId;
    }
    
    public void set_part_id(int partId) {
        this.partId = partId;
    }
    
    public void set_service_description(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
    
    public void set_quantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void set_unit_price(float unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public void set_line_total(float lineTotal) {
        this.lineTotal = lineTotal;
    }
    
    public int get_order_item_id() {
        return orderItemId;
    }
    
    public int get_order_id() {
        return orderId;
    }
    
    public int get_part_id() {
        return partId;
    }
    
    public String get_service_description() {
        return serviceDescription;
    }
    
    public int get_quantity() {
        return quantity;
    }
    
    public float get_unit_price() {
        return unitPrice;
    }
    
    public float get_line_total() {
        return lineTotal;
    }
    
    public static float calculate_line_total(int quantity, float unitPrice) {
        return quantity * unitPrice;
    }
    
    public static void add_order_item(int orderItemId, int orderId, int partId, String serviceDescription, 
                               int quantity, float unitPrice) {
        if (orderItems.containsKey(orderItemId)) {
            System.out.println("Error: Order Item ID " + orderItemId + " already exists!");
            return;
        }
        
        Inventory inventoryManager = new Inventory();
        HashMap<Integer, Inventory> allInventories = inventoryManager.get_all_inventories();
        
        if (!allInventories.containsKey(partId)) {
            System.out.println("Error: Inventory part ID " + partId + " not found. Cannot add order item.");
            return;
        }
        
        ServiceOrder serviceManager = new ServiceOrder();
        HashMap<Integer, ServiceOrder> allServices = serviceManager.get_all_services();
        
        if (!allServices.containsKey(orderId)) {
            System.out.println("Error: Service order ID " + orderId + " not found. Cannot add order item.");
            return;
        }
        
        float calculatedLineTotal = calculate_line_total(quantity, unitPrice);
        
        OrderItem newItem = new OrderItem(orderItemId, orderId, partId, serviceDescription, 
                                          quantity, unitPrice, calculatedLineTotal);
        orderItems.put(orderItemId, newItem);
        System.out.println("Order item added successfully with ID: " + orderItemId);
        
        if (partId > 0 && inventoryRef != null) {
            update_inventory_after_order(partId, quantity);
        }
    }
    
    public static void edit_order_item(int orderItemId, int orderId, int partId, String serviceDescription, 
                               int quantity, float unitPrice, float lineTotal) {
        if (!orderItems.containsKey(orderItemId)) {
            System.out.println("Error: Order Item ID " + orderItemId + " not found!");
            return;
        }
        
        Inventory inventoryManager = new Inventory();
        HashMap<Integer, Inventory> allInventories = inventoryManager.get_all_inventories();
        
        if (!allInventories.containsKey(partId)) {
            System.out.println("Error: Inventory part ID " + partId + " not found. Cannot add order item.");
            return;
        }
        
        ServiceOrder serviceManager = new ServiceOrder();
        HashMap<Integer, ServiceOrder> allServices = serviceManager.get_all_services();
        
        if (!allServices.containsKey(orderId)) {
            System.out.println("Error: Service order ID " + orderId + " not found. Cannot add order item.");
            return;
        }
        
        float calculatedLineTotal = (lineTotal <= 0) ? calculate_line_total(quantity, unitPrice) : lineTotal;
        
        OrderItem item = orderItems.get(orderItemId);
        
        if (inventoryRef != null && partId > 0) {
            if (item.get_part_id() != partId) {
                if (item.get_part_id() > 0) {
                    update_inventory_after_order(item.get_part_id(), -item.get_quantity());
                }
                update_inventory_after_order(partId, quantity);
            } else if (item.get_quantity() != quantity) {
                int diff = quantity - item.get_quantity();
                if (diff != 0) {
                    update_inventory_after_order(partId, diff);
                }
            }
        }
        
        item.set_order_id(orderId);
        item.set_part_id(partId);
        item.set_service_description(serviceDescription);
        item.set_quantity(quantity);
        item.set_unit_price(unitPrice);
        item.set_line_total(calculatedLineTotal);
        
        System.out.println("Order item with ID " + orderItemId + " updated successfully!");
    }
    
    public static void delete_order_item(int orderItemId) {
        if (!orderItems.containsKey(orderItemId)) {
            System.out.println("Error: Order Item ID " + orderItemId + " not found!");
            return;
        }
        
        OrderItem item = orderItems.get(orderItemId);
        
        if (inventoryRef != null && item.get_part_id() > 0) {
            update_inventory_after_order(item.get_part_id(), -item.get_quantity());
        }
        
        orderItems.remove(orderItemId);
        System.out.println("Order item with ID " + orderItemId + " deleted successfully!");
    }
    
    public void list_order_item(int orderItemId) {
        if (!orderItems.containsKey(orderItemId)) {
            System.out.println("Error: Order Item ID " + orderItemId + " not found!");
            return;
        }
        
        OrderItem item = orderItems.get(orderItemId);
        System.out.println("\nOrder Item Details:");
        System.out.println("Order Item ID: " + item.get_order_item_id());
        System.out.println("Order ID: " + item.get_order_id());
        System.out.println("Part ID: " + item.get_part_id());
        System.out.println("Service Description: " + item.get_service_description());
        System.out.println("Quantity: " + item.get_quantity());
        System.out.println("Unit Price: $" + String.format("%.2f", item.get_unit_price()));
        System.out.println("Line Total: $" + String.format("%.2f", item.get_line_total()));
    }
    
    public void list_all_order_items() {
        if (orderItems.isEmpty()) {
            System.out.println("No order items found in the system!");
            return;
        }
        
        System.out.println("\nAll Order Items:");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-7s | %-7s | %-25s | %-8s | %-10s | %-10s\n", 
                "ID", "OrderID", "PartID", "Description", "Quantity", "Unit Price", "Line Total");
        System.out.println("------------------------------------------------------------------------------------");
        
        for (Map.Entry<Integer, OrderItem> entry : orderItems.entrySet()) {
            OrderItem item = entry.getValue();
            System.out.printf("%-5d | %-7d | %-7d | %-25s | %-8d | $%-9.2f | $%-9.2f\n", 
                item.get_order_item_id(),
                item.get_order_id(),
                item.get_part_id(),
                item.get_service_description(),
                item.get_quantity(),
                item.get_unit_price(),
                item.get_line_total());
        }
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Total order items: " + orderItems.size());
    }
    
    public void list_order_items_by_order(int orderId) {
        boolean found = false;
        float orderTotal = 0.0f;
        
        System.out.println("\nItems for Order ID " + orderId + ":");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-7s | %-25s | %-8s | %-10s | %-10s\n", 
                "ID", "PartID", "Description", "Quantity", "Unit Price", "Line Total");
        System.out.println("------------------------------------------------------------------------------------");
        
        for (Map.Entry<Integer, OrderItem> entry : orderItems.entrySet()) {
            OrderItem item = entry.getValue();
            if (item.get_order_id() == orderId) {
                System.out.printf("%-5d | %-7d | %-25s | %-8d | $%-9.2f | $%-9.2f\n", 
                    item.get_order_item_id(),
                    item.get_part_id(),
                    item.get_service_description(),
                    item.get_quantity(),
                    item.get_unit_price(),
                    item.get_line_total());
                found = true;
                orderTotal += item.get_line_total();
            }
        }
        
        System.out.println("------------------------------------------------------------------------------------");
        if (found) {
            System.out.println("Order Total: $" + String.format("%.2f", orderTotal));
        } else {
            System.out.println("No items found for Order ID " + orderId);
        }
    }
    
    public static void update_inventory_after_order(int partId, int quantity) {
        if (inventoryRef == null) {
            System.out.println("Warning: No inventory reference set. Cannot update inventory.");
            return;
        }
        
        try {
            int currentQuantity = inventoryRef.get_stock_level(partId); 
            int newQuantity = currentQuantity - quantity; 
        
            System.out.println("Updating inventory for part ID " + partId + " by " + 
                              (quantity < 0 ? "adding " : "removing ") + Math.abs(quantity) + " items");
            
            inventoryRef.update_stock_level(partId, newQuantity);
            
        } catch (Exception e) {
            System.out.println("Error updating inventory: " + e.getMessage());
        }
    }
    
    public void delete_all_order_items() {
        if (orderItems.isEmpty()) {
            System.out.println("No order items to delete!");
            return;
        }
        
        int count = orderItems.size();
        orderItems.clear();
        System.out.println(count + " order items deleted successfully!");
    }
    
    public static void backup_order_items() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
            // Convert HashMap to ArrayList for serialization
            ArrayList<OrderItem> orderItemList = new ArrayList<>(orderItems.values());
            oos.writeObject(orderItemList);
            
            System.out.println("Backup successful! " + orderItems.size() + " order items saved to " + BACKUP_FILE);
        } catch (IOException e) {
            System.out.println("Error during backup: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static void restore_order_items() {
        File backupFile = new File(BACKUP_FILE);
        if (!backupFile.exists()) {
            System.out.println("No backup file found!");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            ArrayList<OrderItem> orderItemList = (ArrayList<OrderItem>) ois.readObject();
            
            orderItems.clear();
            for (OrderItem item : orderItemList) {
                orderItems.put(item.get_order_item_id(), item);
            }
            
            System.out.println("Restore successful! " + orderItems.size() + " order items restored from backup.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during restore: " + e.getMessage());
        }
    }
    
    public static HashMap<Integer, OrderItem> get_all_order_items(){
        return new HashMap<>(orderItems);
    }
    
    public static OrderItem get_order_item_by_id(int id){
        return orderItems.get(id);
    }
    
    //Mysql CRUDs
    public static boolean addOrderItemToDB(int orderItemId, int orderId, int partId, 
                                         String serviceDescription, int quantity, 
                                         float unitPrice, float lineTotal) {
        String sql = "INSERT INTO order_items (order_item_id, order_id, part_id, " +
                     "service_description, quantity, unit_price, line_total) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderItemId);
            pstmt.setInt(2, orderId);
            pstmt.setInt(3, partId);
            pstmt.setString(4, serviceDescription);
            pstmt.setInt(5, quantity);
            pstmt.setFloat(6, unitPrice);
            pstmt.setFloat(7, lineTotal);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order item added successfully to database!");

                // Update inventory if partId is valid
                if (partId > 0 && inventoryRef != null) {
                    update_inventory_after_order(partId, quantity);
                }

                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error adding order item to database: " + e.getMessage());
        }
        return false;
    }

    public static boolean editOrderItemInDB(int orderItemId, int orderId, int partId, 
                                          String serviceDescription, int quantity, 
                                          float unitPrice, float lineTotal) {
        String sql = "UPDATE order_items SET order_id = ?, part_id = ?, " +
                     "service_description = ?, quantity = ?, unit_price = ?, " +
                     "line_total = ? WHERE order_item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            pstmt.setInt(2, partId);
            pstmt.setString(3, serviceDescription);
            pstmt.setInt(4, quantity);
            pstmt.setFloat(5, unitPrice);
            pstmt.setFloat(6, lineTotal);
            pstmt.setInt(7, orderItemId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order item ID " + orderItemId + " updated successfully in database!");

                OrderItem existing = getOrderItemFromDB(orderItemId);
                if (existing != null && inventoryRef != null) {
                    if (existing.get_part_id() != partId) {
                        if (existing.get_part_id() > 0) {
                            update_inventory_after_order(existing.get_part_id(), -existing.get_quantity());
                        }
                        update_inventory_after_order(partId, quantity);
                    } else if (existing.get_quantity() != quantity) {
                        int diff = quantity - existing.get_quantity();
                        update_inventory_after_order(partId, diff);
                    }
                }

                return true;
            } else {
                System.out.println("Order item ID " + orderItemId + " not found in database!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating order item in database: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteOrderItemFromDB(int orderItemId) {
        String sql = "DELETE FROM order_items WHERE order_item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            OrderItem item = getOrderItemFromDB(orderItemId);

            pstmt.setInt(1, orderItemId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Order item ID " + orderItemId + " deleted successfully from database!");

                if (item != null && item.get_part_id() > 0 && inventoryRef != null) {
                    update_inventory_after_order(item.get_part_id(), -item.get_quantity());
                }

                return true;
            } else {
                System.out.println("Order item ID " + orderItemId + " not found in database!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting order item from database: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteAllOrderItemsFromDB() {
        String sql = "DELETE FROM order_items";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsDeleted = stmt.executeUpdate(sql);
            System.out.println(rowsDeleted + " order items deleted from database!");
            return true;

        } catch (SQLException e) {
            System.out.println("Error deleting all order items from database: " + e.getMessage());
        }
        return false;
    }

    public static OrderItem getOrderItemFromDB(int orderItemId) {
        String sql = "SELECT * FROM order_items WHERE order_item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderItemId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new OrderItem(
                    rs.getInt("order_item_id"),
                    rs.getInt("order_id"),
                    rs.getInt("part_id"),
                    rs.getString("service_description"),
                    rs.getInt("quantity"),
                    rs.getFloat("unit_price"),
                    rs.getFloat("line_total")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order item from database: " + e.getMessage());
        }
        return null;
    }

    public static ArrayList<OrderItem> getAllOrderItemsFromDB() {
        ArrayList<OrderItem> itemList = new ArrayList<>();
        String sql = "SELECT * FROM order_items ORDER BY order_item_id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                OrderItem item = new OrderItem(
                    rs.getInt("order_item_id"),
                    rs.getInt("order_id"),
                    rs.getInt("part_id"),
                    rs.getString("service_description"),
                    rs.getInt("quantity"),
                    rs.getFloat("unit_price"),
                    rs.getFloat("line_total")
                );
                itemList.add(item);
            }
            System.out.println("Retrieved " + itemList.size() + " order items from database.");

        } catch (SQLException e) {
            System.out.println("Error retrieving order items from database: " + e.getMessage());
        }
        return itemList;
    }

    public static ArrayList<OrderItem> getOrderItemsByOrderFromDB(int orderId) {
        ArrayList<OrderItem> itemList = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ? ORDER BY order_item_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem(
                    rs.getInt("order_item_id"),
                    rs.getInt("order_id"),
                    rs.getInt("part_id"),
                    rs.getString("service_description"),
                    rs.getInt("quantity"),
                    rs.getFloat("unit_price"),
                    rs.getFloat("line_total")
                );
                itemList.add(item);
            }
            System.out.println("Retrieved " + itemList.size() + " order items for order ID " + orderId);

        } catch (SQLException e) {
            System.out.println("Error retrieving order items by order from database: " + e.getMessage());
        }
        return itemList;
    }

    public static void backupOrderItemsFromDB() {
        ArrayList<OrderItem> itemList = new ArrayList<>();
        String sql = "SELECT * FROM order_items ORDER BY order_item_id";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Database connection failed!");
                return;
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    OrderItem item = new OrderItem(
                        rs.getInt("order_item_id"),
                        rs.getInt("order_id"),
                        rs.getInt("part_id"),
                        rs.getString("service_description"),
                        rs.getInt("quantity"),
                        rs.getFloat("unit_price"),
                        rs.getFloat("line_total")
                    );
                    itemList.add(item);
                }

                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
                    oos.writeObject(itemList);
                    System.out.println("Backup successful! " + itemList.size() + " order items saved from database to " + BACKUP_FILE);
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
    public static void restoreOrderItemsToDBFromFile() {
        File backupFile = new File(BACKUP_FILE);
        if (!backupFile.exists()) {
            System.out.println("No backup file found!");
            return;
        }

        ArrayList<OrderItem> itemList = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            itemList = (ArrayList<OrderItem>) ois.readObject();

            if (itemList.isEmpty()) {
                System.out.println("No order items found in backup file!");
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

            String deleteSql = "DELETE FROM order_items";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(deleteSql);
            }

            String insertSql = "INSERT INTO order_items (order_item_id, order_id, part_id, service_description, quantity, unit_price, line_total) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

                int restoredCount = 0;
                for (OrderItem item : itemList) {
                    pstmt.setInt(1, item.get_order_item_id());
                    pstmt.setInt(2, item.get_order_id());
                    pstmt.setInt(3, item.get_part_id());
                    pstmt.setString(4, item.get_service_description());
                    pstmt.setInt(5, item.get_quantity());
                    pstmt.setFloat(6, item.get_unit_price());
                    pstmt.setFloat(7, item.get_line_total());
                    pstmt.addBatch();
                    restoredCount++;
                }

                pstmt.executeBatch();
                System.out.println("Restore successful! " + restoredCount + " order items restored from backup file to database.");

            } catch (SQLException e) {
                System.out.println("Error inserting order items to database: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Database connection error during restore: " + e.getMessage());
        }
    }
    
}