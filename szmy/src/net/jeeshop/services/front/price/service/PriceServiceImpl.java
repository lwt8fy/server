package net.jeeshop.services.front.price.service;

import java.util.ArrayList;
import java.util.List;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.price.dao.PriceDao;
import net.jeeshop.services.front.price.service.PriceService;
import net.jeeshop.services.common.Price;


public class PriceServiceImpl extends ServersManager <Price> implements PriceService {
	@SuppressWarnings("unused")
	private PriceDao priceDao;

	public void setPriceDao(PriceDao priceDao) { 
		this.priceDao=priceDao;
	}

	@Override
	public List<Price> priceList(String createTime) {
		List<Price> pList=new ArrayList<Price>();
		Price p=new Price();
		p.setCreateTime(createTime);
		p.setType("1");
		pList=selectList(p);
		return pList;
	}

	@Override
	public Price countryList(String createTime) {
		List<Price> pList=new ArrayList<Price>();
		Price p=new Price();
		p.setCreateTime(createTime);
		p.setType("0");
		pList=selectList(p);
		p=pList.get(0);
		return p;
	}

	
}
