package org.hush.plugin.mojo;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;
import org.hush.plugin.mojo.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-10 15:02
 **/
public abstract class AbstractForMojo extends AbstractMojo {
    protected  DockerClient dockerClient;
    {
        try {
            dockerClient = DefaultDockerClient.fromEnv().build();
        } catch (DockerCertificateException e) {
            getLog().error(e);
        }
    }

    @Parameter
    protected String repository;

    @Parameter
    protected String tag;

    @Parameter
    protected String dockerHost;

    @Parameter
    protected String dockerFilePath;

    @Parameter
    protected String userName;

    @Parameter
    protected String passWord;

    @Parameter
    protected String email;

    @Parameter
    protected String serviceAddress;

    /**
     * <buildArgs>
     *      <key1>value1</key1>
     *      <key2>value2</key2>
     * </buildArgs>
     */
    @Parameter
    private Map buildArgs;

    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    protected MavenSession session;

    public DockerClient.BuildParam[] getParams(){
        List<DockerClient.BuildParam> buildParamList = new ArrayList();
        if (!StringUtils.isEmpty(tag)){
            getLog().info("tag:"+tag);
        }

        if (!StringUtils.isEmpty(repository)){
            getLog().info("repository:"+repository);
        }

        if (!StringUtils.isEmpty(dockerFilePath)){
            getLog().info("dockerFilePath:"+dockerFilePath);
        }
        if (buildArgs != null) {
            if (buildArgs.size() != 0) {
                try {
                    getLog().info(buildArgs.toString());
                    buildParamList.add(new DockerClient.BuildParam("buildargs", StringUtils.encodeBuildParams(buildArgs)));
                } catch (Exception e) {
                    getLog().error(e);
                }
            }
        }
        if (session != null){
            Settings settings = session.getSettings();
            if (settings != null){
                Server server = settings.getServer("registry.cn-hangzhou.aliyuncs.com");
                getLog().info(server.getPassword()+server.getUsername());
            }

        }
//        getLog().info(session.getSettings().getServer("docker-hub").toString());

        DockerClient.BuildParam buildParams[] = buildParamList.toArray(new DockerClient.BuildParam[buildParamList.size()]);
        return buildParams;
    }

        public void xxx(){
//        List<DockerClient.BuildParam> buildParamList = new ArrayList();
//        Map<String, Object> buildArgs = new HashMap();
//        buildArgs.put("JAR_FILE", "hello/dubbo_consumer.jar");
//        buildParamList.add(new DockerClient.BuildParam("buildargs", StringUtils.encodeBuildParams(buildArgs)));
//        DockerClient.BuildParam buildParams[] = buildParamList.toArray(new DockerClient.BuildParam[buildParamList.size()]);
//        URI url = this.getClass().getResource("/").toURI();
//        System.out.println(url.getPath());
//        Path path = Paths.get(url);
//
//        dockerClient.build(path, "test:1.0.0",new LoggingProgressHandler(getLog(null), true), buildParams);
    }
}
