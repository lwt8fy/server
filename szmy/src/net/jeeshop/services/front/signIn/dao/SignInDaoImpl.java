package net.jeeshop.services.front.signIn.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.SignIn;


public class SignInDaoImpl implements SignInDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(SignIn e) {
		return dao.selectPageList("front.signIn.selectPageList","front.signIn.selectPageCount",e);
	}
	@Override
	public List selectList(SignIn e) {
		return dao.selectList("front.signIn.selectList",e);
	}
	@Override
	public SignIn selectOne(SignIn e) {
		return (SignIn)dao.selectOne("front.signIn.selectOne",e);
	}
	@Override
	public int delete(SignIn e) {
		return dao.delete("front.signIn.selectList",e);
	}
	@Override
	public int update(SignIn e) {
		return dao.update("front.signIn.update",e);
	}
	@Override
	public int insert(SignIn e) {
		return dao.insert("front.signIn.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.signIn.deleteById",id);
	}
	@Override
	public SignIn selectById(String id) {
		return (SignIn) dao.selectOne("front.signIn.selectById",id);
	}
}

