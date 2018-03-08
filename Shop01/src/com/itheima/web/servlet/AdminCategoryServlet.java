package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.bean.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.utils.UUIDUtils;

/**
 * Servlet implementation class AdminCategoryServlet
 */
public class AdminCategoryServlet extends BaseServlet{
	/**
	 * 查询所有分类
	 * @param request
	 * @param response
	 * @return
	 */
	public String findCategory(HttpServletRequest request,HttpServletResponse response){
		try {
			CategoryService service = new CategoryServiceImpl();
			List<Category> list = service.findCategory();
			request.setAttribute("list", list);
			
			return "/admin/category/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String addUI(HttpServletRequest request,HttpServletResponse response){
		return "/admin/category/add.jsp";
	}
	

	/**
	 * 添加商品类别
	 * @param request
	 * @param response
	 * @return
	 */
	public String addCategory(HttpServletRequest request,HttpServletResponse response){
		
		try {
			String cname = request.getParameter("cname");
//			先封装category,再添加到数据库
			Category category = new Category();
			category.setCid(UUIDUtils.getId());
			category.setCname(cname);
			CategoryService service = new CategoryServiceImpl();
			service.addCategory(category);
//			再查询展示
			response.sendRedirect(request.getContextPath()+"/adminCategoryServlet?method=findCategory");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}