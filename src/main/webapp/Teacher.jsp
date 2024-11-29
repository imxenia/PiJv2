<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Учителя</title>

    <!-- Подключение стилей и скриптов для уведомлений -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            // Проверяем, есть ли переданное сообщение об ошибке
            var successMessage = "<%= request.getAttribute("successMessage") != null ? request.getAttribute("successMessage") : "" %>";
            var errorMessage = "<%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>";

            // Если есть сообщение об успехе, показываем его как успешное уведомление
            if (successMessage) {
                toastr.success(successMessage, 'Successfully');
            }

            // Если есть сообщение об ошибке, показываем его как ошибочное уведомление (красное)
            if (errorMessage) {
                toastr.error(errorMessage, 'Error');
            }
        });
    </script>

    <style>
        /* Общие стили */
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f0f9f4;
            margin: 0;
            padding: 0;
            color: #333;
            line-height: 1.6;
        }

        h1, h2 {
            text-align: center;
            color: #2c6b48; /* Пастельный зеленый */
        }

        h1 {
            font-size: 36px;
            margin-top: 50px;
        }

        h2 {
            font-size: 28px;
            margin-top: 40px;
            color: #6dbf8e; /* Пастельный зеленый */
        }

        /* Стилизация формы */
        form {
            max-width: 700px;
            margin: 40px auto;
            padding: 30px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        form p {
            margin: 15px 0;
        }

        input[type="text"] {
            width: 100%;
            padding: 12px;
            border: 2px solid #6dbf8e; /* Пастельный зеленый */
            border-radius: 5px;
            font-size: 1em;
            transition: border 0.3s ease;
        }

        input[type="text"]:focus {
            border-color: #5fae7a; /* Пастельный зеленый */
            outline: none;
        }

        small {
            font-size: 0.9em;
            color: #888;
        }

        .button-container {
            text-align: center;
            margin-top: 20px;
        }

        input[type="submit"] {
            padding: 12px 30px;
            margin: 10px 5px;
            border: none;
            border-radius: 5px;
            background-color: #56C885; /* Пастельный зеленый */
            color: white;
            font-size: 1.1em;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #5fae7a; /* Пастельный зеленый */
        }

        /* Стилизация информации по студентам */
        .teacher-info, .teacher-list {
            max-width: 700px;
            margin: 30px auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        .teacher-info p {
            font-size: 1.1em;
            color: #333;
        }

        .teacher-item {
            background-color: #e3f7e6; /* Пастельный зеленый */
            padding: 5px;
            margin-bottom: 12px;
            border-radius: 6px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s ease;
            font-family: 'Roboto', sans-serif;
            color: #333;
            font-size: 1.2em;
            white-space: pre-wrap;
        }

        .teacher-item:hover {
            background-color: #d0f3dc; /* Пастельный зеленый */
        }

        pre {
            margin: 0;
            padding: 0;
            font-size: 1em;
            color: #555;
            white-space: pre-wrap;
            word-wrap: break-word;
        }
    </style>
</head>
<body>

<h1>Учителя</h1>

<!-- Форма для ввода данных учителя -->
<form action="" method="post" name="teacherform" id="teacherform">
    <p>
        <input type="text" name="id" id="id" value="" size="25" />
        <small> ID учителя </small>
    </p>
    <p>
        <input type="text" name="name" id="name" value="" size="25" />
        <small> Имя </small>
    </p>
    <p>
        <input type="text" name="direction" id="direction" value="" size="25" />
        <small> Направление </small>
    </p>
    <p>
        <input type="text" name="experience" id="experience" value="" size="25" />
        <small> Опыт работы </small>
    </p>

    <div class="button-container">
        <input name="get" type="submit" id="get" value="Искать учителя по номеру" />
        <input name="create" type="submit" id="create" value="Внести в базу нового учителя" />
        <input name="update" type="submit" id="update" value="Обновить учителя" />
        <input name="delete" type="submit" id="delete" value="Удалить учителя из базы данных" />
        <input name="getAll" type="submit" id="getAll" value="Получить всех учителей" />
    </div>
</form>

<!-- Информация по ID учителя -->
<h2>По ID учителя:</h2>
<div class="teacher-info">
    <p>ID: ${id}</p>
    <p>NAME: ${name}</p>
    <p>DIRECTION: ${direction}</p>
    <p>EXPERIENCE: ${experience}</p>
</div>

<!-- Список учителей -->
<div class="teacher-list">
    <h2>Список учителей</h2>
    <c:forEach var="teacher" items="${teachers}">
        <div class="teacher-item">
            <pre><c:out value="${teacher.toString()}" /></pre>
        </div>
    </c:forEach>
</div>

</body>
</html>