package net.jeeshop.web.action.front.orders;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.FrontContainer;
import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.MyCommonDao;
import net.jeeshop.core.kuaidi100Helper;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.CreateAreaUtil;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.common.ActivityCount;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.Logistics_free;
import net.jeeshop.services.common.Logistics_price;
import net.jeeshop.services.common.Profit;
import net.jeeshop.services.front.account.AccountService;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.front.activityCount.service.ActivityCountService;
import net.jeeshop.services.front.address.AddressService;
import net.jeeshop.services.front.address.bean.Address;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.front.comment.CommentService;
import net.jeeshop.services.front.comment.bean.Comment;
import net.jeeshop.services.front.company.CompanyService;
import net.jeeshop.services.front.company.bean.Company;
import net.jeeshop.services.front.coupon.CouponService;
import net.jeeshop.services.front.order.OrderService;
import net.jeeshop.services.front.order.bean.Order;
import net.jeeshop.services.front.orderdetail.OrderdetailService;
import net.jeeshop.services.front.orderdetail.bean.Orderdetail;
import net.jeeshop.services.front.orderlog.OrderlogService;
import net.jeeshop.services.front.orderlog.bean.Orderlog;
import net.jeeshop.services.front.orderpay.OrderpayService;
import net.jeeshop.services.front.orderpay.bean.Orderpay;
import net.jeeshop.services.front.ordership.OrdershipService;
import net.jeeshop.services.front.ordership.bean.Ordership;
import net.jeeshop.services.front.product.ProductService;
import net.jeeshop.services.front.product.bean.Product;
import net.jeeshop.services.front.product.bean.ProductStockInfo;
import net.jeeshop.services.manage.activity.ActivityService;
import net.jeeshop.services.manage.activity.bean.Activity;
import net.jeeshop.services.manage.logistics.bean.Logistics;
import net.jeeshop.services.manage.logistics.service.LogisticsService;
import net.jeeshop.services.manage.logistics_free.service.Logistics_freeService;
import net.jeeshop.services.manage.logistics_price.service.Logistics_priceService;
import net.jeeshop.services.manage.profit.service.ProfitService;
import net.jeeshop.services.manage.spec.SpecService;
import net.jeeshop.services.manage.spec.bean.Spec;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abc.trustpay.client.Base64;
import com.abc.trustpay.client.Constants;
import com.abc.trustpay.client.JSON;
import com.abc.trustpay.client.MerchantConfig;
import com.abc.trustpay.client.TrxResponse;
import com.abc.trustpay.client.XMLDocument;
import com.abc.trustpay.client.ebus.PaymentRequest;
/**
 * 
 * 作者:王海洋
 * 时间:2015-11-14上午11:40:58
 * 描述:神州牧易客户端订单接口
 */
public class SzmyMobileOrdersAction extends BaseAction<Order>{
	private static final Logger logger = LoggerFactory.getLogger(SzmyMobileOrdersAction.class);
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";
	private final String bkSel="nybank";
	private final String beforeStr="M";
	private final String successRel="/szmyMobie/paySuccess.jsp";
	private String jsonStr;
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	private AppUserService appUserService;  
	
	private AddressService addressService;
	private SpecService specService;
	private ActivityService activityService; 
	public SpecService getSpecService() {
		return specService;
	}

	public void setSpecService(SpecService specService) {
		this.specService = specService;
	}

	public ActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

	public ActivityCountService getActivityCountService() {
		return activityCountService;
	}

	public void setActivityCountService(ActivityCountService activityCountService) {
		this.activityCountService = activityCountService;
	}

	public MyCommonDao getMyCommonDao() {
		return myCommonDao;
	}

	public void setMyCommonDao(MyCommonDao myCommonDao) {
		this.myCommonDao = myCommonDao;
	}

	private ActivityCountService activityCountService;
	private OrdershipService ordershipService;
	private OrderlogService orderlogService; 
	private MyCommonDao myCommonDao;
	private  OrderpayService orderpayService;
	
	private ProfitService profitService; 
	private AccountService accountService;
	private OrderService orderService;
	private CompanyService companyService;
	private CouponService couponService;
	private String selectLeftMenu;
	private CommentService commentService;
	private LogisticsService logisticsService;
	public LogisticsService getLogisticsService() {
		return logisticsService;
	}

	public void setLogisticsService(LogisticsService logisticsService) {
		this.logisticsService = logisticsService;
	}

	private Logistics_priceService logistics_priceService;
	public Logistics_priceService getLogistics_priceService() {
		return logistics_priceService;
	}

	public void setLogistics_priceService(
			Logistics_priceService logisticsPriceService) {
		logistics_priceService = logisticsPriceService;
	}

	private Logistics_freeService logistics_freeService;
	
	public Logistics_freeService getLogistics_freeService() {
		return logistics_freeService;
	}

	public void setLogistics_freeService(Logistics_freeService logisticsFreeService) {
		logistics_freeService = logisticsFreeService;
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public String getSelectLeftMenu() {
		return selectLeftMenu;
	}

	public void setSelectLeftMenu(String selectLeftMenu) {
		this.selectLeftMenu = selectLeftMenu;
	}

	public CouponService getCouponService() {
		return couponService;
	}

	public void setCouponService(CouponService couponService) {
		this.couponService = couponService;
	}

	private String json ;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1869416679300070002L;
	
	
	private ProductService productService;
	
	private OrderdetailService orderdetailService;

	@Override
	public Order getE() {
		return this.e;
	}

	@Override
	public void insertAfter(Order e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
			this.e = new Order();
		}
		
	}

	@Override
	protected void selectListAfter() {
		// TODO Auto-generated method stub
		
	}
	/** paymentType = "A" ;// request.getParameter("PaymentType"); // 农业银行是A 其他银行是6
						tPaymentRequest.dicRequest.put("PaymentType", paymentType);            //设定支付类型
						 paymentLinkType  = "2" ;// request.getParameter("PaymentLinkType");  //手机端是2 PC是1       
	 * 
	 * 作者：王海洋
	 * 时间：2015-12-29下午02:10:12
	 * 描述: 
	 * @param num
	 * @param pId
	 * @param paymentType  农业银行是A 跨行银行是6
	 * @param paymentLinkType //手机端是2 PC是1 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String toPay() throws Exception{
		//DecimalFormat df = new DecimalFormat("0.00");
		String paymentType = this.getRequest().getParameter("paymentType");
		String total = this.getRequest().getParameter("total");
		String id = this.getRequest().getParameter("id");
		if(StringUtils.isNotBlank(id)){
		Account u =(Account)this.getSession().getAttribute(FrontContainer.USER_INFO);
		if(u!=null){
						 Order order  = orderService.selectById(id);
						if(order==null){
							getRequest().setAttribute("message", "订单失效！");
							getRequest().getRequestDispatcher("/szmyMobie/payStyle.jsp").forward(getRequest(), getResponse());
							return null;
						}
						//创建支付记录对象
						Orderpay orderpay = new Orderpay();
						orderpay.setOrderid(id);
						orderpay.setPaystatus(net.jeeshop.services.common.Orderpay.orderpay_paystatus_n);
						orderpay.setPayamount(Double.parseDouble(order.getAmount()));
						orderpay.setPaymethod(bkSel);
						orderpayService.insert(orderpay);
						 /*getResponse().sendRedirect(getRequest().getContextPath()+successRel);
						 return null;*/
//						  String status="WAIT_SELLER_SEND_GOODS";
					/*	  if (orderService.quickpayNotify(status,orderpay.getId())) {
							  getSession().setAttribute("compList", null);
							  getSession().setAttribute(FrontContainer.myCart, null);
							  getResponse().sendRedirect(getRequest().getContextPath()+successRel);
							 return null;
						  }*/
				/*////////////////////////////////////////////////////////////////////////////////////////////////////////////////	测试	
						 String status="WAIT_SELLER_SEND_GOODS";
						 if (orderService.quickpayNotify(status,orderpay.getId())) {
							 JSONObject js=new JSONObject();
								js.put("success", "1");
								js.put("url", "http://192.168.0.165:8888/animalshop/appJsp/paySuccess.jsp");
								json=js.toString();
								return "json";
						}else{
							json = ERROR_MSG;
							return "json";
						}
					*////////////////////////////////////////////////////////////////////////////////////////////////////	
						
						//1、生成订单对象
						String ds = DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
						PaymentRequest tPaymentRequest = new PaymentRequest();
						tPaymentRequest.dicOrder.put("PayTypeID","ImmediatePay");// request.getParameter("PayTypeID"));                   //设定交易类型
						tPaymentRequest.dicOrder.put("OrderDate", ds.substring(0, 10).replace("-", "/"));                   //设定订单日期 （必要信息 - YYYY/MM/DD）
						tPaymentRequest.dicOrder.put("OrderTime", ds.substring(11,19) ) ;                   //设定订单时间 （必要信息 - HH:MM:SS）
						tPaymentRequest.dicOrder.put("orderTimeoutDate", new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date( System.currentTimeMillis()+6000000L ))     ) ;// request.getParameter("ExpiredDate"));               //设定订单保存时间     //设定订单有效期
						tPaymentRequest.dicOrder.put("OrderNo", beforeStr+orderpay.getId());                       //设定订单编号 （必要信息）
						tPaymentRequest.dicOrder.put("CurrencyCode", "156");//request.getParameter("CurrencyCode"));             //设定交易币种
						tPaymentRequest.dicOrder.put("OrderAmount", total );      //设定交易金额
						tPaymentRequest.dicOrder.put("InstallmentMark","0");// request.getParameter("InstallmentMark"));       //分期标识

						tPaymentRequest.dicOrder.put("CommodityType", "2") ;//request.getParameter("CommodityType"));           //设置商品种类

						//3、生成支付请求对象
//						 paymentType = "A" ;// request.getParameter("PaymentType"); // 农业银行是A 其他银行是6
						tPaymentRequest.dicRequest.put("PaymentType", paymentType);            //设定支付类型
						String paymentLinkType  = "2" ;// request.getParameter("PaymentLinkType");  //手机端是2 PC是1                                       
						tPaymentRequest.dicRequest.put("PaymentLinkType", paymentLinkType);    //设定支付接入方式
						if (paymentType.equals(Constants.PAY_TYPE_UCBP) && paymentLinkType.equals(Constants.PAY_LINK_TYPE_MOBILE))
						{
						    tPaymentRequest.dicRequest.put("UnionPayLinkType","0" );//request.getParameter("UnionPayLinkType"));  //当支付类型为6，支付接入方式为2的条件满足时，需要设置银联跨行移动支付接入方式
						}
						tPaymentRequest.dicRequest.put("NotifyType","1");// request.getParameter("NotifyType"));              //设定通知方式0：URL页面通知 1：服务器通知，*必输

						tPaymentRequest.dicRequest.put("ResultNotifyURL",SystemManager.systemSetting.getWww()+"/szmyMobileOrdersActionFront!nyRec");// request.getParameter("ResultNotifyURL"));    //设定通知URL地址
						tPaymentRequest.dicRequest.put("MerchantRemarks","TEST" ) ;// request.getParameter("MerchantRemarks"));    //设定附言
						tPaymentRequest.dicRequest.put("IsBreakAccount", "0") ;// request.getParameter("IsBreakAccount"));      //设定交易是否分账

						JSON jsontr = tPaymentRequest.postRequest();

						String ReturnCode = jsontr.GetKeyValue("ReturnCode");
						if (ReturnCode.equals("0000"))
						{  
							getResponse().sendRedirect(jsontr.GetKeyValue("PaymentURL"));
							return null;
						}
					}else{
						getRequest().getRequestDispatcher("/szmyMobie/login.jsp").forward(getRequest(), getResponse());
					}
		}else{
			getRequest().setAttribute("message", "参数错误！");
			getRequest().getRequestDispatcher("/szmyMobie/payStyle.jsp").forward(getRequest(), getResponse());
		}
		return null;
	}
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2015-11-14上午11:41:55
	 * 描述: 插入订单
	 * @return
	 * @throws IOException 
	 */
	/*public String toPay() throws IOException{
		String num = this.getRequest().getParameter("inputBuyNum");//商品数量
		String productId = this.getRequest().getParameter("productID");
		String paymentType = this.getRequest().getParameter("paymentType");
		if(StringUtils.isNotBlank(paymentType)&&StringUtils.isNotBlank(productId)&&NumberUtils.isNumber(num)){
			try{
				return pay(num, productId, paymentType, "2");
			}catch (Exception e) {
				logger.error("支付失败!");
				return null;
			}
		}
		 throw new NullPointerException("支付失败!");
	}*/
	/**
	 * 购物车插入订单
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String toPayCart() throws Exception{
		//DecimalFormat df = new DecimalFormat("0.00");
		String paymentType = this.getRequest().getParameter("paymentType");
		String total = this.getRequest().getParameter("total");
		String id = this.getRequest().getParameter("id");
		if(StringUtils.isNotBlank(id)){
		Account u =(Account)this.getSession().getAttribute(FrontContainer.USER_INFO);
		if(u!=null){
						 Order order  = orderService.selectById(id);
						if(order==null){
							getRequest().setAttribute("message", "订单失效！");
							getRequest().getRequestDispatcher("/szmyMobie/payStyle.jsp").forward(getRequest(), getResponse());
							return null;
						}
						//创建支付记录对象
						Orderpay orderpay = new Orderpay();
						orderpay.setOrderid(id);
						orderpay.setPaystatus(net.jeeshop.services.common.Orderpay.orderpay_paystatus_n);
						orderpay.setPayamount(Double.parseDouble(order.getAmount()));
						orderpay.setPaymethod(bkSel);
						orderpayService.insert(orderpay);
						 /*getResponse().sendRedirect(getRequest().getContextPath()+successRel);
						 return null;*/
