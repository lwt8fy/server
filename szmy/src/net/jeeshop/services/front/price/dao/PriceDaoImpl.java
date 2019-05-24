package net.jeeshop.services.front.price.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.Price;


public class PriceDaoImpl implements PriceDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(Price e) {
		return dao.selectPageList("front.price.selectPageList","front.price.selectPageCount",e);
	}
	@Override
	public List selectList(Price e) {
		return dao.selectList("front.price.selectList",e);
	}
	@Override
	public Price selectOne(Price e) {
		return (Price)dao.selectOne("front.price.selectOne",e);
	}
	@Override
	public int delete(Price e) {
		return dao.delete("front.price.selectList",e);
	}
	@Override
	public int update(Price e) {
		return dao.update("front.price.update",e);
	}
	@Override
	public int insert(Price e) {
		return dao.insert("front.price.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.price.deleteById",id);
	}
	@Override
	public Price selectById(String id) {
		return (Price) dao.selectOne("front.price.selectById",id);
	}
}

