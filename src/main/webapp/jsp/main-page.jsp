<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>СОА - 1-ая Лабораторная</title>
</head>
<style>
    <%@ include file="/css/main.css" %>
</style>
<body>
<jsp:include page="menu.jsp">
    <jsp:param name="list" value="active" />
</jsp:include>
<div class="main-page">
    <div class="table" align="center">
        <caption><h2>List of Dragons</h2></caption>
        <table border="1" cellpadding="13" class="table">
            <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>X</th>
                <th>Y</th>
                <th>Creation Date</th>
                <th>Age</th>
                <th>Type</th>
                <th>Color</th>
                <th>Description</th>
                <th>Depth of cave</th>
                <th>Edit / Delete</th>
            </tr>
            </thead>

            <c:forEach var="dragon" items="${dragons}">
                <c:if test="${dragon != null}">

                    <tr class="table-rows">
                        <td>${dragon.id}</td>
                        <td>${dragon.name}</td>
                        <td>${dragon.coordinates.x}</td>
                        <td>${dragon.coordinates.y}</td>
                        <td>${dragon.creationDate.format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
                        <td>${dragon.age}</td>
                        <td>${dragon.type}</td>
                        <td>${dragon.color}</td>
                        <td>${dragon.description}</td>
                        <td>${dragon.cave.depth}</td>
                        <td>
                            <a href="pages/edit-form?id=${dragon.id}">Edit </a>
                            <button class="btn btn-primary mx-auto mt-2" onclick="deleteDragon(${dragon.id});">Delete</button>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </div>
    <div class="filter">
        <jsp:include page="filter.jsp"/>
    </div>
</div>
</body>
</html>