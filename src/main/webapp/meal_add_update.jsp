<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Add (edit) new meal</title>
</head>
<body>
<h2>${title}</h2>
<form method="POST" action='meals' name="meal_add_update">
    <table border="2" bgcolor="#e6e6fa">
        <br/>
        <tr>
            <td> Meal Date Time :</td>
            <td><input type="datetime-local" name="dateTime" size="45"
                       value="<c:out value = "${meal.dateTime}" />"/></td>
        </tr>
        <br/>
        <tr>
            <td>Description :</td>
            <td><input type="text" name="description"
                       value="<c:out value="${meal.description}" />"/></td>
        </tr>
        <br/>
        <tr>
            <td>Calories :</td>
            <td><input type="number" name="calories"
                       value="<c:out value="${meal.calories}" />"/></td>
        </tr>
    </table>
    <br>
    <input type="submit" value="Записать (отредактировать) еду"/>

</form>
</body>
</html>