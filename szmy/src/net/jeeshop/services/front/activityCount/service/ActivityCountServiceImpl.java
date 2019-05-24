package net.jeeshop.services.front.activityCount.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.activityCount.dao.ActivityCountDao;
import net.jeeshop.services.front.activityCount.service.ActivityCountService;
import net.jeeshop.services.common.ActivityCount;


public class ActivityCountServiceImpl extends ServersManager <ActivityCount> implements ActivityCountService {
	private ActivityCountDao activityCountDao;

	public void setActivityCountDao(ActivityCountDao activityCountDao) { 
		this.activityCountDao=activityCountDao;
	}
}
