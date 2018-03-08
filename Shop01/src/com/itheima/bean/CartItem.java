package com.itheima.bean;

public class CartItem {
//	商品对象
	private Product product;
//	购买数量
	private int count;
//	小计
	private double subTotal;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubTotal() {
		return product.getShop_price()*count;
	}
	
	
	
}
