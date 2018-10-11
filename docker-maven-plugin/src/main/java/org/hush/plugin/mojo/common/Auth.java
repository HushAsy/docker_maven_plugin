package org.hush.plugin.mojo.common;

import com.spotify.docker.client.messages.RegistryAuth;

import javax.annotation.Nullable;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-11 19:17
 **/
public class Auth extends RegistryAuth {

    /**
     * {
     *   "username": "string",
     *   "password": "string",
     *   "email": "string",
     *   "serveraddress": "string"
     * }
     */
    private String username;
    private String password;
    private String email;
    private String serveraddress;
    private String identitytoken;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setServeraddress(String serveraddress) {
        this.serveraddress = serveraddress;
    }

    @Nullable
    @Override
    public String username() {
        return username;
    }

    @Nullable
    @Override
    public String password() {
        return password;
    }

    @Nullable
    @Override
    public String email() {
        return email;
    }

    @Nullable
    @Override
    public String serverAddress() {
        return serveraddress;
    }

    @Nullable
    @Override
    public String identityToken() {
        return identitytoken;
    }



    @Override
    public Builder toBuilder() {
        RegistryAuth registryAuth =RegistryAuth.create("123","123","123","123","123","123");
        return null;
    }
}
