package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.bean.User;
import com.itheima.dao.UserDao;
import com.itheima.utils.C3P0Utils;

public class UserDaoImpl implements UserDao {

	@Override
	public void save(User user) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),
				user.getSex(),user.getState(),user.getCode()};
		runner.update(sql, params);
	}

	@Override
	public User active(String code) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from user where code=?";
		return runner.query(sql, new BeanHandler<>(User.class), code);
	}

	@Override
	public void update(User user) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update user set username=?,password=?,name=?,email=?,"
				+ "telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
		Object[] params = {user.getUsername(),user.getPassword(),user.getName(),
				user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),
				user.getState(),user.getCode(),user.getUid() };
		runner.update(sql, params);
	}

	@Override
	public User findUser(String username, String password) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		return runner.query(sql, new BeanHandler<>(User.class), username,password);
	}

	@Override
	public User findOne(String username) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from user where username=?";
		return runner.query(sql, new BeanHandler<>(User.class), username);
	}

}
