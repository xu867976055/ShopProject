package com.itheima.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.Constant.Constant;
import com.itheima.bean.Cart;
import com.itheima.bean.CartItem;
import com.itheima.bean.Order;
import com.itheima.bean.OrderItem;
import com.itheima.bean.PageBean;
import com.itheima.bean.User;
import com.itheima.service.OrderService;
import com.itheima.service.impl.OrderServiceImpl;
import com.itheima.utils.PaymentUtil;
import com.itheima.utils.UUIDUtils;

public class OrderServlet extends BaseServlet{
	
//	提交订单(生成订单)
	public String submitOrder(HttpServletRequest request,HttpServletResponse response){
		User user = (User) request.getSession().getAttribute("user");
//		System.out.println(user);
			
		try {
				if(user == null){
				response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
				return null;
				}
				
//		封装order
				Order order = new Order();
//		 1. 封装oid
				order.setOid(UUIDUtils.getId());
				//1.2 封装user
				order.setUser(user);
				//1.3 封装订单时间
				order.setOrderTime(new Date());
				//1.4 封装订单状态 没有付款
				order.setState(Constant.UN_PAY);
				//1.5 封装 总金额 从购物车里面来
				Cart cart = (Cart) request.getSession().getAttribute("cart");
				order.setTotal(cart.getTotal());
				//1.6 封装 订单项
				List<OrderItem> orderItems = new ArrayList<>();
				Collection<CartItem> values = cart.getValues();
				for (CartItem cartItem : values) {
//					创建订单项对象
					OrderItem orderItem = new OrderItem();
					orderItem.setItemid(UUIDUtils.getId());
					orderItem.setCount(cartItem.getCount());
					orderItem.setSubtotal(cartItem.getSubTotal());
					orderItem.setProduct(cartItem.getProduct());
					orderItem.setOrder(order);
					orderItems.add(orderItem);
				}
				order.setOrderItems(orderItems);
				OrderService service = new OrderServiceImpl();
				service.saveOrder(order);
				
				request.getSession().setAttribute("o", order);
				response.sendRedirect(request.getContextPath()+"/jsp/order_info.jsp");
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "生成订单失败");
				return "/jsp/msg.jsp";
			}
	}
	
//	我的订单
	public String myOrder(HttpServletRequest request,HttpServletResponse response){
		try {
			User user = (User) request.getSession().getAttribute("user");
			if(user == null){
				response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
				return null;
			}
			
			int curPage =Integer.parseInt(request.getParameter("curPage"));
			String uid = user.getUid();
			
			OrderService service = new OrderServiceImpl();
			PageBean<Order> pageBean = service.myOrder(uid,curPage);
			List<Order> list = pageBean.getList();
			for (Order order : list) {
//				System.out.println(order);
			}
			request.setAttribute("pageBean", pageBean);
			return "/jsp/order_list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查询订单失败");
			return "/jsp/msg.jsp";
		}
	}
	
//	去付款
	public String payOrder(HttpServletRequest request,HttpServletResponse response){
		try {
			String oid = request.getParameter("oid");
//			System.out.println(oid);
			OrderService service = new OrderServiceImpl();
			Order order = service.findOrderByOid(oid);
			
			request.setAttribute("o", order);
			return "/jsp/order_info.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "去付款失败，请重试");
			return "/jsp/msg.jsp";
		}
	}
	
//	付款
	public String pay(HttpServletRequest request,HttpServletResponse response){
		try {
//			1.接收参数
			String oid = request.getParameter("oid");
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String telephone = request.getParameter("telephone");
//		System.out.println(oid+":"+name+":"+address+":"+telephone);
//			2.通过oid获得订单并更新数据
			OrderService service = new OrderServiceImpl();
			Order order = service.findOrderByOid(oid);
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			service.updateOrder(order);
//			3.组织数据发送给第三方支付公司
//			选择的银行信息
			String pd_FrpId = request.getParameter("pd_FrpId");
			String p0_Cmd = "Buy";
			String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
			String p2_Order = oid;
			String p3_Amt = "0.01";
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			// 加密hmac 需要密钥
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
					p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
			
//			发送给第三方
			StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			sb.append("p0_Cmd=").append(p0_Cmd).append("&");
			sb.append("p1_MerId=").append(p1_MerId).append("&");
			sb.append("p2_Order=").append(p2_Order).append("&");
			sb.append("p3_Amt=").append(p3_Amt).append("&");
			sb.append("p4_Cur=").append(p4_Cur).append("&");
			sb.append("p5_Pid=").append(p5_Pid).append("&");
			sb.append("p6_Pcat=").append(p6_Pcat).append("&");
			sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
			sb.append("p8_Url=").append(p8_Url).append("&");
			sb.append("p9_SAF=").append(p9_SAF).append("&");
			sb.append("pa_MP=").append(pa_MP).append("&");
			sb.append("pd_FrpId=").append(pd_FrpId).append("&");
			sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
			sb.append("hmac=").append(hmac);

			response.sendRedirect(sb.toString());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String callback(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");

		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 响应数据有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
//				System.out.println("111");
				request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
				
			} else if (r9_BType.equals("2")) {
				// 服务器点对点 --- 支付公司通知你
//				System.out.println("付款成功！222");
				// 修改订单状态 为已付款
				// 回复支付公司
				response.getWriter().print("success");
			}
			
			//修改订单状态
			OrderService orderService = new OrderServiceImpl();
			Order order = orderService.findOrderByOid(r6_Order);
			order.setState(Constant.WAIT_SEND_GOODS);
			orderService.updateOrder(order);
		
			
		} else {
			// 数据无效
			System.out.println("数据被篡改！");
		}
		return "/jsp/msg.jsp";
	}
}