package org.hush.plugin.mojo.exceptions;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-11 10:18
 **/
public class DockerMojoException extends Exception {

    private String code;
    private String message;

    public DockerMojoException(String code, String message){
        super();
        this.code = code;
        this.message = message;
    }

    public DockerMojoException(String message){
        super();
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
