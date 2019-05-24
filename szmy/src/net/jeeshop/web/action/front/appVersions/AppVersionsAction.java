package net.jeeshop.web.action.front.appVersions;

import java.io.File;
import java.util.List;
import java.util.UUID;

import net.jeeshop.core.BaseAction;
import net.jeeshop.services.common.AppVersions;
import net.jeeshop.services.front.appVersions.service.AppVersionsService;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.LoggerFactory;

public class AppVersionsAction extends BaseAction<AppVersions> {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(AppVersionsAction.class);
	private AppVersionsService appVersionsService;
	private String json;
	private String versionCode;
	
	

	/**上传的文件*/
	private File uploadImage;
	/**上传文件名称*/
	private String uploadImageFileName;
	/**上传文件的格式类型*/
	private String uploadImageContentType;
	
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";



	public String getNewest() {
		try{
		List<AppVersions> list = this.appVersionsService.selectList(new AppVersions());
		e=list.get(0);
		json =JSONObject.fromObject(e).toString();
		}catch (Exception e) {
			System.out.println(e);
			json = Exception_MSG;
		}
		return "json";
	}
	
	
	/**
	 * 上传文件
	 * @return
	 */
	public String upload(){
		try{
		
		String uuid = UUID.randomUUID().toString().toUpperCase();
		uploadImageFileName=uuid+".jpg";
		String path = ServletActionContext.getServletContext().getRealPath("/appUpload")+"/"+uploadImageFileName;
		
		
		//目标文件File
		File destFile = new File(path);
		try {
			//将源文件复制到目标文件
			FileUtils.copyFile(uploadImage, destFile);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		}catch (Exception e) {
			json = Exception_MSG;
		}
		return "json";
	}

	@Override
	public void insertAfter(AppVersions e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if (this.e == null) {
			this.e = new AppVersions();
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
	public AppVersions getE() {
		// TODO Auto-generated method stub
		return this.e;
	}


	public String getVersionCode() {
		return versionCode;
	}


	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
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


	public void setAppVersionsService(AppVersionsService appVersionsService) {
		this.appVersionsService = appVersionsService;
	}
	
	
	
}
