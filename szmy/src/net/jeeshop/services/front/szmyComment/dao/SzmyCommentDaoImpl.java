package net.jeeshop.services.front.szmyComment.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.SzmyComment;


public class SzmyCommentDaoImpl implements SzmyCommentDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(SzmyComment e) {
		return dao.selectPageList("front.szmyComment.selectPageList","front.szmyComment.selectPageCount",e);
	}
	@Override
	public List selectList(SzmyComment e) {
		return dao.selectList("front.szmyComment.selectList",e);
	}
	@Override
	public SzmyComment selectOne(SzmyComment e) {
		return (SzmyComment)dao.selectOne("front.szmyComment.selectOne",e);
	}
	@Override
	public int delete(SzmyComment e) {
		return dao.delete("front.szmyComment.selectList",e);
	}
	@Override
	public int update(SzmyComment e) {
		return dao.update("front.szmyComment.update",e);
	}
	@Override
	public int insert(SzmyComment e) {
		return dao.insert("front.szmyComment.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.szmyComment.deleteById",id);
	}
	@Override
	public SzmyComment selectById(String id) {
		return (SzmyComment) dao.selectOne("front.szmyComment.selectById",id);
	}
}

