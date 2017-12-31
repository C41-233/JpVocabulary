package base.xml;

import org.w3c.dom.Element;

import base.reflect.ClassType;

interface IXmlReaderDeserializer {

	public Object createElement(ClassType type, Element element);
	
}
