package com.factory;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: kaccn
 * @description: 类工厂元素，包含class文件路径及信息
 * @create: 2020-10-09 18:03
 **/
@Data
@AllArgsConstructor
public class ClassElement {

    /**
     * 类文件地址
     */
    private String path;

    /**
     * 类的class对象
     */
    private Class clzzz;

}
