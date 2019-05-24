package net.jeeshop.services.front.connectUser.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.ConnectUser;


public class ConnectUserDaoImpl implements ConnectUserDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(ConnectUser e) {
		return dao.selectPageList("front.connectUser.selectPageList","front.connectUser.selectPageCount",e);
	}
	@Override
	public List selectList(ConnectUser e) {
		return dao.selectList("front.connectUser.selectList",e);
	}
	@Override
	public ConnectUser selectOne(ConnectUser e) {
		return (ConnectUser)dao.selectOne("front.connectUser.selectOne",e);
	}
	@Override
	public int delete(ConnectUser e) {
		return dao.delete("front.connectUser.selectList",e);
	}
	@Override
	public int update(ConnectUser e) {
		return dao.update("front.connectUser.update",e);
	}
	@Override
	public int insert(ConnectUser e) {
		return dao.insert("front.connectUser.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.connectUser.deleteById",id);
	}
	@Override
	public ConnectUser selectById(String id) {
		return (ConnectUser) dao.selectOne("front.connectUser.selectById",id);
	}
}

