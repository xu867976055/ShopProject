package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.Constant.Constant;
import com.itheima.bean.Order;
import com.itheima.bean.OrderItem;
import com.itheima.service.OrderService;
import com.itheima.service.impl.OrderServiceImpl;
import com.itheima.utils.JsonUtil;

public class AdminOrderServlet extends BaseServlet{
	
	public String findByState(HttpServletRequest request,HttpServletResponse response){
		try {
			String state = request.getParameter("state");
			OrderService service = new OrderServiceImpl();
			List<Order> list = service.findByState(state);
			request.setAttribute("list", list);
			return "/admin/order/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public String findByOid(HttpServletRequest request,HttpServletResponse response){
		try {
			String oid = request.getParameter("oid");
			OrderService service = new OrderServiceImpl();
			//调用业务, 根据oid获得order对象
			Order order = service.findOrderByOid(oid);
			//得到当前订单的订单项, 把订单项集合转成json响应
			List<OrderItem> list = order.getOrderItems();
			String data = JsonUtil.list2json(list);
			response.getWriter().println(data);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public String updateState(HttpServletRequest request,HttpServletResponse response){
		try {
			String oid = request.getParameter("oid");
			OrderService service = new OrderServiceImpl();
			Order order = service.findOrderByOid(oid);
			order.setState(Constant.WAIT_CONFIRM_GOODS);
//			状态设置完成,从新更新order
			service.updateOrder(order);
//			重定向到查询已发货订单
			response.sendRedirect(request.getContextPath()+"/adminOrderServlet?method=findByState&state=2");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}