package net.jeeshop.services.manage.logistics.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.logistics.bean.Logistics;


public class LogisticsDaoImpl implements LogisticsDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(Logistics e) {
		return dao.selectPageList("manage.logistics.selectPageList","manage.logistics.selectPageCount",e);
	}
	@Override
	public List selectList(Logistics e) {
		return dao.selectList("manage.logistics.selectList",e);
	}
	@Override
	public Logistics selectOne(Logistics e) {
		return (Logistics)dao.selectOne("manage.logistics.selectOne",e);
	}
	@Override
	public int delete(Logistics e) {
		return dao.delete("manage.logistics.selectList",e);
	}
	@Override
	public int update(Logistics e) {
		return dao.update("manage.logistics.update",e);
	}
	@Override
	public int insert(Logistics e) {
		return dao.insert("manage.logistics.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("manage.logistics.deleteById",id);
	}
	@Override
	public Logistics selectById(String id) {
		return (Logistics) dao.selectOne("manage.logistics.selectById",id);
	}
	@Override
	public Logistics selectLogisticsByProductId(String productid) {
		return (Logistics) dao.selectOne("manage.logistics.selectLogisticsByProductId",productid);
	}
}

