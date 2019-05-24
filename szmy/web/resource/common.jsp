<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="net.jeeshop.core.front.SystemManager"%>
<%@page import="net.jeeshop.services.front.product.bean.Product"%>
<%@page import="net.jeeshop.services.front.product.ProductService"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.*"%>
<%@page import="net.jeeshop.services.front.news.bean.News"%>
<%@page import="net.jeeshop.core.FrontContainer"%>


<link href="<%=request.getContextPath()%>/css/base_front.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/main.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/magnifier.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/tooltip.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery_min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jQselect.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.slides.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jqzoom.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/bootstrap3.0.0/js/bootstrap.min.js"></script>
