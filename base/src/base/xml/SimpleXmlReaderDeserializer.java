package base.xml;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import base.reflect.FieldInfo;
import base.reflect.TypeInfo;
import base.reflect.Types;

class SimpleXmlReaderDeserializer implements IXmlReaderDeserializer{

	private final XmlReaderSettings settings;
	
	public SimpleXmlReaderDeserializer(XmlReaderSettings settings) {
		this.settings = settings;
	}

	@Override
	public Object createElement(TypeInfo type, Element element) {
		Object obj = type.newInstance();
		
		for(FieldInfo field : type.getFields()) {
			TypeInfo fieldType = field.getType();
			
			//数组
			if(fieldType.isArray()) {
				Object array = createArrayObjectElement(field, element);
				field.setValue(obj, array);
			}
			//列表
			else if(fieldType.asClass() == List.class) {
				Object list = createListObjectElement(field, element);
				field.setValue(obj, list);
			}
			//普通类型
			else if(TypeProviers.contains(fieldType.asClass()))
			{
				Object value = createBasicElement(field, element);
				if(value != null) {
					field.setValue(obj, value);
				}
			}
			//级联类型
			else {
				Object value = createObjectElement(field, element);
				if(value != null) {
					field.setValue(obj, value);
				}
			}
		}
		return obj;
	}
	
	private Object createObjectElement(FieldInfo field, Element element) {
		String tagName = field.getName();
		{
			//指定子标签
			String xmlTagName = getXmlTagValue(field);
			if(xmlTagName != null) {
				tagName = xmlTagName;
			}
		}
		
		TypeInfo fieldType = field.getType();
		Element fieldElement = XmlHelper.getChildElement(element, tagName);
		if(fieldElement != null) {
			return createElement(fieldType, fieldElement);
		}
		return null;
	}
	
	private Object createArrayObjectElement(FieldInfo field, Element element) {
		String tagName = field.getName();
		{
			//指定子标签
			String xmlTagName = getXmlTagValue(field);
			if(xmlTagName != null) {
				tagName = xmlTagName;
			}
			else {
				tagName = settings.arrayTagSelector.getTagName(tagName);
			}
		}
		
		Element[] childElements = XmlHelper.getChildElements(element, tagName);

		TypeInfo fieldType = field.getType();
		TypeInfo arrayComponentType = fieldType.getArrayComponentType();
		Object array = Array.newInstance(arrayComponentType.asClass(), childElements.length);
		for(int i=0; i<childElements.length; i++) {
			Object childValue = createElement(arrayComponentType, childElements[i]);
			Array.set(array, i, childValue);
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	private Object createListObjectElement(FieldInfo field, Element element) {
		XmlListClass listClass = field.getAnnotation(XmlListClass.class);
		if(listClass == null) {
			return null;
		}
		
		String tagName = field.getName();
		{
			//指定其子标签
			String xmlTagName = getXmlTagValue(field);
			if(xmlTagName != null) {
				tagName = xmlTagName;
			}
			else {
				tagName = settings.arrayTagSelector.getTagName(tagName);
			}
		}

		Element[] childElements = XmlHelper.getChildElements(element, tagName);

		TypeInfo componentType = Types.typeOf(listClass.value());
		List<Object> list = new ArrayList<>();
		for(int i=0; i<childElements.length; i++) {
			//基本类型
			Object childValue;
			if(TypeProviers.contains(componentType.asClass())) {
				childValue = TypeProviers.create(componentType.asClass(), childElements[i].getTextContent());
			}
			//级联类型
			else {
				childValue = createElement(componentType, childElements[i]);
			}
			list.add(childValue);
		}
		return list;
	}

	private Object createBasicElement(FieldInfo field, Element element) {
		String tagName = field.getName();
		TypeInfo fieldType = field.getType();
		
		//只读取子类型
		XmlTag xmlTag = field.getAnnotation(XmlTag.class);
		if(xmlTag != null) {
			tagName = xmlTag.value().equals("") ? tagName : xmlTag.value();
			Element fieldElement = XmlHelper.getChildElement(element, tagName);
			if(fieldElement != null) {
				return TypeProviers.create(fieldType.asClass(), fieldElement.getTextContent());
			}
			else {
				return null;
			}
		}
		
		//只读取文本
		XmlText xmlText = field.getAnnotation(XmlText.class);
		if(xmlText != null) {
			return TypeProviers.create(fieldType.asClass(), element.getTextContent());
		}
		
		//只读取属性
		XmlAttribute xmlAttribute = field.getAnnotation(XmlAttribute.class);
		if(xmlAttribute != null) {
			tagName = xmlAttribute.value().equals("") ? tagName : xmlAttribute.value();
			String value = element.getAttribute(tagName);
			return TypeProviers.create(fieldType.asClass(), value);
		}
		
		//字段为默认读取文本
		if(tagName.equals(settings.defaultTextFieldName)) {
			return TypeProviers.create(fieldType.asClass(), element.getTextContent());
		}
		
		//其他情况先读取属性，再读取子类型
		if(element.hasAttribute(tagName)) {
			String value = element.getAttribute(tagName);
			return TypeProviers.create(fieldType.asClass(), value);
		}
		else {
			Element fieldElement = XmlHelper.getChildElement(element, tagName);
			if(fieldElement != null) {
				return TypeProviers.create(fieldType.asClass(), fieldElement.getTextContent());
			}
		}
		return null;
	}
	
	private String getXmlTagValue(FieldInfo field) {
		XmlTag xmlTag = field.getAnnotation(XmlTag.class);
		if(xmlTag == null) {
			return null;
		}
		String value = xmlTag.value();
		if(value.equals("")) {
			return null;
		}
		return value;
	}
	
}
