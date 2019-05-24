package net.jeeshop.web.action.front.favorite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.jeeshop.core.BaseAction;
import net.jeeshop.services.common.AppUser;
import net.jeeshop.services.front.appUser.service.AppUserService;
import net.jeeshop.services.front.favorite.FavoriteService;
import net.jeeshop.services.front.favorite.bean.Favorite;
import net.jeeshop.services.front.product.ProductService;
import net.jeeshop.services.front.product.bean.Product;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class SzmyFavoriteAction extends BaseAction<Favorite> {
	private static final long serialVersionUID = 1L;
	
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(SzmyFavoriteAction.class);
	private AppUserService appUserService;
	private FavoriteService favoriteService;
	private ProductService productService;
	
	private String json;
	private String uuid;
	
	
	private final String SUCCESS_MSG="{\"success\":\"1\"}";
	private final String ERROR_MSG="{\"success\":\"0\"}";
	private final String Exception_MSG="{\"success\":\"3\"}";


	
	
	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	
	
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}



	public void setFavoriteService(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}


	/**
	 * 分页查询和个人列表查询
	 */
	@Override
	public String selectList() throws Exception {
		// TODO Auto-generated method stub
		
		try{
			AppUser u = appUserService.selectByUuid(uuid);
			if(u==null){
				json = ERROR_MSG;
			}else{
			e.setAccount(u.getUsername());
			List<Favorite> list = favoriteService.selectList(e);
			List<String> productIds = new LinkedList<String>();
			for(int i=0;i<list.size();i++){
				Favorite ff =list.get(i);
				productIds.add(ff.getProductID());
			}
			
			//根君商品ID集合加载商品信息：名称、价格、销量、是否上下架等
			List<Product> productList=new ArrayList<Product>();
			if(productIds!=null&&productIds.size()>0){
			Product p = new Product();
			p.setProductIds(productIds);
			productList = productService.selectProductListByIds(p);
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("list", productList);
			jsonObject.put("success", "1");
			json=jsonObject.toString();
			}
		}catch (Exception e) {
			e.printStackTrace();
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
				e.setAccount(u.getUsername());
				List<Favorite> list = favoriteService.selectList(e);
				if(list!=null&&list.size()>0){
					json=ERROR_MSG;
					return "json";
				}
				favoriteService.insert(e);
				json=SUCCESS_MSG;
			}
			}catch (Exception e) {
				System.out.println(e);
				json = Exception_MSG;
			}
			return "json";
	}

	public String deleteById() {
		// TODO Auto-generated method stub
		try{
		AppUser u = appUserService.selectByUuid(uuid);
		if(u==null){
			json = ERROR_MSG;
		}else{
			if(StringUtils.isEmpty(e.getId())){
				e.setAccount(u.getUsername());
				List<Favorite> list = favoriteService.selectList(e);
				for (Favorite favorite : list) {
					favoriteService.delete(favorite);
				}
			}else{
			favoriteService.delete(e);
			}
			json=SUCCESS_MSG;
		}
		}catch (Exception e) {
			e.printStackTrace();
			json = Exception_MSG;
		}
		return "json";
	}
	
	
	
	@Override
	public void insertAfter(Favorite e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		if (this.e == null) {
			this.e = new Favorite();
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
	public Favorite getE() {
		// TODO Auto-generated method stub
		return this.e;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	
	
}
