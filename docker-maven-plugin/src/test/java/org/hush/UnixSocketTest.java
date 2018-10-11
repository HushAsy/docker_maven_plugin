package org.hush;

import jnr.unixsocket.UnixSocket;
import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;
import org.hush.plugin.mojo.utils.StringUtils;
import org.junit.Test;

import java.io.*;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-08 20:25
 **/
public class UnixSocketTest {

//    @Test
    public void test(){
        System.out.println(StringUtils.isEmpty(""));
        System.out.println(StringUtils.isEmpty(null));
        System.out.println(StringUtils.isEmpty("asdaf"));
    }

//    @Test
    public void unixSocketTest() throws IOException {
        File sockFile = new File("/var/run/docker.sock");
        UnixSocketAddress address = new UnixSocketAddress(sockFile);
        UnixSocketChannel channel = UnixSocketChannel.open(address);
        UnixSocket unixSocket = new UnixSocket(channel);

        // 调用 Docker API
        PrintWriter w = new PrintWriter(unixSocket.getOutputStream());
        w.println("GET /v1.24/containers/json HTTP/1.1");
        w.println("Host: http");
        w.println("Accept: */*");
        w.println("");
        w.flush();
        // 关闭 Output，否则会导致下面的 read 操作一直阻塞
        unixSocket.shutdownOutput();

        // 获取返回结果
        System.out.println("---- Docker Response ----");
        BufferedReader br = new BufferedReader(new InputStreamReader(unixSocket.getInputStream()));
        String line;
        while ((line = br.readLine()) != null){
            System.out.println(line);
        }
        unixSocket.close();

    }
}
