<%@ page contentType="text/html; charset=UTF-8"%>

<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="net.jeeshop.services.front.account.AccountService"%>
<%@page import="net.jeeshop.services.front.account.bean.Account"%>
<%@page import="net.jeeshop.core.util.TwoDimensionCode"%>
<%@page import="net.jeeshop.core.front.SystemManager"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="java.util.UUID"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="net.jeeshop.core.KeyValueHelper"%> 
<%
String ip = request.getHeader("X-Forwarded-For");
if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    ip = request.getHeader("Proxy-Client-IP");
}
if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    ip = request.getHeader("WL-Proxy-Client-IP");
}
if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    ip = request.getHeader("HTTP_CLIENT_IP");
}
if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
}
if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    ip = request.getRemoteAddr();
}
String ips=KeyValueHelper.get("FORBID_IP");
if(ips.contains(ip.trim())){
	System.out.println(ip+"++++++++++++++++++download_pds.jsp");
	return;
}
%>
<%
String u=null;
Object o=request.getAttribute("u");
if(o!=null){
	u=o.toString();
}else{
u=request.getParameter("u");	
}
WebApplicationContext app = WebApplicationContextUtils
.getWebApplicationContext(request.getSession()
		.getServletContext());
AccountService accService = (AccountService) app
.getBean("accountServiceFront");
String name="";
if(u!=null&&u.length()>0&&!u.equals("52")){
Account ac= accService.selectById(u);
if(ac!=null){
	name=ac.getAccount();
}else{
	u="52";
	name="神州牧易猪贸通";
}
//生成二维码
String realPath = ServletActionContext.getServletContext().getRealPath("/appUpload/ewm");
TwoDimensionCode t=new TwoDimensionCode();
String con=SystemManager.systemSetting.getWww()+"/appDownload/download.jsp?u="+u;
t.encoderQRCode(con,realPath+"/"+u+".png");
}else{
	u="52";
	name="神州牧易猪贸通";
}
%>
<!DOCTYPE html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport"
        content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>猪贸通下载</title>

  <!-- Set render engine for 360 browser -->
  <meta name="renderer" content="webkit">

  <!-- No Baidu Siteapp-->
  <meta http-equiv="Cache-Control" content="no-siteapp"/>

  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/amazeui.min.css">
  <script src="<%=request.getContextPath() %>/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
  <script type="text/javascript">
  		function openme(){
	document.getElementById('zhezhao').style.display='block';
	document.getElementById('popup').style.display='block';
	}
	function closeme(){
	document.getElementById('zhezhao').style.display='none';
	document.getElementById('popup').style.display='none';
	}
  		function openme2(){
	document.getElementById('zhezhao').style.display='block';
	document.getElementById('popup2').style.display='block';
	}
	function closeme2(){
	document.getElementById('zhezhao').style.display='none';
	document.getElementById('popup2').style.display='none';
	}

	function openme3(){

		var mobile = $("#mobile").val();
		if(!/^(1[2-9])\d{9}$/i.test(mobile)){
			 $("#err_info").html("手机号格式错误,请重新输入!");
		  return false;
		}
		$("#yzid").val("");
		reloadImg3();
		closeme();
		document.getElementById('zhezhao').style.display='block';
		document.getElementById('popup3').style.display='block';
		}
		function closeme3(){
		document.getElementById('zhezhao').style.display='none';
		document.getElementById('popup3').style.display='none';
		openme();
		}
	
	
	function qd(){
		closeme2();
	location.href = "<%=request.getContextPath()%>/appDownload/zmt.apk";
	}

	
	
	function check(){
		var mobile=$('#mobile').val();
		var _yzm=$('#_yzm').val();
		var username=$('#username').val();
		var password=$('#password').val();
		if(username==""){
			$("#err_info").html("帐号不能为空!");
			return false;
		}
		if(password==""){
			$("#err_info").html("密码不能为空!");
			return false;
		}
		if(!/^(1[2-9])\d{9}$/i.test(mobile))
		{
		  $("#err_info").html("手机号格式错误,请重新输入!");
		  return false;
		}
		if(!_yzm || _yzm.length!=4){
			$("#err_info").html("验证码错误,请重新输入!");
			return false;
		}
		return true;
	}


	//判断是否电脑
		function IsPC() {
		    var userAgentInfo = navigator.userAgent;
		    var Agents = ["Android", "iPhone",
		                "SymbianOS", "Windows Phone",
		                "iPad", "iPod"];
		    var flag = true;
		    for (var v = 0; v < Agents.length; v++) {
		        if (userAgentInfo.indexOf(Agents[v]) > 0) {
		            flag = false;
		            break;
		        }
		    }
		    return flag;
		}

		

