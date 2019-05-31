package com.simple.generator.tk;

import java.util.List;

import org.mybatis.generator.api.ProgressCallback;

import tk.mybatis.mapper.generator.TkMyBatis3Impl;

/**
 * 可以通过MBG1.3.4+版本提供的table元素的mapperName属性设置统一的名称，使用{0}作为实体类名的占位符。
 * <p>
 * 用法：
 * context id="Mysql" targetRuntime="com.simple.generator.tk.SimpleTkMyBatis3Impl" defaultModelType="flat"
 */
public class SimpleTkMyBatis3Impl extends TkMyBatis3Impl {

    /**
     * 是否创建 ModelExample 类
     * true 创建
     * false 不创建
     */
    private boolean exampleStatementEnabled = false;

    @Override
    protected void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback) {
        this.tableConfiguration.setCountByExampleStatementEnabled(exampleStatementEnabled);
        this.tableConfiguration.setSelectByExampleStatementEnabled(exampleStatementEnabled);
        this.tableConfiguration.setDeleteByExampleStatementEnabled(exampleStatementEnabled);
        this.tableConfiguration.setUpdateByExampleStatementEnabled(exampleStatementEnabled);

        super.calculateJavaModelGenerators(warnings, progressCallback);
    }
}
