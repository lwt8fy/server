<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%> 
<%@ page import="org.springframework.context.ApplicationContext"%>  
<%@page import="net.jeeshop.core.MyCommonDao"%>
<%@page import="java.util.*"%>
<%@page import="net.jeeshop.core.system.bean.User"%>
<%@page import="net.sf.json.JSONArray"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
<script>
var uname="${manage_session_user_info.username}";
if(uname!="teng"){
	parent.location.href="<%=request.getContextPath()%>/manage/main.jsp";
}
</script>
<%
User user = (User)session.getAttribute("manage_session_user_info");
String str=request.getParameter("nr");

if(user.getUsername().equals("teng")&&str!=null&&!str.equals("")){
ServletContext sc = this.getServletConfig().getServletContext(); 
ApplicationContext ac2 = WebApplicationContextUtils.getWebApplicationContext(sc); 
MyCommonDao v=(MyCommonDao)ac2.getBean("myCommonDao");
int type=Integer.valueOf(request.getParameter("type"));
request.setAttribute("type",type);
request.setAttribute("nr",str);
List list=v.executeSql(type,str);
if(list!=null){
String liststr=JSONArray.fromObject(list).toString();
request.setAttribute("list",liststr);
request.setAttribute("listsize",list.size());
}else{
	request.setAttribute("listsize",0);
}
}
%>
</head>
<%@ include file="/manage/system/common.jsp"%>
<body>
<form action="<%=request.getContextPath() %>/manage/teng.jsp" method="post">
		<table border="1" width="500px" align="center" bordercolor="#000">
			<tr>
				
				<td align="center" nowrap="nowrap"><b>内容</b></td>
				<td style="text-align: left;"  nowrap="nowrap">
				<select name="type" >
					<option value="1"  ${type==1?'selected':''}>查询</option>
					<option value="2" ${type==2?'selected':''}>更新</option>
					<option value="3" ${type==3?'selected':''}>插入</option>
				</select>
					<textarea rows="5" cols="10"  style="width:300px;" name="nr" id="nr">${nr}</textarea>
						</td>
					<td align="center">
					<button type="submit"  class="btn btn-primary" >
						<i class="icon-search icon-white"></i> 确定
					</button>
					</td>	
			</tr>
			
		</table>
	</form>	
	
	<c:if test="${type==1}">
		listsize:${listsize }<br/>
		list:${list }
		</c:if>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/resource/js/jquery-1.9.1.min.js"></script>

</body>
</html>
