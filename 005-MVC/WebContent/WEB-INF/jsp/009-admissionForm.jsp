<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form method="post" action="009-admissionSuccess" 	modelAttribute="user">
	Hobby1: <form:input path="hobby1" /> 	
			<form:errors path="hobby1" cssStyle="color: red;"/> <br>
	Hobby2: <form:input path="hobby2" /> 	
			<form:errors path="hobby2" cssStyle="color: red;"/> <br>
	Hobby3: <form:input path="hobby3" /> 	
		<form:errors path="hobby3" cssStyle="color: red;"/> <br>
		
	<input type="submit" value="save" />
</form:form>