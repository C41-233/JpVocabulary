package base.xml;

import org.w3c.dom.Element;

import base.reflect.Type;

interface IXmlReaderDeserializer {

	public Object createElement(Type type, Element element);
	
}
