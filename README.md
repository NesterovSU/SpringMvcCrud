# SpringMvcCrud
Данное web-приложение отображает информацию из базы данных Postgresql. Есть возможность изменения информации, создания новой и удаления записи. При этом используются Get, Post, Patch, Delete запросы HTTP(согласно REST API). Запись в базе содержит числовой идентификатор, имя пользователя, возраст, емайл.

При создании и изменении записей реализованы проверки форм ввода с помощью Hibernate-Validator. Для работы с базой данных используется JDBC driver.

Это тренировочный проект по Spring MVC.