package com.happylife.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Handlers {
	public static class DbPostHandler implements HttpHandler {

		private static int iterator = 0;
		private Connection conn = null;

		public void DbPostHandler() {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (Exception ex) {
				System.err.println("Driver not found!!!");
			}
			Connection conn = getConnection();
		}

		private Connection getConnection() {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:mysql://" + "reto1db" + "/reto1?" +
	                               "user=root&password=passwd");

			} catch (SQLException ex) {
				// handle any errors
				System.err.println("SQLException: " + ex.getMessage());
				System.err.println("SQLState: " + ex.getSQLState());
				System.err.println("VendorError: " + ex.getErrorCode());
			}
			return conn;
		}

		@Override
		public void handle(HttpExchange he) throws IOException {
			// parse request
			Map<String, Object> parameters = new HashMap<String, Object>();
			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			parseQuery(query, parameters);

			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO reto1 VALUES (" + System.currentTimeMillis() + ", now())");
				stmt.close();
			} catch (SQLException ex) {
				// handle any errors
				System.err.println("SQLException: " + ex.getMessage());
				System.err.println("SQLState: " + ex.getSQLState());
				System.err.println("VendorError: " + ex.getErrorCode());
			}

			// send response
			he.sendResponseHeaders(200, 0);
			String response = "<html><body><h1>POST!</h1></body></html>";
			OutputStream os = he.getResponseBody();
			os.write(response.toString().getBytes());
			os.close();

		}
	}

	@SuppressWarnings("unchecked")
	public static void parseQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {

		if (query != null) {
			String pairs[] = query.split("[&]");

			for (String pair : pairs) {
				String param[] = pair.split("[=]");

				String key = null;
				String value = null;
				if (param.length > 0) {
					key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
				}

				if (param.length > 1) {
					value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
				}

				if (parameters.containsKey(key)) {
					Object obj = parameters.get(key);
					if (obj instanceof List<?>) {
						List<String> values = (List<String>) obj;
						values.add(value);
					} else if (obj instanceof String) {
						List<String> values = new ArrayList<String>();
						values.add((String) obj);
						values.add(value);
						parameters.put(key, values);
					}
				} else {
					parameters.put(key, value);
				}
			}
		}
	}
}
