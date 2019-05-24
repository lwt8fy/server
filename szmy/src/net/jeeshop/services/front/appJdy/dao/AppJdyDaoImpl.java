package net.jeeshop.services.front.appJdy.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.AppJdy;


public class AppJdyDaoImpl implements AppJdyDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(AppJdy e) {
		return dao.selectPageList("front.appJdy.selectPageList","front.appJdy.selectPageCount",e);
	}
	@Override
	public List selectList(AppJdy e) {
		return dao.selectList("front.appJdy.selectList",e);
	}
	@Override
	public AppJdy selectOne(AppJdy e) {
		return (AppJdy)dao.selectOne("front.appJdy.selectOne",e);
	}
	@Override
	public int delete(AppJdy e) {
		return dao.delete("front.appJdy.selectList",e);
	}
	@Override
	public int update(AppJdy e) {
		return dao.update("front.appJdy.update",e);
	}
	@Override
	public int insert(AppJdy e) {
		return dao.insert("front.appJdy.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.appJdy.deleteById",id);
	}
	@Override
	public AppJdy selectById(String id) {
		return (AppJdy) dao.selectOne("front.appJdy.selectById",id);
	}
}

