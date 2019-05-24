package net.jeeshop.web.action.front.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.FrontContainer;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.AddressUtils;
import net.jeeshop.core.util.MD5;
import net.jeeshop.services.common.Company;
import net.jeeshop.services.common.FavoriteShop;
import net.jeeshop.services.front.account.AccountService;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.front.address.AddressService;
import net.jeeshop.services.front.address.bean.Address;
import net.jeeshop.services.front.area.bean.Area;
import net.jeeshop.services.front.favorite.FavoriteService;
import net.jeeshop.services.front.favorite.bean.Favorite;
import net.jeeshop.services.front.favoriteShop.service.FavoriteShopService;
import net.jeeshop.services.front.order.OrderService;
import net.jeeshop.services.front.order.bean.Order;
import net.jeeshop.services.front.order.bean.OrderSimpleReport;
import net.jeeshop.services.front.orderdetail.OrderdetailService;
import net.jeeshop.services.front.orderdetail.bean.Orderdetail;
import net.jeeshop.services.front.product.ProductService;
import net.jeeshop.services.front.product.bean.Product;
import net.jeeshop.services.manage.system.impl.MenuService;
import net.jeeshop.services.manage.system.impl.UserService;
import net.jeeshop.web.action.front.orders.CartInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 门户会员服务类
 * 
 * @author huangf
 * 
 */
public class SzmyMobileAccountAction extends BaseAction<Account> {
	private static final Logger logger = Logger.getLogger(AccountAction.class);
	private static final long serialVersionUID = 1L;
	private MenuService menuService;
	private Address address;//配送地址
	private OrderService orderService; 
	private final int pageSize = 10;
	private ProductService productService;
	private List<Address> defList;
	public List<Address> getDefList() {
		return defList;
	}
	public void setDefList(List<Address> defList) {
		this.defList = defList;
	}
	public ProductService getProductService() {
		return productService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	private FavoriteService favoriteService;
	public FavoriteService getFavoriteService() {
		return favoriteService;
	}
	public void setFavoriteService(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}
	private FavoriteShopService favoriteShopService;
	public FavoriteShopService getFavoriteShopService() {
		return favoriteShopService;
	}
	public void setFavoriteShopService(FavoriteShopService favoriteShopService) {
		this.favoriteShopService = favoriteShopService;
	}
	public OrderService getOrderService() {
		return orderService;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	private OrderdetailService orderdetailService;
	public OrderdetailService getOrderdetailService() {
		return orderdetailService;
	}
	public void setOrderdetailService(OrderdetailService orderdetailService) {
		this.orderdetailService = orderdetailService;
	}
	private OrderSimpleReport orderSimpleReport;//简单报表
	public OrderSimpleReport getOrderSimpleReport() {
		return orderSimpleReport;
	}
	public void setOrderSimpleReport(OrderSimpleReport orderSimpleReport) {
		this.orderSimpleReport = orderSimpleReport;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	private AddressService addressService;//配送地址service
	public AddressService getAddressService() {
		return addressService;
	}
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}
	private String selectLeftMenu;
	private List<Address> addressList;//配送地址列表
	public List<Address> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}
	public String getSelectLeftMenu() {
		return selectLeftMenu;
	}
	public void setSelectLeftMenu(String selectLeftMenu) {
		this.selectLeftMenu = selectLeftMenu;
	}
	public MenuService getMenuService() {
		return menuService;
	}
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	private AccountService accountService;
	public AccountService getAccountService() {
		return accountService;
	}
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	private UserService userService; 
	private String errorMsg;
	private static final String toLogin = "toLogin";//转到登陆界面,forword方式 地址不变
	@SuppressWarnings("unused")
	private static final String toLoginRedirect = "toLoginRedirect";//转到登陆界面,getResponse().sendRedirect(arg0)方式 地址变化
	private static final String toIndex = "toIndex";//转到门户首页
	private static final String toJson = "toJson";
	 String confirmOrderNow="confirmOrderNow";
	private String pass ;
	
	private String jsonStr;
	/**
	 * 用户注册
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String doRegister() throws IOException {
		String url = this.getRequest().getParameter("url");
		if(StringUtils.isBlank(e.getAccount())){
//			throw new NullPointerException("手机号码不能为空！");
			errorMsg="<font color='red'>手机号码不能为空！</font>";
			return "register";
		}
		if(StringUtils.isBlank(e.getPassword())||e.getPassword().length()>20||e.getPassword().length()<6){
//			throw new NullPointerException("手机号码不能为空！");
			errorMsg="<font color='red'>密码格式不正确！</font>";
			return "register";
		}
		String mobile = getSession().getAttribute(FrontContainer.validateMobile).toString();
		String validateCode = getSession().getAttribute(FrontContainer.validateMobileCode).toString();
		if(!mobile.equals(e.getMobile())){
			errorMsg="<font color='red'>手机与验证码不匹配！</font>";
			return "register";
		}
		if(!validateCode.equals(e.getVcode())){
			errorMsg="<font color='red'>验证码错误！</font>";
			return "register";
		}
		Account ac = new Account();
		ac.setAccount(e.getAccount());
		if(accountService.selectCount(ac)!=0){
			errorMsg="<font color='red'>手机号码已经存在！</font>";
			return "register";
		}
		e.setPassword(MD5.md5(e.getPassword()));
		e.setEmailIsActive("y");
		e.setNickname(e.getAccount());
		if (StringUtils.isBlank(e.getId())) {
			// 用户注册
			e.setLastLoginTime("yes");
			e.setLastLoginIp(AddressUtils.getIp(getRequest()));
			String address = null;
			try {
				address = AddressUtils.getAddresses("ip=" + e.getLastLoginIp(), "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			e.setLastLoginArea(address);
			/*e.setPresenter(accountService.selectById(KeyValueHelper.get("PTSYZH")).getAccount());
	    	e.setPastDue(DateUtil.dateToStr(DateUtil.addMonth(new Date(), 3),"yyyy-MM-dd HH:mm:ss"));
			Cookie[] cookies = getRequest().getCookies();

			 for(Cookie cookie : cookies) {
			    if("presenter".equals(cookie.getName())) {
			    	Account a = accountService.selectById(cookie.getValue());
			    	e.setPresenter(a.getAccount());
			    	
			    	TuiGuang tg=new TuiGuang();
					tg.setUserName(e.getAccount());
					tg.setUserPhone(e.getMobile());
					tg.setUserID(e.getId());
					tg.setPName(a.getAccount());
					tg.setPID(a.getId());
					tg.setPPhone(a.getMobile());
					tg.setIsLogin("y");
					tuiGuangService.insert(tg);
			    	
			    	cookie.setPath(getRequest().getContextPath());
			        cookie.setMaxAge(0);//清除cookie,设置生命周期为0
			        getResponse().addCookie(cookie);
			    }

			 }
			*/
			getServer().insert(e);
		//accountService.sendEmail(e, NotifyTemplate.email_reg);
		} else {
			// 修改密码
//			getServer().update(e);
			throw new NullPointerException("不支持！");
		}
		Account acc = accountService.selectById(e.getId());
		getSession().setAttribute(FrontContainer.USER_INFO, acc);
		if(StringUtils.isNotBlank(url)&&!"null".equals(url)){
			this.getResponse().sendRedirect(url);
			return null;
		}
		
