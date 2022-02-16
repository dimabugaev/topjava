<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
        form {
            /*margin: 0 auto; */
            width: 800px;

            padding: 1em;
            border: 1px solid #CCC;
            border-radius: 1em;
        }
        ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        form li {
            float: left;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>

    <form method="get" action="meals">
        <ul>
        <li>
            <label for="startDate">from date:</label>
            <input type="date" id="startDate" value="${param.startDate}" name="startDate">
        </li>
        <li>
            <label for="endDate">to date:</label>
            <input type="date" id="endDate" value="${param.endDate}" name="endDate">
        </li>
        <li>
            <label for="startTime">from time:</label>
            <input type="time" id="startTime" value="${param.startTime}" name="startTime">
        </li>
        <li>
            <label for="endTime">to time:</label>
            <input type="time" id="endTime" value="${param.endTime}" name="endTime">
        </li>

        </ul>
        <button type="submit">Set</button>
    </form>

    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>