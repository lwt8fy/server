package net.jeeshop.services.front.price.service;

import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.services.common.Price;


public interface PriceService extends Services <Price> {
	public List<Price> priceList(String createTime); 
	public Price countryList(String createTime); 
	
}
