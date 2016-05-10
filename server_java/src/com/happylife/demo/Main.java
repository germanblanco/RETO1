package com.happylife.demo;

public class Main {
	public static void main(String[] args) {
		// start thread to do db inserts
		InsertThread it = new InsertThread(args[0]);
		it.start();

		// start http server
		SimpleHttpServer httpServer = new SimpleHttpServer();
		httpServer.Start();
		
	}
}
