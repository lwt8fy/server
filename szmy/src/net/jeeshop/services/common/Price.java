package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;


public class Price extends QueryModel implements Serializable {
private static final long serialVersionUID = 1L;
	private String id;
	private String createTime;//创建时间
	private String type;//类型 0：全国，1：省，2：市，3：县
	private String province;//省
	private String city;//城市
	private String area;//区
	private String areaType;//大区域
	private Double ym;//玉米
	private Double dp;//豆粕
	private String zlb;//猪粮比
	private Double nsy;//内三元
	private Double tzz;//土杂猪
	private Double wsy;//外三元
	
	private String whereSql;//查询sql字段,数据库无字段

	
	public String getWhereSql() {
		return whereSql;
	}
	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}
	public void setCreateTime(String createTime){
		this.createTime=createTime;
	}
	public String getCreateTime(){
		return createTime;
	}
	public void setTzz(Double tzz){
		this.tzz=tzz;
	}
	public Double getTzz(){
		return tzz;
	}
	public void setWsy(Double wsy){
		this.wsy=wsy;
	}
	public Double getWsy(){
		return wsy;
	}
	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
	}
	public void setNsy(Double nsy){
		this.nsy=nsy;
	}
	public Double getNsy(){
		return nsy;
	}
	public void setCity(String city){
		this.city=city;
	}
	public String getCity(){
		return city;
	}
	@Override
	public void setId(String id){
		this.id=id;
	}
	@Override
	public String getId(){
		return id;
	}
	public void setArea(String area){
		this.area=area;
	}
	public String getArea(){
		return area;
	}
	public void setAreaType(String areaType){
		this.areaType=areaType;
	}
	public String getAreaType(){
		return areaType;
	}
	
	public String getZlb() {
		return zlb;
	}
	public void setZlb(String zlb) {
		this.zlb = zlb;
	}
	public void setProvince(String province){
		this.province=province;
	}
	public String getProvince(){
		return province;
	}
	public void setYm(Double ym){
		this.ym=ym;
	}
	public Double getYm(){
		return ym;
	}
	public void setDp(Double dp){
		this.dp=dp;
	}
	public Double getDp(){
		return dp;
	}
}

