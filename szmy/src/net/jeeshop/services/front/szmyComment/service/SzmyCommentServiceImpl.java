package net.jeeshop.services.front.szmyComment.service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.szmyComment.dao.SzmyCommentDao;
import net.jeeshop.services.front.szmyComment.service.SzmyCommentService;
import net.jeeshop.services.common.SzmyComment;


public class SzmyCommentServiceImpl extends ServersManager <SzmyComment> implements SzmyCommentService {
	private SzmyCommentDao szmyCommentDao;

	public void setSzmyCommentDao(SzmyCommentDao szmyCommentDao) { 
		this.szmyCommentDao=szmyCommentDao;
	}
}
