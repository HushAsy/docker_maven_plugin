package org.hush.plugin.mojo;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.hush.plugin.mojo.utils.StringUtils;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-10 14:57
 **/
@Mojo(name = "build",
      defaultPhase = LifecyclePhase.PACKAGE,
      requiresProject = true,
      threadSafe = true)
public class BuildMojo extends AbstractForMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getParams();
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
