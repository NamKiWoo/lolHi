package com.sbs.example.lolHi.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.example.lolHi.dao.MemberDao;
import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.util.Util;

@Service
public class MemberService {

	@Autowired
	private MemberDao memberDao;

	public int join(Map<String, Object> param) {
		// TODO Auto-generated method stub
		memberDao.join(param);
		int id = Util.getAsInt(param.get("id"));
		
		return id;
		
	}

	public boolean isJoinAvailableLoginId(String loginId) {
		// TODO Auto-generated method stub
		Member member = memberDao.getMemberByLoginId(loginId);
		
		if(member == null) {
			return true;
		}
		
		return false;
	}

	public Member getMemberByLoginId(String loginId) {
		// TODO Auto-generated method stub
		return memberDao.getMemberByLoginId(loginId);
	}

}