		//企业信息
		/*Company company=new Company();
		company.setCreateAccount(acc.getAccount());
		company=companyService.selectOne(company);
		//if(company!=null){
		getSession().setAttribute(ManageContainer.manage_session_company_info, company);
		*/
		//return "toLogin";
		return toIndex;
/*	
//		getSession().setAttribute("checkEmail","checkEmail");
		getSession().setAttribute("uid", e.getId());
		getRequest().setAttribute("message", "恭喜，帐号注册成功！");
		getRequest().setAttribute("url", getRequest().getContextPath()+"/user/login.html");
		//getResponse().sendRedirect(SystemManager.systemSetting.getWww()+"/user/checkEmail.html");
		return "successMsg";
		*/
	}
	/**
	 * ajax检查用户名称是否存在
	 * @return
	 * @throws IOException 
	 */
	public String checkAccountExist() throws IOException{
		super.utf8JSON();
		if(StringUtils.isBlank(e.getAccount())){
			getResponse().getWriter().write("{\"error\":\"用户名不能为空!\"}");
		}else{
			Account acc = new Account();
			acc.setAccount(e.getAccount());
			if(accountService.selectCount(acc)==0){
				getResponse().getWriter().write("{\"error\":\"用户名不存在!\"}");
			}else{
				getResponse().getWriter().write("{\"ok\":\"用户名输入正确!\"}");
			}
		}
		return null;
	}

	/**
	 * ajax检查密码是否正确
	 * @return
	 * @throws IOException 
	 */
	public String checkPassword() throws IOException{
		super.utf8JSON();
		if(StringUtils.isBlank(e.getPassword())){
			getResponse().getWriter().write("{\"error\":\"密码不能为空!\"}");
		}else{
			Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
			if(!acc.getPassword().equals(MD5.md5(e.getPassword()))){
				getResponse().getWriter().write("{\"error\":\"输入的密码不正确!\"}");
			}else{
				getResponse().getWriter().write("{\"ok\":\"密码正确!\"}");
			}
		}
		return null;
	}
	/**
	 * 用户登陆
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String doLogin() throws IOException {
	String url = this.getRequest().getParameter("url");
	logger.error("doLogin()...");
	getSession().removeAttribute("compList");
	if (getSession().getAttribute(FrontContainer.USER_INFO) != null) {
		return toIndex;
	}

	errorMsg = "<font color='red'>帐号或密码错误!</font>";
	if (e.getAccount() == null || e.getAccount().trim().equals("")
			|| e.getPassword() == null || e.getPassword().trim().equals("")){
		getSession().setAttribute(FrontContainer.login_errorMsg, errorMsg);
		logger.error("doLogin.errorMsg="+errorMsg);
		return toLogin;
	}
	String vcode = getRequest().getParameter("vcode");
	if(StringUtils.isNotBlank(vcode)){//验证验证码输入的是否正确
		String validateCode = getSession().getAttribute(FrontContainer.validateCode).toString();
		if(!validateCode.equalsIgnoreCase(vcode)){
			getSession().setAttribute(FrontContainer.login_errorMsg, "<font color='red'>验证码输入错误!</font>");
			errorMsg="<font color='red'>验证码输入错误!</font>";
			return toLogin;
		}
	}else{
		getSession().setAttribute(FrontContainer.login_errorMsg, "<font color='red'>请输入验证码!</font>");
		errorMsg="<font color='red'>请输入验证码!</font>";
		return toLogin;
	}

	//用户验证
	e.setPassword(MD5.md5(e.getPassword()));
	String account = e.getAccount();
	String password = e.getPassword();
	e.clear();
	e.setAccount(account);
	e.setPassword(password);
	Account acc = accountService.selectOne(e);
	if (acc == null) {
		getSession().setAttribute(FrontContainer.login_errorMsg, errorMsg);
		return toLogin;
	}
	
	errorMsg = null;
	//acc.setLoginType(LoginTypeEnum.system);//登陆方式
	getSession().setAttribute(FrontContainer.USER_INFO, acc);
	e.clear();
	if(url.indexOf("loginout.html")!=-1){
		return toIndex;
	}
	if(StringUtils.isNotBlank(url)&&!"null".equals(url)){
		this.getResponse().sendRedirect(url);
		return  null;
	}
	
	/*e.setId(acc.getId());
	e.setLastLoginTime("yes");
	e.setLastLoginIp(AddressUtils.getIp(getRequest()));
	String address = null;
	try {
		address = AddressUtils.getAddresses("ip=" + e.getLastLoginIp(), "utf-8");
	} catch (Exception e) {
		e.printStackTrace();
	}
	e.setLastLoginArea(address);
	accountService.update(e);
	e.clear();*/
	return toIndex;
 }
	@Override
	public Account getE() {
		// TODO Auto-generated method stub
		return this.e;
	}
	@Override
	public void insertAfter(Account e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void prepare() throws Exception {
		logger.error("AccountAction.prepare...");
		if (this.e == null) {
			this.e = new Account();
		}else{
			e.clear();
		}
		
	}
	@Override
	protected void selectListAfter() {
		// TODO Auto-generated method stub
		
	}	
	
	/**
	 * ajax验证输入的字符的唯一性
	 * @return
	 * @throws IOException
	 */
	public String unique() throws IOException{
		logger.error("验证输入的字符的唯一性"+e);
		logger.error(e.getNickname());
		getResponse().setCharacterEncoding("utf-8");
		if(StringUtils.isNotBlank(e.getNxbUserName())){//验证绑定支付用户名
			Account acc = (Account) getSession().getAttribute(
					FrontContainer.USER_INFO);
			
			String pname=e.getNxbUserName();
			getResponse().setCharacterEncoding("utf-8");
			if(pname.equals(acc.getNxbUserName())){
				getResponse().getWriter().write("{\"ok\":\"帐号可以使用!\"}");
			}else{
				if(accountService.selectCount(e)>0){
					getResponse().getWriter().write("{\"error\":\"帐号已经被占用!\"}");
				}else{
					getResponse().getWriter().write("{\"ok\":\"帐号可以使用!\"}");
				}
			}
			
		}
		
		if(StringUtils.isNotBlank(e.getNickname())){//验证昵称是否被占用
			logger.error("验证昵称是否被占用");
			String nickname = e.getNickname();
			e.clear();
			e.setNickname(nickname);
			getResponse().setCharacterEncoding("utf-8");
			if(accountService.selectCount(e)>0){
				getResponse().getWriter().write("{\"error\":\"昵称已经被占用!\"}");
			}else{
				getResponse().getWriter().write("{\"ok\":\"昵称可以使用!\"}");
			}
		}else if(StringUtils.isNotBlank(e.getAccount())){//验证用户名是否被占用
			logger.error("验证用户名是否被占用");
			String account = e.getAccount();
			e.clear();
			e.setAccount(account);
			getResponse().setCharacterEncoding("utf-8");
			if(accountService.selectCount(e)>0){
				getResponse().getWriter().write("{\"error\":\"用户名已经被占用!\"}");
			}else{
				getResponse().getWriter().write("{\"ok\":\"用户名可以使用!\"}");
			}
		}else if(StringUtils.isNotBlank(e.getEmail())){//验证邮箱是否被占用
			logger.error("验证邮箱是否被占用="+e.getEmail());
			String email = e.getEmail();
			e.clear();
			e.setEmail(email);
			getResponse().setCharacterEncoding("utf-8");
			if(accountService.selectCount(e) > 0){
				getResponse().getWriter().write("{\"error\":\"邮箱已经被占用!\"}");
			}else{
				getResponse().getWriter().write("{\"ok\":\"邮箱可以使用!\"}");
			}
		}else if(StringUtils.isNotBlank(e.getVcode())){//验证验证码输入的是否正确
			logger.error("检查验证码输入的是否正确"+e.getVcode());
			String validateCode = getSession().getAttribute(FrontContainer.validateCode).toString();
			logger.error("validateCode="+validateCode);
			getResponse().setCharacterEncoding("utf-8");
			if(validateCode.equalsIgnoreCase(e.getVcode())){
				getResponse().getWriter().write("{\"ok\":\"验证码输入正确!\"}");
			}else{
				getResponse().getWriter().write("{\"error\":\"验证码输入有误!\"}");
			}
//			vcode = null;
		}else if(StringUtils.isNotBlank(e.getPassword())){//验证原始密码输入是否正确
			logger.error("验证原始密码输入是否正确"+e.getPassword());
			Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
			getResponse().setCharacterEncoding("utf-8");
			if(StringUtils.isNotBlank(e.getPassword()) && MD5.md5(e.getPassword()).equals(acc.getPassword())){
				getResponse().getWriter().write("{\"ok\":\"原密码输入正确!\"}");
			}else{
				getResponse().getWriter().write("{\"error\":\"原密码输入有误!\"}");
			}
		}else if(StringUtils.isNotBlank(e.getCardNO())){//验证身份证号
			logger.error("身份证号是否被占用");
			Account account = (Account) getSession().getAttribute(
					FrontContainer.USER_INFO);
			String cno = e.getCardNO();
			getResponse().setCharacterEncoding("utf-8");
			if(account!=null&&cno.equals(account.getCardNO())){
				getResponse().getWriter().write("{\"ok\":\"身份证号可以使用!\"}");
			}else{
			e.clear();
			e.setCardNO(cno);
			
			if(accountService.selectCount(e)>0){
				getResponse().getWriter().write("{\"error\":\"身份证号已经被占用!\"}");
			}else{
				getResponse().getWriter().write("{\"ok\":\"身份证号可以使用!\"}");
			}
			}
		}
		
		if(e!=null){
			e.clear();
		}
		return null;
	}
	/*public String exit(){
		loginout();
		return "toIndex";
	}*/
	/**
	 * 用户注销
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String loginout() {
		//清除用户session
		Account account = (Account) getSession().getAttribute(
				FrontContainer.USER_INFO);
		if (account != null) {
			account.clear();
		}
		getSession().setAttribute(FrontContainer.USER_INFO, null);
		
		//清除用户购物车缓存
		CartInfo cartInfo = (CartInfo) getSession().getAttribute(FrontContainer.myCart);
		if(cartInfo!=null){
			cartInfo.clear();
		}
		getSession().setAttribute(FrontContainer.myCart, null);
		getSession().setAttribute(FrontContainer.login_errorMsg, null);
		//清除历史浏览记录
		LinkedHashMap<String, Product> history_product_map = (LinkedHashMap<String, Product>) getSession().getAttribute(FrontContainer.history_product_map);
//		List<String> history_product_map = (List<String>) getSession().getAttribute(FrontContainer.history_product_map);
		if(history_product_map!=null){
			history_product_map.clear();
		}
		getSession().setAttribute(FrontContainer.history_product_map,null);
		getRequest().getSession().removeAttribute("uuid");
		return toIndex;
	}
	/**
	 * 添加地址
	 * @return
	 */
	
	public String saveAddress(){
		System.out.println("into the saveAddress。。。。。。。");
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
			return toLogin;
		}
		selectLeftMenu = "address";
		
		//需要将省市区的代号换成中文，插入到pcadetail字段里面去，显示的时候方便。
		StringBuilder pcadetail = new StringBuilder();
		Area sheng = SystemManager.areaMap.get(address.getProvince());//省
		pcadetail.append(sheng.getName());
		
		for(int i=0;i<sheng.getChildren().size();i++){
			Area shi = sheng.getChildren().get(i);//市
			if(shi.getCode().equals(address.getCity())){
				
				pcadetail.append(" ").append(shi.getName());
				
				for(int j = 0;j<shi.getChildren().size();j++){
					Area qu = shi.getChildren().get(j);//区
					if(qu.getCode().equals(address.getArea())){
						pcadetail.append(" ").append(qu.getName());
						break;
					}
				}
				
				break;
			}
		}
		
		address.setPcadetail(pcadetail.toString());
		
		address.setAccount(acc.getAccount());
		
		String isdefault=getRequest().getParameter("isdefault");
		
		if(StringUtils.isBlank(address.getId())){
			int resultid=addressService.insert(address);
			if(isdefault.equals("y")){
				address.setId(String.valueOf(resultid));
				address.setIsdefault("y");
				address.setAccount(acc.getAccount());
				addressService.setAddressDefault(address);
				CartInfo cartInfo = (CartInfo)this.getSession().getAttribute(FrontContainer.myBuyNow);
				if(cartInfo==null){
					cartInfo = new CartInfo();
				}
				Address add = new Address();
				add.setAccount(acc.getAccount());
				List<Address> list = new ArrayList<Address>();
				list = addressService.selectList(add);
				cartInfo.setAddressList(list);
				cartInfo.setDefaultAddessID(String.valueOf(resultid));
				this.getSession().setAttribute(FrontContainer.myBuyNow, cartInfo);
				CartInfo cartInfo1 = (CartInfo)this.getSession().getAttribute(FrontContainer.myCart);
				if(cartInfo1==null){
					cartInfo1 = new CartInfo();
				}
				cartInfo1.setAddressList(list);
				cartInfo1.setDefaultAddessID(String.valueOf(resultid));
				this.getSession().setAttribute(FrontContainer.myCart, cartInfo1);
				
			}
		}else{
			addressService.update(address);
		}
		/*CartInfo cartInfo = (CartInfo)this.getSession().getAttribute(FrontContainer.myBuyNow);
		System.err.println(cartInfo.getAddressList().size());*/
		String buyCount1 = this.getRequest().getParameter("inputBuyNum");
		String productId = this.getRequest().getParameter("productID");
		getRequest().setAttribute("inputBuyNum", buyCount1);
		getRequest().setAttribute("productID", productId);
		address.clear();
		String cfmOrdeNow = this.getRequest().getParameter("confirmOrderNow");
		if(StringUtils.isNotBlank(cfmOrdeNow)){
			return confirmOrderNow;
		}
		return "confirmOrder";
	}
	public String address(){
		Account account = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (account == null || StringUtils.isBlank(account.getAccount())) {
			return toLogin;
		}
		selectLeftMenu = "address";
		address.setAccount(account.getAccount());
		addressList = addressService.selectList(address);
		CartInfo cartInfo = (CartInfo)this.getSession().getAttribute(FrontContainer.myCart);
		if(cartInfo!=null){
			cartInfo.setAddressList(addressList);
			this.getSession().setAttribute(FrontContainer.myCart, cartInfo);
		}
		return "address";
	}
	/**
	 * 
	 * @return
	 */
	public String toAddress(){
		String buyCount1 = this.getRequest().getParameter("inputBuyNum");
		String productId = this.getRequest().getParameter("productID");
		String confirmOrderNow = this.getRequest().getParameter("confirmOrderNow");
		getRequest().setAttribute("confirmOrderNow", confirmOrderNow);
		getRequest().setAttribute("inputBuyNum", buyCount1);
		getRequest().setAttribute("productID", productId);
		return "toAddress";
	}
	
	public String toResetPass(){
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
			return toLogin;
		}
		return "toResetPass";
	}
		/**
	 * 修改密码
	 * @return
	 */
	public String changePwd(){
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
			return toLogin;
		}
		if (StringUtils.isBlank(e.getPassword())) {
			this.errorMsg = "原密码不能为空！";
			return "toResetPass";
		}
		if (StringUtils.isBlank(e.getNewPassword())||StringUtils.isBlank(e.getNewPassword2())) {
			this.errorMsg = "新密码或确认密码不能为空！";
			return "toResetPass";
		}
		if ( !e.getNewPassword2().equals(e.getNewPassword())) {
			this.errorMsg = "两次密码不一致！";
			return "toResetPass";
		}
		getSession().setAttribute(FrontContainer.selectMenu,FrontContainer.not_select_menu);
