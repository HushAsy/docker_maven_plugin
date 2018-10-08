package org.hush.client.utils;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-08 21:58
 **/
public class UrlHolder {
    private static String url = null;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        UrlHolder.url = url;
    }
}
