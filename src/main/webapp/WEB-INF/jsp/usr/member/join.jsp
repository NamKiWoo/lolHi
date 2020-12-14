<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	
	<h1>회원 가입</h1>
	<hr />

	<form action="doJoin" method="POST">
		<div>
			Id : <input type="text" maxlength="30" placeholder="로그인 ID를 입력해주세요."
				name="loginId" />
		</div>
		<div>
			비밀번호 : <input type="password" maxlength="30" placeholder="비밀번호를 입력해주세요."
				name="loginPw" />
		</div>
		<div>
			이름 : <input type="text" maxlength="30" placeholder="이름을 입력해주세요."
				name="name" />
		</div>
		<div>
			가입 : <input type="submit" value="가입" />
		</div>
	</form>
	
</body>
</html>