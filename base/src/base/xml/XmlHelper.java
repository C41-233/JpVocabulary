package base.xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import base.utility.linq.Linq;

class XmlHelper {

	public static Element getChildElement(Element element, String tagName) {
		return Linq.from(element.getChildNodes())
				.where(node->node.getNodeType() == Node.ELEMENT_NODE)
				.<Element>cast()
				.findFirst(e->e.getTagName().equals(tagName));
	}
	
	public static Element[] getChildElements(Element element, String tagName) {
		return Linq.from(element.getChildNodes())
				.where(node->node.getNodeType() == Node.ELEMENT_NODE)
				.<Element>cast()
				.where(e->e.getTagName().equals(tagName))
				.toArray(Element.class);
	}
	
}
