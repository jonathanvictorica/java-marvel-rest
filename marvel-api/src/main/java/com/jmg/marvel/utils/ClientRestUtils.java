package com.jmg.marvel.utils;

public class ClientRestUtils {

    public static String getPathUrl(String endpoint, Object... parameters) {
        for (Object parameter : parameters) {
            endpoint = endpoint.replaceFirst(":param", parameter.toString());
        }
        return endpoint;
    }



}
