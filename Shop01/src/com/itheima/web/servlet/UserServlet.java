package com.itheima.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.Constant.Constant;
import com.itheima.bean.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.CookUtils;
import com.itheima.utils.CookieUtil;
import com.itheima.utils.UUIDUtils;
import com.sun.javafx.sg.prism.web.NGWebView;

public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	public String registerUI(HttpServletRequest request, HttpServletResponse response){
		return "/jsp/register.jsp";
	}
	
//	注册
	public String regist(HttpServletRequest request, HttpServletResponse response){
		String code = request.getParameter("code");
		String vcode = (String) request.getSession().getAttribute("vcode");
		if(code.equalsIgnoreCase(vcode)){
			
			try {
				Map<String, String[]> map = request.getParameterMap();
				User user = new User();
				BeanUtils.populate(user, map);
				user.setUid(UUIDUtils.getId());
				user.setCode(UUIDUtils.getCode());
				user.setState(Constant.USER_NOT_ACTIVED);
				
				UserService service = new UserServiceImpl();
				service.doRegister(user);
				request.setAttribute("msg", "注册成功，快去邮箱激活吧");
				return "/jsp/msg.jsp";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "注册失败");
				return "/jsp/msg.jsp";
			}
		}else{
			request.setAttribute("vmsg", "验证码错误");
			return "/jsp/login.jsp";
		}
			
	}
	
//	激活
	public String active(HttpServletRequest request, HttpServletResponse response){
		try {
			String code = request.getParameter("activeCode");
			UserService service = new UserServiceImpl();
			User user = service.active(code);
			if(user !=null){
				request.setAttribute("msg", "激活成功，赶快登入吧！");
				return "/jsp/msg.jsp";
			}else{
				request.setAttribute("msg", "激活失败！");
				return "/jsp/msg.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "激活失败！");
			return "/jsp/msg.jsp";
			
		}
	}
	
	
//	登入
	public String loginUI(HttpServletRequest request, HttpServletResponse response){
		return "/jsp/login.jsp";
	}
	
	public String login(HttpServletRequest request, HttpServletResponse response){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserService service = new UserServiceImpl();
//		判断输入的验证码和系统生成的验证码是否一致
		String code = request.getParameter("code");
		String vcode = (String) request.getSession().getAttribute("vcode");
		if(code.equalsIgnoreCase(vcode)){
			try {
				User user = service.doLogin(username,password);
				if(user != null){
//					存在用戶
					if(user.getState() == Constant.USER_ACTIVED){
//						激活
//						判斷是否記住用戶名
						String rem = request.getParameter("rem");
						Cookie cookie = new Cookie("username", username);
						if(rem != null){
							cookie.setMaxAge(60*60);
							cookie.setPath(request.getContextPath());
						}else{
							cookie.setMaxAge(0);
						}
						response.addCookie(cookie);
						
						
//						判断是否自动登入
						String auto = request.getParameter("auto");
						Cookie cookie2 = new Cookie("autoLogin", username+"#"+password);
						cookie2.setPath(request.getContextPath());
						if("on".equals(auto)){
//							自动登入
							cookie2.setMaxAge(60*60);
						}else{
							cookie2.setMaxAge(0);
						}
						response.addCookie(cookie2);
//						把user保存到session中
						request.getSession().setAttribute("user", user);
						response.sendRedirect(request.getContextPath()+"/index.jsp");
						return null;
					}else{
						request.setAttribute("msg", "還沒有激活，趕快去郵箱激活吧！");
						return "/jsp/msg.jsp";
					}
				}else{
					request.setAttribute("msg", "帳戶名或密碼錯誤！");
					return "/jsp/login.jsp";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}else{
			request.setAttribute("vmsg", "验证码错误");
			return "/jsp/login.jsp";
		}
		
	}
	
//	注銷
	public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.getSession().removeAttribute("user");
//		request.getSession().removeAttribute("cart");
		
		Cookie cookie = CookUtils.getCookieByName("autoLogin", request.getCookies());
		if(cookie != null){
			cookie.setMaxAge(0);
			cookie.setPath(request.getContextPath());
			response.addCookie(cookie);
		}
		response.sendRedirect(request.getContextPath()+"/index.jsp");
		return null;
	}
	


}
