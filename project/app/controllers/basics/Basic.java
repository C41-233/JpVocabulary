package controllers.basics;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;

import core.controller.HtmlControllerBase;

public class Basic extends HtmlControllerBase{

	public static void index() {
		detail("ka");
	}
	
	public static void detail(String index) {
		processContent();
	}

	private static void processContent() {
	}
	
	private static class ContentGroup{
		public Content[] group;
	}
	
	private static class Content{
		public String name;
		public String value;
	}
	
}
