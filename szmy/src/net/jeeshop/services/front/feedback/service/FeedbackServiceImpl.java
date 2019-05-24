package net.jeeshop.services.front.feedback.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.common.Feedback;
import net.jeeshop.services.front.feedback.dao.FeedbackDao;


public class FeedbackServiceImpl extends ServersManager <Feedback> implements FeedbackService {
	private FeedbackDao feedbackDao;

	public void setFeedbackDao(FeedbackDao feedbackDao) { 
		this.feedbackDao=feedbackDao;
	}
}
