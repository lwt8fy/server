package net.jeeshop.web.action.manage.appOrder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.ManageContainer;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.services.common.AppOrder;
import net.jeeshop.services.common.AppPicture;
import net.jeeshop.services.front.appOrder.service.AppOrderService;
import net.jeeshop.services.front.appPicture.service.AppPictureService;

import org.slf4j.LoggerFactory;

public class AppOrderAction extends BaseAction<AppOrder>{
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger=LoggerFactory.getLogger(AppOrderAction.class);
	private AppOrderService appOrderService;
	private AppPictureService appPictureService;
	
	public void setAppOrderService(AppOrderService appOrderService){
		this.appOrderService=appOrderService;
	}
	public AppPictureService getAppPictureService() {
		return appPictureService;
	}
	public void setAppPictureService(AppPictureService appPictureService) {
		this.appPictureService = appPictureService;
	}
	public AppOrderService getAppOrderService() {
		return appOrderService;
	}
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if (this.e == null) {
			this.e = new AppOrder();
		}
	}
	@Override
	public AppOrder getE(){
		return this.e;
	}
	@Override
	public String selectList() throws Exception{
		return super.selectList();
	}
	@Override
	public String toEdit() throws Exception {
		// TODO Auto-generated method stub
		e = this.server.selectById(e.getId());
		List<AppPicture> plist=appPictureService.selectByOid(e.getId());
		e.setPictureList(plist);
		return toEdit;
	}
	
	public String deleteById() throws Exception{
		// TODO Auto-generated method stub
		this.getServer().delete(e);
		e=new AppOrder();
		return selectList();
	}
	@Override
	protected void selectListAfter(){
		
	}
	@Override
	public void insertAfter(AppOrder e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 显示交易额
	 * @return
	 */
	public String showJye() {
		  try {
		   PrintWriter writer = getResponse().getWriter();
		   User u = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
		 
		  if(u.getRid().equals("1")){
		   Double d=appOrderService.countAppOrderJye(e);
		   if(d==null)d=0.0;
		   writer.print(d);
		   writer.flush();
		   writer.close();
		  }
		  } catch (IOException e) {
		  logger.error(e.toString());
		  }
		  return null;
		 }

}
