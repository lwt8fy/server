package net.jeeshop.web.action.front.orders;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.FrontContainer;
import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.CreateAreaUtil;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.Profit;
import net.jeeshop.services.front.account.AccountService;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.front.address.AddressService;
import net.jeeshop.services.front.address.bean.Address;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.front.company.CompanyService;
import net.jeeshop.services.front.company.bean.Company;
import net.jeeshop.services.front.express.bean.Express;
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
import net.jeeshop.services.manage.profit.service.ProfitService;
import net.jeeshop.services.manage.spec.SpecService;
import net.jeeshop.services.manage.spec.bean.Spec;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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
public class SzmyOrdersAction extends BaseAction<Order>{
	private static final Logger logger = LoggerFactory.getLogger(SzmyOrdersAction.class);
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";
	private final String bkSel="nybank";
	
	private AppUserService appUserService;  
	
	private AddressService addressService;
	
	private OrdershipService ordershipService;
	
	private OrderlogService orderlogService; 
	
	private  OrderpayService orderpayService;
	
	private ProfitService profitService; 
	private AccountService accountService;
	private OrderService orderService;
	private CompanyService companyService;
	private SpecService specService;
	
	
	
	
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
	

	public void setSpecService(SpecService specService) {
		this.specService = specService;
	}

	@Override
	protected void selectListAfter() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2015-11-14上午11:41:55
	 * 描述: 插入订单
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public String toPay() throws IOException{
		DecimalFormat df = new DecimalFormat("0.00");
		String uuid = this.getRequest().getParameter("uuid");
		String num = this.getRequest().getParameter("num");//商品数量
		String ggid = this.getRequest().getParameter("ggid");//规格id
		String productId = this.getRequest().getParameter("productId");
		if(StringUtils.isNotBlank(uuid)&&NumberUtils.isNumber(num)&&StringUtils.isNotBlank(productId)){
			AppUser u = appUserService.selectByUuid(uuid);
			if(u!=null){
				Product product = this.productService.selectById(productId);
				synchronized (num){
					if(product!=null){
						if(product.getStock() > Integer.parseInt(num)){
							//创建订单明细集合
							//获取配送方式
							Express express = SystemManager.expressMap.get("POST");
							if(express==null){
								json = ERROR_MSG;
//								throw new NullPointerException("没有编码为"+e.getExpressCode()+"的配送方式！本次请求视为非法！");
								logger.error("没有编码为"+e.getExpressCode()+"的配送方式！本次请求视为非法！");
								return "json";
							}
							
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
							order.setAccount(u.getUsername());
							order.setRemark(product.getName());
							order.setScore(score);
							order.setExpressCode(express.getCode());//配送方式编码
							order.setExpressName(express.getName());//配送方式名称
//							order.setAmountExchangeScore(cartInfo.getTotalExchangeScore());//订单总兑换积分。订单支付成功以后扣除
							Spec spec=null;
							if(StringUtils.isNotBlank(ggid)){
								spec = specService.selectById(ggid);
								product.setNowPrice(spec.getSpecPrice());
							}
							
							/**
							 * 对金额进行格式化，防止出现double型数字计算造成的益出。
							 */
							order.setPtotal(df.format(Double.valueOf(product.getNowPrice())* product.getBuyCount()));//订单商品总金额
							order.setFee(df.format(express.getFee()));//订单总配送费
							order.setAmount(df.format(Double.valueOf(order.getPtotal())+Double.valueOf(order.getFee())));//订单总金额
							
							
							server.insert(order);
							
							Orderdetail orderdetail = new Orderdetail();
							orderdetail.setOrderID(Integer.valueOf(order.getId()));
							orderdetail.setProductID(Integer.valueOf(product.getId()));
							orderdetail.setGiftID(product.getGiftID());//商品赠品ID
							orderdetail.setPrice(product.getNowPrice());//商品现价
							orderdetail.setNumber(product.getBuyCount());//购买数
							orderdetail.setFee(String.valueOf(express.getFee()));//配送费
							orderdetail.setProductName(product.getName());
							orderdetail.setTotal0(String.valueOf(Double.valueOf(orderdetail.getPrice()) * orderdetail.getNumber()));//订单项小计
							orderdetail.setScore(product.getScore());//活的赠送的积分
							if(StringUtils.isNotBlank(ggid)){
								//按照规格计算
								orderdetail.setSpecInfo(spec.getSpecSize());
								orderdetail.setPurchasePrice(spec.getPurchasePrice());
							}else{
								orderdetail.setPurchasePrice(Double.valueOf(product.getChuChangPrice()));
							}
							if(product.getBuySpecInfo()!=null){
								//按照规格计算
								orderdetail.setSpecInfo(product.getBuySpecInfo().getSpecSize());
							}
							orderdetailService.insert(orderdetail);
							
							
							Ordership ordership = new Ordership();
							ordership.setOrderid(order.getId());
							
							Address address = addressService.selectById("35");
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
							
							// 记录订单创建日志
							Orderlog orderlog = new Orderlog();
							orderlog.setOrderid(String.valueOf(order.getId()));
							orderlog.setAccount(order.getAccount());
							orderlog.setContent("【创建订单】用户创建订单。订单总金额：" + order.getAmount());
							orderlog.setAccountType(net.jeeshop.services.common.Orderlog.orderlog_accountType_w);
							orderlogService.insert(orderlog);
							
							//创建支付记录对象
							Orderpay orderpay = new Orderpay();
							orderpay.setOrderid(order.getId());
							orderpay.setPaystatus(net.jeeshop.services.common.Orderpay.orderpay_paystatus_n);
							orderpay.setPayamount(Double.valueOf(order.getAmount()));
							orderpay.setPaymethod(bkSel);
							orderpayService.insert(orderpay);
							
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
							tPaymentRequest.dicOrder.put("OrderNo", orderpay.getId());                       //设定订单编号 （必要信息）
							tPaymentRequest.dicOrder.put("CurrencyCode", "156");//request.getParameter("CurrencyCode"));             //设定交易币种
							tPaymentRequest.dicOrder.put("OrderAmount", order.getAmount() );      //设定交易金额
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

							tPaymentRequest.dicRequest.put("ResultNotifyURL",SystemManager.systemSetting.getWww()+"/szmyOrdersActionFront!nyRec");// request.getParameter("ResultNotifyURL"));    //设定通知URL地址
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
		  
		  String status="WAIT_SELLER_SEND_GOODS";
		  if (orderService.quickpayNotify(status,tResult.getValue("OrderNo"))) {
				 getResponse().sendRedirect(getRequest().getContextPath()+"/appJsp/paySuccess.jsp");
			}else{
				json="支付失败!";
			}
		}
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
