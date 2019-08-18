package com.zure.plukk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Order {
	
	private int orderNumber;
	private List<String> locations = new ArrayList<>();
	
	public Order(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	private void saveOrder() {
		File dir = new File("res/orders");
		File file = new File(dir, Integer.toString(orderNumber) + ".txt");
		try {
			PrintWriter writer = new PrintWriter(file);
			for (String s : locations) {
				writer.println(s);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addLocation(String location) {
		locations.add(location);
		System.out.println("Added " + location);
		saveOrder();
	}
	
	public void removeLocation(String location) {
		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i).equals(location)) {
				locations.remove(i);
			}
		}
		System.out.println("Removed " + location);
		System.out.println("Size : " + locations.size());
		saveOrder();
	}
	
	public static Order loadOrder(int orderNumber) {
		File file = new File("res/orders/" + Integer.toString(orderNumber) + ".txt");
		if (file.exists()) {
			Order order = new Order(orderNumber);
			try {
				Scanner scan = new Scanner(file);
				while (scan.hasNextLine()) {
					order.getLocations().add(scan.nextLine());
				}
				scan.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return order;
		}
		return new Order(orderNumber);
	}

	public int getOrderNumber() {
		return orderNumber;
	}
	
	public List<String> getLocations() {
		return locations;
	}
	
}
