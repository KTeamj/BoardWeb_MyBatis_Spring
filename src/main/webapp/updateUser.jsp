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

	<h2>ȸ�� ���� ����</h2>
	<hr>
	
	<form action ="updateUser.do" method="post">
		<p>���̵� : <input type ="text" name ="id" value ="${userVO.id }">
		<p>��й�ȣ : <input type ="password" name ="pass" value ="${userVO.pass }">
		<p> �̸� : <input type ="text" name ="name" value="${userVO.name }">
		<p> ���� : <input type ="text" name ="role" value ="${userVO.role }">
		
		<p> <input type ="submit" value ="ȸ����������">
	</form>
</body>
</html>