var is_weixin = (function(){return navigator.userAgent.indexOf('MicroMessenger') !== -1})();
window.onload = function(){
	if(IsPC()){
		document.getElementById('status').className ="download_btn2";
		document.getElementById('div1').className ="popup_confirm2";
		document.getElementById('div2').className ="popup_confirm2";
		document.getElementById('div3').className ="popup_confirm2";
	}
	var winHeight = typeof window.innerHeight != 'undefined' ? window.innerHeight : document.documentElement.clientHeight;
	var btn = document.getElementById('status');
	var tip = document.getElementById('weixin-tip');
	var close = document.getElementById('close');
	if( is_weixin ){
		btn.onclick = function(e){
			
			tip.style.display = 'block';
			return false;
		}
		close.onclick = function(){
			tip.style.display = 'none';
		}
	}else{ 
		var error="${error}";
		if(error!=null&&error!="null"&&error!=""){
			if(error=="0"){
				btn.onclick = function(e){
					window.location.href="<%=request.getContextPath()%>/appDownload/zmt.apk";
				}
				openme2();
			}else if(error=="1"){
				openme();
				$("#err_info").html("验证码错误,请重新输入!");
			}else if(error=="2"){
				openme();
				$("#err_info").html("手机号与验证码不匹配,请重新输入!");
			}else if(error=="3"){
				openme();
				$("#err_info").html("用户名或密码错误!");
			}else if(error=="4"){
				$("#divewm").html("您已注册过,并关联帐号成功,可直接下载!");
				openme2();
				btn.onclick = function(e){
					window.location.href="<%=request.getContextPath()%>/appDownload/zmt.apk";
				}
			}else if(error=="5"){
				openme();
				$("#err_info").html("注册失败,请重试!");
			}
		}
	}
	
} 
function reloadImg3() {
	document.getElementById("codes3").src = "<%=request.getContextPath() %>/ValidateImage.do?" + "radom="+ Math.random();
	$("#yzid").focus();
}


var reg = /1\d{10}/;
var wait = 60;
function sendsms() {
	var yzid=$("#yzid").val();
	if(yzid==""||yzid.length!=4){
		alert("验证码输入错误!");
		return;
	}
	closeme3();
	var o=document.getElementById("btn");
	if (wait == 0) {
		o.removeAttribute("disabled");
		o.innerHTML = "获取验证码";
		wait = 60;
	} else {
		if (wait == 60) {
			var sj = $("#mobile").val();
			if (sj != "" && reg.test(sj)) {
				$.post("<%=request.getContextPath()%>/sendSMSzmt.html?da="
						+ new Date().getTime(), {
					phone : sj,yzid:yzid
				}, function(data) {
				});
				$("#err_info").html("");
			} else {
				 $("#err_info").html("手机号格式错误,请重新输入!");
				sj.focus();
				return;
			}
		}
		o.setAttribute("disabled", true);
		o.innerHTML = "重新获取(" + wait + ")";
		wait--;
		setTimeout(function() {
			sendsms(o);
		}, 1000)
	}

} 

	
  </script>
<style>
#weixin-tip{display:none;position:fixed;left:0;top:0;background:rgba(0,0,0,0.8);filter:alpha(opacity=80);width:100%;height:100%;z-index:100;}
#weixin-tip img{display:inline-block;width:100%;height:auto;}
#weixin-tip p{text-align:center;margin-top:10%;padding:0 0;position:relative;}
#weixin-tip .close{color:#fff;padding:5px;font:bold 40px/48px simsun;text-shadow:0 1px 0 #ddd;position:absolute;top:0;left:5%;}

/*new*/
.download_bg{position:relative;width:100%;z-index: 25;top: 0;left: 0;right: 0;bottom: 0;}
.download_bg_img{display:inline-block;width:100%;height:auto;margin:0 auto;}
.download_btn{cursor:pointer;position:absolute; background:none; display:block;position: fixed;z-index: 99;width: 200px;height: 50px;left:22%;top:6%;}
.download_btn2{cursor:pointer;position:absolute; background:none; display:block;position: fixed;z-index: 99;width:45%;height:30%;left:28%;top:15%;}

/*登陆弹出框*/
#zhezhao{ display:none; position:fixed; z-index:1005; height:100%; width:100%; background:#000; filter:alpha(opacity=25); opacity:0.25;}
.popup{ width:100%;height:400px; overflow:hidden; top:40px; position:absolute; display:none;z-index:1006;}
.popup_confirm{width:98%; height:auto;position:relative; margin:0 auto;}
.popup_confirm2{width:40%; height:auto;position:relative; margin:0 auto;}

