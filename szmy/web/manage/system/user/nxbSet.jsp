<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/manage/system/common.jsp"%>
<%@ include file="/resource/common_html_validator.jsp"%>

</head>

<body style="text-align: center;">
	<s:form action="user!nxbSet.action" namespace="/" theme="simple">
		<table class="table table-bordered">
			<tr>
				<td colspan="2" style="background-color: #D9EDF7;text-align: center;">
					<strong>绑定农信宝</strong>
				</td>
			</tr>
			<tr>
				<th style="text-align: right;">当前绑定帐号:</th>
				<td style="text-align: left;">
				${old}
				</td>
			</tr>
			<tr>
				<th style="text-align: right;">农信宝帐号:</th>
				<td style="text-align: left;"><input  name="e.nxbUserName" type="text" class="form-control" id="nxbUserName"  value=""
					    data-rule="农信宝帐号:required;nxbUserName;remote[user!unique.action]" placeholder="请输入帐号" /></td>
			</tr>
			<tr>
				<th style="text-align: right;">农信宝密码:</th>
				<td style="text-align: left;"><input name="e.nxbPassword" type="password" class="form-control" id="nxbPassword" data-rule="密码: required;nxbPassword;" value="" placeholder="请输入密码" /></td>
			</tr>
			<tr>
				<th style="text-align: right;">再次输入密码:</th>
				<td style="text-align: left;"><input name="nxbPassword2" type="password" class="form-control" id="nxbPassword2" data-rule="确认密码: required;match(e.nxbPassword);" value="" placeholder="请输入密码" /></td>
			</tr>
			
			<tr>
				<td colspan="2" style="text-align: center;">
<%-- 					<s:submit method="updateChangePwd" value="保存" cssClass="btn btn-primary"/> --%>
<%-- 					<s:a method="updateChangePwd" cssClass="btn btn-success"> --%>
<!-- 						<i class="icon-ok icon-white"></i> 保存 -->
<%-- 					</s:a> --%>
					<button method="user!nxbSet.action" class="btn btn-success">
						<i class="icon-ok icon-white"></i> 确认绑定
					</button>
				</td>
			</tr>
		</table>
	</s:form>
	
</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${message!=null && message!=''}">
    <script language="javascript">
        alert('${message}');
    </script>
</c:if>