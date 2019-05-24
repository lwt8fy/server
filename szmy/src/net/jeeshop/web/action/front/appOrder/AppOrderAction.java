package net.jeeshop.web.action.front.appOrder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.MyCommonDao;
import net.jeeshop.core.util.AppDesEncrypt;
import net.jeeshop.core.util.CommonPropertiesUtil;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.core.util.SendSMSUtil;
import net.jeeshop.services.common.AppBroker;
import net.jeeshop.services.common.AppOrder;
import net.jeeshop.services.common.AppPicture;
import net.jeeshop.services.common.AppPigFarm;
import net.jeeshop.services.common.AppSlaughter;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.AppVehicleCompany;
import net.jeeshop.services.common.Profit;
import net.jeeshop.services.front.appBroker.service.AppBrokerService;
import net.jeeshop.services.front.appOrder.service.AppOrderService;
import net.jeeshop.services.front.appPicture.service.AppPictureService;
import net.jeeshop.services.front.appPigFarm.service.AppPigFarmService;
import net.jeeshop.services.front.appSdOrder.service.AppSdOrderService;
import net.jeeshop.services.front.appSlaughter.service.AppSlaughterService;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.front.appVehicleCompany.service.AppVehicleCompanyService;
import net.jeeshop.services.front.orderpay.OrderpayService;
import net.jeeshop.services.front.orderpay.bean.Orderpay;
import net.jeeshop.services.manage.profit.service.ProfitService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abc.trustpay.client.Base64;
import com.abc.trustpay.client.Constants;
import com.abc.trustpay.client.JSON;
import com.abc.trustpay.client.MerchantConfig;
import com.abc.trustpay.client.TrxResponse;
import com.abc.trustpay.client.XMLDocument;
import com.abc.trustpay.client.ebus.PaymentRequest;
import com.abc.trustpay.client.ebus.QueryOrderRequest;

