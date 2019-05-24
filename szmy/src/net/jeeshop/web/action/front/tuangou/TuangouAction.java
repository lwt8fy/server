package net.jeeshop.web.action.front.tuangou;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.front.product.ProductService;
import net.jeeshop.services.front.product.bean.Product;
import net.jeeshop.services.manage.activity.ActivityService;
import net.jeeshop.services.manage.activity.bean.Activity;

public class TuangouAction  extends BaseAction<Activity>{

	private static final long serialVersionUID = 1L;
	private ProductService productService;
	private ActivityService activityService;
	
	private String toIndex="toIndex";
	
	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

	@Override
	public Activity getE() {
		return null;
	}

	@Override
	public void insertAfter(Activity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void selectListAfter() {
		pager.setPagerUrl("tuangou!index.action");
	}
	
	public String index(){
		getSession().setAttribute("selectMenu", "tuangou");
		loadTuanGouJingXuan();
		/*Activity e=new Activity();
		e.setActivityType("t");
		e.setStatus("y");
		this.initPageSelect();
		int offset = pager.getOffset();//分页偏移量
		if (offset < 0)
			offset = 0;
		((Activity) getE()).setOffset(offset);
		pager = getServer().selectPageList(e);
		if(pager==null)pager = new Logistics();
		pager.setPagerSize((pager.getTotal() + pager.getPageSize() - 1) / pager.getPageSize());
		selectListAfter();*/
		loadTuanGouList();
		return toIndex;
	}
	/**
	 * 加载团购每日精选
	 */
	public void loadTuanGouJingXuan(){
		String pids[]=KeyValueHelper.get("meirijingxuan").split(",");
		List<String> productIds=new ArrayList<String>();
		for (int i = 0; i < pids.length; i++) {
			productIds.add(pids[i]);
		}
		net.jeeshop.services.front.product.bean.Product p=new Product();
		p.setProductIds(productIds);
		List<Product> lists=productService.selectProductListByIds(p);
		List<Product> prolists=new ArrayList<Product>();
		for (int i = 0; i < lists.size(); i++) {
			Activity a=getDateByProductId(lists.get(i).getId());
			if(a!=null){
				if(DateUtil.strToDatehhmmss(a.getStartDate().substring(0,19)).after(new Date())){
					lists.get(i).setIsStart("n");
				}else{
					if(lists.get(i).getActivityPrice()!=null&&lists.get(i).getActivityPrice()>0){
						lists.get(i).setNowPrice(lists.get(i).getActivityPrice()+"");//设置活动价格
					}
				}
				lists.get(i).setNowDate(DateUtil.dateToStr(new Date(), "yyyy/MM/dd HH:mm:ss"));
				lists.get(i).setStartDate(a.getStartDate().substring(0,19).replace("-", "/"));
				lists.get(i).setEndDate(a.getEndDate().substring(0,19).replace("-", "/"));
				
				prolists.add(lists.get(i));
			}
		}
		getRequest().setAttribute("tuanGouJingXuanList", prolists);
	}
	/**
	 * 加载团购商品
	 */
	public void loadTuanGouList(){
		Activity e=new Activity();
		e.setActivityType("t");
		e.setStatus("y");
		List<Activity> list=activityService.selectList(e);
		List<String> productIds=new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			String pids[]=list.get(i).getProductID().split(",");
			for (int j = 0; j < pids.length; j++) {
				productIds.add(pids[j]);
			}
		}
		net.jeeshop.services.front.product.bean.Product p=new Product();
		p.setProductIds(productIds);
		List<Product> lists=productService.selectProductListByIds(p);
		for (int i = 0; i < lists.size(); i++) {
			Activity a=getDateByProductId(lists.get(i).getId());
			if(DateUtil.strToDatehhmmss(a.getStartDate().substring(0,19)).after(new Date())){
				lists.get(i).setIsStart("n");
			}else{
				if(lists.get(i).getActivityPrice()!=null&&lists.get(i).getActivityPrice()>0){
					lists.get(i).setNowPrice(lists.get(i).getActivityPrice()+"");//设置活动价格
				}
			}
			lists.get(i).setNowDate(DateUtil.dateToStr(new Date(), "yyyy/MM/dd HH:mm:ss"));
			lists.get(i).setStartDate(a.getStartDate().substring(0,19).replace("-", "/"));
			lists.get(i).setEndDate(a.getEndDate().substring(0,19).replace("-", "/"));
		}
		getRequest().setAttribute("activityTuanProductList", lists);
	}
	/**
	 * 根据活动商品ID查询活动开始结束时间
	 */
	public Activity getDateByProductId(String productid){
		Activity e=activityService.selectByPid(productid);
		return e;
	}
}
