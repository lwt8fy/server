var isfree=2;
$(function(){
	$('#isfree input[type=radio]').change(function(){
		isfree=$('#isfree input[type=radio]:checked').val();
		confirm('修改收费方式，需要重新编辑运送方式');
		$('#priceTd input[type=checkbox]').attr('checked',false);
		if(isfree==1){
			$('#price_kd').hide();
			$('#price_wl').hide();
			$('#freeTr').hide();
		}else{
			$('#freeTr').show();
		}
	});
});
function showPrice(v){
	isfree=$('#isfree input[type=radio]:checked').val();
	if(isfree==1){
		return;
	}
	$('#price_'+v).toggle();
}
var areaTrNum=0;
function setAnother(v){
	if($("#price_tab_"+v).is(":hidden")){
		$("#price_tab_"+v).show();
	}else{
		areaTrNum++;
		var newTr=$("#price_tab_"+v+" tr:last").clone();
		newTr.attr("id", "areaTr"+areaTrNum);
		//清空数据
		newTr.find('span').html('');
		newTr.find('input').val('');
		$("#price_tab_"+v+" tr:last").after(newTr);
	}
}
function deleteTr(v,w){
	var trnum=$("#price_tab_"+w+" tr").length;
	if(trnum==2){
		$('#price_tab_'+w).hide();
	}else{
		$(v).parent().parent().remove();
	}
}
function showFree(){
	$('#free').toggle();
}
function deleteFree(v){
	var trnum=$("#price_tab_by tr").length;
	if(trnum==2){
		$('#ckFree').attr('checked',false);
		$('#free').hide();
	}else{
		$(v).parent().parent().remove();
	}
}
/////////////////////
function toList(){
	window.location.href="../manage/logistics!selectList.action";
}
function toInsertLog(){
	var name=$('#name').val();
	if(name==""){
		confirm('模版名称不能为空!');
		$('#name').focus();
		return;
	}
	if(!checkPriceNull()){
		return;
	}
	if(!checkFreeNull()){
		return;
	}
	var deliverytime=$('#deliverytime').val();
	var isfree=$('#isfree').find('input:checked').val();
	var type=$('#type').find('input:checked').val();
	var _url = "../manage/logistics!insert.action";
	$.post(_url,{"name":name,"deliverytime":deliverytime,"isfree":isfree,"type":type},function(data){
	 	if(data.type=="success"){
	 		var inPrice=toInsertLogPrice(data.logid);
 			var inFree=toInsertLogFree(data.logid);
 			if(inFree&&inPrice){
	 			toList();
	 		}else{
		 		alert('网络繁忙，请稍候再试!');
		 	}
	 	}else{
	 		alert('网络繁忙，请稍候再试!');
	 	}
	},'json');
}
function toInsertLogPrice(logid){
	var bool=false;
	var p_url = "../manage/logistics_price!insert.action";
	var isfree=$('#isfree').find('input:checked').val();
	if(isfree==1){//卖家承担运费
		var types=$('#priceTd').find('input:checked');
		var result=0;
		for ( var i = 0; i < types.length; i++) {
			var type=$(types[i]).val();
			$.ajax({
		        dataType:'json',
		        type: 'POST',
		        data: {"logid":logid,"type":type},
		        url : p_url,
		        async:false,
		        success : function(data){
		        	if(data.type=="success"){
				 		result++;
				 	}else{
				 		confirm('网络繁忙，请稍候再试!');
				 	}
				},error:function(e){
					confirm('网络繁忙，请稍候再试!');
					bool=false;
				}
			});
		}
		if(result=types.length){
			bool=true;
		}else{	
			bool=false;
		}
		return bool;
	}else if(isfree==2){//自定义运费
		var kd=$('input[name=kd]').is(':checked');
		var wl=$('input[name=wl]').is(':checked');
		if(kd){
			var isdefault=$('#default_kd').children('input[name=isdefault]').val();
			var firstnum=$('#default_kd').children('input[name=firstnum]').val();
			var firstprice=$('#default_kd').children('input[name=firstprice]').val();
			var extendnum=$('#default_kd').children('input[name=extendnum]').val();
			var extendprice=$('#default_kd').children('input[name=extendprice]').val();
			
			var data={"logid":logid,"type":1,"isdefault":isdefault,"firstnum":firstnum,"firstprice":firstprice,"extendnum":extendnum,"extendprice":extendprice};
			$.ajax({
		        dataType:'json',
		        type: 'POST',
		        data:data,
		        url : p_url,
		        async:false,
		        success : function(data){
					if(data.type=="success"){
				 		bool=true;
				 	}else{
				 		confirm('网络繁忙，请稍候再试!');
				 		bool=false;
				 	}
				},error:function(e){
					confirm('网络繁忙，请稍候再试!');
					bool=false;
				}
			});
			var tab=$("#price_tab_kd").is(":hidden");
			if(!tab){
				var trs=$('#price_tab_kd tr:eq(0)').nextAll();
				var result=0;
				for ( var i = 0; i < trs.length; i++) {
					var areas=$(trs[i]).find('span[name=areas]').html();
					var areasid=$(trs[i]).find('span[name=areasids]').html();
					var isd=$(trs[i]).find('td[name=isdefault]').html();
					var fn=$(trs[i]).find('input[name=firstnum]').val();
					var fp=$(trs[i]).find('input[name=firstprice]').val();
					var en=$(trs[i]).find('input[name=extendnum]').val();
					var ep=$(trs[i]).find('input[name=extendprice]').val();
					
					var data={"logid":logid,"type":1,"areas":areas,"areasid":areasid,"isdefault":isd,"firstnum":fn,"firstprice":fp,"extendnum":en,"extendprice":ep};
					$.ajax({
				        dataType:'json',
				        type: 'POST',
				        data:data,
				        url : p_url,
				        async:false,
				        success : function(data){
							if(data.type=="success"){
						 		result++;
						 	}else{
						 		confirm('网络繁忙，请稍候再试!');
						 		bool=false;
						 	}
						},error:function(e){
							confirm('网络繁忙，请稍候再试!');
							bool=false;
						}
					});
				}
				if(result=trs.length){
					bool=true;
				}else{	
					bool=false;
				}
			}
		}
		if(wl){
			var isdefault=$('#default_kd').children('input[name=isdefault]').val();
			var firstnum=$('#default_wl').children('input[name=firstnum]').val();
			var firstprice=$('#default_wl').children('input[name=firstprice]').val();
			var extendnum=$('#default_wl').children('input[name=extendnum]').val();
			var extendprice=$('#default_wl').children('input[name=extendprice]').val();
			
			var data={"logid":logid,"type":2,"isdefault":isdefault,"firstnum":firstnum,"firstprice":firstprice,"extendnum":extendnum,"extendprice":extendprice};
			$.ajax({
		        dataType:'json',
		        type: 'POST',
		        data:data,
		        url : p_url,
		        async:false,
		        success : function(data){
					if(data.type=="success"){
				 		bool=true;
				 	}else{
				 		confirm('网络繁忙，请稍候再试!');
				 		bool=false;
				 	}
				},error:function(e){
					confirm('网络繁忙，请稍候再试!');
					bool=false;
				}
			});
			
			var tab=$("#price_tab_wl").is(":hidden");
			if(!tab){
				var trs=$('#price_tab_wl tr:eq(0)').nextAll();
				var result=0;
				for ( var i = 0; i < trs.length; i++) {
					var areas=$(trs[i]).find('span[name=areas]').html();
					var areasid=$(trs[i]).find('span[name=areasids]').html();
					var isd=$(trs[i]).find('td[name=isdefault]').html();
					var fn=$(trs[i]).find('input[name=firstnum]').val();
					var fp=$(trs[i]).find('input[name=firstprice]').val();
					var en=$(trs[i]).find('input[name=extendnum]').val();
					var ep=$(trs[i]).find('input[name=extendprice]').val();
					
					var data={"logid":logid,"type":2,"areas":areas,"areasid":areasid,"isdefault":isd,"firstnum":fn,"firstprice":fp,"extendnum":en,"extendprice":ep};
					$.ajax({
				        dataType:'json',
				        type: 'POST',
				        data:data,
				        url : p_url,
				        async:false,
				        success : function(data){
							if(data.type=="success"){
						 		result++;
						 	}else{
						 		confirm('网络繁忙，请稍候再试!');
						 		bool=false;
						 	}
						},error:function(e){
							confirm('网络繁忙，请稍候再试!');
							bool=false;
						}
					});
				}
				if(result=trs.length){
					bool=true;
				}else{	
					bool=false;
				}
			}
		}
		return bool;
	}
}
function toInsertLogFree(logid){
	var bool=false;
	var isfree=$('#isfree').find('input:checked').val();
	var ckFree=$('#ckFree').is(':checked');
	if(isfree==2&&ckFree==true){
		var result=0;
		var trs=$('#price_tab_by tr:eq(0)').nextAll();
		for ( var i = 0; i < trs.length; i++) {
			var areas=$(trs[i]).find('span[name=areas]').html();
			var areasid=$(trs[i]).find('span[name=areasids]').html();
			var type=$(trs[i]).find('select[name=type]').val();
			var conditiontype=$(trs[i]).find('td[name=conditiontype]').html();
			var conditions=$(trs[i]).find('input[name=conditions]').val();
			var _url = "../manage/logistics_free!insert.action";
			var data={"logid":logid,"areas":areas,"areasid":areasid,"type":type,"conditiontype":conditiontype,"conditions":conditions};
			$.ajax({
		        dataType:'json',
		        type: 'POST',
		        data: data,
		        url : _url,
		        async:false,
		        success : function(data){
		        	if(data.type=="success"){
			 			result++;
				 	}else{
				 		confirm('网络繁忙，请稍候再试!');
				 	}
				},error:function(e){
					confirm('网络繁忙，请稍候再试!');
					bool=false;
				}
			});
		}
		if(result=trs.length){
			bool=true;
		}else{	
			bool=false;
		}
		return bool;
	}else{
		return true;
	}
}

