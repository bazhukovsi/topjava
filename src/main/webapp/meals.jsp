<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Meals</title>
</head>
<body>
<h2>Список еды</h2>
<table border=1>
    <thead>
    <tr>
        <th>Дата время</th>
        <th>Описание</th>
        <th>Количество калорий</th>
        <th>Превышение</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealsTo}" var="meal">
        <tr style="color:${ (meal.excess == true ? 'red' : 'green')}; background-color: lightgray">
            <td>
                <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
            </td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><c:out value="${meal.excess}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="./">На домашнюю страницу</a></p>
</body>
</html>