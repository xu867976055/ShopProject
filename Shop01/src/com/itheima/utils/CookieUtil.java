package com.itheima.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
	/**
	 * 创建Cookie
	 * @param name   cookie的名称
	 * @param value  cookie的值
	 * @param age    cookie的最大有效期
	 * @param path   cookie的有效范围
	 * @return
	 */
	public static Cookie createCookie(String name,String value,int age,String path){
		//1.创建Cookie对象
		//先将中文通过URLEncoder编码成英文
		try {
			value = URLEncoder.encode(value,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Cookie cookie = new Cookie(name, value);
		//2.设置cookie的最大有效期
		cookie.setMaxAge(age);
		//3.设置cookie的有效范围
		cookie.setPath(path);
		return cookie;
	}
	/**
	 * 获取cookie的值
	 * @param request
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request,String key){
		//1.获取客户端携带过来的Cookie
		Cookie[] cookies = request.getCookies();
		String value = null;
		if (cookies != null) {
			//2.遍历出每一个cookie
			for (Cookie cookie : cookies) {
				//3.要获取存放在cookie中的username
				String name = cookie.getName();
				if (key.equals(name)) {
					value = cookie.getValue();
					//使用URLDecoder对value进行解码
					try {
						value = URLDecoder.decode(value, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return value;
	}
}
