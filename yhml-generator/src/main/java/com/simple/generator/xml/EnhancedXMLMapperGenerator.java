package com.simple.generator.xml;

import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import com.simple.generator.xml.enhanced.SelectByPrimaryKeyForUpdateElementGenerator;

public class EnhancedXMLMapperGenerator extends XMLMapperGenerator {

	public EnhancedXMLMapperGenerator() {
		super();
	}

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
