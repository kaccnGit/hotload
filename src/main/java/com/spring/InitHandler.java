package com.spring;

import com.config.MyConfig;
import com.factory.ClassElement;
import com.factory.ClassFactory;
import com.util.FileTool;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

/**
 * @author: kaccn
 * @description: 容器启动时，初始化工厂
 * @create: 2020-08-25 10:59
 **/
@Component
public class InitHandler implements ApplicationContextAware {

    /**
     * 初始化工厂，将需要加载的类及路径包装存入工厂的自定义类加载池
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            //将目标类的.class文件读取并转移至特定目录，如myclasses/下，方便后续读取、解析、转换
            InputStream input = this.getClass().getClassLoader().getResourceAsStream(MyConfig.class.getName().replace(".", "/") + ".class");
            byte[] bytes = new byte[input.available()];
            input.read(bytes);
            String path = "myclasses/";
            FileTool.write(path + MyConfig.class.getName().replace(".", File.separator) + ".class", bytes, true, true);

            //将目标类的反射class对象放入自定义类加载池
            ClassFactory.getInstance().addClass(MyConfig.class.getName(), new ClassElement(path, MyConfig.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