//						  String status="WAIT_SELLER_SEND_GOODS";
					/*	  if (orderService.quickpayNotify(status,orderpay.getId())) {
							  getSession().setAttribute("compList", null);
							  getSession().setAttribute(FrontContainer.myCart, null);
							  getResponse().sendRedirect(getRequest().getContextPath()+successRel);
							 return null;
						  }*/
				/*////////////////////////////////////////////////////////////////////////////////////////////////////////////////	测试	
						 String status="WAIT_SELLER_SEND_GOODS";
						 if (orderService.quickpayNotify(status,orderpay.getId())) {
							 JSONObject js=new JSONObject();
								js.put("success", "1");
								js.put("url", "http://192.168.0.165:8888/animalshop/appJsp/paySuccess.jsp");
								json=js.toString();
								return "json";
						}else{
							json = ERROR_MSG;
							return "json";
						}
					*////////////////////////////////////////////////////////////////////////////////////////////////////	
						
						//1、生成订单对象
						String ds = DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
						PaymentRequest tPaymentRequest = new PaymentRequest();
						tPaymentRequest.dicOrder.put("PayTypeID","ImmediatePay");// request.getParameter("PayTypeID"));                   //设定交易类型
						tPaymentRequest.dicOrder.put("OrderDate", ds.substring(0, 10).replace("-", "/"));                   //设定订单日期 （必要信息 - YYYY/MM/DD）
						tPaymentRequest.dicOrder.put("OrderTime", ds.substring(11,19) ) ;                   //设定订单时间 （必要信息 - HH:MM:SS）
						tPaymentRequest.dicOrder.put("orderTimeoutDate", new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date( System.currentTimeMillis()+6000000L ))     ) ;// request.getParameter("ExpiredDate"));               //设定订单保存时间     //设定订单有效期
						tPaymentRequest.dicOrder.put("OrderNo", beforeStr+orderpay.getId());                       //设定订单编号 （必要信息）
						tPaymentRequest.dicOrder.put("CurrencyCode", "156");//request.getParameter("CurrencyCode"));             //设定交易币种
						tPaymentRequest.dicOrder.put("OrderAmount", total );      //设定交易金额
						tPaymentRequest.dicOrder.put("InstallmentMark","0");// request.getParameter("InstallmentMark"));       //分期标识

						tPaymentRequest.dicOrder.put("CommodityType", "2") ;//request.getParameter("CommodityType"));           //设置商品种类

						//3、生成支付请求对象
