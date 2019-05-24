<%@page import="net.jeeshop.core.PrivilegeUtil"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page session="false" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/manage/system/common.jsp"%>
</head>

<body> 
<s:form action="user" namespace="/" method="post" theme="simple">
	<table class="table table-bordered table-condensed">
		<tr>
			<td style="text-align: right;">用户名</td>
			<td style="text-align: left;" >
				<s:textfield id="username" name="e.username"  cssClass="search-query input-small"  />
			</td>
			<td style="text-align: right;">注册来源</td>
			<td style="text-align: left;">
			    <s:select name="e.source" id="source" list="#{'':'',1:'前台注册',2:'后台注册',3:'app注册',4:'平顶山app注册'}" listKey='key'
			    listValue="value"/>
			</td>
		</tr>
		<tr>
			<td colspan="11">
				<%if(PrivilegeUtil.check(request.getSession(), "user!selectList.action")){%>
<%-- 					<s:submit method="selectList" value="查询" cssClass="btn btn-primary"/> --%>
<%-- 					<s:a method="selectList" cssClass="btn btn-primary"> --%>
<!-- 						<i class="icon-search icon-white"></i> 查询 -->
<%-- 					</s:a> --%>
					<button method="user!selectList.action" class="btn btn-primary" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
				<%} %>
				
				<%if(PrivilegeUtil.check(request.getSession(), "user!insert.action")){%>
<%-- 					<s:submit method="toAdd" value="添加" cssClass="btn btn-success"/> --%>
					<s:a method="toAdd" cssClass="btn btn-success">
						<i class="icon-plus-sign icon-white"></i> 添加
					</s:a>
				<%} %>
				 
				 <button method="<%=request.getContextPath() %>/manage/user!deletes.action" class="btn btn-danger"  onclick="return  submitIDs(this,'确定删除选择的记录?');">
				     <i class="icon-remove-sign icon-white"></i>  删除
				    </button>
				
				<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<%@ include file="/manage/system/pager.jsp"%>
				</div>
			</td>
		</tr>
	</table>
	
	<table class="table table-bordered table-hover">
		<thead>
		<tr style="background-color: #dff0d8">
			<th width="20"><input type="checkbox" id="firstCheckbox"/></th>
			<th style="display: none;">id</th>
			<th>帐号</th>
			<th>昵称</th>
			<th>创建时间</th>
			<th>角色</th>
			<th>状态</th>
			<th>注册来源</th>
			<th>推荐人</th>
			<th nowrap="nowrap">操作</th>
		</tr></thead>
		<s:iterator value="pager.list">
			<tr >
				<td><s:if test="#session.manage_session_user_info.rid==1"><input type="checkbox" name="ids" value="<s:property value="id"/>"/></s:if></td>
				<td  style="display: none;">&nbsp;<s:property value="id"/></td>
				<td>&nbsp;<s:property value="username"/></td>
				<td>&nbsp;<s:property value="nickname"/></td>
				<td>&nbsp;<s:property value="createtime"/></td>
				<td>&nbsp;<s:property value="role_name"/></td>
				<td>
					<s:if test="status.equals(\"y\")">
						<img src="<%=request.getContextPath() %>/resource/images/action_check.gif">
					</s:if>
					<s:elseif test="status.equals(\"n\")">
						<img src="<%=request.getContextPath() %>/resource/images/action_delete.gif">
					</s:elseif>
				</td>
				<td>
				<s:if test="source==1">
					前台注册
				</s:if>
				<s:if test="source==2">
					后台管理添加
				</s:if>
				<s:if test="source==3">
					生猪交易app注册
				</s:if>
				<s:if test="source==4">
					平顶山app注册
				</s:if>
				</td>
				<td>
				<s:if test="presenter==null">
					无
				</s:if>
				<s:elseif test="presenter.equals(\"0\")">
					一级推荐人
				</s:elseif>
				<s:else>
					<s:property value="presenter"/>
				</s:else>
				</td>
				<td>
					<s:if test="#session.manage_session_user_info.rid==1">
						<s:a href="user!toEdit.action?e.id=%{id}">编辑</s:a>
					</s:if>
					
				</td>
			</tr>
		</s:iterator>
		
		<tr><td colspan="16" style="text-align:center;"><%@ include file="/manage/system/pager.jsp"%></td>
		</tr>
	</table>
</s:form>
</body>
</html>
<c:if test="${message!=null && message!=''}">
    <script language="javascript">
        alert('${message}');
    </script>
</c:if>