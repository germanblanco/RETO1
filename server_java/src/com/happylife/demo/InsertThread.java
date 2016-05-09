package com.happylife.demo;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InsertThread extends Thread {

	private String db;

	public InsertThread(String db) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			System.out.println("Driver not found!!!");
		}
		this.db = db;
	}

	private Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + this.db + "/reto1?" +
                               "user=root&password=passwd");

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}

	public void run() {
		while (true) {

			try {
				int value = MyQueue.pop();

				Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO reto1 VALUES (" + value + ", now())");
				stmt.close();
				conn.close();
				System.out.println("Value inserted.");
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
    }
}