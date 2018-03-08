package com.itheima.service;

import java.util.List;

import com.itheima.bean.Order;
import com.itheima.bean.PageBean;

public interface OrderService {
	void saveOrder(Order order) throws Exception;

	PageBean<Order> myOrder(String uid, int curpage)throws Exception;

	Order findOrderByOid(String oid) throws Exception;

	void updateOrder(Order order) throws Exception;

	List<Order> findByState(String state) throws Exception;



}
