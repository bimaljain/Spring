<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form method="post" action="save" modelAttribute="user">
	Userid: <form:input path="userid" /> <br>
	Password: <form:input path="password" /> <br>
	<input type="submit" value="save" />
</form:form>