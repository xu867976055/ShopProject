package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		解决response响应乱码
		response.setContentType("text/html;charset=utf-8");
//		1.获取请求参数（方法名）
		String methodStr = request.getParameter("method");
//		2.获取字节码文件对象
		Class clazz = this.getClass();
//		3.通过反射获得方法对象
		try {
			Method method = clazz.getMethod(methodStr, HttpServletRequest.class,HttpServletResponse.class);
			String path = (String) method.invoke(this, request,response);
			if(path != null){
				request.getRequestDispatcher(path).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
