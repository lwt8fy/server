<%@page import="net.jeeshop.core.system.bean.User"%>
<%@page import="java.util.Map"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="net.jeeshop.core.ManageContainer"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head> 
<%@ include file="/resource/common_html_meat.jsp"%>
</head>

<%

		Map<String,Object> map = ActionContext.getContext().getSession();
		User u = (User)map.get(ManageContainer.manage_session_user_info);
		if(u==null){
			out.println("u="+u);
			response.sendRedirect("user!loginOut.action");
			return;
		}
		//out.print(u.getNickname()+"("+u.getUsername()+")");
	%>

<frameset rows="110,*" frameborder="no" border="0" framespacing="0">
<frame id=_top name='top' src="top.jsp" scrolling=no this.textBox1.ScrollBars = ScrollBars.None;/>
 
<frameset  id=_main name='main' frameSpacing=0 frameBorder=no cols=210,10,*>
<frame src="<%=request.getContextPath()%>/manage/system/left.jsp" id="leftFrame" name="leftFrame">
<FRAME name='control' src="kun.jsp" scrolling=no >
<frame src="<%=request.getContextPath()%>/manage/user!initManageIndex.action" name="rightFrame" scrolling=yes id="kunFrame" />

</frameset>
</frameset>
	
	
	
	
	
<frameset cols="210,*" > 
	<frame src="<%=request.getContextPath()%>/manage/system/left.jsp" name="leftFrame" noresize="noresize"/>
<%-- 	<frame src="<%=request.getContextPath()%>/manage/system/right.jsp" name="rightFrame" /> --%>
	<frame src="<%=request.getContextPath()%>/manage/user!initManageIndex.action" name="rightFrame" />
</frameset>
</html>
