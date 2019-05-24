<%@page import="net.jeeshop.services.manage.order.bean.OrdersReport"%>
<%@page import="net.jeeshop.services.front.systemSetting.bean.SystemSetting"%>
<%@page import="net.jeeshop.core.ManageContainer"%>
<%@page import="net.jeeshop.core.front.SystemManager"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<title>side</title>
<link href="<%=request.getContextPath() %>/css/base.css" type="text/css" rel="stylesheet" />
<script src="<%=request.getContextPath() %>/js/jquery1.42.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/jquery.SuperSlide.2.1.1.js" type="text/javascript"></script>
<title>无标题文档</title>
</head>

<body>
<div class="home_main">
<%OrdersReport orderReport = SystemManager.ordersReport; %>
<!--home-->
<div class="home">
	<div class="home_top">
		<p>十万火急</p>
	</div>
    <div class="order">
    	<a class="order01" href="<%=SystemManager.systemSetting.getManageHttp()%>/manage/order!selectList.action?paystatus=n&status=init" >
        	<img src="<%=request.getContextPath() %>/images/home_cl01.jpg" />
        	<p>待付款订单</p><span><%=orderReport.getNotPayCount()%></span>
        </a>
        <a class="order02" href="<%=SystemManager.systemSetting.getManageHttp()%>/manage/order!selectList.action?paystatus=y&status=init" >
        	<p style=" margin-left:30px;">已付款未审核订单</p><span><%=orderReport.getPayButNotPassCount()%></span>
        </a>
        <a class="order03" href="<%=SystemManager.systemSetting.getManageHttp()%>/manage/order!selectList.action?paystatus=y&status=pass" >
        	<img src="<%=request.getContextPath() %>/images/home_cl02.jpg" />
        	<p>待发货订单</p><span><%=orderReport.getWaitSendGoodsCount()%></span>
        </a>
        <a class="order04" href="<%=SystemManager.systemSetting.getManageHttp()%>/manage/order!selectList.action?status=cancel" >
        	<img src="<%=request.getContextPath() %>/images/home_cl03.jpg" />
        	<p>已取消订单</p><span><%=orderReport.getCancelCount() %></span>
        </a>
    </div>
</div>
<!--end home-->
<!--menu-->
<div class="menu">
	<div class="menu_top">
    	<p>退款订单</p>
    </div>
    <div class="menu_box">
    	<a href="<%=SystemManager.systemSetting.getManageHttp()%>/manage/order!selectList.action?refundStatus=WAIT_SELLER_AGREE" >【卖家须立即处理】退款协议等待卖家确认中：<span><%=orderReport.getWAIT_SELLER_AGREE() %></span></a>
    </div>
    <div class="menu_box">
    	<a href="<%=SystemManager.systemSetting.getManageHttp()%>/manage/order!selectList.action?refundStatus=WAIT_SELLER_CONFIRM_GOODS" >【卖家须立即处理】等待卖家收货：<span><%=orderReport.getWAIT_SELLER_CONFIRM_GOODS() %></span></a>
    </div>
    <div class="menu_box">
    	<a href="<%=SystemManager.systemSetting.getManageHttp()%>/manage/order!selectList.action?refundStatus=SELLER_REFUSE_BUYER" >【等待买家处理完】卖家不同意协议，等待买家修改：<span><%=orderReport.getSELLER_REFUSE_BUYER() %></span></a>
    </div>
    <div class="menu_box">
    	<a href="<%=SystemManager.systemSetting.getManageHttp()%>/manage/order!selectList.action?refundStatus=WAIT_BUYER_RETURN_GOODS" >【等待买家处理完】退款协议达成，等待买家退货：<span><%=orderReport.getWAIT_BUYER_RETURN_GOODS() %></span></a>
    </div>
</div>
<!--end menu-->
<!--menu--> 
<div class="menu">
	<div class="menu_top">
    	<p>吐槽和缺货</p>
    </div>
    <div class="menu_box">
    	<a href="<%=SystemManager.systemSetting.getManageHttp()%>/manage/product!selectList.action?selectOutOfStockProduct=true" >缺货商品数：<span><%=orderReport.getOutOfStockProductCount()%></span></a>
    </div>
    <div class="menu_box">
    	<a href="<%=SystemManager.systemSetting.getManageHttp()%>/manage/comment!selectList.action?selectCommentFromIndex=true" >未回复的吐槽评论数：<span><%=orderReport.getNotReplyCommentCount()%></span></a>
    </div>
</div>
<!--end menu-->
</div>
</body>
</html>
