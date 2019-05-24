package net.jeeshop.services.front.appNatural.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.AppNatural;


public class AppNaturalDaoImpl implements AppNaturalDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(AppNatural e) {
		return dao.selectPageList("front.appNatural.selectPageList","front.appNatural.selectPageCount",e);
	}
	@Override
	public List selectList(AppNatural e) {
		return dao.selectList("front.appNatural.selectList",e);
	}
	@Override
	public AppNatural selectOne(AppNatural e) {
		return (AppNatural)dao.selectOne("front.appNatural.selectOne",e);
	}
	@Override
	public int delete(AppNatural e) {
		return dao.delete("front.appNatural.selectList",e);
	}
	@Override
	public int update(AppNatural e) {
		return dao.update("front.appNatural.update",e);
	}
	@Override
	public int insert(AppNatural e) {
		return dao.insert("front.appNatural.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.appNatural.deleteById",id);
	}
	@Override
	public AppNatural selectById(String id) {
		return (AppNatural) dao.selectOne("front.appNatural.selectById",id);
	}
}

