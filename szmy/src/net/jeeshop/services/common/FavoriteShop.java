package net.jeeshop.services.common;

import java.util.Date;
import java.util.List;
import java.io.Serializable;
import net.jeeshop.core.dao.page.PagerModel;


public class FavoriteShop extends PagerModel implements Serializable {
private static final long serialVersionUID = 1L;
	private Date createtime;
	private String id;
	private String companyID;
	private String account;
	private List<net.jeeshop.services.front.product.bean.Product> products;
	public List<net.jeeshop.services.front.product.bean.Product> getProducts() {
		return products;
	}

	public void setProducts(
			List<net.jeeshop.services.front.product.bean.Product> products) {
		this.products = products;
	}

	private Company company;
	@Override
	public void clear(){
		super.clear();
		id=null;
		account=null;
		companyID=null;
		createtime=null;
		if(company!=null){
			company.clear();
		}
	}
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public void setCreatetime(Date createtime){
		this.createtime=createtime;
	}
	public Date getCreatetime(){
		return createtime;
	}
	@Override
	public void setId(String id){
		this.id=id;
	}
	@Override
	public String getId(){
		return id;
	}
	public void setcompanyID(String companyID){
		this.companyID=companyID;
	}
	public String getcompanyID(){
		return companyID;
	}
	public void setAccount(String account){
		this.account=account;
	}
	public String getAccount(){
		return account;
	}
}

