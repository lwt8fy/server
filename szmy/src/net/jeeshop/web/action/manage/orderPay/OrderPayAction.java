package net.jeeshop.web.action.manage.orderPay;

import java.util.Date;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.manage.orderpay.OrderpayService;
import net.jeeshop.services.manage.orderpay.bean.Orderpay;

import org.slf4j.LoggerFactory;

public class OrderPayAction extends BaseAction<Orderpay> {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(OrderPayAction.class);
	private AppUserService appUserService;
	private OrderpayService orderPayService;
	private String isFirst;
	
	

	public void setIsFirst(String isFirst) {
		this.isFirst = isFirst;
	}

	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}

	public void setOrderPayService(OrderpayService orderPayService) {
		this.orderPayService = orderPayService;
	}
	@Override
	public String selectList() throws Exception {
		// TODO Auto-generated method stub
		if(isFirst!=null&&isFirst.equals("1")){
			String date = DateUtil.dateToStr(new Date());
			e.setStartDate(DateUtil.getYear(date)+"-"+DateUtil.getMonth(date)+"-01");
		}
		return super.selectList();
	}


	@Override
	public void insertAfter(Orderpay e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if (this.e == null) {
			this.e = new Orderpay();
		}
	}

	@Override
	protected void selectListAfter() {
		// TODO Auto-generated method stub

	}

	@Override
	public Orderpay getE() {
		// TODO Auto-generated method stub
		return this.e;
	}


	
}
