package net.jeeshop.services.front.appFyy.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.AppFyy;


public class AppFyyDaoImpl implements AppFyyDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(AppFyy e) {
		return dao.selectPageList("front.appFyy.selectPageList","front.appFyy.selectPageCount",e);
	}
	@Override
	public List selectList(AppFyy e) {
		return dao.selectList("front.appFyy.selectList",e);
	}
	@Override
	public AppFyy selectOne(AppFyy e) {
		return (AppFyy)dao.selectOne("front.appFyy.selectOne",e);
	}
	@Override
	public int delete(AppFyy e) {
		return dao.delete("front.appFyy.selectList",e);
	}
	@Override
	public int update(AppFyy e) {
		return dao.update("front.appFyy.update",e);
	}
	@Override
	public int insert(AppFyy e) {
		return dao.insert("front.appFyy.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.appFyy.deleteById",id);
	}
	@Override
	public AppFyy selectById(String id) {
		return (AppFyy) dao.selectOne("front.appFyy.selectById",id);
	}
}

