package com.simple.generator.util;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

/**
 * @author: Jianfeng.Hu
 * @date: 2017/11/3
 */
public interface FullyJavaTypeUtil {

    FullyQualifiedJavaType stringType = new FullyQualifiedJavaType("String");
    FullyQualifiedJavaType stringutils = new FullyQualifiedJavaType("org.apache.commons.lang3.StringUtils");

    String _Getter  = "@Getter";
    FullyQualifiedJavaType getter = new FullyQualifiedJavaType("lombok.Getter");

    String _Setter  = "@Setter";
    FullyQualifiedJavaType setter = new FullyQualifiedJavaType("lombok.Setter");

    String _Tosirng  = "@ToString";
    FullyQualifiedJavaType tostring = new FullyQualifiedJavaType("lombok.ToString");

    String _Slf4j  = "@Slf4j";
    FullyQualifiedJavaType slf4j = new FullyQualifiedJavaType("lombok.extern.slf4j.Slf4j");

    String _AllArgsConstructor  = "@AllArgsConstructor";
    FullyQualifiedJavaType allargsconstructor = new FullyQualifiedJavaType("lombok.AllArgsConstructor");

    FullyQualifiedJavaType arrays = new FullyQualifiedJavaType("java.util.Arrays");
    FullyQualifiedJavaType optional = new FullyQualifiedJavaType("java.util.Optional");
    FullyQualifiedJavaType listType = new FullyQualifiedJavaType("java.util.List");

    String _Autowired  = "@Autowired";
    FullyQualifiedJavaType autowired = new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");

    String _RestController  = "@RestController";
    FullyQualifiedJavaType restController = new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController");

    String _ApiModelProperty  = "@ApiModelProperty(\"{0}\")";
    FullyQualifiedJavaType apiModelProperty = new FullyQualifiedJavaType("io.swagger.annotations.ApiModelProperty;");

    String _ApiModel  = "@ApiModel";
    FullyQualifiedJavaType apiModel = new FullyQualifiedJavaType("io.swagger.annotations.ApiModel;");


}
