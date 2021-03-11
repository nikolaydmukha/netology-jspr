package ru.home.jspr.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String method;
    private String path;
    private String protocol;

    public Request(String method, String path, String protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> parseQueryString (){
        Map<String, String> queryParams = new HashMap<>();
        if (path != null && !path.isEmpty()) {
            for (NameValuePair param : URLEncodedUtils.parse(path, StandardCharsets.UTF_8)) {
                queryParams.put(param.getName(), param.getValue());
            }
        }
        return queryParams;
    }
}
