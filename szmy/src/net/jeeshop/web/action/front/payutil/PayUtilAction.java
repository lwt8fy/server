package net.jeeshop.web.action.front.payutil;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.core.util.DesEncrypt;
import net.jeeshop.services.front.account.AccountService;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.front.address.AddressService;
import net.jeeshop.services.front.comment.CommentService;
import net.jeeshop.services.front.comment.bean.Comment;
import net.jeeshop.services.front.order.OrderService;
import net.jeeshop.services.front.order.bean.Order;
import net.jeeshop.services.front.orderdetail.OrderdetailService;
import net.jeeshop.services.front.orderpay.OrderpayService;
import net.jeeshop.services.front.orderpay.bean.Orderpay;
import net.jeeshop.services.front.ordership.OrdershipService;
import net.jeeshop.services.front.ordership.bean.Ordership;
import net.jeeshop.services.front.payutil.PayUtilService;
import net.jeeshop.services.front.product.ProductService;
import net.jeeshop.services.manage.company.CompanyService;
import net.jeeshop.services.manage.company.bean.Company;
import net.jeeshop.services.manage.orderlog.OrderlogService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PayUtilAction extends ActionSupport{
	private static final Logger logger = LoggerFactory.getLogger(PayUtilAction.class);
	private static final long serialVersionUID = 1L;
	private OrderService orderService;
	private net.jeeshop.services.manage.order.OrderService orderServiceManage;
	private OrderdetailService orderdetailService;
	private OrderpayService orderpayService;
	private ProductService productService; 
	private OrdershipService ordershipService;
	private AddressService addressService;
	private CompanyService companyService;
	private AccountService accountService;
	private OrderlogService orderlogService;
	private PayUtilService payUtilService;
	private CommentService commentService;
	private String selectLeftMenu;//选中的个人中心的菜单项
	private Order e;
	
	
	private String jsonStr;
	private final String yhdm="yhdm";
	private final String JSONJSP="json";
	
	
	
	private final String error="NzRCOEM1QjQ5OUI2QUVENDQxRTlENkQwODUwQTVGNUM=";//失败
	private final String success="QzY4QkNCMjk1MEYxQjYzNkQ3NEM1QURGMUY4QzY4MDM=";//成功
	private final String methodError="MEVEREE5NUQyOTFFQzdBMzg3MTlCRTdDQzcwNTdGRDE=";//请求方式为get错误
	private final String userError="RkUwRTM2NTkwRDAzMjJDNTE1RTlDM0E4MUEzQ0ZFRjU=";//用户身份验证错误
	
	
	private final String KEY="szmy123";//用户身份验证错误
	
	
	
	/**
	 * 显示订单详情
	 * @return
	 */
	public String showOrder(){
		String msg = getRequest().getParameter("MSG");
		msg=DesEncrypt.getDesString(msg);
		Map<String, Object> map = parseJSON2Map(msg);
		String id = (String) map.get("orderid");
		if(StringUtils.isBlank(id)){
			throw new NullPointerException("参数为空！");
		}
		
		String username=(String) map.get("username");
		String password=(String) map.get("password");
		
		
		Account ac=new Account();
		ac.setNxbUserName(username);
		ac.setNxbPassword(password);
		ac = accountService.selectOne(ac);
		if(ac==null){
			throw new NullPointerException("用户问题！");
		}
		
		selectLeftMenu = "orders";
		//查询订单信息
		Order order = new Order();
		order.setId(id);
		order.setAccount(ac.getAccount());
		List<Order> orders = orderService.selectOrderInfo(order);
		if(orders==null || orders.size()==0){
			throw new NullPointerException("根据订单ID查询不到订单信息！");
		}
		logger.error("orders.size="+orders.size());
		e = orders.get(0);
		e.setOrders(orders);
		
		//查询订单配送信息
		Ordership ordership = new Ordership();
		ordership.setOrderid(id);
		ordership = ordershipService.selectOne(ordership);
		if(ordership==null){
			throw new NullPointerException("根据订单ID查询不到订单配送信息！");
		}
		e.setOrdership(ordership);
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
	 * 修改订单状态
	 * @return
	 */
	public String updateOrderStatus(){
		String method = getRequest().getMethod();
		if(method.equals("GET")){
			jsonStr=methodError;
			return JSONJSP;
		}
		String msg = getRequest().getParameter("MSG");
		msg=DesEncrypt.getDesString(msg);
		Map<String, Object> map = parseJSON2Map(msg);
		
		
		String username=(String) map.get("username");
		String password=(String) map.get("password");
		
		Account ac=new Account();
		ac.setNxbUserName(username);
		ac.setNxbPassword(password);
		ac = accountService.selectOne(ac);
		if(ac==null){
			jsonStr=userError;
			return JSONJSP; 
		}
		
		String orderid=(String) map.get("orderid");
		String orderPayId=(String) map.get("orderPayId");
		String status=(String) map.get("status");
		
		Order o = orderService.selectById(orderid);
		if(!o.getAccount().equals(ac.getAccount())){
			jsonStr=userError;
			return JSONJSP;
		}
		
		if(StringUtils.isBlank(orderid)||StringUtils.isBlank(status)){
			jsonStr=error;
			return JSONJSP;
		}
		if(status.equals("1")){ 
			orderService.quickpayMergeNotify("WAIT_SELLER_SEND_GOODS",orderid);
		}else if(status.equals("2")){ //提款支付成功
			o=new Order();
			o.setId(orderid);
			o.setStatus(net.jeeshop.services.common.Order.order_status_tikuan);
			orderService.update(o);
		}
		jsonStr=success;
		
		return	JSONJSP;
	}
	public String sendPayRequest() throws IOException{
		String orderid = getRequest().getParameter("orderid");
		Order order = orderService.selectById(orderid);
		Orderpay op=new Orderpay();
		op.setOrderid(orderid);
		List<Orderpay> list = orderpayService.selectList(op);
		order.setOrderpayID(list.get(0).getId());
		String url = payUtilService.sendPayRequest(order,KeyValueHelper.get("nxbUrl"));
		if(url.equals("0"))jsonStr="用户还没有神州牧易支付账户!";
		else if(url.equals("1"))jsonStr="商家还没有神州牧易支付账户!";
		else getResponse().sendRedirect(url);
		return JSONJSP;
	}
	
	/**
	 * 获取授信申请商家详细信息
	 * @return
	 */
	public String getCompanyDetails(){
		String method = getRequest().getMethod();
		if(method.equals("GET")){
			jsonStr=methodError;
			return JSONJSP;
		}
		String msg = getRequest().getParameter("MSG");
		msg=DesEncrypt.getDesString(msg);
		
		try {
			Map<String, Object> map = parseJSON2Map(msg);
			
			
			String username=(String) map.get("username");
			String password=(String) map.get("password");
			
			if(username==null||password==null){
				jsonStr=error;
				return JSONJSP;
			}
			Account ac=new Account();
			ac.setNxbUserName(username);
			ac.setNxbPassword(password);
			ac = accountService.selectOne(ac);
			if(ac==null){
				jsonStr=userError;
				return JSONJSP; 
			}
			Company com = companyService.selectById(ac.getAccount());
			String y=DateUtil.getToday().substring(0, 4);
			String endd=DateUtil.getToday().substring(4);
			
			net.jeeshop.services.manage.order.bean.Order o=new net.jeeshop.services.manage.order.bean.Order();
			o.setStartDate(Integer.valueOf(y)-1+endd);
			o.setCompID(com.getId());
			o.setPgSize(100);
			PagerModel model = orderServiceManage.selectPageList(o);
			String xml=getComXml(com, model.getList());
			jsonStr=DesEncrypt.getEncString(xml);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString());
			jsonStr=error;
		}
		return JSONJSP;
	}
	public String getOrderDetails(){
		
		String method = getRequest().getMethod();
		if(method.equals("GET")){
			jsonStr=methodError;
			return JSONJSP;
		}
		String msg = getRequest().getParameter("MSG");
		msg=DesEncrypt.getDesString(msg);
		
		try {
			Map<String, Object> map = parseJSON2Map(msg);
			
			
			String username=(String) map.get("username");
			String password=(String) map.get("password");
			
			if(username==null||password==null){
				jsonStr=error;
				return JSONJSP;
			}
			Account ac=new Account();
			ac.setNxbUserName(username);
			ac.setNxbPassword(password);
			ac = accountService.selectOne(ac);
			if(ac==null){
				jsonStr=userError;
				return JSONJSP; 
			}
			Company com = companyService.selectById(ac.getAccount());
			String y=DateUtil.getToday().substring(0, 4);
			String endd=DateUtil.getToday().substring(4);
			net.jeeshop.services.manage.order.bean.Order o=new net.jeeshop.services.manage.order.bean.Order();
			o.setStartDate(Integer.valueOf(y)-1+endd);
			o.setCompID(com.getId());
			o.setPgSize(100);
			PagerModel model = orderServiceManage.selectPageList(o);
			String xml=getOrderXml(model.getList());
			jsonStr=DesEncrypt.getEncString(xml);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString());
			jsonStr=error;
		}
		return JSONJSP;
	}
	private String getOrderXml(List<net.jeeshop.services.manage.order.bean.Order> orders) throws Exception{
		Document document = DocumentHelper.createDocument();	
		//创建root 
        Element root = document.addElement("MSG");  
        //生成root的一个接点  
        Element param = null;
        param = root.addElement("MORTGAGORNO");//借款人编号
        param.addText("");
        param = root.addElement("MORTGAGORNAME");//借款人名称
        param.addText("");
       
        param = root.addElement("ORDERLIST");//交易明细
        	for (net.jeeshop.services.manage.order.bean.Order o : orders) { 
        		Element param2=param.addElement("TRADE");//交易信息
	        		 Element param3 = param2.addElement("ORDERNO");//订单编号
	 	        	 param3.addText(o.getId());
	 	        	 param3 = param2.addElement("RECEIVABLENO");//应收款款号
	 	        	 param3.addText("");//*****************************
	 	        	 param3 = param2.addElement("BUYERNAME");//买方客户名称
	 	        	 param3.addText(o.getAccount());
	 	        	 param3 = param2.addElement("GOODSNAME");//货物名称
	 	        	 param3.addText(o.getRemark());
	 	        	 param3 = param2.addElement("QTY");//数量
	 	        	 param3.addText(o.getQuantity()+"");
	 	        	 param3 = param2.addElement("AMOUNT");//订单金额
	 	        	 param3.addText(o.getAmount());
	 	        	 param3 = param2.addElement("RECEIVABLEAMOUNT");//应收款金额
	 	        	 param3.addText(o.getAmount());
	 	        	 param3 = param2.addElement("FACTORY");//生产厂家
	 	        	 param3.addText(o.getCompName());
	 	        	 param3 = param2.addElement("ORDERDATE");//订单日期
	 	        	 param3.addText(o.getCreatedate());
	 	        	 param3 = param2.addElement("REVEIVABLEDUEDATE");//应收款到账日期
	 	        	 param3.addText("");//*****************************************
			}
	       
		return formatXml(document);
	}
	public String getComXml(Company com,List<net.jeeshop.services.manage.order.bean.Order> orders) throws Exception{
		Document document = DocumentHelper.createDocument();	
		//创建root 
        Element root = document.addElement("MSG");  
        //生成root的一个接点  
        Element param = root.addElement("CREDITPPID"); //授权申请流水号
        param.addText("");
        param = root.addElement("MORTGAGORNO");//借款人编号
        param.addText("");
        param = root.addElement("MORTGAGORNAME");//借款人名称
        param.addText("");
        param = root.addElement("BUSINESSLICENSENO");//营业执照号
        param.addText(com.getBusinessLicense());
        param = root.addElement("LEGALCORPORATE");//法定代表人
        param.addText(com.getLegalPerson());
        param = root.addElement("CONTACTS");//联系人
	        Element param2 = param.addElement("NAME");//联系人姓名
	        param2.addText(com.getContactor());
	        param2=param.addElement("MOBILE");//联系人手机
	        param2.addText(com.getContactorPhone());
        param = root.addElement("ORDERLIST");//订单明细
        	for (net.jeeshop.services.manage.order.bean.Order o : orders) {
        		 param2=param.addElement("ORDER");//订单
	        		 Element param3 = param2.addElement("ORDERNO");//订单编号
	 	        	 param3.addText(o.getId());
	 	        	 param3 = param2.addElement("BUYERNAME");//买方客户名称
	 	        	 param3.addText(o.getAccount());
	 	        	 param3 = param2.addElement("GOODSNAME");//货物名称
	 	        	 param3.addText(o.getRemark());
	 	        	 param3 = param2.addElement("QTY");//数量
	 	        	 param3.addText(o.getQuantity()+"");
	 	        	 param3 = param2.addElement("AMOUNT");//订单金额
	 	        	 param3.addText(o.getAmount());
	 	        	 param3 = param2.addElement("FACTORY");//生产厂家
	 	        	 param3.addText(o.getCompName());
	 	        	 param3 = param2.addElement("ORDERDATE");//订单日期
	 	        	 param3.addText(o.getCreatedate());
			}
	       
        	
		return formatXml(document);
	}
	public String formatXml(Document document) throws IOException{
		 //创建字符串缓冲区 
        StringWriter stringWriter = new StringWriter();  
        //设置文件编码  
        OutputFormat xmlFormat = new OutputFormat();  
        xmlFormat.setEncoding("UTF-8"); 
        // 设置换行 
        xmlFormat.setNewlines(true); 
        // 生成缩进 
        xmlFormat.setIndent(true); 
        // 使用4个空格进行缩进, 可以兼容文本编辑器 
        xmlFormat.setIndent("    "); 
        
        //创建写文件方法  
        XMLWriter xmlWriter = new XMLWriter(stringWriter,xmlFormat);  
        //写入文件  
        xmlWriter.write(document);  
        //关闭  
        xmlWriter.close(); 
        // 输出xml 
        return stringWriter.toString();
	}
	
	public Map<String, Object> parseJSON2Map(String jsonStr){  
        Map<String, Object> map = new HashMap<String, Object>();  
        //最外层解析  
        JSONObject json = JSONObject.fromObject(jsonStr);  
        for(Object k : json.keySet()){  
            Object v = json.get(k);   
            //如果内层还是数组的话，继续解析  
            if(v instanceof JSONArray){  
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
                Iterator<JSONObject> it = ((JSONArray)v).iterator();  
                while(it.hasNext()){  
                    JSONObject json2 = it.next();  
                    list.add(parseJSON2Map(json2.toString()));  
                }  
                map.put(k.toString(), list);  
            } else {  
                map.put(k.toString(), v);  
            }  
        }  
        return map;  
    }  
	
	
	public static void main(String[] args) throws IOException {
		Document document = DocumentHelper.createDocument();	
		//创建root 
		        Element root = document.addElement("parameters");  
		        //生成root的一个接点  
		        Element param = root.addElement("a"); 
		        // 为节点添加属性 
		        //param.addAttribute("key", "sys.username"); 
		        // 为节点添加文本, 也可以用addText() 
		        Element param2 = param.addElement("b");
		        param2.addText("河南"); 
		        
		        //创建字符串缓冲区 
		        StringWriter stringWriter = new StringWriter();  
		        //设置文件编码  
		        OutputFormat xmlFormat = new OutputFormat();  
		        xmlFormat.setEncoding("UTF-8"); 
		        // 设置换行 
		        xmlFormat.setNewlines(true); 
		        // 生成缩进 
		        xmlFormat.setIndent(true); 
		        // 使用4个空格进行缩进, 可以兼容文本编辑器 
		        xmlFormat.setIndent("    "); 
		        
		        //创建写文件方法  
		        XMLWriter xmlWriter = new XMLWriter(stringWriter,xmlFormat);  
		        //写入文件  
		        xmlWriter.write(document);  
		        //关闭  
		        xmlWriter.close(); 
		        // 输出xml 
		        System.out.println(stringWriter.toString()); 

	}
	
	public ActionContext getActionContext() {
		return ActionContext.getContext();
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) getActionContext().get(
				StrutsStatics.HTTP_REQUEST);
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) getActionContext().get(
				StrutsStatics.HTTP_RESPONSE);
	}

	public HttpSession getSession() {
		return getRequest().getSession();
	}
	
	public Map<String,Object> getApplication() {
		return ActionContext.getContext().getApplication();
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public void setOrderdetailService(OrderdetailService orderdetailService) {
		this.orderdetailService = orderdetailService;
	}
	public void setOrderpayService(OrderpayService orderpayService) {
		this.orderpayService = orderpayService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	public void setOrdershipService(OrdershipService ordershipService) {
		this.ordershipService = ordershipService;
	}
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	public void setOrderlogService(OrderlogService orderlogService) {
		this.orderlogService = orderlogService;
	}

	
	public PayUtilService getPayUtilService() {
		return payUtilService;
	}
	public void setPayUtilService(PayUtilService payUtilService) {
		this.payUtilService = payUtilService;
	}
	public OrderService getOrderService() {
		return orderService;
	}
	public OrderdetailService getOrderdetailService() {
		return orderdetailService;
	}
	public OrderpayService getOrderpayService() {
		return orderpayService;
	}
	public ProductService getProductService() {
		return productService;
	}
	public OrdershipService getOrdershipService() {
		return ordershipService;
	}
	public AddressService getAddressService() {
		return addressService;
	}
	public CompanyService getCompanyService() {
		return companyService;
	}
	public AccountService getAccountService() {
		return accountService;
	}
	public OrderlogService getOrderlogService() {
		return orderlogService;
	}
	public String getYhdm() {
		return yhdm;
	}
	
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	public Order getE() {
		return e;
	}
	public void setE(Order e) {
		this.e = e;
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
	public net.jeeshop.services.manage.order.OrderService getOrderServiceManage() {
		return orderServiceManage;
	}
	public void setOrderServiceManage(
			net.jeeshop.services.manage.order.OrderService orderServiceManage) {
		this.orderServiceManage = orderServiceManage;
	}
	
}
