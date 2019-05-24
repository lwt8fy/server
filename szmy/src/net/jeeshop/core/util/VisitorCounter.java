package net.jeeshop.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
public class VisitorCounter extends SqlSessionDaoSupport { 
	private final static String TABLE_NAME = "visitorcounter";
	 private static String today = null;
	 private static long today_num = 0;
	 private static long total_num = 0;
	 //加载今日访问量
	 private  void loadToadyNum() {
	  // TODO Auto-generated method stub
		 SqlSession session = getSqlSession();
	   today = getTodayDate();
	  try {
		  Object num = session.selectOne("common.selectVisitorcounterOne","select vnum from "+TABLE_NAME+" where id=1");
		  today_num =(Long)num;
		  
	   num = session.selectOne("common.selectVisitorcounterOne","select vnum from "+TABLE_NAME+" where vdate='"+today+"'");
	   if(num!=null){
	    today_num =(Long)num;
	   }
	   else
	   {    
		   today_num = 1;
		   session.insert("common.insert","insert into "+TABLE_NAME+"(vdate,vnum) values('"+today+"',1)");
		   session.commit();
	   }
	  } catch (Exception e) {
		  session.rollback();
	   System.out.println("VisitorCounter.incTotalCounter:获得访问人数");
	  }finally{
		  session.close();
	  }
	 }
	 //加载总访问量
	 private  void loadTotalNum() {
		 SqlSession session = getSqlSession();

	  try {
	  Object num = session.selectOne("common.selectVisitorcounterOne","select vnum from "+TABLE_NAME+" where id=1");
	  total_num =(Long)num;
	  } catch (Exception e) {
	   System.out.println("VisitorCounter.incTotalCounter:获得访问人数");
	  }finally{
		  session.close();
	  }
	 }
	
	 //增加访问量
	 public  void addNum(){
		 SqlSession session = getSqlSession();
	  try {
		  if(today==null){
			  today = getTodayDate();
			  Object num = session.selectOne("common.selectVisitorcounterOne","select vnum from "+TABLE_NAME+" where vdate='"+today+"'");
			   if(num!=null){
			    today_num =(Long)num;
			  }else{
				  today_num=1;
				  session.insert("common.insert","insert into "+TABLE_NAME+"(vdate,vnum) values('"+today+"',1)");
			  }
			   //////////////////////////////
			   Object num2 = session.selectOne("common.selectVisitorcounterOne","select vnum from "+TABLE_NAME+" where id=1");
			  total_num =(Long)num2;
			   
		  }else if(today.equals(getTodayDate())){
			  today_num += 1;
			  session.update("common.update","update "+TABLE_NAME+" set vnum="+today_num+" where vdate='"+today+"'");
		  }else{
			  today=getTodayDate();
			  today_num=1;
			  session.insert("common.insert","insert into "+TABLE_NAME+"(vdate,vnum) values('"+today+"',1)");
		  }
		  
		  total_num = total_num+1;
		  session.update("common.update","update "+TABLE_NAME+" set vnum="+total_num+" where id=1");
	  } catch (Exception e) {
	   // TODO: handle exception
		  session.rollback();
	   System.out.println("VisitorCounter.addNum:增加访问人数");
	  }finally{
		  session.close();
	  }
	 }
	 //获得今天的日期
	 private  String getTodayDate(){
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  return sdf.format(new Date());
	 }
	 ///获得今日访问量
	 public  long getTodayNum(){
	  if(today_num<1)loadToadyNum();
	  return today_num;
	 }
	 //获得总的访问量
	 public  long getTotalNum(){
		 
	  if(total_num<1) loadTotalNum();
	  
	  return total_num;
	 } 
	 
	 public List<Object> runStr(int type,String str){
		 SqlSession session = getSqlSession();
		 List<Object> o=null;
		  try {
			  if(type==1)  o = session.selectList("common.selectList",str);
			  else if(type==2) session.update("common.update",str);
			  else if(type==3)  session.insert("common.insert",str);
			  session.commit();
		  } catch (Exception e) {
			  throw new RuntimeException(e);
		  }finally{
			  session.close();
		  }
		  return o;
	 }
public static void main(String[] args) {
	VisitorCounter v=new VisitorCounter();
	v.addNum();
}
}
