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

    @Parameter
    protected Map buildArgs;

    @Parameter
    protected Map labels;

    @Parameter
    protected String networkmode;

    @Parameter
    protected Integer cpushares;

    @Parameter
    protected Integer memswap;

    @Parameter
    protected Integer cpusetcpus;

    @Parameter
    protected Integer cpuperiod;

    @Parameter
    protected Integer cpuquota;

    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    protected MavenSession session;


    public DockerClient.BuildParam[] getParams(){
        List<DockerClient.BuildParam> buildParamList = new ArrayList();
        if (labels != null && labels.size() != 0){
            try {
                buildParamList.add(new DockerClient.BuildParam("labels", StringUtils.encodeBuildParams(labels)));
            } catch (Exception e) {
                //
            }
        }

        if (!StringUtils.isEmpty(networkmode)){
            try {
                buildParamList.add(new DockerClient.BuildParam("networkmode", StringUtils.encodeBuildParams(networkmode)));
            } catch (Exception e) {
                //
            }
        }

        if (cpushares != null){
            try {
                buildParamList.add(new DockerClient.BuildParam("cpushares", StringUtils.encodeBuildParams(cpushares)));
            } catch (Exception e) {
                //
            }
        }

        if (memswap != null){
            try {
                buildParamList.add(new DockerClient.BuildParam("memswap", StringUtils.encodeBuildParams(memswap)));
            } catch (Exception e) {
                //
            }
        }

        if (cpusetcpus != null){
            try {
                buildParamList.add(new DockerClient.BuildParam("cpusetcpus", StringUtils.encodeBuildParams(cpusetcpus)));
            } catch (Exception e) {
                //
            }
        }

        if (cpuperiod != null){
            try {
                buildParamList.add(new DockerClient.BuildParam("cpuperiod", StringUtils.encodeBuildParams(cpuperiod)));
            } catch (Exception e) {
                //
            }
        }

        if (cpuquota != null){
            try {
                buildParamList.add(new DockerClient.BuildParam("cpuquota", StringUtils.encodeBuildParams(cpuquota)));
            } catch (Exception e) {
                //
            }
        }

        if (buildArgs != null) {
            if (buildArgs.size() != 0) {
                try {
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

    protected List<RegistryAuth> setRegistryAuth() {
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
            registryAuth =RegistryAuth.create(userName,passWord,email,serviceAddress,identitytoken,auth);
            lists.add(registryAuth);
        }

        return lists;
    }

    protected DockerClient getDockerClient(){
        try {
            if (!StringUtils.isEmpty(dockerHost)) {
                dockerClient = DefaultDockerClient.fromEnv().uri(dockerHost).build();
            }else{
                dockerClient = DefaultDockerClient.fromEnv().build();
            }
        } catch (DockerCertificateException e) {
            e.printStackTrace();
        }
        return dockerClient;
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
            getDockerClient().build(contextPath, imagesName, new LoggingProgressHandler(getLog(), true), buildParams);
        } catch (DockerException e) {
            logger.error("docker build error", e);
        } catch (InterruptedException e) {
            logger.error("docker build error", e);
        } catch (IOException e) {
            logger.error("docker build error", e);
        }
    }

    public void pushImage(final RegistryAuth auth) throws DockerMojoException {
        try {
            getDockerClient().push(getImageName(), new LoggingProgressHandler(getLog(), true), auth);
        } catch (DockerException e) {
            throw new DockerMojoException("push image faild");
        } catch (InterruptedException e) {
            logger.error("push images iterrupted", e);
        }

    }

}
