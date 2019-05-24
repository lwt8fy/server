<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
</head>
<%@ include file="/manage/system/common.jsp"%>
<body>
		<table border="1" width="500px" align="center" bordercolor="#000">
			<tr>
				
				<td align="center" nowrap="nowrap"><b>交易量统计</b></td>
				<td style="text-align: left;" colspan="3" nowrap="nowrap">日期范围:
					<input id="d4311" class="Wdate search-query input-small" type="text"	value="" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
					~ 
					<input id="d4312" class="Wdate search-query input-small" type="text"  value=""	onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})" />
						</td>
					<td align="center">
					<button  class="btn btn-primary" onclick="countPay();">
						<i class="icon-search icon-white"></i> 查询
					</button>
					</td>	
			</tr>
			
		</table>
		
		
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/resource/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
function countPay(){
$.post("<%=request.getContextPath()%>/manage/order!countPay",
		{startDate:$("#d4311").val(),endDate:$("#d4312").val()},
       function (data) {
               alert("该日期范围内交易量为:"+data);
         });
}
</script>
</body>
</html>
