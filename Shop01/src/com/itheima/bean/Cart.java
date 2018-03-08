package com.itheima.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {

//	购物项
	private Map<String , CartItem> cartItemMap = new HashMap<String, CartItem>();
//	总金额
	private double total;
//	建一个方法获得购物项中的值
	public Collection<CartItem> getValues(){
		Collection<CartItem> values = cartItemMap.values();
		return values;
	}
	
	
//	商品添加进购物车
	public void addToCart(CartItem cartItem){
//		得到cartItem商品的pid
		String pid = cartItem.getProduct().getPid();
//		判断之前是否添加过该商品
		if(cartItemMap.containsKey(pid)){
//			添加过
			CartItem oldItem = cartItemMap.get(pid);
			//把购物车里面存在的购物项的数量+新购买的数量
			oldItem.setCount(oldItem.getCount()+cartItem.getCount());
		}else{
//			没添加过
			cartItemMap.put(pid, cartItem);
		}
		total += cartItem.getSubTotal();
	}
	
//	从购物车中移除商品
	public void delFromCart(String pid){
//		1.根据pid移除指定商品
		CartItem cartItem = cartItemMap.remove(pid);
//		2.总价格减去移除的价格
		total -= cartItem.getSubTotal();
	}
	
//	清空购物车
	public void clearCart(){
//		1.清空map
		cartItemMap.clear();
//		2.总金额为0
		total = 0.0;
	}
	
	
	
	public Map<String, CartItem> getCartItem() {
		return cartItemMap;
	}
	public void setCartItem(Map<String, CartItem> cartItem) {
		this.cartItemMap = cartItem;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
	
}
