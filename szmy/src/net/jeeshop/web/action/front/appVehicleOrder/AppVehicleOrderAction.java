package net.jeeshop.web.action.front.appVehicleOrder;

import java.util.Date;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.AppVehicle;
import net.jeeshop.services.common.AppVehicleOrder;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.front.appVehicle.service.AppVehicleService;
import net.jeeshop.services.front.appVehicleOrder.service.AppVehicleOrderService;
import net.jeeshop.services.front.orderlog.OrderlogService;
import net.jeeshop.services.front.orderlog.bean.Orderlog;
import net.jeeshop.services.front.orderpay.OrderpayService;
import net.jeeshop.services.front.orderpay.bean.Orderpay;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.abc.trustpay.client.Base64;
import com.abc.trustpay.client.Constants;
import com.abc.trustpay.client.JSON;
import com.abc.trustpay.client.MerchantConfig;
import com.abc.trustpay.client.TrxResponse;
import com.abc.trustpay.client.XMLDocument;
import com.abc.trustpay.client.ebus.PaymentRequest;

public class AppVehicleOrderAction extends BaseAction<AppVehicleOrder>{
	private static final long serialVersionUID = -5098200715815258540L;
	private final String bkSel="nybank";
	public static final String wl="WL";
	public static final String wldj="WLDJ";
	public static final String dj="dj";
	public static final String fk="fk";
	Logger logger = Logger.getLogger(AppVehicleOrderAction.class);
	private String uuid;
	private String json;
    private  AppUserService appUserService;
    private AppVehicleService appVehicleService;
	private OrderlogService orderlogService; 
	private  OrderpayService orderpayService;
	private  AppVehicleOrderService appVehicleOrderService;
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";
	
	
	@Override
	public AppVehicleOrder getE() {
		// TODO Auto-generated method stub
		return this.e;
	}

