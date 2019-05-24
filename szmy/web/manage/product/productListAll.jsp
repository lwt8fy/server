<%@page import="net.jeeshop.core.PrivilegeUtil"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page session="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE html>
<html>
<head>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/manage/system/common.jsp"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/resource/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.product-name {
	display: inline-block;
	width: 250px;
	overflow: hidden; /*注意不要写在最后了*/
	white-space: nowrap;
	-o-text-overflow: ellipsis;
	text-overflow: ellipsis;
}
</style>

</head>

<body>
	<s:form action="product" namespace="/manage" method="post" theme="simple">
		<input type="hidden" value="${activityid }" name="activityid" id="activityid"/>
		<table class="table table-bordered table-condensed">
			<tr>
			<td style="text-align: right;">商品名称</td>
				<td style="text-align: left;" ><s:textfield name="e.name" cssClass="input-small"
						id="name" /></td>
				<td style="text-align: right;">商品编号</td>
				<td style="text-align: left;"><s:textfield name="e.id" cssClass="search-query input-small"
						id="id" /></td>
				
				
					<s:if test="#session.manage_session_user_info.rid==1"> 		
				<td style="text-align: right;">公司名称</td>
				<td style="text-align: left;" ><s:textfield name="e.compName" cssClass="input-small"
						id="compName" /></td>
				</s:if>
				<td style="text-align: right;">录入日期</td>
				<td style="text-align: left;" colspan="1">
					<input id="d4311" class="Wdate search-query input-small" type="text" name="e.startDate"
					value="<s:property value="e.startDate" />"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
					~ 
					<input id="d4312" class="Wdate search-query input-small" type="text" name="e.endDate" 
					value="<s:property value="e.endDate" />"
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
				</td>
				
			</tr>
			<tr>
				<td colspan="20">
					<%if(PrivilegeUtil.check(request.getSession(), "product!selectList.action")){%>
<%-- 						<s:submit method="selectList" value="查询" cssClass="btn btn-primary" /> --%>
						<button method="product!selectListAll.action" class="btn btn-primary" onclick="selectList(this)">
							<i class="icon-search icon-white"></i> 查询
						</button>
					<%} %>
					${compName}
			<div style="float: right;">
		<a  class="btn btn-danger" onclick="goBack()">
							<i class="icon-ok icon-white"></i> 确定并返回
						</a>
					</div>
			
					
					
<%-- 					<a target="_blank" href="<%=request.getContextPath()%>/product/selectMemoryStock.html" class="btn btn-info">
					<i class="icon-eye-open icon-white"></i> 内存库存查询</a>
					<a href="<%=request.getContextPath()%>/freemarker!create.action?method=products" class="btn btn-danger" onclick="javascript:return confirm('此操作将静态化前台所有商品的商品介绍，确定这么做吗？');">所有商品介绍全部静态化</a> --%>
					
					
				</td>
			</tr>
		</table>

		<table class="table table-bordered table-condensed table-hover">
			<tr style="background-color: #D9EDF7">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th nowrap="nowrap">商品编号</th>
				<th>名称</th>
				<th>原价</th>
				<th>现价</th>
				<th>录入日期</th>
				<th>浏览次数</th>
				<th>库存</th>
				<th>销量</th>
				<th>活动编号</th>
			</tr>
			<s:iterator value="pager.list" >
				<tr>
					<td>
					<s:if test="activityID==null or activityID.equals(\"\") or activityID.equals(#request.activityid)">
					<input type="checkbox"  name="ids" id="<s:property value="id"/>" 
						value="<s:property value="id"/>" />
					</s:if>	
						</td>
					<td nowrap="nowrap">&nbsp;<s:property value="id" /></td>
					<td >
						<s:if test="giftID!=null and giftID!=''">
							【赠品】
						</s:if>
						<s:property value="name" />
					</td>
					<td>&nbsp;<s:property value="price" /></td>
					<td>&nbsp;<s:property value="nowPrice" /></td>
					<td>&nbsp;<s:property value="createtime" /></td>
					
					<td>&nbsp;<s:property value="hit" /></td>
					<td>&nbsp;
						<s:if test="stock>0">
							<s:property value="stock" />
						</s:if>
						<s:else>
							<span class="badge badge-important">库存告急</span>
						</s:else>
					</td>
					<td>&nbsp;<s:property value="sellcount" /></td>
					<td>&nbsp;<s:property value="activityID" /></td>
				</tr>
			</s:iterator>

		
		</table>
		
		<div class="alert alert-info" style="text-align: left;font-size: 14px;margin: 2px 0px;">
			图标含义：<BR>
			<img alt="新增" src="<%=request.getContextPath() %>/resource/images/action_add.gif">：新增商品
			<img alt="已上架" src="<%=request.getContextPath() %>/resource/images/action_check.gif">：商品已上架
			<img alt="已下架" src="<%=request.getContextPath() %>/resource/images/action_delete.gif">：商品已下架
		</div>

	</s:form>
	
<script type="text/javascript">
	function goBack(){
		var ids=document.getElementsByName("ids");
		var id="";
		 for(i = 0; i < ids.length; i++) {
			 if(ids[i].checked){
				 id=id+","+ids[i].value.split(",")[0];
			 }
		   }
		   id=id.substring(1, id.length);
		 window.opener.document.getElementById("productID").value=id;
		window.close(); 
	}
	$(function() {
		var ids='<%=request.getParameter("pids")%>';
		if(ids!=''&&ids!=undefined){
		var idss=ids.split(",");
		for(var i=0;i<idss.length;i++){
			$("#"+idss[i]).attr("checked","checked");
		}
		}
	});
	function selectDefaultCatalog(){
		var _catalogID = $("#catalogID").val();
		if(_catalogID!='' && _catalogID>0){
			$("#catalogSelect").attr("value",_catalogID);
		}
	}
	function selectColor(obj){
		obj.innerHTML="|编辑";
	}
</script>
</body>
</html>

<c:if test="${message!=null && message!=''}"> 
    <script language="javascript">
    alert('${message}');
    window.close(); 
    </script>
</c:if>