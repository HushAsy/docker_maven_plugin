package org.hush.plugin.mojo;

import com.spotify.docker.client.messages.RegistryAuth;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.hush.plugin.mojo.exceptions.DockerMojoException;

import java.util.List;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-11 16:58
 **/
@Mojo(name = "push",
      defaultPhase = LifecyclePhase.DEPLOY,
      requiresProject = true,
      threadSafe = true)
public class PushMojo extends AbstractForMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        List<RegistryAuth> registryAuths = setRegistryAuth();
        for (RegistryAuth registryAuth : registryAuths){
            try {
                pushImage(registryAuth);
            } catch (DockerMojoException e) {
                logger.error("push image failes", e);
            }
        }
    }


}
