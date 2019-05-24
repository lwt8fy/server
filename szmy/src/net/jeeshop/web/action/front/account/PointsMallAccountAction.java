package net.jeeshop.web.action.front.account;

import java.io.IOException;
import java.util.LinkedHashMap;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.FrontContainer;
import net.jeeshop.core.util.MD5;
import net.jeeshop.services.front.account.AccountService;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.front.product.bean.Product;
import net.jeeshop.web.action.front.orders.CartInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 积分商城用户
 * @author jin
 *
 */
public class PointsMallAccountAction extends BaseAction<Account>{
	private static final Logger logger = Logger.getLogger(AccountAction.class);
	private static final long serialVersionUID = 1L;
	private static final String toLogin = "toLogin";//转到登陆界面,forword方式 地址不变
	@SuppressWarnings("unused")
	private static final String toLoginRedirect = "toLoginRedirect";//转到登陆界面,getResponse().sendRedirect(arg0)方式 地址变化
	private static final String toIndex = "toIndex";//转到门户首页
	private static final String toJson = "toJson";
	private AccountService accountService;

	public AccountService getAccountService() {
		return accountService;
	}
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	private String errorMsg;
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public Account getE() {
		return this.e;
	}

	@Override
	public void insertAfter(Account e) {
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
	}
	
	public String points(){
		getSession().setAttribute("jf_Menu","shouye");
		return "toIndex";
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
		getSession().setAttribute(FrontContainer.USER_INFO, acc);
		e.clear();
		if(StringUtils.isNotBlank(url)&&!"null".equals(url)){
			this.getResponse().sendRedirect(url);
			return  null;
		}
		return toIndex;
	}
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
		if(history_product_map!=null){
			history_product_map.clear();
		}
		getSession().setAttribute(FrontContainer.history_product_map,null);
		
		return toIndex;
	}
}
