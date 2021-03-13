package ru.home.jspr.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String method;
    private String path;
    private String queryString;
    private String protocol;

    public Request(String method, String path, String protocol) {
        this.method = method;
        this.path = prepareFoPathParam(path);
        this.queryString = prepareQueryParams(path);
        this.protocol = protocol;

    }
    private String prepareFoPathParam(String path){
        if(path.contains("?")){
            return path.substring(0, path.indexOf("?")).replace("/", "");
        }
        return path;
    }

    private String prepareQueryParams(String path){
        if(path.contains("?")){
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

    public Map<String, String> parseQueryString (){
        Map<String, String> queryParams = new HashMap<>();
        if (getQueryString() != null && !getQueryString().isEmpty()) {
            for (NameValuePair param : URLEncodedUtils.parse(path, StandardCharsets.UTF_8)) {
                queryParams.put(param.getName(), param.getValue());
            }
        }
        return queryParams;
    }
}
