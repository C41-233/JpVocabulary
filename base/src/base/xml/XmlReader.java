package base.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import base.reflect.FieldInfo;
import base.reflect.Type;
import base.reflect.Types;
import base.utility.collection.map.HashArrayListMultiMap;
import base.utility.linq.Linq;

public class XmlReader {

	@FunctionalInterface
	public static interface IArrayTagSelector{
		String getTagName(String fieldName);
	}
	
	private final DocumentBuilderFactory factory;
	
	private IArrayTagSelector arrayTagSelector;
	private String defaultTextFieldName;
	
	public XmlReader() {
		this.factory = DocumentBuilderFactory.newInstance();
		this.arrayTagSelector = field->{
			if(field.length() > 1 && field.endsWith("s")) {
				return field.substring(0, field.length()-1);
			}
			return field;
		};
	}
	
	/**
	 * 设置数组类型映射的XML标签，默认情况下数组字段与标签一致，但字段以s结尾时，取其前面部分
	 * @param selector 数组字段映射到的标签
	 */
	public void setArrayTagSelector(IArrayTagSelector selector) {
		if(selector == null) {
			this.arrayTagSelector = field -> field;
		}
		else {
			this.arrayTagSelector = selector;
		}
	}

	/**
	 * 设置文本字段
	 * @param field 文本字段名称，null表示不设置默认的文本字段
	 */
	public void setDefaultTextField(String field) {
		this.defaultTextFieldName = field;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T readInner(Document document, Class<T> clazz) {
		Element root = document.getDocumentElement();
		
		Type<T> type = Types.typeOf(clazz);
		return (T) createElement(type, root);
	}
	
	public <T> T read(File file, Class<T> clazz){
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			return readInner(document, clazz);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new XmlException(e);
		}
	}
	
	public <T> T read(InputStream is, Class<T> clazz) {
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);
			return readInner(document, clazz);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new XmlException(e);
		}
	}

	public <T> T read(String xml, Class<T> clazz) {
		ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
		return read(is, clazz);
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<T> readListInner(Document document, Class<T> clazz, String tag) {
		Element root = document.getDocumentElement();

		List<Element> tags = Linq.from(root.getChildNodes()).instanceOf(Element.class).where(e->e.getTagName().equals(tag)).toList();
		List<T> list = new ArrayList<>(tags.size());
		
		Type<T> type = Types.typeOf(clazz);
		for(Element e : tags) {
			list.add((T) createElement(type, e));
		}
		return list;
	}
	
	public <T> List<T> readList(File file, Class<T> clazz, String tag){
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			return readListInner(document, clazz, tag);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new XmlException(e);
		}
	}
	
	public <T> List<T> readList(InputStream is, Class<T> clazz, String tag){
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);
			return readListInner(document, clazz, tag);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new XmlException(e);
		}
	}
	
	public <T> List<T> readList(String xml, Class<T> clazz, String tag){
		ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
		return readList(is, clazz, tag);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T[] readArrayInner(Document document, Class<T> clazz, String tag) {
		Element root = document.getDocumentElement();

		List<Element> tags = Linq.from(root.getChildNodes()).instanceOf(Element.class).where(e->e.getTagName().equals(tag)).toList();
		
		T[] arr = (T[]) Array.newInstance(clazz, tags.size());
		
		Type<T> type = Types.typeOf(clazz);
		for(int i=0; i<tags.size(); i++) {
			arr[i] = (T) createElement(type, tags.get(i));
		}
		return arr;
	}
	
	public <T> T[] readArray(InputStream is, Class<T> clazz, String tag) {
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);
			return readArrayInner(document, clazz, tag);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new XmlException(e);
		}
	}
	
	public <T> T[] readArray(String xml, Class<T> clazz, String tag) {
		ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
		return readArray(is, clazz, tag);
	}

	public <T> T[] readArray(File file, Class<T> clazz, String tag) {
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			return readArrayInner(document, clazz, tag);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new XmlException(e);
		}
	}

	private Object createElement(Type type, Element element) {
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
	
	private Object createObjectElement(FieldInfo field, Element element) {
		String fieldName = field.getName();
		Type fieldType = field.getType();
		HashArrayListMultiMap<String, Element> childs = XmlHelper.getChildElements(element);
		
		Element fieldElement = childs.getOne(fieldName);
		if(fieldElement != null) {
			return createElement(fieldType, fieldElement);
		}
		return null;
	}
	
	private Object createArrayObjectElement(FieldInfo field, Element element) {
		String fieldName = field.getName();
		Type fieldType = field.getType();
		HashArrayListMultiMap<String, Element> childs = XmlHelper.getChildElements(element);
		
		Type arrayComponentType = fieldType.getArrayComponentType();
		String arrayTagName = arrayTagSelector.getTagName(fieldName);
		Element[] childElements = Linq.from(childs.getAll(arrayTagName)).toArray(Element.class);
		Object array = Array.newInstance(arrayComponentType.asClass(), childElements.length);
		for(int i=0; i<childElements.length; i++) {
			Object childValue = createElement(arrayComponentType, childElements[i]);
			Array.set(array, i, childValue);
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	private Object createListObjectElement(FieldInfo field, Element element) {
		String fieldName = field.getName();
		XmlListClass listClass = field.getAnnotation(XmlListClass.class);
		if(listClass == null) {
			return null;
		}

		Type componentType = Types.typeOf(listClass.value());
		HashArrayListMultiMap<String, Element> childs = XmlHelper.getChildElements(element);
		
		String arrayTagName = arrayTagSelector.getTagName(fieldName);
		Element[] childElements = Linq.from(childs.getAll(arrayTagName)).toArray(Element.class);
		List<Object> list = new ArrayList<>();
		for(int i=0; i<childElements.length; i++) {
			Object childValue = createElement(componentType, childElements[i]);
			list.add(childValue);
		}
		return list;
	}

	private Object createBasicElement(FieldInfo field, Element element) {
		String fieldName = field.getName();
		Type fieldType = field.getType();

		if(fieldName.equals(defaultTextFieldName)) {
			return TypeProviers.create(fieldType.asClass(), element.getTextContent());
		}
		
		HashArrayListMultiMap<String, Element> childs = XmlHelper.getChildElements(element);
		
		if(element.hasAttribute(fieldName)) {
			String value = element.getAttribute(fieldName);
			return TypeProviers.create(fieldType.asClass(), value);
		}
		else {
			Element fieldElement = childs.getOne(fieldName);
			if(fieldElement != null) {
				return TypeProviers.create(fieldType.asClass(), fieldElement.getTextContent());
			}
		}
		return null;
	}
	
}
