package net.jeeshop.web.action.manage.feedback;

import net.jeeshop.core.BaseAction;
import net.jeeshop.services.common.Feedback;
import net.jeeshop.services.front.feedback.service.FeedbackService;

import org.slf4j.LoggerFactory;

public class FeedbackAction extends BaseAction <Feedback>  {
	
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FeedbackAction.class);
	private FeedbackService feedbackService;
	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService=feedbackService;
	}

	@Override
	public void insertAfter(Feedback e) {

	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
		this.e = new Feedback();
		}
	}
	@Override
	public String selectList() throws Exception{
		return super.selectList();
	}
    @Override
    public String update()throws Exception{
    	  super.update();
    	  return toList;
    }
    @Override
    public String toEdit() throws Exception{
    	super.update();
    	return super.toEdit();
    }
	@Override
	public void selectListAfter() {

	}

	@Override
	public Feedback getE() {
		return this.e;
	}
	
}
