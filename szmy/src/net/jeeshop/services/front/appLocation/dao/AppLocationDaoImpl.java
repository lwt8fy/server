package net.jeeshop.services.front.appLocation.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.AppLocation;


public class AppLocationDaoImpl implements AppLocationDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(AppLocation e) {
		return dao.selectPageList("front.appLocation.selectPageList","front.appLocation.selectPageCount",e);
	}
	@Override
	public List selectList(AppLocation e) {
		return dao.selectList("front.appLocation.selectList",e);
	}
	@Override
	public AppLocation selectOne(AppLocation e) {
		return (AppLocation)dao.selectOne("front.appLocation.selectOne",e);
	}
	@Override
	public int delete(AppLocation e) {
		return dao.delete("front.appLocation.selectList",e);
	}
	@Override
	public int update(AppLocation e) {
		return dao.update("front.appLocation.update",e);
	}
	@Override
	public int insert(AppLocation e) {
		return dao.insert("front.appLocation.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.appLocation.deleteById",id);
	}
	@Override
	public AppLocation selectById(String id) {
		return (AppLocation) dao.selectOne("front.appLocation.selectById",id);
	}
}

