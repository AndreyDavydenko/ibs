<%@ page import="ru.appline.logic.Model" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Добро пожаловать на домашнюю страницу</title>
</head>
<body>
<h1>Работа с пользователями</h1>
Введите ID пользователя (0 - вывести список всех пользователей)
<br/>
Доступно: <%
        Model model = Model.getInstance();
        out.print(model.getFromList().size());
        %>
<form method="get" action="get">
    <label>ID:
        <input type="text" name="id"><br/>
    </label>
    <button type="submit">Поиск</button>
</form>
<a href="addUser.html">Создать нового пользователя</a>
</body>
</html>