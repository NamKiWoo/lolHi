package com.sbs.example.lolHi.controller.usr;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.dto.ResultData;
import com.sbs.example.lolHi.service.MemberService;
import com.sbs.example.lolHi.util.Util;

@Controller
public class MemberController {
	
	@Autowired	
	private MemberService memberService;
	
	@RequestMapping("/usr/member/checkLoginPw")
	public String showCheckLoginPw() {
		
		return "usr/member/checkLoginPw";
	}
	
	@RequestMapping("/usr/member/doCheckLoginPw")
	public String doCheckLoginPw(HttpServletRequest req, Model model, String loginPw, String redirectUrl) {
				
		Member loginedMember = (Member) req.getAttribute("loginedMember");

		if (loginedMember.getLoginPw().equals(loginPw) == false) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
			return "common/redirect";
		}

		String authCode = memberService.genCheckLoginPwAuthCode(loginedMember.getId());

		if (redirectUrl == null || redirectUrl.length() == 0) {
			redirectUrl = "/usr/home/main";
		}

		redirectUrl = Util.getNewUri(redirectUrl, "checkLoginPwAuthCode", authCode);

		model.addAttribute("redirectUrl", redirectUrl);

		return "common/redirect";
	}

	@RequestMapping("/usr/member/findLoginId") 
	public String showFindLoginId() {
				
		return "usr/member/findLoginId";
	}
	
	@RequestMapping("/usr/member/doFindLoginId") 
	public String doFindLoginId(Model model, String name, String email) {
		Member member = memberService.getMemberByNameAndEmail(name, email);
		
		if (member == null) {
			model.addAttribute("msg", String.format("해당 회원은 존재하지 않습니다."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		model.addAttribute("msg", String.format("가입날짜 : %s, 로그인 아이디 : %s",member.getRegDate(),  member.getLoginId()));
		model.addAttribute("historyBack", true);
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/findLoginPw") 
	public String showFindLoginPw() {
				
		return "usr/member/findLoginPw";
	}
	
	@RequestMapping("/usr/member/doFindLoginPw") 
	public String doFindLoginPw(Model model, String loginId, String email) {
		Member member = memberService.getMemberByLoginId(loginId);
		
		if (member == null) {
			model.addAttribute("msg", String.format("해당 회원은 존재하지 않습니다."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		if (member.getEmail().equals(email) ==false) {
			model.addAttribute("msg", String.format("해당 회원은 존재하지 않습니다."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		ResultData setTempPasswordAndNotifyRsData = memberService.setTempPasswordAndNotify(member);
		
		model.addAttribute("msg", String.format(setTempPasswordAndNotifyRsData.getMsg()));
		model.addAttribute("historyBack", true);
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/login") 
	public String showLogin() {
		return "usr/member/login";
	}
	
	@RequestMapping("/usr/member/doLogin")	
	public String doLogin(String loginId, String loginPw, HttpSession session, Model model) {
					
		if(loginId.length() == 0) {
			//return String.format("<script> alert('로그인 아이디를 입력해주세요.'); history.back(); </script>");
			model.addAttribute("msg", String.format("로그인 아이디를 입력해주세요."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}		
		
		if(loginPw.length() == 0) {
			//return String.format("<script> alert('로그인 비밀번호를 입력해주세요.'); history.back(); </script>");
			model.addAttribute("msg", String.format("로그인 비밀번호를 입력해주세요."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
//		Map<String, Object> param = new HashMap<>();
//		param.put("userId", loginId);
//		param.put("userPw", loginPw);
//		
//		SqlInjection sqlInjection = new SqlInjection();
//		sqlInjection.initialize();
//		param = sqlInjection.replaceStr(param);
//		System.out.print("param : " + param);
		
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if (member == null) {
			//return String.format("<script> alert('%s(은)는 존재하지 않는 아이디입니다.'); history.back(); </script>",loginId);
			model.addAttribute("msg", String.format("%s(은)는 존재하지 않는 아이디입니다.", loginId));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		if (member.getLoginPw().equals(loginPw) == false) {
			//return String.format("<script> alert('비밀번호를 정확히 입력해주세요'); history.back(); </script>");
			model.addAttribute("msg", String.format("비밀번호를 정확히 입력해주세요"));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		session.setAttribute("loginedMemberId", member.getId());
		
		model.addAttribute("msg", String.format("%s님 환영합니다.", member.getName()));
		model.addAttribute("redirectUrl", String.format("/usr/article-free/list"));
		return "common/redirect";
		
		//return String.format("<script> alert('%s님 환영합니다.'); location.replace('/usr/article-free/list'); </script>",member.getName());
	}
	
	@RequestMapping("/usr/member/doLogout")
	public String doLogout(HttpSession session, Model model) {
										
		session.removeAttribute("loginedMemberId");
		
		//return String.format("<script> location.replace('/usr/article-free/list'); </script>");
		
		model.addAttribute("redirectUrl", "/usr/article-free/list");
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/join") 
	public String showJoin() {
		return "usr/member/join";
	}
	
	@RequestMapping("/usr/member/doJoin")	
	public String doJoin(@RequestParam Map<String, Object> param, Model model) {
		String loginId = Util.getAsStr(param.get("loginId"), "");
		String loginPw = Util.getAsStr(param.get("loginPw"), "");
		String loginPwConfirm = Util.getAsStr(param.get("loginPwConfirm"), "");
		String name = Util.getAsStr(param.get("name"), "");
		String email = Util.getAsStr(param.get("email"), "");
		
		if(loginId.length() == 0) {
			//return String.format("<script> alert('로그인 아이디를 입력해주세요.'); history.back(); </script>");
			model.addAttribute("msg", String.format("로그인 아이디를 입력해주세요."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
				
		if(loginPw.length() == 0) {
			//return String.format("<script> alert('로그인 비밀번호를 입력해주세요.'); history.back(); </script>");
			model.addAttribute("msg", String.format("로그인 비밀번호를 입력해주세요."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
				
		
		if(loginPwConfirm.length() == 0) {
			//return String.format("<script> alert('로그인 비밀번호를 입력해주세요.'); history.back(); </script>");
			model.addAttribute("msg", String.format("로그인 비밀번호를 입력해주세요."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		if(!loginPw.equals(loginPwConfirm)) {
			//return String.format("<script> alert('입력한 비밀번호가 다릅니다. 비밀번호를 확인해주세요.'); history.back(); </script>");
			model.addAttribute("msg", String.format("입력한 비밀번호가 다릅니다. 비밀번호를 확인해주세요."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
				
		if(name.length() == 0) {
			//return String.format("<script> alert('이름을 입력해주세요.'); history.back(); </script>");
			model.addAttribute("msg", String.format("이름을 입력해주세요."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		boolean isJoinAvailableLoginId = memberService.isJoinAvailableLoginId(loginId);
		if(isJoinAvailableLoginId == false) {
			//return String.format("<script> alert('%s(은)는 이미 사용중인 아이디입니다.'); history.back(); </script>",loginId);
			model.addAttribute("msg", String.format("%s(은)는 이미 사용중인 아이디입니다.",loginId));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
						
		boolean isJoinAvailableNameAndEmail = memberService.isJoinAvailableNameAndEmail(name, email);
		if(isJoinAvailableNameAndEmail == false) {
			//return String.format("<script> alert('%s(은)는 이미 사용중인 아이디입니다.'); history.back(); </script>",loginId);
			model.addAttribute("msg", String.format("이미 가입된 회원의 정보입니다."));
			model.addAttribute("redirectUrl", "/usr/member/findLoginId");
			return "common/redirect";
		}
		
		memberService.join(param);
		
		//return String.format("<script>alert('%d번 회원이 생성되었습니다.'); location.replace('/usr/article-free/list') </script>",id);
		model.addAttribute("msg", String.format("가입되었습니다."));
		model.addAttribute("redirectUrl", "/usr/article-free/list");
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/modify")
	public String showModify(Model model, HttpServletRequest req, String checkLoginPwAuthCode) {
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		ResultData checkValidCheckloginPwAuthCodeResultData = memberService
				.checkValidCheckLoginPwAuthCode(loginedMemberId, checkLoginPwAuthCode);

		if (checkLoginPwAuthCode == null || checkLoginPwAuthCode.length() == 0) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "비밀번호 체크 인증코드가 없습니다.");
			return "common/redirect";
		}

		if (checkValidCheckloginPwAuthCodeResultData.isFail()) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", checkValidCheckloginPwAuthCodeResultData.getMsg());
			return "common/redirect";
		}
		return "/usr/member/modify";
	}
	
	@RequestMapping("/usr/member/doModify")
	public String doModify(Model model, HttpServletRequest req, @RequestParam Map<String, Object> param) {
		
		int loginedMemberId = (int)req.getAttribute("loginedMemberId");
		param.put("id", loginedMemberId);
		
		//해킹방지
		param.remove("loginId");
		param.remove("loginPw");
		
		memberService.modify(param);
		
		model.addAttribute("msg", String.format("수정되었습니다."));
		model.addAttribute("redirectUrl", "/usr/article-free/list");
		
		return "common/redirect";
	}
}
