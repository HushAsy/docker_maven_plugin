package org.hush;

import org.junit.Test;

import java.io.File;

/**
 * @description:
 * @author: hewater
 * @create: 2018-10-11 11:12
 **/
public class TestBuild {

    @Test
    public void testFile(){
        String pathStr = "E:\\2018\\edas\\jar";
        File file = new File(pathStr);
        if (file.isFile()){
            System.out.println(file.getParent());
        }else{
            System.out.println(file.getPath());
        }
    }
}
