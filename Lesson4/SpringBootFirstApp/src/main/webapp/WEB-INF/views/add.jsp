<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%-- <%@ page xmlns:th="http://www.thymeleaf.org"%> --%>
<%@ page session="false"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><spring:message code="label.title" /></title>
<style type="text/css">
.red {
	color: red;
}
</style>
</head>
<body>
	<form:form modelAttribute="product" action="" method="post">
		<p>
			<form:label path="name">
				<spring:message code="label.productName" />
				<form:input type="text" path="name" />
			</form:label>
			<form:errors path="name" cssClass="red" />
		</p>
		<p>
			<form:label path="price">
				<spring:message code="label.price" />
				<form:input type="text" path="price" />
			</form:label>
			<form:errors path="price" cssClass="red" />
		</p>
		<spring:message code="label.submit" var="labelSubmit"></spring:message>
		<input type="submit" value="${labelSubmit}" />
	</form:form>

</body>
</html>