function checkPriceNull(){
	var isfree=$('#isfree').find('input:checked').val();
	var types=$('#priceTd').find('input:checked');
	if(types.length==0){
		alert('请选择运送方式!');
		return;
	}
	if(isfree==2){//自定义运费
		var kd=$('input[name=kd]').is(':checked');
		var wl=$('input[name=wl]').is(':checked');
		if(kd){
			var inputs=$('#default_kd input');
			for ( var i = 0; i < inputs.length; i++) {
				if($(inputs[i]).val()==""){
					alert('请填写完整运费标准!');
					$(inputs[i]).focus();
					return;
				}
			}
			var tab=$("#price_tab_kd").is(":hidden");
			if(!tab){
				var trinputs=$("#price_tab_kd input");
				var trspans=$("#price_tab_kd").find('span[name=areasids]');
				for ( var i = 0; i < trspans.length; i++) {
					if($(trspans[i]).html()==""){
						alert('请填写完整运送区域!');
						$(trspans[i]).focus();
						return;
					}
				}
				for ( var i = 0; i < trinputs.length; i++) {
					if($(trinputs[i]).val()==""){
						alert('请填写完整运费标准!');
						$(trinputs[i]).focus();
						return;
					}
				}
			}
		}
		if(wl){
			var inputs=$('#default_wl input');
			for ( var i = 0; i < inputs.length; i++) {
				if($(inputs[i]).val()==""){
					alert('请填写完整运费标准!');
					$(inputs[i]).focus();
					return;
				}
			}
			var tab=$("#price_tab_wl").is(":hidden");
			if(!tab){
				var trinputs=$("#price_tab_wl input");
				var trspans=$("#price_tab_wl").find('span[name=areasids]');
				for ( var i = 0; i < trspans.length; i++) {
					if($(trspans[i]).html()==""){
						alert('请填写完整运送区域!');
						$(trspans[i]).focus();
						return;
					}
				}
				for ( var i = 0; i < trinputs.length; i++) {
					if($(trinputs[i]).val()==""){
						alert('请填写完整运费标准!');
						$(trinputs[i]).focus();
						return;
					}
				}
			}
		}
	}
	return true;
}
function checkFreeNull(){
	var isfree=$('#isfree').find('input:checked').val();
	var ckFree=$('#ckFree').is(':checked');
	var inputs=$('#price_tab_by').find('input[name=conditions]');
	var spans=$('#price_tab_by').find('span[name=areas]');
	if(isfree==2&&ckFree==true){
		for ( var i = 0; i < spans.length; i++) {
			if($(spans[i]).html()==''){
				confirm('请选择包邮地区!');
				return;
			}
		}
		for ( var i = 0; i < inputs.length; i++) {
			if($(inputs[i]).val()==''){
				confirm('请设置包邮条件!');
				$(inputs[i]).focus();
				return;
			}
		}
		return true;
	}else{
		return true;
	}
}

