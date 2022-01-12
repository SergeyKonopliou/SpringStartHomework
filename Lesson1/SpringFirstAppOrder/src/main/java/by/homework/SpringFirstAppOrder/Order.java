package by.homework.SpringFirstAppOrder;

import java.util.List;

public class Order {
	private int numberOrder;
	private double totalCost;
	private List<Product> orderList;
	
	public Order() {
	}

	public Order(int numberOrder,double totalCost,List<Product> orderList) {
		this.numberOrder = numberOrder;
		this.totalCost = totalCost;
		this.orderList = orderList;
	}

	public int getNumberOrder() {
		return numberOrder;
	}

	public void setNumberOrder(int numberOrder) {
		this.numberOrder = numberOrder;
	}

	public double getTotalCost() {
		if(orderList != null) {
			totalCost = orderList.stream().mapToDouble(Product::getPrice).sum(); //или mapToDouble(x -> x.getPrice())
		}
		return totalCost;
	}
	
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public List<Product> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Product> orderList) {
		this.orderList = orderList;
	}

	@Override
	public String toString() {
		return "Order №" + numberOrder + " totalCost=" + getTotalCost() + ", orderList:" + orderList;
	}

	
}
