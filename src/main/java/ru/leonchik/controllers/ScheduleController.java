package ru.leonchik.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.leonchik.models.ScheduleModel;
import ru.leonchik.storages.CommonStorage;
import spark.Request;
import spark.Response;
import spark.Route;

import static ru.leonchik.Main.JSON_MIME_TYPE;

public class ScheduleController extends CommonController {
    // тестовый роут
    //public final Route test;
    // имя параметра для передачи идентификатора записи
    private static final String TIMESTAMP_PARAM = "timestamp";

    // список записей
    public final Route list;

    // запрос заданной записи
    public final Route find;

    // создание или изменение записи
    public final Route createOrModify;

    // удаление заданной записи
    public final Route kill;

    // конструктор
    public ScheduleController(final CommonStorage<ScheduleModel> storage) {
        /*this.test = (Request req, Response res) -> {
          return  ok();
        };*/
        // объявление роутов
        this.list = (Request req, Response res) -> {
            return ok(storage.list());
        };

        this.find = (Request req, Response res) -> {
            long timestamp;
            try {
                timestamp = Long.parseLong(req.params(TIMESTAMP_PARAM));
            } catch (Exception exc) {
                return error(Code.BAD_REQUEST, "Задан некорректный идентификатор записи");
            }

            final ScheduleModel schedule = storage.get(timestamp);

            if (schedule != null) {
                return ok(schedule);
            } else {
                return error(Code.BAD_REQUEST, "Не найдена запись с заданным идентификатором");
            }
        };

        this.createOrModify = (Request req, Response res) -> {
            // проверка метода запроса
            if (!req.requestMethod().equalsIgnoreCase("post") && !req.requestMethod().equalsIgnoreCase("put")) {
                return error(Code.BAD_REQUEST, "Задан некорректный метод запроса");
            }

            // проверка типов входных данных
            final String contentType = req.contentType();
            if (contentType == null || !contentType.equals(JSON_MIME_TYPE)) {
                return error(Code.BAD_REQUEST, "Задан неправильный тип входных данных запроса");
            }

            // парсинг входных данных и сохранение
            try {
                final ScheduleModel schedule = (new ObjectMapper()).readValue(req.body(), ScheduleModel.class);
                storage.set(schedule);
            } catch (JsonProcessingException exc) {
                return error(Code.BAD_REQUEST, "Заданы некорректные входные данные запроса " + exc.getMessage());
            }

            return ok();
        };

        this.kill = (Request req, Response res) -> {
            long timestamp;

            try {
                timestamp = Long.parseLong(req.params(TIMESTAMP_PARAM));
            } catch (Exception exc) {
                return error(Code.BAD_REQUEST, "Задан некорректный идентификатор записи");
            }

            storage.kill(timestamp);
            return ok();
        };

    }
}
