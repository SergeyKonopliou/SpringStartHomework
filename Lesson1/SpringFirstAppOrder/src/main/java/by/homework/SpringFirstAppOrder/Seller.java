package by.homework.SpringFirstAppOrder;

import java.util.List;

public class Seller {
	
	private String name;
	private List<Order> order;
	
	public Seller() {
		
	}

	public Seller(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Seller " + name + "\n";
	}

	public String takeOrder(Order order) {
		this.order.add(order);
		StringBuilder str = new StringBuilder();
		str.append("\n").append(this.toString());
		for (Order order2 : this.order) {
			str.append(order2).append("\n");
		}
		return str.append("Order is accepted").toString();
	}
}
