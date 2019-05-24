$(function(){
	var specJsonStringVal = $("#specJsonString").val();
	
	//如果规格存在
	if(specJsonStringVal && specJsonStringVal.length>0){
		console.log("specJsonStringVal = " + specJsonStringVal);
		var specJsonStringObject = eval('('+specJsonStringVal+')');
		
		for(var i=0;i<specJsonStringObject.length;i++){
			console.log("specJsonStringObject = " + specJsonStringObject[i].specColor);
		}

		//规格被点击，则标记选中和不选中
		$("#specDiv li").click(function(){
			console.log("规格被点击。" + $(this).hasClass("specSelectCss"));
			if($(this).hasClass("specNotAllowed")){
				console.log("由于规格被禁用了，直接返回。");
				return;
			}
			
			$(this).parent().find("li").not(this).each(function(){
				$(this).removeClass("specSelectCss");
				
			});
			if($(this).is(".specSelectCss")){
				console.log("removeClass specSelectCss");
				$(this).removeClass("specSelectCss");
				
				//如果当前点击的是尺寸，则释放所有的颜色的禁用状态；如果点击的是颜色，则释放所有的尺寸禁用状态
				if($(this).parent().attr("id")=="specSize"){
					console.log("当前点击的是尺寸。");
					//释放所有颜色的鼠标禁用状态
					$("#specColor li").each(function(){
						$(this).removeClass("specNotAllowed");
					});
				}else if($(this).parent().attr("id")=="specColor"){
					console.log("当前点击的是颜色。");
					//释放所有颜色的鼠标禁用状态
					$("#specSize li").each(function(){
						$(this).removeClass("specNotAllowed");
					});
				}else{
					console.log("当前点击的东东不明确。");
				}
			}else{
				console.log("addClass specSelectCss");
				$(this).addClass("specSelectCss");
			}
			
			//$("#specSize")
			
			var parentID = $(this).parent().attr("id");
			console.log("parentID = " + parentID);
			
			if($("#specSize li").hasClass("specSelectCss") ){
				console.log("都选中了。");
				console.log("选中的文本："+$("#specSize .specSelectCss").html());
				//找出对应的规格
				for(var i=0;i<specJsonStringObject.length;i++){
					var specItem = specJsonStringObject[i];
					if(specItem.specSize==$("#specSize .specSelectCss").html()){
						console.log("找到了规格对象。");
						//改变商品的价格和库存数
						$("#nowPrice").html(specItem.specPrice);
						$("#stock_span_id").html(specItem.specStock);
						$("#specIdHidden").val(specItem.id);
						console.log("选中的规格ID="+$("#specIdHidden").val());
						break;
					}
				}
				//specNotAllowed
			}else if($("#specSize li").hasClass("specSelectCss")){
				resetProductInfo();
				//尺寸被选中了一个，则将于该尺寸不匹配的颜色禁用掉。
				console.log("尺寸被选中了一个，则将于该尺寸不匹配的颜色禁用掉。");
				//找出对应的规格
				var colorArr = [];//与选中的规格相匹配的颜色集合
				for(var i=0;i<specJsonStringObject.length;i++){
					var specItem = specJsonStringObject[i];
					if(specItem.specSize==$("#specSize .specSelectCss").html()){
						colorArr.push(specItem.specColor);
					}
				}
				
				//释放所有颜色的鼠标禁用状态
				$("#specColor li").each(function(){
					$(this).removeClass("specNotAllowed");
				});
				
				//找出于选择的尺寸不匹配的颜色，将其禁用掉。
				for(var i=0;i<specJsonStringObject.length;i++){
					var specItem = specJsonStringObject[i];
					var hanFind = false;
					for(var j=0;j<colorArr.length;j++){
						if(specItem.specColor==colorArr[j]){
							hanFind = true;
							break;
						}
					}
					
					if(!hanFind){
						console.log("禁掉的颜色有："+specItem.specColor);
						
						$("#specColor li").each(function(){
							console.log("text="+$(this).text());
							if($(this).text()==specItem.specColor){
								console.log("找到了。");
								$(this).addClass("specNotAllowed");
								return false;
							}
						});
					}
				}
				
			}else if($("#specColor li").hasClass("specSelectCss")){
				resetProductInfo();
				//颜色被选中了一个，则将于该颜色不匹配的尺寸禁用掉。
				console.log("颜色被选中了一个，则将于该颜色不匹配的尺寸禁用掉。");
				
				//找出对应的规格
				var sizeArr = [];//与选中的规格相匹配的颜色集合
				for(var i=0;i<specJsonStringObject.length;i++){
					var specItem = specJsonStringObject[i];
					if(specItem.specColor==$("#specColor .specSelectCss").html()){
						sizeArr.push(specItem.specSize);
					}
				}
				
				//释放所有颜色的鼠标禁用状态
				$("#specSize li").each(function(){
					$(this).removeClass("specNotAllowed");
				});
				
				//找出于选择的尺寸不匹配的颜色，将其禁用掉。
				for(var i=0;i<specJsonStringObject.length;i++){
					var specItem = specJsonStringObject[i];
					var hanFind = false;
					for(var j=0;j<sizeArr.length;j++){
						if(specItem.specSize==sizeArr[j]){
							hanFind = true;
							break;
						}
					}
					
					if(!hanFind){
						console.log("禁掉的尺寸有："+specItem.specSize);
						
						$("#specSize li").each(function(){
							console.log("text="+$(this).text());
							if($(this).text()==specItem.specSize){
								console.log("找到了。");
								$(this).addClass("specNotAllowed");
								return false;
							}
						});
					}
				}
				
			}else{
				console.log("都没选中。");
				resetProductInfo();
			}
			
		});
	}
	//键盘按下的时候对字符进行检查，只能是数字
	$("input[name=inputBuyNum]").keyup(function(event) {
		var key = event.keyCode ? event.keyCode : event.which;
		if ((key >= 48 && key <= 57) || key==8||(key >= 96 && key <= 105)) {
			var _obj = $(this);
			console.log(">>>_obj.val()="+_obj.val());
			//库存字符检查
			if($.trim(_obj.val())=='' || parseInt(_obj.val())<=0){
				_obj.val("1");
			}
			checkStockFunc();
			return true;
		} else {
			return false;
		}
	});
	
	
	//键盘抬起来的时候对库存进行检查
	$("input[name=inputBuyNum]").keyup(function(event) {
		var key = event.keyCode ? event.keyCode : event.which;
		if ((key >= 48 && key <= 57) || key==8) {
			var _obj = $(this);
			if($.trim(_obj.val())=='' || parseInt(_obj.val())<=0){
				_obj.val("1");
			}
			checkStockFunc();
			console.log("val="+_obj.val());
			var _pid = _obj.attr("pid");
			console.log(_obj.val()+",_pid="+_pid);
			if(_pid){
				notifyCart(_obj);
			}
			return true;
		} else {
			return false;
		}
	});
});

