package net.jeeshop.web.action.front.szmyAddress;

import java.io.File;

import net.jeeshop.core.BaseAction;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.front.address.AddressService;
import net.jeeshop.services.front.address.bean.Address;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

public class SzmyAddressAction extends BaseAction<Address> {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SzmyAddressAction.class);
	private AppUserService appUserService;
	private AddressService addressService;
	
	private String json;
	private String uuid;

	/**上传的文件*/
	private File uploadImage;
	/**上传文件名称*/
	private String uploadImageFileName;
	/**上传文件的格式类型*/
	private String uploadImageContentType;
	
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";


	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	
	@Override
	public String selectList() throws Exception {
		int os=pager.getOffset();
		pager.setOffset((os - 1) * e.getPageSize() );
		try{
			if(e==null){
				e=new Address();
			 }
			AppUser auser = appUserService.selectByUuid(uuid);
			if(uuid!=null&&!uuid.equals("")&&auser!=null){
			  e.setAccount(auser.getUsername());
			  super.selectList();
			  pager.setOffset(os);
			  JSONObject jsonObject = JSONObject.fromObject(pager);
			  jsonObject.put("success", "1");
			  json=jsonObject.toString();
			}else{
				json = ERROR_MSG;
			}
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			json = Exception_MSG;
		}
		return "json";
	}

	@Override
	public String insert() throws Exception {
		try{
				AppUser u = appUserService.selectByUuid(uuid);
				if(u==null){
					json = ERROR_MSG;
				}else{
					AppUser u2=new AppUser();
					u2.setId(u.getId());
					u2.setAuthentication("1");
					appUserService.update(u2);
					e.setAccount(u.getUsername());
					if("y".equals(e.getIsdefault())){
						this.addressService.setAddressDefault(e);
					}
					getServer().insert(e);
					json=SUCCESS_MSG;
				}
			}catch (Exception e) {
				logger.error(e.getMessage(),e);
				json = Exception_MSG;
			}
			return "json";
	}
	
	@Override //ids
	public String deletes() throws Exception {
		try{    
			   if(ids==null){
				   json = ERROR_MSG;
				   return "json";
			   }
				AppUser u = appUserService.selectByUuid(uuid);
				if(u==null){
					json = ERROR_MSG;
				}else{
					super.deletes();
					json=SUCCESS_MSG;
				}
			}catch (Exception e) {
				logger.error(e.getMessage(),e);
				json = Exception_MSG;
			}
			return "json";
	}

	@Override
	public String update() throws Exception {
		// TODO Auto-generated method stub
		try{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u==null){
				json = ERROR_MSG;
			}else{
				if(StringUtils.isNotBlank(e.getId())){
					e.setAccount(u.getUsername());
					if("y".equals(e.getIsdefault())){
						this.addressService.setAddressDefault(e);
					}
					this.server.update(e);
					json=SUCCESS_MSG;
				}else{
					json = ERROR_MSG;
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			json = Exception_MSG;
		}
		return "json";
	}
	@Override
	public void insertAfter(Address e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if (this.e == null) {
			this.e = new Address();
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

	@Override
	public Address getE() {
		// TODO Auto-generated method stub
		return this.e;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public AddressService getAddressService() {
		return addressService;
	}

	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	
}
