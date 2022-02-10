<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Meal info</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal info</h2>

    <form method="POST" action='meals' name="frmUserInfo">
        Meal ID : <input type="text" readonly="readonly" name="id"
                         value="<c:out value="${meal.id}" />" /> <br />
        Date : <input type="datetime-local" name="dateTime"
                    value="${ meal.dateTime }" /> <br />
        Description : <input type="text" name="description"
            value="<c:out value="${meal.description}" />" /> <br />
        Calories : <input type="text" name="calories"
                       value="<c:out value="${meal.calories}" />" /> <br /> <input
            type="submit" value="Submit" />
    </form>
</body>
</html>