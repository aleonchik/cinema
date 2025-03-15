package ru.leonchik;

import ru.leonchik.models.ScheduleModel;
import ru.leonchik.storages.FileStorage;
import spark.Spark;
import static spark.Spark.*;

import ru.leonchik.controllers.ScheduleController;
import static ru.leonchik.transformers.JsonTransformer.JSON;

public class Main {
    public static final String JSON_MIME_TYPE = "application/json";

    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 8808;

    private static final String DEFAULT_FILE = "schedule.tsv";

    public static void main(String[] args) {
        // создание фалового хранилища
        final FileStorage<ScheduleModel> storage = new FileStorage<ScheduleModel>(
                args != null && args.length == 1 ? args[0] : DEFAULT_FILE,
                ScheduleModel.class
        );
        // чтение файла данных
        storage.readAll();

        // создание контроллера
        final ScheduleController controller = new ScheduleController(storage);

        // конфигурация сервера
        ipAddress(SERVER_HOST);
        port(SERVER_PORT);

        // объявление роутов
        path("/api", () -> {
            get(    "/schedule",            controller.list,           JSON);
            get(    "/schedule/:timestamp", controller.find,           JSON);
            post(   "/schedule",            controller.createOrModify, JSON);
            put(    "/schedule",            controller.createOrModify, JSON);
            delete( "/schedule/:timestamp", controller.kill,           JSON);

        });

        /*after((req, res) -> {
            res.type(JSON_MIME_TYPE);
        });*/
        after((req, res) -> res.type(JSON_MIME_TYPE));

        // останов сервера
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Spark.stop();
                // сохранение данных в файл
                storage.writeAll();
            }
        });
        //Runtime.getRuntime().addShutdownHook(new Thread(Spark::stop));
    }
}
