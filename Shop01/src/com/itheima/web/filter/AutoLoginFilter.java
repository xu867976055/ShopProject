package com.itheima.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.bean.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;

public class AutoLoginFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
//		1.从session中取出user,判断是否登入
		User user = (User) req.getSession().getAttribute("user");
		if(user == null){
//		2.未登入的情况下,判断是否需要自动登入,获取cookie
			Cookie[] cookies = req.getCookies();
			
			if(cookies != null){
				for (Cookie cookie : cookies) {
					if("autoLogin".equals(cookie.getName())){
//						需要自动登入
						String value = cookie.getValue();
						String username = value.split("#")[0];
						String password = value.split("#")[1];
						UserService service = new UserServiceImpl();
						try {
							user = service.doLogin(username, password);
							req.getSession().setAttribute("user", user);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
