package net.jeeshop.web.action.front.appNatural;

import java.io.File;
import java.util.UUID;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.core.util.HttpUtil;
import net.jeeshop.services.common.AppNatural;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.ConnectUser;
import net.jeeshop.services.front.appNatural.service.AppNaturalService;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.front.connectUser.service.ConnectUserService;
import net.jeeshop.services.manage.profit.service.ProfitService;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class AppNaturalAction extends BaseAction <AppNatural>  {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AppNaturalAction.class);
	private AppNaturalService appNaturalService;
	public void setAppNaturalService(AppNaturalService appNaturalService) {
		this.appNaturalService=appNaturalService;
	}
	
private AppUserService appUserService;
private ProfitService profitService;

public void setProfitService(ProfitService profitService) {
	this.profitService = profitService;
}	
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

	private ConnectUserService connectUserService; //关联用户
	public void setConnectUserService(ConnectUserService connectUserService) {
		this.connectUserService = connectUserService;
	}
	
	
	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	
	
	

	/**
	 * 分页查询和个人列表查询
	 */
	@Override
	public String selectList() throws Exception {
		// TODO Auto-generated method stub
		int os=pager.getOffset();
		pager.setOffset((os - 1) * e.getPageSize() );
		try{
		
					super.selectList();
					pager.setOffset(os);
					JSONObject jsonObject = JSONObject.fromObject(pager);
					jsonObject.put("success", "1");
					json=jsonObject.toString();
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
	public String insert() throws Exception {
		try{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u==null){
				json = ERROR_MSG;
			}else{
				e.setStatus("1");
				e.setCreateTime(DateUtil.dateToStrSS());
				e.setUsername(u.getUsername());
				if(uploadImage!=null){
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
					e.setCardImg("/appUpload/"+uploadImageFileName);
					}
				this.appNaturalService.insert(e);
				
				AppUser u2=new AppUser();
				u2.setId(u.getId());
				u2.setProvince(e.getProvince());
				u2.setCity(e.getCity());
				u2.setArea(e.getArea());
				u2.setAuthentication("1");
				appUserService.update(u2);
				json=SUCCESS_MSG;
			}
			}catch (Exception e) {
				e.printStackTrace();
				json = Exception_MSG;
			}
			return "json";
	}

	@Override
	public String toEdit() throws Exception {
		// TODO Auto-generated method stub
		try{
		AppUser u = appUserService.selectByUuid(uuid);
		if(u==null){
			json = ERROR_MSG;
		}else{
			e.setId(null);
			e.setUsername(u.getUsername());
			e = appNaturalService.selectOne(e);
			if(e==null){
				json="{\"success\":\"4\"}";
			}else{
			json =JSONObject.fromObject(e).toString();
			}
			if(u.getSource()!=null&&u.getSource().equals("4")){
				if(e==null){
					e=new AppNatural();
				}
				ConnectUser cu=new ConnectUser();
				cu.setAccount(u.getUsername());cu.setSource("1");
				cu = connectUserService.selectOne(cu);
				if(cu!=null){
				String url=KeyValueHelper.get("PDS_URL")+"?api_method=c.auth&api_name="+cu.getUserName()+"&api_pwd="+cu.getPassword();
				String str = HttpUtil.get(url, null, null);
				System.out.println(str);
				JSONObject js=JSONObject.fromObject(str);
				Object s = js.get("result");
				if(s!=null&&s.toString().equals("success")){
					/*
    //养殖企业信息
    y_lx       ： 养殖企业类型
    y_nf       ： 养殖企业存栏母猪
    t_name     ： 屠宰企业名称
    t_tzl      ： 屠宰企业屠宰量
					 */
				e.setPhone( js.get("a_phone").toString());
				e.setRealName(js.get("a_name").toString());
				if(StringUtils.isBlank(e.getId())){
					e.setSuccess("4");
					}
				JSONObject js2 = JSONObject.fromObject(e);
				js2.put("source", "1");
				json =js2.toString();
				}
				}
				}
			
			
		}
		}catch (Exception e) {
			e.printStackTrace();
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
			if(uploadImage!=null){
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
				e.setCardImg("/appUpload/"+uploadImageFileName);
				}
			e.setStatus("1");
			super.update();
			AppUser u2=new AppUser();
			u2.setId(u.getId());
			u2.setProvince(e.getProvince());
			u2.setCity(e.getCity());
			u2.setArea(e.getArea());
			u2.setAuthentication("1");
			appUserService.update(u2);
			json=SUCCESS_MSG;
		}
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}
	
	
	
	

	@Override
	public void insertAfter(AppNatural e) {

	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
		this.e = new AppNatural();
		}
	}

	@Override
	public void selectListAfter() {

	}

	@Override
	public AppNatural getE() {
		return this.e;
	}




	public String getJson() {
		return json;
	}




	public void setJson(String json) {
		this.json = json;
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
