<%@page import="net.jeeshop.core.system.bean.User"%>
<%@page import="java.util.Map"%>
<%@page import="net.jeeshop.core.ManageContainer"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/resource/common_html_meat.jsp"%>
<head>
    <script type="text/javascript"> 
    	//注销
    	function a(){
    		window.parent.location.href = "../user!loginOut.action";
    	}
    </script>
</head>

<body>

<jsp:include page="../system/menu/leftMenu2.jsp"></jsp:include>
</body>
</html>
