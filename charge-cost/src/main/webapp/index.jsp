<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path;
	String str = (String) request.getAttribute("flag");
%>
<html>
<body>
	<h2>扣费服务接收端!<%=str%>,<%=path%>,<%=basePath%></h2>
	<%
		if (str.equals("yes")) {
	%>
	<h2>开启中!</h2>
	<a href="<%=path%>/stopreceive">关闭接收数据</a>
	<%
		} else {
	%>
	<h2>未开启!</h2>
	<a href="<%=path%>/receive">开启接收数据</a>
	<%
		}
	%>
</body>
</html>
