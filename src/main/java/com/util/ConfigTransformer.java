package com.util;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

/**
 * @author: kaccn
 * @description: 类转换器，用于修改.class的类信息
 * @create: 2020-09-30 10:06
 **/
public class ConfigTransformer {

    private int fieldAccess;
    private String fieldName;
    private String fieldDesc;
    private String fieldValue;

    /**
     * 构造器
     *
     * @param fieldAccess 属性修饰符
     * @param fieldName   属性名
     * @param fieldDesc   属性类型
     * @param fieldValue  属性值
     */
    public ConfigTransformer(int fieldAccess, String fieldName, String fieldDesc, String fieldValue) {
        this.fieldAccess = fieldAccess;
        this.fieldName = fieldName;
        this.fieldDesc = fieldDesc;
        this.fieldValue = fieldValue;
    }

    /**
     * 执行类转换
     *
     * @param cn
     */
    public void transform(ClassNode cn) {
        //删除原属性
        cn.fields.removeIf(fieldNode -> fieldNode.name.equals(fieldName));
        //添加属性，并赋新值
        cn.fields.add(new FieldNode(fieldAccess, fieldName, fieldDesc, null, fieldValue));
    }
}
