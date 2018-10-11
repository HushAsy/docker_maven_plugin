package org.hush.plugin.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import java.io.File;
import java.util.List;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-08 16:59
 **/
@Mojo(name = "sayHi")
public class TestMojo extends AbstractForMojo {

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
        Settings setting = session.getSettings();
        List<Server> list = setting.getServers();
        org.codehaus.plexus.util.xml.Xpp3Dom dom = (Xpp3Dom) list.get(0).getConfiguration();
        System.out.println(dom.getChild("email").getValue());
    }
}
