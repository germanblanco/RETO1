package com.happylife.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyQueue {

	private static ArrayBlockingQueue<Long> queue = new ArrayBlockingQueue<Long>(100);

	public static void push(long value) throws InterruptedException { queue.offer(value, 10, TimeUnit.MINUTES); }
	public static long pop() throws InterruptedException { return queue.poll(10, TimeUnit.MINUTES); }

}