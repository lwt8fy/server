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
        <b><c:if test="${message!=null and message ne ''}">
     		<span class="glyphicon glyphicon-ok"></span>&nbsp;
     		${message}
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
        	 <input type="button" id="rateBtn2" class="btn btn-primary" onclick="to('${url}');" value="确定"/>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
  <script type="text/javascript">
function to(url) {
	if(url!=null&&url!=''){
		window.location.href=url;
	}else{
	 window.opener.location.reload();
     window.open('', '_self');
     window.close();
	}
}
</script>
</body>
</html>
