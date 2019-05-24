<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="net.jeeshop.core.util.VisitorCounter"%>
<%@page import="net.jeeshop.services.front.company.bean.Company"%>
<%@page import="net.jeeshop.services.front.company.CompanyService"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%> 
<%@ page import="org.springframework.context.ApplicationContext"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit" />
<title>伟林</title>
</head>
<body>
<%@ include file="resource/common.jsp"%>
<form>
用户名:<input type="text" name="name" value=""/>
用户名:<input type="password" name="password" value=""/>
<input type="button" value="提交" onclick="tologin();"/>
</form>
<%-- <a href="<%=request.getContextPath()%>/manage/product!selectList.action">商品列表<a> --%>
</body>
<script type="text/javascript">
function tologin(){
	if($("input[name='name']").val()=="admin"&&$("input[name='password']").val()=="123456"){
			this.location.href="<%=request.getContextPath()%>/manage/product!selectList.action";
	}

}
</script>
</html>
