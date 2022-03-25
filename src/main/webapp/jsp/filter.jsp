<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 26.02.2022
  Time: 6:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<style>
    <%@ include file="/css/filter_style.css" %>
</style>
<body>
<caption><h2>Filter</h2></caption>
<form method="get" class="filter-form paginatorForm" name="numberOfRecordsPerPageForm">
    <p> Items per page </p>
    <select id="numberOfRecordsPerPage" class="form-select" name="numberOfRecordsPerPage" onchange="changePagesQuantity(${dragonsLength})">
        <option value="5">5</option>
        <option selected value="10">10</option>
        <option value="25">25</option>
    </select>
    <p> Selected Page </p>
    <div class="selectedPage"></div>
    <select id="selectedPage" name="selectedPage">
        <c:forEach var="item" items='${pagesQuality}'>
            <option value="${item}">${item}</option>
        </c:forEach>
    </select>
    <%--    <input type="submit" class="btn btn-primary name-filter-btn"/>--%>
</form>
<ul class="nav nav-tabs filter-tabs" data-tabs="tabs" id="filter-tab">
    <li class="nav-item active">
        <a class="nav-link active" data-toggle="tab" href="#filter">Filter</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#sort">Sort</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#age">Age</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#color">Color</a>
    </li>
</ul>
<div class="tab-content">
    <div class="tab-pane fade show active" id="filter">
        <form action="${pageContext.request.contextPath}/dragons/filter" method="get" class="filter-form" name="filterForm">
            <p> <input class="form-check-input isNameDisabled" type="checkbox" > Name:
                <input type="text" name="name" class="form-control Name" disabled/></p>
            <p>Coordinates:</p>
            <p> <input class="form-check-input isXDisabled" type="checkbox" > X: </p>
            <div class="filter-form__range">
                <input type="text" name="x1" class="form-control X" disabled/> - <input type="text" name="x2" class="form-control X" disabled/></div>
            <p> <input class="form-check-input isYDisabled" type="checkbox" > Y: </p>
            <div class="filter-form__range">
                <input type="text" name="y1" class="form-control Y" disabled/> - <input type="text" name="y2" class="form-control Y" disabled/></div>
            <p>Creation date:</p>
            <p> <input class="form-check-input isDateDisabled" type="checkbox" > Date:</p>
            <div class="filter-form__range">
                <input type="date" name="start-creation-date" class="form-control Date" disabled> - <input type="date"
                                                                                                           name="end-creation-date" class="form-control Date" disabled>
            </div>
            <p>Time:</p>
            <div class="filter-form__range">
                <input type="time" name="start-creation-time" value="00:00" class="form-control Date" disabled> - <input type="time"
                                                                                                                         name="end-creation-time"
                                                                                                                         value="23:59" class="form-control Date" disabled>
            </div>
            <p> <input class="form-check-input isAgeDisabled" type="checkbox" > Age:</p>
            <div class="filter-form__range">
                <input type="text" name="age1" class="form-control Age" disabled/>- <input type="text" name="age2" class="form-control Age" disabled/>
            </div>

            <p> <input class="form-check-input isColorDisabled" type="checkbox" > Color:
            <div class="form-check form-check-inline">
                <input class="form-check-input Color" name="color" type="checkbox" id="color1" value="RED" disabled>
                <label class="form-check-label" for="color1">RED</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Color" name="color" type="checkbox" id="color2" value="BLACK" disabled>
                <label class="form-check-label" for="color2">BLACK</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Color" name="color" type="checkbox" id="color3" value="YELLOW" disabled>
                <label class="form-check-label" for="color3">YELLOW</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Color" name="color" type="checkbox" id="color4" value="ORANGE" disabled>
                <label class="form-check-label" for="color4">ORANGE</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Color" name="color" type="checkbox" id="color5" value="WHITE" disabled>
                <label class="form-check-label" for="color5">WHITE</label>
            </div>
            </p>

            <p> <input class="form-check-input isTypeDisabled" type="checkbox" > Type:
            <div class="form-check form-check-inline">
                <input class="form-check-input Type" name="type" type="checkbox" id="type1" value="WATER" disabled>
                <label class="form-check-label" for="type1">WATER</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Type" name="type" type="checkbox" id="type2" value="UNDERGROUND" disabled>
                <label class="form-check-label" for="type2">UNDERGROUND</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Type" name="type" type="checkbox" id="type3" value="AIR" disabled>
                <label class="form-check-label" for="type3">AIR</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Type" name="type" type="checkbox" id="type4" value="FIRE" disabled>
                <label class="form-check-label" for="type4">FIRE</label>
            </div>
            </p>

            <p>Cave: </p>
            <p> <input class="form-check-input isDepthDisabled" type="checkbox" > Depth:</p>
            <div class="filter-form__range">
                <input type="text" name="depth1" class="form-control Depth" disabled/> - <input type="text" name="depth2"
                                                                                                  class="form-control Height" disabled/></div>
            <input type="submit" class="btn btn-primary filter-btn" id="filter-btn" value="find"/>
        </form>
    </div>
    <div class="tab-pane fade" id="sort">
        <form action="${pageContext.request.contextPath}/dragons/sort" method="get" class="filter-form" name="sortForm">
            <input type="radio" class="form-check-input" name="sortBy" value="id" checked>  id<BR>
            <input type="radio" class="form-check-input" name="sortBy" value="name">  name<BR>
            <input type="radio" class="form-check-input" name="sortBy" value="x">  x<BR>
            <input type="radio" class="form-check-input" name="sortBy" value="y">  y<BR>
            <input type="radio" class="form-check-input" name="sortBy" value="creation-date">  creation-date<BR>
            <input type="radio" class="form-check-input" name="sortBy" value="age">  age<BR>
            <input type="radio" class="form-check-input" name="sortBy" value="color">  color <BR>
            <input type="radio" class="form-check-input" name="sortBy" value="type">  type<BR>
            <input type="radio" class="form-check-input" name="sortBy" value="depth">  depth <BR>
            Order: <BR>
            <input type="radio" class="form-check-input" name="order" value="ASC" checked>  ASC<BR>
            <input type="radio" class="form-check-input" name="order" value="DESC">  DESC<BR>
            <input type="submit" class="btn btn-primary name-filter-btn" value="sort"/>
        </form>
    </div>
    <div class="tab-pane fade" id="age">
        <form action="${pageContext.request.contextPath}/age/" method="get" class="filter-form" name="aggregateFunctions"
        target="result">
            <select id = "function">
                <option value="avg" selected>Average</option>
                <option value="sum">Sum</option>
            </select>
            <input type="submit" class="btn btn-primary name-filter-btn" value="get result"/>
            <iframe id="result" name="result"></iframe>
        </form>
    </div>
    <div class="tab-pane fade" id="color">
        <form action="${pageContext.request.contextPath}/color" method="get" class="filter-form" name="findDragonsWithLesserColor">
            <p> Get dragons with Color less than: </p>
            <div class="form-check form-check-inline">
                <input class="form-check-input Color" name="color" type="radio" id="color01" value="RED" checked>
                <label class="form-check-label" for="color01">RED</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Color" name="color" type="radio" id="color02" value="BLACK">
                <label class="form-check-label" for="color02">BLACK</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Color" name="color" type="radio" id="color03" value="YELLOW">
                <label class="form-check-label" for="color03">YELLOW</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Color" name="color" type="radio" id="color04" value="ORANGE">
                <label class="form-check-label" for="color04">ORANGE</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input Color" name="color" type="radio" id="color05" value="WHITE">
                <label class="form-check-label" for="color05">WHITE</label>
            </div>
            </p>
            <input type="submit" class="btn btn-primary name-filter-btn" value="find"/>
        </form>
    </div>
</div>
<script>
    <%@ include file="/js/filter.js" %>
</script>
</body>
</html>