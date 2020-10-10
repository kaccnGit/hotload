package com.controller;

import com.config.MyConfig;
import com.factory.ClassElement;
import com.factory.ClassFactory;
import com.util.ConfigTransformer;
import com.util.FileTool;
import com.util.MyClassLoader;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author: kaccn
 * @description: AsmController
 * @create: 2020-10-10 14:24
 **/
@RestController
@RequestMapping("asm")
public class AsmController {

    /**
     * 获取业务配置信息，渠道信息
     *
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    @RequestMapping("/getconfig")
    public String getConfig() throws IllegalAccessException, NoSuchFieldException {
        //从类加载池中获取配置类MyConfig的Class对象
        ClassElement classElement = ClassFactory.getInstance().getConfig(MyConfig.class.getName());
        Class c = classElement.getClzzz();
        //通过反射获取channel属性值
        Field f = c.getField("channel");
        String channelValue = (String) f.get(null);
        return "渠道信息为：" + channelValue;
    }

    /**
     * 执行配置类转换
     *
     * @param config
     * @throws IOException
     */
    @RequestMapping("/transform")
    public void transForm(@RequestParam String config) throws IOException {
        //第一步，构建类分析器ClassReader
        ClassElement classElement = ClassFactory.getInstance().getConfig(MyConfig.class.getName());

        FileInputStream io = new FileInputStream(classElement.getPath() + MyConfig.class.getName().replace(".", File.separator) + ".class");
        ClassReader cr = new ClassReader(io);

        //第二步，构建树API ClassNode
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);

        //第三步，进行类转换，这是最关键的一步，将静态属性channel的值替换为请示值
        ConfigTransformer at = new ConfigTransformer(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
                "channel", "Ljava/lang/String;", config);
        at.transform(cn);

        //第四步，将转换成功的类生成byte流
        ClassWriter cw = new ClassWriter(0);
        cn.accept(cw);
        byte[] toByte = cw.toByteArray();

        //第五步，生成class文件，转换完成
        FileTool.write(classElement.getPath() + MyConfig.class.getName().replace(".", File.separator) + ".class", toByte, true, true);
    }

    /**
     * 加载配置类
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/loadclass")
    public String loadClass() throws Exception {
        //自定义类加载器读取并加载class文件，MyConfig.class
        ClassElement classElement = ClassFactory.getInstance().getConfig(MyConfig.class.getName());
        MyClassLoader loader = new MyClassLoader(null, "myloader", classElement.getPath());
        Class c = loader.loadClass(MyConfig.class.getName());

        //类加载成功后，存放入类加载池
        ClassFactory configFactory = ClassFactory.getInstance();
        configFactory.removeClass(MyConfig.class.getName());
        configFactory.addClass(MyConfig.class.getName(), new ClassElement(classElement.getPath(), c));

        return "重新加载类" + c.getName() + "成功";
    }

}
