package org.hush.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.hush.client.docker.Version;
import org.hush.client.exceptions.ParseJsonException;
import org.hush.client.exceptions.ResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-08 22:07
 **/
public class DockerFactory {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JSONObject getInfo(Request request) throws ParseJsonException {
        Version version = new Version();
        JSONObject jsonObject = version.getJsonObject(request);
        return jsonObject;
    }


}
