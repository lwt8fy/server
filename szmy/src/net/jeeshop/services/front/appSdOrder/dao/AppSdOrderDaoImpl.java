package net.jeeshop.services.front.appSdOrder.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.AppSdOrder;


public class AppSdOrderDaoImpl implements AppSdOrderDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(AppSdOrder e) {
		return dao.selectPageList("front.appSdOrder.selectPageList","front.appSdOrder.selectPageCount",e);
	}
	@Override
	public List selectList(AppSdOrder e) {
		return dao.selectList("front.appSdOrder.selectList",e);
	}
	@Override
	public AppSdOrder selectOne(AppSdOrder e) {
		return (AppSdOrder)dao.selectOne("front.appSdOrder.selectOne",e);
	}
	@Override
	public int delete(AppSdOrder e) {
		return dao.delete("front.appSdOrder.selectList",e);
	}
	@Override
	public int update(AppSdOrder e) {
		return dao.update("front.appSdOrder.update",e);
	}
	@Override
	public int insert(AppSdOrder e) {
		return dao.insert("front.appSdOrder.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.appSdOrder.deleteById",id);
	}
	@Override
	public AppSdOrder selectById(String id) {
		return (AppSdOrder) dao.selectOne("front.appSdOrder.selectById",id);
	}
	@Override
	public List<String> selectSdUser() {
		return  dao.selectList("front.appSdUser.selectSdUser"); 
	}
}

