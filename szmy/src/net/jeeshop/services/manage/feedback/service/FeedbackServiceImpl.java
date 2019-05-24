package net.jeeshop.services.manage.feedback.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.Feedback;
import net.jeeshop.services.manage.feedback.dao.FeedbackDao;


public class FeedbackServiceImpl extends ServersManager <Feedback> implements FeedbackService {
	private FeedbackDao feedbackDao;

	public void setFeedbackDao(FeedbackDao feedbackDao) { 
		this.feedbackDao=feedbackDao;
	}
}
