package net.jeeshop.services.manage.logistics_free.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.Logistics_free;


public class Logistics_freeDaoImpl implements Logistics_freeDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(Logistics_free e) {
		return dao.selectPageList("manage.logistics_free.selectPageList","manage.logistics_free.selectPageCount",e);
	}
	@Override
	public List selectList(Logistics_free e) {
		return dao.selectList("manage.logistics_free.selectList",e);
	}
	@Override
	public Logistics_free selectOne(Logistics_free e) {
		return (Logistics_free)dao.selectOne("manage.logistics_free.selectOne",e);
	}
	@Override
	public int delete(Logistics_free e) {
		return dao.delete("manage.logistics_free.selectList",e);
	}
	@Override
	public int update(Logistics_free e) {
		return dao.update("manage.logistics_free.update",e);
	}
	@Override
	public int insert(Logistics_free e) {
		return dao.insert("manage.logistics_free.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("manage.logistics_free.deleteById",id);
	}
	@Override
	public Logistics_free selectById(String id) {
		return (Logistics_free) dao.selectOne("manage.logistics_free.selectById",id);
	}
	@Override
	public List<Logistics_free> selectListByLogid(String logisticsid){
		return dao.selectList("manage.logistics_free.selectListByLogid",logisticsid);
	}
	@Override
	public int deleteByLogId(int logisticsid){
		return dao.delete("manage.logistics_free.deleteByLogId",logisticsid);
	}
	@Override
	public List<Logistics_free> selectFreeByArea(Logistics_free e){
		return dao.selectList("manage.logistics_free.selectFreeByArea",e);
	}
}

