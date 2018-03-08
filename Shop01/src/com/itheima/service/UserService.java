package com.itheima.service;

import java.sql.SQLException;

import com.itheima.bean.Seller;
import com.itheima.bean.User;

public interface UserService {

	/**
	 * 注册
	 * @param user
	 * @throws Exception 
	 */
	void doRegister(User user) throws Exception;

	/**
	 * 激活
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	User active(String code) throws Exception;

	/**
	 * 登入
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	User doLogin(String username, String password)throws Exception;

	/**
	 * 查询用户名是否存在
	 * @param username
	 * @return
	 * @throws SQLException 
	 */
	User searchOne(String username) throws SQLException;

	/**
	 * 查询商家的账号
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	
}
