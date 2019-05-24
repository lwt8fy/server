package net.jeeshop.services.front.appJyy.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.AppJyy;


public class AppJyyDaoImpl implements AppJyyDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(AppJyy e) {
		return dao.selectPageList("front.appJyy.selectPageList","front.appJyy.selectPageCount",e);
	}
	@Override
	public List selectList(AppJyy e) {
		return dao.selectList("front.appJyy.selectList",e);
	}
	@Override
	public AppJyy selectOne(AppJyy e) {
		return (AppJyy)dao.selectOne("front.appJyy.selectOne",e);
	}
	@Override
	public int delete(AppJyy e) {
		return dao.delete("front.appJyy.selectList",e);
	}
	@Override
	public int update(AppJyy e) {
		return dao.update("front.appJyy.update",e);
	}
	@Override
	public int insert(AppJyy e) {
		return dao.insert("front.appJyy.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.appJyy.deleteById",id);
	}
	@Override
	public AppJyy selectById(String id) {
		return (AppJyy) dao.selectOne("front.appJyy.selectById",id);
	}
}

