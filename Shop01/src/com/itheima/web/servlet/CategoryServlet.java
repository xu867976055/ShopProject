package com.itheima.web.servlet;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;

public class CategoryServlet extends BaseServlet{
	public String findAll(HttpServletRequest request,HttpServletResponse response){
		try {
			CategoryService service = new CategoryServiceImpl();
			String data = service.findAll();
			PrintWriter writer = response.getWriter();
			writer.println(data);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}