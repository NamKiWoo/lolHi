package com.sbs.example.lolHi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sbs.example.lolHi.dao.MemberDao;
import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.dto.ResultData;
import com.sbs.example.lolHi.util.Util;

@Service
public class MemberService {

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MailService mailService;
	@Value("${custom.siteMainUri}")
	private String siteMainUri;
	@Value("${custom.siteName}")
	private String siteName;
	@Value("${custom.siteUri}")
	private String siteUri;
	@Value("${custom.siteLoginUri}")
	private String siteLoginUri;
	@Autowired
	private AttrService attrService;
	

	public int join(Map<String, Object> param) {
		// TODO Auto-generated method stub
		memberDao.join(param);
		int id = Util.getAsInt(param.get("id"));
		
		String authCode = genEmailAuthCode(id);
		
		sendJoinCompleteMail(id, (String)param.get("email"), authCode);
		
		return id;
		
	}
	
	private String genEmailAuthCode(int id) {
		
		String authCode = UUID.randomUUID().toString();
		attrService.setValue("member__" + id + "__extra__emailAuthCode", authCode);

		return authCode;
	}

	private void sendJoinCompleteMail(int id, String email, String authCode) {
		String mailTitle = String.format("[%s] 가입이 완료되었습니다. 이메일 인증을 진행해주세요.", siteName);

		StringBuilder mailBodySb = new StringBuilder();
		mailBodySb.append("<h1>가입이 완료되었습니다.</h1>");
		mailBodySb.append("<div>아래 인증코드를 클릭하여 이메일 인증을 마무리해주세요.</div>");
		
		String doAuthEmailUrl = siteUri + "/usr/member/doAuthEmail?authCode=" + authCode + "&email=" + email + "&id=" + id;
		mailBodySb.append(String.format("<p><a href=\"%s\" target=\"_blank\">인증하기</a>로 이동</p>", doAuthEmailUrl, siteName));

		mailService.send(email, mailTitle, mailBodySb.toString());
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

	public  Member getMemberById(int id) {
		// TODO Auto-generated method stub
		
		return memberDao.getMemberById(id);
	}

	public void modify(Map<String, Object> param) {
		// TODO Auto-generated method stub
		memberDao.modify(param);
	}

	public boolean isJoinAvailableNameAndEmail(String name, String email) {
		
		if (name == null || name.length() == 0) {
			return false;
		}
		if (email == null || email.length() == 0) {
			return false;
		}
		
		Member member = memberDao.getMemberByNameAndEmail(name, email);
			
		return member == null;
	}

	public Member getMemberByNameAndEmail(String name, String email) {

		return memberDao.getMemberByNameAndEmail(name, email);
	}

	public ResultData setTempPasswordAndNotify(Member member) {
				
		Random r = new Random();
		
		String tempLoginPw = (10000 + r.nextInt(90000)) + "";
		
		String mailTitle = String.format("[%s] 임시 비밀번호가 발송되었습니다.", siteName);
		String mailBody = ""; 
				
		mailBody += String.format("로그인아이디 : %s<br>", member.getLoginId());
		mailBody += String.format("임시 비밀번호 : %s",tempLoginPw);
		mailBody += "<br>";
		mailBody += String.format("<a href=\"%s\" target=\"_blank\">로그인 하러가기</a>",siteLoginUri);

		ResultData sendResultData = mailService.send(member.getEmail(), mailTitle, mailBody);
		
		if (sendResultData.isFail()) {
			return new ResultData("F-1", "메일발송에 실패했습니다");			
		}
		
		Map<String, Object> modifyParam = new HashMap<>();
		modifyParam.put("loginPw", Util.sha256(tempLoginPw));
		modifyParam.put("id", member.getId());
		memberDao.modify(modifyParam);
		
		return new ResultData("S-1", "임시 패스워드를 메일로 발송하셨습니다.");
	}

	public String genCheckLoginPwAuthCode(int id) {
		
		String authCode = UUID.randomUUID().toString();
		attrService.setValue("member__" + id + "__extra__modifyPrivateAuthCode", authCode, Util.getDateStrLater(60 * 60));

		return authCode;
	}

	public ResultData checkValidCheckLoginPwAuthCode(int loginedMemberId, String checkPasswordAuthCode) {
		
		if (attrService.getValue("member__" + loginedMemberId + "__extra__modifyPrivateAuthCode").equals(checkPasswordAuthCode)) {
			return new ResultData("S-1", "유효한 키 입니다.");
		}

		return new ResultData("F-1", "유효하지 않은 키 입니다.");
	}

	public String getEmailAuthCode(int id) {
		
		return attrService.getValue("member__" + id + "__extra__emailAuthCode");	 
	}

	public void saveAuthedEmail(int id, String email) {
		
		attrService.setValue("member__" + id + "__extra__authedEmail", email);
		
	}

	public String getAuthedEmail(int id) {

		return attrService.getValue("member__" + id + "__extra__authedEmail");
	}
	

}
