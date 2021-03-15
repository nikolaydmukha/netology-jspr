package ru.home.jspr.task2.http;

import java.io.BufferedOutputStream;

public interface Handler {
    void handle(Request request, BufferedOutputStream responseStream);
}
