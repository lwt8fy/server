package net.jeeshop.web.action.front.appUser;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.MyCommonDao;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.CommonPropertiesUtil;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.core.util.MD5;
import net.jeeshop.core.util.SendSMSUtil;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.ConnectUser;
import net.jeeshop.services.common.Price;
import net.jeeshop.services.common.Profit;
import net.jeeshop.services.common.ScrollPic;
import net.jeeshop.services.common.SignIn;
import net.jeeshop.services.common.TuiGuang;
import net.jeeshop.services.front.appOrder.service.AppOrderService;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.front.connectUser.service.ConnectUserService;
import net.jeeshop.services.front.signIn.service.SignInService;
import net.jeeshop.services.manage.area.AreaService;
import net.jeeshop.services.manage.area.bean.Area;
import net.jeeshop.services.manage.profit.service.ProfitService;
import net.jeeshop.services.manage.scrollPic.ScrollPicService;
import net.jeeshop.services.manage.tuiGuang.service.TuiGuangService;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class AppUserAction extends BaseAction<AppUser> {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(AppUserAction.class);
	@SuppressWarnings("unused")
	private AppOrderService appOrderService;
	private AppUserService appUserService;
	private TuiGuangService tuiGuangService;
	private ProfitService profitService;
	private SignInService signInService;
	private MyCommonDao myCommonDao;
	private String json;
	private String uuid;
	private String orderType;//WLDJ物流定金,SZDJ生猪定金
	private AppUser e;
	private AreaService areaService;
	private ConnectUserService connectUserService; //关联用户
	

	/**上传的文件*/
	private File uploadImage;
	/**上传文件名称*/
	private String uploadImageFileName;
	/**上传文件的格式类型*/
	private String uploadImageContentType;
	
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";
	
	private ScrollPicService scrollPicService;
	
	
	public void setConnectUserService(ConnectUserService connectUserService) {
		this.connectUserService = connectUserService;
	}

	public void setScrollPicService(ScrollPicService scrollPicService) {
		this.scrollPicService=scrollPicService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}


	public void setAppOrderService(AppOrderService appOrderService) {
		this.appOrderService = appOrderService;
	}

	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	public void setMyCommonDao(MyCommonDao myCommonDao) {
		this.myCommonDao = myCommonDao;
	}
	
	public void setProfitService(ProfitService profitService) {
		this.profitService = profitService;
	}
	

	public SignInService getSignInService() {
		return signInService;
	}


	public void setSignInService(SignInService signInService) {
		this.signInService = signInService;
	}


	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setTuiGuangService(TuiGuangService tuiGuangService) {
		this.tuiGuangService = tuiGuangService;
	}
	/**
	 * 获取首页banner图
	 * @author 滕武超
	 * @date 2015-12-28 下午12:55:14 
	 * @Description:
	 */
	public String getBannerList(){
		ScrollPic ap=new ScrollPic();
		ap.setFloor("zmt_b");
		List<ScrollPic> list = scrollPicService.selectList(ap);
		JSONObject jo=new JSONObject();
		jo.put("list", list);
		jo.put("success", "1");
		json =jo.toString();
		return "json";
	}
	
	public String regist() {
		try{
		String uuid = UUID.randomUUID().toString().toUpperCase();
		AppUser au=new AppUser();
		au.setUsername(e.getUsername());
		au = appUserService.selectOne(au);
		if(au!=null&&au.getId()!=null){
			json="{\"success\":\"0\",\"message\":\"注册失败:用户名被占用!\"}";
		}else{
		e.setUuid(uuid);
		e.setPicture("/appUpload/head_default.png");
		e.setPresenter(appUserService.selectById(KeyValueHelper.get("PTSYZH")).getUsername());
		
		e.setTjrsl(0);
		e.setUserStatus("1");
		
		appUserService.insert(e);
		json =JSONObject.fromObject(e).toString();
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
			json =JSONObject.fromObject(u).toString();
		}
		}catch (Exception e) {
			json = Exception_MSG;
		}
		return "json";
	}
	/**
	 * 获取我的积分
	 * @author 滕武超
	 * @date 2015-12-22 下午02:33:11 
	 * @Description:
	 */
	public String getIntegral() throws Exception {
		// TODO Auto-generated method stub
		try{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u==null){
				json = ERROR_MSG;
			}else{
				JSONObject js=new JSONObject();
				js.put("success","1");
				js.put("integral",u.getMoney()==null?"0":u.getMoney());
				
				List<Object> o = myCommonDao.executeSql(1, "select sum(finalProfit) from t_profit where account='"+u.getUsername()+"' and createTime>'"+DateUtil.dateToStr(new Date())+"' and  finalProfit>0 ");
				if(o!=null&&o.size()>0){
					js.put("today_integral",o.get(0)==null?"0":o.get(0));
				}else{
					js.put("today_integral","0");
				}
				
				json=js.toString();
				System.out.println(json);
			}
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}
	/**
	 * 修改
	 */
	@Override
	public String update() throws Exception {
		// TODO Auto-generated method stub
		try{
		AppUser u = appUserService.selectByUuid(uuid);
		if(u==null){
			json = ERROR_MSG;
		}else{
			
			String newPassword=getRequest().getParameter("newPassword");
			if(newPassword!=null&&!newPassword.equals("")){
				if(e.getPassword().equals(u.getPassword())){
					e.setPassword(newPassword);
				}else{
					json = ERROR_MSG;
					return "json";
				}
			}
			e.setId(u.getId());
			appUserService.update(e);
			e= appUserService.selectById(e.getId());
			json =JSONObject.fromObject(e).toString();
		}
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}
	/**
	 * 根据用户名找到唯一用户
	 * @author 滕武超
	 * @date 2015-12-8 上午10:55:33 
	 * @Description:
	 */
	public String getUserByName(){
		try{
				AppUser au=new AppUser();
				au.setUsername(e.getUsername());
				au = appUserService.selectOne(au);
				if(au==null){
					if(isMobile(e.getUsername())){
						au=new AppUser();
						au.setPhone(e.getUsername());
						List<AppUser> list = appUserService.selectList(au);
						if(list!=null&&list.size()==1){
							au=list.get(0);
						}else{
							json = ERROR_MSG;
							return "json";
						}
					}
				}
				AppUser au2=new AppUser();
				au2.setId(au.getId());
				au2.setUuid(UUID.randomUUID().toString().toUpperCase());
				appUserService.update(au2);
				JSONObject js=new JSONObject();
				js.put("success", "1");
				js.put("uuid", au2.getUuid());
				js.put("phone", au.getPhone());
				json =js.toString();
			}catch (Exception e) {
				System.out.println(e);
				json = Exception_MSG;
			}
			return "json";
	}
	/**
	 * 重置密码
	 * @author 滕武超
	 * @date 2015-12-8 上午10:44:37 
	 * @Description:
	 */
	public String resetPassword(){
		try{
			AppUser au = appUserService.selectByUuid(uuid);
			if(au==null){
				json = ERROR_MSG;
				return "json";
			}
			String s = randString(6);
			AppUser au2=new AppUser();
			au2.setId(au.getId());
			au2.setPassword(MD5.md5(s));
			appUserService.update(au2);
			String msg = CommonPropertiesUtil.getSMStemValue("resetPassword");
			msg = msg.replace("username", au.getUsername()).replace("password", s);
			String str = SendSMSUtil.sendSMS(msg, au.getPhone());
			System.out.println(str);
			json =SUCCESS_MSG;
		}catch (Exception e) {
			logger.error(e.toString());
			json = Exception_MSG;
		}
		return "json";
	}
	/** 
     * 手机号验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public  boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][358][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    } 
   
    /**
     * 获取随机字符 
     * length 字符长度
     * @author 滕武超
     * @date 2015-12-8 下午03:05:17 
     * @Description:
     */
    private  String randString (int length)
    {
    	Random r = new Random();
        String ssource = "0123456789";
        char[] src = ssource.toCharArray();
            char[] buf = new char[length];
            int rnd;
            for(int i=0;i<length;i++)
            {
                    rnd = Math.abs(r.nextInt()) % src.length;

                    buf[i] = src[rnd];
            }
            return new String(buf);
    }
    /**
     * 
     * 作者：王海洋
     * 时间：2016-2-15下午01:36:31
     * 描述: 根据用户名和密码查询用户在平顶山
     * @return
     */
    public String getConnectUserInfo(){
    	AppUser u = appUserService.selectOne(getE());
    	if(u!=null && "4".equals(u.getSource())){
    		ConnectUser connectUser = new ConnectUser();
    		connectUser.setAccount(u.getUsername());
    		ConnectUser one = connectUserService.selectOne(connectUser);
    		if(one!=null)
    		json = JSONObject.fromObject(one).toString();
    	}else{
    		json = ERROR_MSG;
    	}
    	return "json";
    }
	/**
	 * 登录
	 * @author 滕武超
	 * @date 2015-12-22 下午02:33:44 
	 * @Description:
	 */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
	public String login() {
		try{
		AppUser u = appUserService.selectOne(getE());
		if(u==null){
			ConnectUser cu=new ConnectUser();
			cu.setUserName(e.getUsername());
			cu.setPassword(e.getPassword());
			ConnectUser one = connectUserService.selectOne(cu);
			if(one!=null){
				u=new AppUser();
				u.setUsername(one.getAccount());
				u = appUserService.selectOne(u);
			}
		}
		if (u == null) {
			json = ERROR_MSG;
		} else {
			String phoneModel = getRequest().getParameter("phoneModel");
			String equipmentNumber = getRequest().getParameter("equipmentNumber");
			System.out.println("手机型号+++++++++++++"+phoneModel);
			System.out.println("设备号+++++++++++++"+equipmentNumber);
			u.setUuid(UUID.randomUUID().toString().toUpperCase());
			String stat=u.getUserStatus();
			if(stat!=null&&stat.equals("0")){
				u.setUserStatus("1");
			}
			
			if(stat!=null&&stat.equals("0")){
				List<TuiGuang> tglist =new ArrayList<TuiGuang>();
				TuiGuang tg1=new TuiGuang();
				tg1.setEquipmentNumber(equipmentNumber);
				tglist = tuiGuangService.selectList(tg1);//判断设备号唯一
				
				if(u.getPresenter()!=null&&!u.getPresenter().equals("admin")&&(tglist==null||tglist.size()==0)){
					
					AppUser au=new AppUser();
					au.setUsername(u.getPresenter());
					au = appUserService.selectOne(au);
					
					
					AppUser au2=new AppUser();
					au2.setId(au.getId());
					au2.setTjrsl((au.getTjrsl()==null?0:au.getTjrsl())+1); 
					
					//根据配置规则计算推广注册收益
					String reg = KeyValueHelper.get("APP_TGSYGZ");
					String[] ss = reg.split("-----");
					String[] s1 = ss[0].split("\\|");//基准金额规则
					String[] s2 = ss[1].split("\\|");//附加金额规则
					
					int jz=0;//基准金额
					int fjrs=0;//满附加人数
					int fj=0;//附加金额
					for (String s : s1) {
						if(au2.getTjrsl()>=Integer.valueOf(s.split("=")[0])){
							jz=Integer.valueOf(s.split("=")[1]);
						}
					}
					for (String s : s2) {
						if(au2.getTjrsl().equals(Integer.valueOf(s.split("\\+")[0]))){
							fjrs=Integer.valueOf(s.split("\\+")[0]);
							fj=Integer.valueOf(s.split("\\+")[1]);
						}
					}
					au2.setMoney((au.getMoney()==null?0:au.getMoney())+jz+fj);
					appUserService.updateMoney(au2);
					
					Profit profit=new Profit();
					profit.setPresentee(u.getUsername());
					profit.setAccount(au.getUsername());
					profit.setUserID(au.getId());
					profit.setFinalProfit(Double.valueOf(jz+fj));
					profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
					profit.setSource("3");
					if(fj>0){
						profit.setRemark("满"+fjrs+"人,另加:"+fj);
					}
					profitService.insert(profit);
					
					TuiGuang tg=new TuiGuang(); 
					tg.setUserName(u.getUsername());
					tg.setIsLogin("y");
					tg.setLoginTime(DateUtil.dateToStrSS());
					tg.setPhoneModel(phoneModel);
					tg.setEquipmentNumber(equipmentNumber);
					tg.setIntegral(jz+fj);
					if(fj>0){
						tg.setRemark("满"+fjrs+"人,另加:"+fj);
					}
					tuiGuangService.update(tg);
					
					
					u.setMoney(100.00);
					profit=new Profit();
					profit.setAccount(u.getUsername());
					profit.setUserID(u.getId());
					profit.setFinalProfit(100.00);
					profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
					profit.setSource("3");
					profitService.insert(profit);
					appUserService.updateMoney(u);
					
					
				}else{
					TuiGuang tg=new TuiGuang();
					tg.setUserName(u.getUsername());
					tg.setIsLogin("y");
					tg.setLoginTime(DateUtil.dateToStrSS());
					tg.setPhoneModel(phoneModel);
					tg.setEquipmentNumber(equipmentNumber);
					if(tglist!=null&&tglist.size()>0){
						tg.setRemark("同一设备,不予计算!");
						tg.setIntegral(0);
					}
					tuiGuangService.update(tg);
				}
			}
			appUserService.update(u);
			
			json = JSONObject.fromObject(u).toString();
		}
		}catch (Exception e) { 
			logger.error(e.getMessage(),e);
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}
	
	/**
	 * 上传图片
	 * @return
	 */
	public String upload(){
		try{
		AppUser u = appUserService.selectByUuid(uuid);
		if(u==null||uploadImage==null){
			json = ERROR_MSG;
			return "json";
		}
		
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
		
		AppUser u2=new AppUser();
		u2.setId(u.getId());
		u2.setPicture("/appUpload/"+uploadImageFileName);
		appUserService.update(u2);
		u.setPicture("/appUpload/"+uploadImageFileName);
		json =JSONObject.fromObject(u).toString();
		}catch (Exception e) {
			json = Exception_MSG;
		}
		return "json";
	}
	
	
	public String getBusinessList(){
		try{
			List<Object> list = myCommonDao.executeSql(1, "select business from app_business ");
			JSONObject jo=new JSONObject();
			jo.put("list", list);
			jo.put("success", "1");
			json =jo.toString();
		}catch (Exception e) {
			System.out.println(e);
			json = Exception_MSG;
		}
		return "json";
	}
	/**
	 * 获取当天价格
	 * @author 滕武超
	 * @date 2016-1-9 下午01:18:24 
	 * @Description:
	 */
	public String getToDayPeice(){
		try{
			String province = getRequest().getParameter("province");
			String city = getRequest().getParameter("city");
			String area = getRequest().getParameter("area");
			System.out.println(province+city+area+"-------------------");
			Price p1=null;
			Price p2=null;
			List<Price> list=SystemManager.toDayPrice;
			if(StringUtils.isBlank(province)){
				province="0";
			}
			for (Price p : list) {
				if(!p.getType().equals("3")){
					if(p.getProvince().contains(province)){
						p1=p;
					}
				}else if(province.contains("河南")){
					if(p.getCity()!=null&&p.getArea()!=null&&p.getCity().equals(city)&&p.getArea().equals(area)){
						p2=p;
					}
				}
			}
			JSONObject jo=new JSONObject();
			if(p2!=null){
				jo=JSONObject.fromObject(p2);
			}else{
				jo=JSONObject.fromObject(p1);	
			}
			jo.put("success", "1");
			json =jo.toString();
			return "json";	
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}
	
	
	/**
	 * 是否支付定金
	 * @author 滕武超
	 * @date 2015-12-4 下午02:44:08 
	 * @Description:
	 */
	public String sfzfdj(){
		JSONObject js=new JSONObject();
		js.put("success", 1);
		js.put("status", KeyValueHelper.get("APP_SFZFDJ"));//是否支付定金
		js.put("dj", KeyValueHelper.get("APP_"+orderType));//根据订单类型定金
		json=js.toString();
		return "json";
	}
	/**
	 * 获取签到信息
	 * @author 滕武超
	 * @date 2016-1-12 下午02:28:27 
	 * @Description:
	 */
	public String getSignInInfo(){
		if(StringUtils.isBlank(uuid)){
			json = ERROR_MSG;
			return "json";
		}
		AppUser u = appUserService.selectByUuid(uuid);
		if(u==null){
			json = ERROR_MSG;
			return "json";
		}
		
		SignIn si=new SignIn();
		si.setCreateTime(DateUtil.dateToStr(new Date()));
		si.setUserName(u.getUsername());
		List<SignIn> list = signInService.selectList(si);
		JSONObject js=new JSONObject();
		js.put("success", 1);
		if(list!=null&&list.size()>0){
			js.put("status", "y");
			js.put("signNum", list.get(0).getNum());
		}else{
			js.put("status", "n");
			si.setCreateTime(DateUtil.dateToStr(DateUtil.addDay(new Date(), -1)));
			list = signInService.selectList(si);
			if(list!=null&&list.size()>0){
				js.put("signNum", list.get(0).getNum());
			}else{
				js.put("signNum", 0);
			}
			
		}
		json=js.toString();
		return "json";
	}
	/**
	 * 签到返积分7天循环
	 * @author 滕武超
	 * @date 2016-1-12 下午03:13:18 
	 * @Description:
	 */
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
	public String signIn(){
		if(StringUtils.isBlank(uuid)){
			json = ERROR_MSG;
			return "json";
		}
		AppUser u = appUserService.selectByUuid(uuid);
		if(u==null){
			json = ERROR_MSG;
			return "json";
		}
		SignIn si=new SignIn();
		si.setCreateTime(DateUtil.dateToStr(new Date()));
		si.setUserName(u.getUsername());
		List<SignIn> list = signInService.selectList(si);
		if(list!=null&&list.size()>0){
			json = Exception_MSG;
			return "json";
		}
		si.setCreateTime(DateUtil.dateToStr(DateUtil.addDay(new Date(), -1)));
		list = signInService.selectList(si);
		int num=1;
		if(list!=null&&list.size()>0){
		num=list.get(0).getNum()+1;
		}
		int jf=num%7;
		if(jf==0)jf=7;
		
		Profit profit=new Profit();
		profit.setAccount(u.getUsername());
		profit.setUserID(u.getId());
		profit.setFinalProfit(Double.valueOf(jf));
		profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
		profit.setSource("4");//签到
		profitService.insert(profit);
		
		AppUser au=new AppUser();
		au.setId(u.getId());
		au.setMoney((u.getMoney()==null?0:u.getMoney())+Double.valueOf(jf));
		appUserService.updateMoney(au);
		
		si=new SignIn();
		si.setNum(num);
		si.setCreateTime(DateUtil.dateToStr(new Date()));
		si.setUserName(u.getUsername());
		signInService.insert(si);
		
		JSONObject js=new JSONObject();
		js.put("success", 1);
		js.put("status", "y");
		js.put("signNum", num);
		js.put("integral", jf);
		json=js.toString();
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