//		selectLeftMenu = "changePwd";
		selectLeftMenu = "topwd";
//		logger.error(">>e.getNewPassword() = "+e.getNewPassword());
		e.setPassword(MD5.md5(e.getNewPassword()));
		e.setId(acc.getId());
//		logger.error(">>e.getPassword() = "+e.getPassword());
		accountService.update(e);
		this.errorMsg = "修改密码成功！";
		//重新缓存密码数据
		acc.setPassword(e.getPassword());
		
		e.clear();
		return "changePwdSuccess";
	}
	/**
	 * 
	 * @return
	 */
	public String toShowOrders(){
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
			return toLogin;
		}
		String flag = this.getRequest().getParameter("flag");
		if(StringUtils.isNotBlank(flag)){
			this.getRequest().setAttribute("flag", flag);
		}
		return "orders";
	}
	/**
	 * 我的订单列表
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getOrdersJson() throws Exception{
		String offset = this.getRequest().getParameter("offset");
		String paystatus = this.getRequest().getParameter("paystatus");
		String status = this.getRequest().getParameter("status");
		Order order = new Order();
		order.setStatus(status);
		if(StringUtils.isNotBlank(offset)){
			order.setOffset(Integer.parseInt(offset)*pageSize);
		}else{
			order.setOffset(0);
		}
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
			return toLogin;
		}
		order.setPaystatus(paystatus);
//		getSession().setAttribute(FrontContainer.selectMenu,FrontContainer.not_select_menu);
		selectLeftMenu = "orders";

		getMyOrders(acc.getAccount(),order);
		
		//查询汇总
		//orderSimpleReport = orderService.selectOrdersSimpleReport(acc.getAccount());
		//logger.error("orderSimpleReport="+orderSimpleReport);
		List<Order> orderList = pager.getList();
		if(orderList!=null && orderList.size()>0){
			JSONArray reslut = JSONArray.fromObject(orderList);
			jsonStr = reslut.toString();
		}else{
			jsonStr="0";
		}
		return "toJson";
	}
	
	/**
	 * 分页查询订单集合
	 * @return
	 * @throws Exception
	 */
	private void selectMyOrders(String account,Order order) throws Exception {
		/*int offset = 0;
		if (getRequest().getParameter("pager.offset") != null) {
			offset = Integer
					.parseInt(getRequest().getParameter("pager.offset"));
		}
		if (offset < 0)
			offset = 0;*/
		order.setAccount(account);
//	/	((PagerModel)order).setOffset(pager.getOffset());
		order.setPageSize(pageSize);
		pager = orderService.selectPageList(order);
		if(pager==null)pager = new PagerModel();
		// 计算总页数
		order.setPagerSize((order.getTotal() + order.getPageSize() - 1)
				/ order.getPageSize());
		
//		selectListAfter();
//		pager.setPagerUrl("orders.html");
	}
	/**
	 * 分页获取我的订单列表，首页分页查询订单集合，然后把查询到的ID集合仍到一个多表联合的查询里面，查询出更多的信息。分页显示用户的订单只用一个SQL貌似不好搞的。想到好办法再去优化。
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void getMyOrders(String account,Order order) throws Exception {
		//分页查询订单ID集合
//		super.selectList();
		//1、分页查询订单集合
		selectMyOrders(account,order);
		//根据上面查询出来的ID集合，多表联合查询出订单和订单明细数据
		List<Order> ordersTemp = getPager().getList();
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
		System.out.println(list.size());
//		Order order = new Order();
//		order.clear();
//		order.setAccount(account);
//		order.setQueryOrderIDs(ids);
//		//2、查询指定订单集合的所有订单项集合，然后内存中对订单项进行分组
//		List<Order> myOrders = orderService.selectList(order);
//		if(myOrders!=null && myOrders.size()>0){
//			for(int i=0;i<ordersTemp.size();i++){
//				Order orderItem = ordersTemp.get(i);
//				for(Iterator<Order> it = myOrders.iterator();it.hasNext();){
//					Order orderdetail = it.next();
////					logger.error("orderdetail.getId()="+orderdetail.getId());
////					logger.error("orderItem.getId()="+orderItem.getId());
//					if(orderdetail.getId().equals(orderItem.getId())){
//						orderItem.getOrders().add(orderdetail);
//						it.remove();
//					}
//				}
//			}
//		}
		
//		Map<String, Order> orderMap = new HashMap<String, Order>();
		//处理成页面显示的数据格式
//		if(myOrders!=null && myOrders.size()>0){
//			orderMap.clear();
//			for(int i=0;i<myOrders.size();i++){
//				order = myOrders.get(i);
//				Order entry = orderMap.get(order.getId());
//				if(entry==null){
//					//添加订单
//					orderMap.put(order.getId(), order);
//					//添加订单项
//					order.getOrders().add(order);
//					continue;
//				}
//				
//				//否则添加订单到此MAP订单的orders集合中，此集合存储的是订单明细信息
//				entry.getOrders().add(order);
//			}
//			myOrders.clear();
//			myOrders.addAll(orderMap.values());
//			orderMap.clear();
//
//			//根据订单ID排序
//			Collections.sort(myOrders, new Comparator<Order>() {
//				@Override
//				public int compare(Order o1, Order o2) {0
//					int id1 = Integer.valueOf(o1.getId());
//					int id2 = Integer.valueOf(o2.getId());
//					if (id1 > id2) {
//						return 1;
//					} else if (id1 < id2) {
//						return 2;
//					}
//					return 0;
//				}
//			});
//			getPager().setList(myOrders);
//		}
//		getSession().setAttribute(FrontContainer.selectMenu, "user_centers");
	}
	//
	@SuppressWarnings("unchecked")
	public String getFavoriteShopJson() {
		String offset = this.getRequest().getParameter("offset");
		FavoriteShop favoriteshop=new FavoriteShop();
		if(StringUtils.isNotBlank(offset)){
			favoriteshop.setOffset(Integer.parseInt(offset)*pageSize);
		}else{
			favoriteshop.setOffset(0);
		}
		Account user=(Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (user == null || StringUtils.isBlank(user.getAccount())) {
			return toLogin;
		}
		favoriteshop.setAccount(user.getAccount());
		favoriteshop.setPagerSize(pageSize);
		pager=favoriteShopService.selectPageList(favoriteshop);
		
		if(pager==null)pager = new PagerModel();
		favoriteshop.setPagerSize((favoriteshop.getTotal()+ favoriteshop.getPageSize() - 1)/ favoriteshop.getPageSize());
		List<FavoriteShop> favoriteshoplist = getPager().getList();
		if(favoriteshoplist!=null && favoriteshoplist.size()>0){
		List<Company> companylist=favoriteShopService.companyList(favoriteshop);
		if(companylist!=null && companylist.size()>0){
			for(int i=0;i<favoriteshoplist.size();i++){
				FavoriteShop ff = favoriteshoplist.get(i);
				for(int j=0;j<companylist.size();j++){
					Company company = companylist.get(j);
					if(ff.getcompanyID().equals(company.getId())){
						ff.setCompany(company);
						break;
					}
				}
			}
		}
		JSONArray reslut = JSONArray.fromObject(favoriteshoplist);
		jsonStr = reslut.toString();
	}else{
		jsonStr="0";
	}
		return "toJson";
	}
	
	@SuppressWarnings("unchecked")
	public String getFavoriteJson() throws Exception{
		String offset = this.getRequest().getParameter("offset");
		Favorite favorite = new Favorite();
		if(StringUtils.isNotBlank(offset)){
			favorite.setOffset(Integer.parseInt(offset)*pageSize);
		}else{
			favorite.setOffset(0);
		}
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
			return toLogin;
		}
		
		selectLeftMenu = FrontContainer.user_leftMenu_favorite;
		favorite.setAccount(acc.getAccount());
		favorite.setPagerSize(pageSize);
		pager = favoriteService.selectPageList(favorite); 
		
		//PagerModel commentPager = super.selectPagerModelByServices(favoriteService, favorite);
		//super.pager = commentPager;//公用分页控件需要这么写。
		if(pager==null)pager = new PagerModel();
		// 计算总页数
		favorite.setPagerSize((favorite.getTotal() + favorite.getPageSize() - 1)
				/ favorite.getPageSize());
		List<Favorite> favoriteList = getPager().getList();
		if(favoriteList!=null && favoriteList.size()>0){
			List<String> productIds = new LinkedList<String>();
			for(Favorite favo :  favoriteList){
				productIds.add(favo.getProductID());
			}
			//根君商品ID集合加载商品信息：名称、价格、销量、是否上下架等
			Product p = new Product();
			p.setProductIds(productIds);
			List<Product> productList = productService.selectProductListByIds(p);
			
			//将查询出来的每一个商品对象挂到收藏夹对象上去
			if(productList!=null && productList.size()>0){
				for(int i=0;i<favoriteList.size();i++){
					Favorite ff = favoriteList.get(i);
					for(int j=0;j<productList.size();j++){
						Product product = productList.get(j);
						if(ff.getProductID().equals(product.getId())){
							ff.setProduct(product);
							break;
						}
					}
				}
			}
			JSONArray reslut = JSONArray.fromObject(favoriteList);
			jsonStr = reslut.toString();
		}else{
			jsonStr="0";
		}
		return "toJson";
		
	}
	//删除店铺收藏
	public String delFavoriteShop(){
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
			jsonStr = "2";
			return toJson;
		}
		String pid = getRequest().getParameter("companyID");
		FavoriteShop favoriteshop=new FavoriteShop();
		favoriteshop.setcompanyID(pid);
		favoriteshop.setAccount(acc.getAccount());
		List<FavoriteShop> list = favoriteShopService.selectList(favoriteshop);
		 try {
			 favoriteshop=list.get(0);
			 favoriteShopService.delete(favoriteshop);
				jsonStr="1";
			  } catch (Exception e) {
				  logger.error("deleteOrder..."+e);
			  }
		return toJson;
		
	}
	/**
	 * ajax删除收藏夹
	 * teng
	 * @return
	 */
	public String delFavorite(){
		
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
			jsonStr = "2";
			return toJson;
		}
		String pid = getRequest().getParameter("productId");
		Favorite favorite = new Favorite();
		favorite.setProductID(pid);
		favorite.setAccount(acc.getAccount());
		List<Favorite> list = favoriteService.selectList(favorite);
		
		  try {
		   favorite=list.get(0);
			favoriteService.delete(favorite);
			jsonStr="1";
		  } catch (Exception e) {
			  logger.error("deleteOrder..."+e);
		  }
		return toJson;
	}
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2015-12-27下午02:43:18
	 * 描述: 跳转到个人中心页面
	 * @return
	 */
	public String toUserMain(){
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
			return toLogin;
		}
		//查询收藏商品数量
		Favorite favorite = new Favorite();
		favorite.setAccount(acc.getAccount());
		int f=  this.favoriteService.selectCount(favorite);
		this.getRequest().setAttribute("fcount", f);
		Order e = new Order();
		e.setAccount(acc.getAccount());
		e.setStatus(net.jeeshop.services.common.Order.order_status_init);
		e.setPaystatus(net.jeeshop.services.common.Order.order_paystatus_n);
		f = this.orderService.selectCount(e);
		this.getRequest().setAttribute("dfkcount", f);//待付款
		e = new Order();
		e.setAccount(acc.getAccount());
		e.setStatus(net.jeeshop.services.common.Order.order_status_pass);
		e.setPaystatus(net.jeeshop.services.common.Order.order_paystatus_y);
		f = this.orderService.selectCount(e);
		this.getRequest().setAttribute("dfahcount", f);//待发货
		e = new Order();
		e.setAccount(acc.getAccount());
		e.setStatus(net.jeeshop.services.common.Order.order_status_send);
		e.setPaystatus(net.jeeshop.services.common.Order.order_paystatus_y);
		f = this.orderService.selectCount(e);
		this.getRequest().setAttribute("dshcount", f);//待收货
		
		e = new Order();
		e.setAccount(acc.getAccount());
		e.setClosedComment("n");
		e.setPaystatus(net.jeeshop.services.common.Order.order_paystatus_y);
		f = this.orderService.selectCount(e);
		this.getRequest().setAttribute("dpjcount", f);//待评价
		
		return "toUserMain";
	}
	public String toShowFavorite(){
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
			return toLogin;
		}
		return "toShowFavorite";
	}
	/**
	 * 地址管理
	 */
	@SuppressWarnings("unchecked")
	public String getAddressJson(){
		String offset = this.getRequest().getParameter("offset");
		Address address = new Address();
		if(StringUtils.isNotBlank(offset)){
			address.setOffset(Integer.parseInt(offset)*pageSize);
		}else{
			address.setOffset(0);
		}
		Account account = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (account == null || StringUtils.isBlank(account.getAccount())) {
			return toLogin;
		}
		selectLeftMenu = "address";
		address.setAccount(account.getAccount());
		address.setPagerSize(pageSize);
		pager = addressService.selectPageList(address); 
		
		//PagerModel commentPager = super.selectPagerModelByServices(favoriteService, favorite);
		//super.pager = commentPager;//公用分页控件需要这么写。
		if(pager==null)pager = new PagerModel();
		// 计算总页数
		address.setPagerSize((address.getTotal() + address.getPageSize() - 1)
				/ address.getPageSize());
		addressList = getPager().getList();
		Address defAddress = new Address();
		int k =0;
		for(int i=0;i<addressList.size();i++){
			Address addr = addressList.get(i);
			if(addr.getIsdefault().equals("y")){
				defAddress = addr;
				addressList.remove(i);
				k=1;
				break;
			}
		}
		Map<String, Object>addrMap = new HashMap<String, Object>();
		addrMap.put("defaultAddress", defAddress);
		if(k==0){
			addrMap.put("defaultAddress", "");
		}
		addrMap.put("address", addressList);
		if(addrMap!=null && addrMap.size()>0){
			JSONObject jsonObject = JSONObject.fromObject(addrMap);
			jsonStr = jsonObject.toString();
		}else{
			jsonStr = "0";
		}
		return "toJson";
	}
	/**
	 * 转到地址详情页面
	 * @return
	 */
    public String toAddressInfo(){
    	String id = getRequest().getParameter("id");
    	Address addr = new Address();
    	addr.setId(id);
    	address=addressService.selectOne(addr);
    	getRequest().setAttribute("addr", address);
		return "toAddressInfo";
	}
    /**
     * 更新地址
     * @return
     */
    public String updateAddr(){
    	StringBuilder pcadetail = new StringBuilder();
    	String id = getRequest().getParameter("e.id");
    	String name = getRequest().getParameter("e.name");
    	String mobile = getRequest().getParameter("e.mobile");
    	String isdefault = getRequest().getParameter("e.isdefault");
    	String province = getRequest().getParameter("e.province");
    	String city = getRequest().getParameter("e.city");
    	String area = getRequest().getParameter("e.area");
    	String address = getRequest().getParameter("e.address");
    	Account acc = (Account)getSession().getAttribute(FrontContainer.USER_INFO);
    	if (acc == null || StringUtils.isBlank(acc.getAccount())) {
			return toLogin;
		}
    	Address addr = new Address();
    	if(province!=null && city!=null && area!=null && province!="" && city!="" && area!=""){
    	Area sheng = SystemManager.areaMap.get(province);//省
		pcadetail.append(sheng.getName());
		
		for(int i=0;i<sheng.getChildren().size();i++){
			Area shi = sheng.getChildren().get(i);//市
			if(shi.getCode().equals(city)){
				
				pcadetail.append(" ").append(shi.getName());
				
				for(int j = 0;j<shi.getChildren().size();j++){
					Area qu = shi.getChildren().get(j);//区
					if(qu.getCode().equals(area)){
						pcadetail.append(" ").append(qu.getName());
						break;
					}
				}
				
				break;
			}
		}
		addr.setProvince(province);
		addr.setCity(city);
    	addr.setArea(area);
		addr.setPcadetail(pcadetail.toString());
    	}
    	addr.setId(id);
    	addr.setName(name);
    	addr.setMobile(mobile);
    	addr.setIsdefault(isdefault);
    	addr.setAddress(address);
    	addr.setAccount(acc.getAccount());
    	if(isdefault!=null && isdefault!=""){
    		addressService.setAddressDefault(addr);
    	}
    	else{
    		addressService.update(addr);
    	}
    	return "toAddressList";
    }
    /**
     * 删除地址
     * @return
     */
    public String deleteAddress(){
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
			return toLogin;
		}
		selectLeftMenu = "address";
		String id = getRequest().getParameter("id");
		if(StringUtils.isBlank(id)){
			throw new NullPointerException("id is null!");
		}
		Address add = new Address();
		add.setId(id);
		addressService.delete(add);
		
		///String type=getRequest().getParameter("type");
		//if (StringUtils.isNotBlank(type)) {
		//	return type;
		//}else{
			return "toAddressList";
		//}
	}
	public String toAddressList(){
		
		return "toAddressList";
	}
	/**
	 * 新添加地址
	 * @return
	 */
	public String saveAddress1(){
		System.out.println("into the saveAddress。。。。。。。");
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if (acc == null || StringUtils.isBlank(acc.getAccount())) {
			return toLogin;
		}
		selectLeftMenu = "address";
		
		//需要将省市区的代号换成中文，插入到pcadetail字段里面去，显示的时候方便。
		StringBuilder pcadetail = new StringBuilder();
		Area sheng = SystemManager.areaMap.get(address.getProvince());//省
		pcadetail.append(sheng.getName());
		
		for(int i=0;i<sheng.getChildren().size();i++){
			Area shi = sheng.getChildren().get(i);//市
			if(shi.getCode().equals(address.getCity())){
				
				pcadetail.append(" ").append(shi.getName());
				
				for(int j = 0;j<shi.getChildren().size();j++){
					Area qu = shi.getChildren().get(j);//区
					if(qu.getCode().equals(address.getArea())){
						pcadetail.append(" ").append(qu.getName());
						break;
					}
				}
				
				break;
			}
		}
		
		address.setPcadetail(pcadetail.toString());
		
		address.setAccount(acc.getAccount());
		
		String isdefault=getRequest().getParameter("isdefault");
		
		if(StringUtils.isBlank(address.getId())){
			int resultid=addressService.insert(address);
			
			if(isdefault.equals("y")){
				
				address.setId(String.valueOf(resultid));
				address.setIsdefault("y");
				address.setAccount(acc.getAccount());
				addressService.setAddressDefault(address);
				//CartInfo cartInfo = (CartInfo)this.getSession().getAttribute(FrontContainer.myCart);
				//if(cartInfo==null){
					return "toAddressList";
				//}
				///cartInfo.setDefaultAddessID(String.valueOf(resultid));
				//this.getSession().setAttribute(FrontContainer.myCart, cartInfo);
			}
		}else{
			addressService.update(address);
		}
	/*	String buyCount1 = this.getRequest().getParameter("inputBuyNum");
		String productId = this.getRequest().getParameter("productID");
		getRequest().setAttribute("inputBuyNum", buyCount1);
		getRequest().setAttribute("productID", productId);*/
		address.clear();
		/*String type=getRequest().getParameter("type");
		if (StringUtils.isNotBlank(type)) {
			return type;
		}else{
		}*/
		//address();
		return "toAddressList";
	}
	public String toAddress1(){
		return "toAddress1";
	}
	
	/**
	 * 根据省份编码获取城市列表
	 * @return
	 * @throws IOException 
	 */
	public String selectCitysByProvinceCode() throws IOException{
		logger.error("selectCitysByProvinceCode...");
		String provinceCode = getRequest().getParameter("provinceCode");
		logger.error("selectCitysByProvinceCode...provinceCode="+provinceCode);
		if(StringUtils.isBlank(provinceCode)){
			throw new NullPointerException("provinceCode is null");
		}
		
//		Area area = new Area();
//		area.setCode(provinceCode);
		if(SystemManager.areaMap!=null && SystemManager.areaMap.size()>0){
			Area areaInfo = SystemManager.areaMap.get(provinceCode);
			
			logger.error("areaInfo = " + areaInfo);
			
			if(areaInfo!=null && areaInfo.getChildren()!=null && areaInfo.getChildren().size()>0){
				String jsonStr = JSON.toJSONString(areaInfo.getChildren());
				logger.error("jsonStr="+jsonStr);
				super.utf8JSON();
				getResponse().getWriter().write(jsonStr);
				return null;
			}
		}
		
		getResponse().getWriter().write("{}");
		return null;
	}

	/**
	 * 根据城市编码获取区域列表
	 * @return
	 * @throws IOException 
	 */
	public String selectAreaListByCityCode() throws IOException{
		logger.error("selectAreaListByCityCode...");
		String provinceCode = getRequest().getParameter("provinceCode");
		String cityCode = getRequest().getParameter("cityCode");
		logger.error("selectAreaListByCityCode...provinceCode="+provinceCode+",cityCode="+cityCode);
		if(StringUtils.isBlank(provinceCode) || StringUtils.isBlank(cityCode)){
			throw new NullPointerException("provinceCode or cityCode is null");
		}
		
		if(SystemManager.areaMap!=null && SystemManager.areaMap.size()>0){
			Area city = SystemManager.areaMap.get(provinceCode);
			
			logger.error("areaInfo = " + city);
			
			if(city!=null && city.getChildren()!=null && city.getChildren().size()>0){
				for(int i=0;i<city.getChildren().size();i++){
					Area item = city.getChildren().get(i);
					if(item.getCode().equals(cityCode)){
						if(item.getChildren()!=null && item.getChildren().size()>0){
							String jsonStr = JSON.toJSONString(item.getChildren());
							logger.error("jsonStr="+jsonStr);
							super.utf8JSON();
							getResponse().getWriter().write(jsonStr);
							return null;
						}
					}
				}
			}
		}
		
		getResponse().getWriter().write("{}");
		return null;
	}
	/**
	 * 获取省份编码
	 * @return
	 * @throws IOException
	 */
	
	public String ProvinceCode() throws IOException{
		//logger.error("selectCitysByProvinceCode...");
		//String provinceCode = getRequest().getParameter("provinceCode");
		//logger.error("selectCitysByProvinceCode...provinceCode="+provinceCode);
		//if(StringUtils.isBlank(provinceCode)){
	//		throw new NullPointerException("provinceCode is null");
		//}
		
//		Area area = new Area();
//		area.setCode(provinceCode);
		if(SystemManager.areaMap!=null && SystemManager.areaMap.size()>0){
			Map<String, Area>addrMap = SystemManager.areaMap;
			
			//logger.error("areaInfo = " + areaInfo);
			
			if(addrMap!=null){
				JSONObject jsonObject = JSONObject.fromObject(addrMap);
				jsonStr = jsonObject.toString();
				getResponse().getWriter().write(jsonStr);
				return null;
			}
		}
		
		getResponse().getWriter().write("{}");
		return null;
	}

	
}