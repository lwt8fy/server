 package net.jeeshop.web.action.front.appVehicle;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.common.AppPicture;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.AppVehicle;
import net.jeeshop.services.common.AppVehicleCompany;
import net.jeeshop.services.front.appPicture.service.AppPictureService;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.front.appVehicle.service.AppVehicleService;
import net.jeeshop.services.front.appVehicleCompany.service.AppVehicleCompanyService;
import net.jeeshop.services.front.orderlog.OrderlogService;
import net.jeeshop.services.front.orderlog.bean.Orderlog;
import net.jeeshop.services.front.orderpay.OrderpayService;
import net.jeeshop.services.front.orderpay.bean.Orderpay;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.abc.trustpay.client.Base64;
import com.abc.trustpay.client.Constants;
import com.abc.trustpay.client.JSON;
import com.abc.trustpay.client.MerchantConfig;
import com.abc.trustpay.client.TrxResponse;
import com.abc.trustpay.client.XMLDocument;
import com.abc.trustpay.client.ebus.PaymentRequest;

public class AppVehicleAction  extends BaseAction<AppVehicle> {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AppVehicleAction.class);
	private AppVehicleService appVehicleService;
	private AppUserService appUserService;
	private AppPictureService appPictureService;
	private AppVehicleCompanyService appVehicleCompanyService;
	private OrderlogService orderlogService; 
	private  OrderpayService orderpayService;
	
	private final String bkSel="nybank";
	public static final String wl="WL";
	public static final String wldj="WLDJ";
	public static final String dj="dj";
	public static final String fk="fk";
	
	private String uuid;
	private String json;
	
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";
	
	/**上传的文件*/
	private File uploadImage;
	/**上传文件名称*/
	private String uploadImageFileName;
	/**上传文件的格式类型*/
	private String uploadImageContentType;
	
	
	
	
	public File getUploadImage() {
		return uploadImage;
	}

	public void setUploadImage(File uploadImage) {
		this.uploadImage = uploadImage;
	}

	public String getUploadImageFileName() {
		return uploadImageFileName;
	}

	public void setUploadImageFileName(String uploadImageFileName) {
		this.uploadImageFileName = uploadImageFileName;
	}

	public String getUploadImageContentType() {
		return uploadImageContentType;
	}

	public void setUploadImageContentType(String uploadImageContentType) {
		this.uploadImageContentType = uploadImageContentType;
	}

	

	public void setAppPictureService(AppPictureService appPictureService) {
		this.appPictureService = appPictureService;
	}

	

	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	public String selectAll(){
		if(uuid!=null&&!uuid.equals("")){
			AppUser u = appUserService.selectByUuid(uuid);
			e.setCreateAccount(u.getUsername());
		}
		e.setIsShow("y"); 
		List<AppVehicle> list = appVehicleService.selectList(e);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", list);
		jsonObject.put("success", "1");
		json=jsonObject.toString();
		return "json";
		
		
	}
	@Override
	public String insert(){
		try {
			if (uuid == null || uuid.equals("")) {
				json = ERROR_MSG;
			} else {
				AppUser u = appUserService.selectByUuid(uuid);
				if (u != null && u.getUsername().equals(e.getCreateAccount())) {
//					根据用户ＩＤ查询他所在的公司
					AppVehicleCompany appVehicleCompany = new AppVehicleCompany();
					appVehicleCompany.setCreateAccount(u.getUsername());
					
					AppVehicleCompany company = this.appVehicleCompanyService.selectOne(appVehicleCompany);
					if(company==null){
						if("1".equals(u.getAuthentication())||"2".equals(u.getAuthentication())){
							if(StringUtils.isBlank(e.getStartTime())){
								e.setStartTime(DateUtil.dateToStrSS());
							}
							e.setCreateAccount(u.getUsername());
							if(StringUtils.isNotBlank(u.getProvince())){
								e.setProvince(u.getProvince());
							}
							if(StringUtils.isNotBlank(u.getArea())){
								e.setArea(u.getArea());
							}
							if(StringUtils.isNotBlank(u.getCity())){
								e.setCity(u.getCity());
							}
							e.setX(u.getX());
							e.setY(u.getY());
							appVehicleService.insert(e);
							json = JSONObject.fromObject(e).toString();
							return "json";
						}else{
							json = ERROR_MSG;
							return "json";
						}
					}
					if(StringUtils.isBlank(e.getStartTime())){
						e.setStartTime(DateUtil.dateToStrSS());
					}
					e.setCreateAccount(u.getUsername());
					e.setCompanyId(company.getId());
					if(StringUtils.isNotBlank(company.getProvince())){
						e.setProvince(company.getProvince());
					}
					if(StringUtils.isNotBlank(company.getArea())){
						e.setArea(company.getArea());
					}
					if(StringUtils.isNotBlank(company.getCity())){
						e.setCity(company.getCity());
					}
					e.setX(company.getX());
					e.setY(company.getY());
					appVehicleService.insert(e);
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
	@Override
	public String update() {
		try{
		if(uuid==null||uuid.equals("")){
			json = ERROR_MSG;
		}else{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u==null){
				json = ERROR_MSG;
			}else{
				if (u != null) {
					if(StringUtils.isBlank(e.getStartTime())){
						e.setStartTime(DateUtil.dateToStrSS());
					}
					if(e.getStatus()!=null &&e.getStatus().equals("2")){
						e.setPurchaserID(u.getId());
						e.setPurchaserName(u.getUsername());
						e.setPurchaserPhone(u.getPhone());
					}
					appVehicleService.update(e);
					json = JSONObject.fromObject(e).toString();
					return "json";
				}
			}
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
	 * 时间：2015-11-25下午03:17:32
	 * 描述: 根据ID查询车辆详情
	 * @return
	 */
	public String selectById(){
		if(StringUtils.isNotBlank(e.getId())){
		  AppVehicle appVehicle = 	this.server.selectById(e.getId());
			if(appVehicle!=null){
				json = JSONObject.fromObject(appVehicle).toString();
				return "json";
			}
		}
		json = ERROR_MSG;
		return "json";
	}
	public String deleteById(){
		if(uuid==null||uuid.equals("")){
			json = ERROR_MSG;
		}else{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u==null){
				json = ERROR_MSG;
			}else{
				if (u != null) {
					try {
						appVehicleService.deleteById(e.getId());
						json=SUCCESS_MSG;
						return "json";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		json = ERROR_MSG;
		return "json";
	}
	public String delete(){
		try{
		if(uuid==null||uuid.equals("")){
			json = ERROR_MSG;
		}else{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u==null){
				json = ERROR_MSG;
			}else{
				if (u != null) {
					appVehicleService.delete(e);
					json=SUCCESS_MSG;
					return "json";
				}
			}
		}
		json = ERROR_MSG;
		}catch (Exception e) {
			json = Exception_MSG;
		}
		return "json";
	}
	/**
	 * 上传图片
	 * @return
	 */
	public String upload(){
		try{
		String orderid = getRequest().getParameter("orderID");
		String on = getRequest().getParameter("orderNum");
		AppPicture ap=new AppPicture();
		ap.setVehicleID(orderid);
		String uuid = UUID.randomUUID().toString().toUpperCase();
		uploadImageFileName=uuid+".jpg";
		String path = ServletActionContext.getServletContext().getRealPath("/appUpload")+"/"+uploadImageFileName;
		if(on!=null&&on.equals("1")){
			AppVehicle ao=  this.server.selectById(orderid);
			ao.setId(orderid);
			ao.setIcon("/appUpload/"+uploadImageFileName);
			appVehicleService.update(ao);
			List<AppPicture> aps = appPictureService.selectList(ap);
			for (AppPicture a : aps) {
				String path2 = ServletActionContext.getServletContext().getRealPath("/appUpload")+a.getPicture();
				FileUtils.deleteQuietly(new File(path2));
			}
			appPictureService.deleteByVid(orderid);
		}
		//目标文件File
		File destFile = new File(path);
		try {
			//将源文件复制到目标文件
			FileUtils.copyFile(uploadImage, destFile);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		ap.setPicture("/appUpload/"+uploadImageFileName);
		appPictureService.insert(ap);
		json=SUCCESS_MSG;
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			json = Exception_MSG;
		}
		return "json";
	}
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2015-11-25下午03:17:32
	 * 描述: 根据用户ID查询 或者根据公司ID查询
	 * e.createAccount
	 * e.companyId
	 * @return
	 */
	@Override
	public String selectList() throws Exception {
		// TODO Auto-generated method stub
		int os=pager.getOffset();
		pager.setOffset((os - 1) * e.getPageSize() );
		try{
			  if(e==null)e=new AppVehicle();
			  if(e.getCreateAccount()!=null&&!e.getCreateAccount().equals("")||(e.getPurchaserID()!=null&&e.getPurchaserID().length()!=0)){
					if(uuid!=null&&!uuid.equals("")&&appUserService.selectByUuid(uuid)!=null){
						super.selectList();
						pager.setOffset(os);
						JSONObject jsonObject = JSONObject.fromObject(pager);
						jsonObject.put("success", "1");
						json=jsonObject.toString();
					}else{
						json = ERROR_MSG;
					}
				}else{
					super.selectList();
					pager.setOffset(os);
					JSONObject jsonObject = JSONObject.fromObject(pager);
					jsonObject.put("success", "1");
					json=jsonObject.toString();
				}
		}catch (Exception e) {
			json = Exception_MSG;
		}
		return "json";
	}
	/**
	 * 查看详细订单信息
	 * @return
	 */
	public String show(){
		try{
			e = appVehicleService.selectById(e.getId());
			AppPicture ap=new AppPicture();
			ap.setVehicleID(e.getId());
			List<AppPicture> list= appPictureService.selectList(ap);
			e.setPictureList(list);
			json=JSONObject.fromObject(e).toString();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Exception_MSG;
		}
		return "json";
	}
	@SuppressWarnings("unchecked")
	public String  getString(){
		String productId = this.getRequest().getParameter("id");
		String type =  this.getRequest().getParameter("type");
		String test =  this.getRequest().getParameter("test");
		if(StringUtils.isNotBlank(uuid)&&StringUtils.isNotBlank(productId)&&StringUtils.isNotBlank(type)){
			String before = "";
			String jiaoyie="";
			AppUser u = appUserService.selectByUuid(uuid);
			if(u!=null){
				AppVehicle product = this.appVehicleService.selectById(productId);
				if(product!=null){
					Orderlog orderlog = new Orderlog();
					if(dj.equals(type)){
						before=wldj;
						jiaoyie = KeyValueHelper.get("APP_WLDJ").toString();
						orderlog.setContent("【创建订单】用户创建订单。订金额：" + product.getPrice());
					}else if(fk.equals(type)){
						orderlog.setContent("【修改订单】用户修改订单。最终金额：" + product.getFinalPrice());
						before=wl;
						jiaoyie = product.getFinalPrice()+"";
					}
					product.setPurchaserID(u.getId());
					product.setPurchaserName(u.getUsername());
					product.setPurchaserPhone(u.getPhone());
					server.update(product);
					// 记录订单创建日志
					orderlog.setOrderid(before+product.getId());
					orderlog.setAccount(u.getUsername());
					orderlog.setAccountType(net.jeeshop.services.common.Orderlog.orderlog_accountType_w);
					orderlogService.insert(orderlog);
					
					//创建支付记录对象
					Orderpay orderpay = new Orderpay();
					orderpay.setOrderid(before+product.getId());
					orderpay.setPaystatus(net.jeeshop.services.common.Orderpay.orderpay_paystatus_n);
					orderpay.setPayamount(Double.parseDouble(jiaoyie));
					orderpay.setPaymethod(bkSel);
					orderpayService.insert(orderpay);
					
					if(StringUtils.isNotBlank(test)){
						String Url = SystemManager.systemSetting.getWww()+"/appVehicle!nyRec?OrderNo="+orderpay.getId()+"&Amount="+jiaoyie+"&type="+type;
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
					tPaymentRequest.dicOrder.put("OrderNo",orderpay.getId());                       //设定订单编号 （必要信息）
					tPaymentRequest.dicOrder.put("CurrencyCode", "156");//request.getParameter("CurrencyCode"));             //设定交易币种
					tPaymentRequest.dicOrder.put("OrderAmount",Double.parseDouble(jiaoyie));      //设定交易金额
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
					tPaymentRequest.dicRequest.put("ResultNotifyURL",SystemManager.systemSetting.getWww()+"/appVehicle!nyRec");// request.getParameter("ResultNotifyURL"));    //设定通知URL地址
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
	
	public String nyRec() throws Exception{
		getRequest().setCharacterEncoding("GBK");
		//1、取得MSG参数，并利用此参数值生成支付结果对象
		String msg =getRequest().getParameter("MSG");
		Base64 tBase64 = new Base64();
		String tMessage = new String(tBase64.decode(msg),"gbk");
		System.out.println("callback:"+tMessage);
		XMLDocument doc = MerchantConfig.getUniqueInstance().verifySignXML(new XMLDocument(tMessage));
//		String type = this.getRequest().getParameter("type");
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
//			  String orderPayId = this.getRequest().getParameter("OrderNo");
//			  String fPrice =  this.getRequest().getParameter("Amount");
			  if(StringUtils.isNotBlank(orderId)&&StringUtils.isNotBlank(fPrice)){
				  Orderpay orderpay = this.orderpayService.selectById(orderId);
				  if(orderpay!=null){
					  if(orderpay.getOrderid().startsWith(wldj)){//定金
						  AppVehicle appVehicle = this.server.selectById(orderpay.getOrderid().replace(wldj, ""));
						  if(appVehicle!=null){
							  appVehicle.setStatus(AppVehicle.xd);
							  this.appVehicleService.update(appVehicle);
							  this.orderpayService.update(orderpay);
							  getResponse().sendRedirect(getRequest().getContextPath()+"/appJsp/paySuccess.jsp");
						  }
					  }else{
						  AppVehicle appVehicle = this.server.selectById(orderpay.getOrderid().replace(wl, ""));
						  if(appVehicle!=null){//最终价格
							  appVehicle.setStatus(AppVehicle.zfwc);
							  appVehicle.setFinalPrice(Double.parseDouble(fPrice));
							  this.appVehicleService.update(appVehicle);
							  orderpay.setPaystatus(net.jeeshop.services.common.Orderpay.orderpay_paystatus_y);
							  this.orderpayService.update(orderpay);
							  getResponse().sendRedirect(getRequest().getContextPath()+"/appJsp/paySuccess.jsp");
						  }
					  }
				  }
			  }
		}
		json=ERROR_MSG;
		return "json";
	}
	@Override
	public void insertAfter(AppVehicle e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if (this.e == null) {
			this.e = new AppVehicle();
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
	public AppVehicle getE() {
		// TODO Auto-generated method stub
		return this.e;
	}

	public void setAppVehicleService(AppVehicleService appVehicleService) {
		this.appVehicleService = appVehicleService;
	}

	public AppVehicleCompanyService getAppVehicleCompanyService() {
		return appVehicleCompanyService;
	}

	public void setAppVehicleCompanyService(
			AppVehicleCompanyService appVehicleCompanyService) {
		this.appVehicleCompanyService = appVehicleCompanyService;
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

	public AppVehicleService getAppVehicleService() {
		return appVehicleService;
	}

	public AppUserService getAppUserService() {
		return appUserService;
	}

	public AppPictureService getAppPictureService() {
		return appPictureService;
	}
	

}
