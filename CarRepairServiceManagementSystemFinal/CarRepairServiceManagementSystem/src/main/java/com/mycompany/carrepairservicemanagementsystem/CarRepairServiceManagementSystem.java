/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.carrepairservicemanagementsystem;

import com.mycompany.carrepairservicemanagementsystem.db.DBConnection;
import com.mycompany.carrepairservicemanagementsystem.models.Customer;
import com.mycompany.carrepairservicemanagementsystem.models.Inventory;
import com.mycompany.carrepairservicemanagementsystem.models.OrderItem;
import com.mycompany.carrepairservicemanagementsystem.models.ServiceOrder;
import com.mycompany.carrepairservicemanagementsystem.models.Vehicle;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author emiryncr
 */
public class CarRepairServiceManagementSystem {

    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("  CAR REPAIR SERVICE MANAGEMENT SYSTEM");
        System.out.println("=============================================");

        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed!");
        }
        
        //Tests
        //testCustomer();
        //testVehicle();
        //testServiceOrder();
        //testInventory();
        //testOrderItem();
    }
    
    private static void testCustomer() {
        System.out.println("\n=============================================");
        System.out.println("  RUNNING CUSTOMER TEST");
        System.out.println("=============================================");
        
        Customer customer = new Customer();
        
        System.out.println("\nSTEP 1: Adding all test customer data");
        System.out.println("---------------------------------------------");
        // Add all sample data
        customer.add_customer(1, "John", "Smith", "555-123-4567", "john.smith@email.com", "123 Main St, Anytown, ST 12345");
        customer.add_customer(2, "Sarah", "Johnson", "555-234-5678", "sarah.j@email.com", "456 Oak Ave, Someville, ST 23456");
        customer.add_customer(3, "Michael", "Davis", "555-345-6789", "m.davis@email.com", "789 Pine Rd, Othercity, ST 34567");
        customer.add_customer(4, "Jessica", "Brown", "555-456-7890", "jbrown@email.com", "101 Maple Dr, Newtown, ST 45678");
        customer.add_customer(5, "David", "Wilson", "555-567-8901", "dwilson@email.com", "202 Cedar Ln, Oldville, ST 56789");
        
        System.out.println("\nSTEP 2: Listing all customers");
        System.out.println("---------------------------------------------");
        // List all customers
        customer.list_all_customers();
        
        System.out.println("\nSTEP 3: Editing the 2nd customer (ID: 2)");
        System.out.println("---------------------------------------------");
        // Edit the 2nd object
        customer.edit_customer(2, "Sarah", "Thompson", "555-987-6543", "sarah.thompson@email.com", "789 Willow St, Someville, ST 23456");
        System.out.println("Customer #2 updated with new last name, phone, email, and address");
        
        System.out.println("\nSTEP 4: Listing all customers after edit");
        System.out.println("---------------------------------------------");
        // List all customers after edit
        customer.list_all_customers();
        
        System.out.println("\nSTEP 5: Backing up customers");
        System.out.println("---------------------------------------------");
        // Backup customers
        customer.backup_customers();
        
        System.out.println("\nSTEP 6: Deleting the 3rd customer (ID: 3)");
        System.out.println("---------------------------------------------");
        // Delete the 3rd object
        customer.delete_customer(3);
        
        System.out.println("\nSTEP 7: Listing all customers after deletion");
        System.out.println("---------------------------------------------");
        // List all customers after deletion
        customer.list_all_customers();
        
        System.out.println("\nSTEP 8: Restoring customers from backup");
        System.out.println("---------------------------------------------");
        // Restore from backup
        customer.restore_customers();
        
        System.out.println("\nSTEP 9: Listing all customers after restore");
        System.out.println("---------------------------------------------");
        // List all customers after restore
        customer.list_all_customers();
        
        System.out.println("\n=============================================");
        System.out.println("  CUSTOMER TEST COMPLETED");
        System.out.println("=============================================");
    }
    private static void testVehicle() {
        System.out.println("\n=============================================");
        System.out.println("  RUNNING VEHICLE TEST");
        System.out.println("=============================================");

        Vehicle vehicle = new Vehicle();

        System.out.println("\nSTEP 1: Adding all test vehicle data");
        System.out.println("---------------------------------------------");
        // Add all sample data
        vehicle.add_vehicle(1, 1, "Toyota", "Camry", 2018, "4T1B11HK1KU123456", "ABC123", "Silver");
        vehicle.add_vehicle(2, 1, "Honda", "Civic", 2020, "2HGFC2F53LH123456", "XYZ789", "Blue");
        vehicle.add_vehicle(3, 2, "Ford", "F-150", 2019, "1FTEW1E51JFB12345", "DEF456", "Red");
        vehicle.add_vehicle(4, 3, "Chevrolet", "Malibu", 2017, "1G1ZD5ST7JF123456", "GHI789", "Black");
        vehicle.add_vehicle(5, 4, "Nissan", "Altima", 2021, "1N4BL4BV3MC123456", "JKL012", "White");

        System.out.println("\nSTEP 2: Listing all vehicles");
        System.out.println("---------------------------------------------");
        // List all vehicles
        vehicle.list_all_vehicles();

        System.out.println("\nSTEP 3: Editing the 2nd vehicle (ID: 2)");
        System.out.println("---------------------------------------------");
        // Edit the 2nd object
        vehicle.edit_vehicle(2, 1, "Honda", "Accord", 2021, "2HGFC2F53LH789012", "XYZ999", "Green");
        System.out.println("Vehicle #2 updated with new model, year, VIN, license plate, and color");

        System.out.println("\nSTEP 4: Listing all vehicles after edit");
        System.out.println("---------------------------------------------");
        // List all vehicles after edit
        vehicle.list_all_vehicles();

        System.out.println("\nSTEP 5: Backing up vehicles");
        System.out.println("---------------------------------------------");
        // Backup vehicles
        vehicle.backup_vehicles();

        System.out.println("\nSTEP 6: Deleting the 3rd vehicle (ID: 3)");
        System.out.println("---------------------------------------------");
        // Delete the 3rd object
        vehicle.delete_vehicle(3);

        System.out.println("\nSTEP 7: Listing all vehicles after deletion");
        System.out.println("---------------------------------------------");
        // List all vehicles after deletion
        vehicle.list_all_vehicles();

        System.out.println("\nSTEP 8: Restoring vehicles from backup");
        System.out.println("---------------------------------------------");
        // Restore from backup
        vehicle.restore_vehicles();

        System.out.println("\nSTEP 9: Listing all vehicles after restore");
        System.out.println("---------------------------------------------");
        // List all vehicles after restore
        vehicle.list_all_vehicles();

        // Optional: Show vehicles by customer
        System.out.println("\nBonus: List vehicles by Customer ID 1");
        System.out.println("---------------------------------------------");
        vehicle.list_customer_vehicles(1);

        System.out.println("\n=============================================");
        System.out.println("  VEHICLE TEST COMPLETED");
        System.out.println("=============================================");
    }
    private static void testServiceOrder() {
        System.out.println("\n=============================================");
        System.out.println("  RUNNING SERVICE ORDER TEST");
        System.out.println("=============================================");

        ServiceOrder serviceOrder = new ServiceOrder();

        System.out.println("\nSTEP 1: Adding all test service order data");
        System.out.println("---------------------------------------------");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null, date2 = null, date3 = null, date4 = null, date5 = null, date6= null, date7= null, date8= null;
        try {
            date1 = dateFormat.parse("2023-05-01");
            date2 = dateFormat.parse("2023-05-02"); 
            date3 = dateFormat.parse("2023-05-03");
            date4 = dateFormat.parse("2023-05-04");
            date5 = dateFormat.parse("2023-05-05");
            date6 = dateFormat.parse("2023-05-07");
            date7 = dateFormat.parse("2023-05-10");
            date8 = dateFormat.parse("2023-05-12");
        } catch (Exception e) {
            System.out.println("Error parsing dates: " + e.getMessage());
        }

        // Add all sample data
        serviceOrder.add_service_order(1, 1, date1, date3, "Completed", "Regular maintenance and oil change", 150.75f);
        serviceOrder.add_service_order(2, 2, date2, date4, "Completed", "Brake pad replacement", 225.50f);
        serviceOrder.add_service_order(3, 3, date5, date6, "Completed", "Transmission fluid change", 189.99f);
        serviceOrder.add_service_order(4, 4, date7, null, "In Progress", "Engine diagnostic and tune-up", 350.00f);
        serviceOrder.add_service_order(5, 5, date8, null, "Pending", "A/C system repair", 275.25f);

        System.out.println("\nSTEP 2: Listing all service orders");
        System.out.println("---------------------------------------------");
        // List all service orders
        serviceOrder.list_all_service_orders();

        System.out.println("\nSTEP 3: Editing the 2nd service order (ID: 2)");
        System.out.println("---------------------------------------------");
        // Edit the 2nd object
        try {
            Date newCompletionDate = dateFormat.parse("2023-05-05");
            serviceOrder.edit_service_order(2, 2, date2, newCompletionDate, "Completed", 
                                          "Brake pad replacement and rotor resurfacing", 295.75f);
        } catch (Exception e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
        System.out.println("Service Order #2 updated with new completion date, description, and cost");

        System.out.println("\nSTEP 4: Listing all service orders after edit");
        System.out.println("---------------------------------------------");
        // List all service orders after edit
        serviceOrder.list_all_service_orders();

        System.out.println("\nSTEP 5: Backing up service orders");
        System.out.println("---------------------------------------------");
        // Backup service orders
        serviceOrder.backup_service_orders();

        System.out.println("\nSTEP 6: Deleting the 3rd service order (ID: 3)");
        System.out.println("---------------------------------------------");
        // Delete the 3rd object
        serviceOrder.delete_service_order(3);

        System.out.println("\nSTEP 7: Listing all service orders after deletion");
        System.out.println("---------------------------------------------");
        // List all service orders after deletion
        serviceOrder.list_all_service_orders();

        System.out.println("\nSTEP 8: Restoring service orders from backup");
        System.out.println("---------------------------------------------");
        // Restore from backup
        serviceOrder.restore_service_orders();

        System.out.println("\nSTEP 9: Listing all service orders after restore");
        System.out.println("---------------------------------------------");
        // List all service orders after restore
        serviceOrder.list_all_service_orders();

        System.out.println("\nSTEP 10: Listing service orders for Vehicle ID 1");
        System.out.println("---------------------------------------------");
        // List service orders for a specific vehicle
        serviceOrder.list_vehicle_service_orders(1);

        System.out.println("\n=============================================");
        System.out.println("  SERVICE ORDER TEST COMPLETED");
        System.out.println("=============================================");
    }
    private static void testInventory() {
        System.out.println("\n=============================================");
        System.out.println("  RUNNING INVENTORY TEST");
        System.out.println("=============================================");

        Inventory inventory = new Inventory();

        System.out.println("\nSTEP 1: Adding all test inventory data");
        System.out.println("---------------------------------------------");
        // Add all sample data
        inventory.add_inventory_item(1, "Synthetic Oil", "OIL-SYN-5W30", "Fluids", 45);
        inventory.add_inventory_item(2, "Oil Filter", "FLT-OIL-T1", "Filters", 32);
        inventory.add_inventory_item(3, "Air Filter", "FLT-AIR-H1", "Filters", 28);
        inventory.add_inventory_item(4, "Brake Pads - Toyota", "BRK-PAD-T1", "Brakes", 16);
        inventory.add_inventory_item(5, "Brake Service Kit", "BRK-SRV-KIT", "Service Kits", 8);
        inventory.add_inventory_item(6, "Transmission Fluid", "FLD-TRANS-1", "Fluids", 22);
        inventory.add_inventory_item(7, "Basic Service Labor", "SVC-BASIC", "Labor", 999);
        inventory.add_inventory_item(8, "Diagnostic Fee", "SVC-DIAG", "Services", 999);
        inventory.add_inventory_item(9, "A/C Refrigerant R134a", "AC-R134A", "A/C Parts", 15);
        inventory.add_inventory_item(10, "Wiper Blades - Standard", "WPR-STD-18", "Exterior", 40);

        System.out.println("\nSTEP 2: Listing all inventory items");
        System.out.println("---------------------------------------------");
        // List all inventory items
        inventory.list_all_inventory_items();

        System.out.println("\nSTEP 3: Listing inventory by category");
        System.out.println("---------------------------------------------");
        // List inventory by category
        inventory.list_inventory_by_category("Filters");

        System.out.println("\nSTEP 4: Checking low stock items (threshold: 20)");
        System.out.println("---------------------------------------------");
        // Check low stock items
        inventory.check_low_stock_items(20);

        System.out.println("\nSTEP 5: Editing the 2nd inventory item (ID: 2)");
        System.out.println("---------------------------------------------");
        // Edit the 2nd object
        inventory.edit_inventory_item(2, "Premium Oil Filter", "FLT-OIL-PREM", "Filters", 25);
        System.out.println("Inventory item #2 updated with new name, part number, and quantity");

        System.out.println("\nSTEP 6: Listing all inventory items after edit");
        System.out.println("---------------------------------------------");
        // List all inventory items after edit
        inventory.list_all_inventory_items();

        System.out.println("\nSTEP 7: Backing up inventory");
        System.out.println("---------------------------------------------");
        // Backup inventory
        inventory.backup_inventory();

        System.out.println("\nSTEP 8: Deleting the 3rd inventory item (ID: 3)");
        System.out.println("---------------------------------------------");
        // Delete the 3rd object
        inventory.delete_inventory_item(3);

        System.out.println("\nSTEP 9: Listing all inventory items after deletion");
        System.out.println("---------------------------------------------");
        // List all inventory items after deletion
        inventory.list_all_inventory_items();

        System.out.println("\nSTEP 10: Updating stock level for part ID 4");
        System.out.println("---------------------------------------------");
        // Update stock level
        inventory.update_stock_level(4, 30);

        System.out.println("\nSTEP 11: Restoring inventory from backup");
        System.out.println("---------------------------------------------");
        // Restore from backup
        inventory.restore_inventory();

        System.out.println("\nSTEP 12: Listing all inventory items after restore");
        System.out.println("---------------------------------------------");
        // List all inventory items after restore
        inventory.list_all_inventory_items();

        System.out.println("\n=============================================");
        System.out.println("  INVENTORY TEST COMPLETED");
        System.out.println("=============================================");
    }
    private static void testOrderItem() {
        System.out.println("\n=============================================");
        System.out.println("  RUNNING ORDER ITEM TEST");
        System.out.println("=============================================");

        OrderItem orderItem = new OrderItem();

        System.out.println("\nSTEP 1: Adding all test order item data");
        System.out.println("---------------------------------------------");
        // Add all sample data
        orderItem.add_order_item(1, 1, 1, "Oil change service", 1, 49.99f);
        orderItem.add_order_item(2, 1, 2, "Oil filter replacement", 1, 15.99f);
        orderItem.add_order_item(3, 1, 3, "Air filter replacement", 1, 24.99f);
        orderItem.add_order_item(4, 2, 4, "Front brake pads", 2, 45.50f);
        orderItem.add_order_item(5, 2, 5, "Brake service labor", 1, 85.00f);
        orderItem.add_order_item(6, 3, 6, "Transmission fluid", 1, 89.99f);
        orderItem.add_order_item(7, 3, 7, "Transmission service labor", 1, 100.00f);
        orderItem.add_order_item(8, 4, 8, "Engine diagnostic", 1, 120.00f);
        orderItem.add_order_item(9, 5, 9, "A/C refrigerant", 1, 75.25f);

        System.out.println("\nSTEP 2: Listing all order items");
        System.out.println("---------------------------------------------");
        // List all order items
        orderItem.list_all_order_items();

        System.out.println("\nSTEP 3: Listing order items by order ID");
        System.out.println("---------------------------------------------");
        // List order items by order ID
        orderItem.list_order_items_by_order(1); // List all items for order ID 1

        System.out.println("\nSTEP 4: Editing the 2nd order item (ID: 2)");
        System.out.println("---------------------------------------------");
        // Edit the 2nd object
        orderItem.edit_order_item(2, 1, 2, "Premium oil filter replacement", 1, 19.99f, 19.99f);
        System.out.println("Order item #2 updated with new description and price");

        System.out.println("\nSTEP 5: Listing all order items after edit");
        System.out.println("---------------------------------------------");
        // List all order items after edit
        orderItem.list_all_order_items();

        System.out.println("\nSTEP 6: Testing line total calculation");
        System.out.println("---------------------------------------------");
        // Test calculate_line_total method
        float calculatedTotal = orderItem.calculate_line_total(3, 25.99f);
        System.out.println("Calculated line total for 3 items at $25.99 each: $" + String.format("%.2f", calculatedTotal));

        System.out.println("\nSTEP 7: Backing up order items");
        System.out.println("---------------------------------------------");
        // Backup order items
        orderItem.backup_order_items();

        System.out.println("\nSTEP 8: Deleting the 3rd order item (ID: 3)");
        System.out.println("---------------------------------------------");
        // Delete the 3rd object
        orderItem.delete_order_item(3);

        System.out.println("\nSTEP 9: Listing all order items after deletion");
        System.out.println("---------------------------------------------");
        // List all order items after deletion
        orderItem.list_all_order_items();

        System.out.println("\nSTEP 10: Listing items for a specific order after deletion");
        System.out.println("---------------------------------------------");
        // List order items for order ID 1 after deletion
        orderItem.list_order_items_by_order(1);

        System.out.println("\nSTEP 11: Restoring order items from backup");
        System.out.println("---------------------------------------------");
        // Restore from backup
        orderItem.restore_order_items();

        System.out.println("\nSTEP 12: Listing all order items after restore");
        System.out.println("---------------------------------------------");
        // List all order items after restore
        orderItem.list_all_order_items();

        System.out.println("\n=============================================");
        System.out.println("  ORDER ITEM TEST COMPLETED");
        System.out.println("=============================================");
    }
}
