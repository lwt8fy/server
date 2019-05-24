package net.jeeshop.core.pay.quickpay.service;

import java.net.URL;

import org.codehaus.xfire.client.Client;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// try {
		// Service serviceModel = new
		// ObjectServiceFactory().create(IQuickPayService.class);
		// IQuickPayService service = (IQuickPayService) new
		// XFireProxyFactory().create(serviceModel,
		// "http://localhost:9090/jeeshop/services/QuickPayService") ;
		// String trade_order_no="";
		// String account_bank="";
		// String account_number="";
		// String trade_price="";
		// String trade_status="";
		// String result="fail";
		// result=service.paymentReq(trade_order_no, account_bank,
		// account_number, trade_price, trade_status) ;
		// if(result.equals("success")){
		// System.out.println("支付成功！");
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		String wsURL = "http://localhost:9090/jeeshop/services/QuickPayService?wsdl";
		try {
			String trade_order_no = "11";
			String account_bank = "";
			String account_number = "";
			String trade_price = "";
			String trade_status = "";
			System.out.println(" ----> 初始客户端 <---- ");
			Client client = new Client(new URL(wsURL));
			Object[] results = client.invoke("paymentReq",
					new Object[] {trade_order_no, account_bank,account_number, trade_price, trade_status });
			System.out.println(results[0]);
			System.out.println(" ----> 客户端调用结束 <---- ");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
