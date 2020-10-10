package com.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: kaccn
 * @description: class工厂
 * @create: 2020-10-08 01:12
 **/
public class ClassFactory {

    /**
     * 单例下维护一个自定义类加载池
     */
    private Map<String, ClassElement> classMap = new ConcurrentHashMap<>();

    /**
     * 获取对应的Class对象
     * @param name
     * @return
     */
    public ClassElement getConfig(String name) {
        return classMap.get(name);
    }

    /**
     * 添加Class元素
     * @param name
     * @param classElement
     * @return
     */
    public boolean addClass(String name, ClassElement classElement) {
        classMap.put(name, classElement);
        return true;
    }

    /**
     * 移除Class元素
     * @param name
     * @return
     */
    public boolean removeClass(String name) {
        classMap.remove(name);
        return true;
    }

    private ClassFactory() {
    }

    public static ClassFactory getInstance() {
        return SingletonEnum.INSTANCE.getInstnce();
    }

    /**
     * 枚举实现单例
     */
    public enum SingletonEnum {
        INSTANCE;

        private ClassFactory classFactory;

        SingletonEnum() {
            classFactory = new ClassFactory();
        }

        public ClassFactory getInstnce() {
            return classFactory;
        }
    }
}
