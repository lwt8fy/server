package net.jeeshop.web.action.front.szmyComment;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.common.SzmyComment;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.front.szmyComment.service.SzmyCommentService;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
/**
 * teng
 * @author Administrator
 *<!--通用评论管理,包含生猪交易,物流交易,平台商品交易评价 -->
 */
public class SzmyCommentAction extends BaseAction <SzmyComment>  {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SzmyCommentAction.class);
	private SzmyCommentService szmyCommentService;
	private AppUserService appUserService;
	public void setSzmyCommentService(SzmyCommentService szmyCommentService) {
		this.szmyCommentService=szmyCommentService;
	}
	
	private String uuid;
	private String json;

	
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";
	
	
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
		return "toJson";
	}
	/**
	 * 插入
	 */
	@Override
	public String insert() throws Exception {
		try{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u==null){
				json = ERROR_MSG;
			}else{
				if(StringUtils.isBlank(e.getParentID())){
					e.setParentID("0");
				}
				e.setUserName(u.getUsername());
				e.setCreateTime(DateUtil.dateToStrSS());
				szmyCommentService.insert(e);
				json=SUCCESS_MSG;
			}
			}catch (Exception e) {
				e.printStackTrace();
				json = Exception_MSG;
			}
			return "toJson";
	}
	
	@Override
	public void insertAfter(SzmyComment e) {

	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
		this.e = new SzmyComment();
		}
	}

	@Override
	public void selectListAfter() {

	}

	@Override
	public SzmyComment getE() {
		return this.e;
	}
}
