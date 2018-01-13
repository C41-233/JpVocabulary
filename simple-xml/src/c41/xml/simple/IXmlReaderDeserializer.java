package c41.xml.simple;

import org.w3c.dom.Element;

import c41.reflect.TypeInfo;

interface IXmlReaderDeserializer {

	public Object createElement(TypeInfo<?> type, Element element);
	
}
