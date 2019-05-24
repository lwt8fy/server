$(function(){
	var lradio=$('.information').find('input[type=radio]:checked');
	var lprovince=$(lradio).parent().children('input[name=province]').val();
	var lcity=$(lradio).parent().children('input[name=city]').val();
	var larea=$(lradio).parent().children('input[name=area]').val();
	setLogistics(lprovince,lcity,larea);
	
	$("input[type=radio]").click(function(){
		$("input[type=radio]").parent().removeClass("receipt yesback");
		$("input[type=radio]").parent().addClass("receipt");
		$(this).parent().addClass("receipt yesback");
		$(this).find("input[type=radio]").attr("checked",true);

		$('#shdz').html($(this).siblings('input[name=shdz]').val());
		$('#sjr').html($(this).siblings('input[name=sjr]').val());
		$('#lxdh').html($(this).siblings('input[name=lxdh]').val());
		
		var province=$(this).parent().children('input[name=province]').val();
		var city=$(this).parent().children('input[name=city]').val();
		var area=$(this).parent().children('input[name=area]').val();
		
		setLogistics(province,city,area);
		
	});
});
function setLogistics(province,city,area){
	var _url ="../order/selectLogistics.html";
	
	var productlist=$('tr[name=productlist]');
	for ( var i = 0; i < productlist.size(); i++) {
		var productID=$(productlist[i]).find('td[name=proid]').html();
		var buyCount=$(productlist[i]).find('span[name=buyCount]').html();
		
		$.ajax({
			type: 'POST',
			url: _url,
			data: {"buyCount":buyCount,"productID":productID,"province":province,"city":city,"area":area},
			async:false,
			cache:false,
			success: function(data){
				if(data.length>0){
					$(productlist[i]).next().find('font[name=logisPrice]').html(data[0].price);
					$(productlist[i]).next().find('font[name=logisType]').html(data[0].type);
				}
			},
			dataType: "json",
			error:function(er){
				console.log("er="+er);
			}
		});
	}
	 var m1=$("#productTotalMonery").val();
	 var yfs=$('font[name=logisPrice]');
	 var m2=0;
	 for ( var i = 0; i < yfs.length; i++) {
		m2+=parseFloat(yfs[i].innerHTML);
	}
	var m3=parseFloat(m1)+parseFloat(m2); 
	document.getElementById("totalPayMonery").innerHTML=parseFloat(m3).toFixed(2);
}

function constraintNumber(obj) {
    obj.value = obj.value.replace(/[^0-9]/g, '');
    obj.value = obj.value.replace(/^0*([1-9]|\d(\.?))/, '$1');
    if (!/\d+/.test(obj.value)) {
        obj.value = obj.substr(0, obj.value.length - 1);
    }
}

/*function submitOrder(){
	console.log("提交订单...");
	var submitFlg = true;
	console.log("提交订单...submitFlg= " + submitFlg);
	var m=Number("${userMoney}");
	var money=$('#userMoney').html();
	if(Number($("#Integral").val())>money){
		alert("使用积分不能大于总积分!");
		document.getElementById("Integral").focus();
		return false;
	}
	
	//ajax验证待提交支付的商品库存数量是否存在超卖或下架之类的情况
	//tips
	var aaa=checkStockLastTime();
	console.log("aaaa="+aaa);
	if(!aaa){console.log("not ok");
		return false;
	}
	console.log("ok");
    document.getElementById("thisForm").submit();
   	return true;
}*/
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
function newadr(){
	document.getElementById('zhezhao').style.display='block';
	document.getElementById('newadr').style.display='block';
}
function newadr_close(){
	document.getElementById('zhezhao').style.display='none';
	document.getElementById('newadr').style.display='none';
}
function deletes(){
	return confirm("确定删除选择的记录?");
}
/*function setDefaultAddress(addressid,type){
	var _url = "user/setAddressDefault.html?type="+type+"&id="+addressid;
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  success: function(data){
	  	 alert(data.success);
	  	 window.location.reload();
	  },
	  dataType: "json",
	  error:function(er){
		alert("操作失败，请联系管理员或更换浏览器再试!");				  
	  }
	});
}*/

function changeProvince(){
	$('#qy').html("");
	var selectVal = $("#province").val();
	if(!selectVal){
		console.log("return;");
		return;
	}
	var _url = "user/selectCitysByProvinceCode.html?provinceCode="+selectVal;
	console.log("_url="+_url);
	$("#citySelect").empty().show().append("<option value=''>请选择</option>");
	$("#areaSelect").empty().show().append("<option value=''>请选择</option>");
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  dataType: "json",
	  success: function(data){
		  $.each(data,function(index,value){
			  $("#citySelect").append("<option value='"+value.code+"'>"+value.name+"</option>");
		  });
	  },
	  error:function(er){
		  console.log("changeProvince error!er = "+er);
	  }
	});
}
function changeCity(){
	$('#qy').html("");
	var selectProvinceVal = $("#province").val();
	var selectCityVal = $("#citySelect").val();
	if(!selectProvinceVal || !selectCityVal){
		console.log("return;");
		return;
	}
	var _url = "user/selectAreaListByCityCode.html?provinceCode="+selectProvinceVal+"&cityCode="+selectCityVal;
	console.log("_url="+_url);
	$("#areaSelect").empty().show().append("<option value=''>请选择</option>");
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  dataType: "json",
	  success: function(data){
		  $.each(data,function(index,value){
			  $("#areaSelect").append("<option value='"+value.code+"'>"+value.name+"</option>");
		  });
	  },
	  error:function(er){
		  console.log("changeCity error!er = "+er);
	  }
	});
}
function changeArea(){
	$('#qy').html("");
}/*
function checkForm(){
	if($("#province").val()==""){
		$('#qy').html("请选择省份!");
		$("#province").focus();
		return false;
	}
	if($("#citySelect").val()==""){
		$('#qy').html("请选择城市!");
		$("#citySelect").focus();
		return false;
	}
	if($("#areaSelect").val()==""){
		$('#qy').html("请选择区域!");
		$("#areaSelect").focus();
		return false;
	}
	if($('#defaultck').is(':checked')){
		$('#isdefault').val('y');
	}else{
		$('#isdefault').val('n');
	}
	//newadr_close();
	$("#addressform").submit();
	return true;
}
function editAddress(id){
	var _url="user/editAddress.html?id="+id+"&type=order";
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  success: function(data){
	  	if(data.success=="success"){
	  		newadr();
	  	}
	  },
	  dataType: "json",
	  error:function(er){
		alert("操作失败，请联系管理员或更换浏览器再试!");				  
	  }
	});
}*/