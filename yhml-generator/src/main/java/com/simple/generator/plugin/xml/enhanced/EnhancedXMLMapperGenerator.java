package com.simple.generator.plugin.xml.enhanced;

import com.simple.generator.plugin.xml.common.SelectByPrimaryKeyForUpdateElementGenerator;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * 已经废弃
 */
@Deprecated
public class EnhancedXMLMapperGenerator extends XMLMapperGenerator {

	@Override
	protected XmlElement getSqlMapElement() {
		XmlElement element = super.getSqlMapElement();
		addSelectByPrimaryKeyForUpdateElement(element);
		return element;
	}

	protected void addSelectByPrimaryKeyForUpdateElement(XmlElement parentElement) {
		AbstractXmlElementGenerator elementGenerator = new SelectByPrimaryKeyForUpdateElementGenerator();
		initializeAndExecuteGenerator(elementGenerator, parentElement);
	}

}
