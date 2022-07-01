<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<style>
*{
margin: auto;
}
h2{
text-align: center;
}

</style>
</head>
<body>

	<h2>회원 정보 수정</h2>
	<hr>
	
	<form action ="updateUser.do" method="post">
		<p>아이디 : <input type ="text" name ="id" value ="${userVO.id }">
		<p>비밀번호 : <input type ="password" name ="pass" value ="${userVO.pass }">
		<p> 이름 : <input type ="text" name ="name" value="${userVO.name }">
		<p> 역할 : <input type ="text" name ="role" value ="${userVO.role }">
		
		<p> <input type ="submit" value ="회원정보수정">
	</form>
</body>
</html>