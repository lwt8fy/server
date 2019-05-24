package net.jeeshop.services.front.appVehicleOrder.dao;

import java.util.List;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.AppVehicleOrder;

public class AppVehicleOrderDaoImpl implements AppVehicleOrderDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
	@Override
	public PagerModel selectPageList(AppVehicleOrder e) {  
		return dao.selectPageList("front.appVehicleOrder.selectPageList",
				"front.appVehicleOrder.selectPageCount", e);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List selectList(AppVehicleOrder e) {
		return dao.selectList("front.appVehicleOrder.selectList", e);
	}
	@Override
	public AppVehicleOrder selectOne(AppVehicleOrder e) {
		return (AppVehicleOrder) dao.selectOne("front.appVehicleOrder.selectOne", e);
	}
	@Override
	public int delete(AppVehicleOrder e) {
		return dao.delete("front.appVehicleOrder.delete", e);
	}
	@Override
	public int update(AppVehicleOrder e) {
		return dao.update("front.appVehicleOrder.update", e);
	}
	
	@Override
	public int insert(AppVehicleOrder e) {
		return dao.insert("front.appVehicleOrder.insert", e);
	}

	@Override
	public int deleteById(int id) {
		return dao.delete("front.appVehicleOrder.deleteById", id);
	}
	@Override
	public  AppVehicleOrder selectById(String id) {
		return (AppVehicleOrder) dao.selectOne("front.appVehicleOrder.selectById", id);
	}
}
