package net.jeeshop.services.front.product.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品库存对象
 * 
 * @author huangf
 * 
 */
public class ProductStockInfo {
	private String id;// 商品ID
	private volatile int stock;// 商品库存
	private boolean changeStock;// 库存是否有所改变，false:库存未改变。true：库存已经改变
	private int score;//赠送积分
	private String activityID;//活动ID
	private Map<String,Integer> specMap=new HashMap<String, Integer>();//规格库存
	
//	List<Spec>
	public ProductStockInfo() {
		super();
	}

	public ProductStockInfo(String id, int stock) {
		super();
		this.id = id;
		this.stock = stock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStock() {
		return stock;
	}

	public synchronized void setStock(int stock) {
		this.stock = stock;
	}

	public boolean isChangeStock() {
		return changeStock;
	}

	public void setChangeStock(boolean changeStock) {
		this.changeStock = changeStock;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getActivityID() {
		return activityID;
	}

	public void setActivityID(String activityID) {
		this.activityID = activityID;
	}
	
	

	public Map<String, Integer> getSpecMap() {
		return specMap;
	}

	public void setSpecMap(Map<String, Integer> specMap) {
		this.specMap = specMap;
	}

	@Override
	public String toString() {
		return "ProductStockInfo [id=" + id + ", stock=" + stock
				+ ", changeStock=" + changeStock + ", score=" + score
				+ ", activityID=" + activityID + "]";
	}

}
