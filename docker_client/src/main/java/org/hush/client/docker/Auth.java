package org.hush.client.docker;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import okhttp3.Request;
import org.hush.client.AbstractHttpClient;
import org.hush.client.exceptions.ParseJsonException;

import java.io.Serializable;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-08 23:21
 **/
public class Auth extends AbstractHttpClient implements Serializable {
    @JSONField(serialize = true)
    private String userName;
    @JSONField(serialize = true)
    private String passWord;
    @JSONField(serialize = true)
    private String email;
    @JSONField(serialize = true)
    private String serveraddress;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServeraddress() {
        return serveraddress;
    }

    public void setServeraddress(String serveraddress) {
        this.serveraddress = serveraddress;
    }


}