	@Override
	public void insertAfter(AppVehicleOrder e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
			this.e = new AppVehicleOrder();
		}
		
	}

	@Override
	protected void selectListAfter() {
		// TODO Auto-generated method stub
		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	@Override
	public String insert(){
		try {
			if (uuid == null || uuid.equals("")) {
				json = ERROR_MSG;
			} else {
				AppUser u = appUserService.selectByUuid(uuid);
				if (u != null) {
					String wId = this.getRequest().getParameter("wId");
					if(StringUtils.isNotBlank(wId)){
						AppVehicle appVehicle = new AppVehicle();
						appVehicle.setId(wId);
//						appVehicle.setStatus(AppVehicle.xd);
						this.server.insert(e);
						json = JSONObject.fromObject(e).toString();
						return "json";
					}
				} 
			}
			json = ERROR_MSG;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Exception_MSG;
		}
		return "json";
	}
	
	@Override
	public String update(){
		try {
			if (uuid == null || uuid.equals("")) {
				json = ERROR_MSG;
			} else {
				AppUser u = appUserService.selectByUuid(uuid);
				if (u != null) {
					this.server.update(e);
					json = JSONObject.fromObject(e).toString();
					return "json";
				} 
			}
			json = ERROR_MSG;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Exception_MSG;
		}
		return "json";
	}
	
	public String delete(){
		try{
		String id = this.getRequest().getParameter("id");
		if(uuid==null||uuid.equals("")||StringUtils.isBlank(id)){
			json = ERROR_MSG;
		}else{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u==null){
				json = ERROR_MSG;
			}else{
				if (u != null ){
					AppVehicle appVehicle = this.appVehicleService.selectById(id);
					if(appVehicle!=null){
						appVehicle.setStatus("1");
						this.appVehicleService.update(appVehicle);
						e.setVehicleId(id);
						server.delete(e);
						json=SUCCESS_MSG;
						return "json";
					}
				}
			}
		}
		json = ERROR_MSG;
		}catch (Exception e) {
			json = Exception_MSG;
		}
		return "json";
	}
	@Override
	public String selectList() throws Exception {
		int os=pager.getOffset();
		pager.setOffset((os - 1) * e.getPageSize() );
		try{
			AppUser u = appUserService.selectByUuid(uuid);
			if(uuid!=null&&!uuid.equals("")&&u!=null&&u.getUsername().equals(e.getPurchaserName())){
				super.selectList();
				pager.setOffset(os);
				JSONObject jsonObject = JSONObject.fromObject(pager);
				jsonObject.put("success", "1");
				json=jsonObject.toString();
				return "json";
			}
			  json = ERROR_MSG;
		}catch (Exception e) {
			json = Exception_MSG;
		}
		return "json";
	}
	
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2015-11-29上午08:23:29
	 * 描述:  type    交易类型  dj：定金  fk：最终成交
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getString(){
		String productId = this.getRequest().getParameter("id");
		String jiaoyie = this.getRequest().getParameter("jiaoyie");
		String type =  this.getRequest().getParameter("type");
		String test =  this.getRequest().getParameter("test");
		if(StringUtils.isNotBlank(uuid)&&StringUtils.isNotBlank(productId)&&StringUtils.isNotBlank(type)){
			String before = "";
			if(dj.equals(type)){
				before=wldj;
				jiaoyie = KeyValueHelper.get("WLDJ").toString();
			}else if(fk.equals(type)){
				if(!NumberUtils.isNumber(jiaoyie)){
					json = ERROR_MSG;
					return "json";
				}
				before=wl;
			}
			AppUser u = appUserService.selectByUuid(uuid);
			if(u!=null){
				AppVehicle product = this.appVehicleService.selectById(productId);
				if(product!=null){
					//创建订单对象
					AppVehicleOrder avo = new AppVehicleOrder();
					avo.setVehicleId(productId);
					server.delete(avo);//删除已经以前已经下过此单的记录
					e.setVehicleId(productId);
					e.setPrice(Double.parseDouble(jiaoyie));
					e.setPurchaserName(u.getUsername());
					e.setPurchaserID(u.getId());
					e.setPurchaserPhone(u.getPhone());
					e.setOrderStatus(AppVehicleOrder.fb);
					e.setType(type);
					if(fk.equals(type)){
						e.setFinalPrice(Double.parseDouble(jiaoyie));
					}
					server.insert(e);
					// 记录订单创建日志
					Orderlog orderlog = new Orderlog();
					orderlog.setOrderid(before+e.getId());
					orderlog.setAccount(u.getUsername());
					orderlog.setContent("【创建订单】用户创建订单。订单总金额：" + e.getPrice());
					orderlog.setAccountType(net.jeeshop.services.common.Orderlog.orderlog_accountType_w);
					orderlogService.insert(orderlog);
					
					//创建支付记录对象
					Orderpay orderpay = new Orderpay();
					orderpay.setOrderid(before+e.getId());
					orderpay.setPaystatus(net.jeeshop.services.common.Orderpay.orderpay_paystatus_n);
					orderpay.setPayamount(Double.valueOf(e.getPrice()));
					orderpay.setPaymethod(bkSel);
					orderpayService.insert(orderpay);
					
					if(StringUtils.isNotBlank(test)){
						String Url = SystemManager.systemSetting.getWww()+"/appVehicleOrderActionFront!nyRec?OrderNo="+e.getId()+"&Amount="+e.getPrice()+"&type="+type;
						JSONObject js=new JSONObject();
						js.put("success", "1");
						js.put("url",  Url);
						json=js.toString();
						return "json";
					}
					
					//1、生成订单对象
					String ds = DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
					PaymentRequest tPaymentRequest = new PaymentRequest();
					tPaymentRequest.dicOrder.put("PayTypeID","ImmediatePay");// request.getParameter("PayTypeID"));                   //设定交易类型
					tPaymentRequest.dicOrder.put("OrderDate", ds.substring(0, 10).replace("-", "/"));                   //设定订单日期 （必要信息 - YYYY/MM/DD）
					tPaymentRequest.dicOrder.put("OrderTime", ds.substring(11,19) ) ;                   //设定订单时间 （必要信息 - HH:MM:SS）
					tPaymentRequest.dicOrder.put("orderTimeoutDate", new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date( System.currentTimeMillis()+6000000L ))     ) ;// request.getParameter("ExpiredDate"));               //设定订单保存时间     //设定订单有效期
					tPaymentRequest.dicOrder.put("OrderNo",e.getId());                       //设定订单编号 （必要信息）
					tPaymentRequest.dicOrder.put("CurrencyCode", "156");//request.getParameter("CurrencyCode"));             //设定交易币种
					tPaymentRequest.dicOrder.put("OrderAmount", e.getPrice() );      //设定交易金额
					tPaymentRequest.dicOrder.put("InstallmentMark","0");// request.getParameter("InstallmentMark"));       //分期标识
					tPaymentRequest.dicOrder.put("CommodityType", "2") ;//request.getParameter("CommodityType"));           //设置商品种类

					//3、生成支付请求对象
					String paymentType = "A" ;// request.getParameter("PaymentType");
					tPaymentRequest.dicRequest.put("PaymentType", paymentType);            //设定支付类型
					String paymentLinkType  = "2" ;// request.getParameter("PaymentLinkType");                                         
					tPaymentRequest.dicRequest.put("PaymentLinkType", paymentLinkType);    //设定支付接入方式
					if (paymentType.equals(Constants.PAY_TYPE_UCBP) && paymentLinkType.equals(Constants.PAY_LINK_TYPE_MOBILE))
					{
					    tPaymentRequest.dicRequest.put("UnionPayLinkType","0" );//request.getParameter("UnionPayLinkType"));  //当支付类型为6，支付接入方式为2的条件满足时，需要设置银联跨行移动支付接入方式
					}
					tPaymentRequest.dicRequest.put("NotifyType","1");// request.getParameter("NotifyType"));              //设定通知方式0：URL页面通知 1：服务器通知，*必输
					tPaymentRequest.dicRequest.put("ResultNotifyURL",SystemManager.systemSetting.getWww()+"/appVehicleOrderActionFront!nyRec");// request.getParameter("ResultNotifyURL"));    //设定通知URL地址
					tPaymentRequest.dicRequest.put("MerchantRemarks","TEST" ) ;// request.getParameter("MerchantRemarks"));    //设定附言
					tPaymentRequest.dicRequest.put("IsBreakAccount", "0") ;// request.getParameter("IsBreakAccount"));      //设定交易是否分账
					JSON jsontr = tPaymentRequest.postRequest();
					String ReturnCode = jsontr.GetKeyValue("ReturnCode");
					if (ReturnCode.equals("0000"))
					{  
						JSONObject js=new JSONObject();
						js.put("success", "1");
						js.put("url", jsontr.GetKeyValue("PaymentURL"));
						json=js.toString();
						return "json";
					}
				}
			}
		}
		json = ERROR_MSG;
		return "json";
	}
	
	/**
	 * 支付返回处理
	 * @author 滕武超
	 * @date 2015-11-15 上午11:11:16 
	 * @Description:
	 */
	public String nyRec() throws Exception{
		getRequest().setCharacterEncoding("GBK");
		//1、取得MSG参数，并利用此参数值生成支付结果对象
		String msg =getRequest().getParameter("MSG");
		Base64 tBase64 = new Base64();
		String tMessage = new String(tBase64.decode(msg),"gbk");
		System.out.println("callback:"+tMessage);
		XMLDocument doc = MerchantConfig.getUniqueInstance().verifySignXML(new XMLDocument(tMessage));
		String type = this.getRequest().getParameter("type");
		TrxResponse tResult = new TrxResponse(doc);
		//2、判断支付结果状态，进行后续操作
		if (tResult.isSuccess()) {
			  System.out.println("TrxType         = [" + tResult.getValue("TrxType"        ) + "]<br>");
			  System.out.println("OrderNo         = [" + tResult.getValue("OrderNo"        ) + "]<br>");
			  System.out.println("Amount          = [" + tResult.getValue("Amount"         ) + "]<br>");
			  System.out.println("BatchNo         = [" + tResult.getValue("BatchNo"        ) + "]<br>");
			  System.out.println("VoucherNo       = [" + tResult.getValue("VoucherNo"      ) + "]<br>");
			  System.out.println("HostDate        = [" + tResult.getValue("HostDate"       ) + "]<br>");
			  System.out.println("HostTime        = [" + tResult.getValue("HostTime"       ) + "]<br>");
			  System.out.println("MerchantRemarks = [" + tResult.getValue("MerchantRemarks") + "]<br>");
			  System.out.println("PayType         = [" + tResult.getValue("PayType"        ) + "]<br>");
			  System.out.println("NotifyType      = [" + tResult.getValue("NotifyType"     ) + "]<br>");
			  System.out.println("TrnxNo          = [" + tResult.getValue("iRspRef"        ) + "]<br>");
			  String orderId = tResult.getValue("OrderNo");
			  String fPrice =  tResult.getValue("Amount");
			  System.out.println("orderId---------------------------------------------------------------"+orderId);
			  System.out.println("fPrice----------------------------------------------------------------"+fPrice);
//			  String orderId = this.getRequest().getParameter("OrderNo");
//			  String fPrice =  this.getRequest().getParameter("Amount");
		
			  if(StringUtils.isNotBlank(orderId)&&StringUtils.isNotBlank(fPrice)){
				  
				  if(orderId.startsWith(wldj)){
					  orderId=orderId.replace(wldj, "");
				  }else if(orderId.startsWith(wl)){
					  orderId=orderId.replace(wl, "");
				  }
				  AppVehicleOrder appVehicleOrder = this.server.selectById(orderId);
				  if(appVehicleOrder!=null){
					    AppVehicle appVehicle = this.appVehicleService.selectById(appVehicleOrder.getVehicleId());
						String before ="";
						 if(orderId.startsWith(wldj)){
							 before=wldj;
								appVehicle.setStatus("2");//交付定金之后锁定物流信息
								appVehicleOrder.setOrderStatus(AppVehicleOrder.xd);
						  }else if(orderId.startsWith(wl)){
							  	before=wl;
								appVehicle.setStatus("1");//最终交易之后改成发布状态
								appVehicleOrder.setOrderStatus(AppVehicleOrder.zfwc);
								appVehicleOrder.setEndTime("不限");
								appVehicleOrder.setFinalPrice(Double.parseDouble(fPrice));
						  }
						Orderpay orderpay2 = new Orderpay();
						orderpay2.setOrderid(before+orderId);
						Orderpay orderpay = this.orderpayService.selectOne(orderpay2);
						if(orderpay==null )orderpay = new Orderpay();
						orderpay.setPaystatus(net.jeeshop.services.common.Orderpay.orderpay_paystatus_y);
						this.orderpayService.update(orderpay);
						this.appVehicleService.update(appVehicle);
						this.server.update(appVehicleOrder);
						getResponse().sendRedirect(getRequest().getContextPath()+"/appJsp/paySuccess.jsp");
				  }
			  }
		}else{
			  System.out.println("ReturnCode         = [" + tResult.getReturnCode  ()+ "]<br>");
			  System.out.println("ErrorMessage         = [" + tResult.getErrorMessage()+ "]<br>");
			
		}
		json=ERROR_MSG;
		return "json";
	}
	public AppUserService getAppUserService() {
		return appUserService;
	}

	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	public AppVehicleService getAppVehicleService() {
		return appVehicleService;
	}

	public void setAppVehicleService(AppVehicleService appVehicleService) {
		this.appVehicleService = appVehicleService;
	}

	public OrderlogService getOrderlogService() {
		return orderlogService;
	}

	public void setOrderlogService(OrderlogService orderlogService) {
		this.orderlogService = orderlogService;
	}

	public OrderpayService getOrderpayService() {
		return orderpayService;
	}

	public void setOrderpayService(OrderpayService orderpayService) {
		this.orderpayService = orderpayService;
	}

	public AppVehicleOrderService getAppVehicleOrderService() {
		return appVehicleOrderService;
	}

	public void setAppVehicleOrderService(
			AppVehicleOrderService appVehicleOrderService) {
		this.appVehicleOrderService = appVehicleOrderService;
	}
	
}
