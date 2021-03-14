package ru.home.jspr.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String method;
    private String path;
    private String queryString;
    private String protocol;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private InputStream in;

    private Request(String method, String path, String queryString, String protocol, Map<String, String> headers, Map<String, String> queryParams) {
        this.method = method;
        this.path = path;
        this.queryString = queryString;
        this.protocol = protocol;
        this.headers = headers;
    }

    public static Request prepareRequest(InputStream in) {
        Request request;
        String method = null;
        String path = null;
        String protocol = null;
        String queryString = null;
        Map<String, String> headers = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            final String requestLine = reader.readLine();
            final String[] parts = requestLine.split(" ");

            if (parts.length != 3) {
                //just close socket
                throw new IllegalArgumentException("Invalid request format");
            }
            method = parts[0];
            path = prepareFoPathParam(parts[1]);
            protocol = parts[2];
            queryString = prepareQueryParams(parts[1]);
            String headerLine;

            while (!(headerLine = reader.readLine()).equals("")) {
                int i = headerLine.indexOf(":");
                String headerName = headerLine.substring(0, i);
                String headerValue = headerLine.substring(i + 2);
                headers.put(headerName, headerValue);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return new Request(method, path, queryString, protocol, headers, parseQueryString(queryString, path));
    }

    private static String prepareFoPathParam(String path) {
        if (path.contains("?")) {
            return path.substring(0, path.indexOf("?")).replace("/", "");
        }
        return path;
    }

    private static String prepareQueryParams(String path) {
        if (path.contains("?")) {
            return path.substring(path.indexOf("?") + 1);
        }
        return path;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getProtocol() {
        return protocol;
    }

    public static Map<String, String> parseQueryString(String queryString, String path) {
        Map<String, String> queryParams = new HashMap<>();
        //if (getQueryString() != null && !getQueryString().isEmpty()) {
            for (NameValuePair param : URLEncodedUtils.parse(path, StandardCharsets.UTF_8)) {
                queryParams.put(param.getName(), param.getValue());
            }
        //}
        return queryParams;
    }
}
