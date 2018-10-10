package org.hush.plugin.mojo.utils;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-10 16:28
 **/
public class StringUtils {

    public static String encodeBuildParams(Object obj) throws Exception {
        String encodeStr = null;
        try {
            encodeStr = URLEncoder.encode(JSON.toJSONString(obj), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new Exception("could not encode param!");
        }
        return encodeStr;
    }

    public static boolean isEmpty(String str){
        return str == null ? true : str.trim().length() == 0 ? true : false;
    }
}
