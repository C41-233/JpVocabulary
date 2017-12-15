package base.xml;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import base.reflect.FieldInfo;
import base.reflect.Type;
import base.reflect.Types;
import base.utility.collection.map.HashArrayListMultiMap;
import base.utility.linq.Linq;

class XmlReaderDeserializer {

	private final XmlReaderSettings settings;
	
	public XmlReaderDeserializer(XmlReaderSettings settings) {
		this.settings = settings;
	}

	public Object createElement(Type type, Element element) {
		Object obj = type.newInstance();
		
		for(FieldInfo field : type.getFields()) {
			Type fieldType = field.getType();
			
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
	
	public Object createObjectElement(FieldInfo field, Element element) {
		String tagName = field.getName();
		{
			//指定子标签
			String xmlTagName = getXmlTagValue(field);
			if(xmlTagName != null) {
				tagName = xmlTagName;
			}
		}
		
		Type fieldType = field.getType();
		HashArrayListMultiMap<String, Element> childs = XmlHelper.getChildElements(element);
		
		Element fieldElement = childs.getOne(tagName);
		if(fieldElement != null) {
			return createElement(fieldType, fieldElement);
		}
		return null;
	}
	
	public Object createArrayObjectElement(FieldInfo field, Element element) {
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
		
		Type fieldType = field.getType();
		HashArrayListMultiMap<String, Element> childs = XmlHelper.getChildElements(element);
		
		Type arrayComponentType = fieldType.getArrayComponentType();
		Element[] childElements = Linq.from(childs.getAll(tagName)).toArray(Element.class);
		Object array = Array.newInstance(arrayComponentType.asClass(), childElements.length);
		for(int i=0; i<childElements.length; i++) {
			Object childValue = createElement(arrayComponentType, childElements[i]);
			Array.set(array, i, childValue);
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	public Object createListObjectElement(FieldInfo field, Element element) {
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

		Type componentType = Types.typeOf(listClass.value());
		HashArrayListMultiMap<String, Element> childs = XmlHelper.getChildElements(element);
		
		Element[] childElements = Linq.from(childs.getAll(tagName)).toArray(Element.class);
		List<Object> list = new ArrayList<>();
		for(int i=0; i<childElements.length; i++) {
			Object childValue = createElement(componentType, childElements[i]);
			list.add(childValue);
		}
		return list;
	}

	public Object createBasicElement(FieldInfo field, Element element) {
		String tagName = field.getName();
		Type fieldType = field.getType();
		HashArrayListMultiMap<String, Element> childs = XmlHelper.getChildElements(element);
		
		//只读取子类型
		XmlTag xmlTag = field.getAnnotation(XmlTag.class);
		if(xmlTag != null) {
			tagName = xmlTag.value().equals("") ? tagName : xmlTag.value();
			Element fieldElement = childs.getOne(tagName);
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
			Element fieldElement = childs.getOne(tagName);
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
