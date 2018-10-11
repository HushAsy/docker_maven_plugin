package org.hush.plugin.mojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.io.Files;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.RegistryAuth;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.hush.plugin.mojo.common.Auth;
import org.hush.plugin.mojo.exceptions.DockerMojoException;
import org.hush.plugin.mojo.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-10 15:02
 **/
public abstract class AbstractForMojo extends AbstractMojo {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected  DockerClient dockerClient;
    {
        try {
            dockerClient = DefaultDockerClient.fromEnv().build();
        } catch (DockerCertificateException e) {
            logger.error("init docker client error", e);
        }
    }

    @Parameter
    protected String repository;

    @Parameter
    protected String tag;

    @Parameter(defaultValue = "false")
    protected boolean nocache;

    @Parameter(defaultValue = "false", property = "dockerfile.useMavenSettingsForAuth")
    protected boolean useMavenSettingsForAuth;

    @Parameter
    protected String dockerHost;

    @Parameter(defaultValue = "${project.basedir}", property = "docker.DockerFile")
    protected File dockerFilePath;

    @Parameter(property = "dockerfile.userName")
    protected String userName;

    @Parameter(property = "dockerfile.passWord")
    protected String passWord;

    @Parameter(property = "dockerfile.email")
    protected String email;

    @Parameter(property = "dockerfile.serviceAddress")
    protected String serviceAddress;

    @Parameter(property = "dockerfile.identitytoken")
    protected String identitytoken;

    @Parameter(property = "dockerfile.auth")
    protected String auth;

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
            logger.info("tag:"+tag);
        }

        if (!StringUtils.isEmpty(repository)){
            logger.info("repository:"+repository);
        }

        if (dockerFilePath != null){
            logger.info("dockerFilePath:"+dockerFilePath);
        }
        if (buildArgs != null) {
            if (buildArgs.size() != 0) {
                try {
                    logger.info(buildArgs.toString());
                    buildParamList.add(new DockerClient.BuildParam("buildargs", StringUtils.encodeBuildParams(buildArgs)));
                } catch (Exception e) {
                    logger.error("encode param error",e);
                }
            }
        }
        DockerClient.BuildParam buildParams[] = buildParamList.toArray(new DockerClient.BuildParam[buildParamList.size()]);
        return buildParams;
    }

    protected String getImageName(){
        StringBuilder stringBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(repository)){
            stringBuilder.append(repository);
        }else {
            stringBuilder.append("none");
        }
        if (!StringUtils.isEmpty(tag)){
            stringBuilder.append(":"+tag);
        }else {
            stringBuilder.append(":none");
        }
        return stringBuilder.toString();
    }

    protected Path getContextPath() throws DockerMojoException {
        File file = dockerFilePath;
        String contextPath = null;
        if (file.isFile()){
            contextPath = file.getParent();
        }
        if (!new File(contextPath, "Dockerfile").exists() ||
                    !new File(contextPath, "dockerfile").exists()){
                throw new DockerMojoException("001", "Dockerfile or dockerfile is not found, please check this file is exist or create it");
        }
        return file.toPath();
    }

    protected List<RegistryAuth> setRegistryAuth(){
        List<RegistryAuth> lists = new ArrayList();
        RegistryAuth registryAuth = null;
        if (useMavenSettingsForAuth == true){
            MavenSession mavenSession = session;
            Settings settings = mavenSession.getSettings();
            List<Server> servers = settings.getServers();
            for (Server server : servers){
                org.codehaus.plexus.util.xml.Xpp3Dom dom = (Xpp3Dom) server.getConfiguration();
                Xpp3Dom xpp3Dom = null;
                String email = null;
                if ((xpp3Dom = dom.getChild("email")) != null){
                    email = xpp3Dom.getValue();
                }
                String serviceAddr = serviceAddress;
                String userName = server.getUsername();
                String passWord = server.getPassword();
                registryAuth =RegistryAuth.create(userName,passWord,email,serviceAddr,identitytoken,auth);
                lists.add(registryAuth);
            }
        }else {
            if (StringUtils.isEmpty(userName)){
                new DockerMojoException("002", "userName not set in maven plugin:<configuration><userName></userName></configuration>");
                return null;
            }
            if (StringUtils.isEmpty(passWord)){
                new DockerMojoException("003", "passWord not set in maven plugin:<configuration><passWord></passWord></configuration>");
                return null;
            }
            if (StringUtils.isEmpty(email)){
                new DockerMojoException("004", "email not set in maven plugin:<configuration><email></email></configuration>");
                return null;
            }
            if (StringUtils.isEmpty(serviceAddress)){
                new DockerMojoException("005", "serviceAddress not set in maven plugin:<configuration><serviceAddress></serviceAddress></configuration>");
                return null;
            }
            registryAuth =RegistryAuth.create(userName,passWord,email,serviceAddress,identitytoken,auth);
            lists.add(registryAuth);
        }

        return lists;
    }

    public void buildImages(){
        String imagesName = getImageName();
        Path contextPath = null;
        try {
            contextPath = getContextPath();
        } catch (DockerMojoException e) {
            logger.error(e.getCode()+":"+e.getMessage(), e);
            return;
        }

        DockerClient.BuildParam[] buildParams = getParams();
        try {
            System.out.println(contextPath);
            dockerClient.build(contextPath, imagesName, new LoggingProgressHandler(getLog(), true), buildParams);
        } catch (DockerException e) {
            logger.error("docker build error", e);
        } catch (InterruptedException e) {
            logger.error("docker build error", e);
        } catch (IOException e) {
            logger.error("docker build error", e);
        }
    }

    public void pushImage(RegistryAuth auth) throws DockerMojoException {
        try {
            dockerClient.auth(auth);
        } catch (DockerException e) {
            logger.error("set auth info error", e);
        } catch (InterruptedException e) {
            logger.error("auth failed",e);
        }
        try {
            dockerClient.push(getImageName(), new LoggingProgressHandler(getLog(), true));
        } catch (DockerException e) {
            throw new DockerMojoException("", "push image faild");
        } catch (InterruptedException e) {
            logger.error("push images iterrupted", e);
        }

    }

}
