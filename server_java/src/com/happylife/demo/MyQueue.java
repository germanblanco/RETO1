package com.happylife.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyQueue {

	private static ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(100000);

	public static void push(int value) throws InterruptedException { queue.offer(value, 10, TimeUnit.MINUTES); }
	public static int pop() throws InterruptedException { return queue.poll(10, TimeUnit.MINUTES); }

}