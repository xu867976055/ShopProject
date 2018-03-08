package com.itheima.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.bean.Order;
import com.itheima.bean.OrderItem;
import com.itheima.bean.Product;
import com.itheima.dao.OrderDao;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.ConnectionManager;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void save(Order order) throws Exception {
		QueryRunner runner = new QueryRunner();
		String sql="insert into orders values(?,?,?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrderTime(),order.getTotal(),order.getState(),
				order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
		runner.update(ConnectionManager.getConnectionByLocalThread(), sql, params);
	}

	@Override
	public void save(OrderItem orderItem) throws Exception {
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Object[] params ={orderItem.getItemid(),orderItem.getCount(),orderItem.getSubtotal(),orderItem.getProduct().getPid(),
				orderItem.getOrder().getOid()};
		runner.update(ConnectionManager.getConnectionByLocalThread(), sql, params);
	}
	
	//根据uid,a,b分页查询我的所有订单
	@Override
	public List<Order> findMyOrderByPage(String uid, int a, int b) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from orders where uid=? limit ?,?";
		List<Order> list = runner.query(sql, new BeanListHandler<>(Order.class),uid,a,b);
		//当前的list里面封装的订单是没有包含订单项的(也就意味着都不知道买了什么东西)
		for (Order order : list) {
		//因为每一个订单的订单项都需要封装(也就是说把当前order的oid拿出来作为中间表的查询条件), 遍历list
			String sql2 = "select * from orderitem od ,product p where od.oid=? and od.pid = p.pid";
			List<Map<String,Object>> list2 = runner.query(sql2, new MapListHandler(), order.getOid());
			for (Map<String, Object> map : list2) {
				OrderItem orderItem = new OrderItem();
		//封装订单项(只能封装一些普通的字段egitemid,subtotal..., 不能封装product对象)
				BeanUtils.populate(orderItem, map);
		//单独封装Product并把其封装到订单项里面
				Product product = new Product();
				BeanUtils.populate(product, map);
				orderItem.setProduct(product);
		//把订单项封装到order中
				order.getOrderItems().add(orderItem);
			}
		}
		return list;
	}

//	根据uid查询我所有的订单个数
	@Override
	public int findMyOrder(String uid) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from orders where uid=?";
		Long n = (Long) runner.query(sql, new ScalarHandler(),uid);
		return n.intValue();
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
//		先根据oid查询订单
		String sql = "select * from orders where oid=?";
		Order order = runner.query(sql, new BeanHandler<>(Order.class), oid);
//		根据oid查询当前订单的订单项
		String sql2 = "select * from orderitem od,product p where oid=? and od.pid=p.pid";
		List<Map<String,Object>> list = runner.query(sql2, new MapListHandler(), oid);
		
		for (Map<String, Object> map : list) {
			OrderItem orderItem = new OrderItem();
			BeanUtils.populate(orderItem, map);
			Product product = new Product();
			BeanUtils.populate(product, map);
			orderItem.setProduct(product);
			order.getOrderItems().add(orderItem);
		}
		return order;
	}

//	更新order
	@Override
	public void updateOrder(Order order) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update orders set state=?,address=?,name=?,telephone=? where oid=?";
		Object[] params ={order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid()};
		runner.update(sql, params);
	}

	@Override
	public List<Order> findByState(String state) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from orders";
		if(state != null){
			sql += " where state=?";
			return	runner.query(sql, new BeanListHandler<>(Order.class), state);
		}
		return runner.query(sql, new BeanListHandler<>(Order.class));
		
	}



}
