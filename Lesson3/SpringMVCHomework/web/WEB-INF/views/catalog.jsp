<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%><!-- без этого jstl не работает -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<link href="css/catalog.css" rel="stylesheet" />
<link rel="icon" href="css/resources/cactus.png">
<title>Catalog page</title>
</head>
<body>


	<div class="search search_margin">
		<div>
			<form action="products" method="get" class="form-inline search" 
				name="searchForm">
				<div class="form-group mx-sm-3 mb-2">
					<input type="text" name="search_product" class="form-control"
						id="inputPassword2" placeholder="Name product">
				</div>
				<button type="submit" class="btn btn-primary mb-2">Search</button>
			</form>
		</div>

		<div>
			<button type="button" class="btn btn-dark"
				onclick="window.location.href = 'exit'">Exit</button>
		</div>
	</div>



	<h1 class="search_margin">Catalog all products</h1>

	<div id="main">
		<table class="table table-bordered">
			<tr>
				<th scope="col">Name</th>
				<th scope="col">Price</th>
				<th scope="col"></th>
			</tr>

			<form action="products/add" method="get" name="addForm">
				<tr>
					<td><input type="text" name="name"
						placeholder="Product name"></td>
					<td><input type="text" name="price"
						placeholder="Product price"></td>
					<td>
						<button type="submit" class="btn btn-primary mb-2">Add new product</button>
					</td>
				</tr>
			</form>

			<c:forEach items="${catalog}" var="good">
				<tr>
					<td>"${good.name}"</td>
					<td>"${good.price}"</td>
					<!-- 					<td><button onclick="myFunction()">Buy</button></td> -->
					<td>
						<button type="button" class="btn btn-primary mb-2"
							onclick="window.location.href = 'products/${good.id}/updateProduct?id=${good.id}&name=${good.name}&price=${good.price}'">Update</button>
						<button type="button" class="btn btn-primary mb-2"
							onclick="window.location.href = 'products/delete?id=${good.id}'">Delete</button>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<c:if test="${flag}">
		<button type="button" class="btn btn-primary mb-2"
							onclick="window.location.href = 'products'">Return to catalog</button>
	</c:if>

	<br>
	<div class="text-info bg-dark">
		<c:out value="Message: '${message}'"></c:out>
	</div>
	<div class="text-warning bg-dark"">
		Action message:
		<c:if test="${message_action != null}">
			<c:out value='${message_action}'></c:out>
		</c:if>
	</div>


	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
		crossorigin="anonymous"></script>
</body>
</html>