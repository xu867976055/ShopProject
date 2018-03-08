package com.itheima.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.Constant.Constant;
import com.itheima.bean.Product;
import com.itheima.dao.ProductDao;
import com.itheima.utils.C3P0Utils;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> hotProduct() throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql= "select * from product where is_hot=? limit ?,?";
		return runner.query(sql, new BeanListHandler<>(Product.class),Constant.HOT_PRODUCT,0,9);
	}

	@Override
	public List<Product> newProduct() throws Exception {
//		根据时间排序,只要0-9
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from product order by pdate desc limit ?,?";
		return 	runner.query(sql, new BeanListHandler<>(Product.class),0,9);
	}

	@Override
	public Product findByPid(String pid) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product where pid=?";
		return runner.query(sql, new BeanHandler<>(Product.class),pid);
	}

	@Override
	public int findAllProduct(String cid) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from product where cid=?";
		Long totalSize = (Long) runner.query(sql, new ScalarHandler(),cid);
		return totalSize.intValue();
	}

	@Override
	public List<Product> selectLimit(String cid ,int a, int b) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product where cid=? limit ?,?";
		return runner.query(sql, new BeanListHandler<>(Product.class), cid,a,b);
	}

	@Override
	public int findAllProduct() throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from product";
		Long n = (Long) runner.query(sql, new ScalarHandler());
		return n.intValue();
	}

	@Override
	public List<Product> findByPage(int a, int b) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from product limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<>(Product.class),a,b);
		return list;
	}

	@Override
	public void add(Product product) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {product.getPid(),product.getPname(),product.getMarket_price(),
				product.getShop_price(),product.getPimage(),product.getPdate(),
				product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCategory().getCid()};
		runner.update(sql, params );
	}

	@Override
	public void delProduct(String pid) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
//		System.out.println(pid);
		String sql = "delete from product where pid=?";
		runner.update(sql, pid);
		
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update product set pid=?,pname=?,market_price=?,shop_price=?,pimage=?,"
				+ "pdate=?,is_hot=?,pdesc=?,pflag=?,cid=?";
		Object[] params = {product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),
				product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),
				product.getCategory().getCid()};
		runner.update(sql, params);
	}

}
