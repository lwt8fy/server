package net.jeeshop.services.manage.scrollPic.dao.impl;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.ScrollPic;
import net.jeeshop.services.manage.scrollPic.dao.ScrollPicDao;


public class ScrollPicDaoImpl implements ScrollPicDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(ScrollPic e) {
		return dao.selectPageList("manage.scrollPic.selectPageList","manage.scrollPic.selectPageCount",e);
	}
	@Override
	public List selectList(ScrollPic e) {
		return dao.selectList("manage.scrollPic.selectList",e);
	}
	@Override
	public ScrollPic selectOne(ScrollPic e) {
		return (ScrollPic)dao.selectOne("manage.scrollPic.selectOne",e);
	}
	@Override
	public int delete(ScrollPic e) {
		return dao.delete("manage.scrollPic.selectList",e);
	}
	@Override
	public int update(ScrollPic e) {
		return dao.update("manage.scrollPic.update",e);
	}
	@Override
	public int insert(ScrollPic e) {
		return dao.insert("manage.scrollPic.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("manage.scrollPic.deleteById",id);
	}
	@Override
	public ScrollPic selectById(String id) {
		return (ScrollPic) dao.selectOne("manage.scrollPic.selectById",id);
	}
}

