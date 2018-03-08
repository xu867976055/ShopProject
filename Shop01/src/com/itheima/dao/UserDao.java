package com.itheima.dao;

import java.sql.SQLException;

import com.itheima.bean.User;

public interface UserDao {

	void save(User user) throws Exception;

	User active(String code) throws Exception;

	void update(User user) throws Exception;

	User findUser(String username, String password) throws Exception;

	User findOne(String username) throws SQLException;

}
