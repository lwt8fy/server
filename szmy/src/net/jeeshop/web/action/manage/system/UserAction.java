package net.jeeshop.web.action.manage.system;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.FrontContainer;
import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.ManageContainer;
import net.jeeshop.core.exception.NotThisMethod;
import net.jeeshop.core.oscache.ManageCache;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.core.util.AddressUtils;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.core.util.MD5;
import net.jeeshop.services.front.account.AccountService;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.front.product.bean.Product;
import net.jeeshop.services.manage.comment.CommentService;
import net.jeeshop.services.manage.company.CompanyService;
import net.jeeshop.services.manage.company.bean.Company;
import net.jeeshop.services.manage.order.OrderService;
import net.jeeshop.services.manage.order.bean.OrdersReport;
import net.jeeshop.services.manage.product.ProductService;
import net.jeeshop.services.manage.system.impl.RoleService;
import net.jeeshop.services.manage.system.impl.UserService;
import net.jeeshop.services.manage.systemlog.SystemlogService;
import net.jeeshop.services.manage.systemlog.bean.Systemlog;
import net.jeeshop.web.action.front.orders.CartInfo;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 后台用户管理
 * @author huangf
 *  
 */
public class UserAction extends BaseAction<User> implements
		ModelDriven<User> {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserAction.class);

	private static final long serialVersionUID = 1L;
	
	private UserService userService; 
	private RoleService roleService;
	private SystemlogService systemlogService;
	private String errorMsg;
	private List roleList;//角色列表-编辑用户用到
	private ManageCache manageCache;
	private CompanyService companyService;
	
	private OrderService orderService;
	private ProductService productService;
	private CommentService commentService;
	//private String prerenyer;
	//private String pastDue;
	//public String getPastDue() {
	//	return pastDue;
	//}

	//public void setPastDue(String pastDue) {
	//	this.pastDue = pastDue;
	//}

	//public String getPrerenyer() {
	//	return prerenyer;
	//}

	//public void setPrerenyer(String prerenyer) {
	//	this.prerenyer = prerenyer;
	//}
	private AccountService accountService;
	
	// getter setter
	
	
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public List getRoleList() {
		return roleList;
	}
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ManageCache getManageCache() {
		return manageCache;
	}

	public void setManageCache(ManageCache manageCache) {
		this.manageCache = manageCache;
	}

	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public User getE() {
		return this.e;
	}

	@Override
	public User getModel() {
		return this.e;
	}

	@Override
	public void insertAfter(User e) {
		e.clear();
	}
	public void setSystemlogService(SystemlogService systemlogService) {
		this.systemlogService = systemlogService;
	}
	
	@Override
	public void prepare() throws Exception {
		if(this.e==null){
			this.e = new User();
		}
		
		super.initPageSelect();
	}

	@Override
	public String selectList() throws Exception {
		// TODO Auto-generated method stub]
		User u=(User)getSession().getAttribute(ManageContainer.manage_session_user_info);
	//	if(u==null||!u.getRid().equals("1"))
		if(u==null){
			return INPUT;
		}
		return super.selectList();
	}
	/**
	 * 后台登录
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		
//		if (getSession().getAttribute(ManageContainer.manage_session_user_info) != null) {
//			return SUCCESS;
//		}
		
		errorMsg = "<font color='red'>帐号或密码错误!</font>";
		if (StringUtils.isBlank(e.getUsername()) || StringUtils.isBlank(e.getPassword())){
			getSession().setAttribute(ManageContainer.loginError,"账户和密码不能为空!");
			return INPUT;
		}
		String vcode = getRequest().getParameter("vcode");
		if(StringUtils.isNotBlank(vcode)){//验证验证码输入的是否正确
			String validateCode = getSession().getAttribute(FrontContainer.validateCode).toString();
			if(!validateCode.equalsIgnoreCase(vcode)){
				getSession().setAttribute(ManageContainer.loginError,"验证码输入错误!");
				return INPUT;
			}
		}else{
			getSession().setAttribute(ManageContainer.loginError,"请输入验证码!");
			return INPUT;
		}
		
		e.setPassword(MD5.md5(e.getPassword()));
		
		e.setId(null);
		e.setRid(null);
		e.setStatus(null);
		
		User u = userService.login(e);
		if (u == null) {
			logger.error("登陆失败，账号不存在！");
			getSession().setAttribute(ManageContainer.loginError, errorMsg);
			return INPUT;
		}else if(u.getFreeze().equals(Account.account_freeze_y)){
			if(StringUtils.isBlank(u.getFreezeStartdate()) && StringUtils.isBlank(u.getFreezeEnddate())){
				getSession().setAttribute(ManageContainer.loginError, "<font color='red'>此账号已永久冻结!有疑问请联系站点管理员!</font>");
			}else{
				getSession().setAttribute(ManageContainer.loginError, "<font color='red'>此账号已暂时冻结!有疑问请联系站点管理员!</font>");
			}
			return INPUT;
		}
		u.setUsername(e.getUsername());
		errorMsg = null;
		e.clear();
		getSession().setAttribute(ManageContainer.manage_session_user_info, u);
		Account acc = accountService.selectById(u.getId());
		getSession().setAttribute(FrontContainer.USER_INFO, acc);
		//企业信息
		Company company=new Company();
		company.setCreateAccount(u.getUsername());
		company=companyService.selectOne(company);
		//if(company!=null){
			getSession().setAttribute(ManageContainer.manage_session_company_info, company);
		//}
		
		//解析用户的数据库权限，以后可以进行DB权限限制
		if(StringUtils.isNotBlank(u.getRole_dbPrivilege())){
			String[] dbPriArr = u.getRole_dbPrivilege().split(",");
			if(u.getDbPrivilegeMap()==null){
				u.setDbPrivilegeMap(new HashMap<String, String>());
			}else{
				u.getDbPrivilegeMap().clear();
			}
			
			if(dbPriArr.length!=0){
				for(int i=0;i<dbPriArr.length;i++){
					u.getDbPrivilegeMap().put(dbPriArr[i], dbPriArr[i]);
				}
			}
		}
		
		try {
			loginLog(u,"login");
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * teng
		 * 用户登录加载配置文件system.properties文件中的审核角色id
		 */
		getSession().setAttribute("checkRid", "1");
		getSession().setAttribute("checkProductRid", KeyValueHelper.get("checkProductRid"));
		getSession().setAttribute("checkProductRid2", KeyValueHelper.get("checkProductRid2"));
		//////////////////////////////////
		return SUCCESS;
	}
	
	private void loginLog(User u,String log) {
		Systemlog systemlog = new Systemlog();
		systemlog.setTitle(log);
		systemlog.setContent(log);
		systemlog.setAccount(u.getUsername());
		systemlog.setType(1);
		systemlog.setLoginIP(AddressUtils.getIp(getRequest()));
		
		String address = null;
		/*
		 * teng
		 * 2015.05.22
		 * 去掉访问ip接口查询,后台登录过慢问题
		 */
		if(false&&!systemlog.getLoginIP().equals("127.0.0.1") && !systemlog.getLoginIP().equals("localhost")){
			//获取指定IP的区域位置
			try {
				address = AddressUtils.getAddresses("ip=" + systemlog.getLoginIP(), "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			systemlog.setLoginArea(address);
			
			//异地登陆的判断方法为：先比较本次登陆和上次登陆的区域位置，如果不一致，说明是异地登陆；如果获取不到区域，则比较IP地址，如果IP地址和上次的不一致，则是异地登陆
			Systemlog firstSystemlog = systemlogService.selectFirstOne(u.getUsername());
			if(firstSystemlog!=null){
				if(StringUtils.isNotBlank(address) && StringUtils.isNotBlank(firstSystemlog.getLoginArea())){
					if(!address.equals(firstSystemlog.getLoginArea())){
						systemlog.setDiffAreaLogin(net.jeeshop.services.common.Systemlog.systemlog_diffAreaLogin_y);
					}
				}else if(StringUtils.isNotBlank(systemlog.getLoginIP()) && StringUtils.isNotBlank(firstSystemlog.getLoginIP())){
					if(!systemlog.getLoginIP().equals(firstSystemlog.getLoginIP())){
						systemlog.setDiffAreaLogin(net.jeeshop.services.common.Systemlog.systemlog_diffAreaLogin_y);
					}
				}
			}
		}
		
		systemlogService.insert(systemlog);
	}
	
	/**
	 * 添加用户
	 */
	@Override
	public String insert() throws Exception {
	//	e.setPresenter(accountService.selectById(KeyValueHelper.get("PTSYZH")).getAccount());
	//  e.setPastDue(DateUtil.dateToStr(DateUtil.addMonth(new Date(), 3),"yyyy-MM-dd HH:mm:ss"));

		return save0();
	}

	/**
	 * 修改用户信息
	 */
	@Override
	public String update() throws Exception {
		return save0();
	}

	private String save0() throws Exception {
		logger.error("save0..."+e.getPassword()+","+e.getNewpassword2());
		
		if(StringUtils.isBlank(e.getId())){//添加
			if(StringUtils.isBlank(e.getPassword()) || StringUtils.isBlank(e.getNewpassword2())){
				throw new NullPointerException("输入的密码不符合要求！");
			}
			
			if(!e.getPassword().equals(e.getNewpassword2())){
				throw new IllegalArgumentException("两次输入的密码不一致！");
			}
			
			User user = (User)getSession().getAttribute(ManageContainer.manage_session_user_info);
			if(StringUtils.isBlank(e.getStatus())){
				e.setStatus(User.user_status_y);
			}
			e.setPassword(MD5.md5(e.getPassword()));
			e.setPresenter(accountService.selectById(KeyValueHelper.get("PTSYZH")).getAccount());
			e.setPastDue(DateUtil.dateToStr(DateUtil.addMonth(new Date(), 3),"yyyy-MM-dd HH:mm:ss"));
			getServer().insert(e);
			getRequest().setAttribute("message", "新增用户成功!");
		}else{//修改
			
			//当前登录用户是admin，才能修改admin的信息，其他用户修改admin信息都属于非法操作。
			User user = (User)getSession().getAttribute(ManageContainer.manage_session_user_info);
			if(!user.getUsername().equals("admin") && e.getUsername().equals("admin")){
				throw new RuntimeException("操作非法！");
			}
			if(!user.getRid().equals("1") && !e.getRid().equals("2")){
				throw new RuntimeException("操作非法！");
			}
			//if(e.getPresenter().equals("0")){
			//	e.setPresenter(accountService.selectById(KeyValueHelper.get("PTSYZH")).getAccount());
			//	e.setPastDue(DateUtil.dateToStr(DateUtil.addMonth(new Date(), 3),"yyyy-MM-dd HH:mm:ss"));
			//}
			String presenter = getRequest().getParameter("presenter");
			if(presenter==null||presenter.equals("")){
				Account u = accountService.selectById(e.getId());
				if(u.getPresenter()==null||u.getPresenter().equals("")||u.getPresenter().equals("0")){
					e.setPresenter(accountService.selectById(KeyValueHelper.get("PTSYZH")).getAccount());
					e.setPastDue(DateUtil.dateToStr(DateUtil.addMonth(new Date(), 3),"yyyy-MM-dd HH:mm:ss"));
				}
				
			}else if(presenter.equals("0")){
				e.setPresenter("0");
				e.setPastDue("2999-12-31");
			}
			
			getServer().update(e);
			//if(e.getPresenter().equals("0")){
			//	e.setPastDue("2999-12-31");
			//}
			getRequest().setAttribute("message", "修改用户成功!");
			e=new User();
		}
		return super.selectList();
	}
	/**
	 * 商家自己注册用户
	 * @return
	 */
	public String regUser(){
		if(StringUtils.isBlank(e.getPassword()) || StringUtils.isBlank(e.getNewpassword2())){
			getRequest().setAttribute("message", "输入的密码不符合要求！");
			return "toRegUser";
		}
		
		if(!e.getPassword().equals(e.getNewpassword2())){
			getRequest().setAttribute("message", "两次输入的密码不一致！");
			return "toRegUser";
		}
		
		String vcode = getRequest().getParameter("vcode");
		if(StringUtils.isNotBlank(vcode)){//验证验证码输入的是否正确
			String validateCode = getSession().getAttribute(FrontContainer.validateCode).toString();
			if(!validateCode.equalsIgnoreCase(vcode)){
				getRequest().setAttribute("message", "验证码输入错误!");
				return "toRegUser";
			}
		}else{
			getRequest().setAttribute("message", "验证码不能为空!");
			return "toRegUser";
		}
		e.setRid("2");
		e.setStatus(User.user_status_y);
		e.setPassword(MD5.md5(e.getPassword()));
		getServer().insert(e);
		User u=e;
		getSession().setAttribute(ManageContainer.manage_session_user_info, u);
		
		
		//解析用户的数据库权限，以后可以进行DB权限限制
		if(StringUtils.isNotBlank(u.getRole_dbPrivilege())){
			String[] dbPriArr = u.getRole_dbPrivilege().split(",");
			if(u.getDbPrivilegeMap()==null){
				u.setDbPrivilegeMap(new HashMap<String, String>());
			}else{
				u.getDbPrivilegeMap().clear();
			}
			
			if(dbPriArr.length!=0){
				for(int i=0;i<dbPriArr.length;i++){
					u.getDbPrivilegeMap().put(dbPriArr[i], dbPriArr[i]);
				}
			}
		}
		
		try {
			loginLog(u,"login");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		getRequest().setAttribute("message", "regSuccess");
		
		return "regSuccess"; 
	}
	
	public String loginOut() throws Exception {
		User u = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
		if(u!=null && StringUtils.isNotBlank(u.getUsername())){
			loginLog(u,"loginOut");
		}
		
		getSession().setAttribute(ManageContainer.manage_session_user_info,null);
		getSession().setAttribute(ManageContainer.resource_menus,null);
		getSession().setAttribute(ManageContainer.user_resource_menus_button,null);
		e.clear();
		
		//清除用户前台session
		
		getSession().setAttribute(FrontContainer.USER_INFO, null);
		getSession().setAttribute(ManageContainer.manage_session_user_info, null);
		
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
		
		
		return Action.INPUT;
	}

	/**
	 * ajax验证输入的字符的唯一性
	 * @return
	 * @throws IOException
	 */
	public String unique() throws IOException{
		logger.error("验证输入的字符的唯一性"+e);
		getResponse().setCharacterEncoding("utf-8");
		synchronized (this) {
			if(StringUtils.isNotBlank(e.getNickname())){//验证昵称是否被占用
				logger.error("验证昵称是否被占用");
				User user = new User();
				user.setNickname(e.getNickname());
				
//				if(userService.selectCount(e)>0){
//					getResponse().getWriter().write("{\"error\":\"昵称已经被占用!\"}");
//				}else{
//					getResponse().getWriter().write("{\"ok\":\"昵称可以使用!\"}");
//				}
				
				user = userService.selectOneByCondition(user);
				
				if(user==null){
					//数据库中部存在此编码
					getResponse().getWriter().write("{\"ok\":\"昵称可以使用!\"}");
				}else{
					if(StringUtils.isBlank(e.getId())){
						//当前为insert操作，但是编码已经存在，则只可能是别的记录的编码
						getResponse().getWriter().write("{\"error\":\"昵称已经存在!\"}");
					}else{
						//update操作，又是根据自己的编码来查询的，所以当然可以使用啦
						getResponse().getWriter().write("{\"ok\":\"昵称可以使用!\"}");
					}
				}
			}else 
				if(StringUtils.isNotBlank(e.getNxbUserName())){//验证绑定支付用户名
					User acc = (User) getSession().getAttribute(
							ManageContainer.manage_session_user_info);
					
					String pname=e.getNxbUserName();
					getResponse().setCharacterEncoding("utf-8");
					if(pname.equals(acc.getNxbUserName())){
						getResponse().getWriter().write("{\"ok\":\"帐号可以使用!\"}");
					}else{
						e.clear();
						e.setNxbUserName(pname);
						if(userService.selectOne(e)!=null){
							getResponse().getWriter().write("{\"error\":\"帐号已经被占用!\"}");
						}else{
							getResponse().getWriter().write("{\"ok\":\"帐号可以使用!\"}");
						}
					}
					
				}else
				if(StringUtils.isNotBlank(e.getUsername())){//验证用户名是否被占用
				logger.error("验证账号是否被占用");
				getResponse().setCharacterEncoding("utf-8");
				User user = new User();
				user.setUsername(e.getUsername());
				if(userService.selectCount(user)>0){
					getResponse().getWriter().write("{\"error\":\"账号已经被占用!\"}");
				}else{
					getResponse().getWriter().write("{\"ok\":\"账号可以使用!\"}");
				}
			}
		}
		return null;
	}
//	@Override
//	protected void toEditBefore(User e) {
//		String id = getRequest().getParameter("id");
//		if (id!=null) {
//			e.clear();
//			e.setId(id);
//			e = getServer().selectOne(e);
//		}else{
//			e.clear();
//		}
//	}
	
	/**
	 * 转到修改密码页面
	 * @return
	 */
	public String toChangePwd(){
		User u = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
		this.e.setId(u.getId());
		return "toChangePwd";
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	public String updateChangePwd(){
		User u = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
		if(!u.getId().equals(e.getId())){
			throw new IllegalArgumentException("非法请求！");
		}
		
		errorMsg = "两次输入的密码不一致，修改密码失败!";
		if(StringUtils.isBlank(e.getNewpassword()) || StringUtils.isBlank(e.getNewpassword2())){
			throw new NullPointerException("密码不能为空！");
		}
		
		if(!e.getNewpassword().equals(e.getNewpassword2())){
			throw new IllegalArgumentException("两次输入的密码不一致！");
		}
		
		errorMsg = "旧密码输入错误，修改密码失败!";
		
		
		e.setPassword(MD5.md5(e.getPassword()));
		if(!e.getPassword().equals(u.getPassword())){//用户输入的旧密码和数据库中的密码一致
			throw new IllegalArgumentException("原密码不正确！");
		}
		
		//修改密码
		e.setPassword(MD5.md5(e.getNewpassword()));
		this.getServer().update(e);
		errorMsg = "密码修改成功!";
		u.setPassword(MD5.md5(e.getNewpassword()));
		getSession().setAttribute(ManageContainer.manage_session_user_info, u);
		return "changePwd";
	}
	
	/**
	 * 禁止删除系统超级管理员帐号。
	 * 只允许超级管理员admin使用此功能
	 */
	@Override
	public String deletes()throws Exception {
		//throw new NotThisMethod(ManageContainer.not_this_method);
		User u = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
		if(!u.getUsername().equals("admin")){
			throw new NotThisMethod(ManageContainer.not_this_method);
		}else{
			if(getIds()!=null && getIds().length > 0){
				for(int i=0;i<getIds().length;i++){
					e.clear();
					e.setId(getIds()[i]);
					e = this.getServer().selectOne(e);
					if(e!=null && StringUtils.isNotBlank(e.getUsername())){
						if(e.getUsername().equals("admin")){
							getRequest().setAttribute("message", "禁止删除超级管理员帐号!");
							return selectList();
						}
					}
				}
				e.clear();
				userService.deletes(ids);
				getRequest().setAttribute("message", "删除成功!");
			}
			return selectList();
			
		}
		
	}
	
	@Override
	public String toAdd() throws Exception {
		roleList = roleService.selectList(null);
		return super.toAdd();
	}
	@Override
	protected void selectListAfter() {
		pager.setPagerUrl("user!selectList.action");
	}
	public String toRegUser(){
		roleList = roleService.selectList(null);
		return "toRegUser";
	}
	/**
	 * 编辑用户
	 */
	@Override
	public String toEdit() throws Exception {
		roleList = roleService.selectList(null);
		
		e = getServer().selectOne(e);
//		if(getRequest().getParameter("id")==null){
//			e.clear();
//		}else{
//			e.setId(getRequest().getParameter("id"));
//			e = getServer().selectOne(e);
//		}
		
		return toEdit;
	}
	
	/**
	 * 查看管理人员信息
	 * @return
	 */
	public String show(){
		String account = getRequest().getParameter("account");
		if(StringUtils.isBlank(account)){
			throw new NullPointerException("非法请求！");
		}
		
		e.setUsername(account);
		e = getServer().selectOne(e);
		return super.show;
	}

	/**
	 * 用户修改密码--验证旧密码是否正确
	 * @return
	 */
	public String checkOldPassword() throws Exception{
		logger.error("checkOldPassword.."+e.getPassword());
		if(StringUtils.isBlank(e.getPassword())){
			super.utf8JSON();
			getResponse().getWriter().write("{\"error\":\"旧密码不能为空!\"}");
		}else{
			//检查旧密码输入的是否正确
			User user = (User)getSession().getAttribute(ManageContainer.manage_session_user_info);
			String oldPass = MD5.md5(e.getPassword());
			if(user.getPassword().equals(oldPass)){
				getResponse().getWriter().write("{\"ok\":\"旧密码输入正确!\"}");
			}else{
				getResponse().getWriter().write("{\"error\":\"旧密码输入错误!\"}");
			}
		}
		return null;
	}

	/**
	 * 加载后台首页数据
	 * @return
	 */
	public String initManageIndex(){
		/*2015.09.07 修改只有admin有首页问题
//		//店主每次登陆后台都需要加载综合统计数据？！还是说每次都触发加载，但是到底加载不加载具体看系统的加载策略？！
//		manageCache.loadOrdersReport();
 */
		User u = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
		if(u==null){
			return INPUT;
		}
		if(u.getRid().equals("1")){
			manageCache.loadOrdersReport();
			return "initManageIndexAdmin";
		}else if(u.getRid().equals("2")){
		
		Company com = (Company) getSession().getAttribute(ManageContainer.manage_session_company_info);
		if(com==null){
			return "msg";
		}
		String compid = com.getId();
		//manageCache.loadOrdersReportByCom(company);
		OrdersReport ordersReport = orderService.loadOrdersReport(compid);
		if(ordersReport==null){
			ordersReport = new OrdersReport();
		}
		//加载缺货商品数
		ordersReport.setOutOfStockProductCount(productService.selectOutOfStockProductCount(compid));
		//加载吐槽评论数 
		ordersReport.setNotReplyCommentCount(commentService.selectNotReplyCount(compid));
		getRequest().setAttribute("ordersReport", ordersReport);
		return "initManageIndex";
		}else{
			return null;
		}
	}
	/**
	 * 转到绑定农信宝页面
	 * @return
	 * @throws IOException 
	 */
	public String toNxbSet() throws IOException{
		User acc = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
		if(acc==null){
			getResponse().sendRedirect(getRequest().getContextPath()+"/user/exit.html");
		}
		getRequest().setAttribute("old", acc.getNxbUserName());
		return "nxbSet";
	}
	/**
	 * 转到绑定农信宝页面
	 * @return
	 * @throws IOException 
	 */
	public String nxbSet() throws IOException{
		User acc = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
		
		if(e.getNxbUserName().equals(acc.getNxbUserName())){
			e.setId(acc.getId());
			e.setNxbPassword(DigestUtils.md5Hex(e.getNxbPassword()));
			userService.update(e);
			acc.setNxbUserName(e.getNxbUserName());
			acc.setNxbPassword(e.getNxbPassword());
			getSession().setAttribute(ManageContainer.manage_session_user_info, acc);
			getRequest().setAttribute("message", "绑定农信宝账号成功!");
			Account ac2 = accountService.selectById(acc.getId());
			getSession().setAttribute(FrontContainer.USER_INFO, ac2);
			
			
		}else{
			User ac=new User();
			ac.setNxbUserName(e.getNxbUserName());
			if(userService.selectOne(ac)!=null){
				getRequest().setAttribute("message", "账号已被占用!");
			}else{
				e.setId(acc.getId());
				e.setNxbPassword(DigestUtils.md5Hex(e.getNxbPassword()));
				userService.update(e);
				acc.setNxbUserName(e.getNxbUserName());
				acc.setNxbPassword(e.getNxbPassword());
				getSession().setAttribute(ManageContainer.manage_session_user_info,acc);
				getRequest().setAttribute("message", "绑定农信宝账号成功!");
				Account ac2 = accountService.selectById(acc.getId());
				getSession().setAttribute(FrontContainer.USER_INFO, ac2);
			}
		}
		
		return toNxbSet();
	}
	
	
}
