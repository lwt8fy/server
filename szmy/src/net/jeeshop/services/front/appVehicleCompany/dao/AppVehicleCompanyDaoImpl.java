package net.jeeshop.services.front.appVehicleCompany.dao;

import java.util.List;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.AppVehicleCompany;

public class AppVehicleCompanyDaoImpl implements AppVehicleCompanyDao {
	
	private BaseDao dao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	@Override
	public PagerModel selectPageList(AppVehicleCompany e) {  
		return dao.selectPageList("front.appVehicleCompany.selectPageList",
				"front.appVehicleCompany.selectPageCount", e);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List selectList(AppVehicleCompany e) {
		return dao.selectList("front.appVehicleCompany.selectList", e);
	}
	@Override
	public AppVehicleCompany selectOne(AppVehicleCompany e) {
		return (AppVehicleCompany) dao.selectOne("front.appVehicleCompany.selectOne", e);
	}
	@Override
	public int delete(AppVehicleCompany e) {
		return dao.delete("front.appVehicleCompany.delete", e);
	}
	@Override
	public int update(AppVehicleCompany e) {
		return dao.update("front.appVehicleCompany.update", e);
	}
	
	@Override
	public int insert(AppVehicleCompany e) {
		return dao.insert("front.appVehicleCompany.insert", e);
	}

	@Override
	public int deleteById(int id) {
		return dao.delete("front.appVehicleCompany.deleteById", id);
	}
	@Override
	public AppVehicleCompany selectById(String id) {
		return (AppVehicleCompany) dao.selectOne("front.appVehicleCompany.selectById", id);
	}
}
