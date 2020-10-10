package com.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: kaccn
 * @description: 文件处理辅助类
 * @create: 2020-10-10 14:17
 **/
public class FileTool {

    /**
     * 删除文件
     *
     * @param fileName 待删除的完整文件名
     * @return
     */
    public static boolean delete(String fileName) {
        boolean result = false;
        File f = new File(fileName);
        if (f.exists()) {
            result = f.delete();

        } else {
            result = true;
        }
        return result;
    }

    /**
     * 写文件
     *
     * @param fileName      完整文件名(类似：/usr/a/b/c/d.txt)
     * @param contentBytes  文件内容的字节数组
     * @param autoCreateDir 目录不存在时，是否自动创建(多级)目录
     * @param autoOverwrite 目标文件存在时，是否自动覆盖
     * @return
     * @throws IOException
     */
    public static boolean write(String fileName, byte[] contentBytes,
                                boolean autoCreateDir, boolean autoOverwrite) throws IOException {
        boolean result = false;
        if (autoCreateDir) {
            createDirs(fileName);
        }
        if (autoOverwrite) {
            delete(fileName);
        }
        File f = new File(fileName);
        FileOutputStream fs = new FileOutputStream(f);
        fs.write(contentBytes);
        fs.flush();
        fs.close();
        result = true;
        return result;
    }

    /**
     * 创建(多级)目录
     *
     * @param filePath 完整的文件名(类似：/usr/a/b/c/d.xml)
     */
    public static void createDirs(String filePath) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

}