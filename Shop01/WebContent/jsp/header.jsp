<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	$.getJSON("${pageContext.request.contextPath}/categoryServlet",{"method":"findAll"},function(result){
		//获得的result是json数组
		$(result).each(function(i,obj){
			$("#ulId").append("<li><a href='${pageContext.request.contextPath}/productServlet?method=findByPage&curPage=1&cid="+obj.cid+"'>"+obj.cname+"</a></li>")
		})
	})
</script>
</head>
<body>
	<!--
            	时间：2015-12-30
            	描述：菜单栏
            -->
			<div class="container-fluid">
				<div class="col-md-4">
					<img src="${pageContext.request.contextPath}/img/logo2.png" />
				</div>
				<div class="col-md-5">
					<img src="${pageContext.request.contextPath}/img/header.png" />
				</div>
				<div class="col-md-3" style="padding-top:20px">
					<c:if test="${empty user }">
						<ol class="list-inline">
							<li><a href="${pageContext.request.contextPath }/userServlet?method=loginUI">登录</a></li>
							<li><a href="${pageContext.request.contextPath }/userServlet?method=registerUI">注册</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/cart.jsp">购物车</a></li>
						</ol>
					</c:if>
					<c:if test="${not empty user }">
						<ol class="list-inline">
							<li>尊敬的:${user.getName() }</li>
							<li><a href="${pageContext.request.contextPath }/userServlet?method=logout">注銷</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/cart.jsp">购物车</a></li>
							<li><a href="${pageContext.request.contextPath }/orderServlet?method=myOrder&curPage=1">我的订单</a></li>
						</ol>
					</c:if>
				</div>
			</div>
			<!--
            	时间：2015-12-30
            	描述：导航条
            -->
			<div class="container-fluid">
				<nav class="navbar navbar-inverse">
					<div class="container-fluid">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="index.jsp">首页</a>
						</div>

						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav" id="ulId">
							
							</ul>
							<form class="navbar-form navbar-right" role="search">
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Search">
								</div>
								<button type="submit" class="btn btn-default">Submit</button>
							</form>

						</div>
						<!-- /.navbar-collapse -->
					</div>
					<!-- /.container-fluid -->
				</nav>
			</div>
	
</body>
</html>