//判断是否是正整数
function IsNum(s)
{
    if(s!=null){
        var r,re;
        re = /\d*/i; //\d表示数字,*表示匹配多个数字
        r = s.match(re);
        return (r==s)?true:false;
    }
    return false;
}
//通知购物车
function notifyCart(_obj){
	//var _url = "cart/notifyCart.html?currentBuyNumber="+_obj.val()+"&productID="+_obj.attr("pid")+"&date="+(new Date().getTime());
	var _url = "cart/notifyCart.html?currentBuyNumber="+_obj.val()+"&productID="+_obj.attr("pid")+"&radom="+Math.random();
	console.log("_url="+_url);
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  cache:false,
	  success: function(data){
		  console.log("notifyCart.data="+data);
		  //console.log("notifyCart.data="+data+",data.amount="+data.amount+",data.code="+data.code);
		  //console.log("notifyCart.data="+data+",data.amount="+data["amount"]+",data.code="+data["code"]);
		  if(data.code=='notThisProduct'){
			  console.log("notifyCart.data.code=notThisProduct");
			  _obj.parent().find("a[name=stockErrorTips]").attr("data-original-title",data.msg).tooltip('show');
		  }else  if(data.code=='buyMore'){
			  console.log("notifyCart.data.code=buyMore");
			  _obj.parent().find("a[name=stockErrorTips]").attr("data-original-title",data.msg).tooltip('show');
		  }else  if(data.code=='ok'){
			  console.log("notifyCart.data.code=ok");
			  var _tips_obj = _obj.parent().find("a[name=stockErrorTips]");
			  _tips_obj.tooltip('hide');
			  _tips_obj.attr("data-original-title",'');
			  $("#totalPayMonery").text(data.amount);
			  $("#totalExchangeScore").text(data.amountExchangeScore);
			  
			  //console.log("_obj.parent().parent().html()="+_obj.parent().parent().html());
			  //console.log("_obj.parent().html()="+_obj.parent().html());
			  _obj.parent().parent().find("td[total0=total0]").text(data.total0);
		  }
	  },
	  dataType: "json",
	  error:function(er){
		  console.log("notifyCart.er="+er);
		  //$.each(er,function(index,value){
			//  console.log("index="+index+",value="+value);
		  //});
	  }
	});
}
//重置商品信息
function resetProductInfo(){
	console.log("resetProductInfo..."+$("#nowPriceHidden").val());
	//设置值为商品原价格
	$("#nowPrice").text($("#nowPriceHidden").val());
	$("#stock_span_id").text($("#stockHidden").val());
	$("#specIdHidden").val("");
}