//						 paymentType = "A" ;// request.getParameter("PaymentType"); // 农业银行是A 其他银行是6
						tPaymentRequest.dicRequest.put("PaymentType", paymentType);            //设定支付类型
						String paymentLinkType  = "2" ;// request.getParameter("PaymentLinkType");  //手机端是2 PC是1                                       
						tPaymentRequest.dicRequest.put("PaymentLinkType", paymentLinkType);    //设定支付接入方式
						if (paymentType.equals(Constants.PAY_TYPE_UCBP) && paymentLinkType.equals(Constants.PAY_LINK_TYPE_MOBILE))
						{
						    tPaymentRequest.dicRequest.put("UnionPayLinkType","0" );//request.getParameter("UnionPayLinkType"));  //当支付类型为6，支付接入方式为2的条件满足时，需要设置银联跨行移动支付接入方式
						}
						tPaymentRequest.dicRequest.put("NotifyType","1");// request.getParameter("NotifyType"));              //设定通知方式0：URL页面通知 1：服务器通知，*必输

						tPaymentRequest.dicRequest.put("ResultNotifyURL",SystemManager.systemSetting.getWww()+"/szmyMobileOrdersActionFront!nyRec");// request.getParameter("ResultNotifyURL"));    //设定通知URL地址
						tPaymentRequest.dicRequest.put("MerchantRemarks","TEST" ) ;// request.getParameter("MerchantRemarks"));    //设定附言
						tPaymentRequest.dicRequest.put("IsBreakAccount", "0") ;// request.getParameter("IsBreakAccount"));      //设定交易是否分账

						JSON jsontr = tPaymentRequest.postRequest();

						String ReturnCode = jsontr.GetKeyValue("ReturnCode");
						if (ReturnCode.equals("0000"))
						{  
							getResponse().sendRedirect(jsontr.GetKeyValue("PaymentURL"));
							return null;
						}
					}else{
						getRequest().getRequestDispatcher("/szmyMobie/login.jsp").forward(getRequest(), getResponse());
					}
		}else{
			getRequest().setAttribute("message", "参数错误！");
			getRequest().getRequestDispatcher("/szmyMobie/payStyle.jsp").forward(getRequest(), getResponse());
		}
		return null;
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
		  
		  String status="WAIT_SELLER_SEND_GOODS";//beforeStr
		  String OrderNo = tResult.getValue("OrderNo");
		  if(StringUtils.isNotBlank(OrderNo)){
			  OrderNo =OrderNo.replace(beforeStr, "");
			  if (orderService.quickpayNotify(status,OrderNo)) {
				  getResponse().sendRedirect(getRequest().getContextPath()+successRel);
			  }
		  }
		}
		json="支付失败!";
		return "json";
	}
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2015-11-14下午02:54:23
	 * 描述: 获取订单列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOrdersList(){
		
		int os=pager.getOffset();
		pager.setOffset((os - 1) * e.getPageSize());
		String uuid = this.getRequest().getParameter("uuid");
		if(StringUtils.isNotBlank(uuid)){
			AppUser u = appUserService.selectByUuid(uuid);
			if(u!=null){
				try {
					e.setAccount(u.getUsername());
					super.selectList();
					pager.setOffset(os);
					
					//封装订单详情
					List<Order> ordersTemp = pager.getList();
					List<String> ids = new LinkedList<String>();
					for(int i=0;i<ordersTemp.size();i++){
						Order orderItem = ordersTemp.get(i);
						orderItem.setCreatedate(orderItem.getCreatedate().substring(0,16));
						ids.add(orderItem.getId());
					}
					
					Orderdetail od= new Orderdetail();
					od.setQueryOrderIDs(ids);
					List<Orderdetail> list = orderdetailService.selectList(od);
					for (Order o : ordersTemp) {
						o.setOrderdetail(new ArrayList<Orderdetail>());
						for (Orderdetail ode : list) {
							if(o.getId().equals(ode.getOrderID()+"")){
								o.getOrderdetail().add(ode);
							}
						}
					}
					
					
					JSONObject jsonObject = JSONObject.fromObject(pager);
					jsonObject.put("success", "1");
				    json=jsonObject.toString();
				    return "json";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		json = ERROR_MSG;
		return "json";
	}
	
	public String updateOrderStatus(){
		String uuid = this.getRequest().getParameter("uuid");
		String orderId = this.getRequest().getParameter("orderId");
		String sturse = this.getRequest().getParameter("sturse") ;
		if(StringUtils.isNotBlank(uuid)){
			AppUser u = appUserService.selectByUuid(uuid);
			if(u!=null){
				Order order = new Order();
				order.setId(orderId);
				order.setStatus(sturse);
				this.server.update(order);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("success", 1);
				JSONObject jsonObject = JSONObject.fromObject(map);
			    json=jsonObject.toString();
			    return "json";
			}
		}
		json = ERROR_MSG;
		return "";
	}
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2015-11-14上午11:30:26
	 * 描述: 农行支付接口
	 * @return
	 */
	/*public String payForNy(){
		
		
		return "json";
	}*/
	
	/**
	 * teng
	 * 确认收货
	 * @return
	 */
	public String receive(){
		try{
		String uuid = this.getRequest().getParameter("uuid");
		AppUser u = appUserService.selectByUuid(uuid);
		//String sturse = this.getRequest().getParameter("sturse") ;
		if(u==null){
			json=ERROR_MSG;
			return json;
		}
		Account acc = accountService.selectById(u.getId());
		String orderid = getRequest().getParameter("orderId");
		logger.error("orderid="+orderid);
		if(StringUtils.isBlank(orderid)){
			throw new NullPointerException();
		}
		
		Order order = orderService.selectById(orderid);
		if(order==null){
			throw new NullPointerException("根据订单号查询不到订单信息！");
		}
		
		Ordership ordership = ordershipService.selectOne(new Ordership(orderid));
		if(ordership==null){
			throw new NullPointerException("根据订单号查询不到配送信息！");
		}
		
		/*
		 * 积分处理
		 * teng
		 */
		
		if(order.getActivityID()!=null&&!order.getActivityID().equals("")){
			Account acc2=new Account();
			acc2.setId(acc.getId());
			acc2.setMoney((acc.getMoney()==null?0.0:acc.getMoney())+order.getIntegral());
			accountService.updateMoney(acc2);
			acc.setMoney((acc.getMoney()==null?0.0:acc.getMoney())+order.getIntegral());
			getSession().setAttribute(FrontContainer.USER_INFO,acc);
		}
		
		//推荐者返款
		if(acc.getPresenter()!=null&&!acc.getPresenter().equals("")){
			//上级
			Account pa=new Account();
			
			if(acc.getPresenter().equals("0")){
				pa=acc;
			}else{
			pa.setAccount(acc.getPresenter());
			pa = accountService.selectOne(pa);
			}
			//上级商家
			Company c=new Company();
			c.setCreateAccount(pa.getAccount());
			c=companyService.selectOne(c);
			
			//购买物品
			Orderdetail orderdetail=new Orderdetail();
			orderdetail.setOrderID(Integer.valueOf(orderid));
			List<Orderdetail> list = orderdetailService.selectList(orderdetail);
			for (Orderdetail od : list) {
				
				
				if(od.getPurchasePrice()==null||Double.valueOf(od.getPrice())<=od.getPurchasePrice()){
					continue;
				}
				
				
				Product p = productService.selectById(od.getProductID()+"");
				if(p.getChuChangPrice()==null||p.getChuChangPrice().length()==0){
					continue;
				}
				
				
				Profit profit=new Profit();
				
				profit.setPresentee(acc.getAccount());
				profit.setProductID(od.getProductID()+"");
				profit.setProductName(od.getProductName());
				profit.setNumber(od.getNumber());
				profit.setPrice(Double.valueOf(od.getPrice()));
				profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
				
				
				profit.setCompID(p.getCompID());
				profit.setCompName(p.getCompName());
				profit.setProfit(Double.valueOf(od.getPrice())-od.getPurchasePrice());
				
				
				if(c!=null&&p.getCompID().equals(c.getId())){
					Account a2=new Account();
					//推荐者收益
					profit.setType("2");
					if(pa.getPresenter()==null||pa.getPresenter().equals("0")){
					profit.setUserID(pa.getId());
					profit.setAccount(pa.getAccount());
					profit.setRatio(Double.valueOf(KeyValueHelper.get("SYBL2").split(":")[0])/10*100);
					profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio());
					a2.setId(profit.getUserID());
					a2.setMoney((pa.getMoney()==null?0:pa.getMoney())+profit.getFinalProfit());
					accountService.updateMoney(a2);
					profitService.insert(profit);
					}else{
						Double sybl=Double.valueOf(KeyValueHelper.get("SYBL2").split(":")[0])/10;
						
						
						Account pa2=new Account();
						pa2.setAccount(pa.getPresenter());
						pa2 = accountService.selectOne(pa2);
						if(pa2.getPresenter()==null||pa2.getPresenter().equals("0")){//一级推广为空(只有两级)
							
							Double sybl1=sybl*Double.valueOf(KeyValueHelper.get("SYBL3").split(":")[0])/10;
							Double sybl2=sybl*Double.valueOf(KeyValueHelper.get("SYBL3").split(":")[1])/10;
						
						if(pa2==null){//二级推广为空(只有一级)
							profit.setUserID(pa.getId());
							profit.setAccount(pa.getAccount());
							profit.setRatio(sybl);
							profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
							a2.setId(profit.getUserID());
							a2.setMoney((pa.getMoney()==null?0:pa.getMoney())+profit.getFinalProfit());
							accountService.updateMoney(a2);
							profitService.insert(profit);
							
						}else{//二级推广不为空(有两级)

							profit.setUserID(pa.getId());
							profit.setAccount(pa.getAccount());
							profit.setRatio(sybl2);
							profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
							a2.setId(profit.getUserID());
							a2.setMoney((pa.getMoney()==null?0:pa.getMoney())+profit.getFinalProfit());
							accountService.updateMoney(a2);
							profitService.insert(profit);
							
							
							profit.setId(null);
							profit.setUserID(pa2.getId());
							profit.setAccount(pa2.getAccount());
							profit.setRatio(sybl1);
							profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
							a2.setId(profit.getUserID());
							a2.setMoney((pa2.getMoney()==null?0:pa2.getMoney())+profit.getFinalProfit());
							accountService.updateMoney(a2);
							profitService.insert(profit);
							
						}
						}else{//一级推广不为空(有三级)
							Double sybl1=sybl*Double.valueOf(KeyValueHelper.get("SYBL4").split(":")[0])/10;
							Double sybl2=sybl*Double.valueOf(KeyValueHelper.get("SYBL4").split(":")[1])/10;
							Double sybl3=sybl*Double.valueOf(KeyValueHelper.get("SYBL4").split(":")[2])/10;
							
							profit.setUserID(pa.getId());
							profit.setAccount(pa.getAccount());
							profit.setRatio(sybl3);
							profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
							a2.setId(profit.getUserID());
							a2.setMoney((pa.getMoney()==null?0:pa.getMoney())+profit.getFinalProfit());
							accountService.updateMoney(a2);
							profitService.insert(profit);
							
							
							profit.setId(null);
							profit.setUserID(pa2.getId());
							profit.setAccount(pa2.getAccount());
							profit.setRatio(sybl2);
							profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
							a2.setId(profit.getUserID());
							a2.setMoney((pa2.getMoney()==null?0:pa2.getMoney())+profit.getFinalProfit());
							accountService.updateMoney(a2);
							profitService.insert(profit);
							
							Account pa3=new Account();
							pa3.setAccount(pa2.getPresenter());
							pa3 = accountService.selectOne(pa3);
							
							profit.setId(null);
							profit.setUserID(pa3.getId());
							profit.setAccount(pa3.getAccount());
							profit.setRatio(sybl1);
							profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
							a2.setId(profit.getUserID());
							a2.setMoney((pa3.getMoney()==null?0:pa3.getMoney())+profit.getFinalProfit());
							accountService.updateMoney(a2);
							profitService.insert(profit);
						}
						
					}
					//平台收益
					profit.setId(null);
					profit.setType("1");
					Account pa2=accountService.selectById(KeyValueHelper.get("PTSYZH"));
					profit.setUserID(pa2.getId());
					profit.setAccount(pa2.getAccount());
					profit.setRatio(Double.valueOf(KeyValueHelper.get("SYBL2").split(":")[1])/10);
					profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
					a2.setId(profit.getUserID());
					a2.setMoney((pa2.getMoney()==null?0:pa2.getMoney())+profit.getFinalProfit());
					accountService.updateMoney(a2);
					profitService.insert(profit);
					
					//渠道商收益
					profit.setId(null);
					profit.setType("4");
					
					pa2=accountService.selectById(KeyValueHelper.get("PTSYZH2"));
					profit.setUserID(pa2.getId());
					profit.setAccount(pa2.getAccount());
					profit.setRatio(Double.valueOf(KeyValueHelper.get("SYBL2").split(":")[2])/10);
					profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
					a2.setId(profit.getUserID());
					a2.setMoney((pa2.getMoney()==null?0:pa2.getMoney())+profit.getFinalProfit());
					accountService.updateMoney(a2);
					profitService.insert(profit);
					
				}else{
					Account a2=new Account();
					//推荐者收益
					profit.setType("3");
					Double sybl=Double.valueOf(KeyValueHelper.get("SYBL1").split(":")[0])/10;
					if(pa.getPresenter()==null||pa.getPresenter().equals("0")){
					profit.setUserID(pa.getId());
					profit.setAccount(pa.getAccount());
					profit.setRatio(sybl);
					profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio());
					a2.setId(profit.getUserID());
					a2.setMoney((pa.getMoney()==null?0:pa.getMoney())+profit.getFinalProfit());
					accountService.updateMoney(a2);
					profitService.insert(profit);
					}else{
						
						Account pa2=new Account();
						pa2.setAccount(pa.getPresenter());
						pa2 = accountService.selectOne(pa2);
						if(pa2.getPresenter()==null||pa2.getPresenter().equals("0")){//一级推广为空(只有两级)
							
							Double sybl1=sybl*Double.valueOf(KeyValueHelper.get("SYBL3").split(":")[0])/10;
							Double sybl2=sybl*Double.valueOf(KeyValueHelper.get("SYBL3").split(":")[1])/10;
						
						if(pa2==null){//二级推广为空(只有一级)
							profit.setUserID(pa.getId());
							profit.setAccount(pa.getAccount());
							profit.setRatio(sybl);
							profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
							a2.setId(profit.getUserID());
							a2.setMoney((pa.getMoney()==null?0:pa.getMoney())+profit.getFinalProfit());
							accountService.updateMoney(a2);
							profitService.insert(profit);
							
						}else{//二级推广不为空(有两级)

							profit.setUserID(pa.getId());
							profit.setAccount(pa.getAccount());
							profit.setRatio(sybl2);
							profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
							a2.setId(profit.getUserID());
							a2.setMoney((pa.getMoney()==null?0:pa.getMoney())+profit.getFinalProfit());
							accountService.updateMoney(a2);
							profitService.insert(profit);
							
							
							profit.setId(null);
							profit.setUserID(pa2.getId());
							profit.setAccount(pa2.getAccount());
							profit.setRatio(sybl1);
							profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
							a2.setId(profit.getUserID());
							a2.setMoney((pa2.getMoney()==null?0:pa2.getMoney())+profit.getFinalProfit());
							accountService.updateMoney(a2);
							profitService.insert(profit);
							
						}
						}else{//一级推广不为空(有三级)
							Double sybl1=sybl*Double.valueOf(KeyValueHelper.get("SYBL4").split(":")[0])/10;
							Double sybl2=sybl*Double.valueOf(KeyValueHelper.get("SYBL4").split(":")[1])/10;
							Double sybl3=sybl*Double.valueOf(KeyValueHelper.get("SYBL4").split(":")[2])/10;
							
							profit.setUserID(pa.getId());
							profit.setAccount(pa.getAccount());
							profit.setRatio(sybl3);
							profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
							a2.setId(profit.getUserID());
							a2.setMoney((pa.getMoney()==null?0:pa.getMoney())+profit.getFinalProfit());
							accountService.updateMoney(a2);
							profitService.insert(profit);
							
							
							profit.setId(null);
							profit.setUserID(pa2.getId());
							profit.setAccount(pa2.getAccount());
							profit.setRatio(sybl2);
							profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
							a2.setId(profit.getUserID());
							a2.setMoney((pa2.getMoney()==null?0:pa2.getMoney())+profit.getFinalProfit());
							accountService.updateMoney(a2);
							profitService.insert(profit);
							
							Account pa3=new Account();
							pa3.setAccount(pa2.getPresenter());
							pa3 = accountService.selectOne(pa3);
							
							profit.setId(null);
							profit.setUserID(pa3.getId());
							profit.setAccount(pa3.getAccount());
							profit.setRatio(sybl1);
							profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
							a2.setId(profit.getUserID());
							a2.setMoney((pa3.getMoney()==null?0:pa3.getMoney())+profit.getFinalProfit());
							accountService.updateMoney(a2);
							profitService.insert(profit);
						}
						
					}
					//平台收益
					profit.setId(null);
					profit.setType("1");
					Account pa2=accountService.selectById(KeyValueHelper.get("PTSYZH"));
					profit.setUserID(pa2.getId());
					profit.setAccount(pa2.getAccount());
					profit.setRatio(Double.valueOf(KeyValueHelper.get("SYBL1").split(":")[1])/10);
					profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
					a2.setId(profit.getUserID());
					a2.setMoney((pa.getMoney()==null?0:pa2.getMoney())+profit.getFinalProfit());
					accountService.updateMoney(a2);
					profitService.insert(profit);
					
					//商家收益
					profit.setId(null);
					profit.setType("2");
					Company com = companyService.selectById(p.getCompID());
					pa2=new Account();
					pa2.setAccount(com.getCreateAccount());
					pa2 = accountService.selectOne(pa2);
					profit.setUserID(pa2.getId());
					profit.setAccount(pa2.getAccount());
					profit.setRatio(Double.valueOf(KeyValueHelper.get("SYBL1").split(":")[2])/10);
					profit.setFinalProfit(profit.getProfit()*profit.getNumber()*profit.getRatio()*100);
					a2.setId(profit.getUserID());
					a2.setMoney((pa2.getMoney()==null?0:pa2.getMoney())+profit.getFinalProfit());
					accountService.updateMoney(a2);
					profitService.insert(profit);
				}
				
			}
			
			if(!DateUtil.strToDate(acc.getPastDue().substring(0,10)).after(DateUtil.strToDate("2999-12-30"))){
			Account a=new Account();
			a.setId(acc.getId());
			a.setPastDue("2999-12-31");
			accountService.update(a);
			
			acc.setPastDue(a.getPastDue());
			getSession().setAttribute(FrontContainer.USER_INFO, acc);
			}
		}
		
		
		
		//add by wm 2015 
		Orderpay orderpay = new Orderpay();
		orderpay.setOrderid(orderid);
		List<Orderpay> list = orderpayService.selectList(orderpay);
		  if(order.getPaystatus().equals(net.jeeshop.services.common.Order.order_paystatus_y)&&order.getStatus().equals(net.jeeshop.services.common.Order.order_status_send)&&list!=null&&list.size()>0){
			   String pid=null;
			   for (Orderpay op : list) {
				if(op.getPaystatus().equals(net.jeeshop.services.common.Orderpay.orderpay_paystatus_y)){
					pid=op.getId();
					break;
				}
			}
			   if(pid!=null){
				   orderService.quickpayNotify("TRADE_FINISHED", pid);
					json=SUCCESS_MSG;
			   }
		}
		} catch (Exception e) {
			  logger.error("receive..."+e);
			  json = Exception_MSG;
		}
		return "json";
	}
	/**
	 * 订单确认
	 * 何南
	 */
	
	public String confirmOrder() throws Exception{
		logger.error("confirmOrder..");
		DecimalFormat df = new DecimalFormat("0.00");
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
//			getSession().getAttribute(FrontContainer);
			return "toLogin";
		}
		String num = this.getRequest().getParameter("inputBuyNum");//商品数量
		String productId = this.getRequest().getParameter("productID");
		getRequest().setAttribute("inputBuyNum", num);
		getRequest().setAttribute("productID", productId);
		
		//检查购买的商品是否超出可购买的库存数
		CartInfo cartInfo = (CartInfo)getSession().getAttribute(FrontContainer.myCart);
		if(cartInfo==null){
			throw new NullPointerException("非法请求");
		}
		Set<String> set=new HashSet<String>();
		for(int i=0;i<cartInfo.getProductList().size();i++){
			Product product = cartInfo.getProductList().get(i);
			ProductStockInfo stockInfo = SystemManager.productStockMap.get(product.getId());
			if(stockInfo==null){
				//商品已卖完或已下架，请联系站点管理员!
				throw new RuntimeException("商品已卖完或已下架，请联系站点管理员!");
			}else if(stockInfo.getStock() < product.getBuyCount()){
				//购买的商品数超出库存数
				throw new RuntimeException("商品已卖完或已下架，请联系站点管理员!");
			}
			String buySpecID = getRequest().getParameter("specIdHidden");
			int activityStart=0;
			/**
			 * 加载指定商品的规格信息
			 */
			if(StringUtils.isNotBlank(buySpecID)){
				Spec spec = specService.selectById(buySpecID);
				if(spec==null){
					throw new NullPointerException("根据指定的规格"+buySpecID+"查询不到任何数据!");
				}
				product.setBuySpecInfo(spec);
				//减少以后的逻辑判断，规格的价格等同于商品的价格
				if(activityStart==1&&spec.getActivityPrice()!=null&&spec.getActivityPrice()>0){
					product.setNowPrice(spec.getActivityPrice()+"");
				}else{
				product.setNowPrice(spec.getSpecPrice());
				}
			}
			
			if(product.getExchangeScore()==0){
				product.setTotal0(df.format(product.getBuyCount() * Double.valueOf(product.getNowPrice())));
			}else{
				product.setTotalExchangeScore(product.getBuyCount() * product.getExchangeScore());
				product.setTotal0("0.00");
			}
			set.add(product.getCompID());
		}
		String province="";
		String city="";
		String area="";
		
		//加载配送信息
		Address add = new Address();
		add.setAccount(acc.getAccount());
		List<Address> addressList = addressService.selectList(add);
		cartInfo.setAddressList(addressList);
		cartInfo.setDefaultAddessID(null);
		if(addressList!=null && addressList.size()>0){
//			boolean exist = false;
			for(int i=0;i<addressList.size();i++){
				Address addItem = addressList.get(i);
				if(StringUtils.isNotBlank(addItem.getIsdefault()) && addItem.getIsdefault().equals("y")){
					cartInfo.setDefaultAddessID(addItem.getId());
					province=addItem.getProvince();
					city=addItem.getCity();
					area=addItem.getArea();
					break;
				}
			}
		}
		/**
		 * 加载商品的物流信息
		 */
		List<Product> productList=new ArrayList<Product>();
		for (int i = 0; i < cartInfo.getProductList().size(); i++) {
			Product product=cartInfo.getProductList().get(i);
			
			/*if(StringUtils.isNotBlank(product.getActivityID())){
				product.product_sorry_str ="本品为活动商品!请选择直接购买!";
				getRequest().setAttribute("message", "包含活动商品!请选择直接购买!");
				getRequest().getRequestDispatcher("/cart!cart.action").forward(getRequest(), getResponse());
				return null;
			}*/
			String productID=product.getId();
			int buyCount=product.getBuyCount();
			Map<String,String> logisMap=getLogistics(buyCount,productID,province,city,area);
			product.setLogisMap(logisMap);
			productList.add(product);
		}
		cartInfo.setProductList(productList);
		getRequest().setAttribute("userMoney", acc.getMoney());
		
		return "confirmOrder";
	}
	/**
	 * 立即支付订单确认
	 * 何南
	 * @return
	 */
	
	public String confirmOrderNow() throws Exception{
		logger.error("confirmOrder..");
		DecimalFormat df = new DecimalFormat("0.00");
		String uuid = this.getRequest().getParameter("uuid");
		Account acc = null;
		if(StringUtils.isNotBlank(uuid)){
			acc = new Account();
			acc.setUuid(uuid);
			acc = accountService.selectOne(acc);
			if(acc!=null){
				getSession().setAttribute(FrontContainer.USER_INFO, acc);
			}
		}
		acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
//			getSession().getAttribute(FrontContainer);
			return "toLogin";
		
		} 
		
		String buyCount1 = this.getRequest().getParameter("inputBuyNum");
		int buyCount = Integer.valueOf(buyCount1);
		String productId = this.getRequest().getParameter("productID");
		getRequest().setAttribute("inputBuyNum", buyCount1);
		getRequest().setAttribute("productID", productId);
		if(StringUtils.isBlank(productId)){
			
			CartInfo cartInfo=(CartInfo) getSession().getAttribute(FrontContainer.myBuyNow);
			
			if(cartInfo!=null){
				
				//加载配送信息
				Address add = new Address();
				add.setAccount(acc.getAccount());
				List<Address> addressList = addressService.selectList(add);
				cartInfo.setAddressList(addressList);
				if(addressList!=null && addressList.size()>0){
					for(int i=0;i<addressList.size();i++){
						Address addItem = addressList.get(i);
						if(StringUtils.isNotBlank(addItem.getIsdefault()) && addItem.getIsdefault().equals("y")){
							cartInfo.setDefaultAddessID(addItem.getId());
							break;
						}
					}
				}
				getSession().setAttribute(FrontContainer.myBuyNow,cartInfo);
				getRequest().setAttribute("userMoney", acc.getMoney());
				return "confirmOrderNow";
			}
		}
		if(StringUtils.isEmpty(productId) || buyCount<=0){
			throw new NullPointerException("参数错误！");
		}
		ProductStockInfo momeryStockInfo = SystemManager.productStockMap.get(productId);
		if(momeryStockInfo==null){
			throw new RuntimeException("商品已卖完或已下架，请联系站点管理员!");
		}
		
		getSession().setAttribute(FrontContainer.myBuyNow,null);
		CartInfo cartInfo =  new CartInfo();
		if(cartInfo==null){
			cartInfo = new CartInfo();
		}
		//getSession().setAttribute(FrontContainer.myCart,cartInfo);
		
		//检查指定的产品是否已购买到购物车，购买了则数量++，否则查询后添加到购物车
		boolean exists = false;
		for(int i=0;i<cartInfo.getProductList().size();i++){
			Product p = cartInfo.getProductList().get(i);
			if(p.getId().equals(productId)){
				p.setBuyCount(p.getBuyCount()+buyCount);
				logger.error("商品已购买，只对数量进行++，总数="+p.getBuyCount());
				
				if(p.getExchangeScore() > 0){
					p.setTotal0("0.00");
					p.setTotalExchangeScore(p.getBuyCount() * p.getExchangeScore());
				}else{
					p.setTotal0(df.format(p.getBuyCount() * Double.valueOf(p.getNowPrice())));
				}
				exists = true;
			}
		}
		
		super.utf8JSON();
		String buySpecID = getRequest().getParameter("specIdHidden");
		//如果购物车中部存在此商品，则添加到购物车
		if(!exists){
			Product product = new Product();
			product.setId(productId);
//			product.setStatus(1);
			product = productService.selectOne(product);
			if(product==null){
				throw new NullPointerException();
			}
			product.setBuyCount(buyCount);
			/**
			 * 如果此商品为促销活动的商品，则按照活动规则计算商品金额
			 */
			//判断是否符合活动-------------------------
			int activityStart=0;
			if(StringUtils.isNotBlank(product.getActivityID())){
				Activity activity = activityService.selectById(product.getActivityID());
				if(DateUtil.strToDatehhmmss(activity.getStartDate().substring(0,19)).before(new Date())){
					if(DateUtil.strToDatehhmmss(activity.getEndDate().substring(0,19)).after(new Date())){
					product.setPrice(product.getActivityPrice()+"");
					activityStart=1;
					
					if(activity.getMaxSellCount()>0){
						ActivityCount ac=new ActivityCount(acc.getAccount(),activity.getId(),product.getId());
						ActivityCount ac2 = activityCountService.selectOne(ac);
						int count=0;
						if(ac2!=null){
							count=ac2.getCount();
						}
						if(activity.getMaxSellCount()>0&&(product.getBuyCount()+count)>activity.getMaxSellCount()){
							getRequest().setAttribute("message", "本商品活动期内,您还能能购买的数量为:"+(activity.getMaxSellCount()-count));
							getRequest().getRequestDispatcher("/szmyMobileProductActionFront!product?e.id="+productId).forward(getRequest(), getResponse());
							return null;
						}
						}
					}else{
						activityStart=3;
						myCommonDao.executeSql(2, "update t_product set activityID=null,activityType=null where activityID="+activity.getId());
					}
				}
			}
			if(activityStart==3){
				getRequest().setAttribute("message", "操作失败!活动已到期,请重新购买!");
				getRequest().getRequestDispatcher("/szmyMobileProductActionFront!product?e.id="+productId).forward(getRequest(), getResponse());
				return null;
			}
			if(activityStart==1){
				if(product.getActivityPrice()!=null&&product.getActivityPrice()>0){
				product.setNowPrice(product.getActivityPrice()+"");
				}
			}
			
			/**
			 * 加载指定商品的规格信息
			 */
			if(StringUtils.isNotBlank(buySpecID)){
				Spec spec = specService.selectById(buySpecID);
				if(spec==null){
					getRequest().setAttribute("message", "根据指定的规格"+buySpecID+"查询不到任何数据!");
					getRequest().getRequestDispatcher("/szmyMobileProductActionFront!product?e.id="+productId).forward(getRequest(), getResponse());
					return null;
				}
				product.setBuySpecInfo(spec);
				//减少以后的逻辑判断，规格的价格等同于商品的价格
				if(activityStart==1&&spec.getActivityPrice()!=null&&spec.getActivityPrice()>0){
					product.setNowPrice(spec.getActivityPrice()+"");
				}else{
				product.setNowPrice(spec.getSpecPrice());
				}
			}
			
			if(product.getExchangeScore()==0){
				product.setTotal0(df.format(product.getBuyCount() * Double.valueOf(product.getNowPrice())));
			}else{
				product.setTotalExchangeScore(product.getBuyCount() * product.getExchangeScore());
				product.setTotal0("0.00");
			}
			cartInfo.getProductList().add(product);
			logger.error("添加商品到购物车!商品ID="+product.getId());
		}
		cartInfo.totalCacl();//计算购物车中商品总金额
		
		String province="0";//默认收货地址-省
		String city="0";//默认收货地址-市
		String area="0";//默认收货地址-区
		
		//加载配送信息
		Address add = new Address();
		add.setAccount(acc.getAccount());
		List<Address> addressList = addressService.selectList(add);
		cartInfo.setAddressList(addressList);
		cartInfo.setDefaultAddessID(null);
		if(addressList!=null && addressList.size()>0){
//			boolean exist = false;
			for(int i=0;i<addressList.size();i++){
				Address addItem = addressList.get(i);
				if(StringUtils.isNotBlank(addItem.getIsdefault()) && addItem.getIsdefault().equals("y")){
					cartInfo.setDefaultAddessID(addItem.getId());
					province=addItem.getProvince();
					city=addItem.getCity();
					area=addItem.getArea();
					break;
				}
			}
		}
		/**
		 * 加载商品的物流信息
		 */
		List<Product> productList=new ArrayList<Product>();
		Product product=cartInfo.getProductList().get(0);
		Map<String,String> logisMap=getLogistics(buyCount,productId,province,city,area);
		product.setLogisMap(logisMap);
		productList.add(product);
		cartInfo.setProductList(productList);
		
		
		getSession().setAttribute(FrontContainer.myBuyNow,cartInfo );
		getRequest().setAttribute("userMoney", acc.getMoney());
		
		return "confirmOrderNow";
		
	}
	/**
	 * 查询我的订单列表信息
	 */
	@Override
	public String selectList() throws Exception {
		return super.selectList();
	}

	/**
	 * 删除我的订单信息
	 */
	@Override
	public String deletes() throws Exception {
		return super.deletes();
	}
	
	/**
	 * 退订或取消指定的订单
	 * @return
	 * @throws Exception
	 */
	public String cancel() throws Exception {
		return "";
	}
	
	/**
	 * 读取指定订单的信息
	 * @return
	 */
	public String read(){
		
		return "";
	}
	
	/**
	 * 对指定的订单进行支付
	 * @return
	 */
