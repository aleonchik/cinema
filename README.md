Cinema
======

Пример REST api работы кионотеатра
----------------------------------

[Плей лист](https://www.youtube.com/playlist?list=PLCtvy1fVM2JQy4TX1mchSBRwEdn1PlrJ6)

Схема API-запростов
-------------------

* GET /api/schedule - прочитать список сеансов
* POST /api/schedule - создать новый сеанс
* GET /api/schedule/{timestamp} - прочитать заданный сеанс
* PUT /api/schedule - сохранить сеанс
* DELETE /api/schedule/{timestamp} - удалить заданный сеанс

GIT
---
```bash
git init
git add .
git commit -m "First commit"
git remote add origin https://github.com/aleonchik/cinema.git
git push -u origin master
```
