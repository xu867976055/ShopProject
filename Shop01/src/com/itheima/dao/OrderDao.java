package com.itheima.dao;

import java.util.List;

import com.itheima.bean.Order;
import com.itheima.bean.OrderItem;

public interface OrderDao {

	void save(Order order) throws Exception;

	void save(OrderItem orderItem) throws Exception;

	List<Order> findMyOrderByPage(String uid, int a, int b) throws Exception;

	int findMyOrder(String uid) throws Exception;

	Order findOrderByOid(String oid) throws Exception;

	void updateOrder(Order order) throws Exception;

	List<Order> findByState(String state) throws Exception;



}
