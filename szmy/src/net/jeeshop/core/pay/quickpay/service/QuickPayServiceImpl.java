package net.jeeshop.core.pay.quickpay.service;

public class QuickPayServiceImpl implements IQuickPayService {
	/**
	 * 支付请求
	 * @param trade_order_no 交易订单号
	 * @param account_bank	开户行
	 * @param account_number 开户账号
	 * @param trade_price	交易金额
	 * @param trade_status	交易状态
	 */
	@Override
	public String paymentReq(String account_bank,String account_number,String trade_order_no,int trade_price,String trade_status){
		System.out.println("*************************success");
		return "success";
	}
}
