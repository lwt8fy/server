package net.jeeshop.services.front.payutil;

import net.jeeshop.services.front.order.bean.Order;


public interface PayUtilService{
	String sendPayRequest(Order order,String url);
}
