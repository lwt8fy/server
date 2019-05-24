<%@page import="net.jeeshop.core.util.TokenUtil"%>
<%@page import="net.jeeshop.core.front.SystemManager"%>
<%@page import="net.jeeshop.services.front.product.bean.Product"%>
<%@page import="net.jeeshop.services.front.product.ProductService"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.*"%>
<%@page import="net.jeeshop.services.front.news.bean.News"%>
<%@page import="net.jeeshop.core.ManageContainer"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html> 
<html class="no-js">
<head> 
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/resource/common_css.jsp"%>
<% 
	String message=request.getParameter("message");
	if(message!=null&&!message.equals("")){
		request.setAttribute("message",message);
	}
%>
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/product.js"></script>
<style type="text/css">
.totalPayMonery{
	color: red;font-weight: bold;font-size:22px;
}
</style>

</head>

<body >

  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="myModalLabel"><b>提示信息:</b></h4>
      </div>
      <div class="modal-body" style="color: #7ABD54;font:normal 24px">
       <h3>
        <b><c:if test="${message!=null and message eq 'regSuccess'}">
     		<span class="glyphicon glyphicon-ok"></span>&nbsp;
     		注册成功!请点击确定添加您的企业信息!
     		</c:if>
     		<c:if test="${message==null or message eq ''}">
     		<span class="glyphicon glyphicon-warning-sign"></span>&nbsp;
     		参数错误!
     		</c:if>
			
			<br>
			<br>
			</b>
			</h3>
      </div>
      <div class="modal-footer">
        	 <input type="button" id="rateBtn2" class="btn btn-primary" onclick="to();"  value="确定"/>
      <input type="button" id="rateBtn2" class="btn" onclick="to2();"  value="取消"/>&nbsp;&nbsp;&nbsp;
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
  <script type="text/javascript">
  function to2() {
	  parent.location.href='<%=request.getContextPath()%>/manage/main.jsp';
  }
function to() {
	var s="${message==null or message eq ''}";
	if(s=="true"){
		parent.location.href='<%=request.getContextPath()%>/manage/main.jsp';
	}else{
	window.location.href="<%=request.getContextPath()%>/manage/company!toAdd.action";
	}
}
</script>
</body>
</html>
