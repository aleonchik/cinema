package ru.leonchik;

import spark.Spark;
import static spark.Spark.*;

public class Main {
    public static final String JSON_MIME_TYPE = "application/json";

    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 8808;

    public static void main(String[] args) {
        // конфигурация сервера
        ipAddress(SERVER_HOST);
        port(SERVER_PORT);

        // объявление роутов
        after((req, res) -> res.type(JSON_MIME_TYPE));

        // останов сервера
        Runtime.getRuntime().addShutdownHook(new Thread(Spark::stop));
    }
}
