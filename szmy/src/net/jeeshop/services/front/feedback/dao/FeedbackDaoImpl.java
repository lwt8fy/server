package net.jeeshop.services.front.feedback.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.Feedback;


public class FeedbackDaoImpl implements FeedbackDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(Feedback e) {
		return dao.selectPageList("manage.feedback.selectPageList","manage.feedback.selectPageCount",e);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List selectList(Feedback e) {
		return dao.selectList("manage.feedback.selectList",e);
	}
	@Override
	public Feedback selectOne(Feedback e) {
		return (Feedback)dao.selectOne("manage.feedback.selectOne",e);
	}
	@Override
	public int delete(Feedback e) {
		return dao.delete("manage.feedback.selectList",e);
	}
	@Override
	public int update(Feedback e) {
		return dao.update("manage.feedback.update",e);
	}
	@Override
	public int insert(Feedback e) {
		return dao.insert("manage.feedback.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("manage.feedback.deleteById",id);
	}
	@Override
	public Feedback selectById(String id) {
		return (Feedback) dao.selectOne("manage.feedback.selectById",id);
	}
}

