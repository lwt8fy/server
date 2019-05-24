package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;


public class CatalogType extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String catalogName;//目录名称
	private String catalogID;//目录id
	private String type;//类型 
	private String catalogPID;//父id
	private String catalogPName;//父名称 
	
	public CatalogType(){}
	public CatalogType(String type){
		this.type=type;
	}

	public void setCatalogName(String catalogName){
		this.catalogName=catalogName;
	}
	public String getCatalogName(){
		return catalogName;
	}
	public void setCatalogID(String catalogID){
		this.catalogID=catalogID;
	}
	public String getCatalogID(){
		return catalogID;
	}
	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
	}
	public String getCatalogPID() {
		return catalogPID;
	}
	public void setCatalogPID(String catalogPID) {
		this.catalogPID = catalogPID;
	}
	public String getCatalogPName() {
		return catalogPName;
	}
	public void setCatalogPName(String catalogPName) {
		this.catalogPName = catalogPName;
	}
	
}

