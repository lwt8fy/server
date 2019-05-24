package net.jeeshop.web.action.front.news;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.BaseAction;
import net.jeeshop.services.front.news.NewsService;
import net.jeeshop.services.front.news.bean.News;
import net.sf.json.JSONObject;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
*/
/**
 * 文章管理
 * 
 * @author huangf
 * 
 */
public class SzmyAppNewsAction extends BaseAction<News> {
	private static final long serialVersionUID = 1L;
	/*private static final Logger logger = LoggerFactory
			.getLogger(SzmyAppNewsAction.class);*/
	private NewsService newsService;
	private String helpCode;
	private News news;
	private String json ;
	
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
//		pager.setPagerUrl("news!selectList.action");
	}

	
	/**
	 * 获取新闻列表
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String newsList() throws Exception{
		News newsInfo = new News();
		newsInfo.setType(net.jeeshop.services.common.News.news_type_notice);
		int os=pager.getOffset();
		pager.setOffset((os - 1) * e.getPageSize());
		e.setType("notice");
		super.selectList();
		pager.setOffset(os);
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		List plist = pager.getList();
		if(pager.getTotal()==0){
			JSONObject jsonObject = JSONObject.fromObject(pager);
			jsonObject.put("success", "0");
		    json=jsonObject.toString();
			return "json";
		}
		for(int i =0;i<plist.size();i++){
			 News news = (News)plist.get(i);
			 Map<String, Object> result = new HashMap<String, Object>();
			 result.put("id", news.getId());
			 result.put("createtime", news.getCreatetime());
			 result.put("title", news.getTitle());
			 list.add(result);
		}
		pager.setList(list);
		JSONObject jsonObject = JSONObject.fromObject(pager);
		jsonObject.put("success", "1");
	    json=jsonObject.toString();
	    System.out.println(pager.getOffset()+"          "+11111111);
	    System.err.println(json);
		return "json";
	}
	
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}
