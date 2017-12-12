package base.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
	
	public void setArrayTagSelector(IArrayTagSelector selector) {
		if(selector == null) {
			this.arrayTagSelector = field -> field;
		}
		else {
			this.arrayTagSelector = selector;
		}
	}

	public void setDefaultTextField(String field) {
		this.defaultTextFieldName = field;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T read(File file, Class<T> clazz){
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			Element root = document.getDocumentElement();
			
			Type<T> type = Types.typeOf(clazz);
			return (T) createElement(type, root);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new XmlException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T read(InputStream is, Class<T> clazz) {
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);
			Element root = document.getDocumentElement();
			
			Type<T> type = Types.typeOf(clazz);
			return (T) createElement(type, root);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new XmlException(e);
		}
	}

	public <T> T read(String xml, Class<T> clazz) {
		ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
		return read(is, clazz);
	}
	
	@FunctionalInterface
	private static interface ITypeProvider<T>{
		public T create(String value);
	}
	
	private static HashMap<Class, ITypeProvider> providers = new HashMap<>();
	
	static {
		providers.put(String.class, value->value);
		providers.put(int.class, value->Integer.parseInt(value));
		providers.put(long.class, value->Long.parseLong(value));
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
			//普通类型
			else if(providers.containsKey(fieldType.asClass()))
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
		HashArrayListMultiMap<String, Element> childs = getChildElements(element);
		
		Element fieldElement = childs.getOne(fieldName);
		if(fieldElement != null) {
			return createElement(fieldType, fieldElement);
		}
		return null;
	}
	
	private Object createArrayObjectElement(FieldInfo field, Element element) {
		String fieldName = field.getName();
		Type fieldType = field.getType();
		HashArrayListMultiMap<String, Element> childs = getChildElements(element);
		
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
	
	private Object createBasicElement(FieldInfo field, Element element) {
		String fieldName = field.getName();
		Type fieldType = field.getType();
		ITypeProvider provider = providers.get(fieldType.asClass());

		if(fieldName.equals(defaultTextFieldName)) {
			return provider.create(element.getTextContent());
		}
		
		HashArrayListMultiMap<String, Element> childs = getChildElements(element);
		
		if(element.hasAttribute(fieldName)) {
			String value = element.getAttribute(fieldName);
			return provider.create(value);
		}
		else {
			Element fieldElement = childs.getOne(fieldName);
			if(fieldElement != null) {
				return provider.create(fieldElement.getTextContent());
			}
		}
		return null;
	}
	
	private static HashArrayListMultiMap<String, Element> getChildElements(Element element){
		HashArrayListMultiMap<String, Element> map = new HashArrayListMultiMap<>();
		NodeList nodes = element.getChildNodes();
		for(int i=0; i<nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				map.put(child.getTagName(), child);
			}
		}
		return map;
	}

}
