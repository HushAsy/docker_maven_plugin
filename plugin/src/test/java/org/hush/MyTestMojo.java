package org.hush;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
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

        TestMojo mojo = (TestMojo) lookupMojo( "sayHi", testPom );
        mojo.execute();
    }
}
