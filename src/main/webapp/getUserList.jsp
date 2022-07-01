<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix ="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>회원 리스트</title>
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
 <h2> 회원 리스트</h2>
 <table style = "width: 700px" border ="1" cellspacing = "0" cellpaddiong = "0" >
 	<tr>
 		<th width= "100">아이디</th>
 		<th width ="100">패스워드</th>
 		<th width ="200">이름</th>
 		<th width ="200">역할</th>
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
	 <a href ="index.jsp">처음으로</a>
	 </h2>
</body>
</html>