package org.hush.plugin.mojo;

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

    @Parameter
    private String dockerHost;

    @Parameter
    private File serverFile;




    public void execute() throws MojoExecutionException, MojoFailureException {
    }
}