//检查库存是否超出数量
function checkStockFunc(){
	console.log($("#stock_span_id").text()+","+$("#inputBuyNum").val());
	if(parseInt($("#inputBuyNum").val())>parseInt($("#stock_span_id").text())){
		$("#exceedDivError").show();
		$("#exceedSpanError").attr("data-original-title","您所填写的商品数量超过库存！").tooltip('show');
		
		console.log("购买的商品数量大于库存数！");
		return false;
	}
	if(parseInt($("#inputBuyNum").val())>parseInt($("#maxSellCount").val())){
		$("#exceedDivError").show();
		$("#exceedSpanError").attr("data-original-title","本活动您还能购买的最大数量为:"+$("#maxSellCount").val()).tooltip('show');
		return false;
	}
	$("#exceedSpanError").html("");
	$("#exceedDivError").hide();
	return true;
}
function setTab(name,cursel,n){
	for(i=1;i<=n;i++){
	var menu=document.getElementById(name+i);
	var con=document.getElementById("con_"+name+"_"+i);
	menu.className=i==cursel?"hover":"";
	con.style.display=i==cursel?"block":"none";
	}
 } 
//添加商品收藏
function addToFavorite(){
	var _url = "addToFavorite.html?productID="+$("#productID").val()+"&radom="+Math.random();
	console.log("_url="+_url);
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  success: function(data){
		  console.log("addToFavorite.data="+data);
		  var _result = "商品已成功添加到收藏夹！";
		  if(data=="0"){
			  _result = "商品已成功添加到收藏夹！";
		  }else if(data=='1'){
			  _result = "已添加，无需重复添加！";
		  }else if(data=='-1'){//提示用户要先登陆
			  _result = "使用此功能需要先登陆！";
		  }
		  
		  $('#addToFavoriteBtn').attr("data-original-title",_result).tooltip('show');
	  },
	  dataType: "text",
	  error:function(er){
		  console.log("addToFavorite.er="+er);
	  }
	});
}
//添加商铺收藏
function addToFavoriteShop(){
	var _url = 'addToFavoriteShop.html?companyID='+$("#compID").val()+'&radom='+Math.random();
	console.log("_url="+_url);
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  success: function(data){
		  console.log("addToFavoriteShop.data="+data);
		  var _result = "店铺已成功添加到收藏夹！";
		  if(data=="0"){
			  _result = "店铺已成功添加到收藏夹！";
		  }else if(data=='1'){
			  _result = "已添加，无需重复添加！";
		  }else if(data=='-1'){//提示用户要先登陆
			  _result = "使用此功能需要先登陆！";
		  }
		  
		  $('#addToFavoriteShopBtn').attr("data-original-title",_result).tooltip('show');
	  },
	  dataType: "text",
	  error:function(er){
		  console.log("addToFavoriteShop.er="+er);
	  }
	});
}
//加入购物车
function addToCart(){
	if(u=="true"){
		openme();
		return false;
	}
	if(up=="true"){
		$('#myModal2').modal('toggle');
		return false;
	}
	var _specIdHidden = $("#specIdHidden").val();
	var specJsonStringVal = $("#specJsonString").val();
	//如果规格存在
	console.log("specIdHidden = " + _specIdHidden);
	if(specJsonStringVal && specJsonStringVal.length>0){
		if(!_specIdHidden || _specIdHidden==''){
			$("#addToCartBtn").attr("data-original-title","请选择商品规格！").tooltip('show');
			return;
		}
	}
	
	if(!checkStockFunc()){
		return false;
	}
	var _url = "cart!addToCart.action?productID="+$("#productID").val()+"&buyCount="+$("#inputBuyNum").val()+"&buySpecID="+$("#specIdHidden").val();
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  success: function(data){
		  console.log("data="+data);
		if(data==0){
			$("#addToCartBtn").attr("data-original-title",data.tips).tooltip('destroy');
			var nowprice=$("#nowPrice").html();
			var nownum=$("#inputBuyNum").val();
			var now_amount=Number(nowprice)*Number(nownum);
			
			var l_count=$('#ts_count').html();
			var l_amount=$('#ts_amount').html();
			
			$('#ts_count').html(Number(1)+Number(l_count));
			$('#cartNum').html(Number(1)+Number(l_count));
			$('#ts_amount').html(Number(now_amount)+Number(l_amount));
			shop();
		}else{
			console.log("出现错误。data.tips="+data.tips);
			
			$("#addToCartBtn").attr("data-original-title",data.tips).tooltip('show');
		}
	  },
	  dataType: "json",
	  error:function(e){
		  console.log("加入购物车失败！请联系站点管理员。异常:"+e);
		  $("#addToCartBtn").attr("data-original-title","加入购物车失败！请联系客服寻求解决办法。").tooltip('show');
	  }
	});
}
function buy2(){
	if(u=="true"){
		openme();
		return false;
	}
	if(up=="true"){
		$('#myModal2').modal('toggle');
		return false;
	}
	
	var _specIdHidden = $("#specIdHidden").val();
	var specJsonStringVal = $("#specJsonString").val();
	//如果规格存在
	console.log("specIdHidden = " + _specIdHidden);
	if(specJsonStringVal && specJsonStringVal.length>0){
		if(!_specIdHidden || _specIdHidden==''){
			$("#buyNow").attr("data-original-title","请选择商品规格！").tooltip('show');
			return;
		}
	}
	
	if(!checkStockFunc()){
		return false;
	}
	$("#buyForm").submit();
}
//增加购买商品数
function addFunc(obj,notifyCartFlg){
	var _obj = $(obj).parent().find("input[name=inputBuyNum]");
	var stock_span=$('#stock_span').html();
	
	if(stock_span==0){
		return;
	}
	var quantity = _obj.val();
	if (/^\d*[1-9]\d*$/.test(quantity)) {
		_obj.val(parseInt(quantity) + 1);
	} else {
		_obj.val(1);
	}
	if(notifyCartFlg){
		notifyCart(_obj);
	}
	checkStockFunc();
}
//减少购买商品数
function subFunc(obj,notifyCartFlg){
	var _obj = $(obj).parent().find("input[name=inputBuyNum]");
	var stock_span=$('#stock_span').html();
	if(stock_span==0){
		return;
	}
	var quantity = _obj.val();
	if (/^\d*[1-9]\d*$/.test(quantity)) {
		if(quantity>1){
			_obj.val(parseInt(quantity) - 1);
		}else{
			_obj.val(1);
		}
	} else {
		_obj.val(1);
	}
	if(notifyCartFlg){
		notifyCart(_obj);
	}
	checkStockFunc();
}
function shop(){
	document.getElementById('bg').style.display='block';
	document.getElementById('shop_box').style.display='block';
	}