function toUpdateLog(){
	var name=$('#name').val();
	if(name==""){
		confirm('模版名称不能为空!');
		$('#name').focus();
		return;
	}
	if(!checkPriceNull()){
		return;
	}
	if(!checkFreeNull()){
		return;
	}
	var id=$('#idd').val();
	var deliverytime=$('#deliverytime').val();
	var isfree=$('#isfree').find('input:checked').val();
	var type=$('#type').find('input:checked').val();
	var _url = "../manage/logistics!update.action";
	$.post(_url,{"id":id,"name":name,"deliverytime":deliverytime,"isfree":isfree,"type":type},function(data){
	 	if(data.type=="success"){
	 		var inFree;
	 		var inPrice;
	 		
	 		var delPrice=deletePriceList(id);
	 		if(delPrice){
	 			inPrice=toInsertLogPrice(id);
	 		}
	 		
	 		var delFree=deleteFreeList(id);
	 		if(delFree){
	 			inFree=toInsertLogFree(id);
	 		}
 			if(inFree&&inPrice){
	 			toList();
	 		}else{
		 		alert('网络繁忙，请稍候再试!');
		 	}
	 	}else{
	 		alert('网络繁忙，请稍候再试!');
	 	}
	},'json');
}

function deleteFreeList(logid){
	var bool=false;
	var _url =  "../manage/logistics_free!deleteByLogId.action";
	$.ajax({
        dataType:'json',
        type: 'POST',
        data: {"logid":logid},
        url : _url,
        async:false,
        success : function(data){
        	bool=true;
		},error:function(e){
			alert('网络繁忙，请稍候再试!');
			bool=false;
		}
	});
	return bool;
}
function deletePriceList(logid){
	var bool=false;
	var _url =  "../manage/logistics_price!deleteByLogId.action";
	$.ajax({
		dataType:'json',
		type: 'POST',
		data: {"logid":logid},
		url : _url,
		async:false,
		success : function(data){
			bool=true;
		},error:function(e){
			alert('网络繁忙，请稍候再试!');
			bool=false;
		}
	});
	return bool;
}