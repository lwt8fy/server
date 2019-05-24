package net.jeeshop.web.action.front.feedback;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.FrontContainer;
import net.jeeshop.services.common.Feedback;
import net.jeeshop.services.front.account.bean.Account;
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
    public String update()throws Exception{
    	Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
    	e.setCreateUser(acc.getId());
    	e.setStatus("1");
    	e.setUserName(acc.getAccount());
    	 super.insert();
    	 return toEdit;
    }
	@Override
	public void selectListAfter() {

	}

	@Override
	public Feedback getE() {
		return this.e;
	}
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2015-12-8下午01:46:40
	 * 描述: 跳转到用户反馈操作页面
	 * @return
	 */
	public String toFeedback(){
		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);
		if(acc==null){
			return "toLogin";
		}
		return "toFeedback";
	}
}
