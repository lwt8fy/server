package net.jeeshop.services.manage.logistics_price.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.Logistics_price;


public class Logistics_priceDaoImpl implements Logistics_priceDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(Logistics_price e) {
		return dao.selectPageList("manage.logistics_price.selectPageList","manage.logistics_price.selectPageCount",e);
	}
	@Override
	public List selectList(Logistics_price e) {
		return dao.selectList("manage.logistics_price.selectList",e);
	}
	@Override
	public Logistics_price selectOne(Logistics_price e) {
		return (Logistics_price)dao.selectOne("manage.logistics_price.selectOne",e);
	}
	@Override
	public int delete(Logistics_price e) {
		return dao.delete("manage.logistics_price.selectList",e);
	}
	@Override
	public int update(Logistics_price e) {
		return dao.update("manage.logistics_price.update",e);
	}
	@Override
	public int insert(Logistics_price e) {
		return dao.insert("manage.logistics_price.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("manage.logistics_price.deleteById",id);
	}
	@Override
	public Logistics_price selectById(String id) {
		return (Logistics_price) dao.selectOne("manage.logistics_price.selectById",id);
	}
	@Override
	public List<Logistics_price> selectListByLogid(Logistics_price e){
		return dao.selectList("manage.logistics_price.selectListByLogid",e);
	}
	@Override
	public List<Logistics_price> selectDefaultByLogid(Logistics_price e){
		return dao.selectList("manage.logistics_price.selectDefaultByLogid",e);
	}
	@Override
	public int deleteByLogId(int logisticsid) {
		return dao.delete("manage.logistics_price.deleteByLogId",logisticsid);
	}
	@Override
	public List<Logistics_price> selectPriceByArea(Logistics_price e) {
		return dao.selectList("manage.logistics_price.selectPriceByArea",e);
	}
}

