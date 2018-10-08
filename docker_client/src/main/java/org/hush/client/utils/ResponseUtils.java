package org.hush.client.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.hush.client.exceptions.ParseJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-08 23:24
 **/
public class ResponseUtils {
    private final static Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

    public static JSONObject parseBodyToJsonObject(Response response) throws ParseJsonException {
        JSONObject jsonObject = null;
        ResponseBody body = response.body();
        String result = null;
        try {
            result = body.string();
        } catch (IOException e) {
            logger.error("response body", e);
        }
        try {
            jsonObject = JSON.parseObject(result);
        }catch (Exception p){
            throw new ParseJsonException("parse json error");
        }
        jsonObject.put("status", response.code());
        return jsonObject;
    }

}
