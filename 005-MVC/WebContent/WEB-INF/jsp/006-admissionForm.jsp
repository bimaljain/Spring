<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<h1>${commonHeader}</h1>
<form:form method="post" action="006-admissionSuccess" 	modelAttribute="user">
	Userid: <form:input path="userid" /> 	
			<form:errors path="userid" cssStyle="color: red;"/> <br>
	Password: <form:input path="password" /> 	
			<form:errors path="password" cssStyle="color: red;"/> <br>
	Phone: <form:input path="phone" /> 	
			<form:errors path="phone" cssStyle="color: red;"/> <br>
	DOB: <form:input path="dob"/> 	
		<form:errors path="dob" cssStyle="color: red;"/> <br>
	Skills: <select name="skills" multiple>
		<option value="Spring Core">Spring Core</option>
		<option value="Spring MVC">Spring MVC</option>
		<option value="Spring AOP">Spring AOP</option>
	</select> 
	<form:errors path="skills" cssStyle="color: red;"/> <br>
	Address:<br>
	Street: <form:input path="userAddress.street"/> 
			<form:errors path="userAddress.street" cssStyle="color: red;"/> <br>
	Zip: <form:input path="userAddress.zip"/> 
		<form:errors path="userAddress.zip" cssStyle="color: red;"/> <br>
		
	<input type="submit" value="save" />
</form:form>