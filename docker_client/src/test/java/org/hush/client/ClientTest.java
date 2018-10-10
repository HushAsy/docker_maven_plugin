package org.hush.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import okhttp3.*;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.hush.client.common.BuildImage;
import org.hush.client.docker.Auth;
import org.hush.client.exceptions.ParseJsonException;
import org.hush.client.utils.LoggingProgressHandler;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-08 22:11
 **/
public class ClientTest {
    private static String url = "http://localhost:2375";
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/x-tar");
    private DockerFactory dockerFactory = new DockerFactory();
    public static DockerClient docker = null;

    static {
        try {
            docker = DefaultDockerClient.fromEnv().build();
        } catch (DockerCertificateException e) {
            e.printStackTrace();
        }
    }


//    @Test
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

//    @Test
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

//    @Test
    public void testPath() throws Exception {
        List<DockerClient.BuildParam> buildParamList = new ArrayList();
        Map<String, Object> buildArgs = new HashMap();
        buildArgs.put("JAR_FILE", "hello/dubbo_consumer.jar");
        buildParamList.add(new DockerClient.BuildParam("buildargs", encodeBuildParams(buildArgs)));
        DockerClient.BuildParam buildParams[] = buildParamList.toArray(new DockerClient.BuildParam[buildParamList.size()]);
        URI url = this.getClass().getResource("/").toURI();
        System.out.println(url.getPath());
        Path path = Paths.get(url);

        docker.build(path, "test:1.0.0",new LoggingProgressHandler(getLog(null), true), buildParams);
    }

    private String encodeBuildParams(Object obj) throws Exception {
        String encodeStr = null;
        try {
            encodeStr = URLEncoder.encode(JSON.toJSONString(obj), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new Exception("could not encode param!");
        }
        return encodeStr;
    }

//    @Test
    public void testPath2() throws URISyntaxException, MalformedURLException {
        URI uri1 = this.getClass().getResource("").toURI();
        System.out.println(uri1);
        URI url2 = new URL("File:/E:/2018/pro/java/docker_maven_plugin/").toURI();
        Path path1 = Paths.get(uri1);
        Path path2 = Paths.get(url2);
        Path path01_to_path02 = path1.relativize(path2);
        System.out.println(path01_to_path02);
    }

    public Request createBuildImageRequest(){
        String client = ClientTest.class.getResource("/").getPath();
        BuildImage buildImage = new BuildImage.Builder().addBuildarg("JAR_FILE", "hello/dubbo_consumer.jar").addDockerTag("1.0.0").addDockerFilePath(client+"Dockerfile").build();
        String jsonObject = JSON.toJSONString(buildImage);
        RequestBody requestBody = RequestBody.create(CONTENT_TYPE, jsonObject);
        Request request = new Request.Builder().addHeader("Content-type", "application/x-tar").url("http://127.0.0.1:2375/build").post(requestBody).build();
        return request;
    }

    public Log getLog(Log log) {
        if (log == null) {
            log = new SystemStreamLog();
        }
        return log;
    }

}
