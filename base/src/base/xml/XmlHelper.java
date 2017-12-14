package base.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import base.utility.collection.map.HashArrayListMultiMap;

class XmlHelper {

	public static HashArrayListMultiMap<String, Element> getChildElements(Element element){
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
