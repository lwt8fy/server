<%@page import="net.jeeshop.core.ManageContainer"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head> 

<%@ include file="/resource/common_html_meat.jsp"%>


<%@ include file="/manage/system/common.jsp"%>


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
<body>
	<div class="container">
		<div class="row">
		
			<div class="span5">
				<div style="margin-top: 100px;text-align: right;margin-right: 10px;">
					<img src="../resource/images/login_left.png" />
				</div>
			</div>
			
			<div class="span7" style="border-left: 1px solid #ccc;margin-left: -2px;">
<!-- 				<hr width="1px" size="100" style="height: 400px;float: left;"> -->
<!-- <HR align=center width=12 height="5"  color=#ccc style="height: 500px;float: left;display:block;"> -->
				<table class="table table-bordered" style="width: 95%;margin-top: 100px;float: right;">
					<caption>
						<%
						Object loginErrorObj = request.getSession().getAttribute(ManageContainer.loginError);
						if(loginErrorObj!=null){
						%>
						<div class="alert alert-warning alert-dismissable" style="margin-bottom:0px;">
						  <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<%-- 			  <strong>登陆失败!</strong> 账号或密码错误！ --%>
							<%=loginErrorObj.toString() %>
						</div>
						<%}
						request.getSession().setAttribute("loginError",null);
						%>
					</caption>
					<tr>
						<td colspan="2"
							style="background-color: #dff0d8; text-align: center;"><strong>&nbsp;商家注册</strong>
							<div style="float:right;"><b><a href="<%=request.getContextPath()%>/">首页</a></b>&nbsp;&nbsp;</div>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<s:form action="" namespace="/manage" theme="simple" id="form22" cssClass="form-horizontal">
		<table class="table table-bordered">
			
			<tr>
				<th class="td_right">帐号</th>
				<td style="text-align: left;"> 
						<s:textfield name="e.username" id="username" maxlength="13" readonly="false" data-rule="帐号:required;username;length[3~12];remote[user!unique.action]"/>
				</td>
			</tr>
			<tr>
				<th class="td_right">昵称</th>
				<td style="text-align: left;">
				<s:textfield name="e.nickname" id="nickname" maxlength="16" readonly="false" data-rule="昵称:required;nickname;length[2~15];remote[user!unique.action]"/>
					</td>
			</tr>
			
				<tr>
					<th class="td_right">密码</th>
					<td style="text-align: left;"><s:password name="e.password" data-rule="密码:required;password;length[6~20];"
							id="password" />
					</td>
				</tr>
				<tr>
					<th class="td_right">确认密码</th>
					<td style="text-align: left;"><s:password name="e.newpassword2" data-rule="确认密码:required;match(e.password)"
							id="newpassword2" />
					</td>
				</tr>
				<tr>
					<th class="td_right">验证码</th>
					<td style="text-align: left;">
					
					<input name="vcode" type="text"  id="vcode"  placeholder="验证码" 
					    data-rule="验证码:required;vcode;" width="4" maxlength="4"  style="width:100px;" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    <img  src="<%=request.getContextPath()%>/ValidateImage.do" id="codes2" 
								onclick="javaScript:reloadImg2();" class="vcodeCss"></img>
					</td>
				</tr>
			
			
			
			
			
		
		</table>
		<div align="center"> 
			<button method="user!regUser.action" type="submit" value="注册"   class="btn btn-success" > 注册</button>
			
							
						 &nbsp;&nbsp;&nbsp;
						
						<a href="<%=request.getContextPath()%>/manage/admin.jsp" value="返回" class="btn" >返回</a>
						</div>
	</s:form>
						</td>
					</tr>
				</table>
				
			</div>
		</div>
	</div>
	<script type="text/javascript">
function reloadImg2() {
	document.getElementById("codes2").src = "<%=request.getContextPath() %>/ValidateImage.do?" + "radom="
			+ Math.random();
	$("#vcode2").focus();
}

</script>
</body>
</html>
<c:if test="${message!=null && message!=''}">
    <script language="javascript">
        alert('${message}');
    </script>
</c:if>