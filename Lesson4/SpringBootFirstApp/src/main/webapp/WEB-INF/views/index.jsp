<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><spring:message code="label.titleMain" /></title>
</head>
<body>
	<div style="text-align: right;padding:5px;margin:5px 0px;background:#ccc;">
		<a href="${pageContext.request.contextPath}/?lang=en">Login
			(English)</a> <a href="${pageContext.request.contextPath}/?lang=fr">Login
			(France)</a> <a href="${pageContext.request.contextPath}/?lang=ru">Login
			(Russian)</a>
	</div>
	<div><spring:message code="label.titlePage" /></div>
	<div>
		<c:if test="${message != null}">
			"${message}"
		</c:if>
	</div>
	<div>
		<c:if test="${list != null}">
			<table>
				<tr>
					<th>#</th>
					<th><spring:message code="label.productName" /></th>
					<th><spring:message code="label.price" /></th>
				</tr>
				<c:forEach items="${list}" var="product">
					<tr>
						<td>"${product.id}"</td>
						<td><a href = '${product.id}/info?id=${product.id}&name=${product.name}&price=${product.price}'>"${product.name}"</a></td>
						<td>"${product.price}"</td>
						<td>
							<button type="button" class="btn btn-primary mb-2"
								onclick="window.location.href = 'delete/${product.id}'">Delete</button>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>

	<div>
		<a href="add"><spring:message code="label.link" /></a>
	</div>
</body>
</html>