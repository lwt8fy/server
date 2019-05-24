package net.jeeshop.chat.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.jeeshop.chat.beans.ChatEntity;
import net.jeeshop.chat.commons.GlobalMsgTypes;

public class DBTempSaveUtil {

	public static void saveUnsentChatMsg(int senderId, int receiverId, ChatEntity ent0) {
		insertItem(senderId, receiverId, ent0.toString(), GlobalMsgTypes.msgFromFriend);
	}

	public static ArrayList<ChatEntity> getUnsentChatMsg(int receiverId) {

		ArrayList<String> strList0 = readItems(receiverId, GlobalMsgTypes.msgFromFriend);

		ArrayList<ChatEntity> outList = new ArrayList<ChatEntity>();
		for(String str0 : strList0) {
			outList.add(ChatEntity.Str2Ent(str0));
		}
	
		return outList;

	}


	// ok, so here requeter is sender and requestee is receiver
	public static void saveUnsentFrdReqs(int requesterId, int requesteeId, String requestStr) {

		insertItem(requesterId, requesteeId, requestStr, GlobalMsgTypes.msgFriendshipRequest);

	}

	public static ArrayList<String> getUnsentFrdReqs(int requesteeId) {

		return readItems(requesteeId, GlobalMsgTypes.msgFriendshipRequest);

	}


	public static void saveUnsentFrdReqResponse(int requesterId, int requesteeId, String requestStr) {

		insertItem(requesteeId, requesterId, requestStr, GlobalMsgTypes.msgFriendshipRequestResponse);

	}

	public static ArrayList<String> getUnsentFrdReqResponse(int requesterId) {

		return readItems(requesterId, GlobalMsgTypes.msgFriendshipRequestResponse);

	}


	/********************************************************************************************************************************/
	private static void insertItem(int senderId, int receiverId, String str0, int type0) {
		Connection con = DBCon.getConnect();
		try {
			con.setAutoCommit(false);
		} catch(Exception e) {
			e.printStackTrace();
		}

		String sql0 = "use my_IM_GGMM";
		String sql1 = "insert into unSendMsgs (senderId,receiverId,msg,_datetime,type)" + 
						" values(?,?,?,now(),?)";
		
		try {
			PreparedStatement ps;

			ps = con.prepareStatement(sql0);
			ps.execute();

			ps = con.prepareStatement(sql1);
			ps.setInt(1, senderId);
			ps.setInt(2, receiverId);
			ps.setString(3, str0);
			ps.setInt(4, type0);
			int res = ps.executeUpdate();

			con.commit();
		} catch(Exception e) {
			try {
				con.rollback();
			} catch(Exception ee) {
				ee.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static ArrayList<String> readItems(int receiverId, int type0) {
		Connection con = DBCon.getConnect();
		try {
			con.setAutoCommit(false);
		} catch(Exception e) {
			e.printStackTrace();
		}

		String sql0 = "use my_IM_GGMM";
		String sql1 = "select * from unSendMsgs where receiverId=? and type=? order by _datetime asc";
		String sql2 = "delete from unSendMsgs where receiverId=? and type=?";

		ArrayList<String> outList = new ArrayList<String>();

		try {
			PreparedStatement ps;
			ResultSet rs;

			ps = con.prepareStatement(sql0);
			ps.execute();

			ps = con.prepareStatement(sql1);
			ps.setInt(1, receiverId);
			ps.setInt(2, type0);
			rs = ps.executeQuery();
			if(rs.first()) {
				do {
					String str0 = rs.getString("msg");
					outList.add(str0);
				} while (rs.next());
			}

			ps = con.prepareStatement(sql2);
			ps.setInt(1, receiverId);
			ps.setInt(2, type0);
			int res = ps.executeUpdate();

			con.commit();
		} catch(Exception e) {
			try {
				con.rollback();
			} catch(Exception ee) {
				ee.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return outList;
	}

}
