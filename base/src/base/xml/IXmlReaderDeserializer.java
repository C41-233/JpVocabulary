package base.xml;

import org.w3c.dom.Element;

import base.reflect.TypeInfo;

interface IXmlReaderDeserializer {

	public Object createElement(TypeInfo type, Element element);
	
}
