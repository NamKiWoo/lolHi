package com.sbs.example.lolHi.controller.usr;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.service.MemberService;
import com.sbs.example.lolHi.util.SqlInjection;
import com.sbs.example.lolHi.util.Util;

@Controller
public class MemberController {
	
	@Autowired	
	private MemberService memberService;

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
		
		Map<String, Object> param = new HashMap<>();
		param.put("userId", loginId);
		param.put("userPw", loginPw);
		
		SqlInjection sqlInjection = new SqlInjection();
		sqlInjection.initialize();
		param = sqlInjection.replaceStr(param);
		System.out.print("param : " + param);
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
		model.addAttribute("replaceUri", String.format("/usr/article-free/list"));
		return "common/redirect";
		
		//return String.format("<script> alert('%s님 환영합니다.'); location.replace('/usr/article-free/list'); </script>",member.getName());
	}
	
	@RequestMapping("/usr/member/doLogout")
	public String doLogout(HttpSession session, Model model) {
										
		session.removeAttribute("loginedMemberId");
		
		//return String.format("<script> location.replace('/usr/article-free/list'); </script>");
		
		model.addAttribute("replaceUri", "/usr/article-free/list");
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/join") 
	public String showJoin() {
		return "usr/member/join";
	}
	
	@RequestMapping("/usr/member/doJoin")	
	public String doJoin(@RequestParam Map<String, Object> param, Model model) {
		String loginId = Util.getAsStr(param.get("loginId"), "");
		
		if(loginId.length() == 0) {
			//return String.format("<script> alert('로그인 아이디를 입력해주세요.'); history.back(); </script>");
			model.addAttribute("msg", String.format("로그인 아이디를 입력해주세요."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		String loginPw = Util.getAsStr(param.get("loginPw"), "");
		
		if(loginPw.length() == 0) {
			//return String.format("<script> alert('로그인 비밀번호를 입력해주세요.'); history.back(); </script>");
			model.addAttribute("msg", String.format("로그인 비밀번호를 입력해주세요."));
			model.addAttribute("historyBack", true);
			return "common/redirect";
		}
		
		String loginPwConfirm = Util.getAsStr(param.get("loginPwConfirm"), "");
		
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
		String name = Util.getAsStr(param.get("name"), "");
		
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
		
		memberService.join(param);
		
		//return String.format("<script>alert('%d번 회원이 생성되었습니다.'); location.replace('/usr/article-free/list') </script>",id);
		model.addAttribute("msg", String.format("가입되었습니다."));
		model.addAttribute("replaceUri", "/usr/article-free/list");
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/modify")
	public String showModify() {
		
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
		model.addAttribute("replaceUri", "/usr/article-free/list");
		
		return "common/redirect";
	}
}
