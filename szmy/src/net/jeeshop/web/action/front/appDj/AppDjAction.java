package net.jeeshop.web.action.front.appDj;

import java.io.File;
import java.util.List;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.MyCommonDao;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.ConnectUser;
import net.jeeshop.services.common.DjSystem;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.front.connectUser.service.ConnectUserService;
import net.jeeshop.services.manage.area.AreaService;
import net.jeeshop.services.manage.area.bean.Area;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;


public class AppDjAction extends BaseAction<AppUser> {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(AppDjAction.class);
	@SuppressWarnings("unused")
	private MyCommonDao myCommonDao;
	private String json;
	private String uuid;
	private AreaService areaService;
	private AppUserService appUserService;
	private ConnectUserService connectUserService; //关联用户

	/**上传的文件*/
	private File uploadImage;
	/**上传文件名称*/
	private String uploadImageFileName;
	/**上传文件的格式类型*/
	private String uploadImageContentType;
	
	@SuppressWarnings("unused")
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String ERROR_PDS="{\"result\":\"failed\"}";
	@SuppressWarnings("unused")
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";
	
	private int pgNum=1;
	private int pgSize=15;
	
	
	
	public int getPgNum() {
		return pgNum;
	}


	public void setPgNum(int pgNum) {
		this.pgNum = pgNum;
	}


	public int getPgSize() {
		return pgSize;
	}


	public void setPgSize(int pgSize) {
		this.pgSize = pgSize;
	}


	public void setConnectUserService(ConnectUserService connectUserService) {
		this.connectUserService = connectUserService;
	}


	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}


	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}


	public void setMyCommonDao(MyCommonDao myCommonDao) {
		this.myCommonDao = myCommonDao;
	}
	
	/**
	 * 获取当前用户报检列表
	 * @author 滕武超
	 * @date 2016-2-19 上午11:31:40 
	 * @Description:
	 */
	public String getDeclarationList(){
		try{
			AppUser au = appUserService.selectByUuid(uuid);
			json = ERROR_PDS;
			if(au==null||au.getSource().equals("1")||au.getSource().equals("2")||au.getSource().equals("3")){
				json = ERROR_PDS;
				return "json";
			}
			ConnectUser connectUser = new ConnectUser();
    		connectUser.setAccount(au.getUsername());
    		ConnectUser cu = connectUserService.selectOne(connectUser);
    		if(cu==null){
    			json = ERROR_PDS;
				return "json";
    		}
    		if(au.getSource().equals("4")){
    			if(au.getType().equals("1")){
    			json=DjSystem.getDeclarationList_pds_zc(cu, pgNum, pgSize);
    			}else{
    				json=DjSystem.getDeclarationList_pds_jyy(cu, pgNum, pgSize);
    			}
    		}else if(au.getSource().equals("5")){
    			json=DjSystem.getDeclarationList_hn(cu, pgNum, pgSize);
    		}
		}catch (Exception e) {
			logger.error(e.toString());
			json = ERROR_PDS;
		}
		return "json";
	}
	
	
	/**
	 * 根据父代码查询区域
	 * @author 滕武超
	 * @date 2016-1-24 上午11:11:53 
	 * @Description:
	 */
	public String getAreaByPcode(){
		try{
		String pcode = getRequest().getParameter("pcode");
		if(StringUtils.isBlank(pcode)){
			pcode="0";
		}
		List<Area> areas = areaService.selectListByPcode(pcode);
		JSONObject js=new JSONObject();
		js.put("list", areas);
		js.put("success", "1");
		json=js.toString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}

	@Override
	public void insertAfter(AppUser e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if (this.e == null) {
			this.e = new AppUser();
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
	public AppUser getE() {
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

	
	
}
