<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix ="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>ȸ�� ����Ʈ</title>
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
 <h2> ȸ�� ����Ʈ</h2>
 <table style = "width: 700px" border ="1" cellspacing = "0" cellpaddiong = "0" >
 	<tr>
 		<th width= "100">���̵�</th>
 		<th width ="100">�н�����</th>
 		<th width ="200">�̸�</th>
 		<th width ="200">����</th>
 	</tr>
	 
	 <c:forEach items="${userList }" var = "userVO" varStatus="loop">
	 <tr>
	 	<td>${userVO.id }</td>
	 	<td>${userVO.pass }</td>
	 	<td><a href="getUser.do?id=${userVO.id }">${userVO.name }</a></td>
	 	<td>${userVO.role }</td>
	 </tr>
	 </c:forEach>
 
 </table>
	 <p></p>
	 <p></p>
	 <p></p>
	 <h2>
	 <a href ="index.jsp">ó������</a>
	 </h2>
</body>
</html>