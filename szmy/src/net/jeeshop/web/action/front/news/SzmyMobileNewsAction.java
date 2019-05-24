package net.jeeshop.web.action.front.news;

import java.util.List;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.FrontContainer;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.front.news.NewsService;
import net.jeeshop.services.front.news.bean.News;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文章管理
 * 
 * @author huangf
 * 
 */
public class SzmyMobileNewsAction extends BaseAction<News> {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(SzmyMobileNewsAction.class);
	private NewsService newsService;
	private String helpCode;
	private News news;
	private final int pageSize = 20;
	private String jsonStr ="";
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public String getHelpCode() {
		return helpCode;
	}

	public void setHelpCode(String helpCode) {
		this.helpCode = helpCode;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	@Override
	public News getE() {
		return this.e;
	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
			this.e = new News();
		}
	}

	@Override
	public void insertAfter(News e) {
	}

	@Override
	protected void selectListAfter() {
		pager.setPagerUrl("news!selectList.action");
	}
	/**
	 * 
	 * 作者：王海洋
	 * 时间：2015-12-27下午01:48:32
	 * 描述: 显示资讯列表
	 * @return
	 */
	public String toShowNewsList(){
		
		return "toShowNewsList";
	}
	/**
	 * 获取新闻详情
	 * @return
	 */
	public String newsInfo() throws Exception{
		
		super.setSelectMenu(FrontContainer.not_select_menu);//设置主菜单为不选中
		String zmt = getRequest().getParameter("zmt");
		String id = getRequest().getParameter("id");
		logger.error("NewsAction.newsInfo=== id="+id);
		if(StringUtils.isBlank(id)){
			throw new NullPointerException("id is null");
		}
		getRequest().setAttribute("zmt", zmt);
		News newsParam = new News();
		newsParam.setId(id);
		e =newsService.selectOne(newsParam);
		/*if(e==null){
			jsonStr="2";
		}*/
		
		String url = "/jsp/notices/"+e.getId()+".jsp";
		logger.error("url = " + url);
		getRequest().setAttribute("newsInfoUrl",url);
		return "newsInfo";
	}
	
	/**
	 * 帮助中心
	 * @return
	 */
	public String help() throws Exception {
		
		super.setSelectMenu(FrontContainer.not_select_menu);//设置主菜单为不选中
		
		logger.error("this.helpCode="+this.helpCode);
		if(StringUtils.isBlank(this.helpCode)){
			throw new NullPointerException("helpCode参数不能为空");
		}else if(this.helpCode.equals("index")){
			return "help";
		}else{
			News newsParam = new News();
			newsParam.setCode(helpCode);
			news = newsService.selectSimpleOne(newsParam);
			if(news==null){
				throw new NullPointerException("根据code查询不到文章！");
			}
			
			String url = "/jsp/helps/"+news.getId()+".jsp";
			logger.error("url = " + url);
			getRequest().setAttribute("newsInfoUrl",url);
			
			return "help";
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getNewsJson() throws Exception{
		String offset = this.getRequest().getParameter("offset");
		News newsInfo = new News();
		newsInfo.setType(net.jeeshop.services.common.News.news_type_notice);
		if(StringUtils.isNotBlank(offset)){
			newsInfo.setOffset(Integer.parseInt(offset)*pageSize);
		}else{
			newsInfo.setOffset(0);
		}
		pager = newsService.selectPageList(newsInfo);
		if(pager==null)pager = new PagerModel();
		// 计算总页数
		newsInfo.setPagerSize((newsInfo.getTotal() + newsInfo.getPageSize() - 1)
				/ newsInfo.getPageSize());
		List<News> newsList = getPager().getList();
		if(pager.getTotal()==0){
			this.jsonStr="0";
			return "json";
		}
		JSONArray jsonObject = JSONArray.fromObject(newsList);
		jsonStr = jsonObject.toString();
		return "json";
		
	}
	
}
