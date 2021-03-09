package ru.home.jspr;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JsprServer {

    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newFixedThreadPool(64);

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
//    public void addHandler(String method, String file, Handler handler){
//
//    }
}
