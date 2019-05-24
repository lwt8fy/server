package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.page.PagerModel;


public class ScrollPic extends PagerModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String id;
	private String imgPath;
	private int orderNum;
	private String floor;
	private String srcPath;
	private String tip;

	@Override
	public void clear() {
		super.clear();
		id = null;
		imgPath = null;
		srcPath = null;
		tip = null;
	}
	
	@Override
	public void setId(String id){
		this.id=id;
	}
	@Override
	public String getId(){
		return id;
	}
	public void setImgPath(String imgPath){
		this.imgPath=imgPath;
	}
	public String getImgPath(){
		return imgPath;
	}
	public void setOrderNum(int orderNum){
		this.orderNum=orderNum;
	}
	public int getOrderNum(){
		return orderNum;
	}
	public void setSrcPath(String srcPath){
		this.srcPath=srcPath;
	}
	public String getSrcPath(){
		return srcPath;
	}
	public void setTip(String tip){
		this.tip=tip;
	}
	public String getTip(){
		return tip;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}
	
}

