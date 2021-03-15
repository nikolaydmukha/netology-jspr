package ru.home.jspr.task2;




import ru.home.jspr.task2.http.Request;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class App {
    public static void main(String[] args) {

        AppConfig appConfig = AppConfig.getInstance();
        JsprServer server = new JsprServer(64);

        // добавление GET handler'ов (обработчиков)
        server.addHandler("GET", "/index.html", (request, responseStream) -> {
            // TODO: handlers code
            addHandlerProcessor(request, responseStream);
        });
        server.addHandler("GET", "/thread.html", (request, responseStream) -> {
            // TODO: handlers code
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            addHandlerProcessor(request, responseStream);
        });

        // добавление POST handler'ов (обработчиков)
        server.addHandler("POST", "/index.html", (request, responseStream) -> {
            // TODO: handlers code
            addHandlerProcessor(request, responseStream);
        });
        server.addHandler("POST", "/thread.html", (request, responseStream) -> {
            // TODO: handlers code
            addHandlerProcessor(request, responseStream);
        });
        server.runServer((appConfig.getPort()));
    }

    private static void addHandlerProcessor(Request request, BufferedOutputStream responseStream) {
        try {
            if (!JsprServer.validPaths.contains(request.getPath())) {
                responseStream.write((
                        "HTTP/1.1 404 Not Found\r\n" +
                                "Content-Length: 0\r\n" +
                                "Connection: close\r\n" +
                                "\r\n"
                ).getBytes());
                responseStream.flush();
            }

            final Path filePath = Path.of(".", "public", request.getPath());
            final String mimeType = Files.probeContentType((filePath));
            final long length = Files.size(filePath);
            responseStream.write((
                    "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: " + mimeType + "\r\n" +
                            "Content-Length: " + length + "\r\n" +
                            "Connection: close\r\n" +
                            "\r\n"
            ).getBytes());
            Files.copy(filePath, responseStream);
            responseStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
