<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>База данных групп платного обучения</title>

    <style>
        /* Общие стили страницы */
        body {
            font-family: 'Roboto', sans-serif; /* Изменен шрифт для лучшего восприятия */
            background-color: #f0f9f4;
            color: #333;
            margin: 0;
            padding: 0;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            text-align: center;
            box-sizing: border-box;
            background-blend-mode: overlay; /* Наложение фона */
        }

        /* Заголовок */
        h1 {
            font-size: 3.5em;
            color: #3e5b44; /* Темно-оливковый */
            margin-bottom: 40px;
            text-transform: uppercase;
            letter-spacing: 3px;
            text-shadow: 2px 2px 15px rgba(0, 0, 0, 0.1); /* Легкая тень для заголовка */
        }

        /* Стиль ссылок (кнопок) */
        a {
            font-size: 1.2em;
            color: white;
            text-decoration: none;
            background-color: #56C885; /* Оливковый цвет */
            padding: 15px 30px;
            border-radius: 30px; /* Округлые края кнопок */
            margin: 10px;
            display: inline-block;
            transition: all 0.3s ease;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1); /* Легкая тень */
        }

        /* Эффекты для кнопок */
        a:hover {
            background-color: #479A68; /* Темно-оливковый при наведении */
            transform: translateY(-5px); /* Подъем кнопки */
            box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.2); /* Тень при наведении */
        }

        a:active {
            background-color: #4a6336; /* Еще темнее при нажатии */
            transform: translateY(2px); /* Легкий эффект нажатия */
        }

        /* Адаптивность */
        @media (max-width: 800px) {
            h1 {
                font-size: 2.5em;
                margin-bottom: 30px;
            }
            a {
                font-size: 1.1em;
                padding: 12px 25px;
            }
        }

        @media (max-width: 500px) {
            h1 {
                font-size: 2em;
                margin-bottom: 20px;
            }
            a {
                font-size: 1em;
                padding: 10px 20px;
            }
        }

        /* Контейнер для кнопок */
        div {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
        }
    </style>
</head>
<body>

<h1>База данных групп платного обучения</h1>

<div>
    <a href="group-servlet">Группы</a>
    <a href="pupil-servlet">Ученики</a>
    <a href="teacher-servlet">Учителя</a>
</div>

</body>
</html>
