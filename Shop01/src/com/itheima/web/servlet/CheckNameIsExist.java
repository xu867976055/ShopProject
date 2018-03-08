package com.itheima.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.bean.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
public class CheckNameIsExist extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String username = request.getParameter("username");
			UserService service = new UserServiceImpl();
			User user = service.searchOne(username);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			if(user != null){
				writer.println("用户名已存在");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
