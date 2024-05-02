<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="<c:url value="/resources/test.css"/>">
<title>Student Registration</title>
<style>
.error {
	color: red;
}
</style>
</head>
<body class="login-page-body">

	<div class="login-page">
		<div class="form">
			<div class="login">
				<div class="login-header">
					<h1>Welcome!</h1>
					<h1>Hello world</h1>
					<h3 class="error">${error}</h3>
					<h3 class="error">${error1}</h3>
					<%-- //<h3 class="error">${error2}</h3> --%>

				</div>
			</div>
			<form:form class="login-form" action="login" method="post" modelAttribute="users">
				<form:input type="email" placeholder="Email" path="email"></form:input>
				<form:input type="password" placeholder="Password" path="password"></form:input>
				<button>login</button>
			</form:form>
		</div>
	</div>
</body>

</html>