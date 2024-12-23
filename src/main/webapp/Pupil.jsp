<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ученики</title>

    <!-- Подключение стилей и скриптов для уведомлений -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
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
            background-color: #f0f3f5;
            margin: 0;
            padding: 0;
            color: #333;
            line-height: 1.6;
        }

        h1, h2 {
            text-align: center;
            color: #2c3e50;
        }

        h1 {
            font-size: 36px;
            margin-top: 50px;
        }

        h2 {
            font-size: 28px;
            margin-top: 40px;
            color: #3498db;
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
            border: 2px solid #3498db;
            border-radius: 5px;
            font-size: 1em;
            transition: border 0.3s ease;
        }

        input[type="text"]:focus {
            border-color: #2980b9;
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
            background-color: #3498db;
            color: white;
            font-size: 1.1em;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #2980b9;
        }

        /* Стилизация информации по студентам */
        .pupil-list {
            max-width: 800px;
            margin: 30px auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        .pupil-item {
            background-color: #ecf0f1;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s ease;
            font-family: 'Roboto', sans-serif;
            color: #333;
            font-size: 1.1em;
            line-height: 1.4;
        }

        .pupil-item:hover {
            background-color: #dfe6e9;
        }

        .pupil-item pre {
            font-size: 1em;
            color: #555;
            white-space: pre-wrap;
            word-wrap: break-word;
            margin: 10px 0;
        }

        /* Стили для кнопок "Изменить" и "Удалить" */
        .pupil-item input[type="submit"] {
            padding: 8px 20px;
            margin: 5px;
            font-size: 1em;
            background-color: #3498db;
            color: white;
            border-radius: 5px;
            cursor: pointer;
            border: none;
            transition: background-color 0.3s ease;
        }

        .pupil-item input[type="submit"]:hover {
            background-color: #2980b9;
        }

        .pupil-item input[type="submit"]:nth-child(2) {
            background-color: #e74c3c;
        }

        .pupil-item input[type="submit"]:nth-child(2):hover {
            background-color: #c0392b;
        }

        .back-button {
            position: absolute;
            top: 20px;
            left: 20px;
            padding: 10px 20px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s, transform 0.3s;
        }

        .back-button:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }

        .back-button:active {
            transform: scale(1);
        }
    </style>
</head>
<body>

<h1>Ученики</h1>

<!-- Форма для ввода данных студента -->
<form action="" method="post" name="pupilForm" id="pupilForm">
<%--    <p>--%>
<%--        <input type="text" name="id" id="id" value="${fn:escapeXml(p_id)}" size="25"/>--%>
<%--        <small> ID ученика </small>--%>
<%--    </p>--%>
    <p>
        <input type="text" name="name" id="name" value="${fn:escapeXml(p_name)}" size="25"/>
        <small> Имя </small>
    </p>
    <p>
        <input type="text" name="number" id="number" value="${fn:escapeXml(p_number)}" size="25" />
        <small> Номер телефона </small>
        <%--        <input type="submit" name = "search_surname" id = "search_surname" value="Найти группу по на" size="15"/>--%>
    </p>
    <p>
        <input type="text" name="group_id" id="group_id" value="${fn:escapeXml(p_group_id)}" size="25"/>
        <small> ID группы </small>
    </p>

    <div class="button-container">
<%--        <input name="get" type="submit" id="get" value="Получить ученика по номеру"/>--%>
        <input name="createWithoutId" type="submit" id="createWithoutId" value="Внести в базу нового ученика"/>
        <input name="update" type="submit" id="update" value="Обновить ученика"/>
        <input name="getAll" type="submit" id="getAll" value="Получить все"/>
    </div>
</form>

<!-- Список учеников -->
<c:if test="${not empty pupils}">
    <div class="pupil-list">
        <h2>Список учеников</h2>
        <c:forEach var="pupil" items="${pupils}">
            <div class="pupil-item">
                <form action="" method="post" name="pupilForm" id="onePupilForm">
                    <pre><c:out value="${pupil.toString()}"/></pre>
                    <input name="change" type="submit" id="change" value="Изменить">
                    <input name="deleteOther" type="submit" id="deleteOther" value="Удалить">
                    <input name="view" type="submit" id="view" value="Группа">
                    <input type="hidden" name="idOther" id="idOther" value="${fn:escapeXml(pupil.getId())}">
                    <input type="hidden" name="nameOther" id="nameOther" value="${fn:escapeXml(pupil.getName())}">
                    <input type="hidden" name="numberOther" id="numberOther" value="${fn:escapeXml(pupil.getNumber())}">
                    <input type="hidden" name="group_idOther" id="group_idOther" value="${fn:escapeXml(pupil.getGroupId())}">
                </form>
            </div>
        </c:forEach>
    </div>
</c:if>

<c:if test="${not empty groups}">
    <div class="pupil-list">
        <h2 id="groupHeader">${fn:escapeXml(param.nameOther)} принадлежит группе:</h2>
        <c:forEach var="group" items="${groups}">
            <div class="pupil-item">
                <form action="" method="post" name="subForm" id="oneSubForm">
                    <pre><c:out value="${group.toString()}"/></pre>
                </form>
            </div>
        </c:forEach>
    </div>
</c:if>

<button class="back-button" onclick="window.location.href='index.jsp';">Назад на главную</button>

</body>
</html>
