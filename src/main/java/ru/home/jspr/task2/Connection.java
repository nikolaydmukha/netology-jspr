package ru.home.jspr.task2;

import ru.home.jspr.task2.http.Request;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class Connection implements Runnable {
    private final Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (socket;
             final InputStream in = socket.getInputStream();
             final BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
        ) {
            while (true) {

                Request request = Request.prepareRequest(in);
                //Some query params
                JsprServer.handlers.get(request.getPath().concat(":").concat(request.getMethod())).get(request.getMethod()).handle(request, out);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

