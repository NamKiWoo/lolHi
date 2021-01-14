package com.sbs.example.lolHi.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;

public class SqlInjection extends HttpServlet {
	
	private final static int MAX_USER_ID_LENGTH = 10;
	private final static int MAX_PASSWORD_LENGTH = 20;
	
	private final String UNSECURED_CHAR_REGULAR_EXPRESSION ="[^\\p{Alnum}] | select | delete | update | insert | create | alter |drop";

	private static Pattern unsecuredCharPattern;

	//정규식 초기화
	public void initialize() {
		unsecuredCharPattern = Pattern.compile(UNSECURED_CHAR_REGULAR_EXPRESSION, Pattern.CASE_INSENSITIVE);
	}
	private String makeSecureString(final String str, int maxLength) {
		
		String secureStr = "";
				
		if(str.length() >= maxLength) {
			secureStr = str.substring(0, maxLength);
		} else {
			secureStr = str;
		}
		
		Matcher matcher = unsecuredCharPattern.matcher(secureStr);
		
		return matcher.replaceAll("");
	}

	public  Map<String, Object> replaceStr(Map<String, Object> param){
		
		param.put("replaceUserId", makeSecureString((String)param.get("userId"), MAX_USER_ID_LENGTH));
		param.put("replacePassword", makeSecureString((String)param.get("userPw"), MAX_PASSWORD_LENGTH));
		
		return param; 
	 }
}
