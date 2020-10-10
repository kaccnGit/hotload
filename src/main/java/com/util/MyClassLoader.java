package com.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author: kaccn
 * @description: 自定义类加载器
 * @create: 2020-10-10 14:17
 **/
public class MyClassLoader extends ClassLoader {

    private String path;//类加载类的路径
    private String name;//类加载器的名称

    /**
     * 让系统类加载器成为该类的父加载器
     *
     * @param name
     * @param path
     */
    public MyClassLoader(String name, String path) {
        super();
        this.name = name;
        this.path = path;
    }

    /**
     * 指定父加载器
     *
     * @param parent
     * @param name
     * @param path
     */
    public MyClassLoader(ClassLoader parent, String name, String path) {
        super(parent);
        this.name = name;
        this.path = path;
    }

    /**
     * 重写findClass方法，父加载器找不到class文件时，通过该方法寻找文件并转化成流
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] date = readToByte(name);
        return this.defineClass(name, date, 0, date.length);
    }

    /**
     * .class文件转化为byte数组
     *
     * @param name
     * @return
     */
    private byte[] readToByte(String name) {
        InputStream is = null;
        byte[] returnData = null;

        name = name.replaceAll("\\.", "/");

        String filePath = this.path + name + ".class";
        File file = new File(filePath);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            is = new FileInputStream(file);
            int tmp = 0;
            while ((tmp = is.read()) != -1) {
                os.write(tmp);
            }
            returnData = os.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnData;
    }

}
