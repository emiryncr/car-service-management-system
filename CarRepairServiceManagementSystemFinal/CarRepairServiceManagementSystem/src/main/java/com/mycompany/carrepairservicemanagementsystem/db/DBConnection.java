/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carrepairservicemanagementsystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Digikey
 */
public class DBConnection {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/car_repair_db";
        String user = "root";
        String password = ""; 

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Connection failure: " + e.getMessage());
            return null;
        }
    }
}