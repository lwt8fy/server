package net.jeeshop.services.front.activityCount.dao;

import java.util.List;
import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.common.ActivityCount;


public class ActivityCountDaoImpl implements ActivityCountDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) { 
		this.dao=dao;
	}
	@Override
	public PagerModel selectPageList(ActivityCount e) {
		return dao.selectPageList("front.activityCount.selectPageList","front.activityCount.selectPageCount",e);
	}
	@Override
	public List selectList(ActivityCount e) {
		return dao.selectList("front.activityCount.selectList",e);
	}
	@Override
	public ActivityCount selectOne(ActivityCount e) {
		return (ActivityCount)dao.selectOne("front.activityCount.selectOne",e);
	}
	@Override
	public int delete(ActivityCount e) {
		return dao.delete("front.activityCount.selectList",e);
	}
	@Override
	public int update(ActivityCount e) {
		return dao.update("front.activityCount.update",e);
	}
	@Override
	public int insert(ActivityCount e) {
		return dao.insert("front.activityCount.insert",e);
	}
	@Override
	public int deleteById(int id) {
		return dao.delete("front.activityCount.deleteById",id);
	}
	@Override
	public ActivityCount selectById(String id) {
		return (ActivityCount) dao.selectOne("front.activityCount.selectById",id);
	}
}

