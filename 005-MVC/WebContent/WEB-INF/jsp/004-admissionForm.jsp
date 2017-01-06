<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<h1>${commonHeader}</h1>
<form:form method="post" action="004-admissionSuccess" 	modelAttribute="user">
	Userid: <form:input path="userid" /> 	<br>
	Password: <form:input path="password" /> 	<br>
	Phone: <form:input path="phone" /> 	<br>
	DOB: <form:input path="dob"/> 	<br>
	Skills: <select name="skills" multiple>
		<option value="Spring Core">Spring Core</option>
		<option value="Spring MVC">Spring MVC</option>
		<option value="Spring AOP">Spring AOP</option>
	</select>
	Address:<br>
	Street: <form:input path="userAddress.street"/> <br>
	Zip: <form:input path="userAddress.zip"/><br>
	<input type="submit" value="save" />
</form:form>