<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Meals</title>
</head>
<body>
<h2>Список еды</h2>
<table border="2" bgcolor="#e6e6fa">
    <thead>
    <tr>
        <th>ID</th>
        <th>Дата время</th>
        <th>Описание</th>
        <th>Количество калорий</th>
        <th>Превышение</th>
        <th colspan=2>Действия с едой</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealsTo}" var="meal">
        <tr style="color:${ (meal.excess == true ? 'red' : 'green')}; background-color: lightgray">
            <td><c:out value="${meal.id}"/></td>
            <td>
                <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
            </td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><c:out value="${meal.excess}"/></td>
            <td><a href="meals?action=edit&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=insert">Добавить еду</a></p>
<p><a href="index.html">На домашнюю страницу</a></p>
</body>
</html>