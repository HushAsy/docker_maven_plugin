package org.hush.plugin.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

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
