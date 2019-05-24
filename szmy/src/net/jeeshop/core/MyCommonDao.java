package net.jeeshop.core;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class MyCommonDao extends SqlSessionDaoSupport {
	/**
	 * teng 
	 * @param type 1为查询 	2为更新		3为插入
	 * @param str	原生sql
	 * @return
	 */
	 public List<Object> executeSql(int type,String str){
		 SqlSession session = getSqlSession();
		 List<Object> o2=null; 
		  try {
			  if(type==1)  o2 = session.selectList("common.selectList",str);
			  else if(type==2) session.update("common.update",str);
			  else if(type==3)  session.insert("common.insert",str);
			  session.commit();
		  } catch (Exception e) {
			  throw new RuntimeException(e);
		  }finally{
			  session.close();
		  }
		  return o2;
	 }
}
