package com.github.drango.microservices.gateway.common;

import java.util.HashSet;
import java.util.Set;

public class UrlWhiteList {
    private static final Set<String> GLOBAL_FILTER_URI_WHITE_LIST = new HashSet<>();

    private static final Set<String> GET_FILTER_URI_WHITE_LIST = new HashSet<>();
    private static final Set<String> POST_FILTER_URI_WHITE_LIST = new HashSet<>();
    private static final Set<String> PUT_FILTER_URI_WHITE_LIST = new HashSet<>();
    private static final Set<String> DELETE_FILTER_URI_WHITE_LIST = new HashSet<>();

    static {
        GLOBAL_FILTER_URI_WHITE_LIST.add("/favicon.ico");
        GLOBAL_FILTER_URI_WHITE_LIST.add("/actuator/health");

        GLOBAL_FILTER_URI_WHITE_LIST.add("/api/user/login");
    }

    static {
        POST_FILTER_URI_WHITE_LIST.add("/api/user");
    }

    public static boolean checkWhiteList(String uri, String methodValue) {
        if (UrlWhiteList.GLOBAL_FILTER_URI_WHITE_LIST.contains(uri)) {
            return true;
        }

        boolean getWhiteList = UrlWhiteList.GET_FILTER_URI_WHITE_LIST.contains(uri) && methodValue.equals("GET");
        boolean postWhiteList =  UrlWhiteList.POST_FILTER_URI_WHITE_LIST.contains(uri) && methodValue.equals("POST");
        boolean putWhiteList =  UrlWhiteList.PUT_FILTER_URI_WHITE_LIST.contains(uri) && methodValue.equals("PUT");
        boolean deleteWhiteList =  UrlWhiteList.DELETE_FILTER_URI_WHITE_LIST.contains(uri) && methodValue.equals("DELETE");
        return getWhiteList || postWhiteList || putWhiteList || deleteWhiteList;
    }
}
