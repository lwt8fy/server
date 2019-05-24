package net.jeeshop.services.front.quote.service;

import java.util.List;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.Quote;
import net.jeeshop.services.front.quote.dao.QuoteDao;


public class QuoteServiceImpl extends ServersManager <Quote> implements QuoteService {
	private QuoteDao quoteDao;

	public void setQuoteDao(QuoteDao quoteDao) { 
		this.quoteDao=quoteDao;
	}
	@Override
	public int updateAll(List<Quote> sz,List<Quote> ym,List<Quote> dp) throws Exception{
		 quoteDao.updateAll(sz);
		 quoteDao.updateAll(ym);
		 quoteDao.updateAll(dp);
		 return 0;
	}
	@Override
	public void updatePrice() throws Exception {
		quoteDao.updatePrice();
	}
	@Override
	public List<Quote> getQuoteList(String type){
		return quoteDao.getQuoteList(type);
	}
}
