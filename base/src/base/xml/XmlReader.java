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

import base.reflect.Type;
import base.reflect.Types;
import base.utility.linq.Linq;

public class XmlReader {

	private final XmlReaderSettings settings = new XmlReaderSettings();
	
	@FunctionalInterface
	public static interface IArrayTagSelector{
		String getTagName(String fieldName);
	}
	
	private final DocumentBuilderFactory factory;
	
	public XmlReader() {
		this.factory = DocumentBuilderFactory.newInstance();
		settings.arrayTagSelector = field->{
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
			settings.arrayTagSelector = field -> field;
		}
		else {
			settings.arrayTagSelector = selector;
		}
	}

	/**
	 * 设置文本字段
	 * @param field 文本字段名称，null表示不设置默认的文本字段
	 */
	public void setDefaultTextField(String field) {
		settings.defaultTextFieldName = field;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T readInner(Document document, Class<T> clazz) {
		Element root = document.getDocumentElement();
		
		Type<T> type = Types.typeOf(clazz);
		IXmlReaderDeserializer deserializer = getDeserializer();
		return (T) deserializer.createElement(type, root);
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

		IXmlReaderDeserializer deserializer = getDeserializer();
		for(Element e : tags) {
			list.add((T) deserializer.createElement(type, e));
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
		IXmlReaderDeserializer deserializer = getDeserializer();
		for(int i=0; i<tags.size(); i++) {
			arr[i] = (T) deserializer.createElement(type, tags.get(i));
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

	private IXmlReaderDeserializer getDeserializer() {
		SimpleXmlReaderDeserializer deserializer = new SimpleXmlReaderDeserializer(settings);
		return deserializer;
	}
	
}
