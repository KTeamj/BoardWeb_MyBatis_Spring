<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>ȸ�� ����</title>
</head>
<style>
* {
align-content: center;
}
</style>
<body>
	<h3>ȸ������</h3>
	
	<form action = "insertUser.do" method = "post">
		���̵� : <input type ="text" name = "id">
		<p> �н����� : <input type ="password" name ="pass">
		<p> �̸� : <input type="text" name ="name">
		<p> ���� : <input type="text" name ="role">
		<p> <input type ="submit" value ="ȸ������">
	</form>

</body>
</html>