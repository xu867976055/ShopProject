package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.dsna.util.images.ValidateCode;
public class VcodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		1.创建ValidateCode对象
		ValidateCode validateCode = new ValidateCode(80, 30, 4, 5);
//		获取验证码
		String vcode = validateCode.getCode();
//		2.把vcode放到session中
		request.getSession().setAttribute("vcode", vcode);
//		3.将验证码图片输入到客户端
		ServletOutputStream outputStream = response.getOutputStream();
		validateCode.write(outputStream);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
