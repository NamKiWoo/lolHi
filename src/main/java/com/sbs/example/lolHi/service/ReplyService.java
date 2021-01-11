package com.sbs.example.lolHi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.example.lolHi.dao.ReplyDao;
import com.sbs.example.lolHi.dto.Reply;
import com.sbs.example.lolHi.util.Util;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyDao replyDao;

	public int write(Map<String, Object> param) {
		// TODO Auto-generated method stub
		replyDao.write(param);
		
		int id = Util.getAsInt(param.get("id"));
		
		return id;
	}

	public List<Reply> getForPrintReplies(String relTypeCode, int relId) {
		// TODO Auto-generated method stub
		return replyDao.getForPrintReplies(relTypeCode, relId);
	}

	public Reply getReply(int id) {
		return replyDao.getReply(id);
	}

	public void deleteReplyById(int id) {
		replyDao.deleteReplyById(id);		
	}

	public void modify(Map<String, Object> param) {
		replyDao.modify(param);
		
	}

}
