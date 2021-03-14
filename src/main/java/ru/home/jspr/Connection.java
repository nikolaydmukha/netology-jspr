package ru.home.jspr;

import ru.home.jspr.http.Request;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Connection implements Runnable {
    private final Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
        System.out.println(socket.isClosed());
    }

    @Override
    public void run() {
        while (true) {
            try (socket;
                 final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 final BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
            ) {
                final String requestLine = in.readLine();
                final String[] parts = requestLine.split(" ");

                if (parts.length != 3) {
                    //just close socket
                    continue;
                }

                Request request = new Request(parts[0], parts[1], parts[2]);
                //Some query params
//                String paramValue = request.parseQueryString().get("PARAM_NAME");
                if (request.getPath().equals("/thread.html")) {
                    Thread.sleep(6000);
                }
                JsprServer.handlers.get(request.getPath().concat(":").concat(request.getMethod())).get(request.getMethod()).handle(request, out);
            } catch (IOException | InterruptedException exception) {
                System.out.println("2 " + exception.getMessage());
            }
        }
    }
}