.popup_box{width:98%;height:auto; background-color:#FFF;border:2px solid #d6d6d6;}
.popup_box_top{height:3.5rem;margin-bottom:10px;border-bottom:1px solid #eaeaea;}
.popup_box_top strong{margin-left:15px;float:left; font-size:1.2em; line-height:2em;}
.popup_box_top a{float:right;width:2rem;height:2rem;margin-right:2rem;margin-top:0rem;}

.phone_box_main{margin:2rem auto;overflow:hidden;}
.phone_box{clear:both;margin:1em auto 0.2em;overflow:hidden;}
.phone_box label{float:left;line-height:2.2; text-align:right;width:8rem;}
.phone_box input{
	float:left;
 	display: block;
	width:63%;
	padding: .5em;
	font-size: 1.6rem;
	line-height: 1.2;
	color: #555;
	vertical-align: middle;
	background-color: #fff;
	background-image: none;
	border: 1px solid #ccc;
	border-radius: 0;
	-webkit-appearance: none;
	-webkit-transition: border-color .15s ease-in-out, -webkit-box-shadow .15s ease-in-out;
	transition: border-color .15s ease-in-out, -webkit-box-shadow .15s ease-in-out;
	transition: border-color .15s ease-in-out, box-shadow .15s ease-in-out;
	transition: border-color .15s ease-in-out, box-shadow .15s ease-in-out, -webkit-box-shadow .15s ease-in-out
}
.phone_box .yzm_box{width:30%;}
.yzm_img{display:block;height:3.2rem;float:left;margin-left:1.5rem;}
.phone_btn{margin:0 auto;width:35%; display:block;}
</style>
</head>

<body>
<!--弹出框-->
<div id="zhezhao"></div>
<div class="popup am-u-sm-centered" id="popup">
  <div id="div1" class="popup_confirm">
    <form  action="<%=request.getContextPath() %>/user/regZmtPds.html" method="post">
    <div class="popup_box">
      <div class="popup_box_top"><strong>注册信息</strong>
        <a class="am-btn am-btn-block" onClick="closeme()">
        	<i class="am-icon-close"></i>
        </a>
      </div>
      <div class="phone_box_main">
          <div class="phone_box">
            <label>帐号：</label>
            <input type="hidden" id="_tuiGuang" name="u" value="<%=u %>">
            <input onkeyup="javascript:$('#err_info').html('');"  name="username" type="text" id="username" value="${username}" />
          </div>
          <div class="phone_box">
            <label>密码：</label>
            <input onkeyup="javascript:$('#err_info').html('');"  name="password" type="password" id="password" value="${password}" />
          </div>
          <div class="phone_box">
            <label>手机：</label>
            <input onkeyup="javascript:$('#err_info').html('');"  name="mobile" type="text" id="mobile"  value="${mobile}"/>
          </div>
          <div class="phone_box">
            <label>验证码：</label>
            
            <input onkeyup="javascript:$('#err_info').html('');"  class="yzm_box" name="yzm" type="text" id="_yzm"  />
	        			<button onclick="openme3();" type="button" id="btn"  class="yzm_img">获取验证码</button>
            <br/>
            <p id="err_info" style="padding-left: 9rem; font-size:0.8em;color:#f00;"></p>
          </div> 
            <button type="submit" class="am-btn am-btn-danger phone_btn"  onclick="return check();">注册</button>
      </div>    
    </div>
    </form>
  </div>
</div>

<div class="popup am-u-sm-centered" id="popup3">
  <div id="div3" class="popup_confirm">
    <div class="popup_box">
      <div class="popup_box_top"><strong>图形验证码</strong>
        <a class="am-btn am-btn-block" onClick="closeme3()">
        	<i class="am-icon-close"></i>
        </a>
      </div>
      <div class="phone_box_main">
          <div  align="center">
            <label>验证码：</label>
             <input tabindex="3" id="yzid" type="text"  size="4" maxlength="4" style="width: 100px;"/>
	        <img id="codes3" src="<%=request.getContextPath() %>/ValidateImage.do" onclick="javaScript:reloadImg3();"/>
            <br/>
          </div> 
            <button type="button" class="am-btn am-btn-danger phone_btn"  onclick="sendsms();">提交</button>
      </div>    
    </div>
  </div>
</div>


<div class="popup am-u-sm-centered" id="popup2">
  <div id="div2" class="popup_confirm">
    
    <div class="popup_box">
      <div class="popup_box_top"><strong>提示</strong>
        <a class="am-btn am-btn-block" onClick="closeme2()">
        	<i class="am-icon-close"></i>
        </a>
      </div>
      <div class="phone_box_main">
          <div id="divewm" align="center">
         <c:if test="${e.id!=null&&e.id!=''}">
          	注册成功!已生成您的专属二维码!<br>
          	<img id="ewmImg" alt="" src="<%=request.getContextPath() %>/appUpload/ewm/${e.id}.png">
          	</c:if>
          </div>
          <br/>
            <button class="am-btn am-btn-danger phone_btn"  onclick="javascript:qd();">确定</button>
      </div>   
    </div>
  </div>
</div>

<!--end 弹出框-->


<div class="download_bg" id="preloader">
	<img class="download_bg_img" src="<%=request.getContextPath()%>/appDownload/bg.jpg" />
    <div id="status" class="download_btn" onclick="openme();" ></div>
</div>
  <div  style="font-size:1em;color: f00; " align="center">
  推荐人:&nbsp;<%=name %>
  <br>
  <img alt="" src="<%=request.getContextPath() %>/appUpload/ewm/<%=u%>.png">
    
 </div>
  		
  <div id="weixin-tip"><p><img  src="live_weixin.png" alt="微信打开"><span id="close" title="关闭" class="close"><img src="close.png"></span></p></div>		
  		
</body>
</html>



