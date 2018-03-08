package com.itheima.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
	private String oid;
	private Date ordertime;
	private double total;
	private int state;
	private String address;
	private String name;
	private String telephone;
	private User user;
	
//	订单项列表
	private List<OrderItem> orderItems = new ArrayList<>();
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> items) {
		this.orderItems = items;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrderTime() {
		return ordertime;
	}
	public void setOrderTime(Date datetime) {
		this.ordertime = datetime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", ordertime=" + ordertime + ", total=" + total + ", state=" + state + ", address="
				+ address + ", name=" + name + ", telephone=" + telephone + ", user=" + user + ", orderItems="
				+ orderItems + "]";
	}

	
	
	
}
