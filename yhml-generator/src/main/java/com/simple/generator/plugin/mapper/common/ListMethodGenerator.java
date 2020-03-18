/**
 * Copyright 2006-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.simple.generator.plugin.mapper.common;

import com.simple.generator.util.FullyJavaTypeUtil;
import com.simple.generator.plugin.xml.common.XmlStatementId;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Jeff Butler
 */
public class ListMethodGenerator extends AbstractJavaMapperMethodGenerator implements FullyJavaTypeUtil {

    public ListMethodGenerator() {
        super();
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        importedTypes.add(fqjt);

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType("List<" + introspectedTable.getBaseRecordType() + ">")); //$NON-NLS-1$
        method.setName(XmlStatementId.LIST_BY_CRITERIA);
        method.addParameter(new Parameter(fqjt, "record")); //$NON-NLS-1$
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

        addMapperAnnotations(method);

        if (context.getPlugins().clientCountByExampleMethodGenerated(method, interfaze, introspectedTable)) {
            addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Method method) {
    }

    public void addExtraImports(Interface interfaze) {
        interfaze.addImportedType(listType);
    }
}
