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

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Handlers {
	public static class DbPostHandler implements HttpHandler {

		private String db;

		public DbPostHandler(String db) {
			try {
				// The newInstance() call is a work around for some
				// broken Java implementations

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


		@Override
		public void handle(HttpExchange he) throws IOException {
			System.out.println("Served by /dbPost handler...");
			// parse request
			Map<String, Object> parameters = new HashMap<String, Object>();
			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			parseQuery(query, parameters);

			String value = (String)parameters.get("value");

			try {
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