function shop_close(){
	document.getElementById('bg').style.display='none';
	document.getElementById('shop_box').style.display='none';
}
//最后一次检查库存
function checkStockLastTime(){
	var _url = "cart/checkStockLastTime.html?radom="+Math.random();
	console.log("_url="+_url);
	var result;
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  async:false,
	  cache:false,
	  success: function(data){
		  console.log("notifyCart.data="+data);
		  
		  if(data=="-1"){
			  console.log("提示用户需要登录！");
			  $("#confirmOrderBtn").attr("data-original-title","需要先登陆，才能提交订单！").tooltip('show');
			  result = false;
			  
		  }else if(data.code=='login'){
			  console.log("notifyCart.data.code=login");
		  }else  if(data.code=='result'){
			  if(!data.list && !data.error){
				 console.log("true");
				 result = true;
			  }else{
				  $.each(data.list,function(index,value){
					  console.log("each>>"+index+","+value);
					  $("a[name=stockErrorTips]").each(function(){
						  console.log("each2>>"+value.id);
						  if($(this).attr("productid")==value.id){
							  $(this).attr("data-original-title",value.tips).tooltip('show');
						  }
					  });
				  });
				  console.log("false");
				  data.error = "按钮错误！";
				  $("#confirmOrderBtn").attr("data-original-title",data.error).tooltip('show');
				  result = false;
			  }
		  }
	  },
	  dataType: "json",
	  error:function(er){
		  console.log("notifyCart.er="+er);
		  result = false;
	  }
	});
	return result;
}
