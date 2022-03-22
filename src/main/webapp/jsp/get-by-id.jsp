<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 26.02.2022
  Time: 2:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>GetByID</title>
</head>
<body>
<jsp:include page="menu.jsp">
  <jsp:param name="id" value="active" />
</jsp:include>
<form align="center" action="${pageContext.request.contextPath}/dragons/" method="get" style="margin-top: 20px" name="getDragonByID">
  <caption><h2>Get dragon by id</h2></caption>
  <input class="form-control mt-3" type="text" name="id" value="0" style="width: 30%; margin: 0 auto;"/>
  <c:if test="${dragon == null && msg != null}">
    <div class="mx-auto" style="color: red">
      <h7>${msg}</h7>
    </div>
  </c:if>
  <input type="submit" name="get" class="btn btn-primary mx-auto mt-3" value="find"/>
</form>
<c:if test="${dragon != null}">
  <div align="center" class="mx-5">
    <caption><h2>dragon with id ${dragon.id}</h2></caption>
    <table border="1" cellpadding="13" class="table">
      <thead class="thead-dark">
      <tr>
        <th>Name</th>
        <th>X</th>
        <th>Y</th>
        <th>CreationDate</th>
        <th>Age</th>
        <th>Type</th>
        <th>Color</th>
        <th>Description</th>
        <th>Depth of cave</th>
      </tr>
      </thead>
      <tr>
        <td>${dragon.name}</td>
        <td>${dragon.coordinates.x}</td>
        <td>${dragon.coordinates.y}</td>
        <td>${dragon.creationDate.format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
        <td>${dragon.age}</td>
        <td>${dragon.type}</td>
        <td>${dragon.color}</td>
        <td>${dragon.description}</td>
        <td>${dragon.cave.depth}</td>
      </tr>
    </table>
  </div>
</c:if>
</body>
</html>