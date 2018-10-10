package org.hush.client.common;

import com.alibaba.fastjson.JSONObject;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-09 11:52
 **/
public class BuildImage {

    private String dockerFilePath;
    private String tag;
    private String extrahosts;
    private String remote;
    private String q;//阻止详细输出,默认为false;
    private String nocache;//Do not use the cache when building the image.默认为false;
    private JSONObject buildargs = new JSONObject(); //buildargs={"FOO":"bar"}  args parameters
    private JSONObject headers = new JSONObject();

    public BuildImage(BuildImage.Builder builder){
        this.dockerFilePath = builder.dockerFilePath;
        this.tag = builder.tag;
        this.extrahosts = builder.extrahosts;
        this.remote = builder.remote;
        this.q = builder.q;
        this.nocache = builder.nocache;
        this.buildargs = builder.buildargs;
        this.headers = builder.headers;
    }

    public static class Builder{
        String dockerFilePath;
        String tag;
        String extrahosts;
        String remote;
        String q;//阻止详细输出,默认为false;
        String nocache;//Do not use the cache when building the image.默认为false;
        JSONObject buildargs = new JSONObject(); //buildargs={"FOO":"bar"}  args parameters

        JSONObject headers = new JSONObject();

        public Builder addDockerFilePath(String path){
            this.dockerFilePath = path;
            return this;
        }

        public Builder addDockerTag(String tag){
            this.tag = tag;
            return this;
        }

        public Builder addDockerExtrahosts(String extrahosts){
            this.extrahosts = extrahosts;
            return this;
        }

        public Builder addDockerRemoteUri(String remote){
            this.remote = remote;
            return this;
        }

        public Builder addDockerQ(boolean flag){
            this.q = String.valueOf(flag);
            return this;
        }

        public Builder addDockerNoCache(boolean flag){
            this.nocache = String.valueOf(flag);
            return this;
        }

        public Builder addBuildarg(String key, String value){
            buildargs.put(key, value);
            return this;
        }

        public Builder addHeader(String key, Object value){
            headers.put(key, value);
            return this;
        }

        public BuildImage build(){
            return new BuildImage(this);
        }

    }

    public static class LoginInfo{
        String url;
        String username;
        String password;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }

    public String getDockerFilePath() {
        return dockerFilePath;
    }

    public void setDockerFilePath(String dockerFilePath) {
        this.dockerFilePath = dockerFilePath;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getExtrahosts() {
        return extrahosts;
    }

    public void setExtrahosts(String extrahosts) {
        this.extrahosts = extrahosts;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getNocache() {
        return nocache;
    }

    public void setNocache(String nocache) {
        this.nocache = nocache;
    }

    public JSONObject getBuildargs() {
        return buildargs;
    }

    public void setBuildargs(JSONObject buildargs) {
        this.buildargs = buildargs;
    }

    public JSONObject getHeaders() {
        return headers;
    }

    public void setHeaders(JSONObject headers) {
        this.headers = headers;
    }
}
