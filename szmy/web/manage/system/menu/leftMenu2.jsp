<%@page import="net.jeeshop.core.system.bean.MenuItem"%>
<%@page import="java.util.*"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<meta charset="utf-8">
<title>side</title>
<link href="<%=request.getContextPath()%>/css/base.css" type="text/css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/jquery1.42.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.SuperSlide.2.1.1.js" type="text/javascript"></script>
<title>无标题文档</title>
</head>

<body>

<!--侧导航-->
	<div class="side">
		<a id="sy" class="home" href="../user!initManageIndex.action" onclick="rm_bg(this)" target="rightFrame"><span>首页</span></a>
		
		<%
		List<MenuItem> root=(List<MenuItem>)session.getAttribute("left_menu");
		for(MenuItem m: root){
			if(!m.getName().equals("首页")){
			%>
			<h3><span><%=m.getName() %></span></h3>
			<%
			if(m.getChildren()!=null &&m.getChildren().size()>0){
				%>
				<ul>
				<%
				for(MenuItem cm: m.getChildren()){
					%>
					<li><a  href="<%=cm.getUrl() %>" target="rightFrame" onclick="change_bg(this)"><%=cm.getName() %></a></li>
					<%
				}
				%>
				</ul>
				<%
			}
			}
		}
		%>
		
		</div><!-- sideMenu End -->
   <script type="text/javascript">
   		jQuery(".side").slide({titCell:"h3", targetCell:"ul",defaultIndex:1,effect:"slideDown",delayTime:300,trigger:"click",defaultPlay:false});

	function change_bg(a){
		$('a').css('color', '#1e678a');
		a.style.color="red";
		$('#sy').css('color', '#fff');
	}
	function rm_bg(a){
		$('a').css('color', '#1e678a');
	}
   		
   </script>
<!--end 侧导航-->

</body>
</html>
