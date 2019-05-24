package net.jeeshop.services.front.quote.dao;

import java.util.List;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.common.Quote;


public interface QuoteDao extends DaoManager <Quote> {
	public int updateAll(List<Quote> list);
	
	public void updatePrice() throws Exception;
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2016-1-20下午05:41:19
	 * 描述: 查询报价信息
	 * @param type
	 * @return
	 */
	public List<Quote> getQuoteList(String type);
		
}
