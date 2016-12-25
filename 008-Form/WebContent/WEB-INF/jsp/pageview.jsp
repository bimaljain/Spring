<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Employees List</h1>
<table border="2" width="70%" cellpadding="2">
	<tr>
		<th>Id</th>
		<th>Userid</th>
		<th>Password</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<c:forEach var="user" items="${userList}">
		<tr>
			<td>${user.id}</td>
			<td>${user.userid}</td>
			<td>${user.password}</td>
			<td><a href="editemp/${user.id}">Edit</a></td>
			<td><a href="deleteemp/${user.id}">Delete</a></td>
		</tr>
	</c:forEach>
</table>
<br />
   <a href="/pageview/1">1</a>   
   <a href="/pageview/2">2</a>   
   <a href="/pageview/3">3</a>
