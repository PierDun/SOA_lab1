<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 26.02.2022
  Time: 3:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dragons</title>
    <script language="JavaScript" type="text/JavaScript" src="${pageContext.request.contextPath}/js/dragonForm.js"></script>
</head>
<body>
<jsp:include page="menu.jsp">
    <jsp:param name="new" value="active" />
</jsp:include>
<div class="dragon-form">
    <caption>
        <h2>
            <c:if test="${dragon != null}">
                Edit Dragon
            </c:if>
            <c:if test="${dragon == null}">
                Add New Dragon
            </c:if>
        </h2>
    </caption>
    <div class="error-msg">
    </div>
    <c:if test="${dragon != null}">
    <div align="center">
        <form name="updateDragonForm">
            </c:if>
            <c:if test="${dragon == null}">
            <form method="post" align="center" name="addDragonForm"></c:if>
                <c:if test="${dragon != null}">
                    <input type="hidden" name="id" value="<c:out value='${dragon.id}' />" class="form-control"/>
                    <input type="hidden" name="creationDate" value="<c:out value='${dragon.creationDate}' />" class="form-control"/>
                </c:if>
                <p>Name:
                    <input type="text" name="name" value="<c:out value='${dragon.name}' />" class="form-control"/> </p>
                <p>X:
                    <input type="text" name="x" value="<c:out value='${dragon.coordinates.x}' />" class="form-control"/></p>
                <p>Y:
                    <input type="text" name="y" value="<c:out value='${dragon.coordinates.y}' />" class="form-control"/></p>
                <p>Age:
                    <input type="text" name="age" value="<c:out value='${dragon.age}' />" class="form-control"/></p>
                <p>Description:
                    <input type="text" name="description" value="<c:out value='${dragon.description}' />" class="form-control"/></p>
                <p>Color:
                    <select name="color" value="<c:out value='${dragon.color}' />" class="form-control">
                        <option value="RED">RED</option>
                        <option value="BLACK">BLACK</option>
                        <option value="YELLOW">YELLOW</option>
                        <option value="ORANGE">ORANGE</option>
                        <option value="WHITE">WHITE</option>
                    </select></p>
                <p>Type:
                    <select name="type" value="<c:out value='${dragon.type}' />" class="form-control">
                        <option value="WATER">WATER</option>
                        <option value="UNDERGROUND">UNDERGROUND</option>
                        <option value="AIR">AIR</option>
                        <option value="FIRE">FIRE</option>
                    </select></p>
                <p>Cave: </p>
                <p>Depth: <input type="text" name="depth" value="<c:out value='${dragon.cave.depth}' />" class="form-control"/></p>
                <c:if test="${dragon == null}">
                    <button class="btn btn-primary mx-auto mt-2" onclick="addDragon()">Add</button>
                </c:if>
            </form>
            <c:if test="${dragon != null}">
            <button class="btn btn-primary mx-auto mt-2" onclick="updateDragon()">Update</button>
            </c:if>
    </div>
</div>
</body>
</html>