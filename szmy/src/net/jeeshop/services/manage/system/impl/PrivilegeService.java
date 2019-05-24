package net.jeeshop.services.manage.system.impl;

import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.system.bean.Privilege;
import net.jeeshop.core.system.bean.Role;


/**
 * 权限业务逻辑实现类
 * 
 * @author huangf
 * 
 */
public class PrivilegeService implements Services<Privilege> {
	private BaseDao dao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Privilege> selectList(Privilege privilege) {
		if (privilege == null)
			return dao.selectList("privilege.selectList");
		return dao.selectList("privilege.selectList", privilege);
	}

	@Override
	public Privilege selectOne(Privilege privilege) {
		return (Privilege) dao.selectOne("privilege.selectOne", privilege);
	}

	@Override
	public int insert(Privilege privilege) {
		return dao.insert("privilege.insert", privilege);
	}

	@Override
	public int delete(Privilege privilege) {
		return dao.delete("privilege.delete", privilege);
	}

	@Override
	public int update(Privilege privilege) {
		return dao.update("privilege.update", privilege);
	}

	/**
	 * 根绝角色删除权限
	 * 
	 * @param role
	 */
	public void deleteByRole(Role role) {
		Privilege privilege = new Privilege();
		privilege.setRid(role.getId());
		delete(privilege);
	}

	@Override
	public PagerModel selectPageList(Privilege e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deletes(String[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Privilege selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
