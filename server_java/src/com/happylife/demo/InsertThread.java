package com.happylife.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

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
			System.err.println("Driver not found!!!");
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
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}

	public void run() {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		while (true) {

			try {
				String value = Long.toString(MyQueue.pop());

				Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO reto1 VALUES (" + value + ", now())");
				stmt.close();
				conn.close();
			}
			catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		// start thread to do db inserts
		InsertThread it = new InsertThread(args[0]);
		it.start();
	}
}