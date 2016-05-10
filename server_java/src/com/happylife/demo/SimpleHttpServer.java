package com.happylife.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
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
import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer {
	private int port;
	private HttpServer server;

	public void Start() {
		try {
			this.port = 80;
			server = HttpServer.create(new InetSocketAddress(port), 0);
			System.err.println("server started at " + port);
			server.createContext("/", new Handlers.DbPostHandler());
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void Stop() {
		server.stop(0);
		System.err.println("server stopped");
	}
}
