package org.hush.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Request;
import org.hush.client.docker.Auth;
import org.hush.client.exceptions.ParseJsonException;
import org.junit.Test;

import java.io.IOException;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-08 22:11
 **/
public class ClientTest {
    private static String url = "http://localhost:2375";
    private DockerFactory dockerFactory = new DockerFactory();

    @Test
    public void versionTest() throws IOException, ParseJsonException {
//        DockerFactory dockerFactory = new DockerFactory();
        String versionAction = "/info";
        Request request = new Request.Builder().url(url+versionAction).build();
//        JSONObject jsonObject = dockerFactory.getVersion(request);
//        if (jsonObject != null){
//            System.out.println(jsonObject.toJSONString());
//        }
        JSONObject jsonObject = dockerFactory.getInfo(request);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void authTest() throws ParseJsonException {
        Auth auth = new Auth();
        auth.setEmail("270232645@qq.com");
        auth.setPassWord("edas@123");
        auth.setServeraddress("registry.cn-hangzhou.aliyuncs.com");
        auth.setUserName("edas_test1@aliyun-test.com");
        String string = "{\"username\": "+ auth.getUserName() +",\"password\": "+auth.getPassWord() + ",\"email\": "+auth.getEmail()+",\"serveraddress\": "+auth.getServeraddress()+"}";
        String encodeStr = java.util.Base64.getEncoder().encodeToString(string.getBytes());
        Request request = new Request.Builder().url("http://"+auth.getServeraddress()+ "/hush/basecontainer").addHeader("X-Registry-Auth", encodeStr).build();
        JSONObject jsonObject = dockerFactory.getInfo(request);
        System.out.println(jsonObject.toJSONString());
    }
}
