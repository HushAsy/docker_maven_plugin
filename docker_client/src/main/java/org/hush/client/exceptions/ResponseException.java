package org.hush.client.exceptions;

import java.io.IOException;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-08 21:31
 **/
public class ResponseException extends IOException {

    public ResponseException(String message){
        super(message);
    }

    public ResponseException(Throwable throwable){
        super();
    }

}
