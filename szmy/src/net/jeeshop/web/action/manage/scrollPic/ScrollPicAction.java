package net.jeeshop.web.action.manage.scrollPic;

import net.jeeshop.core.BaseAction;
import net.jeeshop.services.common.ScrollPic;
import net.jeeshop.services.manage.scrollPic.ScrollPicService;

import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class ScrollPicAction extends BaseAction <ScrollPic>  {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ScrollPicAction.class);
	private ScrollPicService scrollPicService;
	public void setScrollPicService(ScrollPicService scrollPicService) {
		this.scrollPicService=scrollPicService;
	}

	@Override
	public void insertAfter(ScrollPic e) {

	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
			this.e = new ScrollPic();
		}
	}

	@Override
	public void selectListAfter() {
		pager.setPagerUrl("scroll!selectList.action");
	}

	@Override
	public ScrollPic getE() {
		return this.e;
	}
	@Override
	public String insert() throws Exception{
		return super.insert();
	}
	@Override
	public String update() throws Exception {
		return super.update();
	}
}
