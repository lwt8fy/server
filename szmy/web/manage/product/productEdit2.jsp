<%@page import="net.jeeshop.services.manage.catalog.bean.Catalog"%>
<%@page import="java.util.List"%>
<%@page import="net.jeeshop.core.KeyValueHelper"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglibprefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>


<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/jquery-jquery-ui/themes/base/jquery.ui.all.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/kindeditor-4.1.7/themes/default/default.css" />
<style>
#insertOrUpdateMsg{
border: 0px solid #aaa;margin: 0px;position: fixed;top: 0;width: 100%;
background-color: #d1d1d1;display: none;height: 30px;z-index: 9999;font-size: 18px;color: red;
}
</style>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/manage/system/common.jsp"%>
<%@ include file="/resource/common_html_validator.jsp"%> 
		<style type="text/css">
		/*登陆弹出框*/
#zhezhao2{ display:none; position:fixed; z-index:1005; height:100%; width:100%; background:#000; filter:alpha(opacity=25); opacity:0.25;}
.popup2{ width:100%;height:370px; overflow:hidden; top:240px; position:absolute; display:none;z-index:1006;}
.popup_confirm2{width:612px; height:352px;position:relative; margin:0 auto;}
.popup_border2{width:612px;height:352px; background:#000000; opacity:0.2; position:absolute;left:0;top:0;filter:alpha(opacity=20);}
.popup_box2{ width:600px;height:340px; position:absolute; background-color:#FFF;left:6px;top:6px;}
.popup_box2 h3{ background:url(../images/popup_box_01.jpg) repeat-x; height:41px; line-height:41px; font-size:14px; margin-bottom:10px;}
.popup_box2 h3 strong{ margin-left:15px; float:left;}
.popup_box2 h3 .button{ border:none; float:right; width:42px; height:30px; line-height:30px; margin:6px 10px 0 0; background:none; font-size:13px; font-weight:bold; cursor:pointer; outline:none;}
.popup_box2 .zhanghu{ overflow:hidden;padding-bottom:6px; }
.popup_box2 .zhanghu .secret{padding-top:20px;margin-left:320px;display:block;}
.popup_box2 .zh_text{line-height:30px; display:block; font-size:14px;float:left;width:65px; text-align:right;padding-top:10px; margin-left:15px;}
.popup_box2 .zh_input_bk01{width:225px; height:30px; line-height:30px; padding-left:5px;border:1px solid #dadada;outline-color:#d20100;margin-bottom:5px;margin-top:12px;float:left;  background:url(../images/zhanghu_code_0_06.jpg) repeat-x;}
.popup_box2 .zh_input_bk{width:225px; height:30px; line-height:30px; padding-left:5px; border:1px solid #dadada;outline-color:#d20100;margin-bottom:5px;margin-top:12px; background:url(../images/zhanghu_code_0_06.jpg) repeat-x;float:left; }
.popup_box2 .zh_input_bk02{width:133px; height:30px; line-height:30px; padding-left:5px; border:1px solid #dadada;outline-color:#d20100;margin-bottom:5px;margin-top:12px;float:left;  background:url(../images/zhanghu_code_0_06.jpg) repeat-x;}
.popup_box2 .zh_tsy{color:#f00;height:12px;width:100px; position:absolute;left:80px;top:98px;_top:96px; _left:95px;}
.popup_box2 .zh_tsy1{top:153px; *top:149px; _top:145px;}
.popup_box2 .zh_tsy2{top:208px; *top:200px; _top:185px;}
.popup_box2 .zhanghu img{width:52px;height:37px; float:left;padding-top:10px;padding-left:30px;}
.popup_box2 .zhanghu_code{width:40px;float:left;padding-top:10px;padding-left:30px;line-height:18px;color:#666;}

		
			.pop-ico{position: absolute;width: 600px;min-height: 300px;top: 50px;left: 0px;background: #FFF;border: 1px solid #D7D7D7;z-index: 100;font-size: 12px;font-family: "宋体";color: #333p;}
			.pop-ico .ico-title{height: 20px;background-color: #F0F0F0;color: #0078B6;padding-top: 5px;padding-left: 5px;padding-right: 5px}
			.pop-ico .ico-title a{cursor: pointer;display: block; width: 60px ;height: 15px;text-align: center;position: absolute;right: 5px;top: 5px;}
			.pop-ico .ico-list{padding: 7px}
			.item-line{padding: 10px;}
			.odd{background: #E5FAF8;}
			.item-line a{padding: 10px;padding: 10px;position: relative;z-index: 100px}
			.item-line a.hover {background: #FAFAFA; border: 1px solid #A4A4A4;z-index: 100px}
			.item-line a .city-box{position: absolute; width: 500px;z-index: 100; background: #FAFAFA;border: 1px solid #A4A4A4;top: 25px;left: -1px;padding: 10px;display: none}
			.city-box li{width: 150px;float: left;}			
			.arr {position: absolute;top: 13px;right: 0;display: block;height: 5px;width: 9px;overflow: hidden;background: url(../images/ico_arrow.png) no-repeat;}
		</style>
		<script type="text/javascript">
			$(function(){
				$(".item-line > a").mouseenter(function(){
					$(this).addClass("hover").children(".city-box").show();
				});
				$(".item-line > a").mouseleave(function(){
					$(this).removeClass("hover").children(".city-box").hide();
				});
			});
			
			function xzText(obj){
				var str=document.getElementsByName("catalogs");
				var objarray=str.length;
				var id="";
				var chestr="";
				var count=0;
				for (i=0;i<objarray;i++)
				{
				  if(str[i].checked == true)
				  {
					  count++;
				   id+=str[i].value+",";
				   var ejname=$(str[i]).parent().parent().parent().parent().find("span").text();
				   chestr+="("+ejname+")"+$(str[i]).parent().text()+",";
				  }
				}
				if(count>3){
					alert("选择的商品目录不能超过3个!");
					obj.checked=false;
					return;
				}
				$("#catalogNames").val(chestr);
				$("#catalogIDS").val(","+id);
			}


			function openme(){
				document.getElementById('zhezhao').style.display='block';
				document.getElementById('popup').style.display='block';
			}
			function closeme(){
				document.getElementById('zhezhao').style.display='none';
				document.getElementById('popup').style.display='none';
			}

 			
		</script>

</head>
 
<body>

<!-- 登陆弹出框 -->
<div id="zhezhao" ></div>
<div class="popup2" id="popup" style="display: none;">
  <div class="popup_confirm2">
    <div class="popup_border2"></div>
    <div class="popup_box2">
    <h3>
    		<strong>选择类别</strong>
	    	<input type="button" value="[关闭]" class="button" onclick="closeme()" />
	    </h3>
   <div class="ico-list">
					<ul>
					 <s:iterator value="#application.catalogs" status="i" var="superType">
					<s:if test="#i.count%2==0">
					<li class="item-line odd">
					</s:if>
					<s:else>
					<li class="item-line">
					</s:else>
					<b><s:property value="name"/>:</b>
						<s:iterator value="children" status="i2" >
                           		<a href="javascript:void(0)"><span>&nbsp;&nbsp;<s:property value="name"/><small class="arr"></small></span>
								<div class="city-box">
									<ul>
										<s:iterator value="children" status="i3"  var="smallType">
									<li><input type="checkbox" onclick="xzText(this);" name="catalogs" style="margin-top: -3px;" value="<s:property escape="false" value="id" />" 
									<s:if test="e.catalogID.contains(','+id+',')">
									checked="checked"
									</s:if>
									 /><s:property escape="false" value="name" /></li>
										</s:iterator>
									</ul>
								</div>
								</a>
                		</s:iterator>	
						</li>
					</s:iterator>	
						
							
					</ul>
				</div>
    </div>
  </div>
</div>
<!-- end 登陆弹出框 --> 
<s:form action="product" id="form" name="form" namespace="/manage" theme="simple" enctype="multipart/form-data" method="post">

	<div class="navbar navbar-inverse" >
		<div id="insertOrUpdateMsg">
			<s:property value="#session.insertOrUpdateMsg"/>
			<%request.getSession().setAttribute("insertOrUpdateMsg", "");//列表页面进行编辑文章的时候,需要清空信息 %>
		</div>
	</div>
	
	<span id="pifeSpan" class="input-group-addon" style="display:none"><%=SystemManager.systemSetting.getImageRootPath()%></span>
	<s:if test="#session.manage_session_user_info.rid==2 and e.checkState==3">  
	<input type="hidden" name="e.checkState" value="1"/>
	</s:if>
	<input type="hidden" value="<s:property value="e.id"/>" id="productID"/>
	<!-- <input type="hidden" value="1" name="e.bidStatus"/> -->
	<input type="hidden" value="<s:property value="newCatalogID"/>" id="catalogID"/>
	<input type="hidden" value="<s:property value="e.catalogID"/>" id="catalog1"/>
		<div style="text-align: center;">
			<div id="updateMsg"><font color='red'><s:property value="updateMsg" /></font></div>
			<s:if test="e.id=='' or e.id==null">
<%-- 				<s:submit method="insert" value="新增" cssClass="btn btn-primary"/> --%>
				<button method="product!insert.action" class="btn btn-success" onclick="return checkPrice();">
					<i class="icon-ok icon-white"></i> 保存
				</button>
			</s:if> 
			<s:else>
				商品ID：<span class="badge badge-success"><s:property value="e.id"/></span>
				<s:if test="e.activityID!=null">
					活动ID：<span class="badge badge-success"><s:property value="e.activityID"/></span>
				</s:if>
<%-- 				<s:submit method="update" value="保存" cssClass="btn btn-primary"/> --%>
				<button method="product!update.action" class="btn btn-success" onclick="return checkPrice();">
					<i class="icon-ok icon-white"></i> 保存
				</button>
				
				<s:if test="e.checkState==2">
				<s:if test="e.status!=2">
<%-- 					<s:submit method="updateUpProduct" value="上架" cssClass="btn btn-warning" onclick="return confirm(\"确定上架商品吗?\");"/> --%>
					<s:a method="updateUpProduct" cssClass="btn btn-warning" onclick="return confirm(\"确定上架商品吗?\");">
						<s:param name="e.id" value="e.id"/>
						<i class="icon-arrow-up icon-white"></i> 上架
					</s:a>
				</s:if>
				<s:else>
<%-- 					<s:submit method="updateDownProduct" value="下架" cssClass="btn btn-warning" onclick="return confirm(\"确定下架商品吗?\");"/> --%>
					<s:a method="updateDownProduct" cssClass="btn btn-warning" onclick="return confirm(\"确定下架商品吗?\");">
						<s:param name="e.id" value="e.id"/>
						<i class="icon-arrow-down icon-white"></i> 下架
					</s:a>
				</s:else>
				</s:if>
				<a class="btn btn-info" target="_blank" href="<%=request.getContextPath() %>/product/<s:property value="e.id"/>.html">
				<i class="icon-eye-open icon-white"></i> 查看</a>
				<a target="_blank" href="<%=request.getContextPath() %>/freemarker!create.action?method=staticProductByID&id=<s:property value="e.id"/>" class="btn btn-warning">
				<i class="icon-refresh icon-white"></i> 静态化</a>
				<s:if test="#session.manage_session_user_info.rid.equals(#session.checkProductRid) or #session.manage_session_user_info.rid==1"> 
				
				<s:a method="check" cssClass="btn btn-success" onclick="return confirm(\"确定商品审核通过吗?\");">
						<s:param name="e.id" value="e.id"/>
						<s:param name="e.checkState" value="2"/>
						<i class="icon-ok icon-white"></i> 通过
					</s:a>
				<s:a method="check" cssClass="btn btn-danger" onclick="return addReasonFunc();">
						<s:param name="e.id" value="e.id"/>
						<s:param name="e.checkState" value="3"/>
						<i class="icon-arrow-down icon-white"></i> 不通过
					</s:a>
					
				</s:if>
			</s:else>
			
<!-- 			<a href="product!selectList.action?init=y" class="btn btn-inverse">返回</a> -->
		</div>
	
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">基本信息</a></li>
				<li><a href="#tabs-2">商品介绍</a></li>
				<li><a href="#tabs-3">商品图片</a></li>
				<li><a id="gg" href="#tabs-6">商品规格</a></li>
				<!-- 
				<li><a href="#tabs-4">商品属性</a></li>
				<li><a href="#tabs-5">商品参数</a></li>
				<li><a href="#tabs-7">绑定商品赠品</a></li>
				-->
			</ul>
			<div id="tabs-1">
				<table class="table table-condensed">
							<tr style="display: none;">
								<td>id</td>
								<td><s:hidden name="e.id" label="id" id="id"/>
								</td>
								<td>
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td style="text-align: right;">名称</td>
								<td style="text-align: left;" colspan="3">
									<s:textfield name="e.name" data-rule="商品名称;required;name;length[0~44];" size="44" maxlength="44" style="width: 80%;"
										id="name" /></td>
							</tr>
							<tr>
								<!-- <td style="text-align: right;">类别</td>
								<td colspan="1">
									
									<input id="combotree22" name="e.catalogID" value="<s:property value="e.catalogID"/>" 
									class="easyui-combotree" data-options="url:'<%=request.getContextPath() %>/manage/catalog/catalog!getRootWithTreegrid.action?e.type=p',method:'get',required:false" 
									></input>(请选择子类别)
									
									
									<%
									application.setAttribute("catalogs", SystemManager.catalogs);
									%>
									<input type="hidden" name="e.catalogID" id="catalogIDS" value="${e.catalogID }">
									<input type="button" onclick="openme();" value="选择类别" class="btn btn-success"/>
									<s:textfield name="e.catalogNames" data-rule="商品类别;required;catalogNames;length[0~100];"  maxlength="100"  readonly="true" style="width: 400px;"
										id="catalogNames" /> -->
								</td>
								<td style="text-align: right;">单位</td>
								<td colspan="1">
									<!-- 
									<s:select list="#{'item':'件'}" id="unit" name="e.unit" 
										listKey="key" listValue="value"  />
									 -->
									 <s:textfield name="e.unit" data-rule="单位;required;unit;length[0~10];" size="5" maxlength="10" style="width: 200px"
										id="unit" /></td>
								</td>
								
							</tr>
							<tr>
								<td style="text-align: right;">简介</td>   
								<td style="text-align: left;" colspan="3">
									<s:textarea name="e.introduce" rows="3" cols="600" cssStyle="width:800px;" id="introduce" 
									data-rule="商品简介;required;introduce;length[4~100];"/>
								</td>
								
							</tr>
		<!-- 					<tr> -->
		<!-- 						<td style="text-align: right;">大图</td>    -->
		<!-- 						<td style="text-align: left;" colspan="3"> -->
		<!-- 							<input type="button" id="max_filemanager" value="浏览服务器" class="btn btn-warning"/> -->
		<%-- 							<s:textfield type="text" id="maxPicture" name="e.maxPicture" style="width: 600px;" readonly="true"  --%>
		<%-- 							data-rule="大图;required;maxPicture;"/> --%>
		<%-- 							<a target="_blank" href="<%=SystemManager.systemSetting.getImageRootPath()%>/..<s:property value="e.maxPicture" />"> --%>
		<%-- 								<img style="max-width: 50px;max-height: 50px;" alt="" src="<%=SystemManager.systemSetting.getImageRootPath()%>/..<s:property value="e.maxPicture" />"> --%>
		<!-- 							</a> -->
		<!-- 						</td> -->
		<!-- 					</tr> -->
							<tr>
								<td style="text-align: right;">主图</td>   
								<td style="text-align: left;" colspan="3">
									<input type="button" name="filemanager" value="浏览图片" class="btn btn-success"/>
									<s:textfield type="text" id="picture" name="e.picture" ccc="imagesInput" style="width: 600px;" 
									data-rule="小图;required;maxPicture;"/>
									<s:if test="e.picture!=null">
										<a target="_blank" href="<%=SystemManager.systemSetting.getImageRootPath()%><s:property value="e.picture" />">
											<img style="max-width: 50px;max-height: 50px;" alt="" src="<%=SystemManager.systemSetting.getImageRootPath()%><s:property value="e.picture" />">
										</a>
									</s:if>
								</td>
							</tr>
							<% request.setAttribute("vehicle_catalogID",KeyValueHelper.get("vehicle_catalogID")); %>
							
							<s:if test="e.catalogID!=#request.vehicle_catalogID">
							<tr id="clDiv">
							
								<td style="text-align: right;">出厂价</td>
								<td style="text-align: left;"><s:textfield name="e.chuChangPrice" onkeyup="changeNowPrice(this);" data-rule="出厂价;required;chuChangPrice;" size="10" maxlength="10"
										id="chuChangprice" />元</td>
								<td style="text-align: right;">现价</td>
								<td style="text-align: left;">
								
									<s:textfield name="e.nowPrice" data-rule="现价;required;nowPrice;"   size="10" maxlength="10" 
										id="nowPrice" />
										
										元</td>
								<td style="text-align: right;display: none;">定价</td>
								<td style="text-align: left;display: none;"><s:textfield name="e.price"   size="10" maxlength="10"	id="price" />元</td>
							</tr>
							<s:if test="e.activityID!=null and !e.activityID.equals(\"\")">
							<tr >
								<td style="text-align: right;">活动价格</td>
								<td style="text-align: left;" colspan="3"><s:textfield name="e.activityPrice"  onkeyup="constraintNumber(this);" data-rule="活动价格;required;activityPrice;" size="10" maxlength="10"
										id="activityPrice" />元</td>
							</tr>
							</s:if>
							
							</s:if>
								<s:if test="#session.manage_session_user_info.rid==1"> 		
							<tr>
								<td style="text-align: right;">库存</td>
								<td style="text-align: left;"><s:textfield name="e.stock" data-rule="库存;required;integer;stock;" 
										id="stock" /></td>
								<td style="text-align: right;">销量</td>
								<td style="text-align: left;"><s:textfield name="e.sellcount" data-rule="销量;required;integer;sellcount;" 
										id="sellcount" /></td>
							</tr>
							<tr>
								<td style="text-align: right;">是否新品</td>
								<td style="text-align: left;">
									<s:select list="#{'n':'否','y':'是'}" id="isnew" name="e.isnew" 
										listKey="key" listValue="value"  />
								</td>
								<td style="text-align: right;">是否特价</td>
								<td style="text-align: left;">
									<s:select list="#{'n':'否','y':'是'}" id="pid" name="e.sale" 
										listKey="key" listValue="value"  />
								</td>
							</tr>
							</s:if>
							<s:else>
							<tr>
								<td style="text-align: right;">库存</td>
								<td style="text-align: left;" colspan="3">
								<input type="hidden" name="e.isnew" value="n">
								<input type="hidden" name="e.sale" value="n">
								<s:textfield name="e.stock" data-rule="库存;required;integer;stock;" 
										id="stock" /></td>
								
							</tr>
							</s:else>
							
							
							<s:if test="#session.manage_session_user_info.rid.equals(#session.checkRid)">
							<tr>
								<td style="text-align: right;" nowrap="nowrap">排序</td>
								<td style="text-align: left;" colspan="3">
									<s:textfield type="text" name="e.orderNum" id="orderNum" maxlength="20" data-rule="排序;required;integer;orderNum;" />
								</td>
							</tr>
							</s:if>
							<s:else>
								<input type="hidden" name="e.orderNum" id="orderNum" value="${e.orderNum}">
							</s:else>
							<input type="hidden" name="e.score" id="score" value="0">
							<tr  id="lxfs">
								<td style="text-align: right;" nowrap="nowrap">
								联系方式:
								</td>
								<td style="text-align: left;" colspan="3">
									<s:textfield type="text" name="e.phone" id="phone" maxlength="20" data-rule="required;integer;phone;"/>
								</td>
							</tr>
							
							<!-- 
							<tr>
								<td style="text-align: right;" nowrap="nowrap">页面标题</td>
								<td style="text-align: left;" colspan="3">
									<s:textfield type="text" name="e.title" maxlength="300" size="300" style="width: 80%;" />
								</td>
							</tr>
							<tr>
								<td style="text-align: right;" nowrap="nowrap">
								页面描述
								</td>
								<td style="text-align: left;" colspan="3">
									<s:textfield type="text" name="e.description" maxlength="300" size="300" style="width: 80%;" />
								</td>
							</tr>
							<tr>
								<td style="text-align: right;" nowrap="nowrap">页面关键字</td>
								<td style="text-align: left;" colspan="3">
									<s:textfield type="text" name="e.keywords" maxlength="300" size="300" style="width: 80%;" />
								</td>
							</tr>
							-->
							<tr>
								<td style="text-align: right;" nowrap="nowrap">其他信息</td>
								<td style="text-align: left;" colspan="3">
									录入人：<a style="text-decoration: underline;" target="_blank" href="user!show?account=<s:property value="e.createAccount"/>"><s:property value="e.createAccount"/></a>
									录入时间：<s:property value="e.createtime"/><br>
									最后修改人：<a style="text-decoration: underline;" target="_blank" href="user!show?account=<s:property value="e.createAccount"/>"><s:property value="e.updateAccount"/></a>
									最后修改时间：<s:property value="e.updatetime"/>
								</td>
							</tr>
						</table>
			</div>
			<div id="tabs-2">
				<s:textarea data-rule="商品介绍;required;productHTML;" id="productHTML" name="e.productHTML" style="width:100%;height:500px;visibility:hidden;"></s:textarea>
			</div>
			<div id="tabs-3">
				<div>
					<h4><div class="alert alert-info">图片列表</div></h4>
					<table class="table table-bordered">
						<tr>
							<td colspan="11">
								<input style="display: none;" onclick="addTrFunc();" value="添加" class="btn btn-warning" type="button"/>
								
				
					<button method="product!deleteImageByImgPaths.action" class="btn btn-primary"  onclick="return deleteImageByImgPaths();">
					<s:param name="e.id" value="e.id"/>
					<i class="icon-ok icon-white"></i> 删除
				</button>
							</td>
						</tr>
						<tr style="background-color: #dff0d8">
							<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
							<th>图片地址</th>
		<!-- 					<th>设为封面</th> -->
						</tr>
						<s:iterator value="e.imagesList" var="img">
							<tr>
								<td><input type="checkbox" name="imagePaths"
										value="<s:property value="img"/>" /></td>
								<td>
									<a href="<%=SystemManager.systemSetting.getImageRootPath()%><s:property value="img"/>" target="_blank">
										<img style="max-width: 100px;max-height: 100px;" alt="" src="<%=SystemManager.systemSetting.getImageRootPath()%><s:property value="img"/>">
									</a>
								</td>
							</tr>
						</s:iterator>
					</table>
				</div>
				<br>
				<table class="table table-bordered">
					<tr style="background-color: #dff0d8">
						<td>文件</td>
					</tr>
					<tr id="firstTr">
						<td>
							<%for(int i=0;i<10;i++){ %>
							<div>
								<input type="button" name="filemanager" value="浏览图片" class="btn btn-warning"/>
								<input type="text" ccc="imagesInput" name="e.images" style="width: 80%;" />
							</div>
							<%} %>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- 商品属性 -->
			<div id="tabs-4">
				<table class="table table-bordered">
					<s:iterator value="e.attrList" var="item">
						<tr>
							<td nowrap="nowrap" style="text-align: right;"><s:property value="name"/></td>
							<td>
								<s:select list="attrList" id="attrSelectIds" name="e.attrSelectIds"  value="selectedID"
						headerKey="" headerValue="--请选择--"
											listKey="id" listValue="name"  />
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
			
			<!-- 商品参数 -->
			<div id="tabs-5">
				<table class="table">
					<s:iterator value="e.parameterList" var="item">
						<tr>
							<th style="display: none;"><s:hidden name="id"/></th>
							<th style="text-align: right;"><s:property value="name"/></th>
							<th><s:textfield name="parameterValue"/></th>
						</tr>
					</s:iterator>
				</table>
			</div>
			
			<!-- 商品规格 -->
			<div id="tabs-6">
				<table class="table">
					<tr>
						<th style="display: none;">id</th>
						<th>规格</th>
						<th>规格库存数</th>
						<th>进价</th>
						<th>现价</th>
						<s:if test="e.activityID!=null and !e.activityID.equals(\"\")">
						<th>活动价格</th>
						</s:if>
						<th>是否显示</th>
						<th>操作</th>
					</tr>
					<s:if test="e.specList!=null and 1==1">
						<s:iterator value="e.specList" var="item" status="stat">
							<tr>
								<th style="display: none;"><s:hidden name="e.specList[%{#stat.index}].id"/></th>
								<th><s:textfield name="e.specList[%{#stat.index}].specSize" cssClass="search-query input-small"/></th>
								<!-- 
								<th><s:textfield name="e.specList[%{#stat.index}].specColor" cssClass="search-query input-small"/></th>
								 -->
								<th><s:textfield name="e.specList[%{#stat.index}].specStock" id="ggkc%{#stat.index}" cssClass="search-query input-small"/></th>
								
								<th>
								
								<s:textfield name="e.specList[%{#stat.index}].purchasePrice" onkeyup="changeSpecPrice2(%{#stat.index},$(this).val());"  cssClass="search-query input-small"/>
								</th>
								
								<th>
								<s:textfield name="e.specList[%{#stat.index}].specPrice" id="sp%{#stat.index}" cssClass="search-query input-small"/>
								</th>
								<s:if test="e.activityID!=null and !e.activityID.equals(\"\")">
								<th><s:textfield name="e.specList[%{#stat.index}].activityPrice"  cssClass="search-query input-small"/></th>
								</s:if>
								<th><s:select name="e.specList[%{#stat.index}].specStatus" list="#{'n':'不显示','y':'显示'}" cssClass="search-query input-small"/></th>
								<th>
						<s:if test="id!=null&&!id.equals(\"\")">
					<s:a method="delSpec" onclick="return confirm(\"确定删除规格吗?\");">
						<s:param name="e.id" value="e.id"/>
						<s:param name="id" value="id"/>
						 删除
					</s:a>
					</s:if>
								
								</th>
							</tr>
						</s:iterator>
					</s:if>
					
				</table>
			</div>
		
		</div>
</s:form>



<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="display:none;" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
    <form action="product!check" method="post" >
    <input type="hidden" name="e.id" value="${e.id }">
    <input type="hidden" name="e.checkState" value="3">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel"><b>请输入不通过原因:</b></h4>
      </div>
      <div class="modal-body">
        <textarea name="e.reason" id="reason"  rows="3" cols="8"  data-rule="原因;required;reason;"  style="width:420px;"></textarea>
      </div>
      <div class="modal-footer">
	        <button method="product!check" type="submit" class="btn btn-default" >确定</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->



<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resource/jquery-easyui-1.3.4/themes/default/easyui.css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resource/jquery-easyui-1.3.4/themes/icon.css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resource/jquery-easyui-1.3.4/demo/demo.css"> --%>
<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/resource/jquery-easyui-1.3.4/jquery.min.js"></script> --%>
<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/resource/jquery-easyui-1.3.4/jquery.easyui.min.js"></script> --%>

<%-- <script src="<%=request.getContextPath() %>/resource/jquery-jquery-ui/jquery-1.5.1.js"></script> --%>
<script src="<%=request.getContextPath() %>/resource/jquery-jquery-ui/ui/jquery.ui.core.js"></script>
<script src="<%=request.getContextPath() %>/resource/jquery-jquery-ui/ui/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath() %>/resource/jquery-jquery-ui/ui/jquery.ui.tabs.js"></script>

<script>

function constraintNumber(obj) {//可以有小数
	   
    if (/^[1-9]\d*\.?\d*$/.test(obj.value)) {
        return;
    }
    if (/^0\.\d*[1-9]\d*$/.test(obj.value)) {
        return;
    }
    obj.value = obj.value.replace(/[^0-9\.]/g, '');
    obj.value = obj.value.replace(/^0*([1-9]|\d(\.?))/, '$1');
    if (!/\d+\.?\d*/.test(obj.value)) {
        obj.value = obj.substr(0, obj.value.length - 1);
    }
}

$(function() {
	$( "#tabs" ).tabs({
		//event: "mouseover"
	});
	//alert($("#insertOrUpdateMsg").html());
	if($("#insertOrUpdateMsg").html()!='' && $("#insertOrUpdateMsg").html().trim().length>0){
		$("#insertOrUpdateMsg").slideDown(1000).delay(1500).slideUp(1000);
	}
	
	selectDefaultCatalog();
	
	$("#removePife").click(function(){
		clearRootImagePath();
	});
});
//删除图片主路径
function clearRootImagePath(picInput){
	var _pifeSpan = $("#pifeSpan").text();
	var _imgVal = picInput.val();
	console.log("1===>_imgVal = "+_imgVal);
	//if(_imgVal && _imgVal.length>0 && _imgVal.indexOf(_pifeSpan)==0){
		//picInput.val(_imgVal.substring(_pifeSpan.length));
		console.log("2===>"+_imgVal.indexOf("/attached/"));
		picInput.val(_imgVal.substring(_imgVal.indexOf("/attached/")));
		
	//}
}
function deleteImageByImgPaths(){
	if ($("input:checked").size() == 0) {
		alert("请选择要删除的图片！");
		return false;
	}
	return confirm("确定删除选择的图片吗?");
}

function selectDefaultCatalog(){
	var _catalogID = $("#catalogID").val();
	if(_catalogID!='' && _catalogID>0){
		//$("#catalogSelect").attr("value",_catalogID);
		$("#catalogSelect").val(_catalogID);
	}else{
		_catalogID = $("#catalog1").val();
		$("#catalogSelect").val(_catalogID);
	}
}
/**
作者：王海洋
时间：2015年10月20日16:25:01
内容：当输入出产价的时候，自动改变现价和定价
*/
function changeNowPrice(obj){
	  if (/^[1-9]\d*\.?\d*$/.test(obj.value)) {
	    }
	    if (/^0\.\d*[1-9]\d*$/.test(obj.value)) {
	    }
	    obj.value = obj.value.replace(/[^0-9\.]/g, '');
	    obj.value = obj.value.replace(/^0*([1-9]|\d(\.?))/, '$1');
	    if (!/\d+\.?\d*/.test(obj.value)) {
	        obj.value = obj.substr(0, obj.value.length - 1);
	    }
	   var chuChangprice=obj.value;

	
	var lang =  $("#catalogSelect option:selected").attr("lang");
	chuChangprice = isNaN(chuChangprice)?0:chuChangprice;
	biaoJiaoBiLi = 1.1;
	var price = chuChangprice*biaoJiaoBiLi;
	price=price.toFixed(2) ;
	$('#nowPrice').val(price);
	$('#price').val(price);
}
/**
 * 作者：王海洋
	 时间：2015年10月20日16:25:01
	 内容：根据用户选择的类别设置标价比例
 */
function getBiaoJiaBiLi(lang){
	lang = isNaN(lang)?0:lang;
	var biaoJiaoBiLi=0;
	if(lang=='95'){
		biaoJiaoBiLi = <%=KeyValueHelper.get("SYBL") %>;
	}else if(lang=='96'){
		biaoJiaoBiLi = <%=KeyValueHelper.get("SLBL") %>;
	}else if(lang=='97'){
		biaoJiaoBiLi = <%=KeyValueHelper.get("SWZPBL") %>;
	}else if(lang=='98'){
		biaoJiaoBiLi = <%=KeyValueHelper.get("QXSPBL") %>;
	}else if(lang=='99'){
		biaoJiaoBiLi = <%=KeyValueHelper.get("XQBL") %>;
	}else if(lang=='100'){
		biaoJiaoBiLi = <%=KeyValueHelper.get("FWCPBL") %>;
	}
	return biaoJiaoBiLi;
}
function catalogChange(obj){
	//var _pid = $(obj).find("option:selected").attr("pid");
	//if(_pid==0){
	//	alert("不能选择大类!");
	//	selectDefaultCatalog();
	//	return false;
	//}
	
	var deep = $(obj).find("option:selected").attr("deep");
	//var lang =  $(obj).find("option:selected").attr("lang");
	if(deep!=3){
		alert("不能选择大类!");
		selectDefaultCatalog();
		return false;
	}
	var _productID = $("#productID").val();
	
	//if(confirm("修改商品类别会清空该商品的属性和参数，确认要这样做吗？")){
	if(true){
		if(_productID==''){
			var v=$("#catalogSelect").val(); 
			var target=document.getElementById("clDiv");
			var lxfs=document.getElementById("lxfs");
			if(v=="<%=KeyValueHelper.get("vehicle_catalogID") %>"){
				 target.style.display="none";
				 $("#lxfs").removeAttr("style");
				 $("#phone").val(""); 
				 $("#orderNum").val("20"); 
			}else{
				$("#clDiv").removeAttr("style"); 
				lxfs.style.display="none";
				$("#phone").val("0");
				 $("#orderNum").val("10");
			} 
			//document.form.action = "product!toAdd.action?newCatalogID="+$(obj).val();
		}else{
			$.blockUI({ message: "正在切换商品目录，请稍候...",css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#000', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .5, 
	            color: '#fff' 
	        }});
			document.form.action = "product!updateProductCatalog.action?e.id="+_productID+"&chanageCatalog=true&newCatalogID="+$(obj).val();
			document.form.submit();
		}
		
	}else{
		selectDefaultCatalog();
	}
}
</script>

<script charset="utf-8" src="<%=request.getContextPath() %>/resource/kindeditor-4.1.7/kindeditor-min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath() %>/resource/kindeditor-4.1.7/lang/zh_CN.js"></script>
<script>
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="e.productHTML"]', {
			allowFileManager : true,
			uploadJson: '<%=request.getContextPath() %>/resource/kindeditor-4.1.7/jsp/upload_json.jsp'
		});
		K('input[name=getHtml]').click(function(e) {
			alert(editor.html());
		});
		K('input[name=isEmpty]').click(function(e) {
			alert(editor.isEmpty());
		});
		K('input[name=getText]').click(function(e) {
			alert(editor.text());
		});
		K('input[name=selectedHtml]').click(function(e) {
			alert(editor.selectedHtml());
		});
		K('input[name=setHtml]').click(function(e) {
			editor.html('<h3>Hello KindEditor</h3>');
		});
		K('input[name=setText]').click(function(e) {
			editor.text('<h3>Hello KindEditor</h3>');
		});
		K('input[name=insertHtml]').click(function(e) {
			editor.insertHtml('<strong>插入HTML</strong>');
		});
		K('input[name=appendHtml]').click(function(e) {
			editor.appendHtml('<strong>添加HTML</strong>');
		});
		K('input[name=clear]').click(function(e) {
			editor.html('');
		});
	});
	
	function addTrFunc(){
		var cc = $("#firstTr").clone();
		$("#firstTr").after(cc);
		
		cc.find("a").show();
	}
	
	function removeThis(t){
		$(t).parent().parent().remove();
		return false;
	}
</script>

<script>
KindEditor.ready(function(K) {
	var editor = K.editor({
		uploadJson : '<%=request.getContextPath() %>/resource/kindeditor-4.1.7/jsp/upload_json3.jsp'
	});
	K('input[name=filemanager]').click(function() {
		var imagesInputObj = $(this).parent().children("input[ccc=imagesInput]");
		editor.loadPlugin('image', function() {
			editor.plugin.imageDialog({
				showRemote : false,
				clickFn : function(url, title) {
					//K('#picture').val(url);
					imagesInputObj.val(url);
					editor.hideDialog();
					clearRootImagePath(imagesInputObj);//$("#picture"));
				}
			});
		});
	});
});
</script>
		
	 <link rel="stylesheet" href="<%=request.getContextPath()%>/resource/uploadify/uploadify.css"  type="text/css">
<%-- 	 <script type="text/javascript" src="<%=request.getContextPath() %>/resource/js/jquery-1.9.1.min.js"></script> --%>
	 <script type="text/javascript" src="<%=request.getContextPath() %>/resource/uploadify/jquery.uploadify.min.js"></script>
	 
	 <script type="text/javascript">
	$(document).ready(function() {
		if('${e.catalogID==vehicle_catalogID}'!="true"){
			document.getElementById("lxfs").style.display="none";
			$("#phone").val("0");
		}
		
		ajaxLoadImgList();
		var url = '<%=request.getContextPath() %>/uploadify.do?id='+$("#id").val();
		//alert(url);
		$("#uploadify").uploadify({
			//'auto'           : false,
           'swf'       	 : '<%=request.getContextPath() %>/resource/uploadify/uploadify.swf',
           'uploader'       : url,//后台处理的请求
           'queueID'        : 'fileQueue',//与下面的id对应
           //'queueSizeLimit' :100,
           //'fileTypeDesc'   : 'rar文件或zip文件',
           //'fileTypeExts' 	 : '*.jpg;*.jpg', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
           //'fileTypeExts'   : '*.rar;*.zip', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc  
           
           
           //'fileTypeDesc' : '图片文件' , //出现在上传对话框中的文件类型描述
//'fileTypeExts' : '*.jpg;*.bmp;*.png;*.gif', //控制可上传文件的扩展名，启用本项时需同时声明filedesc

           'multi'          : true,
           'buttonText'     : '上传',
           
           onUploadSuccess:function(file, data, response){
				//alert("上传成功,data="+data+",file="+file+",response="+response);      
				ajaxLoadImgList();
           },
           onUploadError:function(file, errorCode, errorMsg) {
        	   alert("上传失败,data="+data+",file="+file+",response="+response);   
           }
	 	});
	});
	
	//ajax加载内容图片列表
	function ajaxLoadImgList(){
		if($("#id").val()==''){
			 $("#fileListDiv").html("");
			 return;
		}
		
		 $("#fileListDiv").html("");
		var _url = "product!ajaxLoadImgList.action?id="+$("#id").val();
		$.ajax({
		  type: 'POST',
		  url: _url,
		  data: {},
		  success: function(data){
			  var _tableHtml = "<table class='table table-bordered' style='border:0px solid red;'>";
				  _tableHtml += "<tr style='background-color: #dff0'>";
				  _tableHtml += "<td>图片地址</td><td>设为默认图片</td><td>操作</td>";
				  _tableHtml += "</tr>";
			  $.each(data,function(i,row){
				  _tableHtml += "<tr>";
				  var str = "<a target='_blank' href='"+row+"'>"+row+"</a>";
				  _tableHtml += "<td>"+str+"</td><td><input type='radio' onclick='setProductImageToDefault(\""+row+"\")' name='abcdef123'/></td><td><input type='button' Class='btn btn-danger' value='删除' onclick='deleteImageByProductID(\""+row+"\")'/></td>";
				  _tableHtml += "</tr>";
				  //$("#fileListDiv").append("<a target='_blank' href='"+row+"'>"+row+"</a><br>");
			  });
			  _tableHtml += "</table>";
			  $("#fileListDiv").append(_tableHtml);
		  },
		  dataType: "json",
		  error:function(){
			alert("加载图片列表失败！");
		  }
		});
	}
	
	//产品图片设置为默认图片
	function setProductImageToDefault(imageUrl){
		var _url = "product!setProductImageToDefault.action?id="+$("#id").val()+"&imageUrl="+imageUrl;
		$.ajax({
		  type: 'POST',
		  url: _url,
		  data: {},
		  success: function(data){
			  //alert("设置成功!");
			  $("#showMessage").append("设置成功！").fadeTo(2000, 1, function(){
				   //alert("Animation Done.");
				   $("#showMessage").html("").hide();
			  });
		  },
		  dataType: "text",
		  error:function(){
			alert("设置失败！");
		  }
		});
	}
	
	//产品图片设置为默认图片
	function deleteImageByProductID(imageUrl){
		if(!confirm("确定删除选择的记录?")){
			return ;
		}
		var _url = "product!deleteImageByProductID.action?id="+$("#id").val()+"&imageUrl="+imageUrl;
		$.ajax({
		  type: 'POST',
		  url: _url,
		  data: {},
		  success: function(data){
				  	ajaxLoadImgList();
			  //$("#showMessage").append("删除成功！").fadeTo(2000, 1, function(){
				//   $("#showMessage").html("").hide();
			  //});
			  
		  },
		  dataType: "text",
		  error:function(){
			alert("删除失败！");
		  }
		});
	}

	function addReasonFunc(){
		$('#myModal').modal('toggle');
		return false;
	}
	function checkPrice(){
		var ggkc=0;
		var len=Number("${fn:length(e.specList)}");
		
		for (var i=0;i<len;i++)
		{
			var gk=document.getElementById("ggkc"+i).value;
			if(gk!=''){
			ggkc=ggkc+Number(gk);
			}
		}
		if(ggkc>document.getElementById("stock").value){
			alert("规格库存不能超过总库存!");
			document.getElementById("gg").click();
			return false;
		}
		if('${e.catalogID==vehicle_catalogID}'!="true"){
		if($("#nowPrice").val()<=0){
			alert("商品现价不能为0");
			$("#nowPrice").val("");
			$("#nowPrice").focus();
			return false;
		}else{
		return true;
		}
		}else{
			return true;
		}
	}

	/**
	作者：teng
	时间：2015年10月21日16:25:01
	内容：当输入出产价的时候，自动改变现价和定价
	*/
	function changeSpecPrice2(id,purchasePrice){
		id="sp"+id;
		var lang =  $("#catalogSelect option:selected").attr("lang");
		chuChangprice = isNaN(purchasePrice)?0:purchasePrice;
		biaoJiaoBiLi = getBiaoJiaBiLi(lang);
		var price = purchasePrice*biaoJiaoBiLi;
		price=price.toFixed(2) ;
		document.getElementById(id).value=price;
	}

	
	</script>
</body>
</html>
<c:if test="${message!=null && message!=''}">
    <script language="javascript">
        alert('${message}');
    </script>
</c:if>