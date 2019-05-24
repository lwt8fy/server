package net.jeeshop.core;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import net.jeeshop.core.util.DateUtil;
import net.jeeshop.services.common.Price;
import org.apache.struts2.ServletActionContext;
public class StaticNewsByTime {
	String createtime=DateUtil.dateToStr(new Date());
	String yesterday=DateUtil.dateToStr(DateUtil.addDay(new Date(), -1));
	String lastweek=DateUtil.dateToStr(DateUtil.addDay(new Date(), -7));
	DecimalFormat    df   = new DecimalFormat("######0.00");   
		/*
		 * 根据创建时间写出猪类jsp文件
		 */
	public void writeToPigFile(List<Price> cityList,List<Price> ycityList,List<Price> lcityList,String id){
	        BufferedWriter bufferedWriter = null;
	      String filename=ServletActionContext.getServletContext().getRealPath("/jsp/notices");

	      
	        try {
	        	bufferedWriter = new BufferedWriter(new FileWriter(filename+"/"+id+".jsp"));
	        	PrintWriter pw = new PrintWriter(bufferedWriter);
	        	
	        	pw.append("<%@ page contentType=\"text/html; charset=UTF-8\"%>\r\n");
	        	pw.append("");
	        	pw.append("<div>");
//外三元
	        	pw.append(" <table width=\"95%\" border=\"0\" align=\"center\" style=\"color:#000000;font-family:Simsun;\" class=\"ke-zeroborder\">\r\n");
	        	pw.append("<tbody>");
	        	pw.append("<tr>\r\n<td>\r\n<div style=\"font-family:宋体; color:#6D6D6D;\">\r\n");
	        	pw.append("<h2 style=\"font-family:\"Microsoft YaHei\"; color:#666666; \">"+createtime+"全国外三元价格排行榜</h2>\r\n");
	        	pw.append("<span style=\"line-height:30px;\">单位： （元/公斤）</span>\r\n");
	        	pw.append("<table width=\"100%\" border=\"0\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" style=\"border:none;text-align:center;color:#6D6D6D;font-size:14px;\" class=\"ke-zeroborder\">\r\n");
	        	pw.append("<tbody>\r\n");
	        	pw.append("<tr>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">区域分类</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">排名</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">省市</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">"+createtime+"</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">"+yesterday+"</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">较昨日涨跌</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">较上周涨跌</td>\r\n");
	        	pw.append(" </tr>\r\n");
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"4\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">东北</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	Collections.sort(cityList,new Comparator<Price>(){
	   			 @Override
				public int compare(Price o1, Price o2) {  
	   			        if(null!=o1&&null!=o2)  
	   			        {  
	   			        	double menu1=o1.getWsy();  
	   			        	double menu2=o2.getWsy();  
	   			            if(menu1>menu2){  
	   			                return 1;  
	   			            }else {  
	   			                return 0;  
	   			            }  
	   			        }  
	   			        return 0;  
	   			    }
	   		});
	        	 for(int i=0;i<cityList.size();i++){
	     	    	if(cityList.get(i).getAreaType().equals("东北")){
	     	    			 String province=cityList.get(i).getProvince();
	     	    			 Double price=cityList.get(i).getWsy();
	     	    			 for(int j=0;j<ycityList.size();j++){
	     	    				 if(province.equals(ycityList.get(j).getProvince())){
	     	    					 Double yPrice=ycityList.get(j).getWsy();
	     	    					 Double y=price-yPrice;
	     	    					 for(int l=0;l<lcityList.size();l++)
	     	    					 if(province.equals(lcityList.get(l).getProvince())){
	     	    						 Double lPrice=lcityList.get(l).getWsy();
	     	    						 Double lw=price-lPrice;
	     	    			 
	     	    			 int a=i+1;
	        	pw.append("<tr>\r\n");
	        	pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        	pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        	pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        	pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        	pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        	pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	     	    					 }
	     	    					 
	     	    				 }
	     	    			 }
	     	    	}
	        	 }
	        	
	        	
	        	 
	        	 pw.append("<tr>\r\n");
	        	 pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华北</td>\r\n");
	        	 pw.append("</tr>\r\n");
	        	 
	        	 for(int i=0;i<cityList.size();i++){
	        		 if(cityList.get(i).getAreaType().equals("华北")){
	        			 String province=cityList.get(i).getProvince();
	        			 Double price=cityList.get(i).getWsy();
	        			 for(int j=0;j<ycityList.size();j++){
	        				 if(province.equals(ycityList.get(j).getProvince())){
	        					 Double yPrice=ycityList.get(j).getWsy();
	        					 Double y=price-yPrice;
	        					 for(int l=0;l<lcityList.size();l++)
	        						 if(province.equals(lcityList.get(l).getProvince())){
	        							 Double lPrice=lcityList.get(l).getWsy();
	        							 Double lw=price-lPrice;
	        							 
	        							 int a=i+1;
	        							 pw.append("<tr>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							 pw.append("</tr>\r\n");
	        							 
	        						 }
	        					 
	        				 }
	        			 }
	        		 }
	        	 }
	        	 
	        	 
	        	 pw.append("<tr>\r\n");
	        	 pw.append("<td rowspan=\"7\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华东</td>\r\n");
	        	 pw.append("</tr>\r\n");
	        	 
	        	 for(int i=0;i<cityList.size();i++){
	        		 if(cityList.get(i).getAreaType().equals("华东")){
	        			 String province=cityList.get(i).getProvince();
	        			 Double price=cityList.get(i).getWsy();
	        			 for(int j=0;j<ycityList.size();j++){
	        				 if(province.equals(ycityList.get(j).getProvince())){
	        					 Double yPrice=ycityList.get(j).getWsy();
	        					 Double y=price-yPrice;
	        					 for(int l=0;l<lcityList.size();l++)
	        						 if(province.equals(lcityList.get(l).getProvince())){
	        							 Double lPrice=lcityList.get(l).getWsy();
	        							 Double lw=price-lPrice;
	        							 
	        							 int a=i+1;
	        							 pw.append("<tr>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							 pw.append("</tr>\r\n");
	        							 
	        						 }
	        					 
	        				 }
	        			 }
	        		 }
	        	 }
	        	 
	        	 
	        	 
	        	 pw.append("<tr>\r\n");
	        	 pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华中</td>\r\n");
	        	 pw.append("</tr>\r\n");
	        	 
	        	 for(int i=0;i<cityList.size();i++){
	        		 if(cityList.get(i).getAreaType().equals("华中")){
	        			 String province=cityList.get(i).getProvince();
	        			 Double price=cityList.get(i).getWsy();
	        			 for(int j=0;j<ycityList.size();j++){
	        				 if(province.equals(ycityList.get(j).getProvince())){
	        					 Double yPrice=ycityList.get(j).getWsy();
	        					 Double y=price-yPrice;
	        					 for(int l=0;l<lcityList.size();l++)
	        						 if(province.equals(lcityList.get(l).getProvince())){
	        							 Double lPrice=lcityList.get(l).getWsy();
	        							 Double lw=price-lPrice;
	        							 
	        							 int a=i+1;
	        							 pw.append("<tr>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							 pw.append("</tr>\r\n");
	        							 
	        						 }
	        					 
	        				 }
	        			 }
	        		 }
	        	 }
	        	 
	        	 
	        	 
	        	 pw.append("<tr>\r\n");
	        	 pw.append("<td rowspan=\"4\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华南</td>\r\n");
	        	 pw.append("</tr>\r\n");
	        	 
	        	 for(int i=0;i<cityList.size();i++){
	        		 if(cityList.get(i).getAreaType().equals("华南")){
	        			 String province=cityList.get(i).getProvince();
	        			 Double price=cityList.get(i).getWsy();
	        			 for(int j=0;j<ycityList.size();j++){
	        				 if(province.equals(ycityList.get(j).getProvince())){
	        					 Double yPrice=ycityList.get(j).getWsy();
	        					 Double y=price-yPrice;
	        					 for(int l=0;l<lcityList.size();l++)
	        						 if(province.equals(lcityList.get(l).getProvince())){
	        							 Double lPrice=lcityList.get(l).getWsy();
	        							 Double lw=price-lPrice;
	        							 
	        							 int a=i+1;
	        							 pw.append("<tr>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							 pw.append("</tr>\r\n");
	        							 
	        						 }
	        					 
	        				 }
	        			 }
	        		 }
	        	 }
	        	 
	        	 
	        	 
	        	 pw.append("<tr>\r\n");
	        	 pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">西北</td>\r\n");
	        	 pw.append("</tr>\r\n");
	        	 
	        	 for(int i=0;i<cityList.size();i++){
	        		 if(cityList.get(i).getAreaType().equals("西北")){
	        			 String province=cityList.get(i).getProvince();
	        			 Double price=cityList.get(i).getWsy();
	        			 for(int j=0;j<ycityList.size();j++){
	        				 if(province.equals(ycityList.get(j).getProvince())){
	        					 Double yPrice=ycityList.get(j).getWsy();
	        					 Double y=price-yPrice;
	        					 for(int l=0;l<lcityList.size();l++)
	        						 if(province.equals(lcityList.get(l).getProvince())){
	        							 Double lPrice=lcityList.get(l).getWsy();
	        							 Double lw=price-lPrice;
	        							 
	        							 int a=i+1;
	        							 pw.append("<tr>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							 pw.append("</tr>\r\n");
	        							 
	        						 }
	        					 
	        				 }
	        			 }
	        		 }
	        	 }
	        	 
	        	 
	        	 
	        	 pw.append("<tr>\r\n");
	        	 pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">西南</td>\r\n");
	        	 pw.append("</tr>\r\n");
	        	 
	        	 for(int i=0;i<cityList.size();i++){
	        		 if(cityList.get(i).getAreaType().equals("西南")){
	        			 String province=cityList.get(i).getProvince();
	        			 Double price=cityList.get(i).getWsy();
	        			 for(int j=0;j<ycityList.size();j++){
	        				 if(province.equals(ycityList.get(j).getProvince())){
	        					 Double yPrice=ycityList.get(j).getWsy();
	        					 Double y=price-yPrice;
	        					 for(int l=0;l<lcityList.size();l++)
	        						 if(province.equals(lcityList.get(l).getProvince())){
	        							 Double lPrice=lcityList.get(l).getWsy();
	        							 Double lw=price-lPrice;
	        							 
	        							 int a=i+1;
	        							 pw.append("<tr>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							 pw.append("</tr>\r\n");
	        							 
	        						 }
	        					 
	        				 }
	        			 }
	        		 }
	        	 }
	        	 for(int i=0;i<cityList.size();i++){
	     	    	if(cityList.get(i).getAreaType().equals("全国")){
	     	    			 String province=cityList.get(i).getProvince();
	     	    			 Double price=cityList.get(i).getWsy();
	     	    			 for(int j=0;j<ycityList.size();j++){
	     	    				 if(province.equals(ycityList.get(j).getProvince())){
	     	    					 Double yPrice=ycityList.get(j).getWsy();
	     	    					 Double y=price-yPrice;
	     	    					 for(int l=0;l<lcityList.size();l++)
	     	    					 if(province.equals(lcityList.get(l).getProvince())){
	     	    						 Double lPrice=lcityList.get(l).getWsy();
	     	    						 Double lw=price-lPrice;
	     	    			 
	        	 
	        	pw.append("<tr>\r\n");
	        	pw.append("<td style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">全国</td>\r\n");
	        	pw.append(" <td height=\"40px\" style=\"border:1px solid #e6e6e6; \"><s>&nbsp;</s></td>\r\n");
	        	pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \"><s>&nbsp;</s></td>\r\n");
	        	 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
				 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
				 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
				 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
		        						}
		        					
		        				}
		        			}
		        		}
		        	}
	        	pw.append("</tbody>\r\n");
	        	pw.append("</table>\r\n");
	        	pw.append("</div>\r\n");
	        	pw.append("</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	pw.append("</tbody>\r\n");
	        	pw.append("</table>\r\n");
	        	
	        	
//内三元	        	
	        	pw.append(" <table width=\"95%\" border=\"0\" align=\"center\" style=\"color:#000000;font-family:Simsun;\" class=\"ke-zeroborder\">\r\n");
	        	pw.append("<tbody>");
	        	pw.append("<tr>\r\n<td>\r\n<div style=\"font-family:宋体; color:#6D6D6D;\">\r\n");
	        	pw.append("<h2 style=\"font-family:\"Microsoft YaHei\"; color:#666666; \">"+createtime+"全国内三元价格排行榜</h2>\r\n");
	        	pw.append("<span style=\"line-height:30px;\">单位： （元/公斤）</span>\r\n");
	        	pw.append("<table width=\"100%\" border=\"0\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" style=\"border:none;text-align:center;color:#6D6D6D;font-size:14px;\" class=\"ke-zeroborder\">\r\n");
	        	pw.append("<tbody>\r\n");
	        	pw.append("<tr>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">区域分类</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">排名</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">省市</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">"+createtime+"</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">"+yesterday+"</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">较昨日涨跌</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">较上周涨跌</td>\r\n");
	        	pw.append(" </tr>\r\n");
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td style=\"background:#f4f4f4; border:none; border:1px solid #e6e6e6; \" rowspan=\"4\">东北</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	        	Collections.sort(cityList,new Comparator<Price>(){
	   			 @Override
				public int compare(Price o1, Price o2) {  
	   			        if(null!=o1&&null!=o2)  
	   			        {  
	   			        	double menu1=o1.getNsy();  
	   			        	double menu2=o2.getNsy();  
	   			            if(menu1>menu2){  
	   			                return 1;  
	   			            }else {  
	   			                return 0;  
	   			            }  
	   			        }  
	   			        return 0;  
	   			    }
	   		});
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("东北")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getNsy();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getNsy();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getNsy();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华北</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("华北")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getNsy();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getNsy();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getNsy();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"7\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华东</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("华东")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getNsy();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getNsy();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getNsy();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华中</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("华中")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getNsy();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getNsy();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getNsy();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"4\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华南</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("华南")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getNsy();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getNsy();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getNsy();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">西北</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("西北")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getNsy();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getNsy();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getNsy();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">西南</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("西南")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getNsy();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getNsy();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getNsy();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	for(int i=0;i<cityList.size();i++){
	     	    	if(cityList.get(i).getAreaType().equals("全国")){
	     	    			 String province=cityList.get(i).getProvince();
	     	    			 Double price=cityList.get(i).getNsy();
	     	    			 for(int j=0;j<ycityList.size();j++){
	     	    				 if(province.equals(ycityList.get(j).getProvince())){
	     	    					 Double yPrice=ycityList.get(j).getNsy();
	     	    					 Double y=price-yPrice;
	     	    					 for(int l=0;l<lcityList.size();l++)
	     	    					 if(province.equals(lcityList.get(l).getProvince())){
	     	    						 Double lPrice=lcityList.get(l).getNsy();
	     	    						 Double lw=price-lPrice;
	     	    			 
	        	pw.append("<tr>\r\n");
	        	pw.append("<td style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">全国</td>\r\n");
	        	pw.append(" <td height=\"40px\" style=\"border:1px solid #e6e6e6; \"><s>&nbsp;</s></td>\r\n");
	        	pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \"><s>&nbsp;</s></td>\r\n");
	        	 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
				 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
				 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
				 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
		        						}
		        					
		        				}
		        			}
		        		}
		        	}
	        	pw.append("</tbody>\r\n");
	        	pw.append("</table>\r\n");
	        	pw.append("</div>\r\n");
	        	pw.append("</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	pw.append("</tbody>\r\n");
	        	pw.append("</table>\r\n");
	        	
	        	
//土杂猪	        	
	        	pw.append(" <table width=\"95%\" border=\"0\" align=\"center\" style=\"color:#000000;font-family:Simsun;\" class=\"ke-zeroborder\">\r\n");
	        	pw.append("<tbody>");
	        	pw.append("<tr>\r\n<td>\r\n<div style=\"font-family:宋体; color:#6D6D6D;\">\r\n");
	        	pw.append("<h2 style=\"font-family:\"Microsoft YaHei\"; color:#666666; \">"+createtime+"全国土杂猪价格排行榜</h2>\r\n");
	        	pw.append("<span style=\"line-height:30px;\">单位： （元/公斤）</span>\r\n");
	        	pw.append("<table width=\"100%\" border=\"0\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" style=\"border:none;text-align:center;color:#6D6D6D;font-size:14px;\" class=\"ke-zeroborder\">\r\n");
	        	pw.append("<tbody>\r\n");
	        	pw.append("<tr>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">区域分类</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">排名</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">省市</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">"+createtime+"</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">"+yesterday+"</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">较昨日涨跌</td>\r\n");
	        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">较上周涨跌</td>\r\n");
	        	pw.append(" </tr>\r\n");
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"4\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">东北</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	Collections.sort(cityList,new Comparator<Price>(){
	    			@Override
					public int compare(Price o1, Price o2) {  
	    				if(null!=o1&&null!=o2)  
	    				{  
	    					double menu1=o1.getTzz();  
	    					double menu2=o2.getTzz();  
	    					if(menu1>menu2){  
	    						return 1;  
	    					}else {  
	    						return 0;  
	    					}  
	    				}  
	    				return 0;  
	    			}
	    		});
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("东北")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getTzz();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getTzz();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getTzz();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华北</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("华北")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getTzz();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getTzz();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getTzz();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"7\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华东</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("华东")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getTzz();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getTzz();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getTzz();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华中</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("华中")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getTzz();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getTzz();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getTzz();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"4\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华南</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("华南")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getTzz();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getTzz();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getTzz();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">西北</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("西北")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getTzz();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getTzz();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getTzz();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	
	        	
	        	pw.append("<tr>\r\n");
	        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">西南</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
	        	for(int i=0;i<cityList.size();i++){
	        		if(cityList.get(i).getAreaType().equals("西南")){
	        			String province=cityList.get(i).getProvince();
	        			Double price=cityList.get(i).getTzz();
	        			for(int j=0;j<ycityList.size();j++){
	        				if(province.equals(ycityList.get(j).getProvince())){
	        					Double yPrice=ycityList.get(j).getTzz();
	        					Double y=price-yPrice;
	        					for(int l=0;l<lcityList.size();l++)
	        						if(province.equals(lcityList.get(l).getProvince())){
	        							Double lPrice=lcityList.get(l).getTzz();
	        							Double lw=price-lPrice;
	        							
	        							int a=i+1;
	        							pw.append("<tr>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
	        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        							pw.append("</tr>\r\n");
	        							
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
	        	
	        	
	        	for(int i=0;i<cityList.size();i++){
	     	    	if(cityList.get(i).getAreaType().equals("全国")){
	     	    			 String province=cityList.get(i).getProvince();
	     	    			 Double price=cityList.get(i).getTzz();
	     	    			 for(int j=0;j<ycityList.size();j++){
	     	    				 if(province.equals(ycityList.get(j).getProvince())){
	     	    					 Double yPrice=ycityList.get(j).getTzz();
	     	    					 Double y=price-yPrice;
	     	    					 for(int l=0;l<lcityList.size();l++)
	     	    					 if(province.equals(lcityList.get(l).getProvince())){
	     	    						 Double lPrice=lcityList.get(l).getTzz();
	     	    						 Double lw=price-lPrice;
	     	    			 
	        	pw.append("<tr>\r\n");
	        	pw.append("<td style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">全国</td>\r\n");
	        	pw.append(" <td height=\"40px\" style=\"border:1px solid #e6e6e6; \"><s>&nbsp;</s></td>\r\n");
	        	pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \"><s>&nbsp;</s></td>\r\n");
	        	 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
				 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
				 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
				 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	
		        						}
		        					
		        				}
		        			}
		        		}
		        	}
	        	pw.append("</tbody>\r\n");
	        	pw.append("</table>\r\n");
	        	pw.append("</div>\r\n");
	        	pw.append("</td>\r\n");
	        	pw.append("</tr>\r\n");
	        	pw.append("</tbody>\r\n");
	        	pw.append("</table>\r\n");
	        	pw.append("</br>\r\n");
	        	pw.append("</div>\r\n");
	        	} 
	        	
		        catch (FileNotFoundException ex) {
		            ex.printStackTrace();
		        } 
		        catch (IOException ex) {
		            ex.printStackTrace();
		        } 
		        finally {
		            //Close the BufferedWriter
		            try {
		                if (bufferedWriter != null) {
		                    bufferedWriter.flush();
		                    bufferedWriter.close();
		                }
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
		     
		        }
	}
	
	public void writeToYDFile(List<Price> cityList,List<Price> ycityList,List<Price> lcityList,String id){
        BufferedWriter bufferedWriter = null;
      String filename=ServletActionContext.getServletContext().getRealPath("/jsp/notices");

      
        try {
        	bufferedWriter = new BufferedWriter(new FileWriter(filename+"/"+id+".jsp"));
        	PrintWriter pw = new PrintWriter(bufferedWriter);
        	
        	pw.append("<%@ page contentType=\"text/html; charset=UTF-8\"%>\r\n");
        	pw.append("<div>");
//玉米
        	pw.append(" <table width=\"95%\" border=\"0\" align=\"center\" style=\"color:#000000;font-family:Simsun;\" class=\"ke-zeroborder\">\r\n");
        	pw.append("<tbody>");
        	pw.append("<tr>\r\n<td>\r\n<div style=\"font-family:宋体; color:#6D6D6D;\">\r\n");
        	pw.append("<h2 style=\"font-family:\"Microsoft YaHei\"; color:#666666; \">"+createtime+"全国玉米价格排行榜</h2>\r\n");
        	pw.append("<span style=\"line-height:30px;\">单位： （）</span>\r\n");
        	pw.append("<table width=\"100%\" border=\"0\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" style=\"border:none;text-align:center;color:#6D6D6D;font-size:14px;\" class=\"ke-zeroborder\">\r\n");
        	pw.append("<tbody>\r\n");
        	pw.append("<tr>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">区域分类</td>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">排名</td>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">省市</td>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">"+createtime+"</td>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">"+yesterday+"</td>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">较昨日涨跌</td>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">较上周涨跌</td>\r\n");
        	pw.append(" </tr>\r\n");
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"4\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">东北</td>\r\n");
        	pw.append("</tr>\r\n");
        	Collections.sort(cityList,new Comparator<Price>(){
    			@Override
				public int compare(Price o1, Price o2) {  
    				if(null!=o1&&null!=o2)  
    				{  
    					double menu1=o1.getYm();  
    					double menu2=o2.getYm();  
    					if(menu1>menu2){  
    						return 1;  
    					}else {  
    						return 0;  
    					}  
    				}  
    				return 0;  
    			}
    		});
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("东北")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getYm();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getYm();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getYm();        							Double lw=price-lPrice;
        							
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华北</td>\r\n");
        	pw.append("</tr>\r\n");
        	
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("华北")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getYm();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getYm();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getYm();
        							Double lw=price-lPrice;
        							
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"7\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华东</td>\r\n");
        	pw.append("</tr>\r\n");
        	
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("华东")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getYm();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getYm();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getYm();
        							Double lw=price-lPrice;
        							
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华中</td>\r\n");
        	pw.append("</tr>\r\n");
        	
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("华中")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getYm();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getYm();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getYm();
        							Double lw=price-lPrice;
        							
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"4\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华南</td>\r\n");
        	pw.append("</tr>\r\n");
        	
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("华南")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getYm();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getYm();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getYm();
        							Double lw=price-lPrice;
        							
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">西北</td>\r\n");
        	pw.append("</tr>\r\n");
        	
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("西北")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getYm();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getYm();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getYm();
        							Double lw=price-lPrice;
        							
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">西南</td>\r\n");
        	pw.append("</tr>\r\n");
        	
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("西南")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getYm();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getYm();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getYm();
        							Double lw=price-lPrice;
        							
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	for(int i=0;i<cityList.size();i++){
    	    	if(cityList.get(i).getAreaType().equals("全国")){
    	    			 String province=cityList.get(i).getProvince();
    	    			 Double price=cityList.get(i).getYm();
    	    			 for(int j=0;j<ycityList.size();j++){
    	    				 if(province.equals(ycityList.get(j).getProvince())){
    	    					 Double yPrice=ycityList.get(j).getYm();
    	    					 Double y=price-yPrice;
    	    					 for(int l=0;l<lcityList.size();l++)
    	    					 if(province.equals(lcityList.get(l).getProvince())){
    	    						 Double lPrice=lcityList.get(l).getYm();
    	    						 Double lw=price-lPrice;
    	    			 
       	pw.append("<tr>\r\n");
       	pw.append("<td style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">全国</td>\r\n");
       	pw.append(" <td height=\"40px\" style=\"border:1px solid #e6e6e6; \"><s>&nbsp;</s></td>\r\n");
       	pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \"><s>&nbsp;</s></td>\r\n");
        pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
		 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
		 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
		 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
       	pw.append("</tr>\r\n");
       	
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
        	pw.append("</tbody>\r\n");
        	pw.append("</table>\r\n");
        	pw.append("</div>\r\n");
        	pw.append("</td>\r\n");
        	pw.append("</tr>\r\n");
        	pw.append("</tbody>\r\n");
        	pw.append("</table>\r\n");
        	
 
//豆粕        	
        	pw.append(" <table width=\"95%\" border=\"0\" align=\"center\" style=\"color:#000000;font-family:Simsun;\" class=\"ke-zeroborder\">\r\n");
        	pw.append("<tbody>");
        	pw.append("<tr>\r\n<td>\r\n<div style=\"font-family:宋体; color:#6D6D6D;\">\r\n");
        	pw.append("<h2 style=\"font-family:\"Microsoft YaHei\"; color:#666666; \">"+createtime+"全国豆粕价格排行榜</h2>\r\n");
        	pw.append("<span style=\"line-height:30px;\">单位： （）</span>\r\n");
        	pw.append("<table width=\"100%\" border=\"0\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" style=\"border:none;text-align:center;color:#6D6D6D;font-size:14px;\" class=\"ke-zeroborder\">\r\n");
        	pw.append("<tbody>\r\n");
        	pw.append("<tr>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">区域分类</td>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">排名</td>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">省市</td>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">"+createtime+"</td>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">"+yesterday+"</td>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">较昨日涨跌</td>\r\n");
        	pw.append(" <td width=\"10%\" height=\"40px\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">较上周涨跌</td>\r\n");
        	pw.append(" </tr>\r\n");
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"4\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">东北</td>\r\n");
        	pw.append("</tr>\r\n");
        	Collections.sort(cityList,new Comparator<Price>(){
    			@Override
				public int compare(Price o1, Price o2) {  
    				if(null!=o1&&null!=o2)  
    				{  
    					double menu1=o1.getDp();  
    					double menu2=o2.getDp();  
    					if(menu1>menu2){  
    						return 1;  
    					}else {  
    						return 0;  
    					}  
    				}  
    				return 0;  
    			}
    		});
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("东北")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getDp();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getDp();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getDp();
        							Double lw=price-lPrice;
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华北</td>\r\n");
        	pw.append("</tr>\r\n");
        	
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("华北")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getDp();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getDp();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getDp();
        							Double lw=price-lPrice;
        							
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"7\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华东</td>\r\n");
        	pw.append("</tr>\r\n");
        	
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("华东")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getDp();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getDp();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getDp();
        							Double lw=price-lPrice;
        							
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华中</td>\r\n");
        	pw.append("</tr>\r\n");
        	
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("华中")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getDp();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getDp();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getDp();
        							Double lw=price-lPrice;
        							
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"4\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">华南</td>\r\n");
        	pw.append("</tr>\r\n");
        	
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("华南")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getDp();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getDp();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getDp();
        							Double lw=price-lPrice;
        							
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">西北</td>\r\n");
        	pw.append("</tr>\r\n");
        	
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("西北")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getDp();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getDp();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getDp();
        							Double lw=price-lPrice;
        							
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	
        	
        	pw.append("<tr>\r\n");
        	pw.append("<td rowspan=\"5\" style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">西南</td>\r\n");
        	pw.append("</tr>\r\n");
        	
        	for(int i=0;i<cityList.size();i++){
        		if(cityList.get(i).getAreaType().equals("西南")){
        			String province=cityList.get(i).getProvince();
        			Double price=cityList.get(i).getDp();
        			for(int j=0;j<ycityList.size();j++){
        				if(province.equals(ycityList.get(j).getProvince())){
        					Double yPrice=ycityList.get(j).getDp();
        					Double y=price-yPrice;
        					for(int l=0;l<lcityList.size();l++)
        						if(province.equals(lcityList.get(l).getProvince())){
        							Double lPrice=lcityList.get(l).getDp();
        							Double lw=price-lPrice;
        							
        							int a=i+1;
        							pw.append("<tr>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+a+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+province+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
        							pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
        							pw.append("</tr>\r\n");
        							
        						}
        					
        				}
        			}
        		}
        	}
        	
        	
        	for(int i=0;i<cityList.size();i++){
    	    	if(cityList.get(i).getAreaType().equals("全国")){
    	    			 String province=cityList.get(i).getProvince();
    	    			 Double price=cityList.get(i).getDp();
    	    			 for(int j=0;j<ycityList.size();j++){
    	    				 if(province.equals(ycityList.get(j).getProvince())){
    	    					 Double yPrice=ycityList.get(j).getDp();
    	    					 Double y=price-yPrice;
    	    					 for(int l=0;l<lcityList.size();l++)
    	    					 if(province.equals(lcityList.get(l).getProvince())){
    	    						 Double lPrice=lcityList.get(l).getDp();
    	    						 Double lw=price-lPrice;
    	    			 
       	pw.append("<tr>\r\n");
       	pw.append("<td style=\"border:1px solid #E6E6E6;background:#F4F4F4;\">全国</td>\r\n");
       	pw.append(" <td height=\"40px\" style=\"border:1px solid #e6e6e6; \"><s>&nbsp;</s></td>\r\n");
       	pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \"><s>&nbsp;</s></td>\r\n");
        pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+price+"</td>\r\n");
		 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+yPrice+"</td>\r\n");
		 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(y)+"</td>\r\n");
		 pw.append("<td height=\"40px\" style=\"border:1px solid #e6e6e6; \">"+df.format(lw)+"</td>\r\n");
       	pw.append("</tr>\r\n");
       	
	        						}
	        					
	        				}
	        			}
	        		}
	        	}
        	pw.append("</tbody>\r\n");
        	pw.append("</table>\r\n");
        	pw.append("</div>\r\n");
        	pw.append("</td>\r\n");
        	pw.append("</tr>\r\n");
        	pw.append("</tbody>\r\n");
        	pw.append("</table>\r\n");
        	pw.append("</br>\r\n");
        	pw.append("</div>\r\n");
        	
        	
        	
        } 
    	
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        } 
        finally {
            //Close the BufferedWriter
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
     
        }
}
}

