package net.jeeshop.services.front.quote.service;

import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.services.common.Quote;


public interface QuoteService extends Services <Quote> {
	public int updateAll(List<Quote> sz,List<Quote> ym,List<Quote> dp) throws Exception;
	
	public void updatePrice() throws Exception;
	public List<Quote> getQuoteList(String type);
}
