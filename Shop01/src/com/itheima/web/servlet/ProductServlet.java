package com.itheima.web.servlet;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.bean.PageBean;
import com.itheima.bean.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;

public class ProductServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;

	public String index(HttpServletRequest request,HttpServletResponse response){
		//调用业务, 获得最新商品的数据(List<Product> newList) 热门商品(List<Product> hostList))
		try {
			ProductService service = new ProductServiceImpl();
			List<Product> hotproduct = service.hotProduct();
			List<Product> newProduct = service.newProduct();
			request.setAttribute("hotList", hotproduct);
			request.setAttribute("newList", newProduct);
			return "jsp/index.jsp";
		} catch (Exception e) {
			request.setAttribute("msg", "服务器异常");
			
			e.printStackTrace();
			return "/jsp/msg.jsp";
		}
	}
	
	public String findByPid(HttpServletRequest request,HttpServletResponse response){
		try {
			String pid = request.getParameter("pid");
			ProductService service = new ProductServiceImpl();
			Product product = service.findByPid(pid);
//			System.out.println(product);
			request.setAttribute("p", product);
			return "/jsp/product_info.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "服务器异常");
			return "/jsp/msg.jsp";
		}
	}
	
	public String findByPage(HttpServletRequest request,HttpServletResponse response){
		try {
			String cid = request.getParameter("cid");
			int curPage = Integer.parseInt(request.getParameter("curPage"));
			ProductService service = new ProductServiceImpl();
			PageBean<Product> pageBean = service.findByPage(curPage,cid);
			request.setAttribute("pageBean", pageBean);
//			System.out.println(pageBean.toString());
			
			return "/jsp/product_list.jsp";
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查询商品失败");
			return "/jsp/msg.jsp";
		}
		return null;
	}
	
}