package ru.home.jspr;


public class App {
    public static void main(String[] args) {

        AppConfig appConfig = AppConfig.getInstance();
        JsprServer server = new JsprServer();
        server.runServer((appConfig.getPort()));
    }
}
