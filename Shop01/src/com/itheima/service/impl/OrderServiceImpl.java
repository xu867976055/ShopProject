package com.itheima.service.impl;

import java.sql.Connection;
import java.util.List;

import com.itheima.Constant.Constant;
import com.itheima.bean.Order;
import com.itheima.bean.OrderItem;
import com.itheima.bean.PageBean;
import com.itheima.dao.OrderDao;
import com.itheima.dao.impl.OrderDaoImpl;
import com.itheima.service.OrderService;
import com.itheima.utils.ConnectionManager;

public class OrderServiceImpl implements OrderService{

	@Override
	public void saveOrder(Order order) throws Exception {
		Connection connection = null;
//		开启事务
		try {
			connection = ConnectionManager.getConnectionByLocalThread();
			connection.setAutoCommit(false);
//			保存订单项,保存订单
			OrderDao dao = new OrderDaoImpl();
			dao.save(order);
			List<OrderItem> orderItems = order.getOrderItems();
			for (OrderItem orderItem : orderItems) {
				dao.save(orderItem);
			}
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			connection.rollback();
		}
		
	}

	@Override
	public PageBean<Order> myOrder(String uid, int curPage) throws Exception {
		int pageSize = Constant.PAGE_ORDER_SIZE;
		int totalPage = 0;
		OrderDao dao = new OrderDaoImpl();
		PageBean<Order> pageBean = new PageBean<>();
//		调用dao层方法获取我的所有订单个数
		int totalSize = dao.findMyOrder(uid);
		if(totalSize % pageSize==0){
			totalPage = totalSize/pageSize;
		}else{
			totalPage = totalSize/pageSize+1;
		}
		
		int b = pageSize;
		int a = (curPage-1)*b;
//		调用dao层方法分页查询我的订单集合
		List<Order> list = dao.findMyOrderByPage(uid,a,b);
//		1.当前页码
		pageBean.setCurPage(curPage);
//		2.当前页码订单个数
		pageBean.setPageSize(pageSize);
//		3.我的所有订单个数
		pageBean.setTotalSize(totalSize);
//		4.订单总页数
		pageBean.setTotalPage(totalPage);
//		5.订单List
		pageBean.setList(list);
		
		
		return pageBean;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		OrderDao dao = new OrderDaoImpl();
		Order order = dao.findOrderByOid(oid);
		return order;
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		OrderDao dao = new OrderDaoImpl();
		dao.updateOrder(order);
		
	}

	@Override
	public List<Order> findByState(String state) throws Exception {
		OrderDao dao = new OrderDaoImpl();
		List<Order> list = dao.findByState(state);
		return list;
	}

}
