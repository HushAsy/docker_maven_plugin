package org.hush.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-09 09:59
 **/
public abstract class AbstractHttpClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OkHttpClient okHttpClient = new OkHttpClient();

    public Response getResponse(Request request){
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            logger.error("http call error", e);
        }
        return response;
    }



}
