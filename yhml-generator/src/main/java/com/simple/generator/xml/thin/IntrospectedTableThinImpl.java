package com.simple.generator.xml.thin;

import java.util.List;

import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import org.mybatis.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;
import org.mybatis.generator.config.init.ContextConfig;
import org.mybatis.generator.config.init.GlobalConfig;


/**
 * 自定义 sqlstatmentId
 * <context id="Mysql" targetRuntime="tk.mybatis.mapper.generator.TkMyBatis3Impl" defaultModelType="flat" />
 * Date: 2017/5/25
 */
public class IntrospectedTableThinImpl extends IntrospectedTableMyBatis3Impl {

    private GlobalConfig gc;

    public IntrospectedTableThinImpl() {
        super();

        this.gc = ContextConfig.getGlobalConfig();
    }

    /**
     * xml 文件名
     *
     * @return
     */
    @Override
    protected String calculateMyBatis3XmlMapperFileName() {

        StringBuilder sb = new StringBuilder();
        sb.append(fullyQualifiedTable.getDomainObjectName());
        sb.append(gc.getXmlSuffix());
        sb.append(".xml");
        return sb.toString();
    }

    @Override
    public void setMyBatis3JavaMapperType(String mybatis3JavaMapperType) {

        StringBuilder sb = new StringBuilder();
        sb.append(calculateJavaClientInterfacePackage());
        sb.append('.');
        sb.append(fullyQualifiedTable.getDomainObjectName());
        sb.append(gc.getMapperSuffix());

        super.setMyBatis3JavaMapperType(sb.toString());
    }

    @Override
    protected void calculateXmlAttributes() {
        // super.calculateXmlAttributes();
        // setIbatis2SqlMapPackage(calculateSqlMapPackage());
        // setIbatis2SqlMapFileName(calculateIbatis2SqlMapFileName());

        setMyBatis3XmlMapperFileName(calculateMyBatis3XmlMapperFileName());
        setMyBatis3XmlMapperPackage(calculateSqlMapPackage());

        // xml namespace
        // setIbatis2SqlMapNamespace(calculateIbatis2SqlMapNamespace());
        // setMyBatis3FallbackSqlMapNamespace(calculateMyBatis3FallbackSqlMapNamespace());

        setSqlMapFullyQualifiedRuntimeTableName(calculateSqlMapFullyQualifiedRuntimeTableName());
        setSqlMapAliasedFullyQualifiedRuntimeTableName(calculateSqlMapAliasedFullyQualifiedRuntimeTableName());

        setCountByExampleStatementId("countByExample");
        setDeleteByExampleStatementId("deleteByExample");
        // setDeleteByPrimaryKeyStatementId("deleteByPrimaryKey"); 
        setDeleteByPrimaryKeyStatementId("deleteById"); // deleteByPrimaryKey改成delete
        setInsertStatementId("insert");
        // setInsertSelectiveStatementId("insertSelective"); 
        setInsertSelectiveStatementId("insert"); // insertSelective改成insert
        setSelectAllStatementId("selectAll");
        // setSelectByExampleStatementId("selectByExample");
        // setSelectByExampleWithBLOBsStatementId("selectByExampleWithBLOBs");
        setSelectByPrimaryKeyStatementId("selectById");  // selectByPrimaryKey改成selectById
        // setUpdateByExampleStatementId("updateByExample");
        // setUpdateByExampleSelectiveStatementId("updateByExampleSelective");
        // setUpdateByExampleWithBLOBsStatementId("updateByExampleWithBLOBs");
        // setUpdateByPrimaryKeyStatementId("updateByPrimaryKey");
        // setUpdateByPrimaryKeySelectiveStatementId("updateByPrimaryKeySelective"); 
        setUpdateByPrimaryKeySelectiveStatementId("update"); // updateByPrimaryKeySelective改成update
        // setUpdateByPrimaryKeyWithBLOBsStatementId("updateByPrimaryKeyWithBLOBs");
        setBaseResultMapId("BaseResultMap");
        // setResultMapWithBLOBsId("ResultMapWithBLOBs");
        // setExampleWhereClauseId("Example_Where_Clause");
        setBaseColumnListId("Base_Column_List");
        // setBlobColumnListId("Blob_Column_List");
        // setMyBatis3UpdateByExampleWhereClauseId("Update_By_Example_Where_Clause");
    }

    protected void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback) {
        // if (getRules().generateExampleClass()) {
        //     AbstractJavaGenerator javaGenerator = new ExampleGenerator();
        //     initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        //     javaModelGenerators.add(javaGenerator);
        // }

        if (getRules().generatePrimaryKeyClass()) {
            AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator();
            initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generateBaseRecordClass()) {
            AbstractJavaGenerator javaGenerator = new BaseRecordGenerator();
            initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generateRecordWithBLOBsClass()) {
            AbstractJavaGenerator javaGenerator = new RecordWithBLOBsGenerator();
            initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        // AbstractJavaGenerator javaGenerator = new ThinModelGenerator();
        // initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        // javaModelGenerators.add(javaGenerator);


    }


}