//	public String doPay(){
//		if(1==1){
//			throw new NullPointerException();
//		}
//		
//		String orderID = getRequest().getParameter("orderID");
//		e.clear();
//		e.setId(orderID);
//		e.setStatus(Order.order_status_init);//等待付款
//		logger.error("orderid=" + orderID);
//		orderService.update(e);
//		return "doPay";
//	}
	
	/**
	 * 查看订单详情
	 * @return
	 */
	public String orderInfo(){
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null) {
			return "toLogin";
		}
		logger.error("orderInfo...");
		String id = getRequest().getParameter("id");
		if(StringUtils.isBlank(id)){
			throw new NullPointerException("订单ID不能为空！");
		}
		e=orderService.selectById(id);
		e.setReceiveTime(e.getReceiveTime().substring(0,19).replace("-", "/"));
//		//查询订单信息
//		Order order = new Order();
//		order.setId(id);
//		order.setAccount(acc.getAccount());
//		List<Order> orders = orderService.selectOrderInfo(order);
//		if(orders==null || orders.size()==0){
//			throw new NullPointerException("根据订单ID查询不到订单信息！");
//		}
//		logger.error("orders.size="+orders.size());
//		e = orders.get(0);
//		e.setOrders(orders);
		
		
		Orderdetail od=new Orderdetail();
		od.setOrderID(Integer.valueOf(id));
		List<Orderdetail> list = orderdetailService.selectList(od);
		e.setOrderdetail(list);
		
		//查询订单配送信息
		Ordership ordership = new Ordership();
		ordership.setOrderid(id);
		ordership = ordershipService.selectOne(ordership);
		if(ordership==null){
			throw new NullPointerException("根据订单ID查询不到订单配送信息！");
		}
		e.setOrdership(ordership);
		
		//查询订单物流信息
		e.setKuaid100Info(kuaidi100Helper.selectKuaidi100());
		selectLeftMenu = "orders";
		
		//评论信息
		Comment c=new Comment();
		c.setOrderID(id);
		List<Comment> clist = commentService.selectList(c);
		if(clist!=null&&clist.size()>0){
		getRequest().setAttribute("clist", clist);
		}
		return "orderInfo";
	}
	/**
	 * 删除订单
	 * @return
	 */
	
	public String deleteOrder(){
		
		  try {
		   if(StringUtils.isBlank(e.getId())){
			   jsonStr="2";
			}else{
				Order o = orderService.selectById(e.getId());
				if(o==null){
					jsonStr="2";
				}else{
					if((o.getStatus().equals(net.jeeshop.services.common.Order.order_status_init)&&o.getPaystatus().equals(net.jeeshop.services.common.Order.order_paystatus_n))||o.getStatus().equals(net.jeeshop.services.common.Order.order_status_file)||o.getStatus().equals(net.jeeshop.services.common.Order.order_status_cancel)){
						orderService.delete(e);
						insertOrderlog(e.getId(), "【订单删除】");
						jsonStr="1";
					}else{
						jsonStr="2";
					}
				}
			}
		   
		  } catch (Exception e) {
			  logger.error("deleteOrder..."+e);
		  }
		return "toJson";
	}
	
	/**
	 * ajax取消订单
	 * @return
	 */
	public String cancelOrder(){
		
		try {
			if(StringUtils.isBlank(e.getId())){
				jsonStr="2";
			}else{
				Order o = orderService.selectById(e.getId());
				if(o==null){
					jsonStr="2";
				}else{
					if((o.getStatus().equals(net.jeeshop.services.common.Order.order_status_init)&&o.getPaystatus().equals(net.jeeshop.services.common.Order.order_paystatus_n))||o.getStatus().equals(net.jeeshop.services.common.Order.order_status_file)||o.getStatus().equals(net.jeeshop.services.common.Order.order_status_cancel)){
						e.setStatus(net.jeeshop.services.common.Order.order_status_cancel);
						orderService.update(e);
						insertOrderlog(e.getId(), "【订单取消】");
						jsonStr="1";
					}
					
				}
			}
			
		} catch (Exception e) {
			logger.error("deleteOrder..."+e);
		}
		return "toJson";
	}
	
	/**
	 * 插入订单操作日志
	 * @param orderid	订单ID
	 * @param content	日志内容
	 */
	private void insertOrderlog(String orderid,String content) {
		Account acc = (Account)getSession().getAttribute(FrontContainer.USER_INFO);
		Orderlog orderlog = new Orderlog();
		orderlog.setOrderid(orderid);//订单ID
		orderlog.setAccount(acc.getAccount());//操作人账号
		orderlog.setContent(content);//日志内容
		orderlog.setAccountType(net.jeeshop.services.common.Orderlog.orderlog_accountType_m);
		orderlogService.insert(orderlog);
	}

	/**
	 * 跳转支付方式
	 * @return
	 */
	public String toPayStyleNow(){
		String type = getRequest().getParameter("type");
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
//			getSession().getAttribute(FrontContainer);
			return "toLogin";
		} 
		if(type.equals("buyNow")){
		CartInfo cartInfo=(CartInfo) getSession().getAttribute(FrontContainer.myBuyNow);
		getSession().setAttribute(FrontContainer.myBuyNow,cartInfo);
		}else{
		CartInfo cartInfo=(CartInfo) getSession().getAttribute(FrontContainer.myCart);
		getSession().setAttribute(FrontContainer.myCart,cartInfo);	
		}
		String buyCount1 = this.getRequest().getParameter("inputBuyNum");
		String productId = this.getRequest().getParameter("productID");
		getRequest().setAttribute("inputBuyNum", buyCount1);
		getRequest().setAttribute("productID", productId);
		getRequest().setAttribute("type", type);
		return "toPayStyle";
	}

	/**
	 * 修改区域重新计算运费
	 * @throws Exception
	 */
	public String selectLogistics() throws Exception{
		int buyCount=Integer.parseInt(getRequest().getParameter("buyCount"));
		String productID=getRequest().getParameter("productID");
		String province=getRequest().getParameter("province");
		String city=getRequest().getParameter("city");
		String area=getRequest().getParameter("area");
		Map<String,String> logisMap=getLogistics(buyCount,productID,province,city,area);
		
		JSONArray json = JSONArray.fromObject(logisMap);
		String jsonStr = json.toString();
		super.write(jsonStr);
		
		return null;
	}
	/**
	 * 计算运费
	 * 靳友斌
	 */
	public Map<String,String> getLogistics(int buyCount,String productID,String province,String city,String area) throws Exception{
		Logistics logistics=logisticsService.selectLogisticsByProductId(productID);
		Map<String,String> logisMap=new HashMap<String, String>();
		if("0".equals(province)&&"0".equals(city)&&"0".equals(area)){
			province="000000";
			city="000000";
			area="000000";
		}
		if (logistics!=null) {
			String isfree=logistics.getIsfree();
			String logisticsid=logistics.getId();
			if("1".equals(isfree)){//包邮
				logisMap.put("type", "包邮");//运送方式
				logisMap.put("price", "0");//费用
			}else if("2".equals(isfree)){//自定义运费
				
				Logistics_free lf=new Logistics_free();
				lf.setLogisticsid(Integer.parseInt(logisticsid));
				lf.setProvince(province);
				lf.setCity(city);
				lf.setArea(area);
				Logistics_price lp=new Logistics_price();
				lp.setLogisticsid(Integer.parseInt(logisticsid));
				lp.setProvince(province);
				lp.setCity(city);
				lp.setArea(area);
				//根据默认地区查询是否有包邮优惠
				List<Logistics_free> freeList=this.logistics_freeService.selectFreeByArea(lf);
				//根据默认地区查询运费
				List<Logistics_price> priceList=this.logistics_priceService.selectPriceByArea(lp);
				//查询默认运费
				List<Logistics_price> defaultPrice=this.logistics_priceService.selectListByLogid(lp);
				
				//如果该商品存在优惠条件
				if(freeList.size()!=0){
					//如果购买的数量满足条件
					if(freeList.get(0).getConditions()<=buyCount){
						logisMap.put("type", "包邮");//运送方式
						logisMap.put("price", "0");//费用
					}else{//如果不满足，使用自定义运费
						//如果买家所在地区有自定义运费
						if(priceList.size()!=0){
							if (priceList.get(0).getType()==1) {
								logisMap.put("type", "快递");//运送方式
							}else{
								logisMap.put("type", "物流");//运送方式
							}
							//计算费用
							//如果购买的数量满足首件条件
							int firstnum=Integer.parseInt(priceList.get(0).getFirstnum());
							double firstprice=Double.parseDouble(priceList.get(0).getFirstprice());
							int extendnum=Integer.parseInt(priceList.get(0).getExtendnum());
							double extendprice=Double.parseDouble(priceList.get(0).getExtendprice());
							if(buyCount<=firstnum){
								logisMap.put("price", String.valueOf((int)firstprice));//费用
							}else{
								int extend=buyCount-firstnum;//超出数量
								int twice=extend%extendnum;
								if(twice>0){
									twice=extend/extendnum+1;
								}else{
									twice=extend/extendnum;
								}
								double c=extendprice * twice;
								logisMap.put("price", String.valueOf((int)(c+firstprice)));//费用
							}
						}else{//如果没有，使用默认运费
							if (defaultPrice.get(0).getType()==1) {
								logisMap.put("type", "快递");//运送方式
							}else{
								logisMap.put("type", "物流");//运送方式
							}
							//计算费用
							//如果购买的数量满足首件条件
							int firstnum=Integer.parseInt(defaultPrice.get(0).getFirstnum());
							double firstprice=Double.parseDouble(defaultPrice.get(0).getFirstprice());
							int extendnum=Integer.parseInt(defaultPrice.get(0).getExtendnum());
							double extendprice=Double.parseDouble(defaultPrice.get(0).getExtendprice());
							if(buyCount<=firstnum){
								logisMap.put("price", String.valueOf((int)firstprice));//费用
							}else{
								int extend=buyCount-firstnum;//超出数量
								///////// 如果被整除不用+1
								int twice=extend%extendnum;
								if(twice>0){
									twice=extend/extendnum+1;
								}else{
									twice=extend/extendnum;
								}
								double c=extendprice * twice;
								logisMap.put("price", String.valueOf((int)(c+firstprice)));//费用
							}
						}
					}
				}else{
					if(priceList.size()!=0){
						if (priceList.get(0).getType()==1) {
							logisMap.put("type", "快递");//运送方式
						}else{
							logisMap.put("type", "物流");//运送方式
						}
						//计算费用
						//如果购买的数量满足首件条件
						int firstnum=Integer.parseInt(priceList.get(0).getFirstnum());
						double firstprice=Double.parseDouble(priceList.get(0).getFirstprice());
						int extendnum=Integer.parseInt(priceList.get(0).getExtendnum());
						double extendprice=Double.parseDouble(priceList.get(0).getExtendprice());
						if(buyCount<=firstnum){
							logisMap.put("price", String.valueOf((int)firstprice));//费用
						}else{
							int extend=buyCount-firstnum;//超出数量
							int twice=extend%extendnum;
							if(twice>0){
								twice=extend/extendnum+1;
							}else{
								twice=extend/extendnum;
							}
							double c=extendprice * twice;
							logisMap.put("price", String.valueOf((int)(c+firstprice)));//费用
						}
					}else{//如果没有，使用默认运费
						if (defaultPrice.get(0).getType()==1) {
							logisMap.put("type", "快递");//运送方式
						}else{
							logisMap.put("type", "物流");//运送方式
						}
						//计算费用
						//如果购买的数量满足首件条件
						int firstnum=Integer.parseInt(defaultPrice.get(0).getFirstnum());
						double firstprice=Double.parseDouble(defaultPrice.get(0).getFirstprice());
						int extendnum=Integer.parseInt(defaultPrice.get(0).getExtendnum());
						double extendprice=Double.parseDouble(defaultPrice.get(0).getExtendprice());
						if(buyCount<=firstnum){
							logisMap.put("price", String.valueOf((int)firstprice));//费用
						}else{
							int extend=buyCount-firstnum;//超出数量
							int twice=extend%extendnum;
							if(twice>0){
								twice=extend/extendnum+1;
							}else{
								twice=extend/extendnum;
							}
							double c=extendprice * twice;
							logisMap.put("price", String.valueOf((int)(c+firstprice)));//费用
						}
					}
				}
			}
		}else{
			logisMap.put("type", "无");
			logisMap.put("price", "0");
		}
		getRequest().setAttribute("logisMap", logisMap);
		
		return logisMap;
	}
	/**
	 * 支付立即购买订单
	 * 何南
	 * @return
	 * @throws Exception
	 */
	public String payNow() throws Exception {
		DecimalFormat df = new DecimalFormat("0.00");
		logger.error("==insertAndPay=="+e.getSelectAddressID()+",expressCode = "+e.getExpressCode());
		String logisPrice = getRequest().getParameter("e.logisPrice");
		Account account = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (account == null || StringUtils.isBlank(account.getAccount())) {
			return "toLogin";
		}
		int yhqzf=0;
		//int jfzf=0;
		if(StringUtils.isBlank(e.getSelectAddressID()) || StringUtils.isBlank(e.getExpressCode())){
//			throw new NullPointerException("非法请求！");
			getRequest().setAttribute("message", "非法请求！");
			getRequest().getRequestDispatcher("/mAProduct/toLimitTime.html").forward(getRequest(), getResponse());
			return null ;
		}

		//从session中获取用户购买的商品列表
		CartInfo cartInfo = (CartInfo) getSession().getAttribute(FrontContainer.myBuyNow);
		if (cartInfo == null || cartInfo.getProductList().size() == 0) {
			//throw new NullPointerException("商品品数据丢失!");
			getRequest().getRequestDispatcher("/szmyMobileAccountActionFront!toShowOrders").forward(getRequest(), getResponse());
			return null ;
		}
		int activityStart=0;
		String activityID=null;
		String activityType=null;
		//检测商品是否都有库存,如果没有库存需要提醒用户
		synchronized (SystemManager.product_stock_lock) {
				boolean no = false;
				String pid="";
				Product product = cartInfo.getProductList().get(0);
				pid=product.getId();
				
				//判断库存--------------------------------------------
				if(SystemManager.productStockMap.get(product.getId())==null){
					no = true;
				}
				
				ProductStockInfo stockInfo = SystemManager.productStockMap.get(product.getId());
				if(product.getBuyCount() > stockInfo.getStock()){
					//如果用户购买的某个商品的数量大于该商品的库存数，则提示
					no = true;
				}
				if(product.getBuySpecInfo()!=null){//判断规格库存
					Integer sto = stockInfo.getSpecMap().get(product.getBuySpecInfo().getSpecSize());
					if(sto==null||product.getBuyCount() >sto){
						no = true;
					}
				}
			
				//库存不足，则刷最后支付页面，提示用户某些商品的库存不足，请重新选购
				if(no){
					getSession().setAttribute(FrontContainer.myBuyNow,null);
					getRequest().setAttribute("message", "商品库存不足！请重新选购！");
					getRequest().getRequestDispatcher("/szmyMobileProductActionFront!product?e.id="+pid).forward(getRequest(), getResponse());
					return null;
				}
				//判断库存--------------------------------------------
				
			//判断是否符合活动-------------------------
			
			/*if(StringUtils.isNotBlank(product.getActivityID())){
				Activity activity = activityService.selectById(product.getActivityID());
				if(DateUtil.strToDatehhmmss(activity.getStartDate().substring(0,19)).before(new Date())){
					if(DateUtil.strToDatehhmmss(activity.getEndDate().substring(0,19)).after(new Date())){
					product.setPrice(product.getActivityPrice()+"");
					activityStart=1;
					activityID=activity.getId();
					activityType=activity.getActivityType();
					if(activity.getMaxSellCount()>0){
						ActivityCount ac=new ActivityCount(account.getAccount(),activity.getId(),product.getId());
						ActivityCount ac2 = activityCountService.selectOne(ac);
						int count=0;
						if(ac2!=null){
							count=ac2.getCount();
						}
						if(activity.getMaxSellCount()>0&&(product.getBuyCount()+count)>activity.getMaxSellCount()){
							getRequest().setAttribute("message", "本商品活动期内,您还能能购买的数量为:"+(activity.getMaxSellCount()-count));
							getRequest().getRequestDispatcher("/product!product?e.id="+product.getId()).forward(getRequest(), getResponse());
							return null;
						}
						if(ac2!=null){
							ac2.setCount(product.getBuyCount()+count);
							activityCountService.update(ac2);
						}else{
							ac.setCount(product.getBuyCount()+count);
							activityCountService.insert(ac);
						}
						
						}
					}else{
						activityStart=3;
						myCommonDao.executeSql(2, "update t_product set activityID=null,activityType=null where activityID="+activity.getId());
					}
				}
			}*/
			if(activityStart==3){
				getRequest().setAttribute("message", "操作失败!活动已到期,请重新购买!");
				getRequest().getRequestDispatcher("/szmyMobileProductActionFront!product?e.id="+product.getId()).forward(getRequest(), getResponse());
				return null;
			}
			//判断是否符合活动-------------------------
			
			
				//如果检查没有出现库存不足的情况，则进行砍库存操作---
					stockInfo.setStock(stockInfo.getStock() - product.getBuyCount());
					stockInfo.setChangeStock(true);
					
					if(product.getBuySpecInfo()!=null){//减规格库存
						Integer sto = stockInfo.getSpecMap().get(product.getBuySpecInfo().getSpecSize());
						stockInfo.getSpecMap().put(product.getBuySpecInfo().getSpecSize(), sto- product.getBuyCount());

						Spec spec=new Spec();
						spec.setProductID(product.getId());
						spec.setSpecSize(product.getBuySpecInfo().getSpecSize());
						spec.setSpecStock((sto- product.getBuyCount())+"");
						specService.updateStock(spec);
					}
					
					SystemManager.productStockMap.put(product.getId(),stockInfo);
					//真实减库存
					Product pro=new Product();
					pro.setId(product.getId());
					pro.setStock(stockInfo.getStock());
					productService.updateStockAfterPaySuccess(pro);
				//如果检查没有出现库存不足的情况，则进行砍库存操作---
			
		}
		
		//获取配送方式
		/*Express express = SystemManager.expressMap.get(e.getExpressCode());
		if(express==null){
			throw new NullPointerException("没有编码为"+e.getExpressCode()+"的配送方式！本次请求视为非法！");
		}*/
		
		
		//创建订单明细集合
			Product product = cartInfo.getProductList().get(0);
			int score= product.getScore();//订单积分 等于订单项中每个商品赠送的积分总和

			//创建订单对象
			Order order = new Order();
			order.setQuantity(product.getBuyCount());
			order.setRebate(1);
			order.setStatus(net.jeeshop.services.common.Order.order_status_init);
			order.setPaystatus(net.jeeshop.services.common.Order.order_paystatus_n);
			order.setOtherRequirement(e.getOtherRequirement());//附加要求
			order.setCompID(product.getCompID());
			order.setCompName(product.getCompName());
			order.setAccount(account.getAccount());
			order.setRemark(product.getName());
			order.setScore(score);
			//order.setExpressCode(express.getCode());//配送方式编码
			//order.setExpressName(express.getName());//配送方式名称
			order.setAmountExchangeScore(cartInfo.getTotalExchangeScore());//订单总兑换积分。订单支付成功以后扣除
			/**
			 * 对金额进行格式化，防止出现double型数字计算造成的益出。
			 */
			order.setPtotal(df.format(Double.valueOf(product.getNowPrice())* product.getBuyCount()));//订单商品总金额
			order.setFee(df.format(Double.valueOf(logisPrice)));//订单总配送费
			order.setAmount(df.format(Double.valueOf(order.getPtotal())+Double.valueOf(logisPrice)));//订单总金额
			Double m=0.00;
			//购物券处理
			/*String syyhq = getRequest().getParameter("syyhq");
			String couponMoney = getRequest().getParameter("couponMoney");
			String[] values = getRequest().getParameterValues("couponId");
			
			
			Double m=0.00;
			if(syyhq!=null&&syyhq.equals("1")){
				if(couponMoney!=null&&Double.valueOf(couponMoney)>0){
					
					for (String s : values) {
						Coupon c = couponService.selectById(s);
						c.setStatus(Coupon.status_n);
						couponService.update(c);
						m+=c.getAmount();
					}
					
					if(m>Double.valueOf(order.getAmount())){
						yhqzf=1;
						order.setPaystatus(Order.order_status_pass);
						//发送短信
						net.jeeshop.services.manage.company.bean.Company comp = companyService.selectById(order.getCompID());
						String sms = CommonPropertiesUtil.getSMStemValue( "WAIT_SELLER_SEND_GOODS").replace("orderid", order.getId());
						String rtmsg=SendSMSUtil.sendSMS(sms,comp.getContactorTelephone());
						logger.error("支付成功发送短信--->号码："+comp.getContactorTelephone()+"，内容："+sms+",结果："+rtmsg);
						
					}
					order.setCoupon(m);
				}
			}*/
			//String integral = getRequest().getParameter("integral");
			/*if(integral!=null&&Double.valueOf(integral)>0){
					if(Double.valueOf(integral)/100>=Double.valueOf(order.getAmount())){
						//jfzf=1;
						order.setPaystatus(Order.order_paystatus_y);
						order.setStatus(Order.order_status_pass);
						
					}
					order.setUseIntegral(Double.valueOf(integral));
			}else{*/
				/*if(product.getActivityID()!=null&&!product.getActivityID().equals("")){
					Activity ac = activityService.selectById(product.getActivityID());
					if(ac.getActivityType().equals("m")){
					order.setActivityID(product.getActivityID());
					order.setIntegral(Double.valueOf((int)(Double.valueOf(order.getPtotal())/Double.valueOf(ac.getMinprice()))*ac.getExchangeScore()));
				}
			}*/
			
			orderService.insert(order);
			
			if(m>0){
				Orderpay orderpay = new Orderpay();
				orderpay.setOrderid(order.getId());
				orderpay.setPaystatus(net.jeeshop.services.common.Orderpay.orderpay_paystatus_y);
				orderpay.setPayamount(m);
				orderpay.setPaymethod(net.jeeshop.services.common.Orderpay.orderpay_paymethod_yhq);
				orderpayService.insert(orderpay);
			}
			/*if(integral!=null&&Double.valueOf(integral)>0){
				Orderpay orderpay = new Orderpay();
				orderpay.setOrderid(order.getId());
				orderpay.setPaystatus(Orderpay.orderpay_paystatus_y);
				orderpay.setPayamount(Double.valueOf(integral)/100);
				orderpay.setPaymethod(Orderpay.orderpay_paymethod_jf);
				orderpayService.insert(orderpay);
				Account ac2=new Account();
				ac2.setId(account.getId());
				Double money=(account.getMoney()==null?0.0:account.getMoney())-Double.valueOf(integral);
				if(money<0)money=0.0;
				ac2.setMoney(money);
				accountService.updateMoney(ac2);
				account.setMoney(ac2.getMoney());
			}*/
			
			Orderdetail orderdetail = new Orderdetail();
			orderdetail.setOrderID(Integer.valueOf(order.getId()));
			orderdetail.setProductID(Integer.valueOf(product.getId()));
			orderdetail.setGiftID(product.getGiftID());//商品赠品ID
			orderdetail.setPrice(product.getNowPrice());//商品现价
			orderdetail.setNumber(product.getBuyCount());//购买数
			orderdetail.setLogisType(product.getLogisMap().get("type"));//配送方式
			orderdetail.setFee(String.valueOf(Double.valueOf(logisPrice)));//配送费
			orderdetail.setProductName(product.getName());
			orderdetail.setTotal0(String.valueOf(Double.valueOf(orderdetail.getPrice()) * orderdetail.getNumber()+Double.valueOf(logisPrice)));//订单项小计
			orderdetail.setScore(product.getScore());//活的赠送的积分
			orderdetail.setActivityID(activityID);
			orderdetail.setActivityType(activityType);
			if(product.getBuySpecInfo()!=null){
				//按照规格计算
				orderdetail.setSpecInfo(product.getBuySpecInfo().getSpecSize());
				orderdetail.setPurchasePrice(product.getBuySpecInfo().getPurchasePrice());
			}else{
				orderdetail.setPurchasePrice(Double.valueOf(product.getChuChangPrice()));
			}
			orderdetailService.insert(orderdetail);
			
			/**
			 * 配送地址信息
			 */
			Ordership ordership = new Ordership();
			ordership.setOrderid(order.getId());
			
			Address address = addressService.selectById(e.getSelectAddressID());
			if(address==null){
				throw new NullPointerException("根据ID="+e.getSelectAddressID()+"查询不到配送地址信息！本次请求视为非法！");
			}
			logger.error(address.toString());
			
			String proinceName = CreateAreaUtil.getAreaMap().get(address.getProvince()).getName();
			String cityName = address.getCity()==null?"":CreateAreaUtil.getAreaMap().get(address.getCity()).getName();
			String areaName = address.getArea()==null?"": CreateAreaUtil.getAreaMap().get(address.getArea()).getName();
			ordership.setShipname(address.getName());
			ordership.setShipaddress(address.getAddress());
			ordership.setProvinceCode(address.getProvince());
			ordership.setProvince(proinceName);
			ordership.setCityCode(address.getCity());
			ordership.setCity(cityName);
			ordership.setAreaCode(address.getArea());
			ordership.setArea(areaName);
			ordership.setPhone(address.getMobile());
			ordership.setZip(address.getZip());
			ordership.setSex("1");
			logger.error(ordership.toString());
			ordershipService.insert(ordership);
			
			
			//createQuickPayInfo(order);//创建快捷支付
			
			// 记录订单创建日志
			Orderlog orderlog = new Orderlog();
			orderlog.setOrderid(String.valueOf(order.getId()));
			orderlog.setAccount(order.getAccount());
			orderlog.setContent("【创建订单】用户创建订单。订单总金额：" + order.getAmount());
			orderlog.setAccountType(net.jeeshop.services.common.Orderlog.orderlog_accountType_w);
			orderlogService.insert(orderlog);
			getSession().setAttribute(FrontContainer.myBuyNow,null);
			getRequest().setAttribute("total", order.getAmount());
			if(yhqzf==1){
				getRequest().setAttribute("message", "你已使用优惠券全额支付成功,请等待发货!");
				return "showOrders";
			}/*else 
				if(jfzf==1){
					//发送短信
					net.jeeshop.services.manage.company.bean.Company comp = companyService.selectById(order.getCompID());
					String sms = CommonPropertiesUtil.getSMStemValue( "WAIT_SELLER_SEND_GOODS").replace("orderid", order.getId());
					String rtmsg=SendSMSUtil.sendSMS(sms,comp.getContactorTelephone());
					logger.error("支付成功发送短信--->号码："+comp.getContactorTelephone()+"，内容："+sms+",结果："+rtmsg);
					getRequest().setAttribute("message", "你已使用积分全额支付成功,请等待发货!");
					return "showOrders";
				}*/else{
					this.getRequest().setAttribute("id", order.getId());
					this.getRequest().setAttribute("type", "buyNow");
					this.getRequest().setAttribute("total", order.getAmount());
					getRequest().getRequestDispatcher("/szmyMobie/payStyle.jsp").forward(getRequest(), getResponse());
//					getResponse().sendRedirect(getRequest().getContextPath()+"/szmyMobie/payStyle.jsp?id="+order.getId()+"&type=buyNow&total="+order.getAmount());
			}
			return "showOrders";
	}
	/**
	 * 购物车支付订单
	 * 何南
	 * @return
	 * @throws Exception
	 */
	public String payCart() throws Exception {
//		if(!TokenUtil.getInstance().isTokenValid(getRequest())){
//			throw new Exception("表单重复提交了！");
//		}
		//int yhqzf=0;//是否优惠券全额支付
		//int jfzf=0;//是否积分全额支付
		DecimalFormat df = new DecimalFormat("0.00");
		logger.error("==insertAndPay=="+e.getSelectAddressID()+",expressCode = "+e.getExpressCode()+",otherRequirement = " + e.getOtherRequirement());
		String selectAddressID = getRequest().getParameter("e.selectAddressID");
		Account account = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (account == null || StringUtils.isBlank(account.getAccount())) {
			return "toLogin";
		}
		
		/*if(StringUtils.isBlank(e.getSelectAddressID()) || StringUtils.isBlank(e.getExpressCode())){
			throw new NullPointerException("非法请求！");
		}*/

		//从session中获取用户购买的商品列表
		CartInfo cartInfo = (CartInfo) getSession().getAttribute(FrontContainer.myCart);
		if (cartInfo == null || cartInfo.getProductList().size() == 0) {
			throw new NullPointerException("购物车中没有可支付的商品!");
		}
		
		//检测商品是否都有库存,如果没有库存需要提醒用户
		synchronized (SystemManager.product_stock_lock) {
			boolean no = false;
			for (int i = 0; i < cartInfo.getProductList().size(); i++) {
				Product product = cartInfo.getProductList().get(i);
				if(SystemManager.productStockMap.get(product.getId())==null){
					product.product_sorry_str = "抱歉，该商品目前库存不足！";
					no = true;
				}
				
				ProductStockInfo stockInfo = SystemManager.productStockMap.get(product.getId());
				if(product.getBuyCount() > stockInfo.getStock()){
					//如果用户购买的某个商品的数量大于该商品的库存数，则提示
					product.product_sorry_str = "抱歉，该商品目前库存不足！";
					no = true;
				}
				if(product.getBuySpecInfo()!=null){//判断规格库存
					Integer sto = stockInfo.getSpecMap().get(product.getBuySpecInfo().getSpecSize());
					if(sto==null||product.getBuyCount() >sto){
						product.product_sorry_str = "抱歉，该商品目前库存不足！";
						no = true;
					}
				}
			}
			
			//库存不足，则刷最后支付页面，提示用户某些商品的库存不足，请重新选购
			if(no){
				logger.error("某些商品库存不足！请重新选购！");
				getRequest().setAttribute("message", "某些商品库存不足！请重新选购！");
				getRequest().getRequestDispatcher("/szmyMobileCartActionFront!cart.action").forward(getRequest(), getResponse());
				return null;
			}
			
			if(!no){
				//如果检查没有出现库存不足的情况，则进行砍库存操作
				for (int i = 0; i < cartInfo.getProductList().size(); i++) {
					Product product = cartInfo.getProductList().get(i);
					
					//最后确认订单判断活动日期和数量
					if(StringUtils.isNotBlank(product.getActivityID())){
						product.product_sorry_str ="本品为活动商品!请直接购买!";
						getRequest().setAttribute("message", "包含活动商品!请选择直接购买!");
						getRequest().getRequestDispatcher("/szmyMobileCartActionFront!cart.action").forward(getRequest(), getResponse());
						return null;
						/*
						Activity activity = activityService.selectById(product.getActivityID());
						if(DateUtil.strToDatehhmmss(activity.getStartDate().substring(0,19)).after(new Date())){
							if(activity.getActivityType().equals("ms")){
								product.product_sorry_str ="活动还未开始!请等待!";
								getRequest().setAttribute("message", "活动还未开始!请等待!");
								getRequest().getRequestDispatcher("/cart!cart.action").forward(getRequest(), getResponse());
								return null;
							}
						}
						if(DateUtil.strToDatehhmmss(activity.getEndDate().substring(0,19)).before(new Date())){
							product.product_sorry_str ="活动已经结束!";
							getRequest().setAttribute("message", "活动已经结束!");
							getRequest().getRequestDispatcher("/cart!cart.action").forward(getRequest(), getResponse());
							return null;
						}
						ActivityCount ac=new ActivityCount(account.getAccount(),activity.getId(),product.getId());
						ActivityCount ac2 = activityCountService.selectOne(ac);
						int count=0;
						if(ac2!=null){
							count=ac2.getCount();
						}
						if(activity.getMaxSellCount()>0&&(product.getBuyCount()+count)>activity.getMaxSellCount()){
							product.product_sorry_str ="您选择的数量超出本次活动您能购买的最大数量!";
							getRequest().setAttribute("message", "您选择的数量超出活动最大购买数量!");
							getRequest().getRequestDispatcher("/cart!cart.action").forward(getRequest(), getResponse());
							return null;
						}
						if(ac2!=null){
							ac2.setCount(product.getBuyCount()+count);
							activityCountService.update(ac2);
						}else{
							ac.setCount(product.getBuyCount()+count);
							activityCountService.insert(ac);
						}
						*/
					}
					
					
					ProductStockInfo stockInfo = SystemManager.productStockMap.get(product.getId());
					stockInfo.setStock(stockInfo.getStock() - product.getBuyCount());
					stockInfo.setChangeStock(true);
					
					if(product.getBuySpecInfo()!=null){//减规格库存
						Integer sto = stockInfo.getSpecMap().get(product.getBuySpecInfo().getSpecSize());
						stockInfo.getSpecMap().put(product.getBuySpecInfo().getSpecSize(), sto- product.getBuyCount());
						
						Spec spec=new Spec();
						spec.setProductID(product.getId());
						spec.setSpecSize(product.getBuySpecInfo().getSpecSize());
						spec.setSpecStock((sto- product.getBuyCount())+"");
						specService.updateStock(spec);
						
					}
					
					SystemManager.productStockMap.put(product.getId(),stockInfo);
					//真实减库存
					Product pro=new Product();
					pro.setId(product.getId());
					pro.setStock(stockInfo.getStock());
					productService.updateStockAfterPaySuccess(pro);
				}
			}
			
		}
		
		//获取配送方式
		/*Express express = SystemManager.expressMap.get(e.getExpressCode());
		if(express==null){
			throw new NullPointerException("没有编码为"+e.getExpressCode()+"的配送方式！本次请求视为非法！");
		}*/
		
		Set<String> set=new HashSet<String>();
		
		for (int i = 0; i < cartInfo.getProductList().size(); i++) {
			Product product = cartInfo.getProductList().get(i);
			set.add(product.getCompID());
		}
		String pid =null;
		double amount =0d;
		/*不同商家暂时不做处理
		if(set.size()>1){
		Order po = new Order();
		po.setAccount(account.getAccount());
		po.setAmount(Double.valueOf(cartInfo.getAmount())+express.getFee()*set.size()+"");
		po.setCreatedate(DateUtil.dateToStr(new Date()));
		po.setStatus(Order.order_status_init);
		po.setIsParent(Order.order_isParent_y);
		orderService.insert(po);
		pid = po.getId();
		}
		*/
		//创建订单明细集合
		
		for (String compid : set) {
			
			
		
			//创建订单对象
			Order order = new Order();
			order.setAccount(account.getAccount());
			//order.setExpressCode(express.getCode());//配送方式编码
			/*order.setExpressName(express.getName());//配送方式名称*/
			//order.setFee(df.format(express.getFee()));//订单配送费
			//order.setAmountExchangeScore(cartInfo.getTotalExchangeScore());//订单总兑换积分。订单支付成功以后扣除
			order.setRebate(1);
			order.setStatus(net.jeeshop.services.common.Order.order_status_init);
			order.setPaystatus(net.jeeshop.services.common.Order.order_paystatus_n);
			//order.setOtherRequirement(e.getOtherRequirement());//附加要求
			order.setCompID(compid);
			order.setParentId(pid);
			
			
			orderService.insert(order);
			String oid=order.getId();
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//配送信息
			Ordership ordership = new Ordership();
			ordership.setOrderid(oid);
			Address address = addressService.selectById(selectAddressID);
			if(address==null){
				throw new NullPointerException("根据ID="+selectAddressID+"查询不到配送地址信息！本次请求视为非法！");
			}
			logger.error(address.toString());
			
			String proinceName = CreateAreaUtil.getAreaMap().get(address.getProvince()).getName();
			String cityName =address.getCity()==null?"": CreateAreaUtil.getAreaMap().get(address.getCity()).getName();
			String areaName = address.getArea()==null?"": CreateAreaUtil.getAreaMap().get(address.getArea()).getName();
			ordership.setShipname(address.getName());
			ordership.setShipaddress(address.getAddress());
			ordership.setProvinceCode(address.getProvince());
			ordership.setProvince(proinceName);
			ordership.setCityCode(address.getCity());
			ordership.setCity(cityName);
			ordership.setAreaCode(address.getArea());
			ordership.setArea(areaName);
			ordership.setPhone(address.getMobile());
			ordership.setZip(address.getZip());
			ordership.setSex("1");
			logger.error(ordership.toString());
			ordershipService.insert(ordership);
			
			int score = 0;//订单积分 等于订单项中每个商品赠送的积分总和
			Double ptotal=0.00;//订单商品总金额
			int pnum=0;//订单商品总金额
			Double yunfei=0.00;//订单商品总运费
		///////////////////////////////////////////////////////////////////////////////////////////////////////////	
		for (int i = 0; i < cartInfo.getProductList().size(); i++) {
			Product product = cartInfo.getProductList().get(i);
			if(compid.equals(product.getCompID())){
			pnum+=product.getBuyCount();
			score+= product.getScore();//计算积分
			ptotal+=Double.valueOf(product.getNowPrice()) * product.getBuyCount();////订单商品总金额
			yunfei+=Double.valueOf(product.getLogisMap().get("price"));//订单商品总运费
			order.setRemark(product.getName()+(pnum>1?"...":""));
			order.setCompName(product.getCompName());
			
			//订单中各个商品信息
			Orderdetail orderdetail = new Orderdetail();
			orderdetail.setProductID(Integer.valueOf(product.getId()));
			orderdetail.setGiftID(product.getGiftID());//商品赠品ID
			orderdetail.setPrice(product.getNowPrice());//商品现价
			orderdetail.setNumber(product.getBuyCount());//购买数
//			orderdetail.setFee(String.valueOf(express.getFee()));//配送费
			orderdetail.setLogisType(product.getLogisMap().get("type"));//配送方式
			orderdetail.setFee(product.getLogisMap().get("price"));//配送费
			orderdetail.setProductName(product.getName());
			orderdetail.setTotal0(String.valueOf(Double.valueOf(orderdetail.getPrice()) * orderdetail.getNumber()));//订单项小计
			orderdetail.setScore(product.getScore());//活的赠送的积分
			if(product.getBuySpecInfo()!=null){
				//按照规格计算
				orderdetail.setSpecInfo(product.getBuySpecInfo().getSpecSize());
				orderdetail.setPurchasePrice(product.getBuySpecInfo().getPurchasePrice());
			}else{
				orderdetail.setPurchasePrice(Double.valueOf(product.getChuChangPrice()));
			}
			orderdetail.setOrderID(Integer.valueOf(oid));
			orderdetailService.insert(orderdetail);
			
			//createPayInfo(order,ordership);
			}
		}
		order.setExpressName(cartInfo.getProductList().get(0).getLogisMap().get("type"));//配送方式名称
		order.setFee(df.format(yunfei));//订单配送费
		//c处理订单信息
		order.setQuantity(pnum);
		order.setPtotal(df.format(ptotal));//订单商品总金额
		//order.setAmount(df.format(ptotal+express.getFee()));//订单总金额
		order.setAmount(df.format(ptotal+yunfei));//订单总金额
		order.setScore(score);//订单总积分
		
		
		//购物券处理
		//String syyhq = getRequest().getParameter("syyhq");
	//	String couponMoney = getRequest().getParameter("couponMoney");
		//String[] values = getRequest().getParameterValues("couponId");
		
		
		
		/*if(syyhq!=null&&syyhq.equals("1")){
			if(couponMoney!=null&&Double.valueOf(couponMoney)>0){
				Double m=0.00;
				for (String s : values) {
					Coupon c = couponService.selectById(s);
					c.setRemaining(0.00);
					c.setStatus(Coupon.status_n);
					c.setRemark("已使用");
					couponService.update(c);
					m+=c.getAmount();
				}
				Orderpay orderpay = new Orderpay();
				orderpay.setOrderid(order.getId());
				orderpay.setPaystatus(Orderpay.orderpay_paystatus_y);
				orderpay.setPayamount(m);
				orderpay.setPaymethod(Orderpay.orderpay_paymethod_yhq);
				orderpayService.insert(orderpay);
				
				
				if(m>Double.valueOf(order.getAmount())){
				//	yhqzf=1;
					order.setPaystatus(Order.order_status_pass);
					//发送短信
					//net.jeeshop.services.manage.company.bean.Company comp = companyService.selectById(compid);
					String sms = CommonPropertiesUtil.getSMStemValue( "WAIT_SELLER_SEND_GOODS").replace("orderid", order.getId());
					String rtmsg=SendSMSUtil.sendSMS(sms,comp.getContactorTelephone());
					logger.error("支付成功发送短信--->号码："+comp.getContactorTelephone()+"，内容："+sms+",结果："+rtmsg);
					
				}
				order.setCoupon(m);
			}
		}*/
		
		
		//活动积分
		/*String integral = getRequest().getParameter("integral");
		if(integral!=null&&Double.valueOf(integral)>0){
				if((Double.valueOf(integral)/100)>=Double.valueOf(order.getAmount())){
					jfzf=1;
					order.setPaystatus(Order.order_paystatus_y);
					order.setStatus(Order.order_status_pass);
					//发送短信
					net.jeeshop.services.manage.company.bean.Company comp = companyService.selectById(order.getCompID());
					String sms = CommonPropertiesUtil.getSMStemValue( "WAIT_SELLER_SEND_GOODS").replace("orderid", order.getId());
					String rtmsg=SendSMSUtil.sendSMS(sms,comp.getContactorTelephone());
					logger.error("支付成功发送短信--->号码："+comp.getContactorTelephone()+"，内容："+sms+",结果："+rtmsg);
				}
				order.setUseIntegral(Double.valueOf(integral));
				Account ac=new Account();
				ac.setId(account.getId());
				ac.setMoney(account.getMoney()-order.getUseIntegral());
				accountService.update(ac);
				account.setMoney(account.getMoney()-order.getUseIntegral());
				getSession().setAttribute(FrontContainer.USER_INFO,account);
				
				Orderpay orderpay = new Orderpay();
				orderpay.setOrderid(order.getId());
				orderpay.setPaystatus(Orderpay.orderpay_paystatus_y);
				orderpay.setPayamount(Double.valueOf(integral)/100);
				orderpay.setPaymethod(Orderpay.orderpay_paymethod_jf);
				orderpayService.insert(orderpay);
				Account ac2=new Account();
				ac2.setId(account.getId());
				Double money=(account.getMoney()==null?0.0:account.getMoney())-Double.valueOf(integral);
				if(money<0)money=0.0;
				ac2.setMoney(money);
				accountService.updateMoney(ac2);
				account.setMoney(ac2.getMoney());
				
				
		}else{
			 Product product = cartInfo.getProductList().get(0);
			if(product.getActivityID()!=null&&!product.getActivityID().equals("")){
				Activity ac = activityService.selectById(product.getActivityID());
				if(ac.getActivityType().equals("m")){
				order.setActivityID(product.getActivityID());
				order.setIntegral(Double.valueOf((int)(Double.valueOf(order.getPtotal())/Double.valueOf(ac.getMinprice()))*ac.getExchangeScore()));
				}
			}
		}
		*/
		orderService.update(order);
		
		//createQuickPayInfo(order);
		
		// 记录订单创建日志
		Orderlog orderlog = new Orderlog();
		orderlog.setOrderid(String.valueOf(oid));
		orderlog.setAccount(order.getAccount());
		orderlog.setContent("【创建订单】用户创建订单。订单总金额：" + order.getAmount());
		orderlog.setAccountType(net.jeeshop.services.common.Orderlog.orderlog_accountType_w);
		orderlogService.insert(orderlog);
		
		if(pid==null)pid=oid;//非合并订单
		amount+= Double.parseDouble(order.getAmount());
		
	}
		//清空购物车
		cartInfo.clear();
		cartInfo = null;
		getSession().setAttribute(FrontContainer.myCart,null);

		if(set.size()>1){//不同商家合并订单,暂时不做处理,直接跳转到订单列表
			getRequest().getRequestDispatcher("/szmyMobileAccountActionFront!toShowOrders?flag=2").forward(getRequest(), getResponse());
		}else{//非合并订单,直接跳转到支付
			this.getRequest().setAttribute("id", pid);
			this.getRequest().setAttribute("type", "cart");
			this.getRequest().setAttribute("total", amount);
			getRequest().getRequestDispatcher("/szmyMobie/payStyle.jsp").forward(getRequest(), getResponse());
		}
		return null;
	}
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public AppUserService getAppUserService() {
		return appUserService;
	}

	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public OrderdetailService getOrderdetailService() {
		return orderdetailService;
	}

	public void setOrderdetailService(OrderdetailService orderdetailService) {
		this.orderdetailService = orderdetailService;
	}

	public AddressService getAddressService() {
		return addressService;
	}

	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	public OrdershipService getOrdershipService() {
		return ordershipService;
	}

	public void setOrdershipService(OrdershipService ordershipService) {
		this.ordershipService = ordershipService;
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

	public void setProfitService(ProfitService profitService) {
		this.profitService = profitService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	

}
