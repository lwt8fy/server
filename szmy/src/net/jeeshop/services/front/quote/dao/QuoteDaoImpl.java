package net.jeeshop.services.front.quote.dao;

import java.util.List;
import java.util.Map;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.Quote;


public class QuoteDaoImpl implements QuoteDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(Quote e) {
		return dao.selectPageList("front.quote.selectPageList","front.quote.selectPageCount",e);
	}
	@Override
	public List selectList(Quote e) {
		return dao.selectList("front.quote.selectList",e);
	}
	@Override
	public Quote selectOne(Quote e) {
		return (Quote)dao.selectOne("front.quote.selectOne",e);
	}
	@Override
	public int delete(Quote e) {
		return dao.delete("front.quote.delete",e);
	}
	@Override
	public int update(Quote e) {
		return dao.update("front.quote.update",e);
	}
	public int updatePrice(Map<String, Object> map) {
		return dao.update("front.quote.updatePrice",map);
	}
	@Override
	public int updateAll(List<Quote> list) {
		for(Quote quote :list){
			dao.insert("front.quote.updateAll",quote);
		}
		return 1;
	}
	@Override
	public int insert(Quote e) {
		return dao.insert("front.quote.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.quote.deleteById",id);
	}
	@Override
	public Quote selectById(String id) {
		return (Quote) dao.selectOne("front.quote.selectById",id);
	}
	@Override
	public void updatePrice() throws Exception {
		
		dao.update("front.quote.updatePrice");
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Quote> getQuoteList(String type) {
		if("1".equals(type)){
			return dao.selectList("front.quote.selectSZList");
		}else if("2".equals(type)){
			return dao.selectList("front.quote.selectYMList");
		}else if("3".equals(type)){
			return dao.selectList("front.quote.selectDPList");
		}
		return null;
	}
}

