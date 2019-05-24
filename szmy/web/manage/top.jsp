<%@page import="net.jeeshop.core.front.SystemManager"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>

<title>top</title>
<link href="<%=request.getContextPath()%>/css/base.css" type="text/css" rel="stylesheet" />
</head>
<script type="text/javascript"> 
    	//注销
    	function a(){
    		window.parent.location.href = "<%=request.getContextPath()%>/manage/user!loginOut.action";
    	}
    </script>
<body>
<!--顶部-->
<div class="top">
	<img src="<%=request.getContextPath()%>/images/logo.png" class="logo" />
    <div class="top_rt">        
    	<a class="quit" href="javascript:a();">安全退出</a>
        <a class="password" href="<%=request.getContextPath()%>/manage/user!toChangePwd.action" target="rightFrame">修改密码</a>
        <a class="portal" href="<%=request.getContextPath()%>/" target="_blank">门户</a>
		<div class="top_box">
        	<p>${manage_session_user_info.username }，您好，欢迎登陆！</p>
        </div>
    </div>
</div> 
<!--end 顶部-->

</body>
</html>
