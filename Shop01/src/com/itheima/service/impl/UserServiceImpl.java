package com.itheima.service.impl;

import java.sql.SQLException;

import com.itheima.Constant.Constant;
import com.itheima.bean.Seller;
import com.itheima.bean.User;
import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.service.UserService;
import com.itheima.utils.MailUtils;

public class UserServiceImpl implements UserService{

	@Override
	public void doRegister(User user) throws Exception {
		UserDao dao = new UserDaoImpl();
//		调用dao操作数据库
		dao.save(user);
//		发送激活邮件
		MailUtils.sendMail(user.getEmail(), "尊敬的:"+user.getName()+"欢迎注册黑马商城，激活即可登入<a href='http://localhost:8080/Shop01/userServlet?method=active&activeCode="+user.getCode()+"'>点击激活</a>");
	}

	@Override
	public User active(String code) throws Exception {
		UserDao dao = new UserDaoImpl();
		User user = dao.active(code);
//		判断是否激活
		if(user !=null){
			user.setState(Constant.USER_ACTIVED);
			user.setCode(null);
			dao.update(user);
		}
		return user;
	}

	@Override
	public User doLogin(String username, String password) throws Exception {
		UserDao dao  = new UserDaoImpl();
		User user = dao.findUser(username,password);
		return user;
	}

	@Override
	public User searchOne(String username) throws SQLException {
		UserDao dao = new UserDaoImpl();
		User user = dao.findOne(username);
		return user;
	}


}
