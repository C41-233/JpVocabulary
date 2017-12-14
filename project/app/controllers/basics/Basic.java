package controllers.basics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.sax.SAXResult;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;

import base.xml.XmlReader;
import core.controller.HtmlControllerBase;

public class Basic extends HtmlControllerBase{

	public static void index() {
		detail("ka");
	}
	
	public static void detail(String index) {
		processContent();
	}

	private static void processContent() {
		XmlReader reader = new XmlReader();
		ContentGroup[] groups = reader.readObjects(new File("data/basics/contents.xml"), ContentGroup.class, "group");
		renderText("123");
	}
	
	private static class ContentGroup{
		public String name;
		public List<Content> contents = new ArrayList<>();
	}
	
	private static class Content{
		public String name;
		public String value;
	}
	
}
