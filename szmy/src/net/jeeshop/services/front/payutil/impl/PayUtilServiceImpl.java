package net.jeeshop.services.front.payutil.impl;

import net.jeeshop.core.util.DesEncrypt;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.front.account.dao.AccountDao;
import net.jeeshop.services.front.company.bean.Company;
import net.jeeshop.services.front.company.dao.CompanyDao;
import net.jeeshop.services.front.order.bean.Order;
import net.jeeshop.services.front.order.dao.OrderDao;
import net.jeeshop.services.front.payutil.PayUtilService;
import net.sf.json.JSONObject;

public class PayUtilServiceImpl implements PayUtilService{
	
	private OrderDao orderDao;
	private AccountDao accountDao;
	private CompanyDao companyDao;



	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public String sendPayRequest(Order order,String url) {
		// TODO Auto-generated method stub
		
		Account ac=new Account();
		ac.setAccount(order.getAccount());
		ac = accountDao.selectOne(ac);
		if(ac.getNxbUserName()==null||ac.getNxbUserName().equals("")){
			return "0";
		}
		
		Company com = companyDao.selectById(order.getCompID());
		Account ac2=new Account();
		ac2.setAccount(com.getCreateAccount());
		ac2 = accountDao.selectOne(ac2);
		if(ac2.getNxbUserName()==null||ac2.getNxbUserName().equals("")){
			return "1";
		}
		
		
		
		JSONObject jo=new JSONObject();
		jo.put("orderid", order.getId());
		jo.put("orderPayId", order.getOrderpayID());
		jo.put("remark", order.getRemark());
		jo.put("compName", order.getCompName());
		jo.put("tradePrice", order.getPtotal());
		jo.put("comUserName", ac2.getNxbUserName());
		jo.put("username", ac.getNxbUserName());
		jo.put("password", ac.getNxbPassword());
		System.out.println("sendPayRequest加密前+++++"+jo.toString());
		String mstr = DesEncrypt.getEncString(jo.toString());
		System.out.println("sendPayRequest加密后+++++"+mstr);
		return url+"NXB/szmyService/receivePayRequest?MSG="+mstr;
	}
	public static void main(String[] args) {
//		String encString = DesEncrypt.getEncString("{\"orderid\":\"11126\",\"orderPayId\":\"13496\",\"remark\":\"测试\",\"compName\":\"123\",\"tradePrice\":\"10.00\",\"comUserName\":\"123456\",\"username\":\"111\",\"password\":\"bcbe3365e6ac95ea2c0343a2395834dd\"}");
//		System.out.println(encString);
		//System.out.println(encString.equals("M0RCNzkzNTRBRDk2REVEMzIyMDQ5NDFFQzdENUYwNDY4RTVGOTE1MDFGREREMDI0RUQ5QzNGNDM1MzQ2MTAwNUUzOEY5Qjk5NEE4NUVEMTQwMkUyM0FFRjU0REM0MkY2QkU1RUYwQzg4NUY0Qjk4Q0IyMjA5RjJGNEUwNDVENjM3NkEwM0ExRTVEODlBNEVBMEZEMjdGMTk4NzgyRDc2RjZDMTU2QTQ4MUNGRkYwODhCODU5Mzc4MUZBNDAwNTZFRTE5QUY3ODIzOTNBNEUzOUY1MzVBMDYxQzM4OTQ2REVDRTlEODUzNzA0QUI4MENGMzg2N0IwQkRENjVDQzBCMDhEREQ4RTQ3OTNEQTBBNTVENENBRDQ0Qjk5ODNFRjlFNzE3NTJFOTY4ODVGREExMjE2Q0ZDMzM3RTdGQTJFNDJDREZCRDFDRTEzMDkyNUQ5OUQwQzRCRTQ0NTg1Qjg4RjRBNTc5QzBGMzZEN0Y4Qzk2NDYxMzhEMTlERkQ4NzA5RjNERUU0MDk0OUU0NzQwMkUzQTEyNjQyNTczMjEyNDUzNzQ2QzNBRjkyNjE0NzBDRTlEMjk1QzU2NTA2QTRBRQ=="));
		String encString="M0RCNzkzNTRBRDk2REVEMzIyMDQ5NDFFQzdENUYwNDY4RTVGOTE1MDFGREREMDI0RUQ5QzNGNDM1MzQ2MTAwNUUzOEY5Qjk5NEE4NUVEMTQwMkUyM0FFRjU0REM0MkY2QkU1RUYwQzg4NUY0Qjk4Q0IyMjA5RjJGNEUwNDVENjM3NkEwM0ExRTVEODlBNEVBMEZEMjdGMTk4NzgyRDc2RjZDMTU2QTQ4MUNGRkYwODhCODU5Mzc4MUZBNDAwNTZFRTE5QUY3ODIzOTNBNEUzOUY1MzVBMDYxQzM4OTQ2REVDRTlEODUzNzA0QUI4MENGMzg2N0IwQkRENjVDQzBCMDhEREQ4RTQ3OTNEQTBBNTVENENBRDQ0Qjk5ODNFRjlFNzE3NTJFOTY4ODVGREExMjE2Q0ZDMzM3RTdGQTJFNDJDREZCRDFDRTEzMDkyNUQ5OUQwQzRCRTQ0NTg1Qjg4RjRBNTc5QzBGMzZEN0Y4Qzk2NDYxMzhEMTlERkQ4NzA5RjNERUU0MDk0OUU0NzQwMkUzQTEyNjQyNTczMjEyNDUzNzQ2QzNBRjkyNjE0NzBDRTlEMjk1QzU2NTA2QTRBRQ==";
		String desString = DesEncrypt.getDesString(encString);
		System.out.println(desString);
	}
	
}
