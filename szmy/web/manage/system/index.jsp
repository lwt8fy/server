<%@page import="net.jeeshop.core.FrontContainer"%>
<%@page import="net.jeeshop.core.front.SystemManager"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>

<link href="<%=request.getContextPath() %>/css/base.css" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath }/js/jquery-1.9.0.min.js" type="text/javascript"></script>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/resource/common_html_validator.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#username").focus();

		if (top.location != self.location) {
			top.location = self.location;
		}
	}); 
</script>
</head>


<body style="background:#f1f8fd;">
<!--login-->
<div class="login">
	<div class="login_box">
    	<img class="login_tit" src="<%=request.getContextPath() %>/images/login_tit.png" />
        <div class="login_main">
        	<a href="<%=request.getContextPath() %>/" class="go_home">返回首页</a>
        	<%
						Object loginErrorObj = request.getAttribute(FrontContainer.login_errorMsg);
						if(loginErrorObj!=null){
						%>
						  <p class="pro"><%=loginErrorObj.toString() %></p>
						<%}%>
        	
            <div class="clear"></div>
			<s:form action="/user/doLoginManage.html" theme="simple" namespace="/" 	cssClass="form-horizontal">
            <input  name="e.account" type="text" class="import import01" id="account" data-rule="账号:required;account;" placeholder="请输入账号" />
            <div class="clear"></div>
            <input name="e.password" type="password" class="import import02" id="password" data-rule="密码:required;password;" placeholder="请输入密码" />
            <div class="clear"></div>
            
            <img  src="<%=request.getContextPath() %>/ValidateImage.do"  id="codes2" 
								onclick="javaScript:reloadImg2();" class="yzm" style="cursor:pointer;width:100px;height:40px;"></img>
            <input name="vcode" type="text"  id="vcode"  class="import import03"  size="4" maxlength="4"  data-rule="验证码:required;vcode;"  />
											&nbsp;&nbsp;&nbsp;&nbsp;
								<!--  
            <input class="import import03" />
            -->
            <div class="clear"></div>
            <a href="<%=request.getContextPath() %>/user/forget.html" class="for_pass">忘记密码？</a>
            <div class="clear"></div>
            <button class="login_btn" type="submit" ><font style="font-family:'微软雅黑';">登&nbsp;&nbsp;&nbsp;&nbsp;录</font></button>
            </s:form>
        </div>
    </div>
    <p class="login_fot">Copyright  2013-2015 powered by 360.com,河南电子商务有限公司 All Rights Reserverved.</p>
</div>
<!--end login-->
</body>






</html>

<script type="text/javascript">
function reloadImg2() {
	document.getElementById("codes2").src = "<%=request.getContextPath() %>/ValidateImage.do?" + "radom="
			+ Math.random();
	$("#vcode2").focus();
}
</script>

