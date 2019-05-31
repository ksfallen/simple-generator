package com.simple.generator.util;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/11/3
 */
public interface FullyJavaTypeUtil {

    FullyQualifiedJavaType stringType = new FullyQualifiedJavaType("String");
    FullyQualifiedJavaType stringutils = new FullyQualifiedJavaType("org.apache.commons.lang3.StringUtils");

    FullyQualifiedJavaType getter = new FullyQualifiedJavaType("lombok.Getter");
    FullyQualifiedJavaType setter = new FullyQualifiedJavaType("lombok.Setter");
    FullyQualifiedJavaType tostring = new FullyQualifiedJavaType("lombok.Tostring");
    FullyQualifiedJavaType slf4j = new FullyQualifiedJavaType("lombok.extern.slf4j.Slf4j");
    FullyQualifiedJavaType allargsconstructor = new FullyQualifiedJavaType("lombok.AllArgsConstructor");

    FullyQualifiedJavaType arrays = new FullyQualifiedJavaType("java.util.Arrays");
    FullyQualifiedJavaType optional = new FullyQualifiedJavaType("java.util.Optional");
    FullyQualifiedJavaType listType = new FullyQualifiedJavaType("java.util.List");

    FullyQualifiedJavaType autowired = new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");
    FullyQualifiedJavaType restController = new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController");



    String _Getter  = "@Getter";
    String _Setter  = "@Setter";
    String _Tosirng  = "@ToString";
    String _Slf4j  = "@Slf4j";
    String _Autowired  = "@Autowired";
    String _RestController  = "@RestController";
    String _AllArgsConstructor  = "@AllArgsConstructor";
}
