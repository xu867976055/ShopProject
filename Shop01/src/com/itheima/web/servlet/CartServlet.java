package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.bean.Cart;
import com.itheima.bean.CartItem;
import com.itheima.bean.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet{
	
//	添加商品进入购物车
	public String addToCart(HttpServletRequest request,HttpServletResponse response){
		try {
			String pid = request.getParameter("pid");
			int count = Integer.parseInt(request.getParameter("count"));
//		System.out.println(pid+":::"+count);
			CartItem cartItem = new CartItem();
			cartItem.setCount(count);
			ProductService service = new ProductServiceImpl();
			Product product = service.findByPid(pid);
			cartItem.setProduct(product);
			
			// 从session里面获得购物车(判断购物车是否存在), 把购物项添加到购物车里面
			Cart cart = (Cart) request.getSession().getAttribute("cart");
			if(cart == null){
				cart = new Cart();
			}
			cart.addToCart(cartItem);
			request.getSession().setAttribute("cart", cart);
			// 重定向到购物车页面,展示
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
			return null;
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "添加购物车失败");
			return "/jsp/msg.jsp";
		}
		
		return "/jsp/cart.jsp";
	}
	
//	删除商品
	public String delete(HttpServletRequest request,HttpServletResponse response){
		try {
			
			String pid = request.getParameter("pid");
//			System.out.println(pid);
//		从session中拿出购物车
			Cart cart = (Cart) request.getSession().getAttribute("cart");
//		调用购物车中删除商品的方法
			cart.delFromCart(pid);
			
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			request.setAttribute("msg", "删除失败");
			return "/jsp/msg.jsp";
		}
		
	}
	
//	清空购物车
	public String clearCart(HttpServletRequest request,HttpServletResponse response){
		try {
			Cart cart = (Cart) request.getSession().getAttribute("cart");
			cart.clearCart();
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			request.setAttribute("msg", "清除失败");
			return "/jsp/msg.jsp";
		}
	}
}