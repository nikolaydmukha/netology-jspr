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

    private ExecutorService executorService;
    public static final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png", "/thread.html");
    public static HashMap<String, HashMap<String, Handler>> handlers = new HashMap<>();

    public JsprServer(int threads) {
        this.executorService = Executors.newFixedThreadPool(threads);
    }

    public void runServer(int port) {
        try(ServerSocket serverSocket = new ServerSocket(port);) {
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new Connection(socket));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void addHandler(String method, String file, Handler handler){
        HashMap <String, Handler> innerMap = new HashMap<>();
        innerMap.put(method, handler);
        handlers.put(file.concat(":").concat(method), innerMap);
    }
}