public class AppOrderAction  extends BaseAction<AppOrder> {
	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AppOrderAction.class);
	
	private AppOrderService appOrderService;
	private AppUserService appUserService;
	private AppPictureService appPictureService;
	private OrderpayService orderpayService;
	private MyCommonDao myCommonDao;
	private AppSdOrderService appSdOrderService;
	
	private AppPigFarmService appPigFarmService;
	private AppBrokerService appBrokerService;
	private AppSlaughterService appSlaughterService;
	private AppVehicleCompanyService appVehicleCompanyService;
	private String uuid;
	private String json;
	private String msg;
	private String type; 
	
	

	private final String type1="DJ1SZ";//卖方订金
	private final String type2="DJ2SZ";//买方订金
	private final String type3="SZ";//余款支付
	
	
	
	
	
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";
	
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

	public void setMyCommonDao(MyCommonDao myCommonDao) {
		this.myCommonDao = myCommonDao;
	}
	public void setAppPigFarmService(AppPigFarmService appPigFarmService) {
		this.appPigFarmService = appPigFarmService;
	}

	public void setAppBrokerService(AppBrokerService appBrokerService) {
		this.appBrokerService = appBrokerService;
	}

	public void setAppSlaughterService(AppSlaughterService appSlaughterService) {
		this.appSlaughterService = appSlaughterService;
	}

	public void setAppVehicleCompanyService(
			AppVehicleCompanyService appVehicleCompanyService) {
		this.appVehicleCompanyService = appVehicleCompanyService;
	}

	public void setAppSdOrderService(AppSdOrderService appSdOrderService) {
		this.appSdOrderService = appSdOrderService;
	}

	public void setOrderpayService(OrderpayService orderpayService) {
		this.orderpayService = orderpayService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	

	public void setAppPictureService(AppPictureService appPictureService) {
		this.appPictureService = appPictureService;
	}

	public void setAppOrderService(AppOrderService appOrderService) {
		this.appOrderService = appOrderService;
	}

	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	public String updateComp(){
		e=new AppOrder();
		List<AppOrder> list = appOrderService.selectList(e);
		for (AppOrder o : list) {
			e=new AppOrder();
			e.setId(o.getId());
			
			AppUser u = appUserService.selectById(o.getUserID());
			e.setCompType(u.getType());
			if(u.getType().equals("1")){
				AppPigFarm apf=new AppPigFarm();
				apf.setId(u.getId());
				apf = appPigFarmService.selectOne(apf);
				if(apf!=null){
					e.setCompID(apf.getId());
					e.setCompName(apf.getName());
				}
			}else if(u.getType().equals("2")){
				AppBroker ab=new AppBroker();
				ab.setUserID(u.getId());
				ab = appBrokerService.selectOne(ab);
				if(ab!=null){
					e.setCompID(ab.getId());
					e.setCompName(ab.getRealName());
				}
			}else if(u.getType().equals("3")){
				AppSlaughter as=new AppSlaughter();
				as.setUserID(u.getId());
				as=appSlaughterService.selectOne(as);
				if(as!=null){
					e.setCompID(as.getId());
					e.setCompName(as.getCompanyName());
				}
			}else if(u.getType().equals("4")){
				AppVehicleCompany av=new AppVehicleCompany();
				av.setCreateAccount(u.getUsername());
				 av = appVehicleCompanyService.selectOne(av);
				 if(av!=null){
					 e.setCompID(av.getId());
					 e.setCompName(av.getName());
				 }
			}
			appOrderService.update(e);
		}
		return "json";
	}
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
	@Override
	public String insert(){
		try{
		if(uuid==null||uuid.equals("")){
			json = ERROR_MSG;
		}else{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u!=null&&u.getId().equals(e.getUserID())){
				AppOrder ao=new AppOrder();
				ao.setUserID(u.getId());
				ao.setStartDate(DateUtil.dateToStr(new Date()));
				List<AppOrder> list = appOrderService.selectList(ao);
				
				
			e.setPhone(u.getPhone());
			e.setOrderStatus("1");
			e.setPurchaserSubscription(null);
			e.setSubscription(null);
			
			e.setCompType(u.getType());
			if(u.getType().equals("1")){
				AppPigFarm apf=new AppPigFarm();
				apf.setId(u.getId());
				apf = appPigFarmService.selectOne(apf);
				if(apf!=null){
					e.setCompID(apf.getId());
					e.setCompName(apf.getName());
				}
			}else if(u.getType().equals("2")){
				
				if(u.getAuthentication().equals("2")&&(list==null||list.size()==0)){
					List<Object> l = myCommonDao.executeSql(1,"select id from app_order where userID='"+u.getId()+"' limit 1 ");
					if(l==null||l.size()==0||l.get(0)==null){
						///////////
						if(u.getPresenter()!=null&&!u.getPresenter().equals("admin")&&!u.getPresenter().equals("0")){
							AppUser au= new AppUser();
							au.setUsername(u.getPresenter());
							au = appUserService.selectOne(au);
							int jf = Profit.authentication_tzc;//被推广人认证屠宰场首次发信息
							if(au.getPresenter()!=null){
								if(au.getPresenter().equals("0")){
									au.setMoney((au.getMoney()==null?0:au.getMoney())+jf);
									appUserService.updateMoney(au);
									Profit profit=new Profit();
									profit.setAccount(au.getUsername());
									profit.setUserID(au.getId());
									profit.setFinalProfit(Double.valueOf(jf));//认证
									profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
									profit.setSource("8");//认证
									profit.setPresentee(u.getUsername());
									profitService.insert(profit);
								}else{
									AppUser au2= new AppUser();
									au2.setUsername(au.getPresenter());
									au2 = appUserService.selectOne(au2);
									if(au2.getPresenter()!=null&&au2.getPresenter().equals("0")){
										au.setMoney((au.getMoney()==null?0:au.getMoney())+(jf/2));
										appUserService.updateMoney(au);
										Profit profit=new Profit();
										profit.setAccount(au.getUsername());
										profit.setUserID(au.getId());
										profit.setFinalProfit(Double.valueOf(jf/2));//认证
										profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
										profit.setSource("8");//认证
										profit.setPresentee(u.getUsername());
										profitService.insert(profit);
										
										
										au2.setMoney((au2.getMoney()==null?0:au2.getMoney())+(jf/2));
										appUserService.updateMoney(au2);
										profit=new Profit();
										profit.setAccount(au2.getUsername());
										profit.setUserID(au2.getId());
										profit.setFinalProfit(Double.valueOf(jf/2));//认证
										profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
										profit.setSource("8");//认证
										profit.setPresentee(u.getUsername());
										profitService.insert(profit);
									}
								}
							}
							
						}
						
						////////////
					}
				}
				
				AppBroker ab=new AppBroker();
				ab.setUserID(u.getId());
				ab = appBrokerService.selectOne(ab);
				if(ab!=null){
					e.setCompID(ab.getId());
					e.setCompName(ab.getRealName());
				}
			}else if(u.getType().equals("3")){
				
				
				if(u.getAuthentication().equals("2")&&(list==null||list.size()==0)){
					List<Object> l = myCommonDao.executeSql(1,"select id from app_order where userID='"+u.getId()+"' limit 1 ");
					if(l==null||l.size()==0||l.get(0)==null){
						///////////
						if(u.getPresenter()!=null&&!u.getPresenter().equals("admin")&&!u.getPresenter().equals("0")){
							AppUser au= new AppUser();
							au.setUsername(u.getPresenter());
							au = appUserService.selectOne(au);
							int jf = Profit.authentication_jjr;//被推广人认证经纪人首次发信息
							if(au.getPresenter()!=null){
								if(au.getPresenter().equals("0")){
									au.setMoney((au.getMoney()==null?0:au.getMoney())+jf);
									appUserService.updateMoney(au);
									Profit profit=new Profit();
									profit.setAccount(au.getUsername());
									profit.setUserID(au.getId());
									profit.setFinalProfit(Double.valueOf(jf));//认证
									profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
									profit.setSource("8");//认证
									profit.setPresentee(u.getUsername());
									profitService.insert(profit);
								}else{
									AppUser au2= new AppUser();
									au2.setUsername(au.getPresenter());
									au2 = appUserService.selectOne(au2);
									if(au2.getPresenter()!=null&&au2.getPresenter().equals("0")){
										au.setMoney((au.getMoney()==null?0:au.getMoney())+(jf/2));
										appUserService.updateMoney(au);
										Profit profit=new Profit();
										profit.setAccount(au.getUsername());
										profit.setUserID(au.getId());
										profit.setFinalProfit(Double.valueOf(jf/2));//认证
										profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
										profit.setSource("8");//认证
										profit.setPresentee(u.getUsername());
										profitService.insert(profit);
										
										
										au2.setMoney((au2.getMoney()==null?0:au2.getMoney())+(jf/2));
										appUserService.updateMoney(au2);
										profit=new Profit();
										profit.setAccount(au2.getUsername());
										profit.setUserID(au2.getId());
										profit.setFinalProfit(Double.valueOf(jf/2));//认证
										profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
										profit.setSource("8");//认证
										profit.setPresentee(u.getUsername());
										profitService.insert(profit);
									}
								}
							}
							
						}
						
						////////////
					}
				}
				AppSlaughter as=new AppSlaughter();
				as.setUserID(u.getId());
				as=appSlaughterService.selectOne(as);
				if(as!=null){
					e.setCompID(as.getId());
					e.setCompName(as.getCompanyName());
				}
			}else if(u.getType().equals("4")){
				AppVehicleCompany av=new AppVehicleCompany();
				av.setCreateAccount(u.getUsername());
				 av = appVehicleCompanyService.selectOne(av);
				 if(av!=null){
					 e.setCompID(av.getId());
					 e.setCompName(av.getName());
				 }
			}
			if(e.getIsShow()==null||e.getIsShow().length()==0){
				e.setIsShow("y");
			}
			//用户已认证,并且,(订单为出售,或者(订单为收购,并且,(用户为经纪人或屠宰企业) ) )
			if(u.getAuthentication().equals("2")&&(e.getOrderType().equals("1")||(e.getOrderStatus().equals("2")&&(u.getType().equals("2")||u.getType().equals("3"))))){
				if(list==null||list.size()<2){
					Profit profit=new Profit();
					profit.setAccount(u.getUsername());
					profit.setUserID(u.getId());
					profit.setFinalProfit(Double.valueOf(Profit.issue));//发布出售信息
					profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
					profit.setSource("6");//出售信息
					profitService.insert(profit);
					AppUser au2=new AppUser();
					au2.setId(u.getId());
					au2.setMoney((u.getMoney()==null?0:u.getMoney())+profit.getFinalProfit());
					appUserService.updateMoney(au2);
					
					if(list==null||list.size()==0){
						if(e.getOrderType().equals("1")&&u.getPresenter()!=null&&!u.getPresenter().equals("admin")&&!u.getPresenter().equals("0")){
							AppUser au= new AppUser();
							au.setUsername(u.getPresenter());
							au = appUserService.selectOne(au);
							int jf = Profit.issue_tgr;//被推广人当天首次发信息
							if(au.getPresenter()!=null &&au.getPresenter().equals("0")){
								au.setMoney((au.getMoney()==null?0:au.getMoney())+jf);
								appUserService.updateMoney(au);
								profit=new Profit();
								profit.setAccount(au.getUsername());
								profit.setUserID(au.getId());
								profit.setFinalProfit(Double.valueOf(jf));//认证
								profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
								profit.setSource("11");//(11:被推广人每天发前两条消息)
								profit.setPresentee(u.getUsername());
								profitService.insert(profit);
							}
						}
					}
				}
			}
			appOrderService.insert(e);
			
			 json=JSONObject.fromObject(e).toString();
			 
			 
			}else{
				json = ERROR_MSG;
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ)
	public String update() {
		// TODO Auto-generated method stub
		try{
		if(uuid==null||uuid.equals("")){
			json = ERROR_MSG;
		}else{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u==null){
			json = ERROR_MSG;
			}else{
				
				if(e.getOrderStatus()!=null&&e.getOrderStatus().equals("1")){
					e.setQingKong("purchaser");
				}
				
//				if(e.getOrderStatus()!=null){//下单状态不允许在此修改//确认订单状态不允许在此修改//定金金额不允许在此修改//已付款状态不允许在此修改
//					if(e.getOrderStatus().equals("2")||e.getOrderStatus().equals("5")){
//					json = ERROR_MSG;return "json";
//					}
//				}
				if(e.getOrderStatus()!=null&&e.getOrderStatus().equals("2")){
					AppOrder ao = appOrderService.selectById(e.getId());
					List<String> list = appSdOrderService.selectSdUser();
					if (list.contains(ao.getUserName())) {
						
						e.setLockTime(DateUtil.dateToStrSS());
						e.setOrderStatus("2");
						appOrderService.update(e);
						JSONObject js1 = new JSONObject();
						js1.put("success", 1);
						js1.put("MSG", "1");
						json = js1.toString();
						return "json";
					}
					
					e.setPurchaserID(u.getId());
					e.setPurchaserName(u.getUsername());
					e.setPurchaserPhone(u.getPhone());
					e.setLockTime(DateUtil.dateToStrSS());
				}
				if(e.getOrderStatus()!=null&&e.getOrderStatus().equals("3")){//1为新创建,2为已下单,3为已成交,4 确认订单 5 已支付 6 已发货 
					e.setFinishTime(DateUtil.dateToStrSS());
					AppOrder ao=appOrderService.selectById(e.getId());
					
					if(ao.getOrderType().equals("2")){
						Profit profit=new Profit();
						profit.setAccount(ao.getUserName());
						profit.setUserID(ao.getUserID());
						if(ao.getPayType().equals("2")){
						profit.setFinalProfit(Double.valueOf(Profit.ZMT_XX));//线下成功交易
						}else{
							profit.setFinalProfit(Double.valueOf(Profit.ZMT_XX));//线上成功交易
						}
						profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
						profit.setSource("7");//成功交易
						profitService.insert(profit);
						AppUser au3 = appUserService.selectById(ao.getUserID());
						AppUser au2=new AppUser();
						au2.setId(ao.getUserID());
						au2.setMoney((au3.getMoney()==null?0:au3.getMoney())+profit.getFinalProfit());
						appUserService.updateMoney(au2);
					}else if(ao.getOrderType().equals("1")){
						Profit profit=new Profit();
						profit.setAccount(ao.getPurchaserName());
						profit.setUserID(ao.getPurchaserID());
						if(ao.getPayType().equals("2")){
						profit.setFinalProfit(Double.valueOf(Profit.ZMT_XX));//线下成功交易
						}else{
							profit.setFinalProfit(Double.valueOf(Profit.ZMT_XX));//线上成功交易
						}
						profit.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
						profit.setSource("7");//成功交易
						profitService.insert(profit);
						AppUser au3 = appUserService.selectById(ao.getPurchaserID());
						AppUser au2=new AppUser();
						au2.setId(ao.getPurchaserID());
						au2.setMoney((au3.getMoney()==null?0:au3.getMoney())+profit.getFinalProfit());
						appUserService.updateMoney(au2);
					}
					
				}
				
				/*if(e.getSubscription()!=null||e.getPurchaserSubscription()!=null){//定金金额不允许在此修改
					json = ERROR_MSG;return "json";
				}*/
				

				
				appOrderService.update(e);
				 json=JSONObject.fromObject(e).toString();
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}
	public String update_cs() {
		// TODO Auto-generated method stub
		try{
			if(uuid==null||uuid.equals("")){
				json = ERROR_MSG;
			}else{
				AppUser u = appUserService.selectByUuid(uuid);
				if(u==null){
					json = ERROR_MSG;
				}else{
					
					if(e.getOrderStatus()!=null&&e.getOrderStatus().equals("1")){
						e.setQingKong("purchaser");
					}else if(e.getOrderStatus()!=null&&e.getOrderStatus().equals("2")){
						e.setLockTime(DateUtil.dateToStrSS());
					}else if(e.getOrderStatus()!=null&&e.getOrderStatus().equals("5")){
						e.setPaymentTime(DateUtil.dateToStrSS());
					}else if(e.getOrderStatus()!=null&&e.getOrderStatus().equals("3")){
						e.setFinishTime(DateUtil.dateToStrSS());
					}
					
					appOrderService.update(e);
					json=JSONObject.fromObject(e).toString();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
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
			if(u==null){
				json = ERROR_MSG;
			}else{
				appOrderService.delete(e);
				AppPicture ap=new AppPicture();
				ap.setVehicleID(e.getId());
				List<AppPicture> aps = appPictureService.selectList(ap);
				for (AppPicture a : aps) {
					String path = ServletActionContext.getServletContext().getRealPath("/appUpload")+a.getPicture();
					FileUtils.deleteQuietly(new File(path));
				}
				appPictureService.deleteByVid(e.getId());
				 json=SUCCESS_MSG;
			}
		}
		}catch (Exception e) {
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
		String orderid = getRequest().getParameter("orderID");
		String on = getRequest().getParameter("orderNum");
		
		AppPicture ap=new AppPicture();
		
		ap.setOrderID(orderid);
		String uuid = UUID.randomUUID().toString().toUpperCase();
		uploadImageFileName=uuid+".jpg";
		String path = ServletActionContext.getServletContext().getRealPath("/appUpload")+"/"+uploadImageFileName;
		
		if(on!=null&&on.equals("1")){
			AppOrder ao=new AppOrder();
			ao.setId(orderid);
			ao.setCoverPicture("/appUpload/"+uploadImageFileName);
			appOrderService.update(ao);
			List<AppPicture> aps = appPictureService.selectByOid(orderid);
			for (AppPicture ap2 : aps) {
				String path2 = ServletActionContext.getServletContext().getRealPath("/appUpload")+ap2.getPicture();
				FileUtils.deleteQuietly(new File(path2));
			}
			appPictureService.deleteByOid(orderid);
		}
		//目标文件File
		File destFile = new File(path);
		try {
			//将源文件复制到目标文件
			FileUtils.copyFile(uploadImage, destFile);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		ap.setPicture("/appUpload/"+uploadImageFileName);
		appPictureService.insert(ap);
		json=SUCCESS_MSG;
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
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
		if(e==null)e=new AppOrder();
			 if(e.getUserID()!=null&&!e.getUserID().equals("")){
					if(uuid!=null&&!uuid.equals("")&&appUserService.selectByUuid(uuid)!=null){
						super.selectList();
						pager.setOffset(os);
						JSONObject jsonObject = JSONObject.fromObject(pager);
						jsonObject.put("success", "1");
						json=jsonObject.toString();
					}else{
						json = ERROR_MSG;
					}
				}else if(e.getPurchaserID()!=null&&!e.getPurchaserID().equals("")){
					if(uuid!=null&&!uuid.equals("")&&appUserService.selectByUuid(uuid)!=null){
						super.selectList();
						pager.setOffset(os);
						JSONObject jsonObject = JSONObject.fromObject(pager);
						jsonObject.put("success", "1");
						json=jsonObject.toString();
					}else{
						json = ERROR_MSG;
					}
				}else{
					super.selectList();
					pager.setOffset(os);
					JSONObject jsonObject = JSONObject.fromObject(pager);
					jsonObject.put("success", "1");
					json=jsonObject.toString();
				}
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}
	/**
	 * 查询所有未下单的订单,用来地图显示
	 * @author 滕武超
	 * @date 2015-12-9 下午02:05:52 
	 * @Description:
	 */
	public String selectAll(){
		if(uuid!=null&&!uuid.equals("")){
			AppUser u = appUserService.selectByUuid(uuid);
			e.setUserID(u.getId());
		}
		e.setOrderStatus("1");
		e.setIsShow("y");
		List<AppOrder> list = appOrderService.selectList(e);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", list);
		jsonObject.put("success", "1");
		json=jsonObject.toString();
		return "json";
	}
	/**
	 * 查询所有未下单的并包含12小时内其它订单,用来地图显示
	 * @author 滕武超
	 * @date 2015-12-23 上午09:07:59 
	 * @Description:
	 */
	public String selectAllAnd12h(){
		if(uuid!=null&&!uuid.equals("")){
			AppUser u = appUserService.selectByUuid(uuid);
			e.setUserID(u.getId());
		}
		e.setIsShow("y");
		String sj = DateUtil.dateToStr(DateUtil.addHour(new Date(), -12),"yyyy-MM-dd HH:mm:ss");
		e.setWhereSql("(orderStatus =1 or (orderStatus!=1 and (lockTime>'"+sj+"' or paymentTime>'"+sj+"' or finishTime>'"+sj+"' )))");
		List<AppOrder> list = appOrderService.selectList(e);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", list);
		jsonObject.put("success", "1");
		json=jsonObject.toString();
		return "json";
	}
	
	/**
	 * 查看详细订单信息
	 * @return
	 */
	public String showOrder(){
		try{
		e = appOrderService.selectById(e.getId());
		List<AppPicture> plist=appPictureService.selectByOid(e.getId());
		e.setPictureList(plist);
		if(e.getOrderStatus().equals("1")&&e.getPurchaserID()!=null&&!e.getPurchaserID().equals("")){
				List<String> list = appSdOrderService.selectSdUser();
				if (!list.contains(e.getUserName())) {
					e.setPurchaserID(null);
					e.setPurchaserName(null);
					e.setPurchaserPhone(null);
					AppOrder o=new AppOrder();
					o.setId(e.getId());
					o.setQingKong("purchaser");
					appOrderService.update(o);
				}
		}
		
		
		json=JSONObject.fromObject(e).toString();
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}
	
	public String getString(){
		String orderid = e.getId();
		if (isEmpty(orderid) || isEmpty(uuid)) {
			// throw new NullPointerException("参数为空!");
			json = ERROR_MSG;
			return "json";
		}
		AppUser user = appUserService.selectByUuid(uuid);
		AppOrder ord = appOrderService.selectById(orderid);
		if (user == null) {
			// throw new NullPointerException("用户错误!");
			json = ERROR_MSG;
			return "json";
		}

		if (!type.equals(type1) && !type.equals(type2) && !type.equals(type3)) {
			json = ERROR_MSG;
			return "json";
			// throw new NullPointerException("类型错误!");
		}

		int xd = 0;
		if (type.startsWith(type3)) {
			
		} else if (type.startsWith(type1)) {
			if (!user.getId().equals(ord.getUserID())) {
				json = ERROR_MSG;
				return "json";
				// throw new NullPointerException("用户错误!");
			}
		} else if (type.startsWith(type2)) {
			AppOrder o = appOrderService.selectById(orderid);
			if (o.getPurchaserID() != null
					&& !user.getId().equals(o.getPurchaserID())) {
				xd = 1;
			} else {
				List<String> list = appSdOrderService.selectSdUser();
				if (list.contains(o.getUserName())) {
					e = new AppOrder();
					e.setId(orderid);
					list.remove(o.getUserName());
					Random r = new Random();
					String name2 = list.get(r.nextInt(list.size()));

					AppUser au = new AppUser();
					au.setUsername(name2);
					au = appUserService.selectOne(au);
					if (au != null) {
						e.setPurchaserID(au.getId());
						e.setPurchaserName(au.getUsername());
						e.setPurchaserPhone(au.getPhone());
					}
					e.setLockTime(DateUtil.dateToStrSS());
					e.setOrderStatus("2");
					appOrderService.update(e);
					JSONObject js1 = new JSONObject();
					js1.put("success", 1);
					js1.put("MSG", "1");
					json = js1.toString();
					return "json";
				} else {
					e = new AppOrder();
					e.setId(orderid);
					e.setPurchaserID(user.getId());
					e.setPurchaserName(user.getUsername());
					e.setPurchaserPhone(user.getPhone());
					appOrderService.update(e);
				}
			}
		}
		JSONObject js = new JSONObject();
		if (xd == 1) {
			js.put("success", 1);
			js.put("MSG", "1");// 已下单
		} else {
			String s = "{\"userid\":\"" + user.getId() + "\",\"orderid\":\""
					+ orderid + "\",\"type\":\"" + type + "\"}";
			js.put("MSG", AppDesEncrypt.getEncString(s));
			js.put("success", 1);
		}
		json = js.toString();
		return "json";
		
	}
	
	@SuppressWarnings("unchecked")
	public String toPay() throws IOException{
		System.out.println(msg); 
		msg = AppDesEncrypt.getDesString(msg);
		System.out.println(msg);
		Map<String, Object> map = parseJSON2Map(msg);
		String orderid = (String) map.get("orderid");
		String userid = (String) map.get("userid");
		String type = (String) map.get("type");
		
		if(isEmpty(orderid)||isEmpty(userid)){
//			throw new NullPointerException("参数为空!");
			json = ERROR_MSG;
			return "json";
		}
		AppUser user = appUserService.selectById(userid);
		AppOrder ord = appOrderService.selectById(orderid);
		if(user==null){
//			throw new NullPointerException("用户错误!");
			json = ERROR_MSG;
			return "json";
		}
		if(type.equals(type3)){
			
		}else if(type.equals(type1)){
			if(!user.getId().equals(ord.getUserID())){
//				throw new NullPointerException("用户错误!");
				json = ERROR_MSG;
				return "json";
			}
		}
		
		
		//创建支付记录对象
		Orderpay orderpay = new Orderpay();
		orderpay.setOrderid(type+orderid);
		orderpay.setPaystatus(net.jeeshop.services.common.Orderpay.orderpay_paystatus_n);
		orderpay.setPaymethod("nybank");//农业银行
		
		Double jyje=0.00;//交易金额
		if(type.equals(type3)){
			jyje=Double.valueOf(ord.getFinalPrice());
		}else if(type.equals(type2)||type.equals(type1)){
			jyje=Double.valueOf(KeyValueHelper.get("APP_SZDJ"));
		}else{
//			json="失败!"; 
			json = ERROR_MSG;
			return "json";	
		}
		orderpay.setPayamount(jyje);
		orderpayService.insert(orderpay);
		
		/*
					
					  orderid=type+orderid;
					  
					  Orderpay op= new Orderpay();
					  op.setOrderid(orderid);
					  List<Orderpay> list = orderpayService.selectList(op);
					  if(list==null||list.size()==0){
						  throw new NullPointerException("未找到支付记录!");
					  }
					  op.setPaystatus(Orderpay.orderpay_paystatus_y);
					  orderpayService.update(op);
					  
					  op = list.get(0);
					  
					  e=new AppOrder();
					  if(orderid.startsWith(type3)){
					  e.setId(orderid.replace(type3, ""));
					  e.setOrderStatus("5");
				      e.setPaymentTime(DateUtil.dateToStr(new Date()));
					  }else if(orderid.startsWith(type2)){
						  e.setId(orderid.replace(type2, ""));
						  e.setSubscription(op.getPayamount());
						  e.setOrderStatus("2");
					  }else if(orderid.startsWith(type1)){
						  e.setId(orderid.replace(type1, ""));
						  e.setPurchaserSubscription(op.getPayamount());
						  e.setOrderStatus("4");
					  }
					  appOrderService.update(e);
					  
					  
					  AppOrder o2 = appOrderService.selectById(e.getId());
					  
					//发送短信
					  if(orderid.startsWith(type3)){
						String sms2 = CommonPropertiesUtil.getSMStemValue( "WAIT_SELLER_SEND_GOODS").replace("orderid", e.getId());
						String rtmsg2=SendSMSUtil.sendSMS(sms2,o2.getPhone());
						logger.error("支付成功发送短信--->号码："+o2.getPhone()+"，内容："+sms2+",结果："+rtmsg2);
					  }
					  getResponse().sendRedirect(getRequest().getContextPath()+"/appJsp/paySuccess.jsp");
					  return null;
		*/
		
		String ds = DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");

			//1、生成订单对象
			PaymentRequest tPaymentRequest = new PaymentRequest();
			tPaymentRequest.dicOrder.put("PayTypeID","ImmediatePay");// request.getParameter("PayTypeID"));                   //设定交易类型
			tPaymentRequest.dicOrder.put("OrderDate", ds.substring(0, 10).replace("-", "/"));                   //设定订单日期 （必要信息 - YYYY/MM/DD）
			tPaymentRequest.dicOrder.put("OrderTime", ds.substring(11,19) ) ;                   //设定订单时间 （必要信息 - HH:MM:SS）
			tPaymentRequest.dicOrder.put("orderTimeoutDate", new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date( System.currentTimeMillis()+6000000L ))     ) ;// request.getParameter("ExpiredDate"));               //设定订单保存时间     //设定订单有效期
			tPaymentRequest.dicOrder.put("OrderNo", orderpay.getId());                       //设定订单编号 （必要信息）
			tPaymentRequest.dicOrder.put("CurrencyCode", "156");//request.getParameter("CurrencyCode"));             //设定交易币种
			tPaymentRequest.dicOrder.put("OrderAmount", jyje );      //设定交易金额
			tPaymentRequest.dicOrder.put("InstallmentMark","0");// request.getParameter("InstallmentMark"));       //分期标识
			tPaymentRequest.dicOrder.put("CommodityType", "2") ;//request.getParameter("CommodityType"));           //设置商品种类
			
			//3、生成支付请求对象
			String paymentType = "A" ;// request.getParameter("PaymentType");---------------------------------------------------------------------------------------------------------------------------------------
			
			tPaymentRequest.dicRequest.put("PaymentType", paymentType);            //设定支付类型
			String paymentLinkType  = "2" ;// request.getParameter("PaymentLinkType");                                         
			tPaymentRequest.dicRequest.put("PaymentLinkType", paymentLinkType);    //设定支付接入方式
			if (paymentType.equals(Constants.PAY_TYPE_UCBP) && paymentLinkType.equals(Constants.PAY_LINK_TYPE_MOBILE))
			{
			    tPaymentRequest.dicRequest.put("UnionPayLinkType","0" );//request.getParameter("UnionPayLinkType"));  //当支付类型为6，支付接入方式为2的条件满足时，需要设置银联跨行移动支付接入方式
			}
			tPaymentRequest.dicRequest.put("NotifyType","1");// request.getParameter("NotifyType"));              //设定通知方式0：URL页面通知 1：服务器通知，*必输
			tPaymentRequest.dicRequest.put("ResultNotifyURL","http://www.my360.cn/appOrder!ny_rec");// request.getParameter("ResultNotifyURL"));    //设定通知URL地址
			tPaymentRequest.dicRequest.put("MerchantRemarks","TEST" ) ;// request.getParameter("MerchantRemarks"));    //设定附言
			tPaymentRequest.dicRequest.put("IsBreakAccount", "0") ;// request.getParameter("IsBreakAccount"));      //设定交易是否分账
			
			JSON json2 = tPaymentRequest.postRequest();
			
			String ReturnCode = json2.GetKeyValue("ReturnCode");
//			String ErrorMessage = json2.GetKeyValue("ErrorMessage");
			if (ReturnCode.equals("0000"))
			{  
				  getResponse().sendRedirect(json2.GetKeyValue("PaymentURL"));
			}
			else
			{
				System.out.println("已经支付过!ReturnCode="+ReturnCode);
			  //可能已经支付过. 
			  if ("2306".equals(ReturnCode)){
				  QueryOrderRequest tQueryRequest = new QueryOrderRequest();
				    tQueryRequest.queryRequest.put("PayTypeID",  "ImmediatePay" );    //设定交易类型
				    tQueryRequest.queryRequest.put("OrderNo", orderpay.getId() );    //设定订单编号 （必要信息）
				    tQueryRequest.queryRequest.put("QueryDetail", "0" );//设定查询方式
				    JSON json3 = tQueryRequest.postRequest();
				    String orderInfo = json3.GetKeyValue("Order");
				    Base64 tBase64 = new Base64();
					String orderDetail = new String(tBase64.decode(orderInfo));
				    json3.setJsonString(orderDetail);
				    String stat =  json3.GetKeyValue("Status"); 
				    System.out.println("再次查询状态stat=="+stat);
			    if( "04".equalsIgnoreCase(stat)){
			    	
			    	e=new AppOrder();
			    	e.setId(orderid);
			    	if(type.equals(type3)){
						  e.setOrderStatus("5");
					      e.setPaymentTime(DateUtil.dateToStr(new Date()));
						  }else if(type.equals(type2)){
							  e.setSubscription(orderpay.getPayamount());
							  e.setOrderStatus("2");
						  }else if(type.equals(type1)){
							  e.setPurchaserSubscription(orderpay.getPayamount());
							  e.setOrderStatus("4");
						  }
			    	 appOrderService.update(e);
			    	 
			    	 
			    	 //更新订单支付记录
			    	 orderpay.setPaystatus(net.jeeshop.services.common.Orderpay.orderpay_paystatus_y);
			    	 orderpayService.update(orderpay);
			    	 String otype="出售";
			    	 String phone=ord.getPhone();
			    	 if(ord.getOrderType().equals("2")){
			    		 otype="收购";
			    		 phone=ord.getPurchaserPhone();
			    	 }
					//发送短信
					  if(type.equals(type3)){
						String sms2 = CommonPropertiesUtil.getSMStemValue( "WAIT_SELLER_SEND_GOODS_app").replace("type", otype).replace("name", ord.getTitle());
						String rtmsg2=SendSMSUtil.sendSMS(sms2,phone);
						logger.error("支付成功发送短信--->号码："+phone+"，内容："+sms2+",结果："+rtmsg2);
					  }
					  getResponse().sendRedirect(getRequest().getContextPath()+"/appJsp/paySuccess.jsp");
			     }
			  }
			}
			
//			json="失败!"; 
			json = ERROR_MSG;
			return "json";	
			
}
	
	public String ny_rec() throws Exception{
		getRequest().setCharacterEncoding("GBK");
		//1、取得MSG参数，并利用此参数值生成支付结果对象
		String msg =getRequest().getParameter("MSG");
		Base64 tBase64 = new Base64();

		String tMessage = new String(tBase64.decode(msg),"gbk");
		System.out.println("callback:"+tMessage);
		XMLDocument doc = MerchantConfig.getUniqueInstance().verifySignXML(new XMLDocument(tMessage));
		TrxResponse tResult = new TrxResponse(doc);

		//2、判断支付结果状态，进行后续操作
		if (tResult.isSuccess()) {
		  System.out.println("TrxType         = [" + tResult.getValue("TrxType"        ) + "]<br>");
		  System.out.println("OrderNo         = [" + tResult.getValue("OrderNo"        ) + "]<br>");
		  System.out.println("Amount          = [" + tResult.getValue("Amount"         ) + "]<br>");
		  System.out.println("BatchNo         = [" + tResult.getValue("BatchNo"        ) + "]<br>");
		  System.out.println("VoucherNo       = [" + tResult.getValue("VoucherNo"      ) + "]<br>");
		  System.out.println("HostDate        = [" + tResult.getValue("HostDate"       ) + "]<br>");
		  System.out.println("HostTime        = [" + tResult.getValue("HostTime"       ) + "]<br>");
		  System.out.println("MerchantRemarks = [" + tResult.getValue("MerchantRemarks") + "]<br>");
		  System.out.println("PayType         = [" + tResult.getValue("PayType"        ) + "]<br>");
		  System.out.println("NotifyType      = [" + tResult.getValue("NotifyType"     ) + "]<br>");
		  System.out.println("TrnxNo          = [" + tResult.getValue("iRspRef"        ) + "]<br>");
		  
		  String opid=tResult.getValue("OrderNo");
		  
		  Orderpay op = orderpayService.selectById(opid);
		  
		  if(op.getPaystatus().equals(net.jeeshop.services.common.Orderpay.orderpay_paystatus_n)){
			  
		  String orderid=op.getOrderid();
		  op.setPaystatus(net.jeeshop.services.common.Orderpay.orderpay_paystatus_y);
		  orderpayService.update(op);
		  
		  e=new AppOrder();
		  if(orderid.startsWith(type3)){
		  e.setId(orderid.replace(type3, ""));
		  e.setOrderStatus("5");
	      e.setPaymentTime(DateUtil.dateToStr(new Date()));
		  }else if(orderid.startsWith(type2)){
			  e.setId(orderid.replace(type2, ""));
			  e.setSubscription(op.getPayamount());
			  e.setOrderStatus("2");
		  }else if(orderid.startsWith(type1)){
			  e.setId(orderid.replace(type1, ""));
			  e.setPurchaserSubscription(op.getPayamount());
			  e.setOrderStatus("4");
		  }
		  appOrderService.update(e);
		  
		  
		  AppOrder ord = appOrderService.selectById(e.getId());
		  
		  String otype="出售";
	    	 String phone=ord.getPhone();
	    	 if(ord.getOrderType().equals("2")){
	    		 otype="收购";
	    		 phone=ord.getPurchaserPhone();
	    	 }
			//发送短信
			  if(type.equals(type3)){
				String sms2 = CommonPropertiesUtil.getSMStemValue( "WAIT_SELLER_SEND_GOODS_app").replace("type", otype).replace("name", ord.getTitle());
				String rtmsg2=SendSMSUtil.sendSMS(sms2,phone);
				logger.error("支付成功发送短信--->号码："+phone+"，内容："+sms2+",结果："+rtmsg2);
			  }
		  }
		  getResponse().sendRedirect(getRequest().getContextPath()+"/appJsp/paySuccess.jsp");
		}
		return "json";
	}
	
	
	private Boolean isEmpty(String value){
		return value==null||value.equals("");
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> parseJSON2Map(String jsonStr){  
        Map<String, Object> map = new HashMap<String, Object>();  
        //最外层解析  
        JSONObject json = JSONObject.fromObject(jsonStr);  
        for(Object k : json.keySet()){  
            Object v = json.get(k);   
            //如果内层还是数组的话，继续解析  
            if(v instanceof JSONArray){  
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
                Iterator<JSONObject> it = ((JSONArray)v).iterator();  
                while(it.hasNext()){  
                    JSONObject json2 = it.next();  
                    list.add(parseJSON2Map(json2.toString()));  
                }  
                map.put(k.toString(), list);  
            } else {  
                map.put(k.toString(), v);  
            }  
        }  
        return map;  
    }  

	@Override
	public void insertAfter(AppOrder e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if (this.e == null) {
			this.e = new AppOrder();
		}
	}

	@Override
	protected void selectListAfter() {
		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	@Override
	public AppOrder getE() {
		// TODO Auto-generated method stub
		return this.e;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
