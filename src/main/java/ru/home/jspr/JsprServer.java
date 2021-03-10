package ru.home.jspr;

import ru.home.jspr.http.Handler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class JsprServer {

    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newFixedThreadPool(64);
    public static final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png");
    public static HashMap<String, HashMap<String, Handler>> handlers = new HashMap<>();


    public void runServer(int port) {

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new Connection(socket));
            }
        } catch (
                IOException exception) {
            exception.printStackTrace();
        }
    }

    public void addHandler(String method, String file, Handler handler){
        HashMap <String, Handler> innerMap = new HashMap<>();
        innerMap.put(file, handler);
        handlers.put(method, innerMap);
    }
}
