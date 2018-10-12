package org.hush.plugin.mojo.common;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-11 10:23
 **/
public class Constants {

    public static enum Exception{
        AuthException("");

        private String message;

        Exception(String message){
            this.message = message;
        }
    }

}
