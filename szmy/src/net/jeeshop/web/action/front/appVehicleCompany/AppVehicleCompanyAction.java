package net.jeeshop.web.action.front.appVehicleCompany;

import java.io.File;
import java.util.UUID;

import net.jeeshop.core.BaseAction;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.AppVehicleCompany;
import net.jeeshop.services.front.appPicture.service.AppPictureService;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.manage.profit.service.ProfitService;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class AppVehicleCompanyAction extends BaseAction<AppVehicleCompany>{

	private static final long serialVersionUID = 9040428152031267613L;
	Logger logger = Logger.getLogger(AppVehicleCompanyAction.class);
	private AppUserService appUserService;
	private AppPictureService appPictureService;
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";
	private String json;
	private String uuid;
	/**上传的文件*/
	private File uploadImage;
	/**上传文件名称*/
	private String uploadImageFileName;
	/**上传文件的格式类型*/
	private String uploadImageContentType;
private ProfitService profitService;
	
	public void setProfitService(ProfitService profitService) {
		this.profitService = profitService;
	}

	@Override
	public AppVehicleCompany getE() {
		// TODO Auto-generated method stub
		return this.e;
	}
	@Override
	public void insertAfter(AppVehicleCompany e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if (this.e == null) {
			this.e = new AppVehicleCompany();
		}
	}

	@Override
	protected void selectListAfter() {
		// TODO Auto-generated method stub
		
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2015-11-25上午09:14:29
	 * 描述: 查询物流企业信息
	 * @param json
	 */
	@Override
	public String selectList(){
		int os=pager.getOffset();
		pager.setOffset((os - 1) * e.getPageSize() );
		try{
			if(e==null)e=new AppVehicleCompany();
			super.selectList();
			pager.setOffset(os);
			JSONObject jsonObject = JSONObject.fromObject(pager);
			jsonObject.put("success", "1");
			json=jsonObject.toString();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Exception_MSG;
		}
		return "json";
	}
	
	@Override
	public String insert(){
		try{
			if(uuid==null||uuid.equals("")){
				json = ERROR_MSG;
			}else{
					AppUser u = appUserService.selectByUuid(uuid);
					if(u != null && u.getUsername().equals(e.getCreateAccount())){
						if(uploadImage!=null){
								String uuid = UUID.randomUUID().toString().toUpperCase();
								uploadImageFileName=uuid+".jpg";
								String path = ServletActionContext.getServletContext().getRealPath("/appUpload")+"/"+uploadImageFileName;
								//目标文件File
								File destFile = new File(path);
								FileUtils.copyFile(uploadImage, destFile);
								e.setIcon("/appUpload/"+uploadImageFileName);
							}
						e.setCreateAccount(u.getUsername());
						e.setStatus("1");
						this.server.insert(e);
						AppUser u2=new AppUser();
						u2.setId(u.getId());
						u2.setAuthentication("1");
						u2.setX(e.getX());
						u2.setY(e.getY());
						u2.setProvince(e.getProvince());
						u2.setCity(e.getCity());
						u2.setArea(e.getArea());
						u2.setAddress(e.getAddress());
						appUserService.update(u2);
						json=SUCCESS_MSG;
						return "json";
					}
			}
			json = ERROR_MSG;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Exception_MSG;
		}
		return "json";
	}
	public String delete(){
		try{
			if(uuid==null||uuid.equals("")){
				json = ERROR_MSG;
			}else{
				AppUser u = appUserService.selectByUuid(uuid);
				if(u != null && u.getUsername().equals(e.getCreateAccount())){
					this.server.delete(e);
					json=SUCCESS_MSG;
					return "json";
				}
			}
			json = ERROR_MSG;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Exception_MSG;
		}
		return "json";
	}
	public String selectById(){
		if(StringUtils.isNotBlank(e.getId())){
		  AppVehicleCompany appVehicle = 	this.server.selectById(e.getId());
			if(appVehicle!=null){
				json = JSONObject.fromObject(appVehicle).toString();
				return "json";
			}
		}
		json = ERROR_MSG;
		return "json";
	}
	
	@Override
	public String toEdit() throws Exception {
		if(uuid==null||uuid.equals("")){
			json = ERROR_MSG;
		}else{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u!=null){
				e.setCreateAccount(u.getUsername());
				AppVehicleCompany result = this.server.selectOne(e);
				json = JSONObject.fromObject(result).toString();
				return "json";
			}
		}
		json = ERROR_MSG;
		return "json";
	}
	
	@Override
	public String update() {
		// TODO Auto-generated method stub
		try{
		if(uuid==null||uuid.equals("")){
			json = ERROR_MSG;
		}else{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u==null){
				json = ERROR_MSG;
			}else if(u != null && u.getUsername().equals(e.getCreateAccount())){
				if(uploadImage!=null){
					String uuid = UUID.randomUUID().toString().toUpperCase();
					uploadImageFileName=uuid+".jpg";
					String path = ServletActionContext.getServletContext().getRealPath("/appUpload")+"/"+uploadImageFileName;
					//目标文件File
					File destFile = new File(path);
					FileUtils.copyFile(uploadImage, destFile);
					e.setIcon("/appUpload/"+uploadImageFileName);
				 }
				this.server.update(e);
				AppUser u2=new AppUser();
				u2.setId(u.getId());
				u2.setX(e.getX());
				u2.setY(e.getY());
				u2.setProvince(e.getProvince());
				u2.setCity(e.getCity());
				u2.setArea(e.getArea());
				u2.setAddress(e.getAddress());
				u2.setAuthentication("1");
				appUserService.update(u2);
				json=SUCCESS_MSG;
				return "json";
			}
		}
		json = ERROR_MSG;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			json = Exception_MSG;
		}
		return "json";
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public AppUserService getAppUserService() {
		return appUserService;
	}
	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	public AppPictureService getAppPictureService() {
		return appPictureService;
	}
	public void setAppPictureService(AppPictureService appPictureService) {
		this.appPictureService = appPictureService;
	}
	public File getUploadImage() {
		return uploadImage;
	}
	public void setUploadImage(File uploadImage) {
		this.uploadImage = uploadImage;
	}
	public String getUploadImageFileName() {
		return uploadImageFileName;
	}
	public void setUploadImageFileName(String uploadImageFileName) {
		this.uploadImageFileName = uploadImageFileName;
	}
	public String getUploadImageContentType() {
		return uploadImageContentType;
	}
	public void setUploadImageContentType(String uploadImageContentType) {
		this.uploadImageContentType = uploadImageContentType;
	}
}