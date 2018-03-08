package com.itheima.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.bean.Seller;
import com.itheima.dao.SellerDao;
import com.itheima.utils.C3P0Utils;

public class SellerDaoImpl implements SellerDao {

	@Override
	public Seller findSeller(String username, String password) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from seller where username=? and password=?";
		Seller seller = runner.query(sql, new BeanHandler<>(Seller.class), username,password);
		return seller;
	}

}
