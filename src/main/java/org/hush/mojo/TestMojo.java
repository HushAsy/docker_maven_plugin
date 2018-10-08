package org.hush.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-08 16:59
 **/
@Mojo(name = "sayHi")
public class TestMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.version}")
    private String tag;

    @Parameter
    private String registryUrl;

    @Parameter
    private String userName;

    @Parameter
    private String passWord;

    @Parameter(defaultValue = "127.0.0.1:2375")
    private String dockerHost;

    @Parameter
    private File serverFile;

    @Parameter(defaultValue = "${settings.servers}")
    private String servers;




    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(tag+"---"+registryUrl+"---"+userName+"---"+passWord+"---"+dockerHost+"---"+servers);
    }
}
