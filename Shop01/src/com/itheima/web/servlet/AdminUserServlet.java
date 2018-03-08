package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.bean.Seller;
import com.itheima.service.SellerService;
import com.itheima.service.UserService;
import com.itheima.service.impl.SellerServiceImpl;
import com.itheima.service.impl.UserServiceImpl;

public class AdminUserServlet extends BaseServlet{
	/**
	 * 商家登入判断
	 * @param request
	 * @param response
	 * @return
	 */
	public String findUser(HttpServletRequest request,HttpServletResponse response){
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			SellerService service = new SellerServiceImpl();
			Seller seller = service.findSeller(username,password);
//			System.out.println(seller.toString());
			if(seller != null){
				request.getSession().setAttribute("seller", seller);
				response.sendRedirect(request.getContextPath()+"/admin/home.jsp");
				return null;
			}
			else{
				request.setAttribute("msg", "用户名或密码错误");
				return "/admin/index.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "网站维护中");
			return "/admin/index.jsp";
		}
	}
}