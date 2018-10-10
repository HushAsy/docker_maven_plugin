package org.hush;

import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingRequest;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.hush.plugin.mojo.BuildMojo;
import org.hush.plugin.mojo.TestMojo;

import java.io.File;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-08 18:15
 **/
public class MyTestMojo extends AbstractMojoTestCase {

    public void testMojoGoal() throws Exception {
        File testPom = new File( getBasedir(),
          "src/test/resources/pom.xml" );

//        MavenExecutionRequest executionRequest = new DefaultMavenExecutionRequest();
//        ProjectBuildingRequest configuration = executionRequest.getProjectBuildingRequest()
//              .setRepositorySession(new DefaultRepositorySystemSession());
//        MavenProject project = rule.lookup(ProjectBuilder.class).build(pom, configuration).getProject();

        BuildMojo mojo = (BuildMojo) lookupMojo( "build", testPom );
        mojo.execute();
    }
}
