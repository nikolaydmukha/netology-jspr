package ru.home.jspr;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JsprServer {
    private int port;
    private ServerSocket serverSocket = null;
    private ExecutorService executorService = Executors.newFixedThreadPool(64);

    public void runServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            try (Socket socket = serverSocket.accept();) {
                executorService.submit(new Connection(socket